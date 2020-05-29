package GameObjects;

import KoalaStuff.KoalaDriver;

import java.awt.image.BufferedImage;

public class Boulder extends GameObject implements Collidable {
    int vx;
    int vy;
    public Boulder(int x, int y, BufferedImage img) {
        super(x, y, img);
    }


    private void checkBorder() {
        if (getX() < 50) {
            setX(50);
        }
        if (getX() >= KoalaDriver.WORLD_WIDTH - 100) {
            setX(KoalaDriver.WORLD_WIDTH - 105);
        }
        if (getY() < 40) {
            setY(40);
        }
        if (getY() >= KoalaDriver.WORLD_HEIGHT - 120) {
            setY(KoalaDriver.WORLD_HEIGHT - 121);
        }
    }

    @Override
    public void handleCollision(Collidable co) {
        double calculatedAngle;
        try {
            calculatedAngle = Math.atan((getY() - ((GameObject)co).getY()) / (getX() - ((GameObject)co).getX()));
        } catch (ArithmeticException arithmeticException){
            calculatedAngle = Math.atan((getY() - ((GameObject)co).getY()) / (getX() + 1 - ((GameObject)co).getX()));
        }
        vx = (int) Math.round(Math.cos(calculatedAngle));
        vy = (int) Math.round(Math.sin(calculatedAngle));
        if (getX() < ((GameObject)co).getX()) {
            setX(this.getX() - vx);
        } else {
            setX(getX() + vx);
        }
        if (getY() < ((GameObject)co).getY()) {
            setY(getY() - vx);
        } else {
            setY(getY() + vx);
        }
        checkBorder();
    }

    @Override
    public void update() {
        hitBox.setRect(this.x,this.y,img.getWidth(),img.getHeight());
    }
}
