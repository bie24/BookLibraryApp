import io.Console;
import io.Keyboard;
import io.Keypad;
import io.Screen;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class InterfataUtilizator {

    private Biblioteca biblioteca;
    private Keypad keypad;
    private BibliotecaDatabase bibliotecaDatabase;
    private ImprumutDatabase imprumutDatabase;
    private Imprumut imprumut;
    static Screen screen;

    public InterfataUtilizator() {
        biblioteca = new Biblioteca("Biblioteca ASE");
        bibliotecaDatabase = new BibliotecaDatabase(biblioteca);
        imprumutDatabase = new ImprumutDatabase(biblioteca);
        screen = new Console();
        keypad = new Keyboard(screen);
    }

    public void start() {
        screen.displayMenu();
        int optiune = keypad.getInput();
        if (!executeAction(optiune)) {
        }
    }

    private boolean executeAction(int optiune) {
        switch (optiune) {
            case 1 -> gestionareCarti();
            case 2 -> rapoarte();
            case 3 -> {
                bibliotecaDatabase.salveazaDateleLaInchidereaAplicatiei(biblioteca);
                imprumutDatabase.salveazaDateleLaInchidereaAplicatiei(biblioteca);
                screen.displayMessage("Se inchide aplicatia");
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
                case 9 -> {
                    bibliotecaDatabase.salveazaDateleLaInchidereaAplicatiei(biblioteca);
                    imprumutDatabase.salveazaDateleLaInchidereaAplicatiei(biblioteca);
                    screen.displayMessage("Se inchide aplicatia");
                    System.exit(0);
                }
                default -> screen.displayMessage("Optiune invalida. Incearca din nou");
            }
        }
    }

    private void adaugaCarte() {
        screen.displayMessage("Introduceti detaliile cartii: ");

        String titlu = keypad.getCapitalizedUserInput("Titlu");
        if(verificaDacaExistaCarteByTitlu(titlu) != null) {
            screen.displayMessage("Cartea cu titlul '" + titlu + "' exista deja in biblioteca");
            return;
        }

        String autor = keypad.getCapitalizedUserInput("Autor");
        String editura = keypad.getCapitalizedUserInput("Editura");
        int anPublicare = keypad.getYearInput("An publicare", 2023);
        String categorie = keypad.getCapitalizedUserInput("Categorie");
        long isbn = keypad.getLongInput("ISBN");
        boolean esteImprumutata = keypad.getBooleanInput("Este imprumutata? (true/false)");
        String numeColectie = keypad.getCapitalizedUserInput("Nume colectie");

        Carte carteNoua = new Carte(titlu,autor,editura,anPublicare,categorie,isbn,esteImprumutata,numeColectie);
        bibliotecaDatabase.adaugaCarte(carteNoua);

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

        String titluCautat = keypad.getCapitalizedUserInput("Introduceti titlul cartii pe care doriti sa o editati");
        Carte carteEditata = verificaDacaExistaCarteByTitlu(titluCautat);

        if(carteEditata != null) {
            screen.displayMessage("Cartea a fost gasita. Introduceti noile detalii: ");
            String titlu = keypad.getCapitalizedUserInput("Titlu");
            String autor = keypad.getCapitalizedUserInput("Autor");
            String editura = keypad.getCapitalizedUserInput("Editura");
            int anPublicare = keypad.getYearInput("An publicare",2023);
            String categorie = keypad.getCapitalizedUserInput("Categorie");
            boolean esteImprumutata = keypad.getBooleanInput("Este imprumutata? (true/false)");
            String numeColectie = keypad.getCapitalizedUserInput("Nume colectie");

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

        String titluCautat = keypad.getCapitalizedUserInput("Introduceti titlul cartii pe care doriti sa o stergeti");

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

        String numeColectie = keypad.getCapitalizedUserInput("Doriti sa listati cartile dintr-o anumita colectie sau toate colectiile? Introduceti numele colectiei sau 'toate'");
        List<Colectie> colectii = biblioteca.getColectii();
        screen.displayMessage("");

        if(numeColectie.equalsIgnoreCase("toate")) {
            for(Colectie colectie : colectii) {
                screen.displayMessage("Colectia: " + colectie.getNumeColectie());
                List<Carte> carti = colectie.getCarti();
                for(Carte carte : carti) {
                    screen.displayMessage("'" +carte.getTitlu() + "' de " + carte.getAutor());
                }
                screen.displayMessage("");
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

        String titluCautat = keypad.getCapitalizedUserInput("Introduceti titlul cartii cautate");
        boolean carteGasita = false;

        for(Colectie colectie : biblioteca.getColectii()) {
            for(Carte carte : colectie.getCarti()) {
                if(carte.getTitlu().equalsIgnoreCase(titluCautat)) {
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
            screen.displayMessage("Nu s-a gasit nicio carte care sa se potriveasca cu titlul cautat");
        }
    }

    private void imprumutaCarte() {

        String titlu = keypad.getCapitalizedUserInput("Introduceti titlul cartii pe care doriti sa o imprumutati");
        try{
            Carte carte = obtineCarteDupaTitlu(titlu);
            if (!carte.esteImprumutata()) {
                String numeStudent = keypad.getCapitalizedUserInput("Introduceti numele studentului");
                imprumutDatabase.imprumutaCarte(carte, numeStudent);
            }
            else{
                InterfataUtilizator.screen.displayMessage("Aceasta carte este deja imprumutata");
            }
        }
        catch (Exception e){
            InterfataUtilizator.screen.displayMessage("Nu s-a gasit nicio carte care sa se potriveasca!");
        }
    }

    private Carte obtineCarteDupaTitlu(String titlu) {
        for (Colectie colectie : biblioteca.getColectii()) {
            for (Carte carte : colectie.getCarti()) {
                if (carte.getTitlu().equalsIgnoreCase(titlu)) {
                    return carte;
                }
            }
        }
        return null;
    }

    private void returneazaCarte() {

        String titlu = keypad.getCapitalizedUserInput("Introduceti titlul cartii pe care doriti sa o returnati");
        boolean carteGasita = false;

        for(Colectie colectie : biblioteca.getColectii()) {
            for(Carte carte : colectie.getCarti()) {
                if(carte.getTitlu().equalsIgnoreCase(titlu)) {
                    if(carte.esteImprumutata()) {
                        String numeStudent = keypad.getCapitalizedUserInput("Introduceti numele studentului care returneaza cartea");
                        LocalDate dataReturnare = LocalDate.now();

                        boolean gasitImprumut = false;
                        for(Imprumut imprumut : carte.getListaImprumuturi()) {
                            if(imprumut.getNumeStudent().equalsIgnoreCase(numeStudent)) {
                                imprumut.setDataReturnare(dataReturnare);
                                gasitImprumut = true;
                                carte.setEsteImprumutata(false);
                                screen.displayMessage("Cartea a fost returnata cu succes!");
                                break;
                            }
                        }
                        if (gasitImprumut) {
                            for (Imprumut imprumut : carte.getListaImprumuturi()) {
                                if(imprumut.getNumeStudent().equalsIgnoreCase(numeStudent)) {
                                    screen.displayMessage(imprumut.toString());
                                }
                        }
                        } else {
                            screen.displayMessage("Nu s-a gasit un imprumut cu numele numele studentului sau cartea nu este imprumutata.");
                        }
                    }else {
                        screen.displayMessage("Aceasta carte nu este imprumutata");
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
                case 4 -> raportCartiImprumutateInPrezent();
                case 5 -> {
                    iesire = true;
                    start();
                }
                case 6 -> {
                    bibliotecaDatabase.salveazaDateleLaInchidereaAplicatiei(biblioteca);
                    imprumutDatabase.salveazaDateleLaInchidereaAplicatiei(biblioteca);
                    screen.displayMessage("Se inchide aplicatia");
                    System.exit(0);
                }
                default -> screen.displayMessage("Optiune invalida. Incearcati din nou");
            }
        }
    }

    private void raportCartiDupaAutor() {
        String autorCautat = keypad.getCapitalizedUserInput("Introduceti numele autorului cautat");
        List<Carte> cartiDupaAutor = new ArrayList<>();

        for(Colectie colectie : biblioteca.getColectii()) {
            for(Carte carte : colectie.getCarti()) {
                if(carte.getAutor().equalsIgnoreCase(autorCautat)) {
                    cartiDupaAutor.add(carte);
                }
            }
        }
        if(cartiDupaAutor.isEmpty()) {
            screen.displayMessage("Nu s-au gasit carti scride de autorul " + autorCautat);
        } else {
            screen.displayMessage("Carti scride de " + autorCautat + ":");
            for(Carte carte : cartiDupaAutor) {
                screen.displayMessage("Titlu: " + carte.getTitlu());
            }
        }
    }
    private void raportCartiDinColectie() {
        String numeColectieCautata = keypad.getCapitalizedUserInput("Introduceti numele colectiei cautate");
        Colectie colectieCautata = null;

        for(Colectie colectie : biblioteca.getColectii()) {
            if(colectie.getNumeColectie().equalsIgnoreCase(numeColectieCautata)) {
                colectieCautata = colectie;
                break;
            }
        }

        if(colectieCautata == null) {
            screen.displayMessage("Colectia cu numele " + numeColectieCautata + " nu exista");
        } else {
            List<Carte> cartiInColectie = colectieCautata.getCarti();

            if(cartiInColectie.isEmpty()) {
                screen.displayMessage("Colectia " + numeColectieCautata + " nu contine nicio carte");
            } else {
                screen.displayMessage("Carti din colectia " + numeColectieCautata + ":");
                for(Carte carte : cartiInColectie) {
                    screen.displayMessage("'" + carte.getTitlu() + "' de " + carte.getAutor());
                }
            }
        }
    }
    private void raportCartiImprumutateLaOAnumitaData(){
        LocalDate dataCautata = keypad.getDateInput("Introduceti data pentru raport (dd.MM.yyyy)");

        List<Carte> cartiImprumutateLaData = new ArrayList<>();

        for(Colectie colectie : biblioteca.getColectii()) {
            for(Carte carte : colectie.getCarti()) {
                for(Imprumut imprumut : carte.getListaImprumuturi()) {
                    if(imprumut.getDataImprumut().isEqual(dataCautata)) {
                        cartiImprumutateLaData.add(carte);
                        break;
                    }
                }
            }
        }
        if(cartiImprumutateLaData.isEmpty()) {
            screen.displayMessage("Nu s-au gasit carti imprumutate la data cautata");
        } else {
            screen.displayMessage("Carti imprumutate la data de " + dataCautata);
            for(Carte carte : cartiImprumutateLaData) {
                screen.displayMessage("'" + carte.getTitlu() + "' de " + carte.getAutor());
            }
        }
    }
    //TODO
    private void raportCartiImprumutateInPrezent() {
        for(Colectie colectie : biblioteca.getColectii()) {
            for(Carte carte : colectie.getCarti()) {
                for(Imprumut imprumut : imprumutDatabase.getImprumuturiActive()) {
                    if(imprumut.getDataReturnare().isEmpty()) {
                        screen.displayMessage(imprumut.toString());
                    }
                }
            }
        }
    }
}
