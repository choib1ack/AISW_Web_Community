import {createAsyncThunk, createSlice} from "@reduxjs/toolkit";
import axios from "axios";

// export const checkExist = createAsyncThunk("CHECK_EXIST", async ({username, email}) => {
//     return axios.post(`/user/verification`, {
//         data: {
//             'username': username,
//             'email': email
//         }
//     }).then((res) => {
//         return {...res.data.data, 'username': username};
//     }).catch(error => error);
// });
//
// export const login = createAsyncThunk("LOGIN", async (username) => {
//     return axios.post(`/login`, {
//         'username': username,
//         'password': 'AISW',
//     }).then((res) => {
//         window.localStorage.setItem("ACCESS_TOKEN", res.headers.authorization);
//         window.localStorage.setItem("REFRESH_TOKEN", res.headers.refresh_token);
//     }).catch(error => error);
// });

export const userSlice = createSlice({
    name: 'user',
    initialState: {
        userData: null,
        isOnline: false,
        error: null,
        decoded: null
    },
    reducers: {
        setUserData: (state, action) => {
            state.userData = action.payload;
        },
        setDecoded: (state, action) => {
            state.decoded = action.payload;
        }
    },
    extraReducers: {
        // [checkExist.fulfilled]: (state, action) => {
        //     state.exist = action.payload;
        //     state.error = '';
        // },
        // [checkExist.rejected]: (state, action) => {
        //     state.error = action.payload;
        // },
        // [login.fulfilled]: (state) => {
        //     state.isOnline = true;
        //     state.error = null;
        // },
        // [login.rejected]: (state, action) => {
        //     state.error = action.payload;
        // },
    }
})

export const {setUserData, setDecoded} = userSlice.actions

export default userSlice.reducer
