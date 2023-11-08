import java.util.Date;

public class Imprumut {
    private Carte carte;
    private String numeCititor;
    private Date dataImprumut;
    private Date dataReturnare;

    public Imprumut(Carte carte, String numeCititor, Date dataImprumut, Date dataReturnare) {
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

    public Date getDataImprumut() {
        return dataImprumut;
    }

    public void setDataImprumut(Date dataImprumut) {
        this.dataImprumut = dataImprumut;
    }

    public Date getDataReturnare() {
        return dataReturnare;
    }

    public void setDataReturnare(Date dataReturnare) {
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
