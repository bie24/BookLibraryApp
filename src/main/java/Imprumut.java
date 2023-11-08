import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;

public class Imprumut {
    private Carte carte;
    private String numeCititor;
    private LocalDate dataImprumut;
    private Optional<LocalDate> dataReturnare;

    public Imprumut(Carte carte, String numeCititor, LocalDate dataImprumut, LocalDate dataReturnare) {
        this.carte = carte;
        this.numeCititor = numeCititor;
        this.dataImprumut = dataImprumut;
        this.dataReturnare = Optional.empty();
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
        return dataReturnare;
    }

    public void setDataReturnare(Optional<LocalDate> dataReturnare) {
        this.dataReturnare = dataReturnare;
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
