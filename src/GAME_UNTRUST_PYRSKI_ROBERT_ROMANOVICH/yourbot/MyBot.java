package GAME_UNTRUST_PYRSKI_ROBERT_ROMANOVICH.yourbot;

import EnginePyrskiRobert.engine.MyObject3D;
import EnginePyrskiRobert.engine.Person;
import EnginePyrskiRobert.engine.Room;
import EnginePyrskiRobert.engine.Updation;
import EnginePyrskiRobert.math3D.Point3D;
import EnginePyrskiRobert.math3D.Vector3D;

public class MyBot extends MyObject3D {

    public static final String PATH = Room.DRECTORY_OF_MODELS+"//YourBot.txt";
    private static final int SIZE = 1;
    private final Person PERSON;
    private Thread life;
    private final float SPEED = 10.0f / Updation.FPS;

    private static final int MIN_RANGE_WITH_PLAYER = 10;

    public MyBot(Point3D location, Person person) {
        super(PATH, location, SIZE, 0, 0);
        PERSON = person;
        setLife();
        life.start();
    }

    public void setLife() {
        life = new Thread(this::work);
    }

    private void work() {
        for (;!Thread.currentThread().isInterrupted();) {
            try {
                Thread.sleep((int)(1000.0/Updation.FPS));
            } catch (InterruptedException _) {
            }
            Vector3D vector3D = Vector3D.getVectorFromPointToPoint(this, PERSON.getLocation()).normalize();
            move(vector3D);
        }
    }

    private void move(Vector3D vector3D) {
        if (Point3D.getDistance(this,  PERSON.getLocation()) <= MIN_RANGE_WITH_PLAYER) return;
        x+=vector3D.getX()* SPEED;
        y+=vector3D.getY()* SPEED;
        z+=vector3D.getZ()* SPEED;
    }

}
