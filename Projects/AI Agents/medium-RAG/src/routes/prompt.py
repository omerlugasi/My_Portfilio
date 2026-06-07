from fastapi import APIRouter
from src.models.schemas import PromptRequest
from src.services.rag_service import answer_question

router = APIRouter()


@router.post("/api/prompt")
def prompt(request: PromptRequest):
    return answer_question(request.question)