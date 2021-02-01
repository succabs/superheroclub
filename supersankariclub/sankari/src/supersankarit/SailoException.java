package supersankarit;

/**
 * Poikkeusluokka tietorakenteesta aiheutuville poikkeuksille.
 * @author Arttu Nikkilä
 * @version 28.3.2017
 */
public class SailoException extends Exception {
    private static final long serialVersionUID = 1L;


    /**
     * Poikkeuksen muodostaja jolle tuodaan poikkeuksessa
     * käytettävä viesti
     * @param viesti Poikkeuksen viesti
     */
    public SailoException(String viesti) {
        super(viesti);
    }
}
