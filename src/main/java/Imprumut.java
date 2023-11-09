import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;

public class Imprumut {
    private Carte carte;
    private String numeCititor;
    private LocalDate dataImprumut;
    private LocalDate dataReturnare;

    public Imprumut(Carte carte, String numeCititor, LocalDate dataImprumut) {
        this.carte = carte;
        this.numeCititor = numeCititor;
        this.dataImprumut = dataImprumut;
    }

    public Imprumut(Carte carte, String numeCititor, LocalDate dataImprumut, LocalDate dataReturnare) {
        this.carte = carte;
        this.numeCititor = numeCititor;
        this.dataImprumut = dataImprumut;
        this.dataReturnare = dataReturnare;
    }

    public Carte getCarte() {
        return carte;
    }

    public void setCarte(Carte carte) {
        this.carte = carte;
    }

    public String getNumeCititor() {
        return numeCititor;
    }

    public void setNumeCititor(String numeCititor) {
        this.numeCititor = numeCititor;
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
        return Objects.equals(carte, imprumut.carte) && Objects.equals(numeCititor, imprumut.numeCititor) && Objects.equals(dataImprumut, imprumut.dataImprumut);
    }

    @Override
    public int hashCode() {
        return Objects.hash(carte, numeCititor, dataImprumut);
    }

    @Override
    public String toString() {
        return "Imprumut: " +
                " Cartea '" + carte.getTitlu() +
                "' a fost imprumutata de " + numeCititor +
                " la data de " + dataImprumut +
                " pana la data de " + dataReturnare;
    }
}
