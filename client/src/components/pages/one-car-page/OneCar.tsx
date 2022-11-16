import React, {useEffect, useState} from "react";
import {ApiGetCar, getCancelToken} from "../../Service/ApiRequests";
import {useParams} from "react-router-dom";
import axios from "axios";
import {Car} from "../../IndexPageContent/CarCompany/CarCompany";
import Error from "../error-page/Error";

const OneCar: React.FC = () => {
    let {id} = useParams();
    const [oneCarDetails, setOneCarDetails] = useState<Car[]>([]);
    const [doesCarExist, setCarExist] = useState<boolean>(false);

    useEffect(() => {
        ApiGetCar((id !== undefined) ? id : "")
            .then((res: any) => {
                setCarExist(true);
                setOneCarDetails(res.data)
            })
            .catch(err => {
                if (axios.isCancel(err)) {
                    console.log("cancelled!");
                }
                if (err.response.status === 400 || err.response.status === 404) {
                    setCarExist(false);
                }
            });

        return () => {
            getCancelToken().cancel();
        }
    }, []);


    if(!doesCarExist){
        return (
            <Error/>
        )
    }

    return (
        <>
            <p>HELLO!</p>
        </>
    )
}

export default OneCar;
