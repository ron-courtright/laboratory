package com.jellicles.laboratory.puzzles;

import java.util.logging.Logger;

public class Palindrome {

    private static Logger logger = Logger.getAnonymousLogger();

    private String phrase;

    public Palindrome(String phrase) {
        this.phrase = phrase;
    }

    /**
     * Retrieve the potential palindrome.
     * 
     * @return String
     */
    public String getPhrase() {
        return phrase;
    }

    public boolean isPalindrome() {
        if (phrase == null) {
            logger.info("Attempt to evaluate null string.");
            return false;
        }
        logger.info("phrase is " + phrase);
        // remove case distinctions
        phrase = phrase.toLowerCase();
        // ignore (i.e., remove) non-alphanumeric characters & whitespace
        phrase = phrase.replaceAll("[^a-zA-Z0-9]", "");
        StringBuilder buffer = new StringBuilder(phrase);
        logger.info("phrase is now " + buffer.toString());
        boolean result = false;
        // convenient to set a maximum index
        final int maxIndex = buffer.length() - 1;
        // stop test at or near the middle of the word
        final int breakpoint = maxIndex / 2;
        for (int i = 0; i <= breakpoint; i++) {
            result = buffer.charAt(i) == buffer.charAt(maxIndex - i);
            if (!result) {
                logger.info(phrase
                        + " is not a palindrome.  Comparison broke at index "
                        + i + " and " + (maxIndex - i));
                break;
            }
        }
        return result;
    }

    public static boolean isPalindrome(String phrase) {
        Palindrome palindrome = new Palindrome(phrase);
        return palindrome.isPalindrome();
    }

}
