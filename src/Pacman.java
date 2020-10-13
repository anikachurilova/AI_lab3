import java.awt.EventQueue;
import javax.swing.JFrame;

public class Pacman extends JFrame {

    public Pacman() throws InterruptedException {

        initUI();
    }

    private void initUI() throws InterruptedException {

        add(new Board());


        setTitle("Pacman");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(380, 420);
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {

     //   EventQueue.invokeLater(() -> {

            Pacman ex = null;
            try {
                ex = new Pacman();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ex.setVisible(true);
      //  });
    }
}
