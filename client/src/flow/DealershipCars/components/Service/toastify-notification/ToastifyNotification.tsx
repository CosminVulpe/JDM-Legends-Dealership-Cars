import {toast} from "react-toastify";

export const successfulNotification = (successfulMessage: string) => {
    return toast.success(successfulMessage, {
        position: "top-center",
        autoClose: 2500,
        hideProgressBar: false,
        closeOnClick: true,
        pauseOnHover: true,
        draggable: true,
        progress: undefined,
    });
}

export const warningNotification = (warningMessage: string) => {
    return toast.warn(warningMessage, {
        position: "bottom-center",
        autoClose: 5000,
        hideProgressBar: false,
        closeOnClick: true,
        pauseOnHover: true,
        draggable: true,
        progress: undefined,
        theme: "colored",
        style: { background: '#BE3D25' }
    });
}
