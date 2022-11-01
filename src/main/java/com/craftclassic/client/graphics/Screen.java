package com.craftclassic.client.graphics;

import com.craftclassic.client.Main;
import com.craftclassic.client.assets.FontRender;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class Screen
{
    private FontRender font;

    private int width = 0;
    private int height = 0;

    public int halfWidth = 0;
    public int halfHeight = 0;

    public int quartWidth = 0;
    public int quartHeight = 0;

    public int eigthWidth = 0;
    public int eigthHeight = 0;

    public BufferedImage frameBuffer = new BufferedImage(Main.WIDTH, Main.HEIGHT, BufferedImage.TYPE_INT_RGB);

    public int[] imageData = ((DataBufferInt)frameBuffer.getRaster().getDataBuffer()).getData();

    public Screen(int width, int height)
    {
        this.width = width;
        this.height = height;

        this.halfHeight = height / 2;
        this.halfWidth = width / 2;

        this.quartHeight = halfHeight / 2;
        this.quartWidth = halfWidth / 2;

        this.eigthHeight = quartHeight / 2;
        this.eigthWidth = quartWidth / 2;

        this.font = new FontRender(this);
    }

    public int getPixel(int x, int y)
    {
        return imageData[x + y * this.width];
    }

    public void setPixel(int x, int y, int pixel)
    {
        if(x >= Main.WIDTH || x < 0)
        {
            return;
        }

        if(y >= Main.HEIGHT || y < 0)
        {
            return;
        }

        imageData[x + y * this.width] = pixel;
    }

    public void renderString(String message, int screenX, int screenY)
    {
        this.font.renderString(message, screenX, screenY);
    }

    public void renderStringCenter(String message)
    {
        int x = message.length() * this.font.letterWidth;
        int y = this.eigthHeight - (this.font.letterHeight / 2) - this.font.letterHeight;
        this.font.renderString(message, x, y);
    }
}
