import java.awt.*;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class Enemy {
    int x, y;
    int speed = 2;
    int width = 40, height = 40;
    boolean active = true;
    private Image enemyImage;
    private Random rand;
    private int shootTimer = 0;
    private final int SHOOT_INTERVAL = 60;  // 从120帧改为60帧，发射频率提高一倍
    private int moveDirection = 1;  // 1表示向右，-1表示向左


    public Enemy(int x, int y) {
        this.x = x;
        this.y = y;
        rand = new Random();
        // 随机初始方向
        moveDirection = rand.nextBoolean() ? 1 : -1;
        try {
            // 加载敌人图片
            enemyImage = ImageIO.read(new File("resources/enemy.png"));
            // 调整图片大小
            enemyImage = enemyImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        } catch (IOException e) {
            System.out.println("无法加载敌人图片: " + e.getMessage());
        }
    }

    public void update() {
        // 垂直移动
        y += speed;
        if (y > 800)
            active = false;
            
        // 水平移动
        x += moveDirection * 2;  // 水平移动速度
        
        // 碰到边界时改变方向
        if (x <= 0 || x >= 480 - width) {
            moveDirection *= -1;
        }
            
        // 更新射击计时器
        shootTimer++;
    }

    public boolean canShoot() {
        if (shootTimer >= SHOOT_INTERVAL) {
            shootTimer = 0;
            return true;
        }
        return false;
    }

    public void draw(Graphics g) {
        if (active) {
            if (enemyImage != null) {
                g.drawImage(enemyImage, x, y, null);
            } else {
                // 如果图片加载失败，显示默认的矩形
                g.setColor(Color.RED);
                g.fillRect(x, y, width, height);
            }
        }
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
