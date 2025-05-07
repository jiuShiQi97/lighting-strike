import java.awt.*;

public class Bullet {
    int x, y;
    int speed = 10;
    int width = 5, height = 10;
    boolean active = true;

    public Bullet(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void update() {
        y -= speed;
        if (y < 0)
            active = false;
    }

    public void draw(Graphics g) {
        if (active) {
            g.setColor(Color.YELLOW);
            g.fillRect(x, y, width, height);
        }
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
}
