import React from "react";
import DateTimeDisplay from "./DateTimeDisplay";

interface Props {
    days: number,
    hours: number,
    minutes: number,
    seconds: number
}

const ShowCounter: React.FC<Props> = ({days, hours
                                          , minutes, seconds}) => {

    return (
        <div className="show-counter">
            <div className="countdown-link">
                <DateTimeDisplay value={days} type={'Days'} isDanger={days <= 3}/>
                <DateTimeDisplay value={hours} type={'Hours'} isDanger={false}/>
                <DateTimeDisplay value={minutes} type={'Mins'} isDanger={false}/>
                <DateTimeDisplay value={seconds} type={'Seconds'} isDanger={false}/>
            </div>
        </div>
    )
}
export default ShowCounter;