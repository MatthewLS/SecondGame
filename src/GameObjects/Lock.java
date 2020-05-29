package GameObjects;

import java.awt.image.BufferedImage;

public class Lock extends GameObject implements Collidable {

    boolean locked;
    BufferedImage unlockedimg;

    int member;

    public Lock(int x, int y, BufferedImage lockedimg, BufferedImage unlockedimg, int member) {
        super(x, y, lockedimg);
        this.unlockedimg = unlockedimg;
        locked = true;
        this.member = member;
    }

    public int getMember() {
        return member;
    }
    public void setUnlocked() {
        locked = false;
        super.setImg(unlockedimg);
    }
    public boolean getLockedStatus() {
        return locked;
    }





    @Override
    public void handleCollision(Collidable co) {

    }

    @Override
    public void update() {
        if (removeThis){
            nullThis();
        }
    }
}
