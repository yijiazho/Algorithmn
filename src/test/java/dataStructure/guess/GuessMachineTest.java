package dataStructure.guess;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GuessMachineTest {

    private GuessMachine guessMachine;

    @BeforeEach
    public void setup() {
        guessMachine = new GuessMachine(10, 5);
    }

    @Test
    public void testGuess() {
        String secret = guessMachine.getSecret();
        int correctCount = guessMachine.guess(secret);
        assertEquals(6, correctCount);

        String wrongGuess = "abcdef";
        if (!wrongGuess.equals(secret)) {
            int wrongCount = guessMachine.guess(wrongGuess);
            assertEquals(true, wrongCount <= 5);
        }
    }

    @Test
    public void testGuessLimitExceeded() {
        String wrongGuess = "abcdef";
        for (int i = 0; i < 5; i++) {
            guessMachine.guess(wrongGuess);
        }
        assertThrows(GuessLimitException.class, () -> {
            guessMachine.guess(wrongGuess);
        });
    }

    @Test
    public void testGuessWithHint() {
        String secret = guessMachine.getSecret();
        String hint = guessMachine.guessWithHint(secret);
        assertEquals("6A0B", hint);

        String wrongGuess = "abcdef";
        if (!wrongGuess.equals(secret)) {
            String wrongHint = guessMachine.guessWithHint(wrongGuess);
            assertEquals(true, wrongHint.matches("\\dA\\dB"));
        }
    }
}
