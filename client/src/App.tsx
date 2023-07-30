import React, {useEffect, useState} from 'react';
import {GlobalStyle} from "./flow/DealershipCars/components/GlobalStyle/GlobalStyle";
import NavBar from "./flow/DealershipCars/components/NavBar/NavBar";
import GifAnimation from "./flow/DealershipCars/components/Gif/GifAnimation";
import ContentIndex from "./flow/DealershipCars/components/IndexPageContent/ContentIndex/ContentIndex";
import {ApiGetReview, getCancelToken} from "./flow/DealershipCars/components/Service/api-requests/ApiRequests";
import axios from "axios";
import {ReviewInterface} from "./flow/DealershipCars/components/Service/interfaces/Interfaces";

const App: React.FC = () => {

    const[topThreeReviews, setTopThreeReviews] = useState<ReviewInterface[]>([]);

    useEffect(() => {
        ApiGetReview()
            .then((response) => {
                if (response.status === 200) {
                    setTopThreeReviews(response.data);
                }
            }).catch(err => {
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
            <GlobalStyle/>
            <NavBar/>
            <GifAnimation indexPageVideo={true}/>
            <ContentIndex  reviews={topThreeReviews}/>
        </>
    );
}

export default App;
