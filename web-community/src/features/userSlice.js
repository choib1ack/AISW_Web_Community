import {createAsyncThunk, createSlice} from "@reduxjs/toolkit";
import axios from "axios";

export const checkUser = createAsyncThunk("CHECK_USER", async (userName, email) => {
    return axios.post(`/user/verification`, {
        // headers: {
        //     "Content-Type": `application/json`
        // },
        data: {
            username: userName,
            email: email
        }
    })
        .then((res) => res.data.data)
        .catch(error => error);
});

export const userSlice = createSlice({
    name: 'user',
    initialState: {
        userData: [],
        isLoading: false,
        isOnline: false,
        error: false,
        check: []
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
    },
    extraReducers: {
        [checkUser.fulfilled]: (state, action) => {
            state.check = action.payload;
        },
        [checkUser.rejected]: (state, action) => {
            state.error = action.payload;
        }
    }
})

export const {startLoading, hasError, join, login, logout, setOnline} = userSlice.actions

export default userSlice.reducer
