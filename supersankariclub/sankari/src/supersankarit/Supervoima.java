package supersankarit;

import java.io.*;
import static kanta.PuhNro.rand;
import fi.jyu.mit.ohj2.*;
/** Supervoima, joka huolehtii itse asioistaan.
 * @author Arttu
 * @version 27.3.2017
 *
 */
public class Supervoima implements Cloneable {
   
    private int tunnusNro;
    private int jasenNro;
    private String supervoima ="";
    private int saatu;
    private int teho;
    
    private static int seuraavaNro;
    
    /**
     * @return kenttien lukum‰‰r‰
     */
    public int getKenttia() {
        return 5;
    }
    
    /**
     * @return ensimm‰inen kentt‰, johon k‰ytt‰j‰ syˆtt‰‰ tietoa
     */
    public int ensimmainenKentta() {
        return 2;
    }
    
    /**
     * @return miss‰ kent‰ss‰ on tehot
     */
    public int tehoKentta() {
        return 5;
    }
    
    /**
     * @param j mink‰ kent‰n kysymys halutaan
     * @return valitun kent‰n kysymysteksti
     */
    public String getKysymys(int j) {
        switch (j) {
            case 0:
                return "tunnusNro";
            case 1:
                return "j‰senNro";
            case 2:
                return "supervoima";
            case 3:
                return "saatu";
            case 4:
                return "teho";
            default:
                return "????";
        }
    }
    
    /**
     * Alustetaan supervoima, ei tee mit‰‰n viel‰.
     */
    public Supervoima() {
        // ei tee mit‰‰n viel‰
    }
    
    /**
     * @param k sis‰ltˆ, joka halutaan
     * @return kent‰n sis‰ltˆ
     */
    public String anna(int k) {
        switch (k) {
            case 0:
                return "" + tunnusNro;
            case 1:
                return "" + jasenNro;
            case 2:
                return supervoima;
            case 3:
                return "" + saatu;
            case 4:
                return "" + teho;
            default:
                return "???";
        }
    }
    /**
     * @param k mones kysymys
     * @param s teksti
     * @return null
     */
    public String aseta(int k, String s) {
        String st = s.trim();
        StringBuffer sb = new StringBuffer(st);
        switch (k) {
            case 0:
                setTunnusNro(Mjonot.erota(sb, '$', getTunnusNro()));
                return null;
            case 1:
                jasenNro = Mjonot.erota(sb, '$', jasenNro);
                return null;
            case 2:
                supervoima = st;
                return null;
            case 3:
                try {
                    saatu = Mjonot.erotaEx(sb, 'ß', saatu);
                } catch (NumberFormatException ex) {
                    return "V‰‰r‰ saantivuosi! " + ex.getMessage();
                }
                return null;

            case 4:
                try {
                    teho = Mjonot.erotaEx(sb, 'ß', teho);
                } catch (NumberFormatException ex) {
                    return "V‰‰r‰ teho " + ex.getMessage();
                }
                return null;

            default:
                return "V‰‰r‰ kent‰n indeksi";
        }
    }
    
    
    @Override
    public Supervoima clone() throws CloneNotSupportedException { 
        return (Supervoima)super.clone();
    }
    
    /** alustaa tietyn j‰senen supervoiman.
     * @param jasenNro j‰senen viitenumero
     */
    public Supervoima(int jasenNro) {
        this.jasenNro = jasenNro;
    }
    
    /** T‰ytet‰‰n testiarvot supervoimalle. Arvotaan teho, ettei olisi samoja tietoja monella harrastuksella.
     * @param nro j‰senen viitenumero
     */
    public void vastaaSeitinHeitto(int nro) {
        jasenNro = nro;
        supervoima = "Seitti";
        saatu = 1955;
        teho = rand(50,9000);
    }
    
    /** Tulostetaan supervoiman tiedot
     * @param out tietovirta, johon tiedot tulostetaan.
     */
    public void tulosta(PrintStream out) {
        out.println(supervoima + " " + saatu + " " + teho);
    }
    
    /** Annetaan superovimalle seuraava numero rekisteriin.
     * @return supervoimaan uusi numero.
     * 
     * @example
     * <pre name="test">
     * Supervoima seitti1 = new Supervoima();
     * seitti1.getTunnusNro() === 0;
     * seitti1.rekisteroi();
     * Supervoima seitti2 = new Supervoima();
     * seitti2.rekisteroi();
     * int n1 = seitti1.getTunnusNro();
     * int n2 = seitti2.getTunnusNro();
     * n1 === n2-1;
     * </pre>
     */    
    public int rekisteroi() {
        tunnusNro = seuraavaNro;
        seuraavaNro++;
        return tunnusNro;
    }
    
    /** palautetaan supervoiman oma id
     * @return supervoiman id
     */
    public int getTunnusNro() {
        return tunnusNro;
    }
    
    /** palautetaan jasenen id, jolle supervoima kuuluu
     * @return jasenen id
     */
    public int getJasenNro() {
        return jasenNro;
    }
    
    /** Testiohjelma supervoimalle
     * @param args ei k‰ytˆss‰
     */
    public static void main(String[] args) {
        Supervoima sup = new Supervoima();
        sup.vastaaSeitinHeitto(2);
        sup.tulosta(System.out);
    }
    
    private void setTunnusNro(int nr) {
    	tunnusNro = nr;
    	if (tunnusNro >= seuraavaNro) seuraavaNro = tunnusNro + 1;
    }
    
    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer("");
        String erotin = "";
        for (int k = 0; k < getKenttia(); k++) {
            sb.append(erotin);
            sb.append(anna(k));
            erotin = "|";
        }
        return sb.toString();		
    }
    
    /** parsii rivin
     * @param rivi mik‰ parsitaan
     */
    public void parse(String rivi) {
    	StringBuffer sb = new StringBuffer(rivi);
        for (int k = 0; k < getKenttia(); k++)
            aseta(k, Mjonot.erota(sb, '|'));
    }

    @Override
    public boolean equals(Object jasen) {
    	if(jasen == null) return false;
    	return this.toString().equals(jasen.toString());
    }
    
    @Override
    public int hashCode() {
    	return tunnusNro;
    }
    

}
