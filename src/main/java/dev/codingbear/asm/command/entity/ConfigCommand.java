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
                .withSubcommand(rotate())
                .withSubcommand(rotateByAxis());
    }

    CommandAPICommand tp() {
        return new CommandAPICommand("tp")
                .withArguments(new IntegerArgument("index"))
                .withArguments(new LocationArgument("location"))
                .executesPlayer((player, args) -> {
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

    CommandAPICommand rotate() {
        return new CommandAPICommand("rotate")
                .withArguments(new IntegerArgument("index"))
                .withArguments(new DoubleArgument("rx"))
                .withArguments(new DoubleArgument("ry"))
                .withArguments(new DoubleArgument("rz"))
                .executesPlayer((player, args) -> {
                    int index = (int) args.get("index") - 1;
                    double rx = (double) args.get("rx");
                    double ry = (double) args.get("ry");
                    double rz = (double) args.get("rz");
                    rx = Math.toRadians(rx);
                    ry = Math.toRadians(ry);
                    rz = Math.toRadians(rz);
                    if (ModelEntity.entities.size() <= index) {
                        player.sendMessage("Entity not found");
                        return;
                    } else if (0 > index) {
                        player.sendMessage("Entity not found");
                        return;
                    }
                    ModelEntity.entities.get(index).rotateEuler(rx, ry, rz);
                    ModelEntity.entities.get(index).update();
                    player.sendMessage("§aEntity rotated");
                });
    }

    CommandAPICommand rotateByAxis() {
        return new CommandAPICommand("rotateByAxis")
                .withArguments(new IntegerArgument("index"))
                .withArguments(new DoubleArgument("x"))
                .withArguments(new DoubleArgument("y"))
                .withArguments(new DoubleArgument("z"))
                .withArguments(new DoubleArgument("angle"))
                .executesPlayer((player, args) -> {
                    int index = (int) args.get("index") - 1;
                    double x = (double) args.get("x");
                    double y = (double) args.get("y");
                    double z = (double) args.get("z");
                    double angle = (double) args.get("angle");
                    angle = Math.toRadians(angle);
                    if (ModelEntity.entities.size() <= index) {
                        player.sendMessage("Entity not found");
                        return;
                    } else if (0 > index) {
                        player.sendMessage("Entity not found");
                        return;
                    }
                    ModelEntity.entities.get(index).rotateByAxis(angle, x, y, z);
//                    ModelEntity.entities.get(index).update();
                    player.sendMessage("§aEntity rotated");
                });
    }
}
