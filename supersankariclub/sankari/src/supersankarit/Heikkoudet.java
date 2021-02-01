package supersankarit;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;


/** Supersankarien heikkoudet, joka osaa mm. lisätä uuden heikkouden
 * @author Arttu Nikkilä
 * @version 27.3.2017
 */
public class Heikkoudet implements Iterable<Heikkous> {
    
    private boolean muutettu = false;
    private String tiedostonPerusNimi = "heikkoudet";
    
    /**
     * Taulukko heikkouksista
     */
    private final Collection<Heikkous> alkiot     = new ArrayList<Heikkous>();
    
    /**
     * Heikkouksien alustaminen.
     */
    public Heikkoudet() {
        // ei tee vielä mitään
    }
    
    /** Tiedostosta lukeminen. Ei toimi vielä.
     * @throws SailoException jos tiedostoa ei löydy
     */
    public void lueTiedostosta() throws SailoException {
        
        try (BufferedReader fi = new BufferedReader(new FileReader(getTiedostonNimi()))) {
        	String rivi;
        	while ((rivi = fi.readLine()) != null) {
        		rivi = rivi.trim();
        		if ("".equals(rivi)) continue;
        		Heikkous heikkous = new Heikkous();
        		heikkous.parse(rivi);
        		lisaa(heikkous);
        	}
        	muutettu = false;
        	
        } catch( FileNotFoundException e) {
            throw new SailoException("Tiedostoa " + tiedostonPerusNimi + " ei löydy tai sankareilla ei ole heikkouksia."); 
        } catch(IOException e) {
            throw new SailoException("Ei osata lukea tiedostoa " + tiedostonPerusNimi);
        }
    }
    
    /**
     * @param heikkous joka poistetaan
     * @return true
     */
    public boolean poista(Heikkous heikkous) {
        boolean ret = alkiot.remove(heikkous);
        if (ret) muutettu = true;
        return ret;
    }
    
    /**
     * @param tunnusNro jasenen, jonka poistetaan
     * @return true, jos muutettiin
     */
    public int poistaJasenenHeikkoudet(int tunnusNro) {
        int n = 0;
        for (Iterator<Heikkous> it = alkiot.iterator(); it.hasNext();) {
            Heikkous hei = it.next();
            if ( hei.getJasenNro() == tunnusNro ) {
                it.remove();
                n++;
            }
        }
        if (n > 0) muutettu = true;
        return n;
    }
    
    /**
     * true muutettu
     */
    public void setMuutos() {   
        muutettu = true;   
    }  
    
    

    
    /** Lisää uuden heikkouden tietorakenteeseen.
     * @param hei heikkous, joka lisätään.
     */
    public void lisaa(Heikkous hei) {
        alkiot.add(hei);
        muutettu = true;
    }
    
    /** Tallentaa heikkoudet tiedostoon. Ei toimi vielä.
     * @throws SailoException jos epäonnistuu.
     */
    public void tallenna() throws SailoException {
    	if(!muutettu) return;
    	
    	File fbak = new File(getBakNimi());
    	File ftied = new File(getTiedostonNimi());
    	
    	fbak.delete();
    	ftied.renameTo(fbak);
    	
    	try ( PrintWriter fo = new PrintWriter(new FileWriter(ftied.getCanonicalPath())) ) { 
    		for (Heikkous hei: this) {
    			fo.println(hei.toString());
    		}
    	} catch ( FileNotFoundException ex ) { 
            throw new SailoException("Ei osata tallettaa tiedostoa " + tiedostonPerusNimi);
    	} catch(IOException ex) {
            throw new SailoException("Ei osata tallettaa tiedostoa " + tiedostonPerusNimi);
    	}
    	muutettu = false;
    }
    
    /**
     * @return nimi + dat
     */
    public String getTiedostonPerusNimi() {
    	return tiedostonPerusNimi + ".dat";
    }
    
    /**
     * @param nimi nimi
     */
    public void setTiedostonPerusNimi(String nimi) {
    	tiedostonPerusNimi = nimi;
    }
    
    /**
     * @return +dat
     */
    public String getTiedostonNimi() {
    	return getTiedostonPerusNimi() + ".dat";
    }
    
    /**
     * @return +bak
     */
    public String getBakNimi() {
    	return tiedostonPerusNimi + ".bak";
    }
    
    
    /** Palauttaa sankarien heikkouksien lukumäärän
     * @return heikkouksien määrä
     */
    public int getLkm() {
        return alkiot.size();
    }
    
    /** Iteraattori kaikkien heikkouksien läpikäymiseen.
     * @return heikkouden iteraattori
     * 
     * @example
     * <pre name="test">
     *  #PACKAGEIMPORT
     *  #import java.util*;
     *  
     *  Heikkous heikot = new Heikkoudet();
     *  Heikkous huimaus11 = new Heikkous(3); heikot.lisaa(huimaus11);
     *  Heikkous huimaus21 = new Heikkous(2); heikot.lisaa(huimaus21);
     *  Heikkous huimaus12 = new Heikkous(1); heikot.lisaa(huimaus12);
     *  Heikkous huimaus31 = new Heikkous(3); heikot.lisaa(huimaus31);
     *  
     *  Iterator<Heikkous> i2=heikot.iterator();
     *  i2.next() === huimaus11;
     *  i2.next() === huimaus21;
     *  i2.next() === huimaus12;
     *  i2.next() === huimaus31;
     *  i2.next() === huimaus41; #THROWS NoSuchElementException
     *  
     *  int n = 0;
     *  int jnrot[] = {2,1,2,1,2};
     *  
     *  for ( Heikkous hei:heikot) {
     *      hei.getJasenNro() === jnrot[n]; n++;
     *      }
     *  
     *  n === 5;
     *  
     * </pre>
     */
    @Override
    public Iterator<Heikkous> iterator() {
        return alkiot.iterator();
    }
    
    /** haetaan kaikki sankarin heikkoudet
     * @param tunnusnro sankarin tunnusnumero, jolle heikkouksia haetaan
     * @return tietorakenne, jossa viitteet löydettyihin heikkouksiin
     * 
     * @example
     * <pre name="test">
     *  #import java.util*;
     *  
     *  Heikkous heikot = new Heikkoudet();
     *  Heikkous huimaus11 = new Heikkous(3); heikot.lisaa(huimaus11);
     *  Heikkous huimaus21 = new Heikkous(2); heikot.lisaa(huimaus21);
     *  Heikkous huimaus12 = new Heikkous(1); heikot.lisaa(huimaus12);
     *  Heikkous huimaus31 = new Heikkous(3); heikot.lisaa(huimaus31);
     *  
     *  List<Heikkous> loytyneet;
     *  loytyneet = heikot.annaHeikkoudet(3);
     *  loytyneet.size() === 2;
     *  loytyneet = harrasteet.annaHarrastukset(1);
     *  loytyneet.size() === 1;
     *  loytyneet.get(0) == huimaus11 === true;
     *  
     *  for (Heikkous hei:heikot) {
     *      if (hei.getJasenNro() === tunnusnro) loydetyt.add(hei);
     *      return loydetyt;
     *      }
     *      
     * </pre>
     */
    public List<Heikkous> annaHeikkoudet(int tunnusnro) {
        List<Heikkous> loydetyt = new ArrayList<Heikkous>();
        for (Heikkous hei : alkiot)
            if (hei.getJasenNro() == tunnusnro) loydetyt.add(hei);
        return loydetyt;
    }
    
    /** Testiohjelma supervoimille
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        Heikkoudet miinukset = new Heikkoudet();
        Heikkous kryptoniitti1 = new Heikkous();
        kryptoniitti1.vastaaKryptoniitti(2);
        Heikkous kryptoniitti2 = new Heikkous();
        kryptoniitti2.vastaaKryptoniitti(3);
        Heikkous kryptoniitti3 = new Heikkous();
        kryptoniitti3.vastaaKryptoniitti(3);
       
        miinukset.lisaa(kryptoniitti1);
        miinukset.lisaa(kryptoniitti2);
        miinukset.lisaa(kryptoniitti3);
        
        List<Heikkous> heikkoudet2 = miinukset.annaHeikkoudet(3);
        
        for (Heikkous hei : heikkoudet2) {
            System.out.print(hei.getJasenNro() + " ");
            hei.tulosta(System.out);
        }

    }
    
}
