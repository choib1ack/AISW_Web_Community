import {createSlice} from '@reduxjs/toolkit'

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
        setStateWrite: (state) => {
            state.state = 'write'
        },
        setStateEdit: (state) => {
            state.state = 'edit'
        }
    }
})

// Action creators are generated for each case reducer function
export const {changeWriteField, resetWriteField} = writeSlice.actions

export default writeSlice.reducer
