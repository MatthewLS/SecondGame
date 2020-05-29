package GameObjects;


import KoalaStuff.KoalaDriver;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Background extends GameObject {

    private BufferedImage img;
    public Background(BufferedImage img){
        super(0,0,img);
        this.img = img;
    }

    @Override
    public void drawImage(Graphics g) {
        for (int x = 0; x <= KoalaDriver.WORLD_WIDTH; x += this.img.getWidth()){
            for (int y = 0; y <= KoalaDriver.WORLD_HEIGHT; y += this.img.getHeight()){
                AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
                rotation.rotate(Math.toRadians(90), this.img.getWidth() / 2.0, this.img.getHeight() / 2.0);
                Graphics2D g2d = (Graphics2D) g;
                g2d.drawImage(this.img, rotation, null);
            }
        }
    }

    @Override
    public void update() {

    }
}
