import {configureStore} from '@reduxjs/toolkit'
import writeReducer from "./features/writeSlice";
import userReducer from "./features/userSlice";
import bannerReducer from "./features/bannerSlice";

export default configureStore({
    reducer: {
        write: writeReducer,
        user: userReducer,
        banner: bannerReducer
    }
})
