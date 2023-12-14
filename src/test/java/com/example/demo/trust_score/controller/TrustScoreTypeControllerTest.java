package com.example.demo.trust_score.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.demo.service.trust_score.TrustScoreTypeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class TrustScoreTypeControllerTest {

    @Autowired private MockMvc mockMvc;

    @MockBean private TrustScoreTypeService trustScoreTypeService;

    @Autowired private ObjectMapper objectMapper;

    @Test
    @DisplayName("신뢰점수타입 전체 조회")
    public void getTrustScoreTypeList() throws Exception {
        mockMvc.perform(get("/api/trust-score-type")).andExpect(status().isOk());
    }
}
