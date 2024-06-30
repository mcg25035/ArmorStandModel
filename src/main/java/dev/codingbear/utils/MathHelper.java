package dev.codingbear.utils;

public class MathHelper {
    public static float wrapDegrees(float degrees) {
        float f = degrees % 360.0F;
        if (f >= 180.0F) {
            f -= 360.0F;
        }

        if (f < -180.0F) {
            f += 360.0F;
        }

        return f;
    }

    public static double[][] matrixMultiply(double[][] a, double[][] b) {
        int aRows = a.length;
        int aColumns = a[0].length;
        int bRows = b.length;
        int bColumns = b[0].length;
        if (aColumns != bRows) {
            throw new IllegalArgumentException("A:Rows: " + aColumns + " did not match B:Columns " + bRows + ".");
        }
        double[][] resultant = new double[aRows][bColumns];
        for (int i = 0; i < aRows; i++) {
            for (int j = 0; j < bColumns; j++) {
                resultant[i][j] = 0.0;
                for (int k = 0; k < aColumns; k++) {
                    resultant[i][j] += a[i][k] * b[k][j];
                }
            }
        }
        return resultant;
    }

    public static double[] matrixMultiply(double[] a, double[][] b) {
        int aRows = 1;
        int aColumns = a.length;
        int bRows = b.length;
        int bColumns = b[0].length;
        if (aColumns != bRows) {
            throw new IllegalArgumentException("A:Rows: " + aColumns + " did not match B:Columns " + bRows + ".");
        }
        double[] resultant = new double[bColumns];
        for (int i = 0; i < bRows; i++) {
            resultant[i] = 0.0;
            for (int j = 0; j < aColumns; j++) {
                resultant[i] += a[j] * b[i][j];
            }
        }
        return resultant;
    }

    public static double[][] getRotationXMatrix(double angle) {
        double cos = Math.cos(angle);
        double sin = Math.sin(angle);
        return new double[][]{
                {1, 0, 0},
                {0, cos, -sin},
                {0, sin, cos}
        };
    }

    public static double[][] getRotationYMatrix(double angle) {
        double cos = Math.cos(angle);
        double sin = Math.sin(angle);
        return new double[][]{
                {cos, 0, sin},
                {0, 1, 0},
                {-sin, 0, cos}
        };
    }

    public static double[][] getRotationZMatrix(double angle) {
        double cos = Math.cos(angle);
        double sin = Math.sin(angle);
        return new double[][]{
                {cos, -sin, 0},
                {sin, cos, 0},
                {0, 0, 1}
        };
    }

    public static double[][] getUnitMatrix(int size) {
        double[][] matrix = new double[size][size];
        for (int i = 0; i < size; i++) {
            matrix[i][i] = 1;
        }
        return matrix;
    }

    public static void printMatrix(double[][] matrix) {
        for (double[] row : matrix) {
            for (double value : row) {
                System.out.print(value + " ");
            }
            System.out.println();
        }
    }
}
