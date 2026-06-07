from pinecone import Pinecone

from src.config.settings import (
    PINECONE_API_KEY,
    PINECONE_INDEX_NAME,
)


class PineconeService:

    def __init__(self):
        self.pc = Pinecone(
            api_key=PINECONE_API_KEY
        )

        self.index = self.pc.Index(
            PINECONE_INDEX_NAME
        )

    def upsert_vectors(self, vectors):
        self.index.upsert(
            vectors=vectors
        )

    def get_stats(self):
        return self.index.describe_index_stats()

    def query(self, embedding, top_k):
        return self.index.query(
            vector=embedding,
            top_k=top_k,
            include_metadata=True
        )
    
    def delete_all_vectors(self):
        self.index.delete(delete_all=True)