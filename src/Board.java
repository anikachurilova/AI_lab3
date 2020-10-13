

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.*;


import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Board extends JPanel implements ActionListener {

    private Dimension d;
    private final Font smallFont = new Font("Helvetica", Font.BOLD, 14);

    private Image ii;
    private final Color dotColor = new Color(192, 192, 0);
    private Color mazeColor;

    private boolean inGame = false;
    private boolean dying = false;

    private List<Pair> pathGlDFS = new ArrayList<>();
    private List<Pair> pathGlBFS  = new ArrayList<>();
    private Integer step = 0;




    private final int BLOCK_SIZE = 24;
    private final int N_BLOCKS = 15;
    private final int SCREEN_SIZE = N_BLOCKS * BLOCK_SIZE;
    private final int PAC_ANIM_DELAY = 2;
    private final int PACMAN_ANIM_COUNT = 4;
    private final int PACMAN_SPEED = 6;

    private int pacAnimCount = PAC_ANIM_DELAY;
    private int pacAnimDir = 1;
    private int pacmanAnimPos = 0;
    private int pacsLeft, score;
    private int[] dx, dy;
    boolean firstTimeDFS =true;
    boolean firstTimeBFS = true;

    private Image pacman1, pacman2up, pacman2left, pacman2right, pacman2down;
    private Image pacman3up, pacman3down, pacman3left, pacman3right;
    private Image pacman4up, pacman4down, pacman4left, pacman4right;

    private int pacman_x, pacman_y, pacmand_x, pacmand_y;
    private int req_dx, req_dy, view_dx, view_dy;

    private final short levelData[] = {
            3, 10, 10, 10, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 6,
            5, 3, 2,6, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4,
            5, 1, 0, 4, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4,
            5, 1, 0, 12, 1, 0, 0, 8, 0, 0, 0, 0, 0, 0, 4,
            1, 2, 2, 2, 0, 0, 4, 7, 1, 0, 0, 0, 0, 0,4,
            1, 0, 0, 0, 0, 0, 4, 5, 1, 0, 0, 0, 0, 0, 4,
            9, 0, 0, 0, 8, 8, 12, 5, 9, 8, 8, 0, 0, 0, 4,
            7, 1, 0, 4, 11, 10, 10, 0, 10, 10, 14, 1, 0, 0, 4,
            13, 1, 0, 0, 2, 2, 6, 5, 19, 2, 2, 0, 0, 0, 4,
            3, 0, 0, 0, 0, 0, 4, 5, 1, 0, 0, 0, 0, 0, 4,
            1, 0, 0, 0, 0, 0, 4, 13, 1, 0, 0, 0, 0, 0, 4,
            1, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 4,
            1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4,
            1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4,
            9, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 12
    };


    private final Node [][] allPaths ={
            {new Node(false,new Pair(0,1),new Pair(1,0)), new Node(false, new Pair(0,0),new Pair(0,2)),new Node(false, new Pair(0,1),new Pair(0,3)),new Node(false, new Pair(0,2),new Pair(0,4)), new Node(false, new Pair(0,3),new Pair(0,5), new Pair(1,4)), new Node(false, new Pair(0,4),new Pair(0,6), new Pair(1,5)), new Node(false, new Pair(0,5),new Pair(0,7), new Pair(1,6)), new Node(false, new Pair(0,6),new Pair(0,8), new Pair(1,7)), new Node(false, new Pair(0,7),new Pair(0,9), new Pair(1,8)), new Node(false, new Pair(0,8),new Pair(0,10), new Pair(1,9)), new Node(false, new Pair(0,9),new Pair(0,11), new Pair(1,10)), new Node(false, new Pair(0,10),new Pair(0,12), new Pair(1,11)), new Node(false, new Pair(0,11),new Pair(0,13), new Pair(1,12)),new Node(false, new Pair(0,13),new Pair(0,14), new Pair(1,13)), new Node(false, new Pair(0,13),new Pair(1,14))},
            {new Node(false,new Pair(0,0),new Pair(2,0)), new Node(),new Node(),new Node(), new Node(false, new Pair(0,4),new Pair(1,5), new Pair(2,4)), new Node(false, new Pair(1,4),new Pair(0,5), new Pair(1,6), new Pair(2,5)), new Node(false, new Pair(1,5),new Pair(0,6), new Pair(1,7), new Pair(2,6)), new Node(false, new Pair(1,6),new Pair(0,7), new Pair(1,8),new Pair(2,7)), new Node(false, new Pair(1,7),new Pair(0,8), new Pair(1,9), new Pair(2,8)), new Node(false, new Pair(1,8),new Pair(0,9), new Pair(1,10), new Pair(2,9)), new Node(false, new Pair(1,9),new Pair(0,10), new Pair(1,11), new Pair(2,10)), new Node(false, new Pair(1,10),new Pair(0,11), new Pair(1,12),new Pair(2,11)), new Node(false, new Pair(1,11),new Pair(0,12), new Pair(1,13),new Pair(2,13)),new Node(false, new Pair(1,12),new Pair(0,13), new Pair(1,14),new Pair(2,13)), new Node(false, new Pair(1,13),new Pair(0,14), new Pair(2,14))},
            {new Node (false,new Pair(1,0),new Pair(3,0)), new Node(),new Node(), new Node(), new Node(false, new Pair(1,4),new Pair(2,5),new Pair(3,4)), new Node(false, new Pair(2,4), new Pair(1,5),new Pair(2,6),new Pair(3,5)),new Node(false, new Pair(2,5),new Pair(1,6),new Pair(2,7),new Pair(3,6)),new Node(false, new Pair(2,6),new Pair(1,7),new Pair(2,8),new Pair(3,7)), new Node(false, new Pair(2,7), new Pair(1,8), new Pair(2,9), new Pair(3,8)), new Node(false, new Pair(2,8), new Pair(1,9), new Pair(2,10), new Pair(3,9)), new Node(false, new Pair(2,9), new Pair(1,10), new Pair(2,11), new Pair(3,10)), new Node(false, new Pair(2,10), new Pair(1,11), new Pair(2,12), new Pair(3,11)), new Node(false, new Pair(2,11), new Pair(1,12), new Pair(2,13), new Pair(3,12)), new Node(false, new Pair(2,12), new Pair(1,13), new Pair(2,14), new Pair(3,13)), new Node(false, new Pair(2,13), new Pair(1,14), new Pair(3,14))},
            {new Node (false, new Pair(2,0), new Pair(4,0)), new Node(), new Node(), new Node(), new Node(false, new Pair(2,4), new Pair(3,5), new Pair(4,4)), new Node(false, new Pair(3,4), new Pair(2,5), new Pair(3,6), new Pair(4,5)), new Node(false, new Pair(3,5), new Pair(2,6), new Pair(3,7), new Pair(4,6)), new Node(false, new Pair(3,6), new Pair(2,7), new Pair(3,8)), new Node(false, new Pair(3,7), new Pair(2,8), new Pair(3,9), new Pair(4,8)), new Node(false, new Pair(3,8), new Pair(2,9), new Pair(3,10), new Pair(4,9)), new Node(false, new Pair(3,9), new Pair(2,10), new Pair(3,11), new Pair(4,10)), new Node(false, new Pair(3,10), new Pair(2,11), new Pair(3,12), new Pair(4,11)), new Node(false, new Pair(3,11), new Pair(2,12), new Pair(3,13), new Pair(4,12)), new Node(false, new Pair(3,12), new Pair(2,13), new Pair(3,14), new Pair(4,13)), new Node(false, new Pair(3,13), new Pair(2,14), new Pair(4,14))},


    {new Node(false, new Pair(3,0), new Pair(4,1), new Pair(5,0)), new Node(false, new Pair(4,0), new Pair(4,2), new Pair(5,1)), new Node(false, new Pair(4,1), new Pair(4,3), new Pair(5,2)), new Node(false, new Pair(4,2), new Pair(4,4), new Pair(5,3)), new Node(false, new Pair(4,3), new Pair(3,4), new Pair(4,5), new Pair(5,4)), new Node(false, new Pair(4,4), new Pair(3,5), new Pair(4,6), new Pair(5,5)), new Node(false, new Pair(4,5), new Pair(3,6), new Pair(5,6)), new Node(), new Node(false, new Pair(3,8), new Pair(4,9), new Pair(5,8)), new Node(false, new Pair(4,8), new Pair(3,9), new Pair(4,10), new Pair(5,9)), new Node(false, new Pair(4,9), new Pair(3,10), new Pair(4,11), new Pair(5,10)), new Node(false, new Pair(4,10), new Pair(3,11), new Pair(4,12), new Pair(5,11)), new Node(false,new Pair(4,11), new Pair(3,12), new Pair(4,13), new Pair(5,12)), new Node( false, new Pair(4,12), new Pair(3,13), new Pair(4,14), new Pair(5,13)), new Node(false, new Pair(4,13),new Pair(3,14), new Pair(5,14))},
    {new Node(false, new Pair(4,0), new Pair(5,1), new Pair(6,0)), new Node(false, new Pair(5,0),new Pair(4,1),new Pair(5,2), new Pair(6,1)),new Node(false, new Pair(5,1), new Pair(4,2), new Pair(5,3), new Pair(6,2)), new Node(false, new Pair(5,2), new Pair(4,3), new Pair(5,4), new Pair(6,3)), new Node(false, new Pair(5,3), new Pair(4,4), new Pair(5,5), new Pair(6,4)), new Node(false, new Pair(5,4), new Pair(4,5), new Pair(5,6), new Pair(6,5)), new Node(false, new Pair(5,5), new Pair(4,6), new Pair(6,6)), new Node(), new Node(false, new Pair(4,8), new Pair(5,9), new Pair(6,8)), new Node(false, new Pair(5,8), new Pair(4,9), new Pair(5,10), new Pair(6,9)), new Node(false, new Pair(5,9), new Pair(4,10), new Pair(5,11), new Pair(6,10)), new Node(false,new Pair(5,10),new Pair(4,11),new Pair(5,12), new Pair(6,11)), new Node(false, new Pair(5,11), new Pair(4,12), new Pair(5,13), new Pair(6,12)), new Node(false, new Pair(5,12), new Pair(4,13), new Pair(5,14)), new Node(false, new Pair(5,13), new Pair(4,14), new Pair(6,14))},
    {new Node(false,new Pair(5,0), new Pair(6,1)), new Node(false, new Pair(6,0), new Pair(5,1), new Pair(6,2), new Pair(7,1)), new Node(false, new Pair(6,1), new Pair(5,2), new Pair(6,3), new Pair(7,2)), new Node(false, new Pair(6,3), new Pair(5,3), new Pair(6,4), new Pair(7,3)), new Node(false, new Pair(6,3), new Pair(5,4), new Pair(6,5)), new Node(false, new Pair(6,4), new Pair(5,5), new Pair(6,6)), new Node(false, new Pair(6,5), new Pair(5,6)), new Node(), new Node(false, new Pair(5,8), new Pair(6,9)), new Node(false, new Pair(6,8), new Pair(5,9), new Pair(6,10)), new Node(false, new Pair(6,9), new Pair(5,10), new Pair(6,11)), new Node(false, new Pair(6,10), new Pair(5,11), new Pair(6,12), new Pair(7,11)), new Node(false, new Pair(6,11), new Pair(5,12), new Pair(7,12)), new Node(), new Node(false, new Pair(5,14), new Pair(7,14))},
    {new Node(), new Node(false,new Pair(6,1), new Pair(7,2), new Pair(8,1)), new Node(false, new Pair(7,1), new Pair(6,2), new Pair(7,3), new Pair(8,2)), new Node(false, new Pair(7,2), new Pair(6,3), new Pair(8,3)), new Node(),new Node(),new Node(),new Node(),new Node(),new Node(),new Node(),new Node(false,new Pair(6,11),new Pair(7,12),new Pair(8,11)), new Node(false, new Pair(7,11), new Pair(6,12), new Pair(8,12)), new Node(), new Node(false, new Pair(6,14), new Pair(8,14))},


    {new Node(), new Node(false,new Pair(7,1), new Pair(8,2), new Pair(9,1)), new Node(false, new Pair(8,1), new Pair(7,2), new Pair(8,3), new Pair(9,2)), new Node(false, new Pair(8,2), new Pair(7,3), new Pair(8,4), new Pair(9,3)), new Node(false, new Pair(8,3), new Pair(8,5), new Pair(9,4)), new Node(false, new Pair(8,4), new Pair(8,6), new Pair(9,5)), new Node(false, new Pair(8,5),new Pair(9,6)), new Node(), new Node(true, new Pair(8,9), new Pair(9,8)), new Node(false, new Pair(8,8), new Pair(8,10), new Pair(9,9)), new Node(false, new Pair(8,9), new Pair(8,11),new Pair(9,10)), new Node(false,new Pair(8,10), new Pair(7,11),new Pair(8,12), new Pair(9,11)), new Node(false, new Pair(8,11), new Pair(7,12), new Pair(9,12)), new Node(), new Node(false, new Pair(7,14), new Pair(9,14))},
    {new Node(), new Node(false, new Pair(8,1), new Pair(9,2), new Pair(10,1)), new Node(false, new Pair(9,1), new Pair(8,2), new Pair(9,3), new Pair(10,2)), new Node(false, new Pair(9,2), new Pair(8,3), new Pair(9,4), new Pair(10,3)), new Node(false, new Pair(9,3), new Pair(8,4), new Pair(9,5), new Pair(10,4)), new Node(false,new Pair(9,4), new Pair(8,5), new Pair(9,6), new Pair(10,5)), new Node(false, new Pair(9,5), new Pair(8,6), new Pair(10,6)), new Node(), new Node(false, new Pair(8,8),new Pair(9,9), new Pair(10,8)), new Node(false, new Pair(9,8), new Pair(8,9), new Pair(9,10),new Pair(10,9)), new Node(false, new Pair(9,9), new Pair(8,10), new Pair(9,11), new Pair(10,10)), new Node(false, new Pair(9,10), new Pair(8,11), new Pair(9,12), new Pair(10,11)), new Node(false, new Pair(9,11), new Pair(8,12), new Pair(10,12)), new Node(), new Node(false, new Pair(8,14), new Pair(10,14))},
    {new Node(), new Node(false, new Pair(9,1), new Pair(10,2), new Pair(11,1)), new Node(false, new Pair(10,1), new Pair(9,2), new Pair(10,3), new Pair(11,2)), new Node(false, new Pair(10,2),new Pair(9,3),new Pair(10,4), new Pair(11,3)), new Node(false, new Pair(10,3), new Pair(9,4), new Pair(10,5), new Pair(11,4)), new Node(false, new Pair(10,4), new Pair(9,5), new Pair(10,6), new Pair(11,5)), new Node(false, new Pair(10,5), new Pair(9,6), new Pair(11,6)), new Node(), new Node(false, new Pair(9,8), new Pair(10,9), new Pair(11,8)), new Node(false, new Pair(10,8), new Pair(9,9), new Pair(10,10), new Pair(11,9)), new Node(false,new Pair(10,9), new Pair(9,10), new Pair(10,11), new Pair(11,10)), new Node(false, new Pair(10,10),new Pair(9,11), new Pair(10,12), new Pair(11,11)), new Node(false, new Pair(10,11), new Pair(9,12), new Pair(11,12)), new Node(), new Node(false, new Pair(9,14), new Pair(11,14))},
    {new Node(), new Node(false, new Pair(10,1), new Pair(11,2), new Pair(12,1)), new Node(false, new Pair(11,1), new Pair(10,2), new Pair(11,3), new Pair(12,2)), new Node(false, new Pair(11,2), new Pair(10,3),new Pair(11,4),new Pair(12,3)), new Node(false, new Pair(11,3), new Pair(10,4), new Pair(11,5),new Pair(12,4)), new Node(false, new Pair(11,4),new Pair(10,5),new Pair(11,6),new Pair(12,5)), new Node(false,new Pair(11,5),new Pair(10,6),new Pair(11,7),new Pair(12,6)),new Node(false, new Pair(11,6),new Pair(11,8),new Pair(12,7)),new Node(false, new Pair(11,7),new Pair(10,8),new Pair(11,9),new Pair(12,8)),new Node(false,new Pair(11,8),new Pair(10,9),new Pair(11,10),new Pair(12,9)), new Node(false,new Pair(11,9),new Pair(10,10),new Pair(11,11),new Pair(12,10)), new Node(false, new Pair(11,10),new Pair(10,11),new Pair(11,12),new Pair(12,11)), new Node(false,new Pair(11,11),new Pair(10,12),new Pair(12,12)),new Node(), new Node(false, new Pair(10,14), new Pair(12,14))},

    {new Node(), new Node(false, new Pair(11,1),new Pair(12,2),new Pair(13,1)), new Node(false, new Pair(12,1),new Pair(11,2),new Pair(12,3),new Pair(13,2)), new Node(false, new Pair(12,2), new Pair(11,3),new Pair(12,4),new Pair(13,3)), new Node(false, new Pair(12,3),new Pair(11,4),new Pair(12,5),new Pair(13,4)), new Node(false,new Pair(12,4),new Pair(11,5),new Pair(12,6),new Pair(13,5)), new Node(false, new Pair(12,5), new Pair(11,6), new Pair(12,7), new Pair(13,6)), new Node(false, new Pair(12,6),new Pair(11,7),new Pair(12,8), new Pair(13,7)), new Node(false, new Pair(12,7),new Pair(11,8),new Pair(12,9),new Pair(13,8)), new Node(false, new Pair(12,8), new Pair(11,9), new Pair(12,10), new Pair(13,9)), new Node(false, new Pair(12,9),new Pair(11,10),new Pair(12,11),new Pair(13,10)), new Node(false,new Pair(12,10),new Pair(11,11),new Pair(12,12),new Pair(13,11)), new Node(false,new Pair(12,11),new Pair(12,11),new Pair(13,12)), new Node(), new Node(false, new Pair(11,14),new Pair(13,14))},
    {new Node(), new Node(false, new Pair(1,12),new Pair(13,2)), new Node(false, new Pair(13,1),new Pair(12,2),new Pair(13,3)), new Node(false, new Pair(13,2),new Pair(12,3),new Pair(13,4)), new Node(false, new Pair(13,3), new Pair(12,4), new Pair(13,5)), new Node(false, new Pair(13,4),new Pair(12,5), new Pair(13,6)), new Node(false, new Pair(13,5), new Pair(12,6), new Pair(13,7)), new Node(false, new Pair(13,6), new Pair(12,7),new Pair(13,8)), new Node(false,new Pair(13,7),new Pair(12,8),new Pair(13,9)),new Node(false, new Pair(13,8),new Pair(12,9),new Pair(13,10)), new Node(false, new Pair(13,9),new Pair(12,10),new Pair(13,11),new Pair(14,10)), new Node(false, new Pair(13,10),new Pair(12,11),new Pair(13,12),new Pair(14,11)), new Node(false, new Pair(13,11),new Pair(12,12),new Pair(13,13),new Pair(14,12)), new Node(false, new Pair(13,12),new Pair(13,14),new Pair(14,13)),new Node(false,new Pair(13,13),new Pair(13,14),new Pair(14,14))},
    {new Node(), new Node(),new Node(),new Node(),new Node(),new Node(),new Node(),new Node(),new Node(),new Node(),new Node(false,new Pair(13,10),new Pair(14,11)), new Node(false, new Pair(14,10),new Pair(13,11),new Pair(14,12)), new Node(false, new Pair(14,11),new Pair(13,12),new Pair(14,13)), new Node(false, new Pair(14,12),new Pair(13,13),new Pair(14,14)), new Node(false, new Pair(14,13),new Pair(13,14))}
};

    private short[] screenData;
    private Timer timer;

    public Board() {

        loadImages();
        initVariables();
        initBoard();
    }

    private void initBoard() {

        addKeyListener(new TAdapter());

        setFocusable(true);

        setBackground(Color.black);

    }

    private void initVariables() {

        screenData = new short[N_BLOCKS * N_BLOCKS];
        mazeColor = new Color(5, 100, 5);
        d = new Dimension(400, 400);

        dx = new int[4];
        dy = new int[4];

        timer = new Timer(40, this);
        timer.start();
    }

    @Override
    public void addNotify() {
        super.addNotify();

        initGame();
    }

    private void doAnim() {

        pacAnimCount--;

        if (pacAnimCount <= 0) {
            pacAnimCount = PAC_ANIM_DELAY;
            pacmanAnimPos = pacmanAnimPos + pacAnimDir;

            if (pacmanAnimPos == (PACMAN_ANIM_COUNT - 1) || pacmanAnimPos == 0) {
                pacAnimDir = -pacAnimDir;
            }
        }
    }


    private void showIntroScreen(Graphics2D g2d) {

        g2d.setColor(new Color(0, 32, 48));
        g2d.fillRect(50, SCREEN_SIZE / 2 - 30, SCREEN_SIZE - 100, 50);
        g2d.setColor(Color.white);
        g2d.drawRect(50, SCREEN_SIZE / 2 - 30, SCREEN_SIZE - 100, 50);

        String s = "Press s to start.";
        Font small = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics metr = this.getFontMetrics(small);

        g2d.setColor(Color.white);
        g2d.setFont(small);
        g2d.drawString(s, (SCREEN_SIZE - metr.stringWidth(s)) / 2, SCREEN_SIZE / 2);
    }


    private void drawPacman(Graphics2D g2d) {

        if (view_dx == -1) {
            drawPacnanLeft(g2d);
        } else if (view_dx == 1) {
            drawPacmanRight(g2d);
        } else if (view_dy == -1) {
            drawPacmanUp(g2d);
        } else {
            drawPacmanDown(g2d);
        }
    }

    private void movePac(List<Pair> path) throws InterruptedException {
        if(step <= path.size()-1){
            pacman_x = 24*path.get(step).getFirst();
            pacman_y = 24*path.get(step).getSecond();
            step++;
        }

    }

    private void movePacCoord(Pair coord) throws InterruptedException {

            pacman_x = 24*coord.getFirst();
            pacman_y = 24*coord.getSecond();
            step++;


    }

    private void drawPacmanUp(Graphics2D g2d) {

        switch (pacmanAnimPos) {
            case 1:
                g2d.drawImage(pacman2up, pacman_x + 1, pacman_y + 1, this);
                break;
            case 2:
                g2d.drawImage(pacman3up, pacman_x + 1, pacman_y + 1, this);
                break;
            case 3:
                g2d.drawImage(pacman4up, pacman_x + 1, pacman_y + 1, this);
                break;
            default:
                g2d.drawImage(pacman1, pacman_x + 1, pacman_y + 1, this);
                break;
        }
    }

    private void drawPacmanDown(Graphics2D g2d) {

        switch (pacmanAnimPos) {
            case 1:
                g2d.drawImage(pacman2down, pacman_x + 1, pacman_y + 1, this);
                break;
            case 2:
                g2d.drawImage(pacman3down, pacman_x + 1, pacman_y + 1, this);
                break;
            case 3:
                g2d.drawImage(pacman4down, pacman_x + 1, pacman_y + 1, this);
                break;
            default:
                g2d.drawImage(pacman1, pacman_x + 1, pacman_y + 1, this);
                break;
        }
    }

    private void drawPacnanLeft(Graphics2D g2d) {

        switch (pacmanAnimPos) {
            case 1:
                g2d.drawImage(pacman2left, pacman_x + 1, pacman_y + 1, this);
                break;
            case 2:
                g2d.drawImage(pacman3left, pacman_x + 1, pacman_y + 1, this);
                break;
            case 3:
                g2d.drawImage(pacman4left, pacman_x + 1, pacman_y + 1, this);
                break;
            default:
                g2d.drawImage(pacman1, pacman_x + 1, pacman_y + 1, this);
                break;
        }
    }

    private void drawPacmanRight(Graphics2D g2d) {

        switch (pacmanAnimPos) {
            case 1:
                g2d.drawImage(pacman2right, pacman_x + 1, pacman_y + 1, this);
                break;
            case 2:
                g2d.drawImage(pacman3right, pacman_x + 1, pacman_y + 1, this);
                break;
            case 3:
                g2d.drawImage(pacman4right, pacman_x + 1, pacman_y + 1, this);
                break;
            default:
                g2d.drawImage(pacman1, pacman_x + 1, pacman_y + 1, this);
                break;
        }
    }

    private void drawMaze(Graphics2D g2d) {

        short i = 0;
        int x, y;

        for (y = 0; y < SCREEN_SIZE; y += BLOCK_SIZE) {
            for (x = 0; x < SCREEN_SIZE; x += BLOCK_SIZE) {

                g2d.setColor(mazeColor);
                g2d.setStroke(new BasicStroke(2));

                if ((screenData[i] & 1) != 0) {
                    g2d.drawLine(x, y, x, y + BLOCK_SIZE - 1);
                }

                if ((screenData[i] & 2) != 0) {
                    g2d.drawLine(x, y, x + BLOCK_SIZE - 1, y);
                }

                if ((screenData[i] & 4) != 0) {
                    g2d.drawLine(x + BLOCK_SIZE - 1, y, x + BLOCK_SIZE - 1,
                            y + BLOCK_SIZE - 1);
                }

                if ((screenData[i] & 8) != 0) {
                    g2d.drawLine(x, y + BLOCK_SIZE - 1, x + BLOCK_SIZE - 1,
                            y + BLOCK_SIZE - 1);
                }

                if ((screenData[i] & 16) != 0) {
                    g2d.setColor(dotColor);
                    g2d.fillRect(x + 11, y + 11, 2, 2);
                }

                i++;
            }
        }
    }

    private void initGame() {

        pacsLeft = 3;
        score = 0;
        initLevel();
    }

    private void initLevel() {

        int i;
        for (i = 0; i < N_BLOCKS * N_BLOCKS; i++) {
            screenData[i] = levelData[i];
        }
    }


    private void loadImages() {

        pacman1 = new ImageIcon("src/images/pacman.png").getImage();
        pacman2up = new ImageIcon("src/images/up1.png").getImage();
        pacman3up = new ImageIcon("src/images/up2.png").getImage();
        pacman4up = new ImageIcon("src/images/up3.png").getImage();
        pacman2down = new ImageIcon("src/images/down1.png").getImage();
        pacman3down = new ImageIcon("src/images/down2.png").getImage();
        pacman4down = new ImageIcon("src/images/down3.png").getImage();
        pacman2left = new ImageIcon("src/images/left1.png").getImage();
        pacman3left = new ImageIcon("src/images/left2.png").getImage();
        pacman4left = new ImageIcon("src/images/left3.png").getImage();
        pacman2right = new ImageIcon("src/images/right1.png").getImage();
        pacman3right = new ImageIcon("src/images/right2.png").getImage();
        pacman4right = new ImageIcon("src/images/right3.png").getImage();

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        try {
            doDrawing(g);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void doDrawing(Graphics g) throws InterruptedException {

        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(Color.black);
        g2d.fillRect(0, 0, d.width, d.height);

        drawMaze(g2d);

        doAnim();

        if (inGame) {

            drawPacman(g2d);
            if(firstTimeDFS){
                System.out.println("****************DFS****************");
                DFSSearch dfs = new DFSSearch();
                pathGlDFS= dfs.DFS(allPaths);
                System.out.println("Answer path: " + pathGlDFS);
                dfs.showStatistics();
                firstTimeDFS = false;
            }
            if(firstTimeBFS){
                System.out.println("****************BFS****************");
                BFSSearch bfs = new BFSSearch();
                pathGlBFS= bfs.BFS(allPaths);
                System.out.println("Answer path: " + pathGlBFS);
                bfs.showStatistics();
                firstTimeBFS = false;
            }

        } else {
            showIntroScreen(g2d);
        }

        g2d.drawImage(ii, 5, 5, this);
        Toolkit.getDefaultToolkit().sync();
        g2d.dispose();
    }

    class TAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {

            int key = e.getKeyCode();

            if (inGame) {
                if (key == KeyEvent.VK_SPACE) {
                    try {
                        movePac(pathGlDFS);
                    } catch (InterruptedException interruptedException) {
                        interruptedException.printStackTrace();
                    }
                } else if (key == KeyEvent.VK_ESCAPE && timer.isRunning()) {
                    inGame = false;
                } else if (key == KeyEvent.VK_PAUSE) {
                    if (timer.isRunning()) {
                        timer.stop();
                    } else {
                        timer.start();
                    }
                }
            } else {
                if (key == 's' || key == 'S') {
                    inGame = true;
                    initGame();
                }
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {

            int key = e.getKeyCode();

            if (key == Event.LEFT || key == Event.RIGHT
                    || key == Event.UP || key == Event.DOWN) {
                req_dx = 0;
                req_dy = 0;
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        repaint();
    }
}
