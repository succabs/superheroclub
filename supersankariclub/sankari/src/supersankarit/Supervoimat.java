package supersankarit;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

/** Supervoimat, joka osaa lisätä uuden supervoiman.
 * @author Arttu
 * @version 27.3.2017
 *
 */
public class Supervoimat implements Iterable<Supervoima> {
	
    private boolean muutettu = false;
    private String tiedostonPerusNimi = "supervoimat";
    
    private final Collection<Supervoima> alkiot     = new ArrayList<Supervoima>();
    
    /**
     * Supervoimien alustaminen
     */
    public Supervoimat() {
        // ei tee vielä mitään
    }
    
    /**
     * @throws SailoException jos ei löydy
     */
    public void lueTiedostosta() throws SailoException {
        
        try (BufferedReader fi = new BufferedReader(new FileReader(getTiedostonNimi()))) {
            String rivi;
        	while ((rivi = fi.readLine()) != null) {
        		rivi = rivi.trim();
        		if ("".equals(rivi)) continue;
        		Supervoima supervoima = new Supervoima();
        		supervoima.parse(rivi);
        		lisaa(supervoima);
        	}
        	muutettu = false;
        	
        } catch( FileNotFoundException e) {
            throw new SailoException("Tiedostoa " + tiedostonPerusNimi + " ei löydy tai sankareilla ei ole supervoimia.");	
        } catch(IOException e) {
            throw new SailoException("Ei osata lukea tiedostoa " + tiedostonPerusNimi);
        }
    }
    
   
    
    /**
     * @param supervoima joka poistetaan
     * @return true
     */
    public boolean poista(Supervoima supervoima) {
        boolean ret = alkiot.remove(supervoima);
        if (ret) muutettu = true;
        return ret;
    }
    
    /**
     * @param tunnusNro jonka voimat poistetaan
     * @return true jos poistui
     */
    public int poistaJasenenSupervoimat(int tunnusNro) {
        int n = 0;
        for (Iterator<Supervoima> it = alkiot.iterator(); it.hasNext();) {
            Supervoima sup = it.next();
            if ( sup.getJasenNro() == tunnusNro ) {
                it.remove();
                n++;
            }
        }
        if (n > 0) muutettu = true;
        return n;
    }
    
    
    /**
     * muutettu trueksi
     */
    public void setMuutos() {   
        muutettu = true;   
    }  
    
    
    /** Lisää uuden supervoiman tietorakenteeseen.
     * @param sup supervoima, joka lisätään.
     */
    public void lisaa(Supervoima sup) {
        alkiot.add(sup);
        muutettu = true;
    }
    
    /** Tallentaa voimat tiedostoon. Ei toimi vielä
     * @throws SailoException jos epäonnistuu.
     */
    public void tallenna() throws SailoException {
    	if(!muutettu) return;
    	
    	File fbak = new File(getBakNimi());
    	File ftied = new File(getTiedostonNimi());
    	
    	fbak.delete();
    	ftied.renameTo(fbak);
    	
    	try ( PrintWriter fo = new PrintWriter(new FileWriter(ftied.getCanonicalPath())) ) { 
    		for (Supervoima sup: this) {
    			fo.println(sup.toString());
    		}
    	} catch ( FileNotFoundException ex ) { 
            throw new SailoException("Tiedostoa " + tiedostonPerusNimi + " ei löydy!"); 
    	} catch(IOException ex) {
            throw new SailoException("Ei osata tallettaa tiedostoa " + tiedostonPerusNimi);
    	}
    	muutettu = false;
    }
    
    /**
     * @return pnimi
     */
    public String getTiedostonPerusNimi() {
    	return tiedostonPerusNimi + ".dat";
    }
    
    /**
     * @param nimi tiedoston nimi
     */
    public void setTiedostonPerusNimi(String nimi) {
    	tiedostonPerusNimi = nimi;
    }
    
    /**
     * @return pnimi
     */
    public String getTiedostonNimi() {
    	return getTiedostonPerusNimi() + ".dat";
    }
    
    /**
     * @return pnimi
     */
    public String getBakNimi() {
    	return tiedostonPerusNimi + ".bak";
    }
    
    /** Palauttaa sankarien supervoimien lukumäärän
     * @return voimien määrä
     */
    public int getLkm() {
        return alkiot.size();
    }
    /** Iteraattori kaikkien supervoimien läpikäymiseen.
     * 
     * @return supervoiman iteraattori
     * 
     * @example
     * <pre name="test">
     * 
     * </pre>
     */
    @Override
    public Iterator<Supervoima> iterator() {
        return alkiot.iterator();
    }
    /** haetaan kaikki sankarin supervoimat
     * @param tunnusnro sankarin tunnusnumero, jolle supervoimia haetaan
     * @return tietorakenne, jossa viitteet löydettyihin supervoimiin
     * 
     * @example
     * <pre name="test">
     * 
     * </pre>
     */
    public List<Supervoima> annaSupervoimat(int tunnusnro) {
        List<Supervoima> loydetyt = new ArrayList<Supervoima>();
        for (Supervoima sup : alkiot)
            if (sup.getJasenNro() == tunnusnro) loydetyt.add(sup);
        return loydetyt;
    }
    
    /** Testiohjelma supervoimille
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        Supervoimat voimat = new Supervoimat();
        Supervoima seitti1 = new Supervoima();
        seitti1.vastaaSeitinHeitto(2);
        Supervoima seitti2 = new Supervoima();
        seitti2.vastaaSeitinHeitto(4);
        Supervoima seitti3 = new Supervoima();
        seitti3.vastaaSeitinHeitto(1);
        
        voimat.lisaa(seitti1);
        voimat.lisaa(seitti2);
        voimat.lisaa(seitti3);
        
        List<Supervoima> supervoimat2 = voimat.annaSupervoimat(2);
        for (Supervoima sup : supervoimat2) {
            System.out.print(sup.getJasenNro() + " ");
            sup.tulosta(System.out);
        }

    }
    
    
}
