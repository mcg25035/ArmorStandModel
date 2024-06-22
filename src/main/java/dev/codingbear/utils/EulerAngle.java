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
        double[][] rotationMatrix = calculateRotationMatrix(angleX, angleY, angleZ, order);
        applyRotation(rotationMatrix);
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

    private double[][] calculateRotationMatrix(double angleX, double angleY, double angleZ, RotationOrder order) {
        double[][] Rx = {
                {1, 0, 0},
                {0, Math.cos(angleX), -Math.sin(angleX)},
                {0, Math.sin(angleX), Math.cos(angleX)}
        };
        double[][] Ry = {
                {Math.cos(angleY), 0, Math.sin(angleY)},
                {0, 1, 0},
                {-Math.sin(angleY), 0, Math.cos(angleY)}
        };
        double[][] Rz = {
                {Math.cos(angleZ), -Math.sin(angleZ), 0},
                {Math.sin(angleZ), Math.cos(angleZ), 0},
                {0, 0, 1}
        };

        switch (order) {
            case XYZ:
                return matrixMultiply(matrixMultiply(Rz, Ry), Rx);
            case XZY:
                return matrixMultiply(matrixMultiply(Ry, Rz), Rx);
            case YXZ:
                return matrixMultiply(matrixMultiply(Rz, Rx), Ry);
            case YZX:
                return matrixMultiply(matrixMultiply(Rx, Rz), Ry);
            case ZXY:
                return matrixMultiply(matrixMultiply(Ry, Rx), Rz);
            case ZYX:
                return matrixMultiply(matrixMultiply(Rx, Ry), Rz);
            default:
                return new double[3][3]; // Should not reach here
        }
    }

    private void applyRotation(double[][] rotationMatrix) {
        double newX = rotationMatrix[0][0] * x + rotationMatrix[0][1] * y + rotationMatrix[0][2] * z;
        double newY = rotationMatrix[1][0] * x + rotationMatrix[1][1] * y + rotationMatrix[1][2] * z;
        double newZ = rotationMatrix[2][0] * x + rotationMatrix[2][1] * y + rotationMatrix[2][2] * z;
        x = newX;
        y = newY;
        z = newZ;
    }

    private double[][] matrixMultiply(double[][] A, double[][] B) {
        double[][] result = new double[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                for (int k = 0; k < 3; k++) {
                    result[i][j] += A[i][k] * B[k][j];
                }
            }
        }
        return result;
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
