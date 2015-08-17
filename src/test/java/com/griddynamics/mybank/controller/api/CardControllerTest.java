package com.griddynamics.mybank.controller.api;

import com.griddynamics.mybank.entity.Card;
import com.griddynamics.mybank.entity.Owner;
import com.griddynamics.mybank.entity.Transaction;
import com.griddynamics.mybank.service.CardService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

/**
 * @author Sergey Korneev
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"/context/controller-context.xml"})
public class CardControllerTest {

    @Autowired
    private CardService cardService;

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = webAppContextSetup(this.wac).build();
        reset(cardService);
    }

    @Test
    public void index() throws Exception {
        Card[] expectedCards = {createCard()};

        when(cardService.getCards()).thenReturn(expectedCards);

        MockHttpServletRequestBuilder request = get("/cards/");
        request.contentType(MediaType.APPLICATION_JSON);
        request.accept(MediaType.APPLICATION_JSON);

        ResultActions resultActions = mockMvc.perform(request);
        resultActions.andExpect(status().isOk());
        resultActions.andExpect(jsonPath("$", hasSize(1)));
        assertCardJson(resultActions, "$[0]");

        verify(cardService).getCards();
    }

    @Test
    public void getCard() throws Exception {
        when(cardService.getCard("1")).thenReturn(createCard());

        MockHttpServletRequestBuilder request = get("/cards/1");
        request.contentType(MediaType.APPLICATION_JSON);
        request.accept(MediaType.APPLICATION_JSON);

        ResultActions resultActions = mockMvc.perform(request);
        resultActions.andExpect(status().isOk());
        assertCardJson(resultActions, "$");

        verify(cardService).getCard("1");
    }

    @Test
    public void lock() throws Exception {
        when(cardService.lock("1")).thenReturn(createCard());

        MockHttpServletRequestBuilder request = post("/cards/1/lock");
        request.contentType(MediaType.APPLICATION_JSON);
        request.accept(MediaType.APPLICATION_JSON);

        ResultActions resultActions = mockMvc.perform(request);
        resultActions.andExpect(status().isOk());
        assertCardJson(resultActions, "$");

        verify(cardService).lock("1");
    }

    @Test
    public void unlock() throws Exception {
        when(cardService.unlock("1")).thenReturn(createCard());

        MockHttpServletRequestBuilder request = post("/cards/1/unlock");
        request.contentType(MediaType.APPLICATION_JSON);
        request.accept(MediaType.APPLICATION_JSON);

        ResultActions resultActions = mockMvc.perform(request);
        resultActions.andExpect(status().isOk());
        assertCardJson(resultActions, "$");

        verify(cardService).unlock("1");
    }

    @Test
    public void increaseBalance() throws Exception {
        when(cardService.increaseBalance("1", 10.0)).thenReturn(createCard());

        MockHttpServletRequestBuilder request = post("/cards/1/increase");
        request.contentType(MediaType.APPLICATION_JSON);
        request.accept(MediaType.APPLICATION_JSON);
        request.content("{\"summ\": 10}");

        ResultActions resultActions = mockMvc.perform(request);
        resultActions.andExpect(status().isOk());
        assertCardJson(resultActions, "$");

        verify(cardService).increaseBalance("1", 10.0);
    }

    private void assertCardJson(ResultActions resultActions, String rootPath) throws Exception {
        resultActions
            .andExpect(jsonPath(rootPath + ".id", is("cardId")))
            .andExpect(jsonPath(rootPath + ".balance", is(0.0)))
            .andExpect(jsonPath(rootPath + ".locked", is(false)))
            .andExpect(jsonPath(rootPath + ".owner.id", is("ownerId")))
            .andExpect(jsonPath(rootPath + ".owner.firstName", is("testFirstName")))
            .andExpect(jsonPath(rootPath + ".owner.lastName", is("testLastName")))
            .andExpect(jsonPath(rootPath + ".transactions", hasSize(1)))
            .andExpect(jsonPath(rootPath + ".transactions[0].id", is("transactionId")))
            .andExpect(jsonPath(rootPath + ".transactions[0].summ", is(10.0)))
            .andExpect(jsonPath(rootPath + ".transactions[0].adminName", is("adminName")))
            .andExpect(jsonPath(rootPath + ".transactions[0].creationDate", anything()))
        ;
    }

    private Card createCard() {
        Card card = new Card();
        card.setId("cardId");
        card.setOwner(createOwner());
        card.addTransaction(createTransaction());

        return card;
    }

    private Owner createOwner() {
        Owner owner = new Owner();
        owner.setId("ownerId");
        owner.setFirstName("testFirstName");
        owner.setLastName("testLastName");

        return owner;
    }

    private Transaction createTransaction() {
        Transaction transaction = new Transaction();
        transaction.setId("transactionId");
        transaction.setSumm(10);
        transaction.setAdminName("adminName");

        return transaction;
    }
}
