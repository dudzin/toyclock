package com.daro.toyclock.scheduler;

import com.daro.toyclock.clock.ClockRepository;
import com.daro.toyclock.sender.WebhookSender;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.Mockito.after;
import static org.mockito.Mockito.verify;

@SpringBootTest
class ClockSchedulerTest {
    @MockBean
    private WebhookSender sender;
    @Autowired
    private ClockRepository clockRepository;

    @AfterEach
    public void cleanup() {
        clockRepository.clean();
    }

    @Test
    @DisplayName("Sends according to clock, for registered callbacks")
    public void test() {
        // remember that first send is after saving
        clockRepository.save("A", 1);
        verify(sender, after(5000).atLeast(6)).send("A");

        clockRepository.save("B", 3);
        verify(sender, after(5000).atLeast(2)).send("B");

        clockRepository.save("C", 6);
        verify(sender, after(5000).atMostOnce()).send("C");
    }
}