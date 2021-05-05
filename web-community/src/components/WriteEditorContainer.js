import React, { useCallback, useEffect, useState } from 'react';
import {useDispatch, useSelector} from "react-redux";
import htmlToDraft from 'html-to-draftjs';
import draftToHtml from 'draftjs-to-html';
import { ContentState, convertToRaw, EditorState } from 'draft-js';
import WriteEditor from "./WriteEditor";
import {changeWriteField, resetWriteField} from "../features/writeSlice";

const WriteEditorContainer = () => {
    const [editorState, setEditorState] = useState(EditorState.createEmpty());

    const dispatch = useDispatch();

    // 미리 있는 데이터 불러오기
    const { contents } = useSelector(state => state.write);

    const onChangeContent = useCallback((state) => {
        const value = draftToHtml(convertToRaw(state.getCurrentContent()));

        setEditorState(state);

        dispatch(changeWriteField(value));  // redux에 글 내용 업데이트

    }, [dispatch]);

    useEffect(() => {
        dispatch(resetWriteField());    // 내용 리셋

        if (contents) {
            const { contentBlocks, entityMap } = htmlToDraft(contents);

            const contentState = ContentState.createFromBlockArray(contentBlocks, entityMap);
            const editorValueState = EditorState.createWithContent(contentState);
            setEditorState(editorValueState);
        }
    }, []);

    return (
        <WriteEditor
            onChange={onChangeContent}
            editorState={editorState}
        />
    );
};

export default WriteEditorContainer;
