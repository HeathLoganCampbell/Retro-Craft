package com.craftclassic.client.input;

import com.craftclassic.client.Minecraft;
import org.jnativehook.GlobalScreen;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseInputListener;
import org.jnativehook.mouse.NativeMouseListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseGrabberListener implements NativeMouseInputListener
{
    private JFrame frame;

    private long lastUpdated = 0l;

    public MouseGrabberListener(JFrame jFrame) {
        this.frame = jFrame;

        Point locationOnScreen = frame.getLocationOnScreen();
        Point centerOfWindow = new Point(locationOnScreen.x + frame.getWidth() / 2, locationOnScreen.y + frame.getHeight() / 2);
    }

    @Override
    public void nativeMouseClicked(NativeMouseEvent nativeMouseEvent) {

    }

    @Override
    public void nativeMousePressed(NativeMouseEvent nativeMouseEvent) {

    }

    @Override
    public void nativeMouseReleased(NativeMouseEvent nativeMouseEvent) {

    }

    @Override
    public void nativeMouseMoved(NativeMouseEvent e)
    {
        moveHead(e);
    }

    @Override
    public void nativeMouseDragged(NativeMouseEvent e)
    {
        moveHead(e);
    }

    private void moveHead(NativeMouseEvent e)
    {
        if(!frame.isFocused()) return;
        if(!frame.isActive()) return;
        long now = System.currentTimeMillis();
        long pollingRate = 20L;
        if(now < lastUpdated + pollingRate) return;
        lastUpdated = now;

        Point locationOnScreen = frame.getLocationOnScreen();
        Point centerOfWindow = new Point(locationOnScreen.x + frame.getWidth() / 2, locationOnScreen.y + frame.getHeight() / 2);

        if(centerOfWindow.getX() == e.getX() && centerOfWindow.getY() == e.getY()) return;

        float cursorWidthOffset = 21f;
        float deltaX = e.getX() - centerOfWindow.x + cursorWidthOffset;
        float deltaY = e.getY() - centerOfWindow.y;

        deltaX *= 0.03;
        deltaY *= 0.03;

        Minecraft.INSTANCE.player.turn(deltaX, -deltaY);

        GlobalScreen.postNativeEvent(new NativeMouseEvent(
                NativeMouseEvent.NATIVE_MOUSE_MOVED,
                0,
                centerOfWindow.x,
                centerOfWindow.y,
                NativeMouseEvent.BUTTON1
        ));
    }
}
