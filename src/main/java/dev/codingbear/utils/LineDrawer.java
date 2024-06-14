package dev.codingbear.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;

public class LineDrawer {
    public static void drawLine(World world, int x, int y, int z, int x2, int y2, int z2) {
        double dx = x2 - x;
        double dy = y2 - y;
        double dz = z2 - z;

        double r = Math.sqrt(dx * dx + dy * dy + dz * dz);
        double stepX = (dx/r) * 0.25;
        double stepY = (dy/r) * 0.25;
        double stepZ = (dz/r) * 0.25;
        int stepAmount = (int) (r / 0.25);

        double ix = 0, iy = 0, iz = 0;

        for (double i = 0; i < stepAmount+1; i++) {
            Location loc = new Location(world, x + ix, y + iy, z + iz);
            world.spawnParticle(Particle.FLAME, loc, 1, 0, 0, 0, 0);
            ix += stepX;
            iy += stepY;
            iz += stepZ;
        }
    }

    public static void drawCubeBorder(World world, int x, int y, int z, int x2, int y2, int z2) {
        drawLine(world, x, y, z, x2, y, z);
        drawLine(world, x, y, z, x, y2, z);
        drawLine(world, x, y, z, x, y, z2);
        drawLine(world, x2, y2, z2, x2, y2, z);
        drawLine(world, x2, y2, z2, x2, y, z2);
        drawLine(world, x2, y2, z2, x, y2, z2);
        drawLine(world, x2, y, z, x2, y2, z);
        drawLine(world, x2, y, z, x2, y, z2);
        drawLine(world, x, y2, z, x, y2, z2);
        drawLine(world, x, y2, z, x2, y2, z);
        drawLine(world, x, y, z2, x, y2, z2);
        drawLine(world, x, y, z2, x2, y, z2);

    }
}
