package application.Game.Map;

import application.Game.Node.*;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;

import static application.Config.*;

public class Level {
    static Image end = new Image("end.gif");
    List<Player> players = new ArrayList<>();
    List<Line> lines = new ArrayList<>();
    List<Block> blocks = new ArrayList<>();
    List<Portal> portals = new ArrayList<>();
    List<Block> ends = new ArrayList<>();
    List<Key> keys = new ArrayList<>();

    public Level(int nowLevel) { }

    public Bean get(GraphicsContext gc) {
        CANVAS_WEIGHT = (int) (Data.LevelMap[nowLevel - 1][0] * UNIT_SIZE);
        CANVAS_HEIGHT = (int) (Data.LevelMap[nowLevel - 1][1] * UNIT_SIZE);

        getPlayers(gc);
        getLines(gc);
        getBlocks(gc);
        getPortals(gc);
        getEnds(gc);
        getKeys(gc);

        // 自动切换到下一关
        ++nowLevel;

        //  返回地图类
        return new Bean(MAP, players, lines, blocks, portals, ends, keys);
    }

    public List<Player> getPlayers(GraphicsContext gc) {
        // 玩家
        players.add(new Player(gc,
            ColorType.get(Data.LevelStart[nowLevel - 1][2], Data.LevelStart[nowLevel - 1][3], Data.LevelStart[nowLevel - 1][4]),
            Data.LevelStart[nowLevel - 1][0], Data.LevelStart[nowLevel - 1][1]));
        return players;
    }

    public List<Line> getLines(GraphicsContext gc) {
        // 激光
        for (int i = 0; i < Data.LevelLine[nowLevel - 1].length; i++) {
            double[] l = Data.LevelLine[nowLevel - 1][i];
            Line line = new Line(gc, ColorType.get(l[4], l[5], l[6]),
                getLine(ColorType.get(l[4], l[5], l[6])),
                l[0], l[1], l[2], l[3]);
            lines.add(line);
        }
        return lines;
    }

    public List<Block> getBlocks(GraphicsContext gc) {
        // 颜色块
        for (int i = 0; i < Data.LevelBlock[nowLevel - 1].length; i++) {
            int[] b = Data.LevelBlock[nowLevel - 1][i];
            Block block = new Block(gc, ColorType.get(b[2], b[3], b[4]),
                new Image("block/" + ColorType.get(b[2], b[3], b[4]).toString() + "3.png"),
                b[0], b[1]);
            blocks.add(block);
        }
        return blocks;
    }

    public List<Portal> getPortals(GraphicsContext gc) {
        // 传送门
        if (Data.LevelPortal[nowLevel - 1].length != 0) {
            for (int i = 0; i < Data.LevelPortal[nowLevel - 1].length; i++) {
                int[] b = Data.LevelPortal[nowLevel - 1][i];
                Portal portal1 = new Portal(
                    gc,
                    ColorType.get(b[4], b[5], b[6]),
                    new Image("portal/" + ColorType.get(b[4], b[5], b[6]).toString() + ".png"),
                    b[0],
                    b[1]
                );
                Portal portal2 = new Portal(
                    gc,
                    ColorType.get(b[4], b[5], b[6]),
                    new Image("portal/" + ColorType.get(b[4], b[5], b[6]).toString() + ".png"),
                    b[2],
                    b[3]
                );
                portal1.setOther(portal2);
                portal2.setOther(portal1);
                portals.add(portal1);
                portals.add(portal2);
            }
        }
        return portals;
    }

    public List<Block> getEnds(GraphicsContext gc) {
        int[] e = Data.LevelEnd[nowLevel - 1];
        Block block = new Block(gc, ColorType.get(0, 0, 0),
            end, e[0], e[1]);
        block.setHeight(end.getHeight() / 12);
        block.setWidth(end.getWidth() / 12);
        ends.add(block);
        return ends;
    }

    public List<Key> getKeys(GraphicsContext gc) {
        for (int i = 0; i < Data.LevelKey[nowLevel - 1].length; i++) {
            int[] b = Data.LevelKey[nowLevel - 1][i];
            System.out.println("b = " + b);
            for (Line line : lines) {
                if ((line.getX() + UNIT_SIZE8 - UNIT_SIZE2) / UNIT_SIZE == b[5] & (line.getY() - UNIT_SIZE2) / UNIT_SIZE == b[6]) {
                    System.out.println("line = " + line);
                    Key key = new Key(gc, ColorType.get(b[2], b[3], b[4]),
                        new Image("key/" + ColorType.get(b[2], b[3], b[4]).toString() + "4.png"),
                        b[0], b[1], line);
                    key.setHeight(key.getHeight()/10);
                    key.setWidth(key.getWidth()/10);
                    keys.add(key);
                }
            }
        }
        return keys;
    }
}
