package supersankarit;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

/** Luokka vihamiehille, osaa mm. luoda uuden vihamiehen!
 * @author Arttu Nikkilä
 * @version 27.3.2017
 *
 */
public class Vihamiehet implements Iterable<Vihamies> {
    
    private boolean muutettu = false;
    private String tiedostonPerusNimi = "vihamiehet";
    
    private final Collection<Vihamies> alkiot     = new ArrayList<Vihamies>();
    
    /**
     * Vihamiesten
     */
    public Vihamiehet() {
        // ei tee vielä mitään
    }
    
    /** Lisää uuden vihamiehen tietorakenteeseen.
     * @param vih vihamies, joka lisätään.
     */
    public void lisaa(Vihamies vih) {
        alkiot.add(vih);
        muutettu = true;
    }
    
    /**
     * @throws SailoException virhe
     */
    public void lueTiedostosta() throws SailoException {
        
        try (BufferedReader fi = new BufferedReader(new FileReader(getTiedostonNimi()))) {
            String rivi;
        	while ((rivi = fi.readLine()) != null) {
        		rivi = rivi.trim();
        		if ("".equals(rivi)) continue;
        		Vihamies vihamies = new Vihamies(9);
        		vihamies.parse(rivi);
        		lisaa(vihamies);
        	}
        	muutettu = false;
        	
        } catch( FileNotFoundException e) {
            throw new SailoException("Tiedostoa " + tiedostonPerusNimi + " ei löydy tai sankareilla ei ole vihamiehiä."); 
        } catch(IOException e) {
            throw new SailoException("Ei osata lukea tiedostoa " + tiedostonPerusNimi);
        }
    }
    
    /**
     * @param vihamies joka poistetaan
     * @return muutettu trueksi
     */
    public boolean poista(Vihamies vihamies) {
        boolean ret = alkiot.remove(vihamies);
        if (ret) muutettu = true;
        return ret;
    }
    
    /**
     * @param tunnusNro jonka vihamiehet poistetaan
     * @return n jos muutettu true
     */
    public int poistaJasenenVihamiehet(int tunnusNro) {
        int n = 0;
        for (Iterator<Vihamies> it = alkiot.iterator(); it.hasNext();) {
            Vihamies vih = it.next();
            if ( vih.getJasenNro() == tunnusNro ) {
                it.remove();
                n++;
            }
        }
        if (n > 0) muutettu = true;
        return n;
    }
    
    
    /**
     * m trueksi
     */
    public void setMuutos() {   
        muutettu = true;   
    }  
    
    /** Tallentaa vihamiehet tiedostoon. Ei toimi vielä
     * @throws SailoException jos epäonnistuu.
     */
    public void tallenna() throws SailoException {
    	if(!muutettu) return;
    	
    	File fbak = new File(getBakNimi());
    	File ftied = new File(getTiedostonNimi());
    	
    	fbak.delete();
    	ftied.renameTo(fbak);
    	
    	try ( PrintWriter fo = new PrintWriter(new FileWriter(ftied.getCanonicalPath())) ) { 
    		for (Vihamies vih: this) {
    			fo.println(vih.toString());
    		}
    	} catch ( FileNotFoundException ex ) { 
            throw new SailoException("Ei osata tallettaa tiedostoa " + tiedostonPerusNimi);
    	} catch(IOException ex) {
            throw new SailoException("Ei osata tallettaa tiedostoa " + tiedostonPerusNimi);
    	}
    	muutettu = false;
    }
    
    /**
     * @return +.dat
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
     * @return +.dat
     */
    public String getTiedostonNimi() {
    	return getTiedostonPerusNimi() + ".dat";
    }
    /**
     * @return +.bak
     */
    public String getBakNimi() {
    	return tiedostonPerusNimi + ".bak";
    }
    
    /** Palauttaa sankarien vihamiesten lukumäärän
     * @return vihamiesten määrä
     */
    public int getLkm() {
        return alkiot.size();
    }
    /** Iteraattori kaikkien vihamiesten läpikäymiseen.
     * 
     * @return vihamiehen iteraattori
     * 
     * @example
     * <pre name="test">
     * 
     * </pre>
     */
    @Override
    public Iterator<Vihamies> iterator() {
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
    public List<Vihamies> annaVihamiehet(int tunnusnro) {
        List<Vihamies> loydetyt = new ArrayList<Vihamies>();
        for (Vihamies vih : alkiot)
            if (vih.getJasenNro() == tunnusnro) loydetyt.add(vih);
        return loydetyt;
    }
    
    /** Testiohjelma supervoimille
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        Vihamiehet miehet = new Vihamiehet();
        Vihamies goblin1 = new Vihamies();
        goblin1.vastaaGreenGoblin(2);
        Vihamies goblin2 = new Vihamies();
        goblin1.vastaaGreenGoblin(1);
        Vihamies goblin3 = new Vihamies();
        goblin1.vastaaGreenGoblin(3);
       
        miehet.lisaa(goblin1);
        miehet.lisaa(goblin2);
        miehet.lisaa(goblin3);
        
        List<Vihamies> vihamiehet2 = miehet.annaVihamiehet(3);
        
        for (Vihamies vih : vihamiehet2) {
            System.out.print(vih.getJasenNro() + " ");
            vih.tulosta(System.out);
        }

    }
    
}
