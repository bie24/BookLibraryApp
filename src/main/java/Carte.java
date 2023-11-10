import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Clasa Carte reprezinta o carte in cadrul unei biblioteci, avand diverse atribute
 */
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

    /**
     * Constructor pentru obiectul Carte
     *
     * @param titlu              Titlul cartii
     * @param autor              Numele autorului
     * @param editura            Numele editurii
     * @param anPublicare        Anul publicarii
     * @param categorie          Categorie/genul
     * @param isbn               Codul ISBN
     * @param esteImprumutata    Starea de imprumut
     * @param numeColectie       Numele colectiei din care face parte
     */

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

    public int getAnPublicare() {
        return anPublicare;
    }

    public void setAnPublicare(int anPublicare) {
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

    /**
     * Metoda pentru adaugarea unui imprumut la lista de imprumuturi a cartii
     * @param imprumut  Obiectul imprumut care va fi adaugat
     */
    public void adaugaImprumut(Imprumut imprumut) {
        listaImprumuturi.add(imprumut);
        if(imprumut.getDataReturnare().equals(null)) {
            esteImprumutata = true;
        }
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
