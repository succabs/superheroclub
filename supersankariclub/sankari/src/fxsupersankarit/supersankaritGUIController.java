package fxsupersankarit;

import java.awt.Desktop;
import javafx.scene.layout.GridPane; 
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;


import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;

import fi.jyu.mit.fxgui.ComboBoxChooser;
import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ListChooser;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.StringGrid; 
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import supersankarit.Jasen;
import supersankarit.Supersankarit; 
import supersankarit.SailoException;
import supersankarit.Vihamies;
import supersankarit.Heikkous;
import supersankarit.Supervoima;
import java.util.Collection;
import static fxsupersankarit.SankariDialogController.*;  

/**
 * @author Arttu Nikkil�
 * @version 16.2.2017
 * K�ytt�liittym�n tapahtumien hoitamista
 */
public class SupersankaritGUIController implements Initializable {
    
    @FXML private TextField hakuehto;
    @FXML private ComboBoxChooser<String> cbKentat;
    @FXML private Label labelVirhe;
    @FXML private TextField labelOma;
    @FXML private ScrollPane panelJasen;
    @FXML private ListChooser<Jasen> chooserJasenet;
    @FXML private GridPane gridJasen;
    @FXML private StringGrid<Heikkous> tableHeikkoudet; 
    @FXML private StringGrid<Vihamies> tableVihamiehet; 
    @FXML private StringGrid<Supervoima> tableSupervoimat;
    
    @Override
    public void initialize(URL url, ResourceBundle bundle) {
        alusta(); 
    }
    
    @FXML private void handleHakuehto() {
        if(jasenKohdalla != null)
        	hae(jasenKohdalla.getTunnusNro());
    }
        /**
         * Seuraavissa k�sitell��n nappien painamiset.
         */
        @FXML private void handleUusiSankari() {
            uusiJasen();
        }
        @FXML
        void handleApua() {
            apustus();
        }
        
        @FXML
        void handleAvaa() {
            avaa();
        }
        

        @FXML
        void handleTallenna() {
            tallenna();
        }
        

        @FXML
        void handleTietoja() {
            tiedot();
        }
        

        @FXML
        void handleLisaaHeikkous() {
            uusiHeikkous();
        }
        


        @FXML
        void handleLisaaSupervoima() {
            lisaaSuperVoima();
        }


        @FXML
        void handleLisaaVihaMies() {
            lisaaVihaMies();
        }
        

        @FXML
        void handleLopeta() {
            tallenna();
            lopeta();
        }        
        

        @FXML
        void handlePoistaHeikkous() {
            poistaHeikkous();
        }
        


        @FXML
        void handlePoistaSankari() {
              poistaJasen();
        }
        

        @FXML
        void handlePoistaSuperVoima() {
                poistaSuperVoima();
        }


        @FXML
        void handlePoistaVihaMies() {
            poistaVihaMies();
        }



        //=====================================================================================================hoidetaan edelliset napinpainamiset.
        // kutsutaan erillist� apuohjelmaa. Jos ei toimi, n�ytt�� showMessageDialogia + saattaa kutsua muita toimimattomia, esim lopeta.
        
              
        /**
         * N�ytet��n ohjelman tekij�n tiedot.
         */
        private void tiedot() {
            Dialogs.showMessageDialog("Ohjelman teki Arttu Nikkil�.");      
            
        }

        /**
         * Tallennetaan ja lopetetaan ohjelma.
         */
        private void lopeta() {
            tallenna();
            Dialogs.showMessageDialog("Lopetettu ja tallennettu!");
            Platform.exit();
        }
        
        /**
         * Poistetaan heikkous.
         */
        private void poistaHeikkous(){
        Heikkous heikkous = tableHeikkoudet.getObject();
        if ( heikkous == null ) return;
        int rivi = tableHeikkoudet.getRowNr();
        supersankarit.poistaHeikkous(heikkous);
        naytaHeikkoudet(jasenKohdalla);
        tableHeikkoudet.selectRow(rivi);
        }
        
        /**
         * Poistetaan j�sen
         */        
        private void poistaJasen() {
        if ( jasenKohdalla == null ) return;
        if ( !Dialogs.showQuestionDialog("Poisto", "Poistetaanko j�sen: " + jasenKohdalla.getNimi(), "Kyll�", "Ei") )
            return;
        supersankarit.poista(jasenKohdalla);
        int index = chooserJasenet.getSelectedIndex();
        hae(0);
        chooserJasenet.setSelectedIndex(index);
        }
        
        /**
         * Lis�t��n supervoima.
         */
        private void lisaaSuperVoima() {
            uusiSuperVoima();
        }
        
        /**
         * Poistetaan supervoima.
         */
        private void poistaSuperVoima() {
            Supervoima supervoima = tableSupervoimat.getObject();
            if ( supervoima == null ) return;
            int rivi = tableSupervoimat.getRowNr();
            supersankarit.poistaSupervoima(supervoima);
            naytaSupervoimat(jasenKohdalla);
            tableSupervoimat.selectRow(rivi);  
        }
        
        /**
         * Lis�t��n vihamies.
         */
        private void lisaaVihaMies() {
            uusiVihaMies();  
        }
        
        /**
         * Poistetaan vihamies.
         */
        private void poistaVihaMies() {
            Vihamies vihamies = tableVihamiehet.getObject();
            if ( vihamies == null ) return;
            int rivi = tableVihamiehet.getRowNr();
            supersankarit.poistaVihamies(vihamies);
            naytaVihamiehet(jasenKohdalla);
            tableVihamiehet.selectRow(rivi);   
        }
        
        /**
         * Ohjelman avustuksen n�kee netist�. Ei tosin toimi viel�.
         */       
        private void apustus() {
            Desktop desktop = Desktop.getDesktop();
            try {
                URI uri = new URI("www.arttu.eu");
                desktop.browse(uri);
            } catch (URISyntaxException e) {
                return;
            } catch (IOException e) {
            return;
        }
    }
        
        
        private Supersankarit supersankarit;
        private Jasen jasenKohdalla;
        private TextField[] edits; 
        private Jasen apusankari = new Jasen();
        private Heikkous apuheikkous = new Heikkous();
        
        
        /**
         * Tekee tarvittavat muut alustukset, nyt vaihdetaan GridPanen tilalle
         * yksi iso tekstikentt�, johon voidaan tulostaa j�senten tiedot.
         * Alustetaan my�s j�senlistan kuuntelija 
         */
        protected void alusta() {
            
            panelJasen.setFitToHeight(true);            
            chooserJasenet.clear();
            chooserJasenet.addSelectionListener(e -> naytaJasen());
         edits = SankariDialogController.luoKentat(gridJasen);  
         for (TextField edit: edits)  
              if ( edit != null ) {  
                  edit.setEditable(false);  
                  edit.setOnMouseClicked(e -> { if ( e.getClickCount() > 1 ) muokkaa(getFieldId(e.getSource(),0)); }); 
                  
              }  
         
        cbKentat.clear();
        for (int k = apusankari.ekaKentta(); k < apusankari.getKenttia(); k++)  
            cbKentat.add(apusankari.getKysymys(k), null);
        cbKentat.setSelectedIndex(0);
        
                String otsikot[] = new String[apuheikkous.getKenttia()-apuheikkous.ensimmainenKentta()];
        for (int i=0, k=apuheikkous.ensimmainenKentta(); k < apuheikkous.getKenttia(); i++, k++)
            otsikot[i] = apuheikkous.getKysymys(k);
        tableHeikkoudet.initTable(otsikot);
        tableHeikkoudet.setPlaceholder(new Label("Ei viel� heikkouksia"));
        tableHeikkoudet.setEditable(true);
        
        tableHeikkoudet.setOnGridLiveEdit((g, heikkous, defValue, r, c, edit) -> {
             String virhe = heikkous.aseta(c+heikkous.ensimmainenKentta(), defValue);
             if ( virhe == null ) {
                 edit.setStyle(null);
                 supersankarit.setHeikkousMuutos();
                 Dialogs.setToolTipText(edit,"");
                } else {
                 Dialogs.setToolTipText(edit,virhe);
             }
             return defValue;
         });

     }

      /**
       * 
       * @param k mit� muokataan
       */
     private void muokkaa(int k) {  
          if ( jasenKohdalla == null ) return;  
          try {  
              Jasen jasen;  
              jasen = SankariDialogController.kysyJasen(null, jasenKohdalla.clone(), k);  
              if ( jasen == null ) return;  
              supersankarit.korvaaTaiLisaa(jasen);  
              hae(jasen.getTunnusNro());  
          } catch (CloneNotSupportedException e) {  
              //  
          } catch (SailoException e) {  
              Dialogs.showMessageDialog(e.getMessage());  
          }  
            
            
        }
         /**
          * 
          * @param index montako sankaria on
          */
        private void naytaOma(int index){
            String homo = String.valueOf(index);
            labelOma.setText(homo);
        }
        
        /**
         * 
         * @param virhe virhe
         */
        private void naytaVirhe(String virhe) {
            if ( virhe == null || virhe.isEmpty() ) {
                labelVirhe.setText("");
                labelVirhe.getStyleClass().removeAll("virhe");
                return;
            }
            labelVirhe.setText(virhe);
            labelVirhe.getStyleClass().add("virhe");
            
        }
        
        /**
         * 
         * @param title hakuehto
         */
        private void setTitle(String title) {
            ModalController.getStage(hakuehto).setTitle(title);
        }
        
        /**
         * @return virheen jos on
         */
        protected String lueTiedosto() {
            setTitle("nimi");
            try {
            	supersankarit.lueTiedostosta();
            	hae(0);
            	return null;
            } catch (SailoException e) {
            	hae(0);
            	String virhe = e.getMessage();
            	if(virhe != null) Dialogs.showMessageDialog(virhe);
            	return virhe;
            }
            
        }
        
        /**
         * Kysyt��n tiedoston nimi ja luetaan se
         * @return true jos onnistui, false jos ei
         */
        public boolean avaa() {
            lueTiedosto();
            return true;
        }

        
        /**
         * Tietojen tallennus
         */
        private String tallenna() {
        	try {
        		supersankarit.tallenna();
        		return null;
        	} catch (SailoException ex) {
                Dialogs.showMessageDialog("Tallennuksessa ongelma!" + ex.getMessage());
                return ex.getMessage();
        	}

        }


        /**
         * Tarkistetaan onko tallennus tehty
         * @return true jos saa sulkea sovelluksen, false jos ei
         */
        public boolean voikoSulkea() {
            tallenna();
            return true;
        }
        
        
        /**
         * N�ytt�� listasta valitun j�senen tiedot
         */
        protected void naytaJasen() {
            jasenKohdalla = chooserJasenet.getSelectedObject();
        naytaHeikkoudet(jasenKohdalla);
        naytaSupervoimat(jasenKohdalla);
        naytaVihamiehet(jasenKohdalla);
        gridJasen.setVisible(jasenKohdalla != null); 
        naytaVirhe(null);

            SankariDialogController.naytaJasen(edits, jasenKohdalla);  
        }
        
        /**
         * @param index j�senten lukum��r�
         */
        protected void oma(int index) {

            naytaOma(index);
        }
        
        


        /**
         * Hakee j�senten tiedot listaan
         * @param jnro j�senen numero, joka aktivoidaan haun j�lkeen
         */
        protected void hae(int jnro) {
            int k = cbKentat.getSelectedIndex() + apusankari.ekaKentta();
        	String ehto = hakuehto.getText();
        	if (ehto.indexOf('*') < 0) ehto = "*" + ehto + "*"; 
            naytaVirhe(null);
        		
            chooserJasenet.clear();
            int index = 0;
            int maara = 0;

            Collection<Jasen> jasenet;
            try {
            	jasenet = supersankarit.etsi(ehto, k);
            	int i = 0;
            	for (Jasen jasen:jasenet) {
            		if (jasen.getTunnusNro() == jnro) index = i;
            		chooserJasenet.add(jasen.getNimi(), jasen);
            		i++;
            		maara = maara + 1;
            		
            	}
            	if ( i == 0 ) SankariDialogController.tyhjenna(edits);  // jos ei yht��n j�sent� 
            }catch(SailoException ex) {
            	Dialogs.showMessageDialog("ongelmia" + ex.getMessage());
            }
            chooserJasenet.setSelectedIndex(index); // t�st� tulee muutosviesti joka n�ytt�� j�senen
            oma(maara);
        }


        /**
         * Luo uuden j�senen jota aletaan editoimaan 
         */
        protected void uusiJasen() {
            try { 
             Jasen uusi = new Jasen(); 
             uusi = SankariDialogController.kysyJasen(null, uusi, 0);  
             if ( uusi == null || uusi.getVuosi() == 0 ) return;  
             uusi.rekisteroi();  
                supersankarit.lisaa(uusi);
                hae(uusi.getTunnusNro());  
            } catch (SailoException e) {
                Dialogs.showMessageDialog("Ongelmia uuden luomisessa " + e.getMessage());
                return;
            }
        }
        
        /**
         * Lis�t��n uusi heikkous.
         */
        public void uusiHeikkous() {
            if (jasenKohdalla == null) return;
            Heikkous hei = new Heikkous(jasenKohdalla.getTunnusNro()); 
            hei.rekisteroi();
            try {
            	supersankarit.lisaa(hei);
            }catch (SailoException e) {
            	Dialogs.showMessageDialog("Ongelma! " + e.getMessage());
            }
            hae(jasenKohdalla.getTunnusNro());
        }
        
    private void naytaHeikkoudet(Jasen jasen) {
        tableHeikkoudet.clear();
        if ( jasen == null ) return;         
        try {
            List<Heikkous> heikkoudet;
            heikkoudet = supersankarit.annaHeikkoudet(jasen);
            if ( heikkoudet.size() == 0 ) return;
            for(Heikkous hei:heikkoudet)
            naytaHeikkous(hei);
        } catch (SailoException e) {
            naytaVirhe(e.getMessage());
        } 
    }
    
    /**
     * 
     * @param hei heikkous
     */
    private void naytaHeikkous(Heikkous hei) {
     int kenttia = hei.getKenttia();
     String[] rivi = new String[kenttia - hei.ensimmainenKentta()];
     for ( int i=0, k=hei.ensimmainenKentta(); k < kenttia ; i++, k++)
    rivi[i] = hei.anna(k);
    tableHeikkoudet.add(hei,rivi);
    }
    
    /**
     * 
     * @param jasen jonka vihamies n�ytet��n
     */
    private void naytaVihamiehet(Jasen jasen) {
        tableVihamiehet.clear();
        if ( jasen == null ) return;         
        try {
            List<Vihamies> vihamiehet;
            vihamiehet = supersankarit.annaVihamiehet(jasen);
            if ( vihamiehet.size() == 0 ) return;
            for(Vihamies vih:vihamiehet)
            naytaVihamies(vih);
        } catch (SailoException e) {
            naytaVirhe(e.getMessage());
        } 
    }
    
    /**
     *  
     * @param vih vihamies, joka n�ytet��n
     */
    private void naytaVihamies(Vihamies vih) {
     int kenttia = vih.getKenttia();
     String[] rivi = new String[kenttia - vih.ensimmainenKentta()];
     for ( int i=0, k=vih.ensimmainenKentta(); k < kenttia ; i++, k++)
    rivi[i] = vih.anna(k);
    tableVihamiehet.add(vih,rivi);
    }
    
    
    /**
     * 
     * @param jasen jonka supervoima n�ytet��n
     */
    private void naytaSupervoimat(Jasen jasen) {
        tableSupervoimat.clear();
        if ( jasen == null ) return;         
        try {
            List<Supervoima> supervoimat;
            supervoimat = supersankarit.annaSupervoimat(jasen);
            if ( supervoimat.size() == 0 ) return;
            for(Supervoima sup:supervoimat)
            naytaSupervoima(sup);
        } catch (SailoException e) {
            naytaVirhe(e.getMessage());
        } 
    }
    
    /**
     * 
     * @param sup joka n�ytet��n
     */
    private void naytaSupervoima(Supervoima sup) {
     int kenttia = sup.getKenttia();
     String[] rivi = new String[kenttia - sup.ensimmainenKentta()];
     for ( int i=0, k=sup.ensimmainenKentta(); k < kenttia ; i++, k++)
    rivi[i] = sup.anna(k);
    tableSupervoimat.add(sup,rivi);
    }
        /**
         * Lis�t��n uusi supervoima.
         */
        public void uusiSuperVoima() {
            if (jasenKohdalla == null) return;
            Supervoima sup = new Supervoima();
            sup.rekisteroi();
            sup.vastaaSeitinHeitto(jasenKohdalla.getTunnusNro());
            try {
            	supersankarit.lisaa(sup);
            }catch (SailoException e) {
            	Dialogs.showMessageDialog("Ongelma! " + e.getMessage());
            }
            hae(jasenKohdalla.getTunnusNro());
        }
        
        /**
         * Lis�t��n uusi vihamies.
         */
        public void uusiVihaMies() {
            if (jasenKohdalla == null) return;
            Vihamies vih = new Vihamies();
            vih.rekisteroi();
            vih.vastaaGreenGoblin(jasenKohdalla.getTunnusNro());
            try {
            	supersankarit.lisaa(vih);
            }catch (SailoException e) {
            	Dialogs.showMessageDialog("Ongelma! " + e.getMessage());
            }
            hae(jasenKohdalla.getTunnusNro());
        }
        

        /**
         * @param supersankarit supersankarit jota k�ytet��n t�ss� k�ytt�liittym�ss�
         */
        public void setSuperSankarit(Supersankarit supersankarit) {
            this.supersankarit = supersankarit;
            naytaJasen();
        }

        
}        