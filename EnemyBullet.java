import java.awt.*;

public class EnemyBullet {
    int x, y;
    int speed = 8;  // 敌人子弹速度比玩家子弹慢
    int width = 5, height = 10;
    boolean active = true;

    public EnemyBullet(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void update() {
        y += speed;  // 敌人子弹向下移动
        if (y > 800)  // 超出屏幕底部时消失
            active = false;
    }

    public void draw(Graphics g) {
        if (active) {
            g.setColor(Color.BLUE);  // 蓝色子弹
            g.fillRect(x, y, width, height);
        }
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
} 