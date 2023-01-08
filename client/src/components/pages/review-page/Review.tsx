import React, {useState} from "react";
import styled, {css} from "styled-components/macro";
import {Heading} from "@chakra-ui/react";
import {FaStar} from "react-icons/fa";
import NavBar from "../../NavBar/NavBar";
import {ApiPostReview} from "../../Service/api-requests/ApiRequests";
import {successfulNotification} from "../../Service/toastify-notification/ToastifyNotification";
import {ToastContainer} from "react-toastify";
import 'react-toastify/dist/ReactToastify.css';

const colors = {
    orange: "#FFBA5A",
    grey: "#a9a9a9"
};

const Container = styled.div`
  width: 100%;
  height: 100%;
  padding: 8rem;

  display: flex;
  flex-direction: column;
  align-items: center;
`;

const Stars = styled.div`
  display: flex;
  flex-direction: row;
`;

const Textarea = css`
  border: 1px solid #a9a9a9;
  border-radius: 5px;
  padding: 10px;
  margin: 20px 0;
`;

const TitleReview = styled.textarea`
  ${Textarea};
  min-height: 50px;
  width: 304px;
`;

const DescriptionReview = styled.textarea`
  ${Textarea};
  min-height: 100px;
  width: 450px;
`;

const Button = styled.button`
  border-radius: 5px;
  width: 300px;

  position: relative;
  display: block;
  color: black;
  font-size: 14px;
  font-family: "montserrat", serif;
  text-decoration: none;
  margin: 30px 0;
  border: 3px solid #ff7675;
  padding: 14px 60px;
  text-transform: uppercase;
  overflow: hidden;
  transition: 1s all ease;

  &::before {
    background: #ff7675;
    //background: #873333;
    content: "";
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%) rotate(45deg);
    z-index: -1;
    transition: all 0.6s ease;

    width: 100%;
    height: 0%;
  }

  &:hover::before {
    height: 500%;
  }
`;


const Review: React.FC = () => {
    const titleLengthMin: number = 5;
    const descriptionLengthMin: number = 15;

    const [reviewSection, setReviewSection] = useState({
        starRating: 0,
        title: "",
        description: ""
    });
    const [hoverValue, setHoverValue] = useState(undefined);

    const handleClickStarEffect = (value: number): void => {
        setReviewSection(prevState => ({
            ...prevState,
            starRating: value
        }));
    }

    const handleMouseOver = (newHoverValue: any): void => {
        setHoverValue(newHoverValue);
    };

    const handleMouseLeave = (): void => {
        setHoverValue(undefined);
    }

    const sendInfoReviewBackend = (): void => {
        ApiPostReview(reviewSection, "")
            .then(response => {
                if (response.status === 201) {
                    successfulNotification("Review successfully added!");
                    cleanFieldsReviewSection();
                }
            }).catch(err => console.log(err));
    }

    const handleOnChange = (event: React.ChangeEvent<HTMLTextAreaElement>): void => {
        setReviewSection(prevState => ({
            ...prevState,
            [event.target.name]: event.target.value
        }));
    }

    const checkReviewFieldsEmpty = (): boolean => {
        return reviewSection.starRating > 0
            && reviewSection.title.length > titleLengthMin
            && reviewSection.description.length > descriptionLengthMin
    }

    const cleanFieldsReviewSection = (): void => {
        handleClickStarEffect(0);
        setReviewSection(prevState => ({
            ...prevState,
            title: "",
            description: ""
        }));
    }


    return (
        <>
            <NavBar/>
            <Container>
                <ToastContainer/>
                <Heading as='h2'
                         size='2xl'
                         style={{
                             marginBottom: "1.5rem"
                         }}>Review</Heading>
                <Stars>
                    {[...Array(5)].map((_, index) =>
                        <FaStar
                            key={index}
                            size={24}
                            onClick={() => handleClickStarEffect(index + 1)}
                            onMouseOver={() => handleMouseOver(index + 1)}
                            onMouseLeave={handleMouseLeave}
                            color={(hoverValue || reviewSection.starRating) > index ? colors.orange : colors.grey}
                            style={{
                                marginRight: 10,
                                cursor: "pointer"
                            }}
                        />
                    )}
                </Stars>
                <TitleReview placeholder={"Title for the review"}
                             onChange={handleOnChange}
                             name="title" maxLength={30}
                             value={reviewSection.title} required/>

                <DescriptionReview placeholder={"What's your experience?"}
                                   onChange={handleOnChange}
                                   name="description" maxLength={100}
                                   value={reviewSection.description} required/>

                <Button onClick={sendInfoReviewBackend}
                        disabled={!checkReviewFieldsEmpty()}>Submit</Button>

            </Container>
        </>
    )
}

export default Review;
