import { combineReducers } from 'redux'
import userReducer from './userSlice'
import writeReducer from './writeSlice'
import bannerReducer from './bannerSlice'
import menuReducer from './menuSlice'

const rootReducer = combineReducers({
    user: userReducer,
    write: writeReducer,
    banner: bannerReducer,
    menu: menuReducer
})

export default rootReducer;
