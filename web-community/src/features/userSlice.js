import {createSlice} from "@reduxjs/toolkit";

export const userSlice = createSlice({
    name: 'userReducer',
    initialState: {
        userData: [],
        isLoading: false,
        error: false,
        isOnline: false
    },
    reducers: {
        startLoading: (state) => {
            state.isLoading = true;
        },
        hasError: (state, action) => {
            state.error = action.payload;
            state.isLoading = false;
        },
        joinSuccess: (state, action) => {
            state.userData = action.payload;
            state.isLoading = false;
        },
        loginSuccess: (state, action) => {
            state.isOnline = true;
        }
    }
})

export const {startLoading, hasError, joinSuccess, loginSuccess} = userSlice.actions

export default userSlice.reducer
