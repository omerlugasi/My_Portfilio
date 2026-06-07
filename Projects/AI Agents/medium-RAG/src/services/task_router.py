from src.prompts.task_prompts import (
    FACT_RETRIEVAL_PROMPT,
    LISTING_PROMPT,
    SUMMARY_PROMPT,
    RECOMMENDATION_PROMPT,
)


def get_task_type(question: str) -> str:
    q = question.lower()

    if "title and author" in q:
        return "fact_retrieval"

    if "list" in q and "article" in q:
        return "listing"

    if "summarize" in q:
        return "summary"

    if (
        "recommend" in q
        or "which article" in q
        or "suggest" in q
    ):
        return "recommendation"

    return "qa"


def get_task_prompt(task_type: str) -> str:
    if task_type == "fact_retrieval":
        return FACT_RETRIEVAL_PROMPT

    if task_type == "listing":
        return LISTING_PROMPT

    if task_type == "summary":
        return SUMMARY_PROMPT

    if task_type == "recommendation":
        return RECOMMENDATION_PROMPT

    return ""