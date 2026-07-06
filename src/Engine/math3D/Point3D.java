package Engine.math3D;

import Engine.math2D.Point2D;

public class Point3D extends Point2D {

    protected float z;

    public Point3D(float x, float y, float z) {
        super(x, y);
        this.z = z;
    }

    public Point3D getminusPoint(Point3D point) {
        return new Point3D(x - point.x, y - point.y, z - point.z);
    }

    public Point3D plus(Point3D point) {
        return new Point3D(x + point.getX(), y + point.getY(), z + point.getZ());
    }

    public Point3D copy() {
        return new Point3D(x, y, z);
    }

    @Override
    public boolean equals(Object obj) {
        Point3D point = (Point3D) obj;
        return x == point.x && y == point.y && z == point.z;
    }

    public float getZ() {
        return z;
    }

    public void setZ(float z) {
        this.z = z;
    }

    public static float getDistance(Point3D point1, Point3D point2) {
        float dx = point1.x - point2.x;
        float dy = point1.y - point2.y;
        float dz = point1.z - point2.z;
        return (float) Math.sqrt(dx * dx + dy * dy + dz * dz);
    }

    public void setNextPosition(Vector3D direction, int sign, float speed_moving) {
        Vector3D newDirection = direction.multiply(sign);
        this.x += newDirection.getX() * speed_moving;
        this.y += newDirection.getY() * speed_moving;
        this.z += newDirection.getZ() * speed_moving;
    }

    public static Point3D getCentreFace(Point3D p1, Point3D p2, Point3D p3, Point3D p4) {
        Point3D p12 = getCentreBetwenTwoPoints(p1, p2);
        return p4 == null ? getCentreBetwenTwoPoints(p12, p3) : getCentreBetwenTwoPoints(p12, getCentreBetwenTwoPoints(p3, p4));
    }

    public static Point3D getCentreBetwenTwoPoints(Point3D p1, Point3D p2) {
        if (p1 == null || p2 == null) {
            return null;
        }
        return new Point3D((p1.x + p2.x) / 2, (p1.y + p2.y) / 2, (p1.z + p2.z) / 2);
    }

    @Override
    public String toString() {
        return "Point3D{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }
}
