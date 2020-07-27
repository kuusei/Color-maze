package application.Game.Map;

import application.Game.Node.*;
import javafx.scene.image.Image;

import java.util.List;

/**
 * @Bean
 **/
public class Bean {
    private Image map;
    private List<Player> players;
    private List<Line> lines;
    private List<Block> blocks;
    private List<Portal> portals;
    private List<Block> ends;
    private List<Key> keys;

    public Bean(Image map, List<Player> players, List<Line> lines, List<Block> blocks, List<Portal> portals, List<Block> ends, List<Key> keys) {
        this.map = map;
        this.players = players;
        this.lines = lines;
        this.blocks = blocks;
        this.portals = portals;
        this.ends = ends;
        this.keys = keys;
    }

    public Image getMap() {
        return map;
    }

    public void setMap(Image map) {
        this.map = map;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public List<Line> getLines() {
        return lines;
    }

    public void setLines(List<Line> lines) {
        this.lines = lines;
    }

    public List<Block> getBlocks() {
        return blocks;
    }

    public void setBlocks(List<Block> blocks) {
        this.blocks = blocks;
    }

    public List<Portal> getPortals() {
        return portals;
    }

    public void setPortals(List<Portal> portals) {
        this.portals = portals;
    }

    public List<Block> getEnds() {
        return ends;
    }

    public void setEnds(List<Block> ends) {
        this.ends = ends;
    }

    public List<Key> getKeys() {
        return keys;
    }

    public void setKeys(List<Key> keys) {
        this.keys = keys;
    }
}
