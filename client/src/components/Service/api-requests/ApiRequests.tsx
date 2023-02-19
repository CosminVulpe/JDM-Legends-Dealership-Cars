import axios from "axios";
import {TemporaryUser, HistoryBid, ReviewInterface} from "../interfaces/Interfaces";

export const ApiGetCar = async (endPoint: string) => {
    return await axios.get(process.env.REACT_APP_BACKEND_API_CAR + endPoint);
}

export const getCancelToken = (): any => {
    return axios.CancelToken.source();
}

export const ApiGetCarPictures = async (carName: String | undefined) => {
    return (carName !== undefined) ?
        await axios.get(`https://api.unsplash.com/search/photos?query=${carName}&client_id=${process.env.REACT_APP_UNSPLASH_KEY}&page=1&orientation=landscape`)
        : null;
}

export const ApiPostReview = async (data: ReviewInterface, endPoint: string) => {
    return await axios.post(process.env.REACT_APP_BACKEND_API_REVIEW + endPoint
        , JSON.stringify(data)
        , {
            headers: {
                "Content-Type": "application/json",
                "accept": "application/json"
            }
        }
    );
}

export const ApiGetReview = async (endPoint: string) => {
    return await axios.get(process.env.REACT_APP_BACKEND_API_REVIEW + endPoint);
}

export const ApiPostTemporaryUser = async (endpoint: string, data: TemporaryUser) => {
    return await axios.post(process.env.REACT_APP_BACKEND_API_TEMPORARY_USER + endpoint
        , JSON.stringify(data)
        , {
            headers: {
                "Content-type": "application/json",
                "accept": "application/json"
            }
        })
}

export const ApiPostHistoryBid = async (endPoint: string, data: HistoryBid) => {
    return await axios.post(process.env.REACT_APP_BACKEND_API_HISTORY_BID + endPoint
        , JSON.stringify(data)
        , {
            headers: {
                "Content-Type": "application/json",
                "accept": "application/json"
            }
        });
}
