package com.ps.test.creditcard.webservice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.MappingJsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ps.test.creditcard.persistence.Card;
import com.ps.test.creditcard.persistence.CardRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CardService {

	private final CardRepository cardRepository;
	
	@Autowired
	public CardService(CardRepository cardRepository) {
		super();
		this.cardRepository = cardRepository;
	}
	
	public Card save(Card card) {
		return cardRepository.save(card);
	}
	
	public List<Card> getAll() {
		return cardRepository.findAll();
	}
	
	public static boolean isCardNumber(String cardNum) {
		if (cardNum.isEmpty() || cardNum.length() > 19) {
			return false;
		}
		
		return cardNum.chars().allMatch( Character::isDigit );
	}
	
	
	public static boolean checkLuhn(String card) {
		if (card == null)
			return false;
		char checkDigit = card.charAt(card.length() - 1);
		String digit = calcCheckDigit(card.substring(0, card.length() - 1));
		return checkDigit == digit.charAt(0);
	}
	
	private static String calcCheckDigit(String card) {
		if (card == null)
			return null;
		String digit;
		/* convert to array of int for simplicity */
		int[] digits = new int[card.length()];
		for (int i = 0; i < card.length(); i++) {
			digits[i] = Character.getNumericValue(card.charAt(i));
		}
		
		/* double every other starting from right - jumping from 2 in 2 */
		for (int i = digits.length - 1; i >= 0; i -= 2)	{
			digits[i] += digits[i];
			
			/* taking the sum of digits grater than 10 - simple trick by substract 9 */
			if (digits[i] >= 10) {
				digits[i] = digits[i] - 9;
			}
		}
		int sum = 0;
		for (int i = 0; i < digits.length; i++) {
			sum += digits[i];
		}
		/* multiply by 9 step */
		sum = sum * 9;
		
		/* convert to string to be easier to take the last digit */
		digit = sum + "";
		return digit.substring(digit.length() - 1);
	}
}
