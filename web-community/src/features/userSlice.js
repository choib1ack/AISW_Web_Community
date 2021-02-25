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
            state.userData = action.payload;
            state.isLoading = false;
        },
        login: (state, action) => {
            state.isOnline = true;
        },
        logout: (state, action) => {
            state.isOnline = false;
        }
    }
})

export const {startLoading, hasError, join, login, logout} = userSlice.actions

export default userSlice.reducer
