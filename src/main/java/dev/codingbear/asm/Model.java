package dev.codingbear.asm;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import de.tr7zw.nbtapi.NBT;
import dev.codingbear.utils.Int3d;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.type.Stairs;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class Model {
    static File modelDir = Main.getPath("models");
    List<ItemStack> headItems;
    Int3d size;
    Int3d centerOffset;

    public static class ModelBlockData {
        public Material type;
        public Stairs.Shape shape = null;
        public Vector direction = null;
        public ModelBlockData(Material type, BlockData blockData) {
            this.type = type;
            if (blockData instanceof Stairs) {
                Stairs stairs = (Stairs) blockData;
                this.shape = stairs.getShape();
                this.direction = stairs.getFacing().getDirection();
            }
            if (blockData instanceof Directional) {
                Directional directional = (Directional) blockData;
                this.direction = directional.getFacing().getDirection();
            }
        }

        @Override
        public String toString() {
            return "ModelBlockData{" +
                    "type=" + type +
                    ", shape=" + shape +
                    ", direction=" + direction.getX() + "," + direction.getY() + "," + direction.getZ() +
                    '}';
        }
    }

    public Model(List<ItemStack> headItems, Int3d size, Int3d centerOffset) {
        this.headItems = headItems;
        this.size = size;
        this.centerOffset = centerOffset;
    }

    public File saveModel() throws IOException {
        int filename = 0;
        while (new File(modelDir, filename + ".json").exists()) {
            filename++;
        }

        JsonArray jsonHeadItems = new JsonArray();
        for (ItemStack item : headItems) {
            item.setAmount(1);
            jsonHeadItems.add(NBT.itemStackToNBT(item).toString());
        }

        JsonObject jsonModel = new JsonObject();
        jsonModel.add("size", size.toJson());
        jsonModel.add("centerOffset", centerOffset.toJson());
        jsonModel.add("headItems", jsonHeadItems);
        File result = new File(modelDir, filename + ".json");
        Files.write(result.toPath(), jsonModel.toString().getBytes());
        return result;
    }

    public static File getModelFile(String filename) throws IOException {
        if (filename.contains("..") || filename.contains("/") || filename.contains("\\")) {
            throw new IOException("Invalid filename");
        }
        File file = new File(modelDir, filename);
        if (!file.exists()) {
            throw new IOException("Model file not found");
        }
        return file;
    }

    public static Model loadModel(File file) throws IOException {
        String content = new String(Files.readAllBytes(file.toPath()));
        JsonObject jsonModel = JsonParser.parseString(content).getAsJsonObject();

        Int3d size = Int3d.fromJson(jsonModel.get("size").getAsJsonArray());
        Int3d centerOffset = Int3d.fromJson(jsonModel.get("centerOffset").getAsJsonArray());

        JsonArray jsonHeadItems = jsonModel.get("headItems").getAsJsonArray();
        List<ItemStack> headItems = new ArrayList<>();
        for (JsonElement jsonElement : jsonHeadItems) {
            ItemStack item = NBT.itemStackFromNBT(NBT.parseNBT(jsonElement.getAsString()));
            headItems.add(item);
        }

        return new Model(headItems, size, centerOffset);
    }

    public static Model scanModel(Location location, Int3d delta, Int3d centerOffset) {
        List<ItemStack> headItems = new ArrayList<>();

        for (int x = 0; x < delta.x; x++) {
            for (int y = 0; y < delta.y; y++) {
                for (int z = 0; z < delta.z; z++) {
                    Location loc = location.clone().add(x, y, z);
                    Block block = loc.getBlock();
                    Material type = block.getType();
                    if (type == Material.PLAYER_HEAD || type == Material.PLAYER_WALL_HEAD) {
                        for (ItemStack i : loc.getBlock().getDrops()){
                            if (i.getType() != Material.PLAYER_HEAD) continue;
                            ItemStack item = new ItemStack(i);
                            item.setAmount(1);
                            headItems.add(item);
                            break;
                        }
                        continue;
                    }
//                    if (block.getBlockData() instanceof Stairs) {
//                        Stairs stairs = (Stairs) block.getBlockData();
//                        stairs.getShape();
//                        BlockFace face = stairs.getFacing();
//                        face.getDirection()
//
//                    }
                    ItemStack item = new ItemStack(type);
                    headItems.add(item);
                }
            }
        }

        return new Model(headItems, delta, centerOffset);
    }

    public static boolean deleteModel(String filename) {
        if (filename.contains("..") || filename.contains("/") || filename.contains("\\")) return false;
        File file = new File(modelDir, filename);
        if (!file.exists()) return false;
        return file.delete();
    }

    public static List<String> listModels() {
        List<String> models = new ArrayList<>();
        for (File file : modelDir.listFiles()) {
            models.add(file.getName());
        }
        return models;
    }



}
