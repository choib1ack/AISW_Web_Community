import {createSlice} from '@reduxjs/toolkit';

export const writeSlice = createSlice({
    name: 'write',
    initialState: {
        state: null,
        value: null
    },
    reducers: {
        changeWriteField: (state, action) => {
            state.value = action.payload
        },
        resetWriteField: (state) => {
            state.value = null
        },
        setState: (state, action) => {
            state.state = action.payload
        }
    }
})

// Action creators are generated for each case reducer function
export const {changeWriteField, resetWriteField, setState} = writeSlice.actions

export default writeSlice.reducer
