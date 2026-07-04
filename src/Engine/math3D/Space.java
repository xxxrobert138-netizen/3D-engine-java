package EnginePyrskiRobert.math3D;

public class Space extends Point3D {

    protected Vector3D forwardVector, rightVector, upVector;
    public float rotationRight = Integer.MAX_VALUE;
    public float rotationUp = Integer.MAX_VALUE;

    public Space(float x, float y, float z, Vector3D forwardVector, Vector3D rightVector, Vector3D upVector) {
        super(x, y, z);
        this.forwardVector = forwardVector;
        this.rightVector = rightVector;
        this.upVector = upVector;
    }

    public Space(Point3D point3D, float rotationRight, float rotationUp) {
        super(point3D.getX(), point3D.getY(), point3D.getZ());
        setDirection(rotationRight, rotationUp);
    }

    public void setDirection(float rotationRight, float rotationUp) {
        this.rotationRight = rotationRight;
        this.rotationUp = rotationUp;
        updateDirection();
    }

    public void updateDirection() {
        forwardVector = new Vector3D((float) Math.toRadians(rotationRight), (float) Math.toRadians(rotationUp));
        rightVector = new Vector3D(-forwardVector.getY(), forwardVector.getX(), 0f);
        upVector = forwardVector.getVectorMultiply(rightVector);
    }

    public void updateDirection(float plusRotationRight, float plusRotationUp) {
        setDirection(plusRotationRight + rotationRight, plusRotationUp + rotationUp);
    }

    public Vector3D getForwardVector() {
        return forwardVector;
    }

    public Vector3D getRightVector() {
        return rightVector;
    }

    public Vector3D getUpVector() {
        return upVector;
    }

    public float getRotationRight() {
        return rotationRight;
    }

    public float getRotationUp() {
        return rotationUp;
    }

    public void setRotationRight(float rotationRight) {
        this.rotationRight = rotationRight;
    }

    public void setRotationUp(float rotationUp) {
        this.rotationUp = rotationUp;
    }

    @Override
    public String toString() {
        return "Space{" +
                "forwardVector=" + forwardVector +
                ", rightVector=" + rightVector +
                ", upVector=" + upVector +
                ", rotationRight=" + rotationRight +
                ", rotationUp=" + rotationUp +
                '}';
    }
}
