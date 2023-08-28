package com.example.fintech.controller;

import com.example.fintech.production.controller.ProductionController;
import com.example.fintech.production.dto.CreateProduction;
import com.example.fintech.production.dto.ProductionDto;
import com.example.fintech.production.service.ProductionService;
import com.example.fintech.production.type.InterestPaymentMethod;
import com.example.fintech.production.type.NumMonthlyPayments;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductionController.class)
public class ProductionControllerTest {
    @MockBean
    private ProductionService productionService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private CreateProduction.Request validRequest;

    @BeforeEach
    public void setUp() {
        validRequest = new CreateProduction.Request(
                2L, "프리 적금",
                "매일, 매주, 매월 자유롭게 적금 운용해보세요. 매달 최대 300만원까지 저금이 가능합니다.",
                6, 2.0,
                "maturity_date", "free",
                3000000L);
    }

    @Test
    @DisplayName("계좌 상품 등록")
    void successCreateProduction() throws Exception {
        //given
        given(productionService.createProduction(
                anyLong(), anyString(), anyString(), anyInt(),
                anyDouble(), anyString(), anyString(), anyLong()))
                .willReturn(getProductionDto());
        //when, then
        mockMvc.perform(post("/production")
                .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validRequest)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.productionId").value(2L))
                .andExpect(jsonPath("$.productionTitle").value("프리 적금"))
                .andExpect(jsonPath("$.contractPeriod").value(6))
                .andExpect(jsonPath("$.maxMonthlySavings").value(3000000L))
                .andDo(print());
    }

    private ProductionDto getProductionDto() {
        return ProductionDto.builder()
                .productionId(2L)
                .productionTitle("프리 적금")
                .productionContents("매일, 매주, 매월 자유롭게 적금 운용해보세요. 매달 최대 300만원까지 저금이 가능합니다.")
                .contractPeriod(6)
                .interestRate(2.0)
                .interestPaymentMethod(InterestPaymentMethod.MATURITY_DATE)
                .numMonthlyPayments(NumMonthlyPayments.FREE)
                .maxMonthlySavings(3000000L)
                .build();
    }
}