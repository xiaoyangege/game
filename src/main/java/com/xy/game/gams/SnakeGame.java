package com.xy.game.gams;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

/**
 * @author dxy
 */
public class SnakeGame extends JFrame implements KeyListener {
    // 窗口大小
    private final int WIDTH = 500;
    private final int HEIGHT = 500;

    // 蛇的属性
    private ArrayList<Point> snake = new ArrayList<>();
    private int direction = KeyEvent.VK_RIGHT;
    private boolean isGameOver = false;

    // 食物的属性
    private Point food = new Point(0, 0);

    public SnakeGame() {
        // 设置窗口大小
        setSize(WIDTH, HEIGHT);

        // 添加键盘监听器
        addKeyListener(this);

        // 初始化蛇和食物位置
        initGame();

        // 显示窗口
        setVisible(true);

        // 关闭窗口时退出程序
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    // 初始化游戏
    private void initGame() {
        // 初始化蛇的位置和长度
        snake.clear();
        snake.add(new Point(0, 0));
        snake.add(new Point(10, 0));
        snake.add(new Point(20, 0));

        // 初始化食物位置
        food = new Point(getRandomX(), getRandomY());

        // 初始化游戏结束标志
        isGameOver = false;
    }

    // 随机生成食物的x坐标
    private int getRandomX() {
        return (int) (Math.random() * (WIDTH - 10) / 10) * 10;
    }

    // 随机生成食物的y坐标
    private int getRandomY() {
        return (int) (Math.random() * (HEIGHT - 10) / 10) * 10;
    }

    // 画出游戏界面
    @Override
    public void paint(Graphics g) {
        // 清空界面
        g.clearRect(0, 0, WIDTH, HEIGHT);

        // 画出食物
        g.setColor(Color.RED);
        g.fillRect(food.x, food.y, 10, 10);

        // 画出蛇
        g.setColor(Color.BLACK);
        for (Point p : snake) {
            g.fillRect(p.x, p.y, 10, 10);
        }

        // 如果游戏结束，显示游戏结束提示
        if (isGameOver) {
            g.setColor(Color.RED);
            g.drawString("游戏结束！", WIDTH / 2 - 30, HEIGHT / 2);
        }
    }

    // 蛇移动一格
    private void move() {
        // 计算新头部的位置
        int x = snake.get(0).x;
        int y = snake.get(0).y;
        switch (direction) {
            case KeyEvent.VK_UP:
                y -= 10;
                break;
            case KeyEvent.VK_DOWN:
                y += 10;
                break;
            case KeyEvent.VK_LEFT:
                x -= 10;
                break;
            case KeyEvent.VK_RIGHT:
                x += 10;
                break;
            default:
                break;
        }

        // 判断是否吃到食物
        if (food.x == x && food.y == y) {
            snake.add(0, new Point(x, y));
            food = new Point(getRandomX(), getRandomY());
        } else {
            // 没有吃到食物，移动蛇
            for (int i = snake.size() - 1; i > 0; i--) {
                snake.set(i, snake.get(i - 1));
            }
            snake.set(0, new Point(x, y));
        }

        // 判断是否撞墙或撞到自己
        if (x < 0 || x >= WIDTH || y < 0 || y >= HEIGHT) {
            isGameOver = true;
        }
        for (int i = 1; i < snake.size(); i++) {
            if (x == snake.get(i).x && y == snake.get(i).y) {
                isGameOver = true;
            }
        }

        // 刷新界面
        repaint();
    }

    // 处理按键事件
    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_RIGHT:
                if (isGameOver) {
                    initGame();
                } else {
                    direction = e.getKeyCode();
                    move();
                }
                break;
            default:
                break;
        }
    }

    // 必须实现的方法
    @Override
    public void keyReleased(KeyEvent e) {
    }

    // 必须实现的方法
    @Override
    public void keyTyped(KeyEvent e) {
    }

    public static void main(String[] args) {
        new SnakeGame();
    }
}

