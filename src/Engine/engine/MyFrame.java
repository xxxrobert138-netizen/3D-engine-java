package EnginePyrskiRobert.engine;

import EnginePyrskiRobert.math2D.Point2D;

import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JComponent;
import javax.swing.JFrame;

public final class MyFrame extends JFrame {

    private MyComponent myComponent;

    public MyFrame(
            String name,
            Point2D startPoint,
            Point2D endPoint,
            boolean isvisible,
            boolean isresizable,
            Painting painting,
            KeyListener keyListener,
            MouseListener mouseListener,
            MouseMotionListener mouseMotionListener) {
        super(name);
        this.setBounds((int) startPoint.getX(), (int) startPoint.getY(), (int) (endPoint.getX() - startPoint.getX()), (int) (endPoint.getY() - startPoint.getY()));
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setResizable(isresizable);
        this.setVisible(isvisible);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        if (painting != null) {
            this.add((myComponent = new MyComponent(painting)));
        }
        if (keyListener != null) {
            this.addKeyListener(keyListener);
        }
        if (mouseListener != null) {
            this.addMouseListener(mouseListener);
        }
        if (mouseMotionListener != null) {
            this.addMouseMotionListener(mouseMotionListener);
        }
    }

    public void draw() {
        if (myComponent.getPainting() == null) printWarning();
        myComponent.repaint();
    }

    private static void printWarning() {
        System.out.println("This object cant use this method");
        System.exit(0);
    }

    private class MyComponent extends JComponent {

        private Painting painting;

        public MyComponent(Painting painting) {
            this.painting = painting;
        }

        public Graphics2D g;

        public Painting getPainting() {
            return painting;
        }

        @Override
        protected void paintComponent(Graphics g) {
            this.g = (Graphics2D) g;
            painting.print((Graphics2D) g);
        }

    }

    public Graphics2D getG() {
        return myComponent.g;
    }

    public interface Painting {
        Graphics2D print(Graphics2D g);
    }

}
