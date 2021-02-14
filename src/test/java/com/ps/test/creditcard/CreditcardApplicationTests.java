package com.ps.test.creditcard;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.boot.test.context.SpringBootTest;

import com.ps.test.creditcard.webservice.CardService;

@SpringBootTest
class CreditcardApplicationTests {

	@Test
	void contextLoads() {
	}
	
    @BeforeEach
    void setUp() {}

    @Test
    void shouldReturnTrueForValidNumbers() {
        assertAll(
                () -> assertTrue(CardService.checkLuhn("5277029120773860")),
                () -> assertTrue(CardService.checkLuhn("4556069096852293")),
                () -> assertTrue(CardService.checkLuhn("4852789106979220268"))
        );
    }

    @Test
    void shouldReturnFalseForInvalidNumbers() {
        assertAll(
                () -> assertFalse(CardService.checkLuhn("4852789106979220261")),
                () -> assertFalse(CardService.checkLuhn("3543693387314139")),
                () -> assertFalse(CardService.checkLuhn("6759310784561226"))
        );
    }
    
    @Test
    void shouldReturnTrueForValidStringChars() {
        assertAll(
                () -> assertFalse(CardService.isCardNumber("4556069096852293")),
                () -> assertFalse(CardService.isCardNumber("4556069096852293")),  
                () -> assertFalse(CardService.isCardNumber("4852789106979220268"))    
                );
    }
    
    @Test
    void shouldReturnFalseForInvalidStringChars() {
        assertAll(
                () -> assertFalse(CardService.isCardNumber("48527C9106979A20261")), // contains non numeric C and A
                () -> assertFalse(CardService.isCardNumber("3543693387K14l139")),   // K and l insted of 1
                () -> assertFalse(CardService.isCardNumber("675931078456l226"))     // l insted of 1
        );
    }
}
