import React from "react";
import {useCountDown} from "./useCountdown";
import ExpireNotice from "./ExpireNotice";
import ShowCounter from "./ShowCounter";

const CountdownTimer: React.FC<{ targetDate: number }> = ({targetDate}) => {
    const [days, hours, minutes, seconds] = useCountDown(targetDate);

    if ((days + hours + minutes + seconds) <= 0) {
        return <ExpireNotice/>;
    } else {
        return (
            <ShowCounter
                days={days}
                hours={hours}
                minutes={minutes}
                seconds={seconds}
            />
        );
    }
}
export default CountdownTimer;
