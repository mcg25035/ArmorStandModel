package dev.codingbear.utils;

import org.apache.commons.math3.geometry.euclidean.threed.Rotation;
import org.apache.commons.math3.geometry.euclidean.threed.RotationConvention;
import org.apache.commons.math3.geometry.euclidean.threed.RotationOrder;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.RealMatrix;

public class RotationUtil {
    public static RealMatrix eulerToMatrix(double[] eulerAngles, String sequence) {
        Rotation rotation = new Rotation(Vector3D.PLUS_K, Math.toRadians(eulerAngles[2]), RotationConvention.FRAME_TRANSFORM);
        rotation = rotation.applyTo(new Rotation(Vector3D.PLUS_J, Math.toRadians(eulerAngles[1]), RotationConvention.FRAME_TRANSFORM));
        rotation = rotation.applyTo(new Rotation(Vector3D.PLUS_I, Math.toRadians(eulerAngles[0]), RotationConvention.FRAME_TRANSFORM));

        return new Array2DRowRealMatrix(rotation.getMatrix());
    }

    public static RealMatrix axisAngleToMatrix(Vector3D axis, double angleDegrees) {
        double angleRadians = Math.toRadians(angleDegrees);
        axis = axis.normalize();

        double ux = axis.getX();
        double uy = axis.getY();
        double uz = axis.getZ();

        double cosTheta = Math.cos(angleRadians);
        double sinTheta = Math.sin(angleRadians);

        double[][] data = {
                {cosTheta + ux * ux * (1 - cosTheta), ux * uy * (1 - cosTheta) - uz * sinTheta, ux * uz * (1 - cosTheta) + uy * sinTheta},
                {uy * ux * (1 - cosTheta) + uz * sinTheta, cosTheta + uy * uy * (1 - cosTheta), uy * uz * (1 - cosTheta) - ux * sinTheta},
                {uz * ux * (1 - cosTheta) - uy * sinTheta, uz * uy * (1 - cosTheta) + ux * sinTheta, cosTheta + uz * uz * (1 - cosTheta)}
        };

        return new Array2DRowRealMatrix(data);
    }

    public static double[] rotateModel(double[] eulerAngles, Vector3D axis, double angleDegrees, String sequence) {
        RealMatrix R_initial = eulerToMatrix(eulerAngles, sequence);
        RealMatrix R_axis_angle = axisAngleToMatrix(axis, angleDegrees);
        RealMatrix R_final = R_axis_angle.multiply(R_initial);

        Rotation finalRotation = new Rotation(R_final.getData(), 1e-10);
        return finalRotation.getAngles(RotationOrder.XYZ, RotationConvention.FRAME_TRANSFORM);
    }
}
