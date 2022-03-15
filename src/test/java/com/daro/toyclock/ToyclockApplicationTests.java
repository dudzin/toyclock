package com.daro.toyclock;

import com.daro.toyclock.clock.ClockRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import static org.hamcrest.Matchers.hasItems;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ToyclockApplicationTests {

    @MockBean
    private RestTemplate restTemplate;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ClockRepository clockRepository;

    @AfterEach
    public void cleanup() {
        clockRepository.clean();
    }

    @Test
    @DisplayName("fail to register when parameters are missing")
    void test1() throws Exception {
        mockMvc.perform(
                        post("/clock")
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
    void test2() throws Exception {

        givenCallbackAlreadyExists("http://asd");

        mockMvc.perform(
                        post("/clock")
                                .content("{" +
                                        "\"callback\":\"http://asd\"," +
                                        "\"interval\":5" +
                                        "}")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(
                        status().isBadRequest()
                )
                .andExpect(jsonPath("$.errors",
                        hasItems(
                                "callback is already registered"
                        )
                ));
    }


    @Test
    @DisplayName("on registration success trigger callback")
    void test3() throws Exception {
        mockMvc.perform(
                        post("/clock")
                                .content("{" +
                                        "\"callback\":\"http://asd\"," +
                                        "\"interval\":5" +
                                        "}")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(
                        status().isOk()
                );
        // we could have more sophisticated verification mechanism here, like starting up some server to catch actual requests
        verify(restTemplate, times(1)).postForLocation(anyString(), anyString());
    }

    @Test
    @DisplayName("Cannot deregister callback if it wasn't registered before")
    void test4() throws Exception {
        mockMvc.perform(
                        delete("/clock")
                                .content("{" +
                                        "\"callback\":\"http://asd\""  +
                                        "}")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(
                        status().isBadRequest()
                )
                .andExpect(jsonPath("$.errors",
                        hasItems(
                                "callback unknown"
                        )
                ));
    }

    @Test
    @DisplayName("Can deregister callback")
    void test5() throws Exception {
    }

    private void givenCallbackAlreadyExists(String callback) {
        clockRepository.save(callback, 6);
    }
}
