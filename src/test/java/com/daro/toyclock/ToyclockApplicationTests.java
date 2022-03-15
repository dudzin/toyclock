package com.daro.toyclock;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasItems;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ToyclockApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("fail to register when parameters are missing")
    void test1() throws Exception {
        mockMvc.perform(
                        post("/register")
                                .content("{}")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(
                        status().isBadRequest()
                )
                .andExpect(jsonPath("$.errors",
                        hasItems(
                                "callback is mandatory",
                                "interval is mandatory"
                        )
                ));
    }

    @Test
    @DisplayName("fail to register on url duplicate")
    void test2() {
    }

    @Test
    @DisplayName("on registration success trigger callback")
    void test3() {
    }

}
