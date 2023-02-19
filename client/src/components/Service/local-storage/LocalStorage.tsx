export const saveInfoUserLocal = (key: string, value: string): void => {
    localStorage.setItem(key, value);
}

export const getInfoLocalStorage = (): string | null => {
    return localStorage.getItem("userName");
}

export const areUserInfoSavedLocally = (): boolean => {
    return getInfoLocalStorage() === null;
}