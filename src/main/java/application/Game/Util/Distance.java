package application.Game.Util;

import application.Game.Node.*;

public class Distance {
    // 矩形(中心点)到线的最短距离
    public static double point2Line(Player P, Line L) {
        return point2Line(L, P.getX(false), P.getY(false)) - P.getHeight() / 2;
    }

    // 点到直线距离
    public static double point2Line(Line L, double x0, double y0) {
        return point2Line(L.getX(), L.getY(), L.getWidth(), L.getHeight(), x0, y0);
    }

    // 点到直线距离(原型,内部使用)
    private static double point2Line(double x1, double y1, double x2, double y2, double x0, double y0) {
        double space = 0;
        double a, b, c;
        a = lineSpace(x1, y1, x2, y2);// 线段的长度
        b = lineSpace(x1, y1, x0, y0);// (x1,y1)到点的距离
        c = lineSpace(x2, y2, x0, y0);// (x2,y2)到点的距离
        if (c <= 0.000001 || b <= 0.000001) {
            space = 0;
            return space;
        }
        if (a <= 0.000001) {
            space = b;
            return space;
        }
        if (c * c >= a * a + b * b) {
            space = b;
            return space;
        }
        if (b * b >= a * a + c * c) {
            space = c;
            return space;
        }
        double p = (a + b + c) / 2;// 半周长
        double s = Math.sqrt(p * (p - a) * (p - b) * (p - c));// 海伦公式求面积
        space = 2 * s / a;// 返回点到线的距离（利用三角形面积公式求高）
        return space;
    }

    // 线长计算(原型,内部使用)
    private static double lineSpace(double x1, double y1, double x2, double y2) {
        return Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
    }
}
