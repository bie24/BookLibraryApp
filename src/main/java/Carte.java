import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Carte {

    private String titlu;
    private String autor;
    private String editura;
    private int anPublicare;
    private String categorie;
    private long isbn;
    private boolean esteImprumutata;
    private String numeColectie;
    private List<Imprumut> listaImprumuturi = new ArrayList<>();

    public Carte(String titlu, String autor, String editura, int anPublicare, String categorie, long isbn, boolean esteImprumutata, String numeColectie) {
        this.titlu = titlu;
        this.autor = autor;
        this.editura = editura;
        this.anPublicare = anPublicare;
        this.categorie = categorie;
        this.isbn = isbn;
        this.esteImprumutata = esteImprumutata;
        this.numeColectie = numeColectie;
    }

    public String getTitlu() {
        return titlu;
    }

    public void setTitlu(String titlu) {
        this.titlu = titlu;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getEditura() {
        return editura;
    }

    public void setEditura(String editura) {
        this.editura = editura;
    }

    public int getanPublicare() {
        return anPublicare;
    }

    public void setanPublicare(int anPublicare) {
        this.anPublicare = anPublicare;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public long getIsbn() {
        return isbn;
    }

    public void setIsbn(long isbn) {
        this.isbn = isbn;
    }

    public boolean esteImprumutata() {
        return esteImprumutata;
    }

    public void setEsteImprumutata(boolean esteImprumutata) {
        this.esteImprumutata = esteImprumutata;
    }

    public List<Imprumut> getListaImprumuturi() {
        return listaImprumuturi;
    }

    public void setListaImprumuturi(List<Imprumut> listaImprumuturi) {
        this.listaImprumuturi = listaImprumuturi;
    }

    public String getNumeColectie() {
        return numeColectie;
    }

    public void setNumeColectie(String numeColectie) {
        this.numeColectie = numeColectie;
    }

    public void adaugaImprumut(Imprumut imprumut) {
        listaImprumuturi.add(imprumut);
        if(imprumut.getDataReturnare().equals(null)) {
            esteImprumutata = true;
        }
    }

    public String detaliiImprumut() {
        StringBuilder detalii = new StringBuilder("Imprumuturi pentru cartea '" + titlu + "':\n");
        for(Imprumut imprumut : listaImprumuturi) {
            detalii.append(imprumut.toString()).append("\n");
        }
        return detalii.toString();
    }

    public void duplicareCarte (Carte carteNoua) {
        this.titlu = carteNoua.titlu;
        this.autor =  carteNoua.autor;
        this.editura =  carteNoua.editura;
        this.anPublicare =  carteNoua.anPublicare;
        this.categorie =  carteNoua.categorie;
        this.isbn =  carteNoua.isbn;
        this.esteImprumutata =  carteNoua.esteImprumutata;
        this.numeColectie =  carteNoua.numeColectie;
        this.listaImprumuturi =  carteNoua.listaImprumuturi;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Carte carte = (Carte) o;
        return anPublicare == carte.anPublicare && isbn == carte.isbn && esteImprumutata == carte.esteImprumutata && Objects.equals(titlu, carte.titlu) && Objects.equals(autor, carte.autor) && Objects.equals(editura, carte.editura) && Objects.equals(categorie, carte.categorie) && Objects.equals(numeColectie, carte.numeColectie) && Objects.equals(listaImprumuturi, carte.listaImprumuturi);
    }


    @Override
    public String toString() {
        return "Carte{" +
                "titlu='" + titlu + '\'' +
                ", autor='" + autor + '\'' +
                ", editura='" + editura + '\'' +
                ", anPublicare=" + anPublicare +
                ", categorie='" + categorie + '\'' +
                ", isbn=" + isbn +
                ", esteImprumutata=" + esteImprumutata +
                ", numeColectie='" + numeColectie + '\'' +
                '}';
    }
}
