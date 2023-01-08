import React, {useEffect, useState} from "react";
import {Heading} from "@chakra-ui/react";
import './OneCarContent.css';
import PopUp from "../../../PopUp/PopUp";
import {Car, HistoryBid} from "../../../Service/interfaces/Interfaces";
import {useAtom} from "jotai";
import {HISTORY_BID_DETAILS} from "../../../jotai-atom/JotaiAtom";

interface Props {
    cars?: Car;
}

const OneCarContent: React.FC<Props> = ({cars}) => {
    const getCar = cars as Car;

    const capitalizeLetterString = (value: String): string => {
        if (value.includes("_")) {
            return value.split("_")
                .map(item => item[0].toUpperCase() + item.substring(1))
                .join(" ");
        }
        return value[0].toUpperCase() + value.substring(1).toLowerCase();
    }

    // useEffect(() => {
    //     ApiGetCar("bid-list")
    //         .then(res => setHistoryBid(res.data))
    //         .catch(err => console.log(err))
    // }, []);

    return (
        <>
            <div>
                <div className='details'>
                    <div className='details_border_list'>
                        <Heading as='h3' size='lg'>
                            Vehicle History
                        </Heading>
                        <div className='details_border'>
                            <ul className='details_list'>
                                <li><i>Car Name:</i> {cars?.carName}</li>
                                <li><i>Car Company:</i> {capitalizeLetterString(getCar.carCompany)}</li>
                                <li><i>Car Color:</i> {capitalizeLetterString(getCar.carColor)}</li>
                                <li><i>Fuel Type:</i> {capitalizeLetterString(getCar.carFuelType)}</li>
                                <li>
                                    <i>Transmission:</i> {capitalizeLetterString(getCar.carTransmissionType.toLowerCase()).replace("_", " ")}
                                </li>
                            </ul>
                            <ul className='details_list'>
                                <li><i>Damaged:</i> {(cars?.damaged) ? "Yes" : "No"}</li>
                                <li><i>HP:</i> {cars?.hp}</li>
                                <li><i>KM:</i> {cars?.km}</li>
                                <li><i>Production Year:</i> {cars?.productionYear}</li>
                                <li><i>Quantity in Stock:</i> {cars?.quantityInStock}</li>
                            </ul>
                        </div>

                        <div className='details_paragraph'>
                            <div style={{marginBottom: "30px"}}>
                                <Heading as='h3' size='lg'>Details</Heading>
                                <p>JDM Supply is proud to present this 1 of 715 ever made AP1 Honda S2000's finished in
                                    the
                                    “Grand Prix White” exterior, with tan leather interior combination. Complete with a
                                    clean Carfax report, documentation and service records, this particular Honda S2000
                                    has
                                    gone unmodified its entire life and boasts 29,200 original miles. Included with the
                                    sale
                                    is the rare OE color matched hardtop with cover and stand! A rare investment
                                    opportunity
                                    for collectors and enthusiasts alike!</p>
                            </div>
                            <div style={{marginBottom: "30px"}}>
                                <Heading as='h3' size='lg'>OE equipment</Heading>
                                <ul className='details_paragraph_list'>
                                    <li>16 inch alloy rims</li>
                                    <li>16 inch alloy rims</li>
                                    <li>16 inch alloy rims</li>
                                    <li>16 inch alloy rims</li>
                                    <li>16 inch alloy rims</li>
                                </ul>
                            </div>
                            <div style={{marginBottom: "30px"}}>
                                <Heading as='h3' size='lg'>Recent service history </Heading>
                                <ul className='details_paragraph_list'>
                                    <li>Oil change performed, October 2021</li>
                                    <li>OE Honda S2000 battery installed, October 2021</li>
                                    <li>Front Xpel clear bra installed recently</li>
                                </ul>
                            </div>
                        </div>
                    </div>
                    <div className='bid_information'>
                        <h1 className='bid_title'>Bid Information</h1>
                        <ul>

                            <li className='bid_list'>
                                Bid to:<span>$26.000</span>
                            </li>

                        </ul>
                        <div style={{paddingTop: "20px"}}>
                            <PopUp id={getCar.id}/>
                        </div>
                    </div>
                </div>
            </div>
        </>
    )
}

export default OneCarContent;
