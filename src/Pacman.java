import java.awt.EventQueue;
import javax.swing.JFrame;

public class Pacman extends JFrame {

    public Pacman() throws InterruptedException {

        initUI();
    }

    private void initUI() throws InterruptedException {

      //  Board br = new Board();
       // br.play();
        add(new Board());


        setTitle("Pacman");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(380, 420);
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {

        EventQueue.invokeLater(() -> {

            Pacman ex = null;
            try {
                ex = new Pacman();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ex.setVisible(true);
        });
    }
}

//m = [
//        [Node(true,[(1,2),(2,2)]), Node(false,[(0,0),(1,2)])],
//        [2,2]
//        ]