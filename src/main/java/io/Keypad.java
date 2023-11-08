package io;

public interface Keypad {
    int getInput();
    String getStringInput();
    long getLongInput();
    boolean getBooleanInput();
    String capitalizeFirstLetterOfWords(String input);
    String getCapitalizedUserInput(String message, boolean needMultipleWords);

}
