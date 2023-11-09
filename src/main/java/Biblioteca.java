import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Biblioteca {

    private String numeBiblioteca;
    private ArrayList<Colectie> colectii = new ArrayList<>();

    public Biblioteca(String numeBiblioteca) {
        this.numeBiblioteca = numeBiblioteca;
        incarcaColectii();
    }
    private void incarcaColectii() {
        BibliotecaDatabase bibliotecaDataBase = new BibliotecaDatabase(this);
        List<Carte> carti = bibliotecaDataBase.citesteCarti();
        HashMap<String, List<Carte>> colectiiCarti = new HashMap<>();

        for(Carte carte : carti) {
            String numeColectie = carte.getNumeColectie();
            colectiiCarti.computeIfAbsent(numeColectie, k -> new ArrayList<>()).add(carte);
        }

        for (HashMap.Entry<String, List<Carte>> entry : colectiiCarti.entrySet()) {
            String numeColectie = entry.getKey();
            List<Carte> cartiInColectie = entry.getValue();
            Colectie colectie = new Colectie(numeColectie, cartiInColectie);
            colectii.add(colectie);
        }
    }

    public String getNumeBiblioteca() {
        return numeBiblioteca;
    }

    public void setNumeBiblioteca(String numeBiblioteca) {
        this.numeBiblioteca = numeBiblioteca;
    }

    public ArrayList<Colectie> getColectii() {
        return colectii;
    }

    public void setColectii(ArrayList<Colectie> colectii) {
        this.colectii = colectii;
    }

    @Override
    public String toString() {
        return "Biblioteca{" +
                "numeBiblioteca='" + numeBiblioteca + '\'' +
                ", colectii=" + colectii +
                '}';
    }
}
