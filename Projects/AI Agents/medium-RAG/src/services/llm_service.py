from langchain_core.messages import SystemMessage, HumanMessage
from langchain_openai import ChatOpenAI

from src.config.settings import (
    LLMOD_API_KEY,
    LLMOD_BASE_URL,
    CHAT_MODEL,
)


class LLMService:

    def __init__(self):
        self.llm = ChatOpenAI(
            api_key=LLMOD_API_KEY,
            base_url=LLMOD_BASE_URL,
            model=CHAT_MODEL
        )

    def invoke(self, prompt: str):
        response = self.llm.invoke(prompt)
        return response.content

    def invoke_with_messages(self, system_prompt: str, user_prompt: str):
        response = self.llm.invoke([
            SystemMessage(content=system_prompt),
            HumanMessage(content=user_prompt),
        ])

        return response.content