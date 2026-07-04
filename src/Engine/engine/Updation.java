package EnginePyrskiRobert.engine;

import EnginePyrskiRobert.math2D.Point2D;
import EnginePyrskiRobert.math3D.Face;
import EnginePyrskiRobert.math3D.Point3D;
import EnginePyrskiRobert.math3D.Vector3D;


import java.awt.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.TreeMap;
import java.util.concurrent.*;


public class Updation {

    private final int countThreads;
    private final ScheduledExecutorService executorService;
    private final Phaser phaser;
    private Camera camera;
    private Graphics2D g;
    private Room room;
    public volatile boolean update = false;
    public final static int FPS = 120;
    private static int trueFPS = 0;
    private static int lastTrueFPS = 0;
    private static int countFaces = 0;
    private volatile static int totalCountFaces = 0;
    private Thread updaterFPS = new Thread(() -> {
        for (; true; ) {
            lastTrueFPS = trueFPS;
            trueFPS = 0;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
        }
    });

    public Updation(Room room) {
        this.room = room;
        this.camera = room.getCamera();
        this.g = room.getG();
        countThreads = 1;                    //Runtime.getRuntime().availableProcessors();
        phaser = new Phaser(countThreads);
        executorService = Executors.newScheduledThreadPool(countThreads);
        updaterFPS.start();
        for (MyObject3D object3D : room.getMy_objects_3D()) {
            System.out.println(object3D);
            countFaces += object3D.faces.size();
        }
    }

    public void start() {
        for (int i = 0; i < countThreads; i++) {
            int finalI = i;
            Runnable task = () -> {
                try {
                    draw(finalI);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            };
            executorService.scheduleAtFixedRate(task, 0, (int) (1000 * 1.0 / FPS), TimeUnit.MILLISECONDS);
        }
    }

    public void exit() {
    }

    private void updateProjection(MyObject3D obj, HashSet<Integer> numbers, int numberThread) {
        int i = -1;
        for (Integer integer : numbers) {
            if ((++i) % countThreads != numberThread) continue;
            Point3D newPoint = obj.getPointAfterDirectionAndLocation(obj.vertices.get(integer));
//            System.out.println(numberThread + " " + newPoint.toString());
            obj.projectionPoints[i] = camera.convertPoint3Dto2D(newPoint);
        }
    }

    private void draw(int numberThread) throws Exception {
        if (!update) return;
        if (numberThread == 0) {
            g = room.getG();
            drawBackground();
            phaser.arriveAndAwaitAdvance();
            drawObjects(numberThread);
            drawHaracteristics();
            drawAim();
            phaser.arriveAndAwaitAdvance();
            trueFPS++;
        } else {
            phaser.arriveAndAwaitAdvance();
            drawObjects(numberThread);
            phaser.arriveAndAwaitAdvance();
        }
        update = false;
    }


    private void drawObjects(int numberThread) {
        Room.getRoom().getMy_objects_3D().forEach(obj -> {
            HashSet<Integer> numbers = new HashSet<>();
            TreeMap<Integer, HashSet<Integer>> numbers_triangles = new TreeMap<>();
            updateFaces(obj, numberThread);
            arrangeFaces(obj, numbers, numbers_triangles, numberThread);
            updateProjection(obj, numbers, numberThread);
            phaser.arriveAndAwaitAdvance();
            if (numberThread == 0) {
//                System.out.println(Arrays.toString(obj.projectionPoints));
                totalCountFaces = 0;
                for (HashSet<Integer> numberTrianlges : numbers_triangles.values()) {
                    for (Integer numberTriangle : numberTrianlges) {
                        Face face = obj.faces.get(numberTriangle);
                        if (!face.isNeedToRender()) continue;
                        totalCountFaces++;
                        int angle_sun = face.getAngle_sun();
                        g.setColor(new Color(angle_sun, angle_sun, angle_sun));
                        drawPolygon(face, obj, g);
                    }
                }
            }
        });
    }

    private void updateFaces(MyObject3D obj, int numberThread) {
        int i = -1;
        for (Face face : obj.faces) {
            if ((++i) % countThreads != numberThread) continue;
            updateFace(obj, face);
        }
    }

    private void arrangeFaces(MyObject3D obj, HashSet<Integer> numbers, TreeMap<Integer, HashSet<Integer>> numbersTriangles, int numberThread) {
        if (numberThread != 0) return;
        int i = -1;
        for (Face face : obj.faces) {
            i++;
            int key = (int) (1000 - face.getDistance() * 1000);
            int value = i;
            if (!numbersTriangles.containsKey(key)) {
                numbersTriangles.put(key, new HashSet<>());
            }
            numbersTriangles.get(key).add(value);
            Collections.addAll(numbers, face.vertices[0], face.vertices[1], face.vertices[2]);
            if (face.vertices[3] != Integer.MAX_VALUE) {
                numbers.add(face.vertices[3]);
            }
        }
    }

    public void updateFace(MyObject3D myObject3D, Face face) {
        Point3D newCentre = myObject3D.getPointAfterDirectionAndLocation(face.getCentre());
        Vector3D vector = Vector3D.getVectorFromPointToPoint(camera, newCentre);
        face.setDistanceView(Physics.getDistancePoint3D(camera.getForwardVector(), newCentre.getminusPoint(camera)));
        face.setRadianOfCamera(camera.getForwardVector().getRadiansVector(vector));
        face.setDistance(Point3D.getDistance(newCentre, camera));
        face.setNeedToRender(!(vector.getRadiansVector(face.getNormal()) <= 1.57 || face.getDistance() > camera.DRAWING_DISTANCE || face.getRadianOfCamera() > 1.22));
    }

    private void drawBackground() {
        if (g != null) {
            g.setColor(new Color(3, 173, 252));
            g.fillRect(0, 0, 1920, 1080);
        }
    }


    private void drawHaracteristics() {
        if (g != null) {
            g.setColor(Color.BLACK);
            g.setFont(new Font("", Font.BOLD, 20));
        }
        g.drawString("FPS : " + lastTrueFPS, 20, 80);
        g.drawString("Count totalFaces : " + totalCountFaces, 20, 20);
//        int percentWork=(int)(countFaces*100f/maxcountFaces);
//        countFaces=0;
        g.drawString("Count maxfaces : " + countFaces, 20, 40);
//        maxcountFaces=0;
        g.drawString("Моя локация: " + String.format("[%f, %f, %f]", camera.getX(), camera.getY(), camera.getZ()), 20, 60);
        Point3D locationOfMyRobot = Room.getRoom().getMy_objects_3D().get(0);
        g.drawString("Локация моего робота: " + String.format("[%f, %f, %f]", locationOfMyRobot.getX(), locationOfMyRobot.getY(), locationOfMyRobot.getZ()), 20, 100);
        g.drawString("Расстояние до моего робота: " + Point3D.getDistance(camera, locationOfMyRobot), 20, 120);
//        g.drawString("Camera: " + camera.getRotationRight() + " " + camera.getRotationUp() + " " + camera.getForwardVector().toString() + camera.getRightVector().toString() + " " + camera.getUpVector().toString(), 20, 100);
    }

    private void drawAim() {
        if (g != null) {
            g.setStroke(new BasicStroke(3f));
            g.drawLine(960, 520, 960, 560);
            g.drawLine(940, 540, 980, 540);
        }
    }

    public static void drawPolygon(Face face, MyObject3D obj, Graphics2D g) {
        if (g != null) {
            Point2D point1 = obj.projectionPoints[face.vertices[0]];
            Point2D point2 = obj.projectionPoints[face.vertices[1]];
            Point2D point3 = obj.projectionPoints[face.vertices[2]];
            if (point1 == null || point2 == null || point3 == null) {
                return;
            }
            int x1 = (int) point1.getX(), x2 = (int) (int) point2.getX(), x3 = (int) point3.getX();
            int y1 = (int) point1.getY(), y2 = (int) point2.getY(), y3 = (int) point3.getY();
            int[] xPoints;
            int[] yPoints;
            Point2D point4;
            if (face.vertices[3] != Integer.MAX_VALUE && (point4 = obj.projectionPoints[face.vertices[3]]) != null) {
                xPoints = new int[]{x1, x2, x3, (int) point4.getX()};
                yPoints = new int[]{y1, y2, y3, (int) point4.getY()};
                g.fillPolygon(xPoints, yPoints, 4);
            } else {
                g.fillPolygon(new int[]{x1, x2, x3}, new int[]{y1, y2, y3}, 3);
            }
        }
    }

    public void setG(Graphics2D g) {
        this.g = g;
    }


}
