import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;

public class Imprumut {
    private Carte carte;
    private String numeStudent;
    private LocalDate dataImprumut;
    private LocalDate dataReturnare;

    public Imprumut(Carte carte, String numeStudent, LocalDate dataImprumut) {
        this.carte = carte;
        this.numeStudent = numeStudent;
        this.dataImprumut = dataImprumut;
    }

    public Imprumut(Carte carte, String numeStudent, LocalDate dataImprumut, LocalDate dataReturnare) {
        this.carte = carte;
        this.numeStudent = numeStudent;
        this.dataImprumut = dataImprumut;
        this.dataReturnare = dataReturnare;
    }

    public Carte getCarte() {
        return carte;
    }

    public void setCarte(Carte carte) {
        this.carte = carte;
    }

    public String getNumeStudent() {
        return numeStudent;
    }

    public void setNumeStudent(String numeStudent) {
        this.numeStudent = numeStudent;
    }

    public LocalDate getDataImprumut() {
        return dataImprumut;
    }

    public void setDataImprumut(LocalDate dataImprumut) {
        this.dataImprumut = dataImprumut;
    }

    public Optional<LocalDate> getDataReturnare() {
        return Optional.ofNullable(dataReturnare);
    }

    public void setDataReturnare(LocalDate dataReturnare) {
        this.dataReturnare = dataReturnare;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Imprumut imprumut = (Imprumut) o;
        return Objects.equals(carte, imprumut.carte) && Objects.equals(numeStudent, imprumut.numeStudent) && Objects.equals(dataImprumut, imprumut.dataImprumut);
    }

    @Override
    public int hashCode() {
        return Objects.hash(carte, numeStudent, dataImprumut);
    }

    @Override
    public String toString() {
        return "Informatii despre imprumut: " +
                " Cartea '" + carte.getTitlu() +
                "' a fost imprumutata de " + numeStudent +
                " la data de " + dataImprumut +
                " pana la data de " + dataReturnare;
    }
}
