import React from "react";
import {WinnerUser} from "../Service/interfaces/Interfaces";

const ExpireNotice: React.FC<WinnerUser> = ({userName, bidValue}) => {

    return (
        <div className="expired-notice">
            <span>Sold!!!</span>
            <p>The winner is: { userName } with bid value {"$" + bidValue.toLocaleString() }</p>
        </div>
    )
}
export default ExpireNotice;