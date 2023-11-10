package io;

import exception.InvalidInputException;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Keyboard implements Keypad {
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
                screen.displayMessage("Formatul introdus este invalid. Incercati din nou!");
            }
        }

        return input;
    }

    @Override
    public String getStringInput() {
        String input;
        do {
            input = scanner.nextLine().trim();
            if (isNumeric(input)) {
                screen.displayMessage("Input invalid. Introduceti un string!");
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

    public int getYearInput(String message, int maxYear) {
        screen.displayMessage(message + ": ");
        int year = 0;

        do {
            try {
                year = getInput();
                if (isNotValidYear(year)) {
                    throw new InvalidInputException("Formatul introdus este invalid. Introduceti un an valid (1930 - 2023)!");
                }
            } catch (InvalidInputException e) {
                screen.displayMessage(e.getMessage());
            }
        } while (isNotValidYear(year));

        return year;
    }

    private boolean isNotValidYear(int year) {

        return year <= 1930 || year > 2024;
    }

    public LocalDate getDateInput(String message) {
        screen.displayMessage(message + ": ");
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate date = null;

        boolean validInput = false;
        while(!validInput) {
            try {
                String userInput = scanner.nextLine();
                date = LocalDate.parse(userInput, dateTimeFormatter);
                validInput = true;
            } catch (DateTimeException e) {
                screen.displayMessage("Formatul introdus este invalid. Folositi formatul 'dd.MM.yyyy'");
            }
        }
        return date;
    }

    public long getIsbnInput(String message) {

        long isbn = 0;
        boolean validInput = false;
       do {
            screen.displayMessage(message + ": ");
            String input = scanner.next();
            try{
                isbn = Long.parseLong(input);
                if(isValidIsbn(Long.toString(isbn))) {
                    validInput = true;
                } else {
                    screen.displayMessage("ISBN invalid. Incercati din nou!");
                }
            } catch(NumberFormatException e) {
                screen.displayMessage("Formatul introdus este invalid. Incecati din nou!");
            } catch (InvalidInputException e) {
                screen.displayMessage(e.getMessage());
            }
        } while(!validInput);

        return isbn;
    }

    private boolean isValidIsbn(String isbn) {
        if(!(isbn.length() == 10 || isbn.length() == 13)) {
            return false;
        }
        if(isbn.length() == 10) {
            int sum = 0;
            for(int i = 0; i < 9; i++) {
                sum += (10-i) * Character.getNumericValue(isbn.charAt(i));
            }
            char ultimulChar = isbn.charAt(9);
            int ultimaCifra = Character.getNumericValue(ultimulChar);

            return (sum+ultimaCifra)%11 == 0;
        }

        if(isbn.length() == 13) {
            int sum = 0;
            for(int i = 0; i < 12; i++) {
                int cifra = Character.getNumericValue(isbn.charAt(i));
                sum += (i%2 == 0) ? cifra : 3*cifra;
            }
            int verificaCifra = Character.getNumericValue(isbn.charAt(12));
            return (sum+verificaCifra)%10 == 0;
        }
        return false;
    }

    public boolean getBooleanInput(String message) {
        screen.displayMessage(message + ": ");
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
        if (input == null || input.isEmpty()) {
            return input;
        }

        String regex = "\\b(.)(.*?)\\b";
        input = Pattern.compile(regex)
                .matcher(input)
                .replaceAll(matchResult -> matchResult.group(1).toUpperCase() + matchResult.group(2));

        return input;
    }

    public String getCapitalizedUserInput(String message) {
        screen.displayMessage(message + ": ");
        return capitalizeFirstLetterOfWords(getStringInput());
    }
}
