package dev.codingbear.asm;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class Events implements Listener {

    @EventHandler
    public void BlockPlaceEvent(BlockPlaceEvent event) {
        Model.ModelBlockData blockData = new Model.ModelBlockData(event.getBlock().getType(), event.getBlock().getBlockData());
        System.out.println(blockData);
    }
}

