import React from "react";
import {useCountDown} from "./useCountdown";
import ExpireNotice from "./ExpireNotice";
import ShowCounter from "./ShowCounter";

const CountdownTimer: React.FC<{ targetDate: number }> = ({targetDate}) => {
    const [days, hours, minutes, seconds] = useCountDown(targetDate);

    if (seconds === 0) {
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