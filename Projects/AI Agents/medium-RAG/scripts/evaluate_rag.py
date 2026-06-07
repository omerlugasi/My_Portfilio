# scripts/evaluate_rag.py

from src.services.rag_service import answer_question


QUESTIONS = [
    "What are the effects of coronavirus on mental health?",
    "How does social media affect anxiety during the pandemic?",
    "Why are young adults suffering most from COVID?",
    "What causes loneliness during lockdowns?",
    "What are long-haulers?",
    "How did the pandemic affect productivity?",
    "What are common psychological effects of isolation?",
    "How does coronavirus affect the brain?",
]


for i, question in enumerate(QUESTIONS, start=1):
    print("\n" + "=" * 100)
    print(f"QUESTION #{i}")
    print(question)
    print("=" * 100)

    result = answer_question(question)

    print("\nANSWER:\n")
    print(result["response"])

    print("\nRETRIEVED CONTEXT:\n")

    for j, item in enumerate(result["context"], start=1):
        print(
            f"{j}. "
            f"score={item['score']:.4f} | "
            f"title={item['title']}"
        )

    print("\n")