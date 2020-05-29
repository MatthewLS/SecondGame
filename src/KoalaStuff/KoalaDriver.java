package KoalaStuff;

import GameObjects.*;
import MapLoading.MapLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import static javax.imageio.ImageIO.read;

/**
 * Main driver class of Tank Example.
 * Class is responsible for loading resources and
 * initializing game objects. Once completed, control will
 * be given to infinite loop which will act as our game loop.
 * A very simple game loop.
 * @author anthony-pc
 */
public class KoalaDriver extends JPanel  {

    public static int WORLD_WIDTH=1, WORLD_HEIGHT=1;

    public static HashMap<String,BufferedImage> imgList = new HashMap<>();
    public static HashMap<String, URL> soundURLs = new HashMap<>();

    private Graphics2D buffer;
    private BufferedImage world;


    private JFrame jf;
    private ArrayList<Koala> koalas = new ArrayList<>();
    private Background background;

    private ArrayList<Koala> exitedKoalas = new ArrayList<>();

    private int levelCount = 0;
    private static boolean gameWon = false;

    //gameObjects to be rendered/painted and checked for collisions
    private static CopyOnWriteArrayList<GameObject> gameObjects = new CopyOnWriteArrayList<>();
    private static CopyOnWriteArrayList<Collidable> collidables = new CopyOnWriteArrayList<>();



    public static void main(String[] args) {
        Thread x;
        KoalaDriver koalaDriver = new KoalaDriver();
        if (!KoalaDriver.gameWon)
            koalaDriver.init();
        try {
            while (!(KoalaDriver.gameWon) && !koalaDriver.gameLost()) {
                if (koalaDriver.levelWon()){
                    koalaDriver.levelCount++;
                    koalaDriver.reset();
                    koalaDriver.init();
                }

                koalaDriver.checkCollisions();
                koalaDriver.updateGameObjects();
                koalaDriver.handleRemove();
                koalaDriver.repaint();
                Thread.sleep(1000 / 144);
            }
            if (koalaDriver.gameLost()) {
                koalaDriver.repaint();
            }
        } catch (InterruptedException ignored) {
        }
    }

    private boolean gameLost() {
        return koalas.size() + exitedKoalas.size() < 3;
    }

    private boolean levelWon() {
        return exitedKoalas.size() == 3;
    }

    private void reset() {
        gameObjects.clear();
        koalas.clear();
        exitedKoalas.clear();
        WORLD_HEIGHT = 0;
        WORLD_WIDTH = 0;
    }

    /**
     * Initializes the world.
     * This method does the following:
     * - Creates the JFrame.
     * - Loads images and map file.
     * - Creates background.
     * - Creates a keyListener.
     * - Creates a world.
     */
    private void init() {
        if (levelCount == 0) {
            this.jf = new JFrame("Koalabr8");
        }
        this.jf.setFocusable(true);
        this.jf.requestFocus();
//        this.world = new BufferedImage(this.WORLD_WIDTH, this.WORLD_HEIGHT, BufferedImage.TYPE_INT_RGB);
        BufferedImage backgroundimg = null, koala1img = null, koala2img = null, koala3img = null, wallimg = null, bladeimg = null,
                boulderimg = null, tntimg = null, locklockedimg = null, lockunlockedimg = null, switchimg = null
                , exitredimg = null, exitblueimg = null, exitclosedimg = null;
        try {
            /*
             * There is a subtle difference between using class
             * loaders and File objects to read in resources for your
             * tank games. First, File objects when given to input readers
             * will use your project's working directory as the base path for
             * finding the file. Class loaders will use the class path of the project
             * as the base path for finding files. For Intellij, this will be in the out
             * folder. if you expand the out folder, the expand the production folder, you
             * will find a folder that has the same name as your project. This is the folder
             * where your class path points to by default. Resources, will need to be stored
             * in here for class loaders to load resources correctly.
             *
             * Also one important thing to keep in mind, Java input readers given File objects
             * cannot be used to access file in jar files. So when building your jar, if you want
             * all files to be stored inside the jar, you'll need to class loaders and not File
             * objects.
             *
             */
            //Using class loaders to read in resources
            //Using file objects to read in resources.
            koala1img = read(new File("resources/koala1.png"));
            koala2img = read(new File("resources/koala2.png"));
            koala3img = read(new File("resources/koala3.png"));
            wallimg = read(new File("resources/wallTile.png"));
            tntimg = read(new File("resources/tnt.png"));
            lockunlockedimg = read(new File("resources/lockUnlocked.png"));
            locklockedimg = read(new File("resources/lockLocked.png"));
            switchimg = read(new File("resources/switch.png"));
            bladeimg = read(new File("resources/saw.png"));
            boulderimg = read(new File("resources/boulder.png"));
            exitclosedimg = read(new File("resources/exitClosed.png"));
            exitredimg = read(new File("resources/exitRed.png"));
            exitblueimg = read(new File("resources/exitBlue.png"));
            backgroundimg = read(new File("resources/backgroundTile.png"));

            KoalaDriver.imgList.put("koala1img",koala1img);
            KoalaDriver.imgList.put("koala2img",koala2img);
            KoalaDriver.imgList.put("koala3img",koala3img);
            KoalaDriver.imgList.put("wallimg",wallimg);
            KoalaDriver.imgList.put("tntimg",tntimg);
            KoalaDriver.imgList.put("lockunlockedimg",lockunlockedimg);
            KoalaDriver.imgList.put("locklockedimg",locklockedimg);
            KoalaDriver.imgList.put("switchimg",switchimg);
            KoalaDriver.imgList.put("bladeimg",bladeimg);
            KoalaDriver.imgList.put("boulderimg",boulderimg);
            KoalaDriver.imgList.put("exitclosedimg",exitclosedimg);
            KoalaDriver.imgList.put("exitredimg",exitredimg);
            KoalaDriver.imgList.put("exitblueimg",exitblueimg);
            KoalaDriver.imgList.put("backgroundimg",backgroundimg);

            this.background = new Background(backgroundimg);
            KoalaDriver.addToGameObjects(this.background);

        } catch (IOException ex  ){//UnsupportedAudioFileException ex) {
            System.out.println(ex.getMessage());
        }
        try {
            MapLoader mapLoader = new MapLoader(this.levelCount);
            koalas = mapLoader.init();
            this.world = new BufferedImage(KoalaDriver.WORLD_WIDTH, KoalaDriver.WORLD_HEIGHT, BufferedImage.TYPE_INT_RGB);
        } catch(Exception e) {
            //if statements goes when maploader has failed, indicating that we reached the end of the level and have won the game
            if (KoalaDriver.gameWon) {
                this.world = new BufferedImage(450, 200, BufferedImage.TYPE_INT_RGB);
                this.jf.setLayout(new BorderLayout());
                this.jf.add(this);
                this.jf.setSize(450, 200);
                this.jf.setResizable(false);
                jf.setLocationRelativeTo(null);
                this.jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                this.jf.setVisible(true);
//            this.gameEnd = true;
                return;
            }
        }
            KoalaControl koalaControl = new KoalaControl(koalas, KeyEvent.VK_W,
                    KeyEvent.VK_S,
                    KeyEvent.VK_A,
                    KeyEvent.VK_D);


        jf.setLayout(new BorderLayout());
        jf.add(this,BorderLayout.CENTER);

        jf.addKeyListener(koalaControl);
        jf.setSize(WORLD_WIDTH, WORLD_HEIGHT);
        jf.setResizable(false);
        jf.setLocationRelativeTo(null);
        jf.setDefaultCloseOperation(jf.EXIT_ON_CLOSE);
        jf.setVisible(true);

        jf.setBackground(Color.gray);
    } //end of TankeDriver.init()

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        super.paintComponent(g2);

        if(gameWon){
            g2.setColor(Color.black);
            g2.drawString("Game Won!",80,80);
            return;
        }
        // creates a graphics context which allows you to draw over the splash screen
//        this.background.drawImage(buffer);
        this.buffer = this.world.createGraphics();

        //draw over splash screen
        for (GameObject gameObject : gameObjects) {
            gameObject.drawImage(buffer);
        }

        g2.drawImage(world,0,0,null);
        g2.setColor(Color.white);
        g2.drawString("Roombas Rescued: " + exitedKoalas.size(),25,30);
        if (gameLost()){
            g2.drawString("Game Lost!", KoalaDriver.WORLD_WIDTH - 200,30);
        }
    }

    public static void addToGameObjects(GameObject go){
        gameObjects.add(go);
    }
    public static void addToCollidables(Collidable obj) { collidables.add(obj);}

    public void setKoalas(ArrayList<Koala> k){
        this.koalas.clear();
        koalas.addAll(k);
    }

    public void checkCollisions(){
        try{
        collidables.forEach(c1 -> {
            collidables.forEach(c2 -> {
                if (c1 != c2 && ((GameObject)c1).getHitBox().intersects(((GameObject)c2).getHitBox())){//if they're not the same obj and their hitboxes intersect
                    c1.handleCollision(c2);
                    c2.handleCollision(c1);
                }
            });
        });
        } catch (Exception e){
            System.out.println("TankDriver.checkCollisions error-----------" + e);
        }
    }

    public void updateGameObjects(){
        KoalaDriver.gameObjects.forEach(gameObject -> {
            gameObject.update();
            if (gameObject instanceof Koala){
                if (gameObject.getRemoveThis())
                    koalas.remove(gameObject);
                if (((Koala) gameObject).getHasExited())
                    exitedKoalas.add((Koala)gameObject);
            }
        });
    }

    public void handleRemove(){
        collidables.forEach(co -> {
            if (((GameObject)co).getRemoveThis()){
                collidables.remove(co);
                gameObjects.remove(co);
                ((GameObject)co).nullThis();
            }
        });
    }

    public static void gameWon(){
        gameWon = true;
    }
}

