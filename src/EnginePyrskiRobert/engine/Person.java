package EnginePyrskiRobert.engine;

import EnginePyrskiRobert.math3D.Point3D;
import EnginePyrskiRobert.math3D.Vector3D;

import java.util.ArrayList;

public final class Person {

    private final Camera camera;

    public Person(Camera camera) {
        this.camera = camera;
        new Thread(() -> {
            for (; true; ) {
                update();
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                }
            }
        }).start();
    }

    public boolean up, down, left, right, forward, back;
    private final float speed_moving = 10 / 120.0f;
    private final float speed_rotation = 4_000;

    private void move() {
        ArrayList<Vector3D> vector3DS = new ArrayList<>();
        if (forward && !back) {
//            camera.setNextPosition(camera.getForwardVector(), 1, speed_moving);
            vector3DS.add(camera.getForwardVector());
        }
        if (!forward && back) {
//            camera.setNextPosition(camera.getForwardVector(), -1, speed_moving);
            vector3DS.add(camera.getForwardVector().getRotationPiRadian());
        }
        if (right && !left) {
//            camera.setNextPosition(camera.getRightVector(), 1, speed_moving);
            vector3DS.add(camera.getRightVector());
        }
        if (!right && left) {
//            camera.setNextPosition(camera.getRightVector(), -1, speed_moving);
            vector3DS.add(camera.getRightVector().getRotationPiRadian());
        }
        if (up && !down) {
//            camera.setNextPosition(camera.getUpVector(), 1, speed_moving);
            vector3DS.add(camera.getUpVector());
        }
        if (!up && down) {
//            camera.setNextPosition(camera.getUpVector(), -1, speed_moving);
            vector3DS.add(camera.getUpVector().getRotationPiRadian());
        }
        Vector3D resultVector = new Vector3D(0, 0, 0);
        for (Vector3D vector3D : vector3DS) {
            resultVector = resultVector.plus(vector3D.normalize());
        }
        if (vector3DS.isEmpty()) return;
        camera.setNextPosition(resultVector.normalize(), 1, speed_moving);
    }

    public void update() {
        move();
//		camera.realFps++;
    }

    public Point3D getLocation() {
        return camera;
    }

}
