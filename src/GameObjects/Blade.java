package GameObjects;

import KoalaStuff.KoalaDriver;

import java.awt.image.BufferedImage;

public class Blade extends GameObject implements Collidable {

    private boolean axis;
    private boolean direction;
//    private boolean toBeDestroyed = false;
    private int R = 2;
    private int vx;
    private int vy;

    public Blade(int x, int y, BufferedImage img, boolean axis) {
        super(x, y, img);
        this.axis = axis;
        this.direction = true;
        if (axis) {
            setAngle(90);
        }
    }

    private void moveForwards() {
        vx = (int) Math.round(R * Math.cos(Math.toRadians(getAngle())));
        vy = (int) Math.round(R * Math.sin(Math.toRadians(getAngle())));
        setX(getX() + vx);
        setY(getY() + vy);
        checkBorder();
    }

    private void moveBackwards() {
        vx = (int) Math.round(R * Math.cos(Math.toRadians(getAngle())));
        vy = (int) Math.round(R * Math.sin(Math.toRadians(getAngle())));
        setX(getX() - vx);
        setY(getY() - vy);
        checkBorder();
    }

    private void checkBorder() {
        if (getX() < 50) {
            setX(50);
            changeDirection();
        }
        if (getX() >= KoalaDriver.WORLD_WIDTH - 100) {
            setX(KoalaDriver.WORLD_WIDTH - 105);
            changeDirection();
        }
        if (getY() < 40) {
            setY(40);
            changeDirection();
        }
        if (getY() >= KoalaDriver.WORLD_HEIGHT - 120) {
            setY(KoalaDriver.WORLD_HEIGHT - 121);
            changeDirection();
        }
    }

    // When collisions are handled, direction must be changed using this method.
    public void changeDirection() {
        direction = !direction;
//        System.out.println(direction);
    }

    /**
     * The direction of movement depends on the direction variable, set during collision handling.
     */

    @Override
    public void update() {
        checkBorder();
        if (direction) {
            moveForwards();
        } else {
            moveBackwards();
        }
        hitBox.setRect(this.x,this.y,img.getWidth(),img.getHeight());
    }



    @Override
    public void handleCollision(Collidable co) {
        changeDirection();
        update();
        if (co instanceof Boulder){
            removeThis = true;
        }
    }
}
