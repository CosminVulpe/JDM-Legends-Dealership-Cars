import React from "react";
import {Heading} from "@chakra-ui/react";
import {Car} from "../../../IndexPageContent/CarCompany/CarCompany";
import './OneCarContent.css';

interface Props {
    car: Car | undefined;
}

const OneCarContent: React.FC<Props> = ({car}) => {
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
                                <li>Manufacturing year : 2003</li>
                                <li>Manufacturing year : 2003</li>
                                <li>Manufacturing year : 2003</li>
                                <li>Manufacturing year : 2003</li>
                                <li>Manufacturing year : 2003</li>
                            </ul>
                            <ul className='details_list'>
                                <li>Manufacturing year : 2003</li>
                                <li>Manufacturing year : 2003</li>
                                <li>Manufacturing year : 2003</li>
                                <li>Manufacturing year : 2003</li>
                                <li>Manufacturing year : 2003</li>
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
                            <li className='bid_list'>Bid to:<span>$26,000</span></li>
                            <li className='bid_list'>Bid to:<span>$26,000</span></li>
                            <li className='bid_list'>Bid to:<span>$26,000</span></li>
                            <li className='bid_list'>Bid to:<span>$26,000</span></li>
                            <li className='bid_list'>Bid to:<span>$26,000</span></li>
                            <li className='bid_list'>Bid to:<span>$26,000</span></li>
                        </ul>
                        <button>Bid Now</button>
                        <div className='bid_links'>

                        </div>
                    </div>
                </div>
            </div>
        </>
    )
}

export default OneCarContent;
