import java.io.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import java.time.format.DateTimeFormatter;
import java.util.Optional;


public class ImprumutDatabase {
    private String filePath = "src/main/resources/imprumuturi.txt";
    private Imprumut imprumut;
    private Biblioteca biblioteca;
    private List<Imprumut> imprumuturi = new ArrayList<>();
    private HashMap<String, Imprumut> imprumuturiHashMap = new HashMap<>();

    public ImprumutDatabase(Biblioteca biblioteca) {
        this.biblioteca = biblioteca;
        citesteImprumuturi();
        initializareHashMap();
    }

    private void initializareHashMap() {
        for(Imprumut imprumut : imprumuturi) {
            String numeCititor= imprumut.getNumeCititor();
            imprumuturiHashMap.put(numeCititor, imprumut);
        }
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
                    String numeCititor = dateImprumut[1];
                    LocalDate dataImprumut = LocalDate.parse(dateImprumut[2].trim(), formatter);
                    LocalDate dataReturnare = dateImprumut[3].equalsIgnoreCase("null") ? null : LocalDate.parse(dateImprumut[3].trim(), formatter);
                    Carte carte = obtineCarteDupaTitlu(titluCarte);

                    Imprumut imprumut = new Imprumut(carte, numeCititor, dataImprumut, dataReturnare);
                    imprumuturi.add(imprumut);

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
    public void adaugaImprumut(Imprumut imprumut) {
        String numeCititor = imprumut.getNumeCititor();
        imprumuturiHashMap.put(numeCititor,imprumut);
        getListaImprumuturi().add(imprumut);
        salveazaDateleLaInchidereaAplicatiei(imprumut);
    }

    public List<Imprumut> getListaImprumuturi() {
        return imprumuturi;
    }


    public void salveazaDateleLaInchidereaAplicatiei(Imprumut imprumut) {
        if(imprumut == null) {
            return;
        }
        if (imprumuturi.isEmpty()) {
            citesteImprumuturi();
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            for(Imprumut imprumutExistent : imprumuturi) {
                    imprumuturi.add(imprumutExistent);
                    writer.write(imprumutToDetalii(imprumut));
                    writer.newLine();
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String imprumutToDetalii(Imprumut imprumut) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

        String dataImprumutStr = imprumut.getDataImprumut().format(formatter);
        String dataReturnareStr = imprumut.getDataReturnare().map(date -> date.format(formatter)).orElse("null");

        return String.join(",",
                imprumut.getCarte().getTitlu(),
                imprumut.getNumeCititor(),
                dataImprumutStr,
                dataReturnareStr);
    }

    public boolean returneazaCarte(Carte carte, String numeCititor) {
//        boolean gasitImprumut = false;
//
//        for(Imprumut imprumut : imprumuturi) {
//            if(imprumut.getCarte().equals(carte) && imprumut.getNumeCititor().equalsIgnoreCase(numeCititor) && !imprumut.getDataReturnare().isPresent()) {
//                imprumut.setDataReturnare(Optional.of(LocalDate.now()));
//                gasitImprumut = true;
//                carte.setEsteImprumutata(false);
//                carte.returneazaCarteImprumutata(imprumut);
//                break;
//            }
//        }
//        return gasitImprumut;
        Imprumut imprumut = imprumuturiHashMap.get(numeCititor);
        if(imprumut != null && imprumut.getCarte().equals(carte) && !imprumut.getDataReturnare().isPresent()) {
            imprumut.setDataReturnare(Optional.of(LocalDate.now()));
            carte.setEsteImprumutata(false);
            carte.returneazaCarteImprumutata(imprumut);
            return true;
        }
        return false;
    }
}
