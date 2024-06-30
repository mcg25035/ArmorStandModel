package dev.codingbear.asm;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import dev.codingbear.utils.EulerAngle;
import dev.codingbear.utils.MathHelper;
import dev.codingbear.utils.RotationAngles;
import dev.codingbear.utils.RotationUtil;
import dev.ideog.Quaternion;
import dev.ideog.Vector3;
import dev.ideog.Vector5;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

import static dev.codingbear.utils.EulerAngle.RotationOrder.XYZ;
import static dev.codingbear.utils.EulerAngle.RotationOrder.ZYX;


public class ModelEntity {
    public List<ItemStack> headItems;
    public static List<ModelEntity> entities = new ArrayList<>();
    private final List<ArmorStandNode> armorStands = new ArrayList<>();

    public double[][] currentMatrix = MathHelper.getUnitMatrix(3);
    double[] axisX = {1, 0, 0};
    double[] axisY = {0, 1, 0};
    double[] axisZ = {0, 0, 1};

    public Instant spawnAt;
    public int dx;
    public int dy;
    public int dz;
    double rx = 0;
    double ry = 0;
    double rz = 0;

    public enum ArmorStandSize {
        SMALL, MEDIUM, LARGE
    }

    public ModelEntity(Model model, Location modelLocation, ArmorStandSize size) {
        spawnAt = Instant.now();
        modelLocation.setPitch(0.0F);
        modelLocation.setYaw(0.0F);
        this.headItems = model.headItems;
        this.dx = model.size.x;
        this.dy = model.size.y;
        this.dz = model.size.z;

        int x = 0, y = 0, z = 0;
        double scale = 0;
        if (size == ArmorStandSize.SMALL) scale = 0.4375;
        else if (size == ArmorStandSize.MEDIUM) scale = 0.625;
        else if (size == ArmorStandSize.LARGE) scale = 1.0;

        for (ItemStack i : model.headItems) {
            if (i.getType() != Material.AIR) {
                ArmorStandNode node = new ArmorStandNode(
                        i,
                        ((double)(x - model.centerOffset.x))*scale,
                        ((double)(y - model.centerOffset.y))*scale,
                        ((double)(z - model.centerOffset.z))*scale,
                        modelLocation,
                        size
                );
                this.armorStands.add(node);
            }

            z++;

            if (z >= this.dz) {
                z = 0;
                y++;
            }

            if (y >= this.dy) {
                y = 0;
                x++;
            }

            if (x >= this.dx) {
                break;
            }
        }
        entities.add(this);
    }

    public void rotateX(double angle) {
        this.rx += angle;
        currentMatrix = MathHelper.matrixMultiply(currentMatrix, MathHelper.getRotationXMatrix(angle));
        axisX = MathHelper.matrixMultiply(new double[]{1, 0, 0}, currentMatrix);
        axisY = MathHelper.matrixMultiply(new double[]{0, 1, 0}, currentMatrix);
        axisZ = MathHelper.matrixMultiply(new double[]{0, 0, 1}, currentMatrix);
        double[] armorStandAngles = RotationAngles.findRotationAngles(axisX, axisY, axisZ);

        MathHelper.printMatrix(currentMatrix);

        for (ArmorStandNode node : this.armorStands) {
            double[] nodeCurrent = {node.rawX, node.rawY, node.rawZ};
            double[] rotated = MathHelper.matrixMultiply(nodeCurrent, currentMatrix);
            node.moveTo(rotated[0], rotated[1], rotated[2]);
            node.setRotate(armorStandAngles[0], armorStandAngles[1], armorStandAngles[2]);
        }
    }

    public void rotateY(double angle) {
        this.ry += angle;
        currentMatrix = MathHelper.matrixMultiply(currentMatrix, MathHelper.getRotationYMatrix(angle));
        axisX = MathHelper.matrixMultiply(new double[]{1, 0, 0}, currentMatrix);
        axisY = MathHelper.matrixMultiply(new double[]{0, 1, 0}, currentMatrix);
        axisZ = MathHelper.matrixMultiply(new double[]{0, 0, 1}, currentMatrix);
        double[] armorStandAngles = RotationAngles.findRotationAngles(axisX, axisY, axisZ);

        MathHelper.printMatrix(currentMatrix);

        for (ArmorStandNode node : this.armorStands) {
            double[] nodeCurrent = {node.rawX, node.rawY, node.rawZ};
            double[] rotated = MathHelper.matrixMultiply(nodeCurrent, currentMatrix);
            node.moveTo(rotated[0], rotated[1], rotated[2]);
            node.setRotate(armorStandAngles[0], armorStandAngles[1], armorStandAngles[2]);
            if (node.rawX == 0 && node.rawY == 1 && node.rawZ == 0) {
                System.out.println("node: " + node.currentX + " " + node.currentY + " " + node.currentZ);
            }
        }
    }

    public void rotateZ(double angle) {
        this.rz += angle;
        currentMatrix = MathHelper.matrixMultiply(currentMatrix, MathHelper.getRotationZMatrix(angle));
        axisX = MathHelper.matrixMultiply(new double[]{1, 0, 0}, currentMatrix);
        axisY = MathHelper.matrixMultiply(new double[]{0, 1, 0}, currentMatrix);
        axisZ = MathHelper.matrixMultiply(new double[]{0, 0, 1}, currentMatrix);
        double[] armorStandAngles = RotationAngles.findRotationAngles(axisX, axisY, axisZ);

        MathHelper.printMatrix(currentMatrix);

        for (ArmorStandNode node : this.armorStands) {
            double[] nodeCurrent = {node.rawX, node.rawY, node.rawZ};
            double[] rotated = MathHelper.matrixMultiply(nodeCurrent, currentMatrix);
            node.moveTo(rotated[0], rotated[1], rotated[2]);
            node.setRotate(armorStandAngles[0], armorStandAngles[1], armorStandAngles[2]);
        }
    }



    public void moveTo(Location location) {
        for (ArmorStandNode node : this.armorStands) {
            node.setCenterLocation(location);
        }
    }

    public void update() {
        for (ArmorStandNode node : this.armorStands) {
            node.update();
        }
    }

    public void delete() {
        for (ArmorStandNode node : this.armorStands) {
            node.delete();
        }
        entities.remove(this);
    }

//    @Deprecated
//    public void rotateEuler(double angleX, double angleY, double angleZ) {
//        this.rx = angleX;
//        this.ry = angleY;
//        this.rz = angleZ;
//
//        currentEuler = new EulerAngle(angleX, angleY, angleZ);
//        for (ArmorStandNode node : this.armorStands) {
//            EulerAngle eulerAngle = new EulerAngle(
//                    node.rawX,
//                    node.rawY,
//                    node.rawZ
//            );
//            currentEuler.rotate(angleX, angleY, angleZ, ZYX);
//            eulerAngle.rotate(angleX, angleY, angleZ, ZYX);
//            node.setRotate(angleX, angleY, angleZ);
//            node.moveTo(eulerAngle.getX(), eulerAngle.getY(), eulerAngle.getZ());
//        }
//    }
//
//    @Deprecated
//    public void rotateByAxis(double angle, double x, double y, double z) {
//        System.out.println("euler before: " + currentEuler);
//        currentEuler = EulerAngle.fromArray(RotationUtil.rotateModel(
//                currentEuler.toArray(),
//                new Vector3D(x, y, z),
//                angle*57.2972222222223,
//                "XYZ"
//        ));
//        System.out.println("euler after: " + currentEuler);
//
////        angle = Math.toRadians(angle);
//        for (ArmorStandNode node : this.armorStands) {
//            node.setRotate(currentEuler.getX(), currentEuler.getY(), currentEuler.getZ());
//
//            node.update();
//
//
//
//
////
////            Quaternion nodeQuaternion = new Quaternion(nodeEuler);
//////            nodeQuaternion.rotateByAxis(new Vector3((float) x, (float) y, (float) z), (float) angle);
////            Vector3 euler = nodeQuaternion.eulerAngles();
////
////            node.setRotate(euler.x, euler.y, euler.z);
////            System.out.println(node.getVector());
//        }
//    }
//
//    @Deprecated
//    public void rotationAdd(double rx, double ry, double rz) {
//        this.rx += rx;
//        this.ry += ry;
//        this.rz += rz;
//        currentEuler = new EulerAngle(
//                currentEuler.getX()+rx,
//                currentEuler.getY()+ry,
//                currentEuler.getZ()+rz
//        );
//        for (ArmorStandNode node : this.armorStands) {
//            EulerAngle eulerAngle = new EulerAngle(
//                    node.currentX,
//                    node.currentY,
//                    node.currentZ
//            );
////            eulerAngle.rotate(rx, ry, rz, XYZ);
//            System.out.println(node.headPoseAngleY);
//            node.setRotate(this.rx, this.ry, this.rz);
//            node.moveTo(eulerAngle.getX(), eulerAngle.getY(), eulerAngle.getZ());
//        }
//    }

    private static class ArmorStandNode {
        public ArmorStand armorStand;
        public double rawX;
        public double rawY;
        public double rawZ;
        public double currentX;
        public double currentY;
        public double currentZ;
        public double headPoseAngleX;
        public double headPoseAngleY;
        public double headPoseAngleZ;
        public Location centerLocation;

        public ArmorStandNode(ItemStack block, double x, double y, double z, Location modelLocation, ArmorStandSize size) {
            Entity entity = modelLocation.getWorld().spawnEntity(modelLocation.clone().add(x, y, z), EntityType.ARMOR_STAND);
            this.armorStand = (ArmorStand)entity;
            block.setAmount(1);
            this.armorStand.getEquipment().setHelmet(block);
            this.armorStand.setGravity(false);
            this.armorStand.setVisible(false);
            this.armorStand.setMarker(true);
            this.armorStand.setSmall(size == ArmorStandSize.SMALL);

            this.setCenterLocation(modelLocation);
            this.moveTo(x, y, z);
            this.rawX = x;
            this.rawY = y;
            this.rawZ = z;
        }

        public void moveTo(double x, double y, double z) {
            currentX = x;
            currentY = y;
            currentZ = z;
        }

        public void setCenterLocation(Location location) {
            this.centerLocation = location.clone().subtract(0.0, 1.75, 0.0);
        }

        public void setRotate(double angleX, double angleY, double angleZ) {
            this.headPoseAngleX = angleX;
            this.headPoseAngleY = -angleY;
            this.headPoseAngleZ = -angleZ;
        }

//        public Vector3 getVector() {
//            EulerAngle angle = new EulerAngle(1, 0, 0);
//            angle.rotate(headPoseAngleX, headPoseAngleY, headPoseAngleZ, XYZ);
//            return new Vector3((float) angle.getX(), (float) -angle.getY(), (float) angle.getZ());
//        }
//
//        public void setVector(Vector3 target) {
//            double d = target.x;
//            double e = target.y;
//            double f = target.z;
//            double g = Math.sqrt(d * d + f * f);
//            headPoseAngleX = MathHelper.wrapDegrees((float)(Math.atan2(g, e) * 57.2957763671875) - 90.0F);
//            headPoseAngleY = MathHelper.wrapDegrees((float)((Math.atan2(d, f) * 57.2957763671875)));
//            headPoseAngleZ = 0;
//            System.out.println(headPoseAngleX);
//            System.out.println(headPoseAngleY);
//            System.out.println(headPoseAngleZ);
//        }

        public void update() {
            this.armorStand.teleport(this.centerLocation.clone().add(currentX, currentY, currentZ));
            this.armorStand.setHeadPose(this.armorStand.getHeadPose().setZ(headPoseAngleZ));
            this.armorStand.setHeadPose(this.armorStand.getHeadPose().setY(headPoseAngleY));
            this.armorStand.setHeadPose(this.armorStand.getHeadPose().setX(headPoseAngleX));
        }

        public void delete() {
            this.armorStand.remove();
        }
    }
}
