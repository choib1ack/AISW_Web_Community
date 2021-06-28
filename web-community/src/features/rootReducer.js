import { combineReducers } from 'redux'
import userReducer from './userSlice'
import writeReducer from './writeSlice'
import bannerReducer from './bannerSlice'

const rootReducer = combineReducers({
    user: userReducer,
    write: writeReducer,
    banner: bannerReducer
})

export default rootReducer;
