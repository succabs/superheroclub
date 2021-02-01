package supersankarit;

import static kanta.PuhNro.rand;
import fi.jyu.mit.ohj2.*;
import java.io.*;


/** Luokka heikkoutta varten.
 * @author Arttu
 * @version 27.3.2017
 *
 */
public class Heikkous implements Cloneable {

    
    private int tunnusNro;
    private int jasenNro;
    private String heikkous="";
    private int saatu;
    private String haitallisuus="";
    
    private static int seuraavaNro;
    
    /**
     * Alustetaan heikkous, ei tee mit‰‰n viel‰.
     */
    public Heikkous() {
        // ei tee mit‰‰n viel‰
    }
    
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
                return "heikkous";
            case 3:
                return "saatu";
            case 4:
                return "haitallisuus";
            default:
                return "????";
        }
    }
    
    
    
    /** alustaa tietyn j‰senen heikkouden.
     * @param jasenNro j‰senen viitenumero
     */
    public Heikkous(int jasenNro) {
        this.jasenNro = jasenNro;
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
                return heikkous;
            case 3:
                return "" + saatu;
            case 4:
                return "" + haitallisuus;
            default:
                return "???";
        }
    }
    
    /**
     * @param k mones asetetaan
     * @param s miksi asetetaan
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
                heikkous = st;
                return null;
            case 3:
                try {
                    saatu = Mjonot.erotaEx(sb, 'ß', saatu);
                } catch (NumberFormatException ex) {
                    return "Aloitusvuosi v‰‰rin " + ex.getMessage();
                }
                return null;

            case 4:
                try {
                    haitallisuus = Mjonot.erotaEx(sb, 'ß', haitallisuus);
                } catch (NumberFormatException ex) {
                    return "Viikkotunnit v‰‰rin " + ex.getMessage();
                }
                return null;

            default:
                return "V‰‰r‰ kent‰n indeksi";
        }
    }
    
    @Override
    public Heikkous clone() throws CloneNotSupportedException { 
        return (Heikkous)super.clone();
    }
    
    
    /** T‰ytet‰‰n testiarvot heikkoudelle. Arvotaan syyhyn per‰‰n numero, ettei olisi samoja tietoja monella heikkoudella.
     * @param nro j‰senen viitenumero
     */
    public void vastaaKryptoniitti(int nro) {
        jasenNro = nro;
        heikkous = "Kryptoniitti";
        saatu = rand(1900,2000);
        haitallisuus = "olematon";
    }
    
    /** Tulostetaan heikkouden tiedot
     * @param out tietovirta, johon tiedot tulostetaan.
     */
    public void tulosta(PrintStream out) {
        out.println(heikkous + " " + saatu + " " + haitallisuus);
    }
    
    /** Annetaan heikkoudelle seuraava numero rekisteriin.
     * @return heikkouden uusi numero.
     * 
     * @example
     * <pre name="test">
     * 
     * </pre>
     */    
    public int rekisteroi() {
        tunnusNro = seuraavaNro;
        seuraavaNro++;
        return tunnusNro;
    }
    
    /** palautetaan heikkouden oma id
     * @return heikkouden id
     */
    public int getTunnusNro() {
        return tunnusNro;
    }
    
    /** palautetaan jasenen id, jolle heikkous kuuluu
     * @return jasenen id
     */
    public int getJasenNro() {
        return jasenNro;
    }
    
    /** Testiohjelma heikkoudelle
     * @param args ei k‰ytˆss‰
     */
    public static void main(String[] args) {
        Heikkous hei = new Heikkous();
        hei.vastaaKryptoniitti(2);
        hei.tulosta(System.out);
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
    
    /**
     * @param rivi joka parsetaan
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

