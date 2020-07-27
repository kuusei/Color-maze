package application.Game.Map;

import application.Game.Node.Block;
import application.Game.Node.Line;
import application.Game.Node.Player;
import javafx.scene.canvas.GraphicsContext;

import java.util.List;

import static application.Config.*;

public class RaceGame extends Level {
    int[][] map = {{0, 0, 0, 26},
        {0, 0, 48, 0},
        {48, 26, 48, 0},
        {48, 26, 0, 26},
        {8, 0, 8, 20},
        {16, 6, 16, 26},
        {24, 0, 24, 20},
        {32, 6, 32, 26},
        {40, 0, 40, 20}};

    public RaceGame(int level) {
        super(level);
    }

    @Override
    public Bean get(GraphicsContext gc) {
        CANVAS_WEIGHT = (int) (50 * UNIT_SIZE);
        CANVAS_HEIGHT = (int) (50 * UNIT_SIZE);

        getPlayers(gc);
        getLines(gc);
        getEnds(gc);

        //  返回地图类
        return new Bean(MAP, players, lines, blocks, portals, ends, keys);
    }

    @Override
    public List<Player> getPlayers(GraphicsContext gc) {
        // 玩家
        players.add(new Player(gc,
            ColorType.red,
            5, 5));
        players.add(new Player(gc,
            ColorType.yellow,
            5, 5));
        for (Player player : players) {
            player.setDirection(90);
        }
        return players;
    }

    @Override
    public List<Line> getLines(GraphicsContext gc) {
        // 激光
        for (int i = 0; i < map.length; i++) {
            int[] l = map[i];
            Line line = new Line(gc, ColorType.get(0,0,0),
                getLine(ColorType.white),
                l[0], l[1], l[2], l[3]);
            lines.add(line);
        }
        return lines;
    }

    @Override
    public List<Block> getEnds(GraphicsContext gc) {
        Block block = new Block(gc, ColorType.get(0, 0, 0),
            end, 44, 10);
        block.setHeight(end.getHeight() / 12);
        block.setWidth(end.getWidth() / 12);
        ends.add(block);
        return ends;
    }
}
