import React, {useState} from 'react';
import {Editor} from 'react-draft-wysiwyg';
import 'react-draft-wysiwyg/dist/react-draft-wysiwyg.css';
import styled from 'styled-components';

// convertToRaw: editorState 객체가 주어지면 원시 JS 구조로 변환.
import {EditorState, convertToRaw} from 'draft-js';
// convertToRaw로 변환시켜준 원시 JS 구조를 HTML로 변환.
import draftToHtml from 'draftjs-to-html';

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
                resolve(response);
            });
            xhr.addEventListener('error', () => {
                const error = JSON.parse(xhr.responseText);
                reject(error);
            });
        }
    );
}

const TextEditor = (props) => {
    const [editorState, setEditorState] = useState(EditorState.createEmpty());
    const onEditorStateChange = (editorState) => {
        // editorState에 값 설정
        setEditorState(editorState);

        // editorState의 현재 contentState 값을 원시 JS 구조로 변환시킨뒤, HTML 태그로 변환시켜준다.
        return props.onChange(draftToHtml(convertToRaw(editorState.getCurrentContent())))
    };

    return (
        <>
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
                        inline: {inDropdown: true},
                        list: {inDropdown: true},
                        textAlign: {inDropdown: true},
                        link: {inDropdown: true},
                        history: {inDropdown: false},
                        image: {uploadCallback: uploadImageCallBack, alt: {present: true, mandatory: true}},
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
        </>
    );
};

export default TextEditor;
