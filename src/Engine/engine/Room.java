package EnginePyrskiRobert.engine;

import EnginePyrskiRobert.math2D.Point2D;
import EnginePyrskiRobert.math3D.Point3D;
import EnginePyrskiRobert.math3D.Vector3D;
import GAME_UNTRUST_PYRSKI_ROBERT_ROMANOVICH.yourbot.MyBot;

import java.awt.Graphics2D;
import java.util.*;

public class Room {

    public static final String DRECTORY_OF_MODELS = "D://Модельки для моей игры Untrust us";

    private static Room room;
    public static final Vector3D vector_sun = new Vector3D(0, 0, -1);

    private MyFrame frame;
    private final ArrayList<MyObject3D> my_objects_3D = new ArrayList<MyObject3D>();
    Camera camera = new Camera(new Point3D(0, 0, 0), 0, 0);
    Person person = new Person(camera);
    private Listener listener;
    private Graphics2D g;
    private Updation updation;
    private MyBot myBot = new MyBot(new Point3D(0, 0, 0), person);

    public static final String directoryName = "C://MY_GAME";

    private Room() {
        loadFiles();
    }

    public static Room getRoom() {
        return room;
    }

    public static void main(String[] args) {
        room = new Room();
        Listener listener = new Listener(room);
        room.setListener(listener);
        Updation updation = new Updation(room);
        room.setUpdation(updation);
        room.setFrame(new MyFrame("Untrust us", new Point2D(0, 0), new Point2D(1920, 1080), true, false, new MyFrame.Painting() {
            @Override
            public Graphics2D print(Graphics2D g) {
                room.g = g;
                updation.update = true;
                while (updation.update) {
                }
                return g;
            }
        }, listener, listener, listener));
        updation.start();
        for (; true; ) {
            room.getFrame().draw();
            while (updation.update) {
            }
        }
    }

    private void loadFiles() {
        my_objects_3D.add(myBot);
        my_objects_3D.add(new MyObject3D("D://Mountains.txt", new Point3D(0, 0, 0), 1, 0, (float) Math.toRadians(0)));
//        System.out.println(my_objects_3D.get(0).faces.size());
//        my_objects_3D.add(yourBot);
//        System.out.println(my_objects_3D.get(0).faces.size());
//		my_objects_3D.add(new Map());
    }

    public void draw(Graphics2D g) {
        System.out.println("gg");
        updation.setG(g);
        System.out.println("g");
    }


    public Camera getCamera() {
        return camera;
    }

    public ArrayList<MyObject3D> getMy_objects_3D() {
        return my_objects_3D;
    }

    public Person getPerson() {
        return person;
    }


    public Graphics2D getG() {
        return g;
    }

    public Listener getListener() {
        return listener;
    }

    public Updation getUpdation() {
        return updation;
    }

    public void setUpdation(Updation updation) {
        this.updation = updation;
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public void setFrame(MyFrame frame) {
        this.frame = frame;
    }

    public MyFrame getFrame() {
        return frame;
    }
}
