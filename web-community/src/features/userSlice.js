import {createSlice} from "@reduxjs/toolkit";

export const userSlice = createSlice({
    name: 'userReducer',
    initialState: {
        userData: [],
        isLoading: false,
        isOnline: false,
        error: false
    },
    reducers: {
        startLoading: (state) => {
            state.isLoading = true;
        },
        hasError: (state, action) => {
            state.error = action.payload;
            state.isLoading = false;
        },
        join: (state, action) => {
            state.isLoading = false;
        },
        login: (state, action) => {
            state.userData = action.payload;
            state.isOnline = true;
        },
        logout: (state) => {
            state.isOnline = false;
        },
        setOnline: (state) => {
            state.isOnline = true;
        }
    }
})

export const {startLoading, hasError, join, login, logout, setOnline} = userSlice.actions

export default userSlice.reducer
