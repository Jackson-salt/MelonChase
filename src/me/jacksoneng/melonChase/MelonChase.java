package me.jacksoneng.melonChase;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class MelonChase implements Listener
{
    public static Material[] nonSolidBlocks = {
        Material.AIR,
        Material.WATER,
        Material.STATIONARY_WATER,
        Material.SNOW,
        Material.LONG_GRASS,
        Material.DEAD_BUSH,
        Material.CROPS
    };

    public static Material melon = Material.MELON_BLOCK;
    public static World world;
    public static int x, y, z;

    @EventHandler
    public void onPlayerClick(PlayerInteractEvent event)
    {
        Random ran = new Random();
        Block block = event.getClickedBlock();
        world = block.getWorld();

        x = block.getX();
        y = block.getY();
        z = block.getZ();

        if(block.getType() == melon && event.getAction() == Action.LEFT_CLICK_BLOCK)
        {
            if(isClear(x, y - 1, z))
            {
                setMelon(x, y - 1, z);
            }
            else
            {
                int direction = ran.nextInt(4);

                if(direction == 0)
                {
                    if(moveMelon(1, 0))
                    {
                        if(moveMelon(-1, 0))
                        {
                            if(moveMelon(0, 1))
                            {
                                moveMelon(0, -1);
                            }
                        }
                    }
                }
                else if(direction == 1)
                {
                    if(moveMelon(0, -1))
                    {
                        if(moveMelon(1, 0))
                        {
                            if(moveMelon(-1, 0))
                            {
                                moveMelon(0, 1);
                            }
                        }
                    }
                }
                else if(direction == 2)
                {
                    if(moveMelon(0, 1))
                    {
                        if(moveMelon(0, -1))
                        {
                            if(moveMelon(1, 0))
                            {
                                moveMelon(-1, 0);
                            }
                        }
                    }
                }
                else
                {
                    if(moveMelon(-1, 0))
                    {
                        if(moveMelon(0, 1))
                        {
                            if(moveMelon(0, -1))
                            {
                                moveMelon(1, 0);
                            }
                        }
                    }
                }
            }
        }
    }

    public static boolean blockIsClear(int x, int y, int z)
    {
        for(Material nonSolidBlock : nonSolidBlocks)
        {
            if(nonSolidBlock == getBlock(x, y, z))
            {
                return true;
            }
        }

        return false;
    }

    public static Material getBlock(int x, int y, int z)
    {
        return world.getBlockAt(x, y, z).getType();
    }

    public static boolean isClear(int x, int y, int z)
    {
        boolean[] bothTrue = new boolean[2];

        for(Material nonSolidBlock : nonSolidBlocks)
        {
            if(nonSolidBlock == getBlock(x, y, z))
            {
                bothTrue[0] = true;
                break;
            }
        }

        for(Material nonSolidBlock : nonSolidBlocks)
        {
            if(nonSolidBlock == getBlock(x, y - 1, z))
            {
                bothTrue[1] = true;
                break;
            }
        }

        return bothTrue[0] && !bothTrue[1];
    }

    public boolean moveMelon(int xVector, int zVector)
    {
        if(xVector == 0)
        {
            if(isClear(x, y - 1, z + zVector) && blockIsClear(x, y, z + zVector))
            {
                setMelon(x, y - 1, z + zVector);
            }
            else if(isClear(x, y, z + zVector))
            {
                setMelon(x, y, z + zVector);
            }
            else if(isClear(x, y + 1, z + zVector) && blockIsClear(x, y + 1, z))
            {
                setMelon(x, y + 1, z + zVector);
            }
            else
            {
                return true;
            }
        }
        else
        {
            if(isClear(x + xVector, y - 1, z) && blockIsClear(x + xVector, y, z))
            {
                setMelon(x + xVector, y - 1, z);
            }
            else if(isClear(x + xVector, y, z))
            {
                setMelon(x + xVector, y, z);
            }
            else if(isClear(x + xVector, y + 1, z) && blockIsClear(x, y + 1, z))
            {
                setMelon(x + xVector, y + 1, z);
            }
            else
            {
                return true;
            }
        }

        return false;
    }

    public void setMelon(int newX, int newY, int newZ)
    {
        world.getBlockAt(newX, newY, newZ).setType(melon);
        world.getBlockAt(x, y, z).setType(Material.AIR);
    }
}
