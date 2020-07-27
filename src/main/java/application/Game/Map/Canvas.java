package application.Game.Map;

import application.Game.Node.Block;
import application.Game.Node.Line;
import javafx.application.Platform;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import static application.Config.*;

public class Canvas extends javafx.scene.canvas.Canvas {
    // 游戏地图
    private Bean map;
    private Level level;
    private boolean isRunning = true;

    // 主线程
    private Thread thread = new Thread(new Runnable() {

        @Override
        public void run() {
            while (isRunning) {
                Platform.runLater(new Runnable() {

                    @Override
                    public void run() {
                        draw();
                    }
                });
                try {
                    Thread.sleep(SLEEP * 2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("isRunning = " + isRunning);
        }
    });

    public Canvas(double width, double height, int l) {
        super(width, height);
        // 初始化游戏地图
        switch (gameMode) {
            case main:
                level = new Level(l);
                break;
            case mini:
                level = new MiniGame(l);
                break;
            case race:
                level = new RaceGame(l);
                break;
        }
        map = level.get(getGraphicsContext2D());
        thread.start();
    }

    public void nextMap() {
        map = level.get(getGraphicsContext2D());
    }

    public void draw() {
        // 计算行列单元格数目
        int[][] mapIndex = new int[CANVAS_HEIGHT / MAP_SIZE + 1][CANVAS_WEIGHT / MAP_SIZE + 1];
        int cols = (int) (map.getMap().getWidth() / MAP_SIZE);
        int mapWidth = mapIndex[0].length;
        int mapHeight = mapIndex.length;
        for (int y = 0; y < mapHeight; y++) {
            for (int x = 0; x < mapWidth; x++) {
                // 计算当前坐标在地图上的样式
                int px = 0 % cols;
                int py = 0 / cols;
                // 画图函数, map为原图(对象)
                // sx,sy,sw,sh表示相对于源图片的x,y坐标和截取的宽度和高度。
                // dx,dy,dw,dy表示绘制到画布上的x, y坐标和绘制的宽度和高度。
                getGraphicsContext2D().drawImage(map.getMap(),
                    px * MAP_SIZE,
                    py * MAP_SIZE,
                    MAP_SIZE,
                    MAP_SIZE,
                    x * MAP_SIZE,
                    y * MAP_SIZE,
                    MAP_SIZE,
                    MAP_SIZE);
            }
        }

        // 获取实体类的所有属性，返回Field数组
        Field[] field = map.getClass().getDeclaredFields();
        // 遍历所有属性
        for (Field item : field) {
            // 获取属性的名字
            String name = item.getName();
            // 将属性的首字符大写，方便构造get，set方法
            name = name.substring(0, 1).toUpperCase() + name.substring(1);
            // 获取属性的类型
            String type = item.getGenericType().toString();


            // 如果type是类类型，则前面包含"class "，后面跟类名
            if (!type.equals("class javafx.scene.image.Image")) {
                // 声明方法变量
                Method method = null;
                try {
                    // 尝试利用拼接字符串获得getter方法
                    method = map.getClass().getMethod("get" + name);

                    // 调用getter方法获取属性值
                    List value = (List) method.invoke(map);
                    // 防止空值
                    if (value.size() != 0) {
                        // 如果是Line类的话
                        if (value.get(0).getClass() == Line.class) {
                            for (Object node : value) {
                                // 判断是否可见
                                if (((Line) node).isVisible()) {
                                    // 绘图函数
                                    ((Line) node).draw();
                                }
                            }
                        } else {
                            for (Object node : value) {
                                ((Block) node).draw();
                            }
                        }
                    }
                } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public Bean getMap() {
        return map;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void setRunning(boolean running) {
        isRunning = running;
    }
}