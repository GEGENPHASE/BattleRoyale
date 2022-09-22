package com.gegenphase.battleroyale.util.firework;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.inventory.meta.FireworkMeta;

import java.util.Random;

/**
 * @author GEGENPHASE
 * @version 22.09.2022
 **/
public class FireWorkUtil
{

    public static void spawnFireWork(Location loc)
    {
        //Spawn the Firework, get the FireworkMeta.
        Firework fw = (Firework) loc.getWorld().spawnEntity(loc, EntityType.FIREWORK);
        FireworkMeta fwm = fw.getFireworkMeta();

        //Our random generator
        Random r = new Random();

        //Get the type
        int rt = r.nextInt(5) + 1;
        FireworkEffect.Type type = Type.BALL;
        if (rt == 2)
        {
            type = Type.BALL_LARGE;
        }
        if (rt == 3)
        {
            type = Type.BURST;
        }
        if (rt == 4)
        {
            type = Type.CREEPER;
        }
        if (rt == 5)
        {
            type = Type.STAR;
        }

        //Get our random colors
        Color c1 = Color.fromBGR(r.nextInt(255),r.nextInt(255),r.nextInt(255));
        Color c2 = Color.fromBGR(r.nextInt(255),r.nextInt(255),r.nextInt(255));

        //Create our effect with this
        FireworkEffect effect = FireworkEffect.builder().flicker(r.nextBoolean()).withColor(c1).withFade(c2).with(type).trail(r.nextBoolean()).build();

        //Then apply the effect to the meta
        fwm.addEffect(effect);

        //Generate some random power and set it
        int rp = r.nextInt(4) + 1;
        fwm.setPower(rp);

        //Then apply this to our rocket
        fw.setFireworkMeta(fwm);
    }

}
