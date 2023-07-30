import React, {useEffect, useState} from "react";
import {useCountDown} from "./useCountdown";
import ExpireNotice from "./ExpireNotice";
import ShowCounter from "./ShowCounter";
import {ApiGetTemporaryUser} from "../Service/api-requests/ApiRequests";
import {WinnerUser} from "../Service/interfaces/Interfaces";

const CountdownTimer: React.FC<{ targetDate: number, carId: number }> = ({targetDate, carId}) => {
    const [days, hours, minutes, seconds] = useCountDown(targetDate);

    const isTimerFinished: boolean = (days === 0) && (hours === 0) && (minutes === 0) && (seconds === 0);
    const [winner, setWinner] = useState<WinnerUser>({
        userName: "",
        bidValue: BigInt(0)
    });

    useEffect(() => {
        if (isTimerFinished) {
            ApiGetTemporaryUser("/winner/" + carId)
                .then( (res: any) => setWinner(res.data))
                .catch(err => console.log(err))
        }
    }, [isTimerFinished]);

    if (winner.userName !== "") {
        return <ExpireNotice userName={winner.userName} bidValue={winner.bidValue}/>;
    }

    return (
        <ShowCounter
            days={days}
            hours={hours}
            minutes={minutes}
            seconds={seconds}
        />
    );
}

export default CountdownTimer;