package fxsupersankarit;

/**
 * @author Arttu
 * @version 20.4.2017
 *
 */
import java.net.URL;
import java.util.ResourceBundle;
import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import fi.jyu.mit.ohj2.Mjonot;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import supersankarit.Jasen;
 
  /**
   * Painaessa uuden sankarin lis‰ysnappia, tulee esiin t‰m‰ dialogi
   *
   * @author Arttu Nikkil‰
   * @version 25.4.2017
   *
   */
  public class SankariDialogController implements ModalControllerInterface<Jasen>,Initializable  {
  
      @FXML private ScrollPane panelJasen;
      @FXML private GridPane gridJasen;
      @FXML private Label labelVirhe;
  
      @Override
      public void initialize(URL url, ResourceBundle bundle) {
          alusta(); 
      }
     
      @FXML private void handleOK() {
          if ( jasenKohdalla != null && jasenKohdalla.getNimi().trim().equals("") ) {
              naytaVirhe("Sankarilla on oltava nimi.");
          }
          if ( jasenKohdalla != null && jasenKohdalla.getVuosi() <= 0 ) {
                  naytaVirhe("Liittymisvuoden on oltava suurempi kuin nolla.");
              return;
          }
          ModalController.closeStage(labelVirhe);
      }
  
     
      @FXML private void handleCancel() {
          jasenKohdalla = null;
          ModalController.closeStage(labelVirhe);
      }
  
  // ========================================================   
      private Jasen jasenKohdalla;
      private static Jasen apujasen = new Jasen(); // J‰sen, jolta kysell‰n tietoja
      private TextField[] edits;
      private int kentta = 0;
     
  
      /**
       * Luodaan GridPaneen j‰senen tiedot
       * @param gridJasen mihin tiedot luodaan
       * @return luodut tekstikent‰t
       */
      public static TextField[] luoKentat(GridPane gridJasen) {
          gridJasen.getChildren().clear();
          TextField[] edits = new TextField[apujasen.getKenttia()];
         
          for (int i=0, k = apujasen.ekaKentta(); k < apujasen.getKenttia(); k++, i++) {
              Label label = new Label(apujasen.getKysymys(k));
              gridJasen.add(label, 0, i);
              TextField edit = new TextField();
              edits[k] = edit;
              edit.setId("e"+k);
              gridJasen.add(edit, 1, i);
          }
          return edits;
      }
     
  
      /**
       * Tyhjent‰‰n tekstikent‰t
       * @param edits tyhjennett‰v‰t kent‰t
       */
      public static void tyhjenna(TextField[] edits) {
          for (TextField edit: edits) 
              if ( edit != null ) {
                  edit.setText(""); 
              }
      }
  
      /**
       * Palautetaan komponentin id:st‰ saatava luku
       * @param obj tutkittava komponentti
       * @param oletus mik‰ arvo jos id ei ole kunnollinen
       * @return komponentin id lukuna
       */
      public static int getFieldId(Object obj, int oletus) {
          if ( !( obj instanceof Node)) return oletus;
          Node node = (Node)obj;
          return Mjonot.erotaInt(node.getId().substring(1),oletus);
      }
    
    
     /**
      * Tekee tarvittavat muut alustukset, nyt vaihdetaan GridPanen tilalle
      * yksi iso tekstikentt‰, johon voidaan tulostaa j‰senten tiedot.
      */
     protected void alusta() {
         edits = luoKentat(gridJasen);
         for (TextField edit : edits)
             if ( edit != null )
                 edit.setOnKeyReleased( e -> kasitteleMuutosJaseneen((TextField)(e.getSource())));
         panelJasen.setFitToHeight(true);
     }
   
    
     @Override
     public void setDefault(Jasen oletus) {
         jasenKohdalla = oletus;
         naytaJasen(edits, jasenKohdalla);
     }
 
    
     @Override
     public Jasen getResult() {
         return jasenKohdalla;
     }
    
    
     private void setKentta(int kentta) {
         this.kentta = kentta;
     }
    
    
     /**
      * Mit‰ tehd‰‰n kun dialogi on n‰ytetty
      */
     @Override
     public void handleShown() {
         kentta = Math.max(apujasen.ekaKentta(), Math.min(kentta, apujasen.getKenttia()-1));
         edits[kentta].requestFocus();
     }
    
    
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
      * K‰sitell‰‰n j‰seneen tullut muutos
      * @param edit muuttunut kentt‰
      */
     protected void kasitteleMuutosJaseneen(TextField edit) {
         if (jasenKohdalla == null) return;
         int k = getFieldId(edit,apujasen.ekaKentta());
         String s = edit.getText();
         String virhe = null;
         virhe = jasenKohdalla.aseta(k,s); 
         if (virhe == null) {
             Dialogs.setToolTipText(edit,"");
             edit.getStyleClass().removeAll("virhe");
             naytaVirhe(virhe);
             
         } else {
             Dialogs.setToolTipText(edit,virhe);
             edit.getStyleClass().add("virhe");
             naytaVirhe(virhe);
             
         }
     }
    
    
     /**
      * N‰ytet‰‰n j‰senen tiedot TextField komponentteihin
      * @param edits taulukko TextFieldeist‰ johon n‰ytet‰‰n
      * @param jasen n‰ytett‰v‰ j‰sen
      */
     public static void naytaJasen(TextField[] edits, Jasen jasen) {
         if (jasen == null) return;
         for (int k = jasen.ekaKentta(); k < jasen.getKenttia(); k++) {
             edits[k].setText(jasen.anna(k));
         }
     }
    
    
     /**
      * Luodaan j‰senen kysymisdialogi ja palautetaan sama tietue muutettuna tai null
      * TODO: korjattava toimimaan
      * @param modalityStage mille ollaan modaalisia, null = sovellukselle
      * @param oletus mit‰ dataan n‰ytet‰‰n oletuksena
      * @param kentta mik‰ kentt‰ saa fokuksen kun n‰ytet‰‰n
      * @return null jos painetaan Cancel, muuten t‰ytetty tietue
      */
     public static Jasen kysyJasen(Stage modalityStage, Jasen oletus, int kentta) {
         return ModalController.<Jasen, SankariDialogController>showModal(
                     SankariDialogController.class.getResource("SankariDialogView.fxml"),
                     "Sankari",
                     modalityStage, oletus,
                      ctrl -> ctrl.setKentta(kentta) 
                 );
     }
 
}