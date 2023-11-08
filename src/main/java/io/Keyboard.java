package io;

import exception.InvalidInputException;

import java.util.Scanner;
import java.util.regex.Pattern;

public class Keyboard implements Keypad{
    private final Scanner scanner = new Scanner(System.in);
    private final Screen screen;

    public Keyboard(Screen screen) {
        this.screen = screen;
    }

    @Override
    public int getInput() {
        var input = Integer.MIN_VALUE;

        while (input == Integer.MIN_VALUE) {
            try {
                var inputString = scanner.next();
                input = Integer.parseInt(inputString);
            } catch (NumberFormatException e) {
                screen.displayMessage("Invalid input format. Try again!");
            }
        }

        return input;
    }

    @Override
    public String getStringInput() {
        String input;
        do {
            input = scanner.nextLine().trim();
            if(isNumeric(input)) {
                screen.displayMessage("Invalid input. Please enter a text string");
            }
        } while (input.isEmpty());

        return input;
    }
    private boolean isNumeric(String string) {
        try {
            Integer.parseInt(string);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public long getLongInput() {
        var input = Long.MIN_VALUE;

        while (input == Long.MIN_VALUE) {
            try {
                var inputString = scanner.next();
                input = Integer.parseInt(inputString);
            } catch (NumberFormatException e) {
                screen.displayMessage("Invalid input format. Try again!");
            }
        }

        return input;
    }

    @Override
    public boolean getBooleanInput() {
        while (true) {
            String input = getStringInput();
            if (input.equalsIgnoreCase("true")) {
                return true;
            } else if (input.equalsIgnoreCase("false")) {
                return false;
            } else {
                screen.displayMessage("Introduceți 'true' sau 'false'. Reîncercați: ");
            }
        }
    }
    public String capitalizeFirstLetterOfWords(String input) {
        if(input == null || input.isEmpty()) {
            return input;
        }

        String regex = "\\b(.)(.*?)\\b";
        input = Pattern.compile(regex)
                .matcher(input)
                .replaceAll(matchResult -> matchResult.group(1).toUpperCase() + matchResult.group(2));

        return input;
    }
    public String getCapitalizedUserInput(String message, boolean needMultipleWords) {
        screen.displayMessage(message + ": ");

        String input = "";
        boolean isValidInput = false;

        while(!isValidInput) {
            try {
                input = capitalizeFirstLetterOfWords(getStringInput());
                String[] words = input.split("\\s+");

                if (!needMultipleWords || (needMultipleWords && words.length >= 2)) {
                    isValidInput = true;
                } else {
                    throw new InvalidInputException("The name must contain at least two words. Please try again!");
                }
            } catch(InvalidInputException e) {
                screen.displayMessage(e.getMessage());
            }
        }

        return input;
    }
}
