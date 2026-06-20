# VeinMiner — Spigot Plugin for Minecraft 1.21.11

A lightweight Spigot plugin for Minecraft that removes the tedium of mining ore veins one block at a time. Break any ore block and the entire connected vein is automatically mined — drops included.

---

## Features

- **Instant vein mining** — breaking one ore block harvests the entire connected vein
- **Full ore support** — works with all vanilla ores:
  - Coal, Copper, Iron, Gold, Redstone, Lapis Lazuli, Diamond, Emerald
  - Deepslate variants of all the above
  - Nether Gold, Nether Quartz, and Ancient Debris
- **Tool-aware drops** — uses the player's held item to break blocks naturally, so Fortune and Silk Touch enchantments work correctly
- **Creative mode support** — in Creative, the vein is silently cleared (no drops, as expected)
- **Zero configuration** — no config files or commands needed; just drop in the jar and go

---

## Requirements

- **Minecraft / Spigot:** 1.21.1
- **Java:** 21+
- **API:** Spigot API `1.21.11-R0.1-SNAPSHOT`

---

## Installation

1. Build the plugin (see [Building](#building)) or download the latest release jar.
2. Place the `.jar` file into your server's `plugins/` folder.
3. Start or reload your server.
4. No further configuration required.

---

## Building

This project uses Maven. To build from source:

```bash
git clone https://github.com/USMCsky/VeinMiner_Spigot_Plugin.git
cd VeinMiner_Spigot_Plugin
mvn clean package
```

The compiled jar will be output to the `target/` directory.

---

## How It Works

When a player breaks an ore block, the plugin performs a **breadth-first search** across all 26 adjacent blocks (including diagonals) to find every connected ore of the same type. All connected blocks are then broken simultaneously using the player's held tool, ensuring natural drops with enchantment support.

In **Creative mode**, the vein is removed without generating drops.

---

## Plugin Info

| Field   | Value                              |
|---------|------------------------------------|
| Name    | VeinMiner                          |
| Version | 1.21.11                            |
| Author  | USMCsky                            |
| Main    | `com.usmcsky.veinMiner.VeinMiner`  |
| API     | 1.21                               |
