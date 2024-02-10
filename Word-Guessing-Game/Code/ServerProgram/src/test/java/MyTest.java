import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.DisplayName;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class MyTest {

	@Test
	void GameDataConstructorTest() {
		GameData data = new GameData();
		assertEquals(3, data.getAttempts(1));
		assertEquals(3, data.getAttempts(2));
		assertEquals(3, data.getAttempts(3));
		assertEquals("Fall", data.getCategory(1));
		assertEquals("Winter", data.getCategory(2));
		assertEquals("Summer", data.getCategory(3));
		assertEquals(false, data.isGameOver());
		assertEquals(false, data.didUserWin());
	}

	@Test
	void GameDataDecrementTest()
	{
		GameData data = new GameData();
		data.decrementCategoryAttempt(1);
		assertEquals(2, data.getAttempts(1));
		data.decrementCategoryAttempt(1);
		assertEquals(1, data.getAttempts(1));
		data.decrementCategoryAttempt(2);
		assertEquals(2, data.getAttempts(2));
		data.decrementCategoryAttempt(1);
		assertEquals(0, data.getAttempts(1));
		data.decrementCategoryAttempt(3);
		assertEquals(2, data.getAttempts(3));
		data.decrementCategoryAttempt(2);
		assertEquals(1, data.getAttempts(2));
	}

	@Test
	void GameDataCategoryWonTest()
	{
		GameData data = new GameData();
		data.setCategoryWon(0);
		data.setCategoryWon(1);
		assertEquals(false, data.getCategoryWon(2));
		assertEquals(true, data.getCategoryWon(0));
		data.setCategoryWon(2);
		assertEquals(true, data.getCategoryWon(2));
	}

	@Test
	void GameDataWonGameTest()
	{
		GameData data = new GameData();
		data.setCategoryWon(0);
		data.setCategoryWon(1);
		assertEquals(false, data.didUserWin());
		data.setCategoryWon(2);
		assertEquals(true, data.didUserWin());
	}

	@Test
	void GameDataGameOverTest()
	{
		GameData data = new GameData();
		assertEquals(false, data.isGameOver());
		data.decrementCategoryAttempt(1);
		data.decrementCategoryAttempt(1);
		assertEquals(false, data.isGameOver());
		data.decrementCategoryAttempt(1);
		assertEquals(true, data.isGameOver());
	}

	@Test
	void GuessWordConstructorTest()
	{
		GuessWord guessWord = new GuessWord("hello");
		assertEquals("hello", guessWord.getWord());
		assertEquals(6, guessWord.getGuessesRemaining());
	}
	
	@Test
	void GuessWordMakeGuessTest()
	{
		GuessWord guessWord = new GuessWord("hello");
		assertTrue(guessWord.updateGuesses('h').contains("found at index 0"));
	}

	@Test
	void GuessWordDuplicateGuessTest()
	{
		GuessWord guessWord = new GuessWord("hello");
		guessWord.updateGuesses('h');
		assertTrue(guessWord.updateGuesses('h').contains("already made"));
	}

	@Test
	void GuessWordIncorrectGuessTest()
	{
		GuessWord guessWord = new GuessWord("hello");
		assertTrue(guessWord.updateGuesses('d').contains("not in word"));
		assertEquals(5, guessWord.getGuessesRemaining());
	}

	@Test 
	void GuessWordWonGameTest()
	{
		GuessWord guessWord = new GuessWord("hello");
		assertFalse(guessWord.updateGuesses('h').contains("winning"));
		guessWord.updateGuesses('h');
		assertFalse(guessWord.updateGuesses('e').contains("winning"));
		guessWord.updateGuesses('e');
		assertFalse(guessWord.updateGuesses('l').contains("winning"));
		guessWord.updateGuesses('l');
		assertFalse(guessWord.updateGuesses('a').contains("winning"));
		guessWord.updateGuesses('a');
		assertFalse(guessWord.updateGuesses('d').contains("winning"));
		guessWord.updateGuesses('d');
		assertTrue(guessWord.getGuessesRemaining() == 4);
		assertTrue(guessWord.updateGuesses('o').get(1).contains("winning"));
	}

}
