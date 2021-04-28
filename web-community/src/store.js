import {configureStore} from '@reduxjs/toolkit'
import writeReducer from "./features/writeSlice";
import userReducer from "./features/userSlice"

export default configureStore({
    reducer: {
        write: writeReducer,
        user: userReducer,
    }
})
