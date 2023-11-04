package com.jdm.legends.dealership.cars.integration.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jdm.legends.dealership.cars.controller.dto.ReviewDTO;
import com.jdm.legends.dealership.cars.service.entity.Review;
import com.jdm.legends.dealership.cars.service.mapping.Mapper;
import com.jdm.legends.dealership.cars.service.repository.ReviewRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.IntStream;

import static com.jdm.legends.dealership.cars.utils.TestData.buildReviewRequest;
import static com.jdm.legends.dealership.cars.utils.TestData.writeJsonAsString;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test-in-memory")
class ReviewControllerIT implements Mapper<ReviewDTO, Review> {
    @Autowired
    private ReviewRepository repository;

    @Autowired
    private MockMvc mvc;

    @Test
    void addReview() throws Exception {
        ReviewDTO review = buildReviewRequest();

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/review/add")
                .contentType(APPLICATION_JSON)
                .content(writeJsonAsString(review))
                .accept(APPLICATION_JSON);

        mvc.perform(builder)
                .andExpect(status().isCreated())
                .andDo(print())
                .andExpect(jsonPath("$.title").value("Recommended to friends"))
                .andExpect(jsonPath("$.description").value("Very good"))
                .andExpect(jsonPath("$.starRating").value(5));

        assertThat(repository.findAll()).hasSize(1);
    }

    @Test
    void getRecentReviews() throws Exception {
        List<Review> reviewList = IntStream.range(0, 2).mapToObj(i -> repository.save(map(buildReviewRequest()))).toList();

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/review")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON);

        MvcResult mvcResult = mvc.perform(builder).andExpect(status().isOk()).andReturn();

        ObjectMapper mapper = new ObjectMapper();
        List<Review> retrievedReviewList = mapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<>() {
        });

        assertThat(reviewList.size()).isSameAs(retrievedReviewList.size());
    }

    @Override
    public Review map(ReviewDTO source) {
        return Review.builder()
                .title(source.title())
                .description(source.description())
                .starRating(source.starRating())
                .build();
    }
}
