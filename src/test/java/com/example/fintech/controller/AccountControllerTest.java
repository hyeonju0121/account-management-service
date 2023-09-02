package com.example.fintech.controller;

import com.example.fintech.account.controller.AccountController;
import com.example.fintech.account.dto.AccountDto;
import com.example.fintech.account.dto.CreateAccount;
import com.example.fintech.account.dto.DeleteAccount;
import com.example.fintech.account.service.AccountService;
import com.example.fintech.account.type.AccountStatus;
import com.example.fintech.production.type.ProductionType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AccountController.class)
public class AccountControllerTest {
    @MockBean
    private AccountService accountService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("계좌 개설")
    void successCreateAccount() throws Exception {
        //given
        given(accountService.createAccount(anyString(), anyLong(), anyLong()))
                .willReturn(getAccountDto());

        //when, then
        mockMvc.perform(post("/account")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                new CreateAccount.Request("hyeonju0121", 1L, 5000L)
                        )))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accountNumber").value("714-3722-6286-62"))
                .andExpect(jsonPath("$.memberId").value("hyeonju0121"))
                .andExpect(jsonPath("$.memberName").value("유현주"))
                .andExpect(jsonPath("$.productionId").value(1))
                .andExpect(jsonPath("$.productionType").value("SAVINGS_ACCOUNT"))
                .andExpect(jsonPath("$.maturityAt").isNotEmpty())
                .andExpect(jsonPath("$.registeredAt").isNotEmpty())
                .andDo(print());
    }

    @Test
    @DisplayName("계좌 해지")
    void successDeleteAccount() throws Exception {
        //given
        given(accountService.deleteAccount(anyString(), anyString(), anyString(), anyString()))
                .willReturn(getAccountDto());

        //when
        //then
        mockMvc.perform(delete("/account")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                new DeleteAccount.Request("hyeonju0121", "714-3722-6286-62", "123456", "")
                        )))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accountNumber").value("714-3722-6286-62"))
                .andExpect(jsonPath("$.memberId").value("hyeonju0121"))
                .andExpect(jsonPath("$.memberName").value("유현주"))
                .andExpect(jsonPath("$.productionType").value("SAVINGS_ACCOUNT"))
                .andExpect(jsonPath("$.accountStatus").value("UNREGISTERED"))
                .andDo(print());
    }


    private AccountDto getAccountDto() {
        return AccountDto.builder()
                .accountNumber("714-3722-6286-62")
                .production(1L)
                .productionType(ProductionType.SAVINGS_ACCOUNT)
                .userId(1L)
                .memberId("hyeonju0121")
                .memberName("유현주")
                .balance(5000L)
                .accountStatus(AccountStatus.UNREGISTERED)
                .registeredAt(LocalDateTime.now())
                .maturityAt(LocalDateTime.now())
                .build();
    }
}
