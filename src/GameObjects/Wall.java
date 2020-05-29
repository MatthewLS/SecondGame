package GameObjects;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Wall extends GameObject implements Collidable {

    public Wall(double x, double y, BufferedImage img){
        super(x,y,img);
    }
    public void init(double x, double y,BufferedImage img){
        super.init(x, y, img);
    }

    @Override
    public void handleCollision(Collidable co) {
        if (this.hitBox.intersects(((GameObject)co).getHitBox())){
            if (co instanceof Koala){
                ((Koala) co).setCanMove(false);
                ((Koala) co).setX(((Koala) co).getOldX());
                ((Koala) co).setY(((Koala) co).getOldY());
            } else if (co instanceof Boulder){

            }
        }
    }

    public void drawImage(Graphics g) {
        AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
//        rotation.rotate(Math.toRadians(angle), this.img.getWidth() / 2.0, this.img.getHeight() / 2.0);

        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(this.img, rotation, null);
//        g.setColor(Color.CYAN);
//        g.drawRect((int)x,(int)y,this.img.getWidth(),this.img.getHeight());
    }

    @Override
    public void update() {}
}
