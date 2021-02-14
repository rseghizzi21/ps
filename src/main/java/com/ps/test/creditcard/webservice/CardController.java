package com.ps.test.creditcard.webservice;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ps.test.creditcard.persistence.Card;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = "/card")
public class CardController {
	
	private final CardService cardService;

	@Autowired
	public CardController(CardService cardService) {
		this.cardService = cardService;
	}
	
	
	@GetMapping(value = "/all")
	public List<CardResponse> all() {
		
		List<Card> cards = cardService.getAll();
		List<CardResponse> result = new ArrayList<CardResponse>();
		for(Card card: cards) {
			CardResponse cr = new CardResponse();
			cr.setId(card.getId());
			cr.setName(card.getName());
			cr.setCardNumber(card.getCardNumber());
			cr.setCardLimit(card.getCardLimit());
			cr.setBalance(card.getBalance());
			result.add(cr);
		}
		return result;
	}
	
	@PostMapping(value = "/add")
	public CardResponse create(CardRequest request) {
		Card card = new Card();

		CardResponse response = new CardResponse();
		
		if( !cardService.isCardNumber(request.getCardNumber()) ||
				!cardService.checkLuhn(request.getCardNumber())) {
			System.out.println("Error Card Service validation: Card not valid ");
			response.setStatus("Fail");
			response.setMessage("Error in Card creation - Card number is not valid!");
		}
			
		card.setName(request.getName());
		card.setBalance(new BigDecimal(0));
		card.setCardLimit(request.getCardLimit());
		card.setName(request.getName());
		card.setCardNumber(request.getCardNumber());
		
		try {
			Card newcard = cardService.save(card);	
			response.setId(newcard.getId());
			response.setBalance(newcard.getBalance());
			response.setCardLimit(newcard.getCardLimit());
			response.setCardNumber(newcard.getCardNumber());
			response.setName(newcard.getName());
			response.setStatus("OK");
			response.setMessage("Card creation success.");
			
		} catch (Exception e) {
			System.out.println("Error Card Service Save: " + e.getMessage());
			response.setStatus("Fail");
			response.setMessage("Error in Card creation");
		}
	
		return response;
		
	}
}
