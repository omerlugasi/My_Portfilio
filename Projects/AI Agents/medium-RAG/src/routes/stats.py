from fastapi import APIRouter
from src.config.settings import CHUNK_SIZE, OVERLAP_RATIO, TOP_K

router = APIRouter()


@router.get("/api/stats")
def get_stats():
    return {
        "chunk_size": CHUNK_SIZE,
        "overlap_ratio": OVERLAP_RATIO,
        "top_k": TOP_K
    }