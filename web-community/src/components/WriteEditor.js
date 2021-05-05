import React from 'react';
import { Editor } from 'react-draft-wysiwyg';
import 'react-draft-wysiwyg/dist/react-draft-wysiwyg.css';
import styled from 'styled-components';
import draftToHtml from "draftjs-to-html";
import {convertToRaw} from "draft-js";

const WriteEditorWrapper = styled.div`
  margin-top: 1rem;
  
  .wrapper-class {
    width: 100%;
    margin: 0 auto 2rem;
  }
  //.toolbar {
  //  padding: 6px 5px;
  //  box-shadow: rgba(0, 0, 0, 0.04) 0 0 5px 0;
  //}
  .editor {
    height: 300px !important;
    border: 1px solid #f1f1f1 !important;
    padding: 5px !important;
    border-radius: 2px !important; 
  }
`;

// 변환시켜준 editorState 값을 넣기 위한 div 태그 css
const IntroduceContent = styled.div`
  position: relative;
  border: 0.0625rem solid #d7e2eb;
  border-radius: 0.75rem;
  overflow: hidden;
  padding: 1.5rem;
  width: 50%;
  margin: 0 auto 4rem;
`;

const WriteEditor = ({ editorState, onChange }) => {
    // editorState의 현재 contentState 값을 원시 JS 구조로 변환시킨뒤, HTML 태그로 변환시켜준다.
    const editorToHtml = draftToHtml(convertToRaw(editorState.getCurrentContent()));

    return (
        <>
            <WriteEditorWrapper>
                <Editor
                    editorState={editorState}
                    onEditorStateChange={onChange}
                    ariaLabel="contents"
                    placeholder="내용을 작성해주세요."
                    wrapperClassName="wrapper-class"
                    toolbarClassName="toolbar"
                    editorClassName="editor"
                    localization={{
                        locale: 'ko',
                    }}
                    toolbar={{
                        inline: {inDropdown: true},
                        list: { inDropdown: true },
                        textAlign: { inDropdown: true },
                        link: { inDropdown: true },
                        history: { inDropdown: true },
                    }}
                />
            </WriteEditorWrapper>
            {/*<IntroduceContent dangerouslySetInnerHTML={{__html: editorToHtml}}/>*/}
        </>
    );
}

export default WriteEditor;
