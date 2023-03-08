package com.example.cgrestaurant;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static com.example.cgrestaurant.contants.RestaurantConstants.*;

@AutoConfigureMockMvc
@ActiveProfiles("application-integration")
@ExtendWith({SpringExtension.class})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BaseIntegrationTest {

    public static final String BASE_SUPPLIER_API_ENDPOINT = API_PREFIX + API_VERSION_V1 + API_SUPPLIERS;
    public static final String BASE_BRANCH_ENDPOINT = API_PREFIX + API_VERSION_V1 + API_BRANCHES;
    public static final String BASE_PRODUCT_ENDPOINT = API_PREFIX + API_VERSION_V1 + API_PRODUCTS;
    public static final String BASE_ORDER_ENDPOINT = API_PREFIX + API_VERSION_V1 + API_ORDER;

    public final ObjectMapper mapper = new ObjectMapper();


    @BeforeEach
    public void setup() {
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    }
}
