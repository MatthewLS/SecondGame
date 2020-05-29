package GameObjects;

import KoalaStuff.*;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

/**
 *
 * @author anthony-pc
 */
public class Koala extends GameObject implements Collidable{
    private final double R = 2;
    private final double ROTATION_SPEED = 1.5;

    private double oldX,oldY,
    vx,vy,angle,defaultX,defaultY;
    private int health;
    private boolean canMove = true;
    private boolean hasExited = false;

    private boolean UpPressed;
    private boolean DownPressed;
    private boolean RightPressed;
    private boolean LeftPressed;

    private static final int DEFAULT_TIME_VALUE = 30;




    public Koala(double x, double y, double vx, double vy, double angle, BufferedImage koalaImage) {
        super(x,y,koalaImage);
        defaultX=x;
        defaultY=y;
        this.angle=angle;
        this.vx = vx;
        this.vy = vy;
        health = 1;
        hasExited = false;
        oldX = x;
        oldY = y;
    }

    public void toggleUpPressed() {
        this.UpPressed = true;
    }
    public void toggleDownPressed() {
        this.DownPressed = true;
    }
    public void toggleRightPressed() {
        this.RightPressed = true;
    }
    public void toggleLeftPressed() {
        this.LeftPressed = true;
    }
    public void unToggleUpPressed() {
        this.UpPressed = false;
    }
    public void unToggleDownPressed() {
        this.DownPressed = false;
    }
    public void unToggleRightPressed() {
        this.RightPressed = false;
    }
    public void unToggleLeftPressed() {
        this.LeftPressed = false;
    }
    private void rotateLeft() {
        this.angle -= this.ROTATION_SPEED;
    }
    private void rotateRight() {
        this.angle += this.ROTATION_SPEED;
    }
    private void moveBackwards() {
        vx =  R * Math.cos(Math.toRadians(angle));
        vy =  R * Math.sin(Math.toRadians(angle));
        x -= vx;
        y -= vy;
        checkBorder();
    }
    private void moveForwards() {
        vx =  R * Math.cos(Math.toRadians(angle));
        vy =  R * Math.sin(Math.toRadians(angle));
        x += vx;
        y += vy;
        checkBorder();
    }


    public void update() {
        oldX = this.getX();
        oldY = this.getY();
        if (health <= 0) {
            health = 5;
            super.setX(defaultX);
            super.setY(defaultY);
        }

        if (this.UpPressed) {
            this.moveForwards();
        }
        if (this.DownPressed) {
            this.moveBackwards();
        }
        if (this.LeftPressed) {
            this.rotateLeft();
        }
        if (this.RightPressed) {
            this.rotateRight();
        }
        hitBox.setRect(this.x,this.y,img.getWidth(),img.getHeight());

        if(!canMove){
            this.canMove = true;
            return;
        }
    }


    private void checkBorder() {
        if (x < 50) {
            x = 50;
        }
        if (x >= KoalaDriver.WORLD_WIDTH - 100) {
            x = KoalaDriver.WORLD_WIDTH - 100;
        }
        if (y < 50) {
            y = 50;
        }
        if (y >= KoalaDriver.WORLD_HEIGHT - 120) {
            y = KoalaDriver.WORLD_HEIGHT - 120;
        }
    }



    @Override
    public void handleCollision(Collidable co) {
        if (co instanceof Wall || co instanceof Koala){
            this.setX(oldX);
            this.setY(oldY);
            checkBorder();
        } else if ((co instanceof TNT) || (co instanceof Blade)){
            removeThis = true;
            ((GameObject) co).setRemoveThis(true);
        } else if (co instanceof Lock){
            if (((Lock) co).getLockedStatus()){
                this.setX(oldX);
                this.setY(oldY);
                checkBorder();
            }
        }
    }

    public void setCanMove(boolean flag){
        this.canMove = flag;
    }
    public void setRemoveThis(Boolean b){
        this.removeThis = b;
    }
    public boolean getHasExited(){
        return this.hasExited;
    }
    public void setHasExited(){
        this.hasExited=true;
    }


    //Positional stuff
    public double getX(){return this.x;}
    public double getY(){return this.y;}
    public void setX(double d){this.x = d;}
    public void setY(double d){this.y = d;}
    public double getOldY(){return oldY;}
    public double getOldX(){return oldX;}

    @Override
    public String toString() {
        return "x=" + (int)x + ", y=" + (int)y + ", angle=" + (int)angle;
    }

    public void drawImage(Graphics g) {
        AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
        rotation.rotate(Math.toRadians(angle), img.getWidth() / 2.0, img.getHeight() / 2.0);

        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(img, rotation, null);

//        g2d.setColor(Color.CYAN);
//        g2d.drawRect((int)x,(int)y,this.tankImage.getWidth(),this.tankImage.getHeight());
    }
}