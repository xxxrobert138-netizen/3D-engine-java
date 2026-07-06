package Engine.math2D;

public class Point2D {
	
	protected float x,y;
	
	public Point2D(float x,float y) {
		this.x=x;
		this.y=y;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	@Override
	public String toString() {
		return "Point2D{" +
				"x=" + x +
				", y=" + y +
				'}';
	}
}
