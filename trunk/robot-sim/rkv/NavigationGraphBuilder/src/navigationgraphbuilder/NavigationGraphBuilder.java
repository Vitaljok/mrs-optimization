/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package navigationgraphbuilder;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Transparency;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author Vitaljok
 */
public class NavigationGraphBuilder {

    public class MyPoint extends Point {

        Integer id;

        public MyPoint(Integer id, int x, int y) {
            super(x, y);
            this.id = id;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }
    }

    public class CircularArrayList<E> extends ArrayList<E> {

        @Override
        public E get(int index) {

            index = index % this.size();

            if (index < 0) {
                index += this.size();
            }

            return super.get(index);
        }

        public CircularArrayList() {
        }

        public CircularArrayList(Collection<? extends E> c) {
            super(c);
        }
    }
    Color backColor = Color.BLACK;
    Color foreColor = Color.WHITE;
    BufferedImage image;

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    private Color getPixelColor(int x, int y) {
        if (x < 0
                || x >= this.image.getWidth()
                || y < 0
                || y >= this.image.getHeight()) {
            return backColor;
        } else {
            return new Color(this.image.getRGB(x, y));
        }
    }

    private boolean isCorner(int x, int y, Color c1, Color c2) {
        //top-left
        if (getPixelColor(x - 1, y).equals(c1)
                && getPixelColor(x, y - 1).equals(c1)
                && getPixelColor(x - 1, y - 1).equals(c2)) {
            return true;
        }

        //top-right
        if (getPixelColor(x + 1, y).equals(c1)
                && getPixelColor(x, y - 1).equals(c1)
                && getPixelColor(x + 1, y - 1).equals(c2)) {
            return true;
        }

        //bottom-left
        if (getPixelColor(x - 1, y).equals(c1)
                && getPixelColor(x, y + 1).equals(c1)
                && getPixelColor(x - 1, y + 1).equals(c2)) {
            return true;
        }

        //bottom-right
        if (getPixelColor(x + 1, y).equals(c1)
                && getPixelColor(x, y + 1).equals(c1)
                && getPixelColor(x + 1, y + 1).equals(c2)) {
            return true;
        }
        return false;
    }

    private boolean isInnerCorner(int x, int y) {
        return isCorner(x, y, backColor, backColor);
    }

    private boolean isOuterCorner(int x, int y) {
        return isCorner(x, y, foreColor, backColor);
    }

    public void findVertices() {
        Graphics2D gra = this.image.createGraphics();
        gra.setStroke(new BasicStroke(0));

        for (int j = 0; j < this.image.getHeight(); j++) {
            for (int i = 0; i < this.image.getWidth(); i++) {
                if (getPixelColor(i, j).equals(foreColor)) {
                    if (isOuterCorner(i, j)) {
                        gra.setColor(Color.RED);
                        gra.fillOval(i - 2, j - 2, 5, 5);
                    }

                    if (isInnerCorner(i, j)) {
                        gra.setColor(Color.BLUE);
                        gra.fillOval(i - 2, j - 2, 5, 5);
                    }
                }
            }

        }
    }

    public List<MyPoint> followWalls(int startX, int startY) {

        List<MyPoint> path = new ArrayList<>();

        //move to right until hit wall
        while (getPixelColor(startX + 1, startY).equals(this.foreColor)) {
            startX++;
        }

        int x = startX;
        int y = startY;

        // face down
        int dx = 0;
        int dy = 1;

        do {
            if (isInnerCorner(x, y)) {
                // turn right
                if (dy > 0) {
                    dx = -1;
                    dy = 0;
                } else if (dx < 0) {
                    dx = 0;
                    dy = -1;
                } else if (dy < 0) {
                    dx = 1;
                    dy = 0;
                } else if (dx > 0) {
                    dx = 0;
                    dy = 1;
                }

                path.add(new MyPoint(path.size(), x, y));

            } else if (isOuterCorner(x, y)) {
                //trun left
                if (dy < 0) {
                    dx = -1;
                    dy = 0;
                } else if (dx < 0) {
                    dx = 0;
                    dy = 1;
                } else if (dy > 0) {
                    dx = 1;
                    dy = 0;
                } else if (dx > 0) {
                    dx = 0;
                    dy = -1;
                }

                path.add(new MyPoint(path.size(), x, y));
            }

            //step
            x += dx;
            y += dy;

        } while (x != startX || y != startY);

        return path;
    }

    private void removeEar(CircularArrayList<MyPoint> points, int index) {
    }

    private boolean isConvex(Point a, Point b, Point c) {

        int area = 0;
        area += a.x * (c.y - b.y);
        area += b.x * (a.y - c.y);
        area += c.x * (b.y - a.y);

        return area > 0;
    }

    private boolean isInsideTriangle(Point a, Point b, Point c, Point test) {

        int v1 = (a.x - test.x) * (b.y - a.y) - (b.x - a.x) * (a.y - test.y);
        int v2 = (b.x - test.x) * (c.y - b.y) - (c.x - b.x) * (b.y - test.y);
        int v3 = (c.x - test.x) * (a.y - c.y) - (a.x - c.x) * (c.y - test.y);

        if ((v1 >= 0 && v2 >= 0 && v3 >= 0) || (v1 <= 0 && v2 <= 0 && v3 <= 0)) {
            return true;
        } else {
            return false;
        }
    }

    public BufferedImage triangulate(List<MyPoint> points) {

        List<Line2D> edges = new ArrayList<>();

        Polygon poly = new Polygon();
        poly.addPoint(points.get(0).x, points.get(0).y);
        for (int i = 1; i < points.size(); i++) {
            poly.addPoint(points.get(i).x, points.get(i).y);
            edges.add(new Line2D.Float(points.get(i - 1), points.get(i)));
        }
        edges.add(new Line2D.Float(points.get(points.size() - 1), points.get(0)));

        BufferedImage result = new BufferedImage(this.image.getWidth(), this.image.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D gra = result.createGraphics();

        gra.fillRect(0, 0, result.getWidth(), result.getHeight());
        gra.setColor(Color.RED);
        gra.setStroke(new BasicStroke(1));
        gra.drawPolygon(poly);

        CircularArrayList<MyPoint> pts = new CircularArrayList<>(points);

        for (int i = 0; i < pts.size(); i++) {
            if (isConvex(pts.get(i), pts.get(i - 1), pts.get(i + 1))) {
                gra.setColor(Color.BLUE);
            } else {
                gra.setColor(Color.RED);
            }

            gra.drawOval(pts.get(i).x - 2, pts.get(i).y - 2, 4, 4);
        }


        List<Line2D> lines = new ArrayList<>();
        CircularArrayList<MyPoint> workPoints = new CircularArrayList<>(points);

        Random rnd = new Random();
        int index = 0;

        while (workPoints.size() > 3) {
            MyPoint curr = workPoints.get(index);
            MyPoint prev = workPoints.get(index - 1);
            MyPoint next = workPoints.get(index + 1);

            if (isConvex(curr, prev, next)) {
                boolean isEar = true;

                for (MyPoint p : workPoints) {
                    if (p != curr && p != prev && p != next) {
                        if (isInsideTriangle(curr, prev, next, p)) {
                            isEar = false;
                            break;
                        }
                    }
                }

                if (isEar) {
                    lines.add(new Line2D.Float(prev, next));
                    workPoints.remove(curr);
                    //index--;
                }
            }

            index++;

            Polygon ply = new Polygon();
            for (MyPoint p : workPoints) {
                ply.addPoint(p.x, p.y);
            }
        }

        gra.setColor(Color.DARK_GRAY);
        for (Line2D line : lines) {
            gra.draw(line);
        }

        return result;

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        BufferedImage pngImage = ImageIO.read(new File("autolab.png"));
        GraphicsConfiguration config = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();

        BufferedImage image = config.createCompatibleImage(pngImage.getWidth(null), pngImage.getHeight(null), Transparency.OPAQUE);
        Graphics2D graphics = image.createGraphics();
        graphics.drawImage(pngImage, 0, 0, null);
        graphics.dispose();

        NavigationGraphBuilder builder = new NavigationGraphBuilder();
        builder.setImage(image);

        //BufferedImage result = builder.triangulate(builder.followWalls(100, 100));
        Graphics2D gra = image.createGraphics();
        List<MyPoint> path1 = builder.followWalls(100, 100);
        List<MyPoint> path2 = builder.followWalls(100, 400);

        gra.setColor(Color.red);
        Polygon poly1 = new Polygon();
        for (MyPoint p : path1) {
            poly1.addPoint(p.x, p.y);
        }
        gra.drawPolygon(poly1);

        gra.setColor(Color.blue);
        Polygon poly2 = new Polygon();
        for (MyPoint p : path2) {
            poly2.addPoint(p.x, p.y);
        }
        gra.drawPolygon(poly2);


        int minDist = Integer.MAX_VALUE;
        MyPoint pA = null, pB = null;

        for (MyPoint p1 : path1) {
            for (MyPoint p2 : path2) {
                int dist = (p1.x - p2.x) * (p1.x - p2.x) 
                        + (p1.y - p2.y) * (p1.y - p2.y);
                
                if (dist < minDist) {
                    minDist = dist;
                    pA = p1;
                    pB = p2;
                }
            }
        }
        
        gra.drawOval(pA.x-2, pA.y-2, 4, 4);
        gra.drawOval(pB.x-2, pB.y-2, 4, 4);
        
        System.out.println("Dist: "+ Math.sqrt(minDist));
        System.out.println(pA);
        System.out.println(pB);

        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;




                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainJFrame.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainJFrame.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainJFrame.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainJFrame.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        MainJFrame main = new MainJFrame(image);
        main.setVisible(true);





    }
}
