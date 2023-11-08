package io;

import java.time.LocalDate;

public interface Keypad {
    int getInput();
    String getStringInput();
    int getYearInput(String message, int maxYear);
    LocalDate getDateInput(String message);
    long getLongInput(String message);
    boolean getBooleanInput(String message);
    String capitalizeFirstLetterOfWords(String input);
    String getCapitalizedUserInput(String message);

}
