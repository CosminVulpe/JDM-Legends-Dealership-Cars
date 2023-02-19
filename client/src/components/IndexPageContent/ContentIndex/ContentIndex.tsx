import React from "react";
import styled from 'styled-components/macro';
import Image1 from "../../../Images/Index-cover-image/image1.jpg";
import Button from "../../Button/Button";
import {Heading} from "@chakra-ui/react";
import Testimonials from "../Testimonials/Testimonials";
import CarCompany from "../CarCompany/CarCompany";
import Footer from "../../Footer/Footer";
import {Review} from "../../Service/interfaces/Interfaces";

export const Section = styled.section`
  width: 100%;
  height: 100%;
  padding: 3rem;
`;

export const Container = styled.div`
  padding: 1rem calc((100vw - 1300px) / 2);
  display: grid;
  grid-template-columns: 1fr 1fr;
  grid-template-rows: 600px;
  @media screen and (max-width: 768px) {
    grid-template-columns: 1fr;
  }
`;


const ColumnLeft = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: flex-start;
  line-height: 1.4;
  padding: 1rem 2rem;

  h1 {
    margin-bottom: 1rem;
    font-size: clamp(1.5rem, 6vw, 2rem);
  }

  p {
    margin-bottom: 2rem;
  }
`;

const ColumnRight = styled.div`
  padding: 1rem 2rem;
  display: flex;
  justify-content: center;
  align-items: center;
  order: 1;

  img {
    width: 100%;
    height: 100%;
    object-fit: cover;

    @media screen and (max-width: 768px) {
      width: 90%;
      height: 90%;
      margin-top: 25px;
    }
  }
`;

export interface IProps {
    reviews: Review[] | null
}

const ContentIndex: React.FC<IProps> = ({reviews}) => {

    return (
        <div id="content-index">
            <Section>
                <Container>
                    <ColumnLeft>
                        <Heading as="h2" size="xl" style={{paddingBottom: "1rem"}}>Description</Heading>
                        <p>
                            Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut
                            labore
                            et
                            dolore magna aliqua. Adipiscing tristique risus nec feugiat in fermentum. Mi in nulla
                            posuere
                            sollicitudin aliquam ultrices. Netus et malesuada fames ac turpis egestas. Leo urna molestie
                            at
                            elementum eu facilisis. Massa massa ultricies mi quis. Vestibulum morbi blandit cursus
                            risus.
                        </p>
                        <p>Rhoncus urna neque viverra justo nec ultrices dui. Ultricies mi quis hendrerit dolor. Nec
                            ullamcorper
                            sit amet risus nullam eget. Aliquam malesuada bibendum arcu vitae elementum. Urna id
                            volutpat
                            lacus
                            laoreet non. Adipiscing elit duis tristique sollicitudin. Mi proin sed libero enim.</p>
                        <Button buttonText={"Learn More"}/>
                    </ColumnLeft>

                    <ColumnRight>
                        <img src={Image1} alt="car-line-up"/>
                    </ColumnRight>
                </Container>
            </Section>
            {(reviews === null) ?
                <Testimonials reviews={null} />
                :
                <Testimonials reviews={reviews}/>
            }
            <CarCompany/>
            <Footer/>
        </div>
    );
}

export default ContentIndex;
