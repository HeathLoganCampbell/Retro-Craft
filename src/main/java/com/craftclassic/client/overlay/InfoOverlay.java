package com.craftclassic.client.overlay;

import com.craftclassic.client.Minecraft;
import com.craftclassic.client.graphics.Screen;
import com.craftclassic.client.input.Input;
import com.craftclassic.common.blocks.Block;
import com.craftclassic.common.entities.Player;
import com.craftclassic.common.utils.Location;

import java.awt.event.KeyEvent;

public class InfoOverlay
{
    private Minecraft minecraft;

    private Screen screen;

    private boolean show = false;

    private long debounceTimestamp = 0L;

    public InfoOverlay(Minecraft minecraft, Screen screen)
    {
        this.minecraft = minecraft;
        this.screen = screen;
    }

    public void tick()
    {
        if(Input.getKey(KeyEvent.VK_F2))
        {
            long now = System.currentTimeMillis();
            if(now < debounceTimestamp + 200L) return;
            debounceTimestamp = now;

            show = !show;
        }
    }

    public void render()
    {
        if(!show) return;

        Player player = this.minecraft.player;
        Location location = player.getLocation();

        this.screen.renderString("V0.4.5", 0, 0);
        this.screen.renderString(Block.getBlockById(Minecraft.getPlaceBlockTypeId()).getName(), 0, 7);
        this.screen.renderString("X: " + location.getX(), 0, 14);
        this.screen.renderString("Y: " + location.getY(), 0, 21);
        this.screen.renderString("Z: " + location.getZ(), 0, 28);
        this.screen.renderString("Pitch: " + location.getPitch(), 0, 28 + 7);
        this.screen.renderString("Yaw: " + location.getYaw(), 0, 28 + 14);
        this.screen.renderString("fps: " + Minecraft.FRAMES_PER_SECOND, 0, 28 + 21);

        if(!this.minecraft.input.isFocused())
        {
            String message = "CLICK TO FOCUS";
            this.screen.renderStringCenter(message);
        }
    }
}
