package supersankarit;

import java.io.OutputStream;
import java.io.PrintStream;
import kanta.PuhNro;
import fi.jyu.mit.ohj2.*;
/**
 * Kerhon jäsen, joka osaa huolehtia itse tiedoistaan.
 * 
 * @author Arttu Nikkilä
 * @version 14.3.2017
 *
 */
public class Jasen implements Cloneable {
    
    private int tunnusNro;
    private String      nimi                  = "";
    private String      alias                 = "";
    private String      katuosoite            = "";
    private String      postinumero           = "";
    private String      puhelinnumero         = "";
    private int         liittymisvuosi;
    private String      lisatiedot            = "";
    
    private static int seuraavaNro    = 1;
    
    /**
     * @return kenttien määrän
     */
    public int getKenttia() {
        return 7;
    }
    
    
    /**
     * @return ekan kentän indeksi
     */
    public int ekaKentta() { 
        return 1;
    }
    
    /**
     * alustetaan kaikki tyhjäksi ja nollaksi
     */
    public Jasen() {
        //
    }
    
    /**
     * @return nimen
     */
    public String getNimi() {
        return nimi;
    }
    
    /**
     * @return tunnusnumeron
     */
    public int getTunnusNro() {
        return tunnusNro;
    }
    
    /**
     * @return liittymisvuoden
     */
    public int getVuosi() {
        return liittymisvuosi;
    }
    
    /** Täytetään testiarvot supersankarille. Puhelinnumeron kolme vikaa numeroa randomilla, että nähdään ettei jokainen jäsen ole samanlainen.
     * 
     */
    public void vastaaPeterParker() {
          nimi = "Parker Peter ";
          alias = "Spiderman";
          katuosoite = "Web street 6";
          puhelinnumero = "0400 431 " + PuhNro.rand(100, 999);
          liittymisvuosi = 1996;
          lisatiedot = "Hyppii rakennuksissa seitin avulla";
      }
    
    /**
     * @param k monenko kentät tiedot palautetaan
     * @return sisältö merkkeinä
     */
    public String anna(int k) { 
          switch ( k ) { 
          case 0: return "" + tunnusNro; 
          case 1: return "" + nimi; 
          case 2: return "" + alias; 
          case 3: return "" + katuosoite; 
          case 4: return "" + postinumero; 
          case 5: return "" + puhelinnumero; 
          case 6: return "" + liittymisvuosi; 
          case 7: return "" + lisatiedot;  
          default: return "Virhe"; 
          } 
      } 
    
    /**
     * @param k monesko asetetaan
     * @param jono miksi asetetaan
     * @return null
     */
    public String aseta(int k, String jono) {
        String tjono = jono.trim();
        StringBuffer sb = new StringBuffer(tjono);
        switch ( k ) {
        case 0:
            setTunnusNro(Mjonot.erota(sb, '§', getTunnusNro()));
            return null;
        case 1:
            nimi = tjono;
            return null;
        case 2:
        alias = tjono;
            return null;
        case 3:
            katuosoite = tjono;
            return null;
        case 4:
            postinumero = tjono;
            return null;
        case 5:
            puhelinnumero = tjono;
            return null;
        case 6:
            try {
                liittymisvuosi = Mjonot.erotaEx(sb, '§', liittymisvuosi);
            } catch ( NumberFormatException ex ) {
                return "Liittymisvuosi väärin " + ex.getMessage();
            }
            return null;
        case 7:
            lisatiedot = Mjonot.erota(sb, '§', lisatiedot);
            return null;
        default:
            return "Virhe";
        }
    }
    
    /**
     * @param k palauttaa k:n verran kysymyksiä
     * @return k:nnen kentän kysymys
     */
    public String getKysymys(int k) {
        switch ( k ) {
        case 0: return "Tunnusnumero";
        case 1: return "nimi";
        case 2: return "alias";
        case 3: return "katuosoite";
        case 4: return "postinumero";
        case 5: return "puhelinnumero";
        case 6: return "liittymisvuosi";
        case 7: return "lisätietoja";
        default: return "Virhe";
        }
    }
    
    /**
     * Tulostetaan henkilön tietovirta
     * @param out tietovirta johon tulostetaan
     */
      public void tulosta(PrintStream out) {
          out.println(String.format("%03d", tunnusNro, 3) + "  " + nimi + "  "
                  + alias);
          out.println("  " + katuosoite + "  " + postinumero);
          out.println("  puhelinnumero: " + puhelinnumero);
          out.print("  Liittynyt " + liittymisvuosi + ".");
          out.println("  " + lisatiedot);
      }
      
      /**
       * Tulostetaan henkilön tiedot
       * @param os tietovirta johon tulostetaan
       */
      public void tulosta(OutputStream os) {
          tulosta(new PrintStream(os));
      }
      

      /**
       * Antaa jäsenelle seuraavan rekisterinumeron.
       * @return jäsenen uusi tunnusNro
       * @example
       * <pre name="test">
       *   Jasen peter1 = new Jasen();
       *   peter1.getTunnusNro() === 0;
       *   peter1.rekisteroi();
       *   Jasen peter2 = new Jasen();
       *   peter2.rekisteroi();
       *   int n1 = peter1.getTunnusNro();
       *   int n2 = peter2.getTunnusNro();
       *   n1 === n2-1;
       * </pre>
       */
      public int rekisteroi() {
          tunnusNro = seuraavaNro;
          seuraavaNro++;
          return tunnusNro;
      }
    
    /**
     * Testiohjelma Jasen-luokalle
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        Jasen peter = new Jasen();
        Jasen peter2 = new Jasen();
        
        peter.rekisteroi();
        peter2.rekisteroi();
        
        peter.vastaaPeterParker();
        peter.tulosta(System.out);
        
        peter2.vastaaPeterParker();
        peter2.tulosta(System.out);
        
        peter2.vastaaPeterParker();
        peter2.tulosta(System.out);
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
     * @param rivi tiedoston rivi, joka pitää parsea
     */
    public void parse(String rivi) {
        StringBuffer sb = new StringBuffer(rivi);
        for (int k = 0; k < getKenttia(); k++)
          aseta(k, Mjonot.erota(sb, '|'));
    }
    
    @Override
    public Jasen clone() throws CloneNotSupportedException {
        Jasen uusi;
        uusi = (Jasen) super.clone();
        return uusi;
    }
    
    /**
     * @param jasen jota verrataan
     * @return false jos null, muuten true
     */
    public boolean equals(Jasen jasen) {
        if ( jasen == null ) return false;
        for (int k = 0; k < getKenttia(); k++)
            if ( !anna(k).equals(jasen.anna(k)) ) return false;
        return true;
    }

    @Override
    public boolean equals(Object jasen) {
        if ( jasen instanceof Jasen ) return equals((Jasen)jasen);
        return false;
    }
    
    @Override
    public int hashCode() {
    	return tunnusNro;
    }
}
