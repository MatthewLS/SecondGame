package GameObjects;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Exit extends GameObject implements Collidable{
    // If true, multiple players are accepted.
    int KoalaAcceptanceCapacity;
    int playersAccepted;
    /*
    Red: Exit can accept more than 1 player.
    Blue: Exit can accept only 1 player.
    Closed: Exit cannot accept any players.
     */
    BufferedImage imgRed;
    BufferedImage imgBlue;
    BufferedImage imgClosed;
    ArrayList<Koala> acceptedPlayers;

    public Exit(int x, int y, BufferedImage imgRed, BufferedImage imgBlue, BufferedImage imgClosed, int KoalaAcceptanceCapacity) {
        super(x, y, imgRed);
        this.KoalaAcceptanceCapacity = KoalaAcceptanceCapacity;
        playersAccepted = 0;
        this.imgRed = imgRed;
        this.imgBlue = imgBlue;
        this.imgClosed = imgClosed;
        acceptedPlayers = new ArrayList<>();
    }
    public void update() {
        // If only one more koala can be accepted, the door will be set to look blue;
        if (playersAccepted + 1 == KoalaAcceptanceCapacity) {
            setImg(imgBlue);
        } else if (playersAccepted >= KoalaAcceptanceCapacity) {
            setImg(imgClosed);
        }
    }
    @Override
    public void handleCollision(Collidable co) {
        if (co instanceof Koala) {
            if ((playersAccepted < KoalaAcceptanceCapacity) && !acceptedPlayers.contains(co)){
                acceptedPlayers.add((Koala)co);
                ((Koala)co).setHasExited();
                ((Koala)co).setRemoveThis(true);
                // Main will handle when a koala has been set as 'exited'.
                playersAccepted++;
            }
        }
    }
}
