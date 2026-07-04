package EnginePyrskiRobert.engine;

import java.awt.*;
import java.awt.event.*;


public class Listener implements KeyListener, MouseListener, MouseMotionListener, MouseWheelListener {

    private Camera camera = null;
    private Person person;
    private int lastx = 508, lasty = 508;

    public Listener(Room room) {
        this.camera = room.getCamera();
        this.person = room.getPerson();
        treadCameraRotation.start();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        pressButton(e, false);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        pressButton(e, true);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        moveMouse(e);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        moveMouse(e);
    }

    public void moveMouse(MouseEvent e) {
        if (e.getX() == lastx && e.getY() == lasty) {
            return;
        }
        int distancex = e.getX() - lastx;
        int distancey = e.getY() - lasty;
        camera.rotationRight += distancex*10;  //*10
        camera.rotationUp -= distancey *10;  //*10
        if (camera.rotationUp > 4000) {
            camera.rotationUp = 4000;
        }
        if (camera.rotationUp < -4000) {
            camera.rotationUp = -4000;
        }
        lastx = e.getX();
        lasty = e.getY();
        camera.updateDirection();
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {

    }

    Thread treadCameraRotation=new Thread(()->{
        for (;;) {
            try {
                Robot robot = new Robot();
                robot.mouseMove(500,500);
                lastx=508;
                lasty=508;
                Thread.sleep(100);
            } catch (Exception e) {
                e.printStackTrace();
            }
            camera.setDirection(camera.rotationRight, camera.rotationUp);
        }
    });

    private void pressButton(KeyEvent e, boolean stateButton) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                person.forward = stateButton;
                break;
            case KeyEvent.VK_A:
                person.left = stateButton;
                break;
            case KeyEvent.VK_S:
                person.back = stateButton;
                break;
            case KeyEvent.VK_D:
                person.right = stateButton;
                break;
            case KeyEvent.VK_SPACE:
                person.up = stateButton;
                break;
            case KeyEvent.VK_SHIFT:
                person.down = stateButton;
                break;
            case KeyEvent.VK_ESCAPE:
                System.exit(0);
                break;
        }
    }

}
