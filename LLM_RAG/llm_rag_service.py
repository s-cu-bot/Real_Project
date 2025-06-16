import streamlit as st
import os
from openai import OpenAI
from my_utils import pdf_to_text, search_similar_docs, split_chunks

# 초기 설정
OPENAI_API_KEY="---"

st.title("정책 변경 제안 시스템")

uploaded_file = st.file_uploader("PDF 문서 업로드", type=["pdf"])
if uploaded_file:
    temp_path = os.path.join(os.getcwd(), uploaded_file.name)
    st.info("[1단계] 파일 파싱 중...")
    pdf_text = pdf_to_text(temp_path)
    st.success("문서 파싱 완료")
    st.info("[2단계] 유사 정책 검색 중...")
    chunks = split_chunks(pdf_text)
    query_text = chunks[0]

    similar_docs = search_similar_docs(query_text)  #유사 검색
    if similar_docs:
        st.write("가장 유사한 내부 정책 문서:", similar_docs[0].properties["name"])
        st.text_area("정책 내용", similar_docs[0].properties["content"])
                
    # LLM을 이용한 변경 제안 생성
    if st.button("변경 제안 생성"):
        st.info("[3단계] 변경 제안 생성...")
        prompt = f"""너는 기업의 정책 담당자야. 신규 정책 재개정 문서가 나와 기존 내부 정책을 변경해야해. 내용은 다음과 같아. 기존 내부 정책을 위 내용을 비교하여 기존 정책을 어떻게 수정해야 하는지 제안해줘. 내용은 다음과 같아. 기존 내부 정책 ({similar_docs[0].properties["name"]}):\n
        {similar_docs[0].properties['content']}\n\n
        신규 수정 내용:\n{pdf_text}\n\n
        위 내용을 비교하여 기존 정책을 어떻게 수정해야 하는지 제안해줘."""
        llm_response = OpenAI(api_key=OPENAI_API_KEY).chat.completions.create(
            model="gpt-4o",
            messages=[{"role": "user", "content": prompt}]
        )
        st.success("변경 제안:")
        st.write(llm_response.choices[0].message.content)