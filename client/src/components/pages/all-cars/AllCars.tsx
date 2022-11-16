import React, {useEffect, useState} from "react";
import NavBar from "../../NavBar/NavBar";
import GifAnimation from "../../Gif/GifAnimation";
import styled from "styled-components/macro";
import {ApiGetCar, getCancelToken} from "../../Service/ApiRequests";
import axios from "axios";
import {Car} from "../../IndexPageContent/CarCompany/CarCompany";
import Card from "../../Card/Card";
import {allCarsData} from "./AllCarsData";
import card from "../../Card/Card";

const Wrapper = styled.div`
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(5rem, 20rem));
  gap: 2rem;
  justify-content: center;

  margin-top: 5rem;
  padding: 1rem;
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
            <div id="content-index">
                <Wrapper>
                    {cars.map((car, index) =>
                        <div key={car.id}>
                            <Card
                                imageUrl={allCarsData[index].srcImage}
                                titleCard={ car.carName}
                                descriptionCard={"Take your boring salads up a knotch. This recipe is perfect for lunch" +
                                    "and only contains 5 ingredients!"}
                            />
                        </div>
                    )}
                </Wrapper>
            </div>
        </>
    )
}

export default AllCars;
