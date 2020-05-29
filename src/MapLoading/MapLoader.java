package MapLoading;

import GameObjects.*;
import KoalaStuff.KoalaDriver;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

/*
class MapLoader for the purpose of loading obstacles (walls, power ups, etc.) onto the map.
 */
public class MapLoader {

    Class<?> c;
    GameObject gameObject;
    private String className;
    private static HashMap<String, String> nameTable;
    BufferedReader buffreader;
    ArrayList<Lock> locks = new ArrayList<>();
    ArrayList<Switch> switches = new ArrayList<>();
    private int level;
    static{
        nameTable = new HashMap<>();
        nameTable.put("0","");
        nameTable.put("1","koala1img");
        nameTable.put("2","koala2img");
        nameTable.put("3","koala3img");
        nameTable.put("8","wallimg");
        nameTable.put("9","wallimg");

        nameTable.put("B","boulderimg");
        nameTable.put("T","tntimg");

//        nameTable.put("#","exitimg");
//        nameTable.put("$","exitedimg");
    }

    public MapLoader(int level){
        this.level=level;
    }

    public ArrayList<Koala> init(){
        ArrayList<Koala> koalas = new ArrayList<>();
        int wallWidth = KoalaDriver.imgList.get("wallimg").getWidth();
        int wallHeight = KoalaDriver.imgList.get("wallimg").getHeight();
        File mapData = new File("resources/map" + level + ".txt");

        try {
            InputStreamReader isr = new InputStreamReader(KoalaDriver.class.getClassLoader().getResourceAsStream("maps/map"+level+".txt"));
            BufferedReader mapReader = new BufferedReader(isr);
            String row = mapReader.readLine();
            String mapInfo[] = row.split("\t");
            int numRows = Integer.parseInt(mapInfo[0]), numCols = Integer.parseInt(mapInfo[1]);
            int xloc = 0,yloc =0;

            for (int i = 0;i<numRows;i++){
                yloc = i*50;
                row = mapReader.readLine();
                mapInfo= row.split("\t");

                for (int j = 0;j<numCols;j++) {
                    String current = mapInfo[j];
                    xloc = j * 50;
                    if (current.equals(""))
                        continue;

                    if (current.equals("1")) {
                        Koala koala = new Koala(xloc, yloc, 0, 0, 270, KoalaDriver.imgList.get(nameTable.get("1")));
                        koalas.add(koala);
                        KoalaDriver.addToGameObjects(koala);
                        KoalaDriver.addToCollidables(koala);
                    }
                    else if (current.equals("2")) {
                        Koala koala = new Koala(xloc, yloc, 0, 0, 270, KoalaDriver.imgList.get(nameTable.get("2")));
                        koalas.add(koala);
                        KoalaDriver.addToGameObjects(koala);
                        KoalaDriver.addToCollidables(koala);
                    }
                    else if (current.equals("3")) {
                        Koala koala = new Koala(xloc, yloc, 0, 0, 270, KoalaDriver.imgList.get(nameTable.get("3")));
                        koalas.add(koala);
                        KoalaDriver.addToGameObjects(koala);
                        KoalaDriver.addToCollidables(koala);
                    }
                    else if (current.equals("8")) {
                        Wall wall = new Wall(xloc, yloc, KoalaDriver.imgList.get(nameTable.get("8")));
                        KoalaDriver.addToGameObjects(wall);
                        KoalaDriver.addToCollidables(wall);
                    }
                    else if (current.equals("9"))
                        KoalaDriver.addToGameObjects(new Wall(xloc, yloc, KoalaDriver.imgList.get(nameTable.get("9"))));
                    else if (current.equals("T")) {
                        TNT tnt = new TNT(xloc, yloc, KoalaDriver.imgList.get(nameTable.get("T")));
                        KoalaDriver.addToCollidables(tnt);
                        KoalaDriver.addToGameObjects(tnt);
                    }
                    else if (current.equals("#")) {
                            Exit exit = new Exit(xloc, yloc, KoalaDriver.imgList.get("exitredimg"),
                                    KoalaDriver.imgList.get("exitblueimg"),KoalaDriver.imgList.get("exitclosedimg"),3);

                            KoalaDriver.addToCollidables(exit);
                            KoalaDriver.addToGameObjects(exit);
                    }
                    else if (current.equals("$")) {
                        Exit exit = new Exit(xloc, yloc, KoalaDriver.imgList.get("exitredimg"),
                                KoalaDriver.imgList.get("exitblueimg"),KoalaDriver.imgList.get("exitclosedimg"),1);

                        KoalaDriver.addToCollidables(exit);
                        KoalaDriver.addToGameObjects(exit);
                    }
                    else if (current.equals("H")) {
                        Blade blade = new Blade(xloc, yloc, KoalaDriver.imgList.get("bladeimg"), false);
                        KoalaDriver.addToCollidables(blade);
                        KoalaDriver.addToGameObjects(blade);
                    }
                    else if (current.equals("V")) {
                        Blade blade = new Blade(xloc, yloc, KoalaDriver.imgList.get("bladeimg"), true);
                        KoalaDriver.addToCollidables(blade);
                        KoalaDriver.addToGameObjects(blade);
                    }
                    else if (current.equals("B")) {
                        Boulder boulder = new Boulder(xloc, yloc, KoalaDriver.imgList.get(nameTable.get("B")));
                        KoalaDriver.addToCollidables(boulder);
                        KoalaDriver.addToGameObjects(boulder);
                    }

                    else if (current.charAt(0) >= 97 && current.charAt(0) <= 122) {
                        char character = current.charAt(0);
                        if (character >= 97 && character <= 122) {
                            if (character % 2 == 1) {//is a lock
                                boolean lockAdded = false;
                                for (Switch oneSwitch : switches) {
                                    if (oneSwitch.getMember() == (int) character) {
                                        Lock lock = new Lock(xloc, yloc, KoalaDriver.imgList.get("locklockedimg"), KoalaDriver.imgList.get("lockunlockedimg"), (int) (current.charAt(0)));
                                        KoalaDriver.addToGameObjects(lock);
                                        KoalaDriver.addToCollidables(lock);
                                        KoalaDriver.addToGameObjects(oneSwitch);
                                        KoalaDriver.addToCollidables(oneSwitch);
                                        oneSwitch.setLock(lock);
                                        lockAdded = true;
                                    }
                                }
                                if (!lockAdded) {
                                    locks.add(new Lock(xloc, yloc, KoalaDriver.imgList.get("locklockedimg"), KoalaDriver.imgList.get("lockunlockedimg"), (int) (current.charAt(0))));
                                }
                            } else {//is a switch
                                boolean switchAdded = false;
                                int lockSize = locks.size();
                                for (Lock lock : locks) {
                                    if (lock.getMember() == ((int)character) - 1) {
                                        KoalaDriver.addToGameObjects(lock);
                                        KoalaDriver.addToCollidables(lock);
                                        Switch aSwitch = new Switch(xloc, yloc, KoalaDriver.imgList.get("switchimg"), lock, ((int)character) - 1);
                                        KoalaDriver.addToGameObjects(aSwitch);
                                        KoalaDriver.addToCollidables(aSwitch);
                                        switchAdded = true;
                                    }
                                }
                                if (!switchAdded) {
                                    switches.add(new Switch(xloc, yloc, KoalaDriver.imgList.get("switchimg"), (int) character - 1));

                                }
                            }
                        }
                    }
                }
            }
            KoalaDriver.WORLD_WIDTH = xloc+50;
            KoalaDriver.WORLD_HEIGHT = yloc+70;
        } catch(Exception e) {
            System.out.println("MapLoader.init() problem------" + e);
            KoalaDriver.gameWon();
        }
        return koalas;
    }
}
