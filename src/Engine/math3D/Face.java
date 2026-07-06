package Engine.math3D;

import java.util.Arrays;

public class Face {

    private Vector3D normal;
    private final Vector3D vector_sun = new Vector3D(0, 0, -1);
    private Point3D centre;
    private int angle_sun;
    public final int[] vertices;
    private float size = 5f;
    private float radianOfCamera;
    private float distance;
    private boolean needToRender;
    //	насколько далеко поле зрения камеры
    private float distanceView;

    public Face(Vector3D normal, int... vertices) {
        this.vertices = vertices;
        this.normal = normal;
        this.angle_sun = (int) Math.toDegrees(normal.getRadiansVector(vector_sun));
    }

    @Override
    public String toString() {
        return "Face{" +
                "normal=" + normal +
                ", vector_sun=" + vector_sun +
                ", centre=" + centre +
                ", angle_sun=" + angle_sun +
                ", vertices=" + Arrays.toString(vertices) +
                ", size=" + size +
                ", radianOfCamera=" + radianOfCamera +
                ", distance=" + distance +
                ", needToRender=" + needToRender +
                ", distanceView=" + distanceView +
                '}';
    }

    public Vector3D getNormal() {
        return normal;
    }

    public void setNormal(Vector3D normal) {
        this.normal = normal;
    }

    public Point3D getCentre() {
        return centre;
    }

    public void setCentre(Point3D centre) {
        this.centre = centre;
    }

    public int getAngle_sun() {
        return angle_sun;
    }

    public void setAngle_sun(int angle_sun) {
        this.angle_sun = angle_sun;
    }

    public int[] getVertices() {
        return vertices;
    }

    public float getSize() {
        return size;
    }

    public void setSize(float size) {
        this.size = size;
    }

    public float getRadianOfCamera() {
        return radianOfCamera;
    }

    public void setRadianOfCamera(float radianOfCamera) {
        this.radianOfCamera = radianOfCamera;
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public float getDistanceView() {
        return distanceView;
    }

    public void setDistanceView(float distanceView) {
        this.distanceView = distanceView;
    }

    public boolean isNeedToRender() {
        return needToRender;
    }

    public void setNeedToRender(boolean needToRender) {
        this.needToRender = needToRender;
    }
}