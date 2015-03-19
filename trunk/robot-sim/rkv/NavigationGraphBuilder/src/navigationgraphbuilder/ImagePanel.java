/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package navigationgraphbuilder;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.JPanel;

/**
 *
 * @author Vitaljok
 */
public class ImagePanel extends JPanel {
    Image image;

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if (image != null) {
            g.drawImage(image, 0, 0, null);
        }
    }
    
}
