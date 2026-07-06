package Engine.math3D;

public class Vector3D extends Point3D {


	private float module;
	
	public Vector3D(float x,float y,float z) {
		super(x, y, z);
		module=countModule();
	}
	
	public Vector3D(float gradusXY,float gradusXYZ) {
//		float z=(float)Math.sin(Math.toRadians(gradusXYZ));
//		float x=(float)Math.cos(Math.toRadians(gradusXY));
//		float y=(float)Math.sin(Math.toRadians(gradusXY));
		super(
				(float)Math.cos(Math.toRadians(gradusXY)),
				(float)Math.sin(Math.toRadians(gradusXY)),
				(float)Math.sin(Math.toRadians(gradusXYZ))
		);
		module=countModule();
	}
	
	public static Vector3D parsePoint3DToVector3D(Point3D point3D) {
		return new Vector3D(point3D.getX(), point3D.getY(), point3D.getZ());
	}
	
	public float countModule() {
		return (float) Math.sqrt(x*x+y*y+z*z);
	}

	
	public float getDistancePointSquare(Point3D point) {
		float num=point.getX()*x+point.getY()*y+point.z*z;
		return (float) (num*1.0f/module);
	}
	
	public float getRadiansVector(Vector3D vector) {
		return (float) Math.acos((vector.x*this.x+vector.y*this.y+vector.z*this.z)/(vector.module*this.module));
	}
	
	public Vector3D getVectorMultiply(Vector3D vect) {
		return new Vector3D(y*vect.z-z*vect.y, z*vect.x-x*vect.z, x*vect.y-y*vect.x);
	}
	
	public void setModule(float module) {
		if (module<=0) {
			System.out.println("Модуль не может быть отрицательным или равняться нулю");
			return;
		}
		this.x=x/this.module*module;
		this.y=y/this.module*module;
		this.z=z/this.module*module;
		this.module=module;
	}
	
	public float getModule() {
		return module;
	}
	
	public Vector3D copy() {
		return new Vector3D(x, y, z);
	}
	
	public Vector3D multiply(float num) {
		return new Vector3D(x*num,y*num,z*num);
	}
	
	public Vector3D normalize() {
		Vector3D norm=new Vector3D(x/module, y/module, z/module);
		norm.module=1;
		return norm;
	}
	
	public static Vector3D getVectorFromPointToPoint(Point3D fromPoint, Point3D toPoint) {
		return new Vector3D(toPoint.getX()-fromPoint.getX(), toPoint.getY()-fromPoint.getY(), toPoint.getZ()-fromPoint.getZ());
	}

	public static Vector3D getNormal(Point3D point1, Point3D point2, Point3D point3) {
		Vector3D vect1=new Vector3D(point2.getX() - point1.getX(), point2.getY() - point1.getY(),point2.z-point1.z);
		Vector3D vect2=new Vector3D(point3.getX() - point1.getX(), point3.getY() - point1.getY(),point3.z-point1.z);
		Vector3D vect=vect1.getVectorMultiply(vect2);
		return vect;
	}
	
//	возвращает повернутый вектор на 180 градусов
	public Vector3D getRotationPiRadian() {
		return new Vector3D(-x, -y, -z);
	}

	public Vector3D plus(Vector3D vector3D) {
		return new Vector3D(x+vector3D.x,y+vector3D.y,z+vector3D.z);
	}

	@Override
	public String toString() {
		return "Vector3D{" +
				"x=" + x +
				", y=" + y +
				", z=" + z +
				'}';
	}
}
