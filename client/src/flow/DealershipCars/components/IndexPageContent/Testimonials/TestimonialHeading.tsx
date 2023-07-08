import {Heading} from "@chakra-ui/react";
import React from "react";
import {PropsTestimonials} from "./TestimonialContent";


const TestimonialHeading: React.FC<PropsTestimonials> = ({children}) => {
    return (
        <Heading as={'h3'} fontSize={'xl'}>
            {children}
        </Heading>
    );
};
export default TestimonialHeading;
