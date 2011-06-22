package com.jellicles.laboratory.puzzles;

import org.testng.annotations.Test;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertSame;
import static org.testng.Assert.assertTrue;


public class PalindromeTest {

    private enum PalindromeCases {
        civic("civic", true), cat("cat", false), noon("noon", true), aoxomoxoa(
                "AOXOMOXOA", true), fionaghal("Fionaghal", false), riseToVoteSir(
                "Rise to vote sir", true), ableWasI(
                "Able was I ere I saw Elba", true), ableNow(
                "Able was I NOW I saw Elba", false), madam(
                "Madam in Eden, I'm Adam", true), nullCase(null, false);

        final String phrase;
        final boolean palindrome;

        PalindromeCases(String phrase, boolean palindrome) {
            this.phrase = phrase;
            this.palindrome = palindrome;
        }

        public String getPhrase() {
            return phrase;
        }

        public boolean isPalindrome() {
            return palindrome;
        }
    }

    @Test
    public void testGetPhrase() {
        String expected = "expected";
        Palindrome palindrome = new Palindrome(expected);
        assertSame(expected, palindrome.getPhrase());
    }

    @Test
    public void testIsPalindrome() {
        for (PalindromeCases p : PalindromeCases.values()) {
            Palindrome palindrome = new Palindrome(p.getPhrase());
            if (p.isPalindrome()) {
                assertTrue(palindrome.isPalindrome());
            } else {
                assertFalse(palindrome.isPalindrome());
            }
        }
    }

    @Test
    public void testIsPalindromeString() {
        for (PalindromeCases pc : PalindromeCases.values()) {
            Palindrome palindrome = new Palindrome(pc.getPhrase());
            // wrapper and underlying call must have same result
            boolean result = Palindrome.isPalindrome(pc.getPhrase())
                    && palindrome.isPalindrome();
            if (pc.isPalindrome()) {
                assertTrue(result);
            } else {
                assertFalse(result);
            }
        }
    }

}
