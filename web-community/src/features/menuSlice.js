import {createSlice} from '@reduxjs/toolkit'

export const menuSlice = createSlice({
    name: 'menu',
    initialState: {
        active: 0,
        unread_alert: 0
    },
    reducers: {
        setActiveTab: (state, action) => {
            state.active = action.payload;
        },
        setUnreadAlert: (state, action) => {
            state.unread_alert = action.payload;
        }
    }
})

// Action creators are generated for each case reducer function
export const {setActiveTab, setUnreadAlert} = menuSlice.actions

export default menuSlice.reducer
