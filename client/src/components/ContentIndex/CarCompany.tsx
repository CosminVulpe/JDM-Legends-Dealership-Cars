import React from "react";
import "./CarCompanyStyling.css";
import {Heading} from "@chakra-ui/react";
import {CarsData} from "./CarsData";
import styled from 'styled-components/macro';


const CarCompany: React.FC = () => {
    return (
        <>
            <Heading as={"h1"}
                     style={{marginLeft: "23.5rem"
                         , padding: "9rem 0 2.5rem 0"}}
            >Car Companies</Heading>
            <section className="container">
                {CarsData.map((car, index) =>
                    <div className="card" key={index}>
                        <div className="card-image">
                            <img className={"car-img"}
                                 src={"https://external-content.duckduckgo.com/iu/?u=http%3A%2F%2F4.bp.blogspot.com%2F-9sHkyGUf6fU%2FUCmrmIZQ45I%2FAAAAAAAABJk%2FWXGieaHpyhg%2Fs1600%2FToyota-Logo%2B(1).jpg&f=1&nofb=1"}
                                 alt={"dsd"}/>
                            <Heading as="h2">{car.brand}</Heading>
                            <p style={{paddingTop:"10px"}}>Total Cars: 1,000</p>
                        </div>
                    </div>
                )}
            </section>
        </>
    );
}
export default CarCompany;
