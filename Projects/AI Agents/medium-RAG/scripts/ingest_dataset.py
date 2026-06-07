import pandas as pd
from pathlib import Path

from src.services.chunking_service import ChunkingService
from src.services.embedding_service import EmbeddingService
from src.services.pinecone_service import PineconeService

DATA_PATH = Path("data/medium-english-50mb.csv")

BATCH_SIZE = 100


def main():

    print("Loading dataset...")

    df = pd.read_csv(DATA_PATH)

    chunking_service = ChunkingService()
    embedding_service = EmbeddingService()
    pinecone_service = PineconeService()

    vectors = []
    total_uploaded = 0

    for article_id, row in df.iterrows():

        if article_id % 100 == 0:
            print(f"Processed {article_id} articles...")

        chunks = chunking_service.split_text(
            str(row["text"])
        )

        for chunk_index, chunk in enumerate(chunks):

            embedding = embedding_service.embed_text(
                chunk
            )

            vectors.append(
                {
                    "id": f"{article_id}_{chunk_index}",
                    "values": embedding,
                    "metadata": {
                        "article_id": str(article_id),
                        "title": str(row["title"]),
                        "authors": str(row["authors"]),
                        "timestamp": str(row["timestamp"]),
                        "tags": str(row["tags"]),
                        "chunk_text": chunk,
                    },
                }
            )

            if len(vectors) >= BATCH_SIZE:

                pinecone_service.upsert_vectors(vectors)

                total_uploaded += len(vectors)

                print(f"Uploaded {total_uploaded} vectors")

                vectors = []

    if vectors:

        pinecone_service.upsert_vectors(vectors)

        total_uploaded += len(vectors)

    print(f"Done! Uploaded {total_uploaded} vectors.")


if __name__ == "__main__":
    main()