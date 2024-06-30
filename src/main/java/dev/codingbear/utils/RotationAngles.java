package dev.codingbear.utils;

import dev.codingbear.utils.EulerAngle;

public class RotationAngles {
    public static double[] normalizeVector(double[] vector) {
        double length = Math.sqrt(vector[0] * vector[0] + vector[1] * vector[1] + vector[2] * vector[2]);
        return new double[]{vector[0] / length, vector[1] / length, vector[2] / length};
    }

//    public static double[] findRotationAngles(double[] x1, double[] y1, double[] z1) {
//        x1 = normalizeVector(x1);
//        y1 = normalizeVector(y1);
//        z1 = normalizeVector(z1);
//
//        double r20 = z1[0];
//        double r21 = z1[1];
//        double r22 = z1[2];
//        double r00 = x1[0];
//        double r01 = x1[1];
//
//        // z correct code
//        // double thetaZ = Math.atan2(r01, r00);
//        // double thetaY = Math.atan2(-r20, Math.sqrt(r21 * r21 + r22 * r22));
//        // double thetaX = Math.atan2(r21, r22);
//
//        // θz (Yaw)
//        double thetaZ = Math.atan2(x1[1], x1[0]);
//
//        // θy (Pitch)
//        double thetaY = Math.atan2(-z1[0], z1[2]);
//
//        // Adjusting z1 by reversing θz rotation
//        double cosThetaZ = Math.cos(thetaZ);
//        double sinThetaZ = Math.sin(thetaZ);
//        double z1AdjustedX = z1[0] * cosThetaZ + z1[1] * sinThetaZ;
//        double z1AdjustedY = -z1[0] * sinThetaZ + z1[1] * cosThetaZ;
//        double z1AdjustedZ = z1[2];
//
//        // θx (Roll) - calculated after adjusting for θz
//        double thetaX = Math.atan2(z1AdjustedY, z1AdjustedZ);
//
//
////        if (Math.abs(Math.cos(thetaX)) < 1e-6) {  // 檢測奇異點，使用接近0的小閾值
////            thetaY = 0;  // 在奇異點時，固定 Y 軸旋轉角度為 0
////            thetaZ = Math.atan2(r01, r00);  // 基於 X 軸向量的計算
////            // 檢查是否需要調整 thetaZ，如果 r00 和 r01 同時接近零，可能需要修正
////            if (Math.abs(r00) < 1e-6 && Math.abs(r01) < 1e-6) {
////                thetaZ += Math.PI;  // 調整180度
////                if (thetaZ > Math.PI) {  // 確保角度保持在 -π 到 π 的範圍
////                    thetaZ -= 2 * Math.PI;
////                }
////            }
////        } else {
//
//
////        }
//
//        return new double[]{-thetaX, thetaY, thetaZ};
//    }

    public static double[] findRotationAngles(double[] x1, double[] y1, double[] z1) {
        x1 = normalizeVector(x1);
        y1 = normalizeVector(y1);
        z1 = normalizeVector(z1);

        double thetaZ = Math.atan2(x1[1], x1[0]);
        double thetaY = Math.atan2(-x1[2], Math.sqrt(x1[0] * x1[0] + x1[1] * x1[1]));
        double thetaX = Math.atan2(-y1[2], z1[2]);
        return new double[] {-thetaX, thetaY, thetaZ};
    }

//    public static void test(double rx, double ry, double rz) {
//        // get local x, y, z axes as vector after rotation rx, ry, rz as test input
//        EulerAngle x = new EulerAngle(1, 0, 0);
//        EulerAngle y = new EulerAngle(0, 1, 0);
//        EulerAngle z = new EulerAngle(0, 0, 1);
//        x.rotate(rx, ry, rz, EulerAngle.RotationOrder.XYZ);
//        y.rotate(rx, ry, rz, EulerAngle.RotationOrder.XYZ);
//        z.rotate(rx, ry, rz, EulerAngle.RotationOrder.XYZ);
//        double[] x1 = {x.getX(), x.getY(), x.getZ()};
//        double[] y1 = {y.getX(), y.getY(), y.getZ()};
//        double[] z1 = {z.getX(), z.getY(), z.getZ()};
//
//        // get the euler angles that convert x, y, z axes to x1, y1, z1 axes
//        double[] angles = findRotationAngles(x1, y1, z1);
//        System.out.println("Actual: " + Math.toDegrees(rx) + " " + Math.toDegrees(ry) + " " + Math.toDegrees(rz));
//        System.out.println("Predicated: " + Math.toDegrees(angles[0]) + " " + Math.toDegrees(angles[1]) + " " + Math.toDegrees(angles[2]));
//    }

//    public static void main(String[] args) {
//            double rx = Math.toRadians(30);
//            double ry = Math.toRadians(60);
//            double rz = Math.toRadians(89.99999999);
//            test(rx, ry, rz);
//    }

}
