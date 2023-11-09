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
                    int anPublicare = Integer.parseInt(dateCarte[3].trim());
                    String categorie = dateCarte[4];
                    long isbn = Long.parseLong(dateCarte[5].trim());
                    boolean esteImprumutata = Boolean.parseBoolean(dateCarte[6]);
                    String numeColectie = dateCarte[7];
                    Carte carte = new Carte(titlu,autor,editura,anPublicare,categorie,isbn,esteImprumutata,numeColectie);
                    carti.add(carte);

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return carti;
    }

    public void adaugaCarte(Carte carte) {
        carti.add(carte);
    }

    private String detaliiCarte(Carte carte) {
        return String.join(",",
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
                    writer.write(detaliiCarte(carte));
                    writer.newLine();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
