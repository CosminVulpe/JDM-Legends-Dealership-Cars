package com.jdm.legends.dealership.cars.integration.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.jdm.legends.dealership.cars.controller.dto.ReviewRequest;
import com.jdm.legends.dealership.cars.service.entity.Review;
import com.jdm.legends.dealership.cars.service.repository.ReviewRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.jdm.legends.dealership.cars.service.mapper.ReviewMapper.INSTANCE;
import static com.jdm.legends.dealership.cars.utils.TestDummy.buildReviewRequest;
import static com.jdm.legends.dealership.cars.utils.UtilsMock.readValue;
import static com.jdm.legends.dealership.cars.utils.UtilsMock.writeJsonAsString;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test-in-memory")
class ReviewControllerIT {
    @Autowired
    private ReviewRepository repository;

    @Autowired
    private MockMvc mvc;

    @Test
    void addReview() throws Exception {
        ReviewRequest review = buildReviewRequest();

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/review/add")
                .contentType(APPLICATION_JSON)
                .content(writeJsonAsString(review))
                .accept(APPLICATION_JSON);

        String contentAsString = mvc.perform(builder)
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        Review responseEntity = readValue(contentAsString, Review.class);

        assertThat(repository.findAll()).hasSize(1);
        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getTitle()).isEqualTo("Recommended to friends");
        assertThat(responseEntity.getDescription()).isEqualTo("Very good");
        assertThat(responseEntity.getStarRating()).isEqualTo(5);
    }

    @Test
    void shouldGiveStatusBadRequestWhenAddingReview() throws Exception {
        ReviewRequest review = buildReviewRequest("h", "y", -1);

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/review/add")
                .contentType(APPLICATION_JSON)
                .content(writeJsonAsString(review))
                .accept(APPLICATION_JSON);
        mvc.perform(builder).andExpect(status().isBadRequest());
    }

    @Test
    void getRecentReviews() throws Exception {
        repository.save(INSTANCE.reviewRequestToReviewEntity(buildReviewRequest("very good mate","I suggested to my friends", 5)));
        repository.save(INSTANCE.reviewRequestToReviewEntity(buildReviewRequest("DONT LIKE IT","Prices too high", 1)));

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/review").accept(APPLICATION_JSON);
        String contentAsString = mvc.perform(builder).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        List<Review> retrievedReviewList = readValue(contentAsString, new TypeReference<>() {
        });

        assertThat(repository.findAll().size()).isSameAs(retrievedReviewList.size());
    }

}
