import {createSlice} from '@reduxjs/toolkit'

export const menuSlice = createSlice({
    name: 'menu',
    initialState: {
        active: 0,
    },
    reducers: {
        setActiveTab: (state, action) => {
            state.active = action.payload;
        }
    }
})

// Action creators are generated for each case reducer function
export const {setActiveTab} = menuSlice.actions

export default menuSlice.reducer
