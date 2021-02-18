import React, {useEffect, useState} from 'react';
import PropTypes from 'prop-types';
import './Counter.css';
import * as actions from "../actions";
import {connect} from "react-redux";

const Counter = ({number, onIncrement, onDecrement, color, onSetColor}) => {
    return (
        // <div>
        //     <div
        //         className="Counter"
        //         onClick={() => {
        //             onIncrement();
        //             console.log(number)
        //         }}
        //         onContextMenu={
        //             (e) => {
        //                 e.preventDefault();
        //                 onDecrement();
        //             }
        //         }
        //         onDoubleClick={onSetColor}
        //         style={{backgroundColor: color}}>
        //         {number}
        //     </div>
        // </div>

        <div>
            <button onClick={() => {
                console.log(number);
                onDecrement()
            }}>-
            </button>
            <span>Number: {number}</span>
            <button onClick={() => {
                console.log(number);
                onIncrement()
            }}>+</button>
        </div>
    );
}

// Counter.propTypes = {
//     number: PropTypes.number,
//     color: PropTypes.string,
//     onIncrement: PropTypes.func,
//     onDecrement: PropTypes.func,
//     onSetColor: PropTypes.func
// };
//
// Counter.defaultProps = {
//     number: 0,
//     color: 'black',
//     onIncrement: () => console.warn('onIncrement not defined'),
//     onDecrement: () => console.warn('onDecrement not defined'),
//     onSetColor: () => console.warn('onSetColor not defined')
// };

// store 안의 state 값을 props 로 연결해줍니다.
const mapStateToProps = (state) => ({
    color: state.color,
    number: state.number
});

/*
    액션 생성자를 사용하여 액션을 생성하고,
    해당 액션을 dispatch 하는 함수를 만들은 후, 이를 props 로 연결해줍니다.
*/

const mapDispatchToProps = (dispatch) => ({
    onIncrement: () => dispatch(actions.increment()),
    onDecrement: () => dispatch(actions.decrement()),
    onSetColor: () => {
        const color = 'black'; // 임시
        dispatch(actions.setColor(color));
    }
});

// Counter 컴포넌트의 Container 컴포넌트
// Counter 컴포넌트를 어플리케이션의 데이터 레이어와 묶는 역할을 합니다.

export default connect(mapStateToProps, mapDispatchToProps)(Counter);
