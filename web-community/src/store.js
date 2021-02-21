import {configureStore} from '@reduxjs/toolkit'
import counterReducer from "./features/counterSlice";
import userReducer from "./features/userSlice"

export default configureStore({
    reducer: {
        counter: counterReducer,
        user: userReducer,
    }
})
