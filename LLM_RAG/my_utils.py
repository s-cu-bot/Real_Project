import os
import re
import PyPDF2
from openai import OpenAI
from weaviate.collections.classes.grpc import MetadataQuery
from weaviate.classes.query import Filter
from weaviate.classes.init import Auth
import weaviate

# 초기 설정
WEAVIATE_URL="---"
WEAVIATE_API_KEY="---"

def pdf_to_text(path):
    pdfReader = PyPDF2.PdfReader(path)
    print(" No. Of Pages :", len(pdfReader.pages))
    total = ''
    for i in range(len(pdfReader.pages)):
        data = pdfReader.pages[i].extract_text()
        total += data
    return total

# ✅ 벡터 유사 검색 함수
def search_similar_docs(query_text: str, top_k: int = 1):
    # 클라이언트 연결
    client = weaviate.connect_to_wcs(
        cluster_url=WEAVIATE_URL,
        auth_credentials=Auth.api_key(WEAVIATE_API_KEY),
    )
    client.connect()
    collection = client.collections.get("InternalRegulations")  # 컬렉션 불러오기

    results = collection.query.bm25(  # HTTP 기반 쿼리
        query=query_text,
        limit=top_k
    )

    # 결과는 list of Document 객체
    return results.objects

# ✅ 텍스트를 작은 단위로 나누는 함수
def split_chunks(text, max_tokens=500):
    # 간단한 문장 단위로 나누기
    sentences = re.split(r'(?<=[.!?])\s+', text)
    chunks = []
    current_chunk = ""

    for sentence in sentences:
        if len(current_chunk) + len(sentence) < max_tokens:
            current_chunk += sentence + " "
        else:
            chunks.append(current_chunk.strip())
            current_chunk = sentence + " "

    if current_chunk:
        chunks.append(current_chunk.strip())

    return chunks
