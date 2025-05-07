import java.awt.*;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Player {
    private int x, y;
    private final int width = 60, height = 60;
    private Image playerImage;
    private Image explosionImage;
    private boolean isAlive = true;
    private boolean isExploding = false;
    private int health = 5;  // 初始生命值为5
    private final int MAX_HEALTH = 5;

    public Player(int x, int y) {
        this.x = x;
        this.y = y;
        try {
            playerImage = ImageIO.read(new File("resources/player.png"));
            playerImage = playerImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            
            // 加载爆炸图片
            explosionImage = ImageIO.read(new File("resources/explosion.png"));
            explosionImage = explosionImage.getScaledInstance(width * 2, height * 2, Image.SCALE_SMOOTH);
        } catch (IOException e) {
            System.out.println("can't load image: " + e.getMessage());
        }
    }

    public void move(int dx, int dy) {
        if (!isAlive || isExploding) return;  // 如果玩家已死亡或正在爆炸，不能移动
        x += dx;
        y += dy;
        x = Math.max(0, Math.min(x, 480 - width));
        y = Math.max(0, Math.min(y, 800 - height));
    }

    public void draw(Graphics g) {
        if (!isAlive && !isExploding) return;  // 如果玩家已死亡且爆炸效果结束，不绘制

        if (isExploding) {
            // 绘制爆炸图片
            if (explosionImage != null) {
                g.drawImage(explosionImage, 
                    x - width/2, y - height/2,  // 爆炸效果居中
                    null);
            }
        } else if (playerImage != null) {
            g.drawImage(playerImage, x, y, null);
        } else {
            g.setColor(Color.CYAN);
            g.fillRect(x, y, width, height);
        }

        // 绘制生命条
        drawHealthBar(g);
    }

    private void drawHealthBar(Graphics g) {
        int barWidth = 100;
        int barHeight = 10;
        int barX = 10;
        int barY = 10;
        
        // 绘制背景
        g.setColor(Color.GRAY);
        g.fillRect(barX, barY, barWidth, barHeight);
        
        // 绘制当前生命值
        g.setColor(Color.GREEN);
        int currentWidth = (int)((float)health / MAX_HEALTH * barWidth);
        g.fillRect(barX, barY, currentWidth, barHeight);
        
        // 绘制边框
        g.setColor(Color.WHITE);
        g.drawRect(barX, barY, barWidth, barHeight);
    }

    public void takeDamage() {
        if (!isAlive || isExploding) return;
        
        health--;
        if (health <= 0) {
            die();
        }
    }

    public void die() {
        if (!isExploding) {
            isExploding = true;
            isAlive = false;  // 立即设置死亡状态
        }
    }

    public boolean isAlive() {
        return isAlive;
    }

    public boolean isExploding() {
        return isExploding;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
}
