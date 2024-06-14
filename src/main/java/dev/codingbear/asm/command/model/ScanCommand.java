package dev.codingbear.asm.command.model;

import dev.codingbear.asm.Main;
import dev.codingbear.asm.Model;
import dev.codingbear.utils.Int3d;
import dev.codingbear.utils.LineDrawer;
import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.CommandPermission;
import dev.jorel.commandapi.arguments.IntegerArgument;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import java.io.File;
import java.util.HashMap;

public class ScanCommand {
    public static class ScanArea {
        public enum Direction {
            XP, YP, ZP, XN, YN, ZN
        }

        Location start;
        int dx;
        int dy;
        int dz;

        ScanArea(Location start, int dx, int dy, int dz) {
            this.start = start;
            this.dx = dx;
            this.dy = dy;
            this.dz = dz;
        }

        void expand(int length, Direction direction) {
            switch (direction) {
                case XP:
                    dx += length;
                    break;
                case YP:
                    dy += length;
                    break;
                case ZP:
                    dz += length;
                    break;
                case XN:
                    dx -= length;
                    if (dx < 1) dx = 1;
                    break;
                case YN:
                    dy -= length;
                    if (dy < 1) dy = 1;
                    break;
                case ZN:
                    dz -= length;
                    if (dz < 1) dz = 1;
                    break;
            }
        }

        void move(int length, Direction direction) {
            switch (direction) {
                case XP:
                    start.add(length, 0, 0);
                    break;
                case YP:
                    start.add(0, length, 0);
                    break;
                case ZP:
                    start.add(0, 0, length);
                    break;
                case XN:
                    start.add(-length, 0, 0);
                    break;
                case YN:
                    start.add(0, -length, 0);
                    break;
                case ZN:
                    start.add(0, 0, -length);
                    break;
            }
        }
    }

    public HashMap<Player, ScanArea> scanAreas = new HashMap<>();
    public BukkitTask task = Bukkit.getScheduler().runTaskTimer(Main.getMain(), () -> {
        for (Player player : scanAreas.keySet()) {
            ScanArea scanArea = scanAreas.get(player);
            World world = scanArea.start.getWorld();
            int x = scanArea.start.getBlockX();
            int y = scanArea.start.getBlockY();
            int z = scanArea.start.getBlockZ();
            int x2 = x + scanArea.dx;
            int y2 = y + scanArea.dy;
            int z2 = z + scanArea.dz;
            LineDrawer.drawCubeBorder(world, x, y, z, x2, y2, z2);
        }
    }, 0L, 10);


    public CommandAPICommand command() {
        return new CommandAPICommand("scan")
                .withPermission(CommandPermission.OP)
                .withSubcommand(prepare())
                .withSubcommand(start())
                .withSubcommand(expand())
                .withSubcommand(move());
    }

    CommandAPICommand prepare() {
        return new CommandAPICommand("prepare")
                .withPermission(CommandPermission.OP)
                .withArguments(
                        new IntegerArgument("dx"),
                        new IntegerArgument("dy"),
                        new IntegerArgument("dz")
                )
                .executesPlayer((player, args) -> {
                    int dx = (int) (args.get("dx"));
                    int dy = (int) (args.get("dy"));
                    int dz = (int) (args.get("dz"));
                    scanAreas.put(player, new ScanArea(player.getLocation(), dx, dy, dz));
                });
    }

    CommandAPICommand start() {
        return new CommandAPICommand("start")
                .withPermission(CommandPermission.OP)
                .withArguments(
                        new IntegerArgument("cx"),
                        new IntegerArgument("cy"),
                        new IntegerArgument("cz")
                )
                  .executesPlayer((player, args) -> {
                    Model model = Model.scanModel(
                            scanAreas.get(player).start,
                            new Int3d(
                                    scanAreas.get(player).dx,
                                    scanAreas.get(player).dy,
                                    scanAreas.get(player).dz
                            ),
                            new Int3d(
                                    ((int) args.get("cx")),
                                    ((int) args.get("cy")),
                                    ((int) args.get("cz"))
                            )
                    );
                    try{
                        File file = model.saveModel();
                        player.sendMessage("Model saved to " + file.getName());
                        scanAreas.remove(player);
                    }
                    catch (Exception e){
                        player.sendMessage("Failed to save model");
                        player.sendMessage(e.getMessage());
                    }

                });
    }

    CommandAPICommand expand() {
        return new CommandAPICommand("expand")
                .withPermission(CommandPermission.OP)
                .withArguments(
                        new IntegerArgument("length")
                )
                .executesPlayer((player, args) -> {
                    int length = (int) args.get("length");
                    Vector playerDirection = player.getLocation().getDirection();
                    ScanArea.Direction direction;

                    double x = Math.abs(playerDirection.getX());
                    double y = Math.abs(playerDirection.getY());
                    double z = Math.abs(playerDirection.getZ());
                    if (x > y && x > z) {
                        direction = playerDirection.getX() > 0 ? ScanArea.Direction.XP : ScanArea.Direction.XN;
                    } else if (y > x && y > z) {
                        direction = playerDirection.getY() > 0 ? ScanArea.Direction.YP : ScanArea.Direction.YN;
                    } else if (z > x && z > y) {
                        direction = playerDirection.getZ() > 0 ? ScanArea.Direction.ZP : ScanArea.Direction.ZN;
                    } else {
                        player.sendMessage("§cPlease look at a direction");
                        return;
                    }
                    scanAreas.get(player).expand(length, direction);
                });
    }

    CommandAPICommand move(){
        return new CommandAPICommand("move")
                .withPermission(CommandPermission.OP)
                .withArguments(
                        new IntegerArgument("length")
                )
                .executesPlayer((player, args) -> {
                    int length = (int) args.get("length");
                    Vector playerDirection = player.getLocation().getDirection();
                    ScanArea.Direction direction;

                    double x = Math.abs(playerDirection.getX());
                    double y = Math.abs(playerDirection.getY());
                    double z = Math.abs(playerDirection.getZ());
                    if (x > y && x > z) {
                        direction = playerDirection.getX() > 0 ? ScanArea.Direction.XP : ScanArea.Direction.XN;
                    } else if (y > x && y > z) {
                        direction = playerDirection.getY() > 0 ? ScanArea.Direction.YP : ScanArea.Direction.YN;
                    } else if (z > x && z > y) {
                        direction = playerDirection.getZ() > 0 ? ScanArea.Direction.ZP : ScanArea.Direction.ZN;
                    } else {
                        player.sendMessage("§cPlease look at a direction");
                        return;
                    }
                    scanAreas.get(player).move(length, direction);
                });
    }
}
