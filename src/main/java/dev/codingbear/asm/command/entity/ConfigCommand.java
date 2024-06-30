package dev.codingbear.asm.command.entity;

import dev.codingbear.asm.ModelEntity;
import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.arguments.DoubleArgument;
import dev.jorel.commandapi.arguments.IntegerArgument;
import dev.jorel.commandapi.arguments.LocationArgument;
import org.bukkit.Location;

public class ConfigCommand {
    public CommandAPICommand command() {
        return new CommandAPICommand("config")
                .withSubcommand(tp())
                .withSubcommand(rotateX())
                .withSubcommand(rotateY())
                .withSubcommand(rotateZ());
    }

    CommandAPICommand tp() {
        return new CommandAPICommand("tp")
                .withArguments(new IntegerArgument("index"))
                .withArguments(new LocationArgument("location"))
                .executes((player, args) -> {
                    int index = (int) args.get("index") - 1;
                    Location location = (Location) args.get("location");
                    if (ModelEntity.entities.size() <= index) {
                        player.sendMessage("Entity not found");
                        return;
                    } else if (0 > index) {
                        player.sendMessage("Entity not found");
                        return;
                    }
                    ModelEntity.entities.get(index).moveTo(location);
                    ModelEntity.entities.get(index).update();
                    player.sendMessage("§aEntity teleported");
                });
    }

    CommandAPICommand rotateX() {
        return new CommandAPICommand("rotateX")
                .withArguments(new IntegerArgument("index"))
                .withArguments(new DoubleArgument("angle"))
                .executes((player, args) -> {
                    int index = (int) args.get("index") - 1;
                    double angle = (double) args.get("angle");
                    angle = Math.toRadians(angle);
                    if (ModelEntity.entities.size() <= index) {
                        player.sendMessage("Entity not found");
                        return;
                    } else if (0 > index) {
                        player.sendMessage("Entity not found");
                        return;
                    }
                    ModelEntity.entities.get(index).rotateX(angle);
                    ModelEntity.entities.get(index).update();
                    player.sendMessage("§aEntity rotated");
                });
    }

    CommandAPICommand rotateY() {
        return new CommandAPICommand("rotateY")
                .withArguments(new IntegerArgument("index"))
                .withArguments(new DoubleArgument("angle"))
                .executes((player, args) -> {
                    int index = (int) args.get("index") - 1;
                    double angle = (double) args.get("angle");
                    angle = Math.toRadians(angle);
                    if (ModelEntity.entities.size() <= index) {
                        player.sendMessage("Entity not found");
                        return;
                    } else if (0 > index) {
                        player.sendMessage("Entity not found");
                        return;
                    }
                    ModelEntity.entities.get(index).rotateY(angle);
                    ModelEntity.entities.get(index).update();
                    player.sendMessage("§aEntity rotated");
                });
    }

    CommandAPICommand rotateZ() {
        return new CommandAPICommand("rotateZ")
                .withArguments(new IntegerArgument("index"))
                .withArguments(new DoubleArgument("angle"))
                .executes((player, args) -> {
                    int index = (int) args.get("index") - 1;
                    double angle = (double) args.get("angle");
                    angle = Math.toRadians(angle);
                    if (ModelEntity.entities.size() <= index) {
                        player.sendMessage("Entity not found");
                        return;
                    } else if (0 > index) {
                        player.sendMessage("Entity not found");
                        return;
                    }
                    ModelEntity.entities.get(index).rotateZ(angle);
                    ModelEntity.entities.get(index).update();
                    player.sendMessage("§aEntity rotated");
                });
    }

//    @Deprecated
//    CommandAPICommand rotate() {
//        return new CommandAPICommand("rotate")
//                .withArguments(new IntegerArgument("index"))
//                .withArguments(new DoubleArgument("rx"))
//                .withArguments(new DoubleArgument("ry"))
//                .withArguments(new DoubleArgument("rz"))
//                .executes((player, args) -> {
//                    int index = (int) args.get("index") - 1;
//                    double rx = (double) args.get("rx");
//                    double ry = (double) args.get("ry");
//                    double rz = (double) args.get("rz");
//                    rx = Math.toRadians(rx);
//                    ry = Math.toRadians(ry);
//                    rz = Math.toRadians(rz);
//                    if (ModelEntity.entities.size() <= index) {
//                        player.sendMessage("Entity not found");
//                        return;
//                    } else if (0 > index) {
//                        player.sendMessage("Entity not found");
//                        return;
//                    }
//                    ModelEntity.entities.get(index).rotateEuler(rx, ry, rz);
//                    ModelEntity.entities.get(index).update();
//                    player.sendMessage("§aEntity rotated");
//                });
//    }
//
//    @Deprecated
//    CommandAPICommand rotateTest(){
//        return new CommandAPICommand("rotateTest")
//                .withArguments(new IntegerArgument("experimental_index"))
//                .withArguments(new IntegerArgument("comparison_index"))
//                .executes((player, args) -> {
//                    double rx = Math.random() * Math.PI * 2;
//                    double ry = Math.random() * Math.PI * 2;
//                    double rz = Math.random() * Math.PI * 2;
//                    int experimentalIndex = (int) args.get("experimental_index") - 1;
//                    int comparisonIndex = (int) args.get("comparison_index") - 1;
//                    if (ModelEntity.entities.size() <= experimentalIndex) {
//                        player.sendMessage("Entity not found");
//                        return;
//                    } else if (0 > experimentalIndex) {
//                        player.sendMessage("Entity not found");
//                        return;
//                    }
//
//                    if (ModelEntity.entities.size() <= comparisonIndex) {
//                        player.sendMessage("Entity not found");
//                        return;
//                    } else if (0 > comparisonIndex) {
//                        player.sendMessage("Entity not found");
//                        return;
//                    }
//
//                    ModelEntity.entities.get(experimentalIndex).rotateEuler(rx, ry, rz);
//                    ModelEntity.entities.get(experimentalIndex).update();
//                    ModelEntity.entities.get(comparisonIndex).rotateEuler(rx, ry, rz);
//                    ModelEntity.entities.get(comparisonIndex).update();
//                    player.sendMessage("§aEntity rotated");
//                });
//    }
//
//    @Deprecated
//    CommandAPICommand rotateAdd() {
//        return new CommandAPICommand("rotateAdd")
//                .withArguments(new IntegerArgument("index"))
//                .withArguments(new DoubleArgument("angleX"))
//                .withArguments(new DoubleArgument("angleY"))
//                .withArguments(new DoubleArgument("angleZ"))
//                .executes((player, args) -> {
//                    int index = (int) args.get("index") - 1;
//                    double rx = (double) args.get("angleX");
//                    double ry = (double) args.get("angleY");
//                    double rz = (double) args.get("angleZ");
//                    rx = Math.toRadians(rx);
//                    ry = Math.toRadians(ry);
//                    rz = Math.toRadians(rz);
//                    if (ModelEntity.entities.size() <= index) {
//                        player.sendMessage("Entity not found");
//                        return;
//                    } else if (0 > index) {
//                        player.sendMessage("Entity not found");
//                        return;
//                    }
//                    ModelEntity.entities.get(index).rotationAdd(rx, ry, rz);
//                    ModelEntity.entities.get(index).update();
////                    ModelEntity.entities.get(index).update();
//                    player.sendMessage("§aEntity rotated");
//                });
//    }
}
