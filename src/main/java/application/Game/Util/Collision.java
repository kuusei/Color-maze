package application.Game.Util;

import application.Game.Node.Block;
import application.Game.Node.Line;
import application.Game.Node.Player;

import static application.Config.Collision;
import static application.Game.Util.Distance.point2Line;

public class Collision {

    // 块（以高作为圆半径）碰撞判断
    public static <T> boolean IsCollision(Player P, Block block) {
        return IsCollision(
            P.getX(false), P.getY(false), P.getHeight() / 2,
            block.getX(false), block.getY(false), block.getHeight() / 2
        );
    }

    private static boolean IsCollision(double x1, double y1, double r1, double x2, double y2, double r2) {
        return !(Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2)) > r1 + r2);
    }

    // 点到线段碰撞判断
    public static boolean IsCollision(Player P, Line L, double x, double y) {
        return IsCollision(L, P.getX(false) + x, P.getY(false) + y, P.getHeight() / 2);
    }

    // 圆到直线的碰撞判断
    public static boolean IsCollision(Line L, double x0, double y0, double r0) {
        return (point2Line(L, x0, y0) - r0) <= Collision;
    }
}