package com.jdm.legends.dealership.cars.integration.controller;

import com.jdm.legends.dealership.cars.service.ReviewService;
import com.jdm.legends.dealership.cars.service.dto.Review;
import com.jdm.legends.dealership.cars.service.repository.ReviewRepository;
import com.jdm.legends.dealership.cars.utils.UtilsMock;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.jdm.legends.dealership.cars.utils.UtilsMock.buildReviewRequest;
import static com.jdm.legends.dealership.cars.utils.UtilsMock.writeJsonAsString;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ReviewControllerTest {
    @Autowired
    private ReviewService service;

    @Autowired
    private ReviewRepository repository;

    @Autowired
    private MockMvc mvc;

    @Test
    void addReviewController() throws Exception{
        Review review = buildReviewRequest();
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/review")
                .contentType(APPLICATION_JSON)
                .content(writeJsonAsString(review))
                .accept(APPLICATION_JSON);

        mvc.perform(builder).andExpect(status().isCreated());
        assertThat(repository.findAll().size()).isEqualTo(1);
    }

}
