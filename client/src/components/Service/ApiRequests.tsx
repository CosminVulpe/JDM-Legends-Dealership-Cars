import axios from "axios";


export const ApiGetCar = async (endPoint: string) => {
    return await axios.get(process.env.REACT_APP_BACKEND_API_CAR + endPoint);
}

export const getCancelToken = (): any => {
    return axios.CancelToken.source();
}
