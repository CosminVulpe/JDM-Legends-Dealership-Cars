import React, {SetStateAction} from "react";
import {useEffect, useState} from 'react';

export const useCountDown = (targetDate: number) => {
    let countDownDate = new Date(targetDate).getTime();
    const [countDown, setCountDown] = useState<number>(countDownDate - new Date().getTime());

    useEffect(() => {
        const interval: NodeJS.Timer = setInterval(() => {
            const timeFinish: boolean = getReturnValues(countDown)[3] === 0;
            setCountDown((timeFinish) ? 0 : countDownDate - new Date().getTime())
        }, 1000);
        return () => clearInterval(interval);
    }, [countDown]);

    return getReturnValues(countDown);
}

const getReturnValues = (countDown: number) => {
    const days = Math.floor(countDown / (1000 * 60 * 60 * 24));
    const hours = Math.floor(
        (countDown % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60)
    );
    const minutes = Math.floor((countDown % (1000 * 60 * 60)) / (1000 * 60));
    const seconds = Math.floor((countDown % (1000 * 60)) / 1000);

    return [days, hours, minutes, seconds];
};