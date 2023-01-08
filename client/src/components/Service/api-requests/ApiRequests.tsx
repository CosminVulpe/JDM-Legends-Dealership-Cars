import axios from "axios";
import {HistoryBid} from "../interfaces/Interfaces";

export const ApiGetCar = async (endPoint: string) => {
    return await axios.get(process.env.REACT_APP_BACKEND_API_CAR + endPoint);
}

export const ApiPostCar = async (endPoint: string, data: HistoryBid) => {
    return await axios.post(process.env.REACT_APP_BACKEND_API_CAR + endPoint
        , JSON.stringify(data)
        , {
            headers: {
                "Content-Type": "application/json",
                "accept": "application/json"
            }
        });
}

export const getCancelToken = (): any => {
    return axios.CancelToken.source();
}

export const ApiGetCarPictures = async (carName: String | undefined) => {
    return (carName !== undefined) ?
        await axios.get(`https://api.unsplash.com/search/photos?query=${carName}&client_id=${process.env.REACT_APP_UNSPLASH_KEY}&page=1&orientation=landscape`)
        : null;
}

export const ApiPostReview = async (data: { description: string; starRating: number; title: string }
    , endPoint: string) => {
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
