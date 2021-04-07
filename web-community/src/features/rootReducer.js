import { combineReducers } from 'redux'
import userReducer from './userSlice'
import writeReducer from './writeSlice'

const rootReducer = combineReducers({
    user: userReducer,
    write: writeReducer,
})

export default rootReducer;
