import React, {useEffect} from "react";
import styled from "styled-components/macro";
import {Heading} from "@chakra-ui/react";
import {useNavigate} from "react-router-dom";
import {ApiGetCar} from "../Service/ApiRequests";

interface Props {
    imageUrl: string,
    titleCard: String,
    descriptionCard: String,
    carId: number
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
    text-align: center;
  }

  .card__description {
    padding: 0 1rem;
    text-align: center;
  }

  .card__btn {
    padding: 1rem;
    font-family: inherit;
    font-weight: bold;
    font-size: 1rem;
    margin: 1rem;
    border: 3.5px solid #000000;
    background: transparent;
    color: #000000;
    border-radius: 0.2rem;
    transition: background 200ms ease-in, color 200ms ease-in;
  }

  &:hover {
    transform: scale(1.02);
  }

  .card__btn:hover {
    background: rgba(156, 94, 30, 0.79);
    color: white;
  }
`;


const Card: React.FC<Props> = ({
                                   imageUrl
                                   , titleCard
                                   , descriptionCard,
                                   carId
                               }) => {
    const navigate = useNavigate();

    return (
        <MainCard>
            <div className="card__body">
                <img src={imageUrl} className="card__image" alt={"car"}/>
                <Heading as='h4' size='md' className="card__title">{titleCard}</Heading>
                <p className="card__description">{descriptionCard}</p>
            </div>
            <button className="card__btn" onClick={() => navigate("/car/" + carId)}>View Car</button>
        </MainCard>
    )
}
export default Card;
