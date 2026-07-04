package EnginePyrskiRobert.engine;

import EnginePyrskiRobert.math2D.Point2D;
import EnginePyrskiRobert.math3D.Point3D;
import EnginePyrskiRobert.math3D.Space;

public final class Camera extends Space {

	public static final float DRAWING_DISTANCE=1000;
	public static final float SCREEN_SIZE=960;
	public static final int SPEED_PROJECTION=1000;

	public Camera(Point3D point3D, float rotationRight, float rotationUp) {
		super(point3D, rotationRight, rotationUp);
	}

	public Point2D convertPoint3Dto2D(Point3D point) {
		Point3D new_point=new Point3D(point.getX() - x, point.getY() - y, point.getZ() - z);
		float module_forward= forwardVector.getDistancePointSquare(new_point);
		float module_right= rightVector.getDistancePointSquare(new_point);
		float module_up= upVector.getDistancePointSquare(new_point);
		if (module_forward<=0) {
			return null;
		}
		return new Point2D(module_right/module_forward*540+960, 540-540*module_up/module_forward);
	}

}
