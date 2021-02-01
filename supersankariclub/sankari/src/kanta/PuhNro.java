package kanta;

/** Tehd‰‰n satunnainen puhelinnumero, ett‰ voidaan testimieless‰ erottaa supersankarit toisistaan.
 * @author Arttu Nikkil‰
 * @version 26.3.2017
 *
 */
public class PuhNro {
    
    /**
     * Arvotaan satunnainen kokonaisluku v‰lille [ala,yla]
     * @param ala arvonnan alaraja
     * @param yla arvonnan yl‰raja
     * @return satunnainen luku v‰lilt‰ [ala,yla]
     */
    public static int rand(int ala, int yla) {
      double n = (yla-ala)*Math.random() + ala;
      return (int)Math.round(n);
    }

    
    /**
     * Arvotaan satunnainen puhelinnumero edellist‰ rand-aliohjelmaa hyv‰ksik‰ytt‰en
     * @return satunnainen puhelinnumero
     */
    public static String arvoPuhNro() {
        String nro = String.format("%02d",rand(1,9))   +
        String.format("%02d",rand(1,9))  +
        String.format("%02d",rand(1,9));
        return nro;        
    }
 
}
