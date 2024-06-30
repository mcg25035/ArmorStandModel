package dev.codingbear.utils;

public class EulerAngle {
    public enum RotationOrder {
        XYZ, XZY, YXZ, YZX, ZXY, ZYX
    }

    private final double rawX;
    private final double rawY;
    private final double rawZ;
    private double[] axisX = {1, 0, 0};
    private double[] axisY = {0, 1, 0};
    private double[] axisZ = {0, 0, 1};
    private double x;
    private double y;
    private double z;

    public EulerAngle(double x, double y, double z) {
        this.rawX = x;
        this.rawY = y;
        this.rawZ = z;
        reset();
    }

    public void reset() {
        this.x = rawX;
        this.y = rawY;
        this.z = rawZ;
        axisX = new double[]{1, 0, 0};
        axisY = new double[]{0, 1, 0};
        axisZ = new double[]{0, 0, 1};
    }
    public void rotateX(double angle) {
        double[][] rotationMatrix = {
                {1, 0, 0},
                {0, Math.cos(angle), -Math.sin(angle)},
                {0, Math.sin(angle), Math.cos(angle)}
        };
        applyRotation(rotationMatrix);
        axisX = applyRotationToAxis(rotationMatrix, axisX);
        axisY = applyRotationToAxis(rotationMatrix, axisY);
        axisZ = applyRotationToAxis(rotationMatrix, axisZ);
    }

    public void rotateY(double angle) {
        double[][] rotationMatrix = {
                {Math.cos(angle), 0, Math.sin(angle)},
                {0, 1, 0},
                {-Math.sin(angle), 0, Math.cos(angle)}
        };
        applyRotation(rotationMatrix);
        axisX = applyRotationToAxis(rotationMatrix, axisX);
        axisY = applyRotationToAxis(rotationMatrix, axisY);
        axisZ = applyRotationToAxis(rotationMatrix, axisZ);
    }

    public void rotateZ(double angle) {
        double[][] rotationMatrix = {
                {Math.cos(angle), -Math.sin(angle), 0},
                {Math.sin(angle), Math.cos(angle), 0},
                {0, 0, 1}
        };
        applyRotation(rotationMatrix);
        axisX = applyRotationToAxis(rotationMatrix, axisX);
        axisY = applyRotationToAxis(rotationMatrix, axisY);
        axisZ = applyRotationToAxis(rotationMatrix, axisZ);
    }
    public double[] getArmorStandEuler() {
        return RotationAngles.findRotationAngles(axisX, axisY, axisZ);
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

    private void applyRotation(double[][] rotationMatrix) {
        double newX = rotationMatrix[0][0] * x + rotationMatrix[0][1] * y + rotationMatrix[0][2] * z;
        double newY = rotationMatrix[1][0] * x + rotationMatrix[1][1] * y + rotationMatrix[1][2] * z;
        double newZ = rotationMatrix[2][0] * x + rotationMatrix[2][1] * y + rotationMatrix[2][2] * z;
        x = newX;
        y = newY;
        z = newZ;
    }

    private double[] applyRotationToAxis(double[][] rotationMartix, double[] axis) {
        double newX = rotationMartix[0][0] * axis[0] + rotationMartix[0][1] * axis[1] + rotationMartix[0][2] * axis[2];
        double newY = rotationMartix[1][0] * axis[0] + rotationMartix[1][1] * axis[1] + rotationMartix[1][2] * axis[2];
        double newZ = rotationMartix[2][0] * axis[0] + rotationMartix[2][1] * axis[1] + rotationMartix[2][2] * axis[2];
        return new double[]{newX, newY, newZ};
    }

    public double[] toArray(){
        return new double[]{x, y, z};
    }

    public static EulerAngle fromArray(double[] array){
        return new EulerAngle(array[0], array[1], array[2]);
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
