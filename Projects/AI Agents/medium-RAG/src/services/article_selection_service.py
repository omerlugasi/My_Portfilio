def group_matches_by_article(matches):
    articles = {}

    for match in matches:
        metadata = match["metadata"]
        article_id = str(metadata.get("article_id", ""))

        if article_id not in articles:
            articles[article_id] = {
                "article_id": article_id,
                "title": str(metadata.get("title", "")),
                "authors": str(metadata.get("authors", "")),
                "timestamp": str(metadata.get("timestamp", "")),
                "tags": str(metadata.get("tags", "")),
                "best_score": float(match["score"]),
                "chunks": [],
            }

        articles[article_id]["chunks"].append({
            "chunk": str(metadata.get("chunk_text", "")),
            "score": float(match["score"]),
        })

        articles[article_id]["best_score"] = max(
            articles[article_id]["best_score"],
            float(match["score"])
        )

    return sorted(
        articles.values(),
        key=lambda article: article["best_score"],
        reverse=True
    )


def select_articles_for_task(matches, task_type: str):
    articles = group_matches_by_article(matches)

    if task_type == "listing":
        return articles[:3]

    if task_type in ["fact_retrieval", "summary", "recommendation"]:
        return articles[:1]

    return articles