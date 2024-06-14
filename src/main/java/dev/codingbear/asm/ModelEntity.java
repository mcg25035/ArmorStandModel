package dev.codingbear.asm;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import dev.codingbear.utils.EulerAngle;
import dev.codingbear.utils.MathHelper;
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


public class ModelEntity {
    public List<ItemStack> headItems;
    public static List<ModelEntity> entities = new ArrayList<>();
    private final List<ArmorStandNode> armorStands = new ArrayList<>();
    EulerAngle currentEuler = new EulerAngle(0, 0, 0);
    public Instant spawnAt;
    public int dx;
    public int dy;
    public int dz;

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

        int x = 0;
        int y = 0;
        int z = 0;

        double scale = 0;
        if (size == ArmorStandSize.SMALL) {
            scale = 0.4375;
        } else if (size == ArmorStandSize.MEDIUM) {
            scale = 0.625;
        } else if (size == ArmorStandSize.LARGE) {
            scale = 1.0;
        }

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

    public void rotateEuler(double angleX, double angleY, double angleZ) {
        currentEuler = new EulerAngle(angleX, angleY, angleZ);
        for (ArmorStandNode node : this.armorStands) {
            EulerAngle eulerAngle = new EulerAngle(
                    node.rawX,
                    node.rawY,
                    node.rawZ
            );
            currentEuler.rotate(angleX, angleY, angleZ, XYZ);
            eulerAngle.rotate(angleX, angleY, angleZ, XYZ);
            node.setRotate(angleX, -angleY, angleZ);
            node.moveTo(eulerAngle.getX(), eulerAngle.getY(), eulerAngle.getZ());
        }
    }

    public void rotateByAxis(double angle, double x, double y, double z) {
        System.out.println("euler before: " + currentEuler);
        currentEuler = EulerAngle.fromArray(RotationUtil.rotateModel(
                currentEuler.toArray(),
                new Vector3D(x, y, z),
                angle*57.2972222222223,
                "XYZ"
        ));
        System.out.println("euler after: " + currentEuler);

//        angle = Math.toRadians(angle);
        for (ArmorStandNode node : this.armorStands) {
            node.setRotate(currentEuler.getX(), currentEuler.getY(), currentEuler.getZ());

            node.update();




//
//            Quaternion nodeQuaternion = new Quaternion(nodeEuler);
////            nodeQuaternion.rotateByAxis(new Vector3((float) x, (float) y, (float) z), (float) angle);
//            Vector3 euler = nodeQuaternion.eulerAngles();
//
//            node.setRotate(euler.x, euler.y, euler.z);
//            System.out.println(node.getVector());
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
            this.headPoseAngleY = angleY;
            this.headPoseAngleZ = angleZ;
        }

        public Vector3 getVector() {
            EulerAngle angle = new EulerAngle(1, 0, 0);
            angle.rotate(headPoseAngleX, headPoseAngleY, headPoseAngleZ, XYZ);
            return new Vector3((float) angle.getX(), (float) -angle.getY(), (float) angle.getZ());
        }

        public void setVector(Vector3 target) {
            double d = target.x;
            double e = target.y;
            double f = target.z;
            double g = Math.sqrt(d * d + f * f);
            headPoseAngleX = MathHelper.wrapDegrees((float)(Math.atan2(g, e) * 57.2957763671875) - 90.0F);
            headPoseAngleY = MathHelper.wrapDegrees((float)((Math.atan2(d, f) * 57.2957763671875)));
            headPoseAngleZ = 0;
            System.out.println(headPoseAngleX);
            System.out.println(headPoseAngleY);
            System.out.println(headPoseAngleZ);
        }

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
