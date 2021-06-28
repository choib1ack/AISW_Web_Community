import {createSlice} from '@reduxjs/toolkit'

export const bannerSlice = createSlice({
    name: 'banner',
    initialState: {
        src: null,
    },
    reducers: {
        setBanner: (state, action) => {
            state.src = action.payload;
        }
    }
})

// Action creators are generated for each case reducer function
export const {setBanner} = bannerSlice.actions

export default bannerSlice.reducer
