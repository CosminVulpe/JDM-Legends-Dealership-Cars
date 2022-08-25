import React, {useEffect, useState} from "react";
import "./CarCompanyStyling.css";
import {Heading} from "@chakra-ui/react";
import {ApiGetCar, getCancelToken} from "../../Service/ApiRequests";
import axios from "axios";
import {carBrandData} from "./CarBrandData";


const CarCompany: React.FC = () => {
    const [carBrands, setCarBrand] = useState([]);

    useEffect(() => {
        ApiGetCar("/brand")
            .then((res: any) => setCarBrand(res.data))
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
            <Heading as={"h1"}
                     style={{
                         marginLeft: "23.5rem"
                         , padding: "9rem 0 2.5rem 0"
                     }}
            >Car Companies</Heading>
            <section className="container">
                {carBrands.map((car, index) =>
                    <div className="card" key={index}>
                        <div className="card-image">
                            <img className={"car-img"}
                                 src={carBrandData[index].srcImage[car]}
                                 alt={"car-brand"}/>
                            <Heading as="h2"
                                     style={{
                                         fontSize: "20px",
                                         display: "flex",
                                         justifyContent: "center"
                                     }}
                            >{car}</Heading>
                            <p style={{paddingTop: "10px"}}>Total Cars: 1,000</p>
                        </div>
                    </div>
                )}
            </section>
        </>
    );
}
export default CarCompany;
