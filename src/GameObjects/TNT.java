package GameObjects;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;

public class TNT extends GameObject implements Collidable {

    public TNT(int x, int y, BufferedImage img){
        super(x,y,img);
    }
    
    @Override
    public void update() {

    }

    boolean handleCollisions(Collidable co) {
        // Redundant but for readability.
        if (co instanceof Boulder || co instanceof Koala) {
            removeThis = true;
        }
        return true;
    }

    public boolean getRemoveThis() {
        return removeThis;
    }

    @Override
    public void handleCollision(Collidable co) {

    }
}
