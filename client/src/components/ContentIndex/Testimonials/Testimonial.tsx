import React from 'react';
import {Box,} from '@chakra-ui/react';
import {PropsTestimonials} from "./TestimonialContent";

const Testimonial: React.FC<PropsTestimonials> = ({children}) => {
    return <Box>{children}</Box>;
};

export default Testimonial;
