package com.griddynamics.mybank.controller.api;

import com.griddynamics.mybank.controller.api.dto.IncreaseBalanceDto;
import com.griddynamics.mybank.entity.Card;
import com.griddynamics.mybank.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Sergey Korneev
 */
@RestController
@RequestMapping("/cards")
public class CardController {
    @Autowired
    private CardService cardService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public Card[] index() {
        return cardService.getCards();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Card get(@PathVariable int id) {
        return cardService.getCard(id);
    }

    @RequestMapping(value = "/{id}/lock", method = RequestMethod.POST)
    public Card lock(@PathVariable int id) {
        return cardService.lock(id);
    }

    @RequestMapping(value = "/{id}/unlock", method = RequestMethod.POST)
    public Card unlock(@PathVariable int id) {
        return cardService.unlock(id);
    }

    @RequestMapping(value = "/{id}/increase", method = RequestMethod.POST)
    public Card increaseBalance(@PathVariable int id, @RequestBody IncreaseBalanceDto dto) {
        return cardService.increaseBalance(id, dto.summ);
    }
}
