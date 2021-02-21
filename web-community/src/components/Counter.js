import React, {useEffect, useState} from 'react';
import './Counter.css';
import {connect, useSelector, useDispatch} from "react-redux";
import {decrement, increment} from "../features/counter/counterSlice";

const Counter = () => {
    const count = useSelector(state => state.counter.value)
    const dispatch = useDispatch()

    return (

        <div>
            <button onClick={() => {
                dispatch(decrement())
            }}>-
            </button>
            <span>Number: {count}</span>
            <button onClick={() => {
                dispatch(increment())
            }}>+
            </button>
        </div>
    );
}

export default Counter;
