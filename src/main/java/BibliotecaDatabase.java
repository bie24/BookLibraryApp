import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class BibliotecaDatabase {
    private String filePath = "src/main/resources/carti.txt";
    private Biblioteca biblioteca;
    private List<Carte> carti = new ArrayList<>();

    public BibliotecaDatabase(Biblioteca biblioteca) {
        this.biblioteca = biblioteca;
        citesteCarti();
    }


    public List<Carte> citesteCarti() {
        List<Carte> carti = new ArrayList<>();
        try(BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String linie;
            while((linie = reader.readLine()) != null) {
                String[] dateCarte = linie.split(",");
                if(dateCarte.length == 8) {
                    String titlu = dateCarte[0];
                    String autor = dateCarte[1];
                    String editura = dateCarte[2];
                    int anPublicatie = Integer.parseInt(dateCarte[3].trim());
                    String categorie = dateCarte[4];
                    long isbn = Long.parseLong(dateCarte[5].trim());
                    boolean esteImprumutata = Boolean.parseBoolean(dateCarte[6]);
                    String numeColectie = dateCarte[7];
                    Carte carte = new Carte(titlu,autor,editura,anPublicatie,categorie,isbn,esteImprumutata,numeColectie);
                    carti.add(carte);

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return carti;
    }

    public void adaugaCarte(Carte carte) {
//        String detaliiCarte = carteToDetalii(carte);
//
//        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true)) ) {
//            writer.write(detaliiCarte);
//            writer.newLine();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        carti.add(carte);
    }
    public void actualizeazaCarte(Carte carte) {
        for (Carte existingCarte : carti) {
            if (existingCarte.getTitlu().equals(carte.getTitlu())) {
                existingCarte.setAutor(carte.getAutor());
                existingCarte.setEditura(carte.getEditura());
                existingCarte.setanPublicare(carte.getanPublicare());
                existingCarte.setCategorie(carte.getCategorie());
                existingCarte.setIsbn(carte.getIsbn());
                existingCarte.setEsteImprumutata(carte.esteImprumutata());
                existingCarte.setNumeColectie(carte.getNumeColectie());
            }
        }
        salveazaDateleLaInchidereaAplicatiei(biblioteca);
    }

    public void stergeCarte(Carte carte) {
        carti.remove(carte);
        salveazaDateleLaInchidereaAplicatiei(biblioteca);
    }



//    private Carte createCarteFromDetalii(String[] detaliiCarte) {
//        String titlu = detaliiCarte[0];
//        String autor = detaliiCarte[1];
//        String editura = detaliiCarte[2];
//        int anPublicatie = Integer.parseInt(detaliiCarte[3]);
//        String categorie = detaliiCarte[4];
//        long isbn = Long.parseLong(detaliiCarte[5]);
//        boolean esteImprumutata = Boolean.parseBoolean(detaliiCarte[6]);
//        String numeColectie = detaliiCarte[7];
//
//        return new Carte(titlu, autor, editura, anPublicatie, categorie, isbn, esteImprumutata, numeColectie);
//    }

    private String carteToDetalii(Carte carte) {
        return String.join(", ",
                carte.getTitlu(),
                carte.getAutor(),
                carte.getEditura(),
                String.valueOf(carte.getanPublicare()),
                carte.getCategorie(),
                String.valueOf(carte.getIsbn()),
                String.valueOf(carte.esteImprumutata()),
                carte.getNumeColectie());
    }

    public void salveazaDateleLaInchidereaAplicatiei(Biblioteca biblioteca) {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, false))) {

            for(Colectie colectie : biblioteca.getColectii()){
                for(Carte carte : colectie.getCarti()){
                    writer.write(carteToDetalii(carte));
                    writer.newLine();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
