import java.awt.AWTException;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.MouseInfo;
import java.awt.PointerInfo;
import java.awt.Robot;

public class Main {

    public static void main(String[] args) throws AWTException, InterruptedException {
        //Get the primary monitor from the environment
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        //Create and start the thread that monitors the position of the mouse
        Thread observerThread = new Thread(new Observer(gd));
        observerThread.start();

    }

    private static class Observer implements Runnable{
        private GraphicsDevice mainMonitor;
        private Robot robot;
        int width, height;

        public Observer(GraphicsDevice gd){
            mainMonitor = gd;
            width = mainMonitor.getDisplayMode().getWidth();
            height = mainMonitor.getDisplayMode().getHeight();
            try {
                robot = new Robot();
            } catch (AWTException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        @Override
        public void run() {
            while(true){
                //Check the monitor on which the mouse is currently displayed
                PointerInfo pointerInfo = MouseInfo.getPointerInfo();
                GraphicsDevice device = pointerInfo.getDevice();
                if(!mainMonitor.equals(device)){
                    //If the mouse is not on the primary monitor move it to the center of the primary monitor.
                    robot.mouseMove(width/2, height/2);
                }
                //Wait a while before checking the position of the mouse again.
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }
}