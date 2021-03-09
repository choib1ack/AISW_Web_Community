import React, {useState} from 'react';
import {Editor} from 'react-draft-wysiwyg';
import 'react-draft-wysiwyg/dist/react-draft-wysiwyg.css';
import styled from 'styled-components';
import {EditorState} from 'draft-js';

const MyBlock = styled.div`
  .wrapper-class {
    width: 100%;
    margin: 0 auto 2rem;
  }

  .editor {
    height: 300px !important;
    border: 1px solid #f1f1f1 !important;
    padding: 5px !important;
    border-radius: 2px !important;
  }
`;

function uploadImageCallBack(file) {
    return new Promise(
        (resolve, reject) => {
            const xhr = new XMLHttpRequest();
            xhr.open('POST', 'https://api.imgur.com/3/image');
            xhr.setRequestHeader('Authorization', 'Client-ID XXXXX');
            const data = new FormData();
            data.append('image', file);
            xhr.send(data);
            xhr.addEventListener('load', () => {
                const response = JSON.parse(xhr.responseText);
                console.log(response.url);
                resolve({data: {link: response.url}})
                resolve(response);
            });
            xhr.addEventListener('error', () => {
                const error = JSON.parse(xhr.responseText);
                reject(error);
            });
        }
    );
}

const TestEditorForm = () => {
    // useState로 상태관리하기 초기값은 EditorState.createEmpty()
    // EditorState의 비어있는 ContentState 기본 구성으로 새 개체를 반환 => 이렇게 안하면 상태 값을 나중에 변경할 수 없음.
    const [editorState, setEditorState] = useState(EditorState.createEmpty());

    const onEditorStateChange = (editorState) => {
        // editorState에 값 설정
        setEditorState(editorState);
    };

    return (
        <MyBlock>
            <Editor
                // 에디터와 툴바 모두에 적용되는 클래스
                wrapperClassName="wrapper-class"
                // 에디터 주변에 적용된 클래스
                editorClassName="editor"
                // 툴바 주위에 적용된 클래스
                toolbarClassName="toolbar-class"
                // 툴바 설정
                toolbar={{
                    // inDropdown: 해당 항목과 관련된 항목을 드롭다운으로 나타낼것인지
                    inline:{inDropdown: true},
                    list: {inDropdown: true},
                    textAlign: {inDropdown: true},
                    link: {inDropdown: true},
                    history: {inDropdown: false},
                    image: { uploadCallback: uploadImageCallBack, alt: { present: true, mandatory: true } },
                }}
                placeholder="내용을 작성해주세요."
                // 한국어 설정
                localization={{
                    locale: 'ko',
                }}
                // 초기값 설정
                editorState={editorState}
                // 에디터의 값이 변경될 때마다 onEditorStateChange 호출
                onEditorStateChange={onEditorStateChange}
            />
        </MyBlock>
    );
};

export default TestEditorForm;
