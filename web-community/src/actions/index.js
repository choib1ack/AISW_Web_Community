import * as types from './ActionTypes';

export const increment = () => ({
    type: types.INCREMENT
});

export const decrement = () => ({
    type: types.DECREMENT
});

export const setColor = (color) => ({
    type: types.SET_COLOR,
    color
});

// let nextTodoId = 0
// export const addTodo = (content)=>({
//     type: types.ADD_TODO,
//     payload:{
//         id: ++nextTodoId,
//         content
//     }
// })

