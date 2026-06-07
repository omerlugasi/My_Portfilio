from fastapi import FastAPI
from src.routes.prompt import router as prompt_router
from src.routes.stats import router as stats_router

app = FastAPI(
    title="Medium RAG Assistant",
    version="1.0.0"
)

app.include_router(prompt_router)
app.include_router(stats_router)


@app.get("/")
def root():
    return {
        "message": "Medium RAG Assistant is running"
    }