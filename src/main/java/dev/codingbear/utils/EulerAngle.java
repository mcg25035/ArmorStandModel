package dev.codingbear.utils;

public class EulerAngle {
    public enum RotationOrder {
        XYZ, XZY, YXZ, YZX, ZXY, ZYX
    }

    private final double rawX;
    private final double rawY;
    private final double rawZ;
    private double x;
    private double y;
    private double z;

    public EulerAngle(double x, double y, double z) {
        this.rawX = x;
        this.rawY = y;
        this.rawZ = z;
        reset();
    }

    private void reset() {
        this.x = rawX;
        this.y = rawY;
        this.z = rawZ;
    }

    public void rotate(double angleX, double angleY, double angleZ, RotationOrder order) {
        reset();
        switch (order) {
            case XYZ:
                rotateX(angleX);
                rotateY(angleY);
                rotateZ(angleZ);
                break;
            case XZY:
                rotateX(angleX);
                rotateZ(angleZ);
                rotateY(angleY);
                break;
            case YXZ:
                rotateY(angleY);
                rotateX(angleX);
                rotateZ(angleZ);
                break;
            case YZX:
                rotateY(angleY);
                rotateZ(angleZ);
                rotateX(angleX);
                break;
            case ZXY:
                rotateZ(angleZ);
                rotateX(angleX);
                rotateY(angleY);
                break;
            case ZYX:
                rotateZ(angleZ);
                rotateY(angleY);
                rotateX(angleX);
                break;
        }
    }

    public double[] toArray(){
        return new double[]{x, y, z};
    }

    public static EulerAngle fromArray(double[] array){
        return new EulerAngle(array[0], array[1], array[2]);
    }

    private void rotateX(double angle) {
        double newY = this.y * Math.cos(angle) - this.z * Math.sin(angle);
        double newZ = this.y * Math.sin(angle) + this.z * Math.cos(angle);
        this.y = newY;
        this.z = newZ;
    }

    private void rotateY(double angle) {
        double newX = this.x * Math.cos(angle) + this.z * Math.sin(angle);
        double newZ = -this.x * Math.sin(angle) + this.z * Math.cos(angle);
        this.x = newX;
        this.z = newZ;
    }

    private void rotateZ(double angle) {
        double newX = this.x * Math.cos(angle) + this.y * Math.sin(angle);
        double newY = -this.x * Math.sin(angle) + this.y * Math.cos(angle);
        this.x = newX;
        this.y = newY;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    @Override
    public String toString() {
        return "EulerAngle{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }
}
