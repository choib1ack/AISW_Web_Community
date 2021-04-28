import React from 'react';
import { Editor } from 'react-draft-wysiwyg';
import 'react-draft-wysiwyg/dist/react-draft-wysiwyg.css';
import styled from 'styled-components';

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

const WriteEditor = ({ editorState, onChange }) => (
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
    </>
);

export default WriteEditor;
