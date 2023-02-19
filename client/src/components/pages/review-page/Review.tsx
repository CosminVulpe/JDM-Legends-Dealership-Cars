import React, {useState} from "react";
import styled, {css} from "styled-components/macro";
import {Heading} from "@chakra-ui/react";
import {FaStar} from "react-icons/fa";
import NavBar from "../../NavBar/NavBar";
import {ApiPostReview} from "../../Service/api-requests/ApiRequests";
import {successfulNotification} from "../../Service/toastify-notification/ToastifyNotification";
import {ToastContainer} from "react-toastify";
import 'react-toastify/dist/ReactToastify.css';
import {ReviewInterface} from "../../Service/interfaces/Interfaces";
import * as Yup from "yup";
import {useFormik} from "formik";

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

const Form = styled.form`
  display: flex;
  flex-direction: column;
  align-items: center;
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

    const validationSchema = Yup.object({
        title: Yup.string().min(5).max(20).required("Title is required"),
        description: Yup.string().min(5).required("Description is required"),
        starRating: Yup.number().required("Star Rating is required")
    });

    const formik = useFormik<ReviewInterface>({
        initialValues: {
            title: "",
            description: "",
            starRating: 0
        },
        validationSchema,
        onSubmit: (values: any) => console.log(values)
    })

    const [starRating, setStarRating] = useState<number>(0);

    const [hoverValue, setHoverValue] = useState<number | React.SetStateAction<undefined>>(undefined);

    const handleClickStarEffect = (value: number): void => setStarRating(value);

    const handleMouseOver = (newHoverValue: number | React.SetStateAction<undefined>): void => setHoverValue(newHoverValue);

    const handleMouseLeave = (): void => setHoverValue(undefined);

    const sendInfoReviewBackend = (): void => {
        formik.values["starRating"] = starRating;
        ApiPostReview(formik.values, "")
            .then(response => {
                if (response.status === 201) {
                    successfulNotification("Review successfully added!");
                    cleanFieldsReviewSection();
                }
            }).catch(err => console.log(err));
    }

    const cleanFieldsReviewSection = (): void => {
        handleClickStarEffect(0);
        setStarRating(0);
        formik.resetForm();
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
                            color={(hoverValue || starRating) > index ? colors.orange : colors.grey}
                            style={{
                                marginRight: 10,
                                cursor: "pointer"
                            }}
                        />
                    )}
                </Stars>
                <Form onSubmit={formik.handleSubmit}>
                    <TitleReview placeholder={"Title for the review"}
                                 onChange={formik.handleChange}
                                 onBlur={formik.handleBlur}
                                 name="title"
                                 value={formik.values.title.toString()}/>
                    {(formik.touched.title && formik.errors.title) &&
                        <div style={{color: "red"}}>{formik.errors.title as unknown as string}</div>}

                    <DescriptionReview placeholder={"What's your experience?"}
                                       onChange={formik.handleChange}
                                       onBlur={formik.handleBlur}
                                       name="description"
                                       value={formik.values.description.toString()}/>
                    {(formik.touched.description && formik.errors.description) &&
                        <div style={{color: "red"}}>{formik.errors.description as unknown as string}</div>}

                    <Button onClick={sendInfoReviewBackend}
                            disabled={!!formik.errors.title && !!formik.errors.description}
                            type="submit">Submit</Button>
                </Form>

            </Container>
        </>
    )
}

export default Review;
