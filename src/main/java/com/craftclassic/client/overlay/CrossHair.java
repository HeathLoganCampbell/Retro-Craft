package com.craftclassic.client.overlay;

import com.craftclassic.client.graphics.Screen;

public class CrossHair
{
    private Screen screen;

    public CrossHair(Screen screen)
    {
        this.screen = screen;
    }

    public void render()
    {
        invertPixel(this.screen.halfWidth, (this.screen.halfHeight));
        invertPixel(this.screen.halfWidth, (this.screen.halfHeight - 1));
        invertPixel(this.screen.halfWidth, (this.screen.halfHeight + 1));
        invertPixel(this.screen.halfWidth, (this.screen.halfHeight - 2));
        invertPixel(this.screen.halfWidth, (this.screen.halfHeight - 3));
        invertPixel(this.screen.halfWidth + 1, (this.screen.halfHeight - 1));
        invertPixel(this.screen.halfWidth + 2, (this.screen.halfHeight - 1));
        invertPixel(this.screen.halfWidth - 1, (this.screen.halfHeight - 1));
        invertPixel(this.screen.halfWidth - 2, (this.screen.halfHeight - 1));
    }

    public void invertPixel(int xWidth, int yHeight)
    {
        int currentPixel = 0xFFFFFF - screen.getPixel(xWidth, yHeight);
        screen.setPixel(xWidth, yHeight, currentPixel);
    }
}
