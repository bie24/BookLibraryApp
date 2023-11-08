import java.util.ArrayList;
import java.util.List;

public class Colectie {
    String numeColectie;
    List<Carte> carti = new ArrayList<>();

    public Colectie(String numeColectie, List<Carte> carti) {
        this.numeColectie = numeColectie;
        this.carti = carti;
    }

    public String getNumeColectie() {
        return numeColectie;
    }

    public void setNumeColectie(String numeColectie) {
        this.numeColectie = numeColectie;
    }

    public List<Carte> getCarti() {
        return carti;
    }

    public void setCarti(List<Carte> carti) {
        this.carti = carti;
    }

    @Override
    public String toString() {
        return "Colectie{" +
                "numeColectie='" + numeColectie + '\'' +
                ", carti=" + carti +
                '}';
    }
}
