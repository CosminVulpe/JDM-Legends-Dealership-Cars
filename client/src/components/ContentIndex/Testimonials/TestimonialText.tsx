import React from "react";
import {Text, useColorModeValue,} from '@chakra-ui/react';
import {PropsTestimonials} from "./TestimonialContent";


const TestimonialText: React.FC<PropsTestimonials> = ({children}) => {
    return (
        <Text
            textAlign={'center'}
            color={useColorModeValue('gray.600', 'gray.400')}
            fontSize={'sm'}>
            {children}
        </Text>
    );
}
export default TestimonialText;
