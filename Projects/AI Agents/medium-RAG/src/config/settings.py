import os
from dotenv import load_dotenv

load_dotenv()

LLMOD_API_KEY = os.getenv("LLMOD_API_KEY")
LLMOD_BASE_URL = os.getenv("LLMOD_BASE_URL")

CHAT_MODEL = os.getenv("CHAT_MODEL")
EMBEDDING_MODEL = os.getenv("EMBEDDING_MODEL")

PINECONE_API_KEY = os.getenv("PINECONE_API_KEY")
PINECONE_INDEX_NAME = os.getenv("PINECONE_INDEX_NAME")

CHUNK_SIZE = 800
OVERLAP_RATIO = 0.2
TOP_K = 8