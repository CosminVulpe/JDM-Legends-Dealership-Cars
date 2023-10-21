package com.jdm.legends.dealership.cars.integration.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jdm.legends.dealership.cars.service.dto.Review;
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

import static com.jdm.legends.dealership.cars.utils.UtilsMock.buildReviewRequest;
import static com.jdm.legends.dealership.cars.utils.UtilsMock.writeJsonAsString;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test-in-memory")
public class ReviewControllerIT {
    @Autowired
    private ReviewRepository repository;

    @Autowired
    private MockMvc mvc;

    @Test
    void addReview() throws Exception {
        Review review = buildReviewRequest();
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/review")
                .contentType(APPLICATION_JSON)
                .content(writeJsonAsString(review))
                .accept(APPLICATION_JSON);

        mvc.perform(builder)
                .andExpect(status().isCreated())
                .andDo(print())
                .andExpect(jsonPath("$.title").value("Recommended to friends"))
                .andExpect(jsonPath("$.description").value("Very good"))
                .andExpect(jsonPath("$.starRating").value(5));
        assertThat(repository.findAll().size()).isEqualTo(1);
    }


    @Test
    void getRecentReviews() throws Exception {
        List<Review> reviewList = IntStream.range(0, 2).mapToObj(i -> repository.save(buildReviewRequest())).toList();

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/review")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON);

        MvcResult mvcResult = mvc.perform(builder).andExpect(status().isOk()).andReturn();

        ObjectMapper mapper = new ObjectMapper();
        List<Review> retrievedReviewList = mapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<>() {
        });

        assertThat(reviewList.size()).isEqualTo(retrievedReviewList.size());
    }

}
