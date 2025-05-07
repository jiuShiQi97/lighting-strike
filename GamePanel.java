// Description: 游戏面板类，负责游戏的主要逻辑和渲染
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class GamePanel extends JPanel implements ActionListener, KeyListener {
    private javax.swing.Timer timer;
    private Player player;
    private Set<Integer> keysPressed;
    private java.util.List<Bullet> bullets;
    private java.util.List<Enemy> enemies;
    private java.util.List<EnemyBullet> enemyBullets;  // 添加敌人子弹列表
    private Random rand;
    private int enemySpawnTimer = 0;
    private boolean gameOver = false;  // 添加游戏结束状态
    private AudioManager audioManager;  // 添加音频管理器
    private int score = 0;  // 添加分数变量
    private int shootCooldown = 0;  // 添加射击冷却时间
    private final int SHOOT_DELAY = 10;  // 射击间隔（帧数）

    public GamePanel() {
        setPreferredSize(new Dimension(480, 800));
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(this);

        player = new Player(200, 700);
        keysPressed = new HashSet<>();
        bullets = new ArrayList<>();
        enemies = new ArrayList<>();
        enemyBullets = new ArrayList<>();  // 初始化敌人子弹列表
        rand = new Random();
        audioManager = new AudioManager();  // 初始化音频管理器
        score = 0;  // 初始化分数

        // 播放背景音乐
        audioManager.playBGM("resources/bgm.wav");

        timer = new javax.swing.Timer(16, this);
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (gameOver) {
            audioManager.stopBGM();  // 游戏结束时停止音乐
            return;
        }

        // 玩家移动控制
        if (keysPressed.contains(KeyEvent.VK_LEFT))
            player.move(-5, 0);
        if (keysPressed.contains(KeyEvent.VK_RIGHT))
            player.move(5, 0);
        if (keysPressed.contains(KeyEvent.VK_UP))
            player.move(0, -5);
        if (keysPressed.contains(KeyEvent.VK_DOWN))
            player.move(0, 5);

        // 子弹发射（Z键）带冷却时间
        if (keysPressed.contains(KeyEvent.VK_Z) && shootCooldown <= 0) {
            bullets.add(new Bullet(player.getX() + 25, player.getY()));
            shootCooldown = SHOOT_DELAY;  // 设置冷却时间
        }
        
        // 更新射击冷却时间
        if (shootCooldown > 0) {
            shootCooldown--;
        }

        // 更新子弹
        for (Bullet b : bullets)
            b.update();
        bullets.removeIf(b -> !b.active);

        // 每60帧生成敌人
        if (++enemySpawnTimer >= 60) {
            enemies.add(new Enemy(rand.nextInt(440), -40));
            enemySpawnTimer = 0;
        }

        // 更新敌人和敌人子弹
        for (Enemy enemy : enemies) {
            enemy.update();
            // 检查敌人是否可以发射子弹
            if (enemy.canShoot()) {
                enemyBullets.add(new EnemyBullet(enemy.getX() + enemy.width/2, enemy.getY() + enemy.height));
            }
        }
        enemies.removeIf(enemy -> !enemy.active);

        // 更新敌人子弹
        for (EnemyBullet b : enemyBullets)
            b.update();
        enemyBullets.removeIf(b -> !b.active);

        // 碰撞检测：子弹 vs 敌人
        for (Bullet b : bullets) {
            for (Enemy enemy : enemies) {
                if (b.getBounds().intersects(enemy.getBounds())) {
                    b.active = false;
                    enemy.active = false;
                    score++;  // 击杀敌人增加分数
                }
            }
        }

        // 碰撞检测：玩家 vs 敌人
        for (Enemy enemy : enemies) {
            if (enemy.active && player.getBounds().intersects(enemy.getBounds())) {
                player.die();  // 碰到敌人直接死亡
                enemy.active = false;
                gameOver = true;
                timer.stop();
                break;
            }
        }

        // 碰撞检测：玩家 vs 敌人子弹
        for (EnemyBullet b : enemyBullets) {
            if (b.active && player.getBounds().intersects(b.getBounds())) {
                player.takeDamage();  // 被子弹击中减少生命值
                b.active = false;
                if (!player.isAlive()) {  // 如果生命值为0，游戏结束
                    gameOver = true;
                    timer.stop();
                }
                break;
            }
        }

        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        player.draw(g);
        for (Bullet b : bullets)
            b.draw(g);
        for (Enemy e : enemies)
            e.draw(g);
        for (EnemyBullet b : enemyBullets)  // 绘制敌人子弹
            b.draw(g);

        // 绘制当前分数
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Score: " + score, 10, 40);  // 在生命条下方显示分数

        // 如果游戏结束，立即显示游戏结束文字
        if (gameOver) {
            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, 48));
            String gameOverText = "Game Over";
            FontMetrics metrics = g.getFontMetrics();
            int x = (getWidth() - metrics.stringWidth(gameOverText)) / 2;
            int y = getHeight() / 2;
            g.drawString(gameOverText, x, y);

            // 显示最终分数
            g.setFont(new Font("Arial", Font.BOLD, 36));
            String scoreText = "Final Score: " + score;
            metrics = g.getFontMetrics();
            x = (getWidth() - metrics.stringWidth(scoreText)) / 2;
            y = getHeight() / 2 + 50;  // 在Game Over文字下方显示分数
            g.drawString(scoreText, x, y);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        keysPressed.add(e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keysPressed.remove(e.getKeyCode());
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }
}
