import java.awt.EventQueue;
import java.util.Scanner;
import javax.swing.JFrame;

public class Pacman extends JFrame {

    public static int inputNum;
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
        Scanner in = new Scanner(System.in);
        System.out.println("If you want to test DFS, write 1");
        System.out.println("If you want to test BFS, write 2");
        System.out.println("If you want to test AStar, write 3");
        System.out.println("If you want to test Greedy, write 4");
        inputNum = in.nextInt();

        in.close();
        if (inputNum == 1 || inputNum == 2 || inputNum == 3 || inputNum == 4) {
            Pacman ex = null;
            try {
                ex = new Pacman();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ex.setVisible(true);
            //  });
        }else{
            System.out.println("Incorrect input");
        }
    }
}
