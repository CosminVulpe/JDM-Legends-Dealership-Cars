import axios from "axios";
import {HistoryBid, HistoryBidTemporaryUser, ReviewInterface, TemporaryUser} from "../interfaces/Interfaces";

export const ApiGetCar = async (endPoint?: string) =>
    await axios.get(process.env.REACT_APP_BACKEND_API_CAR + (endPoint ? endPoint : ""));


export const getCancelToken = (): any => {
    return axios.CancelToken.source();
}

export const ApiGetCarPictures = async (carName: String | undefined) =>
    await axios.get(`https://api.unsplash.com/search/photos?query=${carName as String}&client_id=${process.env.REACT_APP_UNSPLASH_KEY}&page=1&orientation=landscape`);

export const ApiPostReview = async (data: ReviewInterface, endPoint?: string) => {
    return await axios.post(process.env.REACT_APP_BACKEND_API_REVIEW + (endPoint ? endPoint : "")
        , JSON.stringify(data)
        , {
            headers: {
                "Content-Type": "application/json",
                "accept": "application/json"
            }
        }
    );
}

export const ApiGetReview = async (endPoint?: string) =>
    await axios.get(process.env.REACT_APP_BACKEND_API_REVIEW + (endPoint ? endPoint : ""));


export const ApiPostHistoryBid = async (endPoint: string, data: HistoryBidTemporaryUser) => {
    return await axios.post(process.env.REACT_APP_BACKEND_API_HISTORY_BID + endPoint
        , JSON.stringify({
            ...data,
            historyBid: {
                ...data.historyBid,
                bidValue: data.historyBid.bidValue.toString()
            }
        })
        , {
            headers: {
                "Content-Type": "application/json",
                "accept": "application/json"
            }
        });
}

export const ApiPostTemporaryUser = async (data: TemporaryUser | string, endpoint?: string,) => {
    return await axios.post(process.env.REACT_APP_USERS_BACKEND_API_USERS + (endpoint ? endpoint : "")
        , JSON.stringify(data)
        , {
            headers: {
                "Content-Type": "application/json",
                "accept": "application/json"
            }
        }
    );
}


export const ApiGetTemporaryUser = async (endPoint?: string) =>
    await axios.get(process.env.REACT_APP_USERS_BACKEND_API_USERS + (endPoint ? endPoint : ""));

