package io;

public class Console implements Screen{
    @Override
    public void displayMessage(String message) {
        System.out.println(message);
    }

    @Override
    public void displayMenu() {
        System.out.println("""
                    ---Meniu---
                    1. Gestionare carti
                    2. Rapoarte
                    3. Iesire
                    """);
    }

    @Override
    public void displayMenuBooks() {
        System.out.println("""
                ---Gestionare Carti---
                1. Adauga carte
                2. Editeaza carte
                3. Sterge carte
                4. Afiseaza toate cartile
                5. Cauta carte
                6. Imprumuta carte
                7. Returneaza carte
                8. Inapoi
                9. Iesire
                """);
    }

    @Override
    public void displayMenuReports() {
        System.out.println("""
                ---Rapoarte---
                1. Carti dupa autor
                2. Carti din colectie
                3. Carti imprumutate la o anumita data
                4. Inapoi
                5. Iesire
                """);
    }

}
