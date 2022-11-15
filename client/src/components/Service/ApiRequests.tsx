import axios from "axios";


export const ApiGetCar = async (endPoint: string) => {
    return await axios.get(process.env.REACT_APP_BACKEND_API_CAR + endPoint);
}

export const getCancelToken = (): any => {
    return axios.CancelToken.source();
}

export const ApiGetCarPictures = async (carName : String) => {
    return await axios.get(`https://api.unsplash.com/search/photos?query=${carName}&client_id=${process.env.REACT_APP_UNSPLASH_KEY}&page=1&orientation=landscape`);
}
