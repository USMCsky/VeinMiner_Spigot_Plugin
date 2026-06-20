package com.usmcsky.veinMiner;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

public final class VeinMiner extends JavaPlugin implements Listener {

    private static final Set<Material> ORE_MATERIALS = EnumSet.of(
            Material.COAL_ORE,
            Material.DEEPSLATE_COAL_ORE,
            Material.COPPER_ORE,
            Material.DEEPSLATE_COPPER_ORE,
            Material.IRON_ORE,
            Material.DEEPSLATE_IRON_ORE,
            Material.GOLD_ORE,
            Material.DEEPSLATE_GOLD_ORE,
            Material.REDSTONE_ORE,
            Material.DEEPSLATE_REDSTONE_ORE,
            Material.LAPIS_ORE,
            Material.DEEPSLATE_LAPIS_ORE,
            Material.DIAMOND_ORE,
            Material.DEEPSLATE_DIAMOND_ORE,
            Material.EMERALD_ORE,
            Material.DEEPSLATE_EMERALD_ORE,
            Material.NETHER_GOLD_ORE,
            Material.NETHER_QUARTZ_ORE,
            Material.ANCIENT_DEBRIS
    );

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {
    }

    @EventHandler(ignoreCancelled = true)
    public void onBlockBreak(BlockBreakEvent event) {
        Block origin = event.getBlock();
        if (!isOre(origin.getType())) {
            return;
        }

        if (event.getPlayer().getGameMode() == GameMode.CREATIVE) {
            clearVein(origin);
            return;
        }

        breakConnectedOre(origin, event.getPlayer().getInventory().getItemInMainHand());
    }

    private void clearVein(Block origin) {
        for (Block block : collectConnectedOre(origin)) {
            if (!block.equals(origin)) {
                block.setType(Material.AIR, false);
            }
        }
    }

    private void breakConnectedOre(Block origin, org.bukkit.inventory.ItemStack tool) {
        for (Block block : collectConnectedOre(origin)) {
            if (!block.equals(origin)) {
                block.breakNaturally(tool);
            }
        }
    }

    private Set<Block> collectConnectedOre(Block origin) {
        Set<Location> visited = new HashSet<>();
        Set<Block> vein = new HashSet<>();
        Deque<Block> queue = new ArrayDeque<>();

        visited.add(origin.getLocation());
        queue.add(origin);

        while (!queue.isEmpty()) {
            Block current = queue.removeFirst();
            if (!isOre(current.getType())) {
                continue;
            }

            vein.add(current);

            for (int xOffset = -1; xOffset <= 1; xOffset++) {
                for (int yOffset = -1; yOffset <= 1; yOffset++) {
                    for (int zOffset = -1; zOffset <= 1; zOffset++) {
                        if (xOffset == 0 && yOffset == 0 && zOffset == 0) {
                            continue;
                        }

                        Block neighbor = current.getRelative(xOffset, yOffset, zOffset);
                        Location neighborLocation = neighbor.getLocation();
                        if (isOre(neighbor.getType()) && visited.add(neighborLocation)) {
                            queue.addLast(neighbor);
                        }
                    }
                }
            }
        }

        return vein;
    }

    private boolean isOre(Material material) {
        return ORE_MATERIALS.contains(material);
    }
}
