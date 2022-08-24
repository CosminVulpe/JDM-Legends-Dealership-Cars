import React, {useEffect, useState} from "react";
import "./CarCompanyStyling.css";
import {Heading} from "@chakra-ui/react";
import {ApiGetCar, getCancelToken} from "../../Service/ApiRequests";
import axios from "axios";


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
                                 src={"https://external-content.duckduckgo.com/iu/?u=http%3A%2F%2F4.bp.blogspot.com%2F-9sHkyGUf6fU%2FUCmrmIZQ45I%2FAAAAAAAABJk%2FWXGieaHpyhg%2Fs1600%2FToyota-Logo%2B(1).jpg&f=1&nofb=1"}
                                 alt={"dsd"}/>
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
