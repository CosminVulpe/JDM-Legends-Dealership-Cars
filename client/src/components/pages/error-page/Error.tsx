import React from "react";
import "./ErrorStyle.css";

const Error: React.FC = () => {
    return (
        <>
            <div className="FourOhFour">
                <div className="bg"
                     style={{backgroundImage: "url(https://media2.giphy.com/media/Vi4MRwWi9sYpi/giphy.gif?cid=ecf05e47rcwkhovkl68c62pdu4mhb6jil6tdgoypvzbjzdku&rid=giphy.gif&ct=g)"}}>
                </div>
                <div className="code">404</div>
            </div>
        </>
    )
}
export default Error;
