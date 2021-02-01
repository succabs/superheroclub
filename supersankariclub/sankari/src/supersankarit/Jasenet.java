package supersankarit;

import java.io.BufferedReader;
import fi.jyu.mit.ohj2.WildChars; 
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Supersankarikerhon j�senet joka voi esim. lis�t� uuden j�senen
 *
 * @author Arttu Nikkil�
 * @version 28.3.2017
 */
public class Jasenet implements Iterable<Jasen> {

    private boolean muutettu = false;
    private int              lkm           = 0;
    private String           tiedostonNimi = "";
    private String           tiedostonPerusNimi = "nimet";
    private Jasen            alkiot[]      = new Jasen[5];


    /**
     * Oletusmuodostaja
     */
    public Jasenet() {
        // Attribuuttien oma alustus riitt��
    }
    


    /**
     * Lis�� uuden supersankarin tietorakenteeseen
     * @param jasen lis�tt�v� supersankari
     * @throws SailoException jos on liikaa sankareita
     * @example
     * <pre name="test">
     * #THROWS SailoException 
     * Jasenet jasenet = new Jasenet();
     * Jasen peter = new Jasen(), matt = new Jasen();
     * jasenet.getLkm() === 0;
     * jasenet.lisaa(peter); jasenet.getLkm() === 1;
     * jasenet.lisaa(matt); jasenet.getLkm() === 2;
     * jasenet.lisaa(matt); jasenet.getLkm() === 3;
     * jasenet.anna(0) === peter;
     * jasenet.anna(1) === matt;
     * jasenet.anna(2) === matt;
     * jasenet.anna(1) == peter === false;
     * jasenet.anna(1) == matt === true;
     * jasenet.anna(3) === peter; #THROWS IndexOutOfBoundsException 
     * jasenet.lisaa(peter); jasenet.getLkm() === 4;
     * jasenet.lisaa(matt); jasenet.getLkm() === 5;
     * jasenet.lisaa(matt);  #THROWS SailoException
     * </pre>
     */
    public void lisaa(Jasen jasen) throws SailoException {
        if ( lkm >= alkiot.length ) alkiot = Arrays.copyOf(alkiot, lkm+20); 
        alkiot[lkm] = jasen;
        lkm++;
        muutettu = true;
    }


    /**
     * Palauttaa viitteen i:teen supersankariin.
     * @param i monennenko j�senen viite halutaan
     * @return viite j�seneen, jonka indeksi on i
     * @throws IndexOutOfBoundsException jos i ei ole sallitulla alueella  
     */
    protected Jasen anna(int i) throws IndexOutOfBoundsException {
        if (i < 0 || lkm <= i) 
            throw new IndexOutOfBoundsException("Laiton indeksi: " + i);  
        return alkiot[i];
    }
    
    /** Korvaa j�senen tietorakenteessa ja ottaa omistukseensa
     * Jos samalla nro:lla ei oo j�sent�, lis�� uutena j�senen�.
     * @param jasen joka lis�t��n
     * @throws SailoException jos liikaa j�seni�
     */
    public void korvaaTaiLisaa(Jasen jasen) throws SailoException {
        int id = jasen.getTunnusNro();
        for(int i = 0; i < lkm; i++) {
            if ( alkiot[i].getTunnusNro() == id) {
                alkiot[i] = jasen;
                muutettu = true;
                        return;
            }
        }
        lisaa(jasen);
    }


    /**
     * Lukee j�senist�n tiedostosta.
     * @throws SailoException jos luku ep�onnistuu
     */
    public void lueTiedostosta() throws SailoException {
        
        try (BufferedReader fi = new BufferedReader(new FileReader(getTiedostonNimi()))) {
        	String rivi;
        	while ((rivi = fi.readLine()) != null) {
        		rivi = rivi.trim();
        		if ("".equals(rivi)) continue;
        		Jasen jasen = new Jasen();
        		jasen.parse(rivi);
        		lisaa(jasen);
        	}
        	muutettu = false;
        	
        } catch( FileNotFoundException e) {
            throw new SailoException("Ei osata lukea tiedostoa " + tiedostonNimi);	
        } catch(IOException e) {
            throw new SailoException("Ei osata lukea tiedostoa " + tiedostonNimi);
        }
        
    }

    /**
     * Tallentaa supersankarikerhon j�senist�n tiedostoon.
     * @throws SailoException jos talletus ep�onnistuu
     */
    public void tallenna() throws SailoException {
    	if(!muutettu) return;
    	
    	File fbak = new File(getBakNimi());
    	File ftied = new File(getTiedostonNimi());    	
    	fbak.delete();
    	ftied.renameTo(fbak);
    	
    	try ( PrintWriter fo = new PrintWriter(new FileWriter(ftied.getCanonicalPath())) ) { 
    		for (Jasen jasen: this) {
    			fo.println(jasen.toString());
    		}
    	} catch ( FileNotFoundException ex ) { 
            throw new SailoException("Ei osata tallettaa tiedostoa " + tiedostonNimi);
    	} catch(IOException ex) {
            throw new SailoException("Ei osata tallettaa tiedostoa " + tiedostonNimi);
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
     * @param nimi on nimi
     */
    public void setTiedostonPerusNimi(String nimi) {
    	tiedostonPerusNimi = nimi;
    }
    
    /**
     * @return pnimi + dat
     */
    public String getTiedostonNimi() {
    	return getTiedostonPerusNimi() + ".dat";
    }
    
    /**
     * @return pnimi + bak
     */
    public String getBakNimi() {
    	return tiedostonPerusNimi + ".bak";
    }
    
    
    


    /**
     * Palauttaa supersankarien lukum��r�n
     * @return j�senten lukum��r�
     */
    public int getLkm() {
        return lkm;
    }

    
    /** Luokka Sankarij�senen tietojen iteroimiseen
     * @author Arttu
     * @version 24.4.2017
     *
     */
    public class JasenetIterator implements Iterator<Jasen> {
    	private int kohdalla = 0;
    	
    	@Override
    	public boolean hasNext() {
    		return kohdalla < getLkm();
    	}
    	
    	@Override
    	public Jasen next() throws NoSuchElementException {
    		if (!hasNext() ) throw new NoSuchElementException("Ei lis�� j�seni�");
    		return anna(kohdalla++);
    	}
    	
    	@Override
    	public void remove() throws UnsupportedOperationException {
    		throw new UnsupportedOperationException("Ei voi poistaa");
    	}
    }
    	
    	@Override
        public Iterator<Jasen> iterator() {
    		return new JasenetIterator();
    	}
    	

    	/**
    	 * @param hakuehto ehto
    	 * @param k j�senet
    	 * @return joihin ehto p�tee
    	 */
    	public Collection<Jasen> etsi(String hakuehto, int k) {
    	    String ehto = "*";
    	    if (hakuehto != null && hakuehto.length() > 0 ) ehto = hakuehto;
    	    int hk = k;
    		Collection<Jasen> loytyneet = new ArrayList<Jasen>();
    		for(Jasen jasen : this) {
    		    if (WildChars.onkoSamat(jasen.anna(hk), ehto)) loytyneet.add(jasen);    
    		}
    		return loytyneet;
    	}
    	
    /** poistaa j�senen, jolla id
     * @param id joka poistetaan
     * @return 1 jos poistettiin, 0 jos ei l�ydy
     */
    public int poista(int id) { 
        int ind = etsiId(id); 
        if (ind < 0) return 0; 
        lkm--; 
        for (int i = ind; i < lkm; i++) 
            alkiot[i] = alkiot[i + 1]; 
        alkiot[lkm] = null; 
        muutettu = true; 
        return 1; 
    } 
    
    /**
     * @param id  jasenen id
     * @return jasenen id
     */
    public Jasen annaId(int id) { 
        for (Jasen jasen : this) { 
            if (id == jasen.getTunnusNro()) return jasen; 
        } 
        return null; 
    } 
    /**
     * @param id  jasenen id
     * @return jasenen id
     */
    public int etsiId(int id) { 
        for (int i = 0; i < lkm; i++) 
            if (id == alkiot[i].getTunnusNro()) return i; 
        return -1; 
    } 
    /**
     * Testiohjelma sankareille
     * @param args ei k�yt�ss�
     */
    public static void main(String args[]) {
        Jasenet jasenet = new Jasenet();

        Jasen peter = new Jasen(), peter2 = new Jasen();
        peter.rekisteroi();
        peter.vastaaPeterParker();
        peter2.rekisteroi();
        peter2.vastaaPeterParker();

        try {
            jasenet.lisaa(peter);
            jasenet.lisaa(peter2);

            System.out.println("============= J�senet testi =================");

            for (int i = 0; i < jasenet.getLkm(); i++) {
                Jasen jasen = jasenet.anna(i);
                System.out.println("J�sen nro: " + i);
                jasen.tulosta(System.out);
            }

        } catch (SailoException ex) {
            System.out.println(ex.getMessage());
        }
    }

}