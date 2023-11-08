import io.Console;
import io.Keyboard;
import io.Keypad;
import io.Screen;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class InterfataUtilizator {

    private Biblioteca biblioteca;
    private Screen screen;
    private Keypad keypad;
    private BibliotecaDatabase bibliotecaDataBase;

    public InterfataUtilizator() {
        biblioteca = new Biblioteca("Biblioteca ASE");
        bibliotecaDataBase = new BibliotecaDatabase(biblioteca);
        screen = new Console();
        keypad = new Keyboard(screen);
    }



    public void start() {
//        while(true) {
        screen.displayMenu();
        int optiune = keypad.getInput();
        if (!executeAction(optiune)) {
            return;
        }
//        }
    }

    private boolean executeAction(int optiune) {
        switch (optiune) {
            case 1 -> gestionareCarti();
            case 2 -> rapoarte();
            case 3 -> {
                bibliotecaDataBase.salveazaDateleLaInchidereaAplicatiei(biblioteca);
                screen.displayMessage("Iesire din aplicatie");
                return false;
            }
            default -> screen.displayMessage("Optiune invalida. Alege alta optiune");
        }
        return true;
    }
    private void gestionareCarti(){
        boolean iesire = false;
        while(!iesire) {
            screen.displayMenuBooks();
            int optiune = keypad.getInput();

            switch (optiune) {
                case 1 -> adaugaCarte();
                case 2 -> editeazaCarte();
                case 3 -> stergeCarte();
                case 4 -> listaCarti();
                case 5 -> cautaCarte();
                case 6 -> imprumutaCarte();
                case 7 -> returneazaCarte();
                case 8 -> {
                    iesire = true;
                    start();
                }
                default -> screen.displayMessage("Optiune invalida. Incearca din nou");
            }
        }
    }

    private void adaugaCarte() {
        screen.displayMessage("Introduceti detaliile cartii: ");

        String titlu = keypad.getCapitalizedUserInput("Titlu", false);
        if(verificaDacaExistaCarteByTitlu(titlu) != null) {
            screen.displayMessage("Cartea cu titlul '" + titlu + "' exista deja in biblioteca");
            return;
        }

        String autor = keypad.getCapitalizedUserInput("Autor", false);

        String editura = keypad.getCapitalizedUserInput("Editura", false);

        screen.displayMessage("An Publicare: ");
        int anPublicare = keypad.getInput();

        String categorie = keypad.getCapitalizedUserInput("Categorie", false);

        screen.displayMessage("ISBN: ");
        long isbn = keypad.getLongInput();

        screen.displayMessage("Este imprumutata? (true/false): ");
        boolean esteImprumutata = keypad.getBooleanInput();

        String numeColectie = keypad.getCapitalizedUserInput("Nume colectie", false);

        Carte carteNoua = new Carte(titlu,autor,editura,anPublicare,categorie,isbn,esteImprumutata,numeColectie);
        bibliotecaDataBase.adaugaCarte(carteNoua);



        boolean colectieExista = false;
        for(Colectie colectie : biblioteca.getColectii()) {
            if(colectie.getNumeColectie().equalsIgnoreCase(numeColectie)) {
                colectie.getCarti().add(carteNoua);
                screen.displayMessage("Carte adaugata cu succes in colectia " + numeColectie);
                colectieExista = true;
                break;
            }
        }
        if(!colectieExista) {
            Colectie colectieNoua = new Colectie(numeColectie, new ArrayList<>());
            colectieNoua.getCarti().add(carteNoua);
            biblioteca.getColectii().add(colectieNoua);

            screen.displayMessage("Colectia " + numeColectie + " a fost creata si cartea a fost adaugata cu succes");
        }
    }
    private Carte verificaDacaExistaCarteByTitlu(String titlu) {
        for(Colectie colectie : biblioteca.getColectii()) {
            for(Carte carte : colectie.getCarti()) {
                if(carte.getTitlu().equalsIgnoreCase(titlu)) {
                    return carte;
                }
            }
        }
        return null;
    }

    private void editeazaCarte() {
        screen.displayMessage("Introduceti titlul cartii pe care doriti sa o editati: ");
        String titluCautat = keypad.getStringInput();
        Carte carteEditata = verificaDacaExistaCarteByTitlu(titluCautat);

        if(carteEditata != null) {

            screen.displayMessage("Cartea a fost gasita. Introduceti noile detalii: ");
            String titlu = keypad.getCapitalizedUserInput("Titlu", false);
            String autor = keypad.getCapitalizedUserInput("Autor", false);
            String editura = keypad.getCapitalizedUserInput("Editura", false);

            screen.displayMessage("An Publicare: ");
            int anPublicare = keypad.getInput();

            String categorie = keypad.getCapitalizedUserInput("Categorie", false);

            screen.displayMessage("Este imprumutata? (true/false): ");
            boolean esteImprumutata = keypad.getBooleanInput();

            String numeColectie = keypad.getCapitalizedUserInput("Nume colectie", false);

            carteEditata.setTitlu(titlu);
            carteEditata.setAutor(autor);
            carteEditata.setEditura(editura);
            carteEditata.setanPublicare(anPublicare);
            carteEditata.setCategorie(categorie);
            carteEditata.setEsteImprumutata(esteImprumutata);
            carteEditata.setNumeColectie(numeColectie);

            boolean colectieGasita = false;

            for(Colectie colectie : biblioteca.getColectii()) {
                if(colectie.getNumeColectie().equalsIgnoreCase(numeColectie)) {
                    colectieGasita = true;
                    for(Carte c : colectie.getCarti()){
                        if(c.getIsbn() == carteEditata.getIsbn()) {
                            c.duplicareCarte(carteEditata);
                            screen.displayMessage("Carte editata cu succes in colectia " + numeColectie);
                        }
                    }

                    return;
                }
            }

            if(!colectieGasita) {
                List<Carte> listaCarti = new ArrayList<>();
                listaCarti.add(carteEditata);
                Colectie colectie = new Colectie(numeColectie,listaCarti);
                biblioteca.getColectii().add(colectie);
            }



            screen.displayMessage("Carte editata cu succes.");
        }
        if(carteEditata == null) {
            screen.displayMessage("Cartea cu titlul '" + titluCautat + "' nu a fost gasita");
        }

    }


    private void stergeCarte() {
        screen.displayMessage("Introduceti titlul cartii pe care doriti sa o stergeti: ");
        String titluCautat = keypad.getStringInput();

        boolean carteGasita = false;
        for (Colectie colectie : biblioteca.getColectii()) {
            for (Carte carte : colectie.getCarti()) {
                if (carte.getTitlu().equalsIgnoreCase(titluCautat)) {
                    carteGasita = true;
                    colectie.getCarti().remove(carte);
                    screen.displayMessage("Cartea cu titlul " + titluCautat + " a fost ștearsă.");
                    break;
                }
            }
            if (carteGasita) {
                break;
            }
        }

        if (!carteGasita) {
            screen.displayMessage("Cartea cu titlul " + titluCautat + " nu a fost găsită în bibliotecă.");
        }
    }
    private void listaCarti() {

        String numeColectie = keypad.getCapitalizedUserInput("Doriti sa listati cartile dintr-o anumita colectie sau toate colectiile? Introduceti numele colectiei sau 'toate'", false);
        List<Colectie> colectii = biblioteca.getColectii();

        if(numeColectie.equalsIgnoreCase("toate")) {
            for(Colectie colectie : colectii) {
                System.out.println();
                screen.displayMessage("Colectia: " + colectie.getNumeColectie());
                List<Carte> carti = colectie.getCarti();
                for(Carte carte : carti) {
                    screen.displayMessage("'" +carte.getTitlu() + "' de " + carte.getAutor());
                }

            }
        } else {
            boolean colectieGasita = false;
            for(Colectie colectie : colectii) {
                if(colectie.getNumeColectie().equalsIgnoreCase(numeColectie)) {
                    colectieGasita = true;
                    screen.displayMessage("Colectia: " + colectie.getNumeColectie());
                    for(Carte carte : colectie.getCarti()) {
                        screen.displayMessage("Titlu: " + carte.getTitlu());
                    }
                    break;
                }
            }
            if(!colectieGasita) {
                screen.displayMessage("Colectia cu numele " + numeColectie + " nu a fost gasita");
            }
        }
    }
    private void cautaCarte() {
        screen.displayMessage("Introduceti titlul cartii cautate: ");
        String criteriu = keypad.getStringInput();
        boolean carteGasita = false;

        for(Colectie colectie : biblioteca.getColectii()) {
            for(Carte carte : colectie.getCarti()) {
                if(carte.getTitlu().equalsIgnoreCase(criteriu)) {
                    carteGasita = true;
                    screen.displayMessage("Carte gasita: ");
                    screen.displayMessage("Titlu: " + carte.getTitlu());
                    screen.displayMessage("Autor: " + carte.getAutor());
                    screen.displayMessage("Editura: " + carte.getEditura());
                    screen.displayMessage("An publicare: " + carte.getanPublicare());
                    screen.displayMessage("Categorie: " + carte.getCategorie());
                    screen.displayMessage("ISBN: " +carte.getIsbn());
                    screen.displayMessage("Colectie: " +carte.getNumeColectie());
                    if(carte.esteImprumutata()) {
                        screen.displayMessage("Cartea nu este disponibila");
                    } else {
                        screen.displayMessage("Cartea este disponibila");
                    }
                    screen.displayMessage("");
                }
            }
        }
        if(!carteGasita) {
            screen.displayMessage("Nu s-a gasit nicio carte care sa se potriveasca cu criteriul de cautare");
        }
    }

    private void imprumutaCarte() {
        screen.displayMessage("Introduceti titlul cartii pe care doriti sa o imprumutati: ");
        String titlu = keypad.getStringInput();
        boolean carteGasita = false;

        for(Colectie colectie : biblioteca.getColectii()) {
            for(Carte carte : colectie.getCarti()) {
                if(carte.getTitlu().equalsIgnoreCase(titlu)) {
                    if(!carte.esteImprumutata()) {
                        screen.displayMessage("Introduceti numele studentului: ");
                        String numeCititor = keypad.getStringInput();

                        Date dataImprumut = new Date();
                        Date dataReturnare = null;
                        Imprumut imprumut = new Imprumut(carte, numeCititor, dataImprumut, dataReturnare);
                        carte.adaugaImprumut(imprumut);
                        carte.setEsteImprumutata(true);

                        screen.displayMessage("Cartea a fost imprumutata cu succes.");
                    } else {
                        screen.displayMessage("Aceasta carte este deja imprumutata");
                    }
                    carteGasita = true;
                    break;
                }
            }
        }
        if(!carteGasita) {
            screen.displayMessage("Nu s-a gasit nicio carte care sa se potriveasca!");
        }
    }

    private void returneazaCarte() {
        screen.displayMessage("Introduceti titlul cartii pe care doriti sa o returnati: ");
        String titlu = keypad.getStringInput();
        boolean carteGasita = false;

        for(Colectie colectie : biblioteca.getColectii()) {
            for(Carte carte : colectie.getCarti()) {
                if(carte.getTitlu().equalsIgnoreCase(titlu)) {
                    if(carte.esteImprumutata()) {
                        screen.displayMessage("Detalii despre imprumuturile cartii: " + carte.getTitlu() + ":");
                        for(Imprumut imprumut : carte.getListaImprumuturi()) {
                            screen.displayMessage(imprumut.toString());
                        }

                        screen.displayMessage("Introduceti numele studentului care returneaza cartea: ");
                        String numeCititor = keypad.getStringInput();
                        Date dataReturnare = new Date();

                        boolean gasitImprumut = false;
                        for(Imprumut imprumut : carte.getListaImprumuturi()) {
                            if(imprumut.getNumeCititor().equalsIgnoreCase(numeCititor)) {
                                imprumut.setDataReturnare(dataReturnare);
                                gasitImprumut = true;
                                carte.setEsteImprumutata(false);
                                screen.displayMessage("Cartea a fost returnata cu succes.");
                                break;
                            }
                        }
                        if(!gasitImprumut) {
                            screen.displayMessage("Nu s-a gasit un imprumut cu numele studentului.");
                        }
                    } else {
                        screen.displayMessage("Aceasta carte nu este imprumutata.");
                    }
                    carteGasita = true;
                    break;
                }
            }
        }
        if(!carteGasita) {
            screen.displayMessage("Nu s-a gasit nicio carte care sa se potriveasca!");
        }
    }



    private void rapoarte(){
        boolean iesire = false;
        while(!iesire) {
            screen.displayMenuReports();
            int optiune = keypad.getInput();

            switch (optiune) {
                case 1 -> raportCartiDupaAutor();
                case 2 -> raportCartiDinColectie();
                case 3 -> raportCartiImprumutateLaOAnumitaData();
                case 4 -> {
                    iesire = true;
                    start();
                }
                default -> screen.displayMessage("Optiune invalida. Incearca din nou");
            }
        }
    }

    private void raportCartiDupaAutor() {}
    private void raportCartiDinColectie() {}
    private void raportCartiImprumutateLaOAnumitaData(){}
}
