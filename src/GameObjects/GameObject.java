package GameObjects;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public abstract class GameObject{

    //these are shared will all children
    protected double x, y, angle=360;
    protected BufferedImage img;
    protected Rectangle2D.Double hitBox;

    protected boolean removeThis = false;

    public GameObject(double x, double y,BufferedImage img){
        this.x = x; this.y = y; this.img = img;
        this.hitBox = new Rectangle2D.Double(x,y,img.getWidth(),img.getHeight());
    }

    public void init(double x, double y, BufferedImage img){
        this.x = x; this.y = y; this.img = img;
        this.hitBox = new Rectangle2D.Double(x,y,img.getWidth(),img.getHeight());
    }

    public void drawImage(Graphics g){
        AffineTransform rotation = AffineTransform.getTranslateInstance(x,y);
        rotation.rotate(Math.toRadians(angle), this.img.getWidth() / 2.0, this.img.getHeight() / 2.0);
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(this.img,rotation,null);

    }

    public Rectangle2D.Double getHitBox(){
        return this.hitBox;
    }

    public boolean getRemoveThis(){
        return removeThis;
    }
    public void setRemoveThis(Boolean removeThis) {
        this.removeThis = removeThis;
    }
    public abstract void update();

    public void nullThis(){
        this.y = +9500;
        this.x += 9500;
        hitBox.setRect(this.x,this.y,img.getWidth(),img.getHeight());
    }

    public double getX(){
        return x;
    }
    public double getY(){
        return y;
    }
    public void setX(double x){this.x = x;}
    public void setY(double y){this.y = y;}

    public double getAngle(){return this.angle;}
    public void setAngle(double angle){this.angle = angle;}

    public void setImg(BufferedImage img){this.img = img;}


}