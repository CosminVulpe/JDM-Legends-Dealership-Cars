export interface Review {
    id: number,
    title: String,
    description: String,
    starRating?: number
}

export interface Car {
    carColor: String,
    carCompany: String,
    carFuelType: String,
    carName: String,
    carTransmissionType: String,
    damaged: boolean,
    hp: number,
    id: number,
    km: number,
    initialPrice: number,
    productionYear: number,
    quantityInStock: number
}

export interface HistoryBid {
    id?: number,
    bidValue: number,
    timeOfTheBid: Date
}

export interface TemporaryUser {
    id?: number,
    userName: string;
    firstName: string,
    lastName: string,
    emailAddress: string
}
