package application.Game.Map;

import application.Game.Node.Block;
import application.Game.Node.Line;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.List;
import java.util.Random;

import static application.Config.*;

public class MiniGame extends Level {
    int[][] map = {{9+15,10,9+15,16},
        {11+15,10,13+15,10},
        {11+15,13,13+15,13},
        {11+15,16,13+15,16},
        {13+15,10,13+15,16}};

    public MiniGame(int level) {
        super(level);
    }

    @Override
    public Bean get(GraphicsContext gc) {
        CANVAS_WEIGHT = (int) (50 * UNIT_SIZE);
        CANVAS_HEIGHT = (int) (50 * UNIT_SIZE);

        getPlayers(gc);
        getLines(gc);
        getBlocks(gc);
        getEnds(gc);

        // 返回地图类
        return new Bean(MAP, players, lines, blocks, portals, ends, keys);
    }

    @Override
    public List<Block> getBlocks(GraphicsContext gc) {

        // 颜色块
        for (int i = 0; i < 60; i++) {
            Block block = new Block(gc, ColorType.black,
                new Image("stone/stone (" + (new Random().nextInt(19) + 1) + ").png"),
                new Random().nextInt(100) - 25, new Random().nextInt(100) - 25);
            blocks.add(block);
        }
        return blocks;
    }

    @Override
    public List<Line> getLines(GraphicsContext gc) {
        // 激光
        for (int i = 0; i < map.length; i++) {
            int[] l = map[i];
            Line line = new Line(gc, ColorType.get(0, 0, 0),
                getLine(ColorType.white),
                l[0], l[1], l[2], l[3]);
            lines.add(line);
        }
        return lines;
    }
}
