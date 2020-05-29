package GameObjects;

import java.awt.image.BufferedImage;
import java.nio.Buffer;

public class Switch extends GameObject implements Collidable {

    Lock lock;

    int member;
    public Switch(int x, int y, BufferedImage img, int member) {
        super(x, y, img);
        this.removeThis = false;
        this.member = member;
    }
    public Switch(int x, int y, BufferedImage img, Lock lock, int member){
        super(x,y,img);
        this.lock = lock;
        this.removeThis = false;
        this.member = member;
    }

    public int getMember() {
        return member;
    }
    public void setLock(Lock lock) {
        this.lock = lock;
    }
    public boolean getRemoveThis() {
        return removeThis;
    }




    @Override
    public void handleCollision(Collidable co) {
        if (co instanceof Koala){
            this.lock.setUnlocked();
            this.lock.setRemoveThis(true);
            removeThis=true;
        }
    }

    @Override
    public void update() {

    }
}
