import React, {useEffect, useState} from "react";
import {ApiGetCar, ApiGetCarPictures, getCancelToken} from "../../Service/api-requests/ApiRequests";
import {useParams} from "react-router-dom";
import axios from "axios";
import Error from "../error-page/Error";
import NavBar from "../../NavBar/NavBar";
import {Section} from "../../IndexPageContent/ContentIndex/ContentIndex";
import {Heading} from "@chakra-ui/react";
import "./OneCarStyle.css";
import OneCarContent from "./one-car-content/OneCarContent";
import Footer from "../../Footer/Footer";
import {Car} from "../../Service/interfaces/Interfaces";

const slideStyles = {
    width: "100%",
    height: "100%",
    borderRadius: "10px",
    backgroundSize: "cover",
    backgroundPosition: "center",
};

const dotsContainerStyles = {
    display: "flex",
    justifyContent: "center",
};

const dotStyle = {
    margin: "0 3px",
    cursor: "pointer",
    fontSize: "20px",
};


const OneCar: React.FC = () => {
    let {id} = useParams();

    const [oneCarDetails, setOneCarDetails] = useState<Car>();
    const [photosLinks, setPhotosLinks] = useState<string[]>([]);
    const [currentIndex, setCurrentIndex] = useState(0);

    useEffect(() => {
        ApiGetCar(id ? id : "")
            .then((res: any) => setOneCarDetails(res.data))
            .catch(err => {
                if (axios.isCancel(err)) {
                    console.log("cancelled!");
                }
                if (err.response.status === 400 || err.response.status === 404) {
                    setOneCarDetails(undefined);
                }
            });

        return () => {
            getCancelToken().cancel();
        }
    }, [setOneCarDetails, id]);

    useEffect(() => {
        ApiGetCarPictures(oneCarDetails?.carName)
            .then((res: any) => {
                const array = [];
                for (let i = 0; i < res.data.results.length; i++) {
                    array.push(res.data.results[i].urls.small)
                }
                setPhotosLinks(array);
            })
            .catch((err) => console.log(err));
    }, [oneCarDetails]);


    const length:number = (oneCarDetails !== undefined) ? Object.keys(oneCarDetails).length : 0;

    const goToPrevious = () => {
        const isFirstSlide = currentIndex === 0;
        const newIndex = isFirstSlide ? length - 1 : currentIndex - 1;
        setCurrentIndex(newIndex);
    };
    const goToNext = () => {
        const isLastSlide = currentIndex === length - 1;
        const newIndex = isLastSlide ? 0 : currentIndex + 1;
        setCurrentIndex(newIndex);
    };

    const goToSlide = (slideIndex: any) => {
        setCurrentIndex(slideIndex);
    };

    const slideStylesWidthBackground = {
        ...slideStyles,
        backgroundImage: `url(${photosLinks[currentIndex]})`,
    };

    if (oneCarDetails === undefined) {
        return <Error/>
    }

    return (
        <>
            <NavBar/>
            <Section style={{padding: "10rem"}}>
                <Heading as='h2' size='2xl' style={{marginBottom: "4rem"}}>{oneCarDetails?.carName}</Heading>
                <div className="container-div">
                    <div className="slides">
                        <div>
                            <div onClick={goToPrevious} className="left-arrow">
                                ❰
                            </div>
                            <div onClick={goToNext} className="right-arrow">
                                ❱
                            </div>
                        </div>
                        <div style={slideStylesWidthBackground}></div>
                        <div style={dotsContainerStyles}>
                            {photosLinks.map((_slide, slideIndex) => (
                                <div
                                    style={dotStyle}
                                    key={slideIndex}
                                    onClick={() => goToSlide(slideIndex)}
                                >
                                    ●
                                </div>
                            ))}
                        </div>
                    </div>
                </div>
            </Section>
            <OneCarContent cars={oneCarDetails}/>
            <Footer/>
        </>
    )
}

export default OneCar;
