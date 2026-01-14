import java.awt.Image;
import javax.swing.ImageIcon;
/**
 * Class that scales button icons to the correct size
 */
public class ImageScaler {
    /**
     * method to scale button icons to the correct size
     * @param filename of the icon
     * @return the formatted icon
     */
    public static ImageIcon scaleImage(String filename) {

        ImageIcon icon = new ImageIcon(filename);
        Image temp = icon.getImage();
        Image scaledTemp = temp.getScaledInstance(15, 15, java.awt.Image.SCALE_SMOOTH);
        icon = new ImageIcon(scaledTemp);
        
        return icon;

    }
}
