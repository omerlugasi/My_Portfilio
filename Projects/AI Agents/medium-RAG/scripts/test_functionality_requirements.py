from src.services.rag_service import answer_question


TEST_QUERIES = [

    # 1. Precise Fact Retrieval
    "Find an article that reframes marketing as a conversation with readers, aimed at writers who find self-promotion uncomfortable. Provide the title and author.",

    # 2. Multi-result Topic Listing
    "List exactly 3 articles about mental health. Return only the titles.",

    # 3. Key Idea Summary Extraction
    "Find an article about coronavirus and summarize its central argument.",

    # 4. Recommendation with Justification
    "I want practical, beginner-friendly advice on managing anxiety during the pandemic. Which article would you recommend and why?"
]


for i, query in enumerate(TEST_QUERIES, start=1):

    print("\n")
    print("=" * 100)
    print(f"FUNCTIONALITY TEST #{i}")
    print("=" * 100)

    print("\nQUERY:")
    print(query)

    result = answer_question(query)

    print("\nRESPONSE:")
    print(result["response"])

    print("\nRETRIEVED CONTEXT:")
    for j, item in enumerate(result["context"], start=1):
        print(
            f"{j}. "
            f"article_id={item['article_id']} | "
            f"{item['score']:.4f} | "
            f"{item['title']}"
        )

    print("\n")