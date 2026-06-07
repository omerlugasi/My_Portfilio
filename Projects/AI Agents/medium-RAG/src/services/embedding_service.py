from langchain_openai import OpenAIEmbeddings

from src.config.settings import (
    LLMOD_API_KEY,
    LLMOD_BASE_URL,
    EMBEDDING_MODEL,
)


class EmbeddingService:

    def __init__(self):
        self.embeddings = OpenAIEmbeddings(
            api_key=LLMOD_API_KEY,
            base_url=LLMOD_BASE_URL,
            model=EMBEDDING_MODEL,
        )

    def embed_text(self, text: str):
        return self.embeddings.embed_query(text)