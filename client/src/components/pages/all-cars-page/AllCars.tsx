import React, {useEffect, useState} from "react";
import NavBar from "../../NavBar/NavBar";
import GifAnimation from "../../Gif/GifAnimation";
import styled from "styled-components/macro";
import {ApiGetCar, getCancelToken} from "../../Service/api-requests/ApiRequests";
import axios from "axios";
import Card from "../../Card/Card";
import {allCarsData} from "./AllCarsData";
import Footer from "../../Footer/Footer";
import SearchBar from "../../SearchBar/SearchBar";
import {Car} from "../../Service/interfaces/Interfaces";

const Wrapper = styled.div`
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(5rem, 20rem));
  gap: 2rem;
  justify-content: center;

  margin-top: 5rem;
  padding: 1rem;
  margin-bottom: 1rem;
`;

const AllCars: React.FC = () => {
    window.scrollTo(0, 0);

    const [cars, setCars] = useState<Car[]>([]);

    useEffect(() => {
        ApiGetCar("")
            .then((res: any) => setCars(res.data))
            .catch(err => {
                if (axios.isCancel(err)) {
                    console.log("cancelled!");
                }
            });
        return () => {
            getCancelToken().cancel();
        }
    }, []);

    return (
        <>
            <NavBar/>
            <GifAnimation indexPageVideo={false}/>
            <SearchBar cars={cars}/>
            <div id="content-index">
                <Wrapper>
                    {cars.map((car, index) =>
                        <div key={car.id}>
                            <Card
                                imageUrl={allCarsData[index].srcImage}
                                titleCard={car.carName}
                                descriptionCard={"A beautiful car is one of the few mechanical creations" +
                                    " that engenders a deep emotional response in people. There’s something" +
                                    " about a car that is alive."}
                                carId={car.id}
                            />
                        </div>
                    )}
                </Wrapper>
            </div>
            <Footer/>
        </>
    )
}

export default AllCars;
