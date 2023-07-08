import {Avatar, Flex, Stack, Text, useColorModeValue} from "@chakra-ui/react";
import React from "react";


interface PropsTestimonials{
    src: string;
    name: String;
    title: String;
}


const TestimonialAvatar:React.FC<PropsTestimonials> = ({src,name,title}) => {
    return (
        <Flex align={'center'} mt={8} direction={'column'}>
            <Avatar src={src}  mb={2}/>
            <Stack spacing={-1} align={'center'}>
                <Text fontWeight={600}>{name}</Text>
                <Text fontSize={'sm'} color={useColorModeValue('gray.600', 'gray.400')}>
                    {title}
                </Text>
            </Stack>
        </Flex>
    );
};

export default TestimonialAvatar;
