/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package navigationgraphbuilder;

import java.awt.Color;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 *
 * @author Vitaljok
 */
public class ObjectColorer {

    private Color backColor;
    private Color foreColor;
    private Map<Color, List<Point>> map = new HashMap<>();
    private Random random = new Random();

    class Mask {

        Color A;
        Color B;
        Color C;

        public Mask(BufferedImage img, int x, int y) {
            this.A = new Color(img.getRGB(x, y));

            if (x > 0) {
                this.B = new Color(img.getRGB(x - 1, y));
            } else {
                this.B = backColor;
            }

            if (y > 0) {
                this.C = new Color(img.getRGB(x, y - 1));
            } else {
                this.C = backColor;
            }
        }
    }
    private List<Color> usedColors = new ArrayList<>();
    private List<Color> colors = new ArrayList<>(Arrays.asList(Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN, Color.CYAN, Color.BLUE, Color.MAGENTA));

    private void setColor(BufferedImage img, int x, int y, Color color) {
        img.setRGB(x, y, color.getRGB());
        if (map.get(color) == null) {
            map.put(color, new ArrayList<Point>());
        }

        map.get(color).add(new Point(x, y));
    }

    public Color getBackColor() {
        return backColor;
    }

    public void setBackColor(Color backColor) {
        this.backColor = backColor;
    }

    public Color getForeColor() {
        return foreColor;
    }

    public void setForeColor(Color foreColor) {
        this.foreColor = foreColor;
    }
       
    public List<Color> doColoring(BufferedImage img) {

        for (int j = 0; j < img.getHeight(); j++) {
            for (int i = 0; i < img.getWidth(); i++) {
                Mask mask = new Mask(img, i, j);

                if (!mask.A.equals(backColor)) {
                    if (mask.B.equals(backColor)
                            && mask.C.equals(backColor)) {

                        if (colors.isEmpty()) {
                            //all colors used, add random
                            colors.add(new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256)));
                        }

                        Color newColor = colors.remove(0);
                        usedColors.add(newColor);
                        setColor(img, i, j, newColor);

                    } else if (!mask.B.equals(backColor)
                            && !mask.C.equals(backColor)
                            && !mask.B.equals(mask.C)) {

                        List<Point> listB = map.get(mask.B);
                        List<Point> listC = map.get(mask.C);

                        List<Point> src;
                        Color srcColor;
                        Color dstColor;

                        if (listB.size() < listC.size()) {
                            src = listB;
                            srcColor = mask.B;
                            dstColor = mask.C;
                        } else {
                            src = listC;
                            srcColor = mask.C;
                            dstColor = mask.B;
                        }

                        colors.add(srcColor);
                        usedColors.remove(srcColor);

                        map.remove(srcColor);
                        for (Point point : src) {
                            setColor(img, point.x, point.y, dstColor);
                        }

                        setColor(img, i, j, dstColor);

                    } else if (!mask.B.equals(backColor)) {
                        setColor(img, i, j, mask.B);
                    } else if (!mask.C.equals(backColor)) {
                        setColor(img, i, j, mask.C);
                    }
                }
            }
        }
        return usedColors;
    }
    
    public void mask(BufferedImage img, Color color) {
        for (int j = 0; j < img.getHeight(); j++) {
            for (int i = 0; i < img.getWidth(); i++) {
                if (img.getRGB(i, j) != color.getRGB())
                    img.setRGB(i, j, this.backColor.getRGB());
                else
                    img.setRGB(i, j, this.foreColor.getRGB());
            }
            
        }
    }
    
    public void mask(BufferedImage img, int x, int y) {
        this.mask(img, new Color(img.getRGB(x, y)));
    }
}
