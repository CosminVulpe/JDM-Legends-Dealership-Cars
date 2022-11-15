import React from "react";
import styled from "styled-components/macro";
import {Heading} from "@chakra-ui/react";

interface Props {
    imageUrl: string,
    titleCard: String,
    descriptionCard: String
}


const MainCard = styled.div`
  overflow: hidden;
  box-shadow: 0 20px 30px #ccd3e2;
  border-radius: 0.2rem;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  cursor: pointer;
  transition: transform 200ms ease-in;

  .card__image {
    height: 13.5rem;
    width: 100%;
    object-fit: fill;
  }

  .card__title {
    padding: 1rem;
    margin-left: 6.5rem;
  }

  .card__description {
    padding: 0 1rem;
  }

  .card__btn {
    padding: 1rem;
    font-family: inherit;
    font-weight: bold;
    font-size: 1rem;
    margin: 1rem;
    border: 3.5px solid #ae5d14;
    background: transparent;
    color: #000000;
    border-radius: 0.2rem;
    transition: background 200ms ease-in, color 200ms ease-in;
  }

  &:hover {
    transform: scale(1.02);
  }

  .card__btn:hover {
    background: #8d6842;
    color: white;
  }
`;


const Card: React.FC<Props> = ({
                                   imageUrl
                                   , titleCard
                                   , descriptionCard
                               }) => {
    return (
        <MainCard >
            <div className="card__body">
                <img src={imageUrl} className="card__image" alt={"car"}/>
                <Heading as='h4' size='md' className="card__title">{titleCard}</Heading>
                <p className="card__description">{descriptionCard}</p>
            </div>
            <button className="card__btn">View Car</button>
        </MainCard>
    )
}
export default Card;
