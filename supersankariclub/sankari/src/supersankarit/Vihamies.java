package supersankarit;

import java.io.*;
import static kanta.PuhNro.rand;
import fi.jyu.mit.ohj2.*;


/** Vihamies, joka huolehtii itse asioistaan.
 * @author Arttu Nikkil‰
 * @version 27.3.2017
 *
 */
public class Vihamies implements Cloneable {

    
    private int tunnusNro;
    private int jasenNro;
    private String alias="";
    private String syy="";
    private String voittaja="";
    
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
                return "alias";
            case 3:
                return "syy";
            case 4:
                return "voittaja";
            default:
                return "????";
        }
    }
    /**
     * Alustetaan vihamies, ei tee mit‰‰n viel‰.
     */
    public Vihamies() {
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
                return alias;
            case 3:
                return "" + syy;
            case 4:
                return "" + voittaja;
            default:
                return "???";
        }
    }
    
    /** alustaa tietyn sankarin vihamiehen.
     * @param jasenNro sankarin viitenumero
     */
    public Vihamies(int jasenNro) {
        this.jasenNro = jasenNro;
    }
    
    /**
     * @param k mik‰ asetetaan
     * @param s miksi asetetaan
     * @return nullia
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
                alias = st;
                return null;
            case 3:
                try {
                    syy = Mjonot.erotaEx(sb, 'ß', syy);
                } catch (NumberFormatException ex) {
                    return "v‰‰r‰ syy " + ex.getMessage();
                }
                return null;

            case 4:
                try {
                    voittaja = Mjonot.erotaEx(sb, 'ß', voittaja);
                } catch (NumberFormatException ex) {
                    return "V‰‰r‰ voitti " + ex.getMessage();
                }
                return null;

            default:
                return "V‰‰r‰ kent‰n indeksi";
        }
    }
    
    @Override
    public Vihamies clone() throws CloneNotSupportedException { 
        return (Vihamies)super.clone();
    }
    
    /** T‰ytet‰‰n testiarvot vihamiehelle. Arvotaan syyhyn per‰‰n numero, ettei olisi samoja tietoja monella vihamiehell‰.
     * @param nro j‰senen viitenumero
     */
    public void vastaaGreenGoblin(int nro) {
        jasenNro = nro;
        alias = "Green Goblin";
        syy = "kateus" + rand(0,100);
        voittaja = "Spiderman";
    }
    
    /** Tulostetaan vihamiehen tiedot
     * @param out tietovirta, johon tiedot tulostetaan.
     */
    public void tulosta(PrintStream out) {
        out.println(alias + " " + syy + " " + voittaja);
    }
    
    /** Annetaan vihamiehelle seuraava numero rekisteriin.
     * @return vihamiehen uusi numero.
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
    
    /** palautetaan vihamiehen oma id
     * @return vihamiehen id
     */
    public int getTunnusNro() {
        return tunnusNro;
    }
    
    /** palautetaan jasenen id, jolle vihamies kuuluu
     * @return jasenen id
     */
    public int getJasenNro() {
        return jasenNro;
    }
    
    /** Testiohjelma vihamiehelle
     * @param args ei k‰ytˆss‰
     */
    public static void main(String[] args) {
        Vihamies vih = new Vihamies();
        vih.vastaaGreenGoblin(2);
        vih.tulosta(System.out);
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
