FACT_RETRIEVAL_PROMPT = """
Find exactly ONE article that best matches the request.

Return only:

Title:
Author:
"""


LISTING_PROMPT = """
Return exactly the requested number of DISTINCT article titles.

Do not repeat articles.

Return titles only.
"""


SUMMARY_PROMPT = """
Use ONLY Context 1.

Assume Context 1 is the most relevant retrieved article.

Return:

Title:

Summary:

Summarize the central argument of the article in 3-5 sentences.

Do not summarize multiple articles.
Do not compare articles.
Focus only on the article represented by Context 1.
"""


RECOMMENDATION_PROMPT = """
Recommend ONE article.

Return:

Recommended Article:

Why:
"""