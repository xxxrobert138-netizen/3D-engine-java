package Engine.engine;

import Engine.math3D.Face;
import Engine.math3D.Point3D;
import Engine.math3D.Vector3D;

public final class Physics {
	
	private static final Vector3D COMMON_VECTOR=new Vector3D(1,0,0);

	private Physics() {
	}
	
	public static boolean intersect(
			Point3D obj1,
			Vector3D direction1,
			float speed_module1,
			float size_obj1,
			Point3D obj2,
			Vector3D direction2,
			float speed_module2,
			float size_obj2
			) {
		Point3D nextpoint1=getNextPoint(obj1, direction1, speed_module1);
		Point3D nextpoint2=getNextPoint(obj2, direction2, speed_module2);
		float distance=getDistance(nextpoint1, nextpoint2);
		return distance<=size_obj1+size_obj2;
	}
	
	public static boolean intersect(
			Point3D obj1,
			Vector3D direction1,
			float speed_module1,
			float size_obj1,
			Point3D obj2,
			float size_obj2
			) {
		return intersect(obj1, direction1, speed_module1, size_obj1, obj2, COMMON_VECTOR, 0, size_obj2);
	}
	
	public static boolean intersect(
			Point3D obj1,
			float size_obj1,
			Point3D obj2,
			float size_obj2) {
		return getDistance(obj1, obj2)<size_obj1+size_obj2;
	}
	
	public static float getDistance(Point3D p1,Point3D p2) {
		float dx= p1.getX() - p2.getX();
		float dy= p1.getY() - p2.getY();
		float dz= p1.getZ() - p2.getZ();
		return (float)Math.sqrt(dx*dx+dy*dy+dz*dz);
	}
	
	public static Point3D getNextPoint(
			Point3D obj1,
			Vector3D direction1,
			float speed_module1
			) {
		return new Point3D(
				obj1.getX() +direction1.getX()*speed_module1,
				obj1.getY() +direction1.getY()*speed_module1,
				obj1.getZ() +direction1.getZ()*speed_module1);
	}
	
	public static boolean see(
			Point3D obj1,
			Vector3D direction,
			float distance_of_view,
			Point3D obj2,
			float size_obj2) {
		return getDistancePoint3D(direction, new Point3D(obj2.getX() - obj1.getX(), obj2.getY() - obj1.getY(), obj2.getZ() - obj1.getZ()))<size_obj2 && getDistance(obj1, obj2)<=distance_of_view;
	}
	
	public static float getDistancePoint3D(Vector3D direction,Point3D obj) {
		float[][] matrix= {
				{-obj.getX(),-obj.getY(),-obj.getZ()},
				{direction.getX(),direction.getY(),direction.getZ()}};
		float[] array= {
				matrix[0][1]*1.0f*matrix[1][2]-matrix[0][2]*1.0f*matrix[1][1],
				matrix[0][0]*1.0f*matrix[1][2]-matrix[0][2]*1.0f*matrix[1][0],
				matrix[0][0]*matrix[1][1]-matrix[0][1]*matrix[1][0]
				};
		return (float)(getModuleVectorInAnyDimension(array)*1.0/getModuleVectorInAnyDimension(matrix[1]));
	}
	
	public static float getModuleVectorInAnyDimension(float[] vect) {
		float sum=0;
        for (float v : vect) {
            sum += (float) Math.pow(v, 2);
        }
		return (float)Math.sqrt(sum);
	}
	
	public static Point3D getNextPoint3DwithTouch(Point3D youLocation, Vector3D youDirection, float speed, Face face) {
		Point3D exceptPoint=getNextPoint(youLocation, youDirection, speed);
		if (face==null) {
			return exceptPoint;
		}
		float distance=getDistance(exceptPoint, face.getCentre());
		if (distance< face.getSize()) {
			Vector3D vectSum=youDirection.normalize().plus(face.getNormal().normalize());
			return getNextPoint(youLocation, vectSum, speed);
//			return youLocation;
		}
		return exceptPoint;
	}
	
	public static Point3D getPoint3DRelativelyOtherPoint(Point3D yourp, Point3D projection) {
		return new Point3D(yourp.getX() - projection.getX(), yourp.getY() - projection.getY(), yourp.getZ() - projection.getZ());
	}
	
}
