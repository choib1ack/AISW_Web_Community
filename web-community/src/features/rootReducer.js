import { combineReducers } from 'redux'
import userReducer from './userSlice'
import counterReducer from './counterSlice'

const rootReducer = combineReducers({
    user: userReducer,
    counter: counterReducer
})

export default rootReducer;
