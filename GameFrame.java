import javax.swing.JFrame;

public class GameFrame extends JFrame {
    public GameFrame() {
        setTitle("Lightning Strike");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        add(new GamePanel());
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        new GameFrame();
    }
}
