package com.jdm.legends.dealership.cars.integration;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;


@SpringBootTest
@ActiveProfiles("test-in-memory")
@Transactional
public class IntegrationTest {
}
