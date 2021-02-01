package supersankarit;

import java.util.*;
import java.io.File;
import java.util.Collection;

/**
 * Supersankarit-luokka, joka huolehtii j‰senist‰. Suurin osa metodeista on v‰litt‰j‰metodeja supersankareille.
 * 
 * @author Arttu Nikkil‰
 * @version 26.3.2017
 *
 */
public class Supersankarit {
    private  Jasenet jasenet = new Jasenet();
    private  Heikkoudet heikkoudet = new Heikkoudet();
    private  Vihamiehet vihamiehet = new Vihamiehet();
    private  Supervoimat supervoimat = new Supervoimat();
    
    /**
     * Palautaa supersankarien m‰‰r‰n
     * @return j‰senm‰‰r‰
     */
    public int getJasenia() {
        return jasenet.getLkm();
    }
    
    /**
     * @param jasen sankari, jonka heikkoutta pyydet‰‰n
     * @return j‰senen heikkoudet
     * @throws SailoException virhe
     * @example
     * <pre name="test">
     *  #import java.util*;
     *  
     * Supersankarit supersankarit = new Supersankarit();
     * Jasen peter1 = new Jasen(), peter2 = new Jasen();
     * peter1.rekisteroi(); peter2.rekisteroi();
     * int id1 = peter1.getTunnusNro();
     * int id2 = peter2.getTunnusNro(); 
     * Heikkous krypto11 = new Heikkous(id1); supersankarit.lisaa(krypto11);
     * Heikkous krypto21 = new Heikkous(id1); supersankarit.lisaa(krypto21);
     * Heikkous krypto31 = new Heikkous(id1); supersankarit.lisaa(krypto31);
     * Heikkous krypto41 = new Heikkous(id2); supersankarit.lisaa(krypto41);
     * Heikkous krypto51 = new Heikkous(id2); supersankarit.lisaa(krypto51);
     * Heikkous krypto61 = new Heikkous(id1); supersankarit.lisaa(krypto61);
     *  
     * List<Heikkous> loytyneet;
     * loytyneet = supersankarit.annaHeikkoudet(peter1);
     * loytyneet.size() === 4;
     * loytyneet = supersankarit.annaHeikkoudet(peter2);
     * loytyneet.size() === 2:
     * loytyneet.get(1) == krypto21 === true;
     * </pre>
     */
    public List<Heikkous> annaHeikkoudet(Jasen jasen) throws SailoException {
        return heikkoudet.annaHeikkoudet(jasen.getTunnusNro());
    }
    
    /**
     * @param jasen j‰sen, jonka supervoimaa pyydet‰‰n
     * @return j‰senen supervoimat
     * @throws SailoException virhe
     * @example
     * <pre name="test">
     *  #import java.util*;
     *  
     * Supersankarit supersankarit = new Supersankarit();
     * Jasen peter1 = new Jasen(), peter2 = new Jasen();
     * peter1.rekisteroi(); peter2.rekisteroi();
     * int id1 = peter1.getTunnusNro();
     * int id2 = peter2.getTunnusNro(); 
     * Supervoima seitti11 = new Supervoima(id1); supersankarit.lisaa(seitti11);
     * Supervoima seitti12 = new Supervoima(id1); supersankarit.lisaa(seitti12);
     * Supervoima seitti13 = new Supervoima(id2); supersankarit.lisaa(seitti13);
     * Supervoima seitti14 = new Supervoima(id1); supersankarit.lisaa(seitti14);
     * Supervoima seitti15 = new Supervoima(id2); supersankarit.lisaa(seitti15);
     * 
     * List<Supervoima> loytyneet;
     * loytyneet = supersankarit.Supervoimat(peter1);
     * loytyneet.size() === 3;
     * loytyneet = supersankarit.annaHeikkoudet(peter2);
     * loytyneet.size() === 2:
     * loytyneet.get(1) == seitti12 === true;
     * </pre>
     */
    public List<Supervoima> annaSupervoimat(Jasen jasen) throws SailoException {
        return supervoimat.annaSupervoimat(jasen.getTunnusNro());
    }
    
    /**
     * @param jasen sankari, jonka vihamiest‰ pyydet‰‰n
     * @throws SailoException virhe
     * @return sankarin vihamiehet
     * @example
     * <pre name="test">
     *  #import java.util*;
     *  
     * Supersankarit supersankarit = new Supersankarit();
     * Jasen peter1 = new Jasen(), peter2 = new Jasen();
     * peter1.rekisteroi(); peter2.rekisteroi();
     * int id1 = peter1.getTunnusNro();
     * int id2 = peter2.getTunnusNro(); 
     * Vihamies hulk11 = new Vihamies(id2); supersankarit.lisaa(hulk11);
     * Vihamies hulk31 = new Vihamies(id1); supersankarit.lisaa(hulk31);
     * Vihamies hulk12 = new Vihamies(id1); supersankarit.lisaa(hulk12);
     * Vihamies hulk41 = new Vihamies(id1); supersankarit.lisaa(hulk41);
     * 
     * List<Vihamies> loytyneet;
     * loytyneet = supersankarit.Vihamiehet(peter1);
     * loytyneet.size() === 2;
     * loytyneet = supersankarit.annaHeikkoudet(peter2);
     * loytyneet.size() === 2:
     * loytyneet.get(1) == hulk31 === true;
     * </pre>
     */
    public List<Vihamies> annaVihamiehet(Jasen jasen) throws SailoException {
        return vihamiehet.annaVihamiehet(jasen.getTunnusNro());
    }
    

    /**
     * Poistaa j‰senistˆst‰ ja harrasteista ne joilla on nro. Kesken.
     * @param jasen joka poistetaan
     * @return montako j‰sent‰ poistettiin
     */
    public int poista(Jasen jasen) {
        if ( jasen == null ) return 0;
        int ret = jasenet.poista(jasen.getTunnusNro()); 
        heikkoudet.poistaJasenenHeikkoudet(jasen.getTunnusNro()); 
        supervoimat.poistaJasenenSupervoimat(jasen.getTunnusNro());
        vihamiehet.poistaJasenenVihamiehet(jasen.getTunnusNro());
        return ret; 
    }
    
    /**
     * @param heikkous heikkous
     */
    public void poistaHeikkous(Heikkous heikkous) { 
        heikkoudet.poista(heikkous); 
    } 
    
    /**
     * @param Supervoima voima
     */
    public void poistaSupervoima(Supervoima Supervoima) { 
        supervoimat.poista(Supervoima); 
    } 
    
    /**
     * @param vihamies vihamies
     */
    public void poistaVihamies(Vihamies vihamies) { 
        vihamiehet.poista(vihamies); 
    } 
    
    /**
     *  muutos
     */
    public void setHeikkousMuutos() {   
        heikkoudet.setMuutos();  
    }
    
    /**
     *  muutos
     */
    public void setSupervoimaMuutos() {   
        supervoimat.setMuutos();  
    }
    
    /**
     * muutos
     */
    public void setVihamiesMuutos() {   
        vihamiehet.setMuutos();  
    }
    
    /**
     * @param jasen j‰sen
     * @throws SailoException jos ei lˆydy
     * 
     * <pre name="test">
     * #THROWS SailoException
     * Supersankarit supersankarit = new Supersankarit();
     * Jasen peter1 = new Jasen(), peter2 = new Jasen();
     * peter1.rekisteroi(); peter2.rekisteroi();
     * supersankarit.getJasenia() === 0;
     * supersankarit.lisaa(peter1); supersankarit.getJasenia() === 1;
     * supersankarit.lisaa(peter2); supersankarit.getJasenia() === 2;
     * supersankarit.lisaa(peter1); supersankarit.getJasenia() === 3;
     * supersankarit.getJasenia() === 3;
     * supersankarit.annaJasen(0) === peter1;
     * supersankarit.annaJasen(1) === peter2;
     * supersankarit.annaJasen(2) === peter1;
     * supersankarit.annaJasen(3) === peter1; #THROWS IndexOutOfBoundsException 
     * supersankarit.lisaa(peter2); supersankarit.getJasenia() === 4;
     * supersankarit.lisaa(peter2); supersankarit.getJasenia() === 5;
     * supersankarit.lisaa(peter1);            #THROWS SailoException
     * </pre>
     */
    public void lisaa(Jasen jasen) throws SailoException {
        jasenet.lisaa(jasen);
    }
    
    /**
     * @param jasen jasen jota muokataan
     * @throws SailoException jos virhe
     */
    public void korvaaTaiLisaa(Jasen jasen) throws SailoException {  
          jasenet.korvaaTaiLisaa(jasen);  
      }  
    
    /**Lis‰t‰‰n uusi heikkous 
     * 
     * @param hei heikkous, joka lis‰t‰‰n
     * @throws SailoException virhe
     */
    public void lisaa(Heikkous hei) throws SailoException {
        heikkoudet.lisaa(hei);
    }
    
    /**Lis‰t‰‰n uusi vihamies
     * 
     * @param vih vihamies, joka lis‰t‰‰n
     * @throws SailoException virhe
     */
    public void lisaa(Vihamies vih) throws SailoException {
        vihamiehet.lisaa(vih);
    }
    
    /**Lis‰t‰‰n uusi supervoima 
     * 
     * @param sup supervoima, joka lis‰t‰‰n
     * @throws SailoException virhe
     */
    public void lisaa(Supervoima sup) throws SailoException {
        supervoimat.lisaa(sup);
    }

    /**
     * Palauttaa i:n sankarin
     * @param hakuehto mit‰ haetaan
     * @param k  mones haetaan
     * @return viite i:teen sankariin
     * @throws SailoException virhe
     * @throws IndexOutOfBoundsException jos i v‰‰rin
     */
    public Collection<Jasen> etsi(String hakuehto, int k) throws SailoException {
    	return jasenet.etsi(hakuehto, k);
    }

    

    /**
     * Lukee kerhon tiedot tiedostoista
     * @throws SailoException jos lukeminen ep‰onnistuu
     */
    public void lueTiedostosta() throws SailoException {
    	jasenet = new Jasenet();
    	heikkoudet = new Heikkoudet();
    	vihamiehet = new Vihamiehet();
    	supervoimat = new Supervoimat();
        jasenet.lueTiedostosta();
        heikkoudet.lueTiedostosta();
        vihamiehet.lueTiedostosta();
        supervoimat.lueTiedostosta();

        
    }
    
    /**
     * @param nimi asettaa tiedostojen perusnimet.
     */
    public void setTiedosto(String nimi) {
    	File dir = new File(nimi);
    	dir.mkdirs();
    	String hakemistonNimi = "";
    	if(!nimi.isEmpty()) hakemistonNimi = nimi + "/";
    	jasenet.setTiedostonPerusNimi(hakemistonNimi + "nimet");
    	heikkoudet.setTiedostonPerusNimi(hakemistonNimi + "heikkoudet");
    	vihamiehet.setTiedostonPerusNimi(hakemistonNimi + "vihamiehet");
    	supervoimat.setTiedostonPerusNimi(hakemistonNimi + "supervoimat");
    }
    
    /**
     * Tallettaa supersankarien tiedot tiedostoihin
     * @throws SailoException jos tallettamisessa ongelmia
     */
    public void tallenna() throws SailoException {
        String virhe = "";
        try {
        	jasenet.tallenna();
        } catch (SailoException ex) {
        	virhe = ex.getMessage();
        }
        if (!"".equals(virhe)) throw new SailoException(virhe);
    
    
    try {
    	heikkoudet.tallenna();
    } catch (SailoException ex) {
    	virhe += ex.getMessage();
    }
    if (!"".equals(virhe)) throw new SailoException(virhe);
    
    try {
    	supervoimat.tallenna();
    } catch (SailoException ex) {
    	virhe += ex.getMessage();
    }
    if (!"".equals(virhe)) throw new SailoException(virhe);
    
    try {
    	vihamiehet.tallenna();
    } catch (SailoException ex) {
    	virhe += ex.getMessage();
    }
    if (!"".equals(virhe)) throw new SailoException(virhe);
}
    
    


    /**
     * Testiohjelma supersankareista
     * @param args ei k‰ytˆss‰
     */
    public static void main(String args[]) {
        Supersankarit supersankarit = new Supersankarit();

        try {
            supersankarit.lueTiedostosta();



            System.out.println("============= Supersankaritesti =================");

            Collection<Jasen> jasenet = supersankarit.etsi("",  -1);
            int i=0;
            for(Jasen jasen: jasenet) {
                System.out.println("J‰sen paikassa: " + i);
                jasen.tulosta(System.out);
                i++;
            }

        } catch (SailoException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
}
