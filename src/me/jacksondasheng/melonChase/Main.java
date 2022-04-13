package me.jacksondasheng.melonChase;

import java.util.Random;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    private final ConsoleCommandSender sender = Bukkit.getConsoleSender();
    
    @Override
    public void onEnable() {
        sender.sendMessage("Enabling MelonChase");
        Bukkit.getServer().getPluginManager().registerEvents(
            new Listener() {
                private final Material[] nonSolidBlocks = {
                    Material.AIR,
                    Material.WATER,
                    Material.LAVA,
                    Material.FIRE,
                    Material.SNOW,
                    Material.GRASS,
                    Material.LONG_GRASS,
                    Material.DEAD_BUSH,
                    Material.VINE
                };

                private final Material melon = Material.MELON;
                private World world;
                private int x, y, z;

                @EventHandler
                public void onPlayerInteract(PlayerInteractEvent event) {
                    Random ran = new Random();
                    Block block = event.getClickedBlock();
                    world = block.getWorld();

                    x = block.getX();
                    y = block.getY();
                    z = block.getZ();

                    if(block.getType() == melon && event.getAction() == Action.LEFT_CLICK_BLOCK) {
                        if(isClear(x, y - 1, z)) {
                            setMelon(x, y - 1, z);
                        } else {
                            int direction = ran.nextInt(4);

                            if(direction == 0) {
                                if(moveMelon(1, 0)) {
                                    if(moveMelon(-1, 0)) {
                                        if(moveMelon(0, 1)) {
                                            moveMelon(0, -1);
                                        }
                                    }
                                }
                            } else if(direction == 1) {
                                if(moveMelon(0, -1)) {
                                    if(moveMelon(1, 0)) {
                                        if(moveMelon(-1, 0)) {
                                            moveMelon(0, 1);
                                        }
                                    }
                                }
                            } else if(direction == 2) {
                                if(moveMelon(0, 1)) {
                                    if(moveMelon(0, -1)) {
                                        if(moveMelon(1, 0)) {
                                            moveMelon(-1, 0);
                                        }
                                    }
                                }
                            } else {
                                if(moveMelon(-1, 0)) {
                                    if(moveMelon(0, 1)) {
                                        if(moveMelon(0, -1)) {
                                            moveMelon(1, 0);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                public boolean blockIsClear(int x, int y, int z) {
                    for(Material nonSolidBlock : nonSolidBlocks) {
                        if(nonSolidBlock == getBlock(x, y, z)) {
                            return true;
                        }
                    }

                    return false;
                }

                public Material getBlock(int x, int y, int z) {
                    return world.getBlockAt(x, y, z).getType();
                }

                public boolean isClear(int x, int y, int z) {
                    boolean[] bothTrue = new boolean[2];

                    for(Material nonSolidBlock : nonSolidBlocks) {
                        if(nonSolidBlock == getBlock(x, y, z)) {
                            bothTrue[0] = true;
                            break;
                        }

                        if(nonSolidBlock == getBlock(x, y - 1, z)) {
                            bothTrue[1] = true;
                            break;
                        }
                    }

                    return bothTrue[0] && !bothTrue[1];
                }

                public boolean moveMelon(int xVector, int zVector) {
                    if(xVector == 0) {
                        if(isClear(x, y - 1, z + zVector) && blockIsClear(x, y, z + zVector)) {
                            setMelon(x, y - 1, z + zVector);
                        } else if(isClear(x, y, z + zVector)) {
                            setMelon(x, y, z + zVector);
                        } else if(isClear(x, y + 1, z + zVector) && blockIsClear(x, y + 1, z)) {
                            setMelon(x, y + 1, z + zVector);
                        } else {
                            return true;
                        }
                    } else {
                        if(isClear(x + xVector, y - 1, z) && blockIsClear(x + xVector, y, z)) {
                            setMelon(x + xVector, y - 1, z);
                        } else if(isClear(x + xVector, y, z)) {
                            setMelon(x + xVector, y, z);
                        } else if(isClear(x + xVector, y + 1, z) && blockIsClear(x, y + 1, z)) {
                            setMelon(x + xVector, y + 1, z);
                        } else {
                            return true;
                        }
                    }

                    return false;
                }

                public void setMelon(int newX, int newY, int newZ) {
                    world.getBlockAt(newX, newY, newZ).setType(melon);
                    world.getBlockAt(x, y, z).setType(Material.AIR);
                }
            },
            this
        );
        sender.sendMessage("MelonChase enabled");
    }
    
    @Override
    public void onDisable() {
        sender.sendMessage("MelonChase disabled");
    }
}
