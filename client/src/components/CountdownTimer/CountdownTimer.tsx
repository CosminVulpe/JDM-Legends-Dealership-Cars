import React, {useEffect, useState} from "react";
import {useCountDown} from "./useCountdown";
import ExpireNotice from "./ExpireNotice";
import ShowCounter from "./ShowCounter";
import {ApiGetHistoryBid} from "../Service/api-requests/ApiRequests";
import {useParams} from "react-router-dom";

const CountdownTimer: React.FC<{ targetDate: number }> = ({targetDate}) => {
    const [days, hours, minutes, seconds] = useCountDown(targetDate);
    const {id} = useParams();

    const [carWinner, setCarWinner] = useState({
        winner: "",
        finalBidPrice: 0
    });

    useEffect(() => {
        ApiGetHistoryBid("car-winner/" + id)
            .then(res => setCarWinner(res.data))
            .catch(err => console.log(err));
    }, [id]);

    const isTimerFinished: boolean = days === 0 && hours === 0 && minutes === 0 && seconds === 0;
    const doesWinnerExist: boolean = Object.keys(carWinner).length === 0;
    if (isTimerFinished && !doesWinnerExist) {
        return <ExpireNotice/>;
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