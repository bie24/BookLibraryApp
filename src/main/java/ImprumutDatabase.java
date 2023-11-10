import java.io.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

import java.time.format.DateTimeFormatter;


public class ImprumutDatabase {
    private String filePath = "src/main/resources/imprumuturi.txt";
    private Imprumut imprumut;
    private Biblioteca biblioteca;
    private List<Imprumut> imprumuturi = new ArrayList<>();

    private HashMap<Imprumut, Boolean> statusImprumuturiHashMap = new HashMap<>();

    public ImprumutDatabase(Biblioteca biblioteca) {
        this.biblioteca = biblioteca;
        imprumuturi = citesteImprumuturi();
    }

    public List<Imprumut> citesteImprumuturi() {

        List<Imprumut> imprumuturi = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        try(BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String linie;

            while((linie = reader.readLine()) != null) {
                String[] dateImprumut = linie.split(",");

                if(dateImprumut.length == 4) {
                    String titluCarte = dateImprumut[0];
                    String numeStudent = dateImprumut[1];
                    LocalDate dataImprumut = LocalDate.parse(dateImprumut[2].trim(), formatter);
                    LocalDate dataReturnare = dateImprumut[3].equalsIgnoreCase("null") ? null : LocalDate.parse(dateImprumut[3].trim(), formatter);

                    Carte carte = obtineCarteDupaTitlu(titluCarte);

                    Imprumut imprumut = new Imprumut(carte, numeStudent, dataImprumut, dataReturnare);
                    imprumuturi.add(imprumut);
                    carte.adaugaImprumut(imprumut);
                    statusImprumuturiHashMap.put(imprumut, true);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imprumuturi;
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


    public void imprumutaCarte(Carte carte, String numeStudent) {

        LocalDate dataImprumut = LocalDate.now();
        Imprumut imprumut = new Imprumut(carte, numeStudent, dataImprumut);
        carte.adaugaImprumut(imprumut);
        carte.setEsteImprumutata(true);
        statusImprumuturiHashMap.put(imprumut,true);
        imprumuturi.add(imprumut);
        GestiuneBiblioteca.screen.displayMessage("Cartea a fost imprumutata cu succes.");
    }

    public void salveazaDateleLaInchidereaAplicatiei(Biblioteca biblioteca) {
        if(biblioteca == null) {
            return;
        }
        if (imprumuturi.isEmpty()) {
            citesteImprumuturi();
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, false))) {
            for(Imprumut imprumutExistent : imprumuturi) {
                writer.write(detaliiImprumut(imprumutExistent));
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String detaliiImprumut(Imprumut imprumut) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

        String dataImprumutStr = imprumut.getDataImprumut().format(formatter);
        String dataReturnareStr = imprumut.getDataReturnare().map(date -> date.format(formatter)).orElse("null");

        return String.join(",",
                imprumut.getCarte().getTitlu(),
                imprumut.getNumeStudent(),
                dataImprumutStr,
                dataReturnareStr);
    }

    public void stergeImprumuturiByCarte(Carte carte) {
        Iterator<Imprumut> iterator = imprumuturi.iterator();
        while(iterator.hasNext()) {
            Imprumut imprumut = iterator.next();
            if(imprumut.getCarte().equals(carte)) {
                iterator.remove();
                statusImprumuturiHashMap.remove(imprumut);
            }
        }
    }

}
