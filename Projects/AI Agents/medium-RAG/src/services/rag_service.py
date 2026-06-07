from src.config.settings import TOP_K
from src.services.embedding_service import EmbeddingService
from src.services.pinecone_service import PineconeService
from src.services.llm_service import LLMService
from src.services.task_router import get_task_prompt, get_task_type
from src.services.article_selection_service import select_articles_for_task


SYSTEM_PROMPT = """
You are a Retrieval-Augmented Generation (RAG) assistant operating over a
Medium articles dataset.

You must answer questions exclusively using the retrieved article passages
provided in the user prompt.

When relevant information exists in the retrieved context:
- Provide a direct answer.
- Summarize and combine information from multiple passages.
- Reference article titles when useful.

When relevant information does not exist:
- Respond exactly:
  "I don't know based on the provided Medium articles data."

Never use information that is not present in the retrieved context.
"""


def build_context_from_articles(articles):
    context_items = []

    for article in articles:
        for chunk_item in article["chunks"]:
            context_items.append(
                {
                    "article_id": article["article_id"],
                    "title": article["title"],
                    "authors": article["authors"],
                    "timestamp": article["timestamp"],
                    "tags": article["tags"],
                    "chunk": chunk_item["chunk"],
                    "score": float(chunk_item["score"]),
                }
            )

    return context_items


def build_context_from_matches(matches):
    context_items = []

    for match in matches:
        metadata = match["metadata"]

        context_items.append(
            {
                "article_id": str(metadata.get("article_id", "")),
                "title": str(metadata.get("title", "")),
                "authors": str(metadata.get("authors", "")),
                "timestamp": str(metadata.get("timestamp", "")),
                "tags": str(metadata.get("tags", "")),
                "chunk": str(metadata.get("chunk_text", "")),
                "score": float(match["score"]),
            }
        )

    return context_items


def build_user_prompt(
    question: str,
    context_items: list[dict],
    task_prompt: str,
) -> str:

    context_text = ""

    for i, item in enumerate(context_items, start=1):
        context_text += f"""
[Context {i}]
Article ID: {item["article_id"]}
Title: {item["title"]}
Authors: {item["authors"]}
Timestamp: {item["timestamp"]}
Tags: {item["tags"]}
Score: {item["score"]}

Passage:
{item["chunk"]}
"""

    return f"""
Use only the following retrieved Medium article context to answer the question.

Retrieved Context:

{context_text}

Task Instructions:
{task_prompt}

Question:
{question}

Additional Instructions:
- Answer using only the retrieved context above.
- If multiple contexts are relevant, combine the information when appropriate.
- Follow the task instructions exactly.
- If the answer does not exist in the retrieved context, respond:
  "I don't know based on the provided Medium articles data."
"""


def answer_question(question: str):

    task_type = get_task_type(question)
    task_prompt = get_task_prompt(task_type)

    embedding_service = EmbeddingService()
    pinecone_service = PineconeService()
    llm_service = LLMService()

    query_embedding = embedding_service.embed_text(question)

    results = pinecone_service.query(
        embedding=query_embedding,
        top_k=TOP_K,
    )

    matches = results["matches"]

    if task_type in ["fact_retrieval", "listing", "summary", "recommendation"]:
        selected_articles = select_articles_for_task(
            matches=matches,
            task_type=task_type,
        )

        context_items = build_context_from_articles(
            selected_articles
        )

    else:
        context_items = build_context_from_matches(
            matches
        )

    user_prompt = build_user_prompt(
        question=question,
        context_items=context_items,
        task_prompt=task_prompt,
    )

    response = llm_service.invoke_with_messages(
        system_prompt=SYSTEM_PROMPT,
        user_prompt=user_prompt,
    )

    return {
        "response": response,
        "context": context_items,
        "Augmented_prompt": {
            "System": SYSTEM_PROMPT,
            "User": user_prompt,
        },
    }