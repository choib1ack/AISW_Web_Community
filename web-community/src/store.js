import {configureStore} from '@reduxjs/toolkit'
import writeReducer from "./features/writeSlice";
import userReducer from "./features/userSlice";
import bannerReducer from "./features/bannerSlice";
import menuReducer from "./features/menuSlice";

export default configureStore({
    reducer: {
        write: writeReducer,
        user: userReducer,
        banner: bannerReducer,
        menu : menuReducer
    }
})
