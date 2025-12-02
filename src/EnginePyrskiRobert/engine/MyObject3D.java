package EnginePyrskiRobert.engine;

import EnginePyrskiRobert.math2D.Point2D;
import EnginePyrskiRobert.math3D.Face;
import EnginePyrskiRobert.math3D.Point3D;
import EnginePyrskiRobert.math3D.Space;
import EnginePyrskiRobert.math3D.Vector3D;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MyObject3D extends Space {

    protected float size;
    //	public float distanceOfCamera;
    public final ArrayList<Face> faces = new ArrayList<>();
    public final ArrayList<Point3D> vertices = new ArrayList<>();
    protected Point2D[] projectionPoints;

    public MyObject3D(String fileName, Point3D location, float size, float rotationRight, float rotationUp) {
        super(location, rotationRight, rotationUp);
        this.size = size;
        loadObject(fileName);
    }

    public void loadObject(String filename) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filename)))) {
            ArrayList<Vector3D> listNormals = new ArrayList<>();
            while (reader.ready()) {
                String[] strings = getDataWithoutSpaces(reader.readLine());
                if (strings.length == 0) continue;
                switch (strings[0]) {
                    case "v":
                        Point3D point = new Point3D((float) Double.parseDouble(strings[3]) * size + x, (float) Double.parseDouble(strings[1]) * size + y, (float) (Double.parseDouble(strings[2])) * size + z);
                        this.vertices.add(getPointAfterDirectionAndLocation(point));
                        break;
                    case "f":
                        int n1 = Integer.parseInt(strings[1].split("/")[0]) - 1;
                        int n2 = Integer.parseInt(strings[2].split("/")[0]) - 1;
                        int n3 = Integer.parseInt(strings[3].split("/")[0]) - 1;
                        int n4 = strings.length == 5 ? Integer.parseInt(strings[4].split("/")[0]) - 1 : Integer.MAX_VALUE;
                        Vector3D normal;
                        try {
                            normal = listNormals.get(Integer.parseInt(strings[2].split("/")[2]) - 1);
                        } catch (Exception e) {
                            normal = Vector3D.getNormal(vertices.get(n1), vertices.get(n2), vertices.get(n3));
                        }
                        Face face = new Face(normal, n1, n2, n3, n4);
                        face.setCentre(Point3D.getCentreFace(vertices.get(n1), vertices.get(n2), vertices.get(n3), n4 == Integer.MAX_VALUE ? null : vertices.get(n4)));
                        this.faces.add(face);
                        break;
                    case "vn":
                        listNormals.add(new Vector3D(
                                Float.parseFloat(strings[3]),
                                Float.parseFloat(strings[1]),
                                Float.parseFloat(strings[2])));
                        break;
                }
            }
            this.projectionPoints = new Point2D[vertices.size()];
        } catch (Exception _) {
            System.out.println("ERROR");
        }
    }

    public Point3D getPointAfterDirectionAndLocation(Point3D point) {
        Point3D plusDirection = new Point3D(0, 0, 0).plus(forwardVector.multiply(point.getX())).plus(rightVector.multiply(point.getY())).plus(upVector.multiply(point.getZ()));
        return plusDirection.plus(this);
    }

    public String[] getDataWithoutSpaces(String string) {
        ArrayList<String> arrayList = new ArrayList<>();
        for (String s : string.split(" ")) {
            if (!s.isEmpty()) arrayList.add(s);
        }
        String[] array = new String[arrayList.size()];
        for (int i = 0; i < array.length; i++) {
            array[i] = arrayList.get(i);
        }
        return array;
    }

}
