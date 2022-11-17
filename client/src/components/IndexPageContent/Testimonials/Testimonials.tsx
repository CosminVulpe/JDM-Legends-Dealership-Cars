import {Box, Container, Heading, Stack, Text, useColorModeValue,} from '@chakra-ui/react';
import Testimonial from "./Testimonial";
import TestimonialContent from "./TestimonialContent";
import TestimonialHeading from "./TestimonialHeading";
import TestimonialText from "./TestimonialText";
import TestimonialAvatar from "./TestimonialAvatar";
import ProfilePicture from "../../../Images/Testimonials/testimonialProfilePicture.png";
import React from "react";
import {IProps} from "../ContentIndex/ContentIndex";
import {TestimonialsData} from "./TestimonialsData";


const Testimonials: React.FC<IProps> = ({reviews}) => {

    console.log(reviews)


    return (
        <Box bg={useColorModeValue('gray.100', 'gray.700')} mt={20}>
            <Container maxW={'7xl'} py={16} as={Stack} spacing={12}>
                <Stack spacing={0} align={'center'}>
                    <Heading>Our Clients Speak</Heading>
                    <Text>We have been working with clients around the world</Text>
                </Stack>
                <Stack
                    direction={{base: 'column', md: 'row'}}
                    spacing={{base: 10, md: 4, lg: 10}}>
                    {reviews?.length !== 0 && reviews !== null ?
                        reviews.map((review, index) =>
                            <Testimonial key={review.id}>
                                <TestimonialContent>
                                    <TestimonialHeading>{review.title}</TestimonialHeading>
                                    <TestimonialText>
                                        {review.description}
                                    </TestimonialText>
                                </TestimonialContent>
                                <TestimonialAvatar
                                    src={ProfilePicture}
                                    name="John Smith"
                                    title="CEO at ABC Corporation"
                                />
                            </Testimonial>
                        )
                        :
                        TestimonialsData.map((data) =>
                            <Testimonial key={data.id}>
                                <TestimonialContent>
                                    <TestimonialHeading>{data.heading}</TestimonialHeading>
                                    <TestimonialText>
                                        Lorem ipsum dolor sit amet, consectetur adipiscing elit. Auctor
                                        neque sed imperdiet nibh lectus feugiat nunc sem.
                                    </TestimonialText>
                                </TestimonialContent>
                                <TestimonialAvatar
                                    src={ProfilePicture}
                                    name={data.name}
                                    title={data.title}
                                />
                            </Testimonial>
                        )}
                </Stack>
            </Container>
        </Box>
    );
}
export default Testimonials;
