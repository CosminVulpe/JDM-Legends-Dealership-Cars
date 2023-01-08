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
    price: number,
    productionYear: number,
    quantityInStock: number
}

export interface HistoryBid {
    id?: number,
    bidValue: number,
    timeOfTheBid: Date
}
