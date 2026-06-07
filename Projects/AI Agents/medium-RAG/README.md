# Medium Article RAG Assistant

A Retrieval-Augmented Generation (RAG) assistant for answering questions over a corpus of Medium articles.

## Architecture

```text
User Question
      |
      v
Question Embedding
      |
      v
Pinecone Retrieval
      |
      v
Task Router
      |
      v
Article Selection
      |
      v
GPT Model
      |
      v
Final Answer
```

## Dataset

- Articles: 7,682
- Indexed Chunks: 18,661
- Chunk Size: 800 tokens
- Overlap: 20%
- Top-K Retrieval: 8

## Project Structure

```text
Medium-RAG/
├── src/
├── scripts/
├── docs/
├── README.md
├── requirements.txt
└── .gitignore
```

## Models

- Embedding: 4UHRUIN-text-embedding-3-small
- Generation: 4UHRUIN-gpt-5-mini

## Run

Build knowledge base:

```bash
py -m scripts.ingest_dataset
```

Start API:

```bash
py -m uvicorn src.main:app --reload
```

Swagger:

```text
http://127.0.0.1:8000/docs
```

## Evaluation

```bash
py -m scripts.test_functionality_requirements
```