
import Loader from 'react-loader-spinner'
import React from "react";

export default function Loading(){
    return(
        <div style={{width:'100%', height:'100%'}}>
            <Loader
                type="Oval"
                color="#FFDD87"
                // color="#6CBACB"
                height={30}
                width={30}
                style={{position:'fixed', left:'50%', top:'50%', transform:'translate(-50%, -50%)'}}
            />
        </div>
    );
}