import React from "react";
import {useCountDown} from "./useCountdown";
import ExpireNotice from "./ExpireNotice";
import ShowCounter from "./ShowCounter";

const CountdownTimer: React.FC<{ targetDate: number }> = ({targetDate}) => {
    const [days, hours, minutes, seconds] = useCountDown(targetDate);

    const isTimerFinished: boolean = days === 0 && hours === 0 && minutes === 0 && seconds === 0;
    if (isTimerFinished) {
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