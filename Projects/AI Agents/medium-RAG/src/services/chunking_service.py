from langchain_text_splitters import RecursiveCharacterTextSplitter

from src.config.settings import (
    CHUNK_SIZE,
    OVERLAP_RATIO,
)


class ChunkingService:

    def __init__(self):
        self.splitter = RecursiveCharacterTextSplitter.from_tiktoken_encoder(
            encoding_name="cl100k_base",
            chunk_size=CHUNK_SIZE,
            chunk_overlap=int(CHUNK_SIZE * OVERLAP_RATIO),
        )

    def split_text(self, text: str):
        return self.splitter.split_text(text)