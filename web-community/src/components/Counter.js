import React, {useEffect, useState} from 'react';
import './Counter.css';
import {useSelector, useDispatch} from "react-redux";
import {decrement, increment, incrementByAmount} from "../features/counterSlice";

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
                dispatch(decrement())
            }}>+
            </button>
        </div>
    );
}

export default Counter;
