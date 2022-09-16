package com.gegenphase.battleroyale.loot.lootcontainer.gui;

import org.junit.Test;

import static com.gegenphase.battleroyale.loot.lootcontainer.gui.LootContainerEditorGUI.LOOT_CONTAINER_EDITOR_TITLE;
import static org.junit.Assert.assertEquals;

/**
 * @author GEGENPHASE
 * @version 12.09.2022
 **/
public class GetLootContainerDataFromStringTest
{

    @Test
    public void testGetFromName()
    {
        String[] got = getFromName(LOOT_CONTAINER_EDITOR_TITLE + "nether_world -1 2 23131341241233");

        assertEquals("nether_world", got[0]);
        assertEquals("-1", got[1]);
        assertEquals("2", got[2]);
        assertEquals("23131341241233", got[3]);
    }


    private String[] getFromName(String title)
    {
        String[] array = new String[4];

        String toSplit = "";

        for (int i = title.length() - 1; i > 0; i--)
        {
            if (title.charAt(i) == '§')
            {
                toSplit = title.substring(i + 2);
            }
        }

        String[] split = toSplit.split(" ");

        array[0] = split[0];
        array[1] = split[1];
        array[2] = split[2];
        array[3] = split[3];

        System.out.println("'" + toSplit + "'");

        return split;
    }

}
