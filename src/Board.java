

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

    private List<Integer> pathGlDFS = new ArrayList<>();
    private List<Integer> pathGlDijkstra = new ArrayList<>();
    private List<Integer> pathGlBFS  = new ArrayList<>();
    private List<Integer> pathGlAStar  = new ArrayList<>();
    private Integer step = 0;
    private static Graph graph;
    private static Double[]h = new Double[131];

    public static void initAll(){
        initGraph();
        initH(96);
    }

    static void initH(int pointVertex) {
        int x = vertexCoord[pointVertex].getFirst();
        int y = vertexCoord[pointVertex].getSecond();
        for (int i = 0; i < 131; i++) {
            if (i == pointVertex) {
                h[i] = -1.0;
            } else {
                int xCurr = vertexCoord[i].getFirst();
                int yCurr = vertexCoord[i].getSecond();
                h[i] = (double) Math.abs((xCurr - x)) + (double) Math.abs((yCurr - y)) + (double) Math.max((double) Math.abs((xCurr - x)), (double) Math.abs((yCurr - y)));
            }

        }
    }

    public List<Pair> convertToVertex(List<Integer> res){
        List<Pair> allPairs = new ArrayList<>();
        for(int i=0; i<vertexCoord.length;i++){
            allPairs.add(vertexCoord[i]);
        }
        List<Pair> resPair= new ArrayList<>();
        for(int i=0; i<res.size();i++){
            resPair.add(allPairs.get(res.get(i)));
        }
        return resPair;
    }
    private static Pair[] vertexCoord = {
            new Pair(0,0), new Pair(1,0),new Pair(2,0), new Pair(3,0),new Pair(4,0), new Pair(5,0),
            new Pair(6,0), new Pair(8,0),new Pair(9,0), new Pair(10,0),new Pair(11,0), new Pair(12,0),
            new Pair(13,0), new Pair(14,0),new Pair(3,1), new Pair(6,1),new Pair(8,1), new Pair(11,1),
            new Pair(0,2), new Pair(3,2),new Pair(4,2), new Pair(5,2),new Pair(6,2), new Pair(7,2),
            new Pair(8,2), new Pair(9,2),new Pair(10,2), new Pair(11,2),new Pair(14,2), new Pair(0,3),
            new Pair(1,3), new Pair(2,3),new Pair(3,3), new Pair(5,3),new Pair(9,3), new Pair(11,3),
            new Pair(12,3), new Pair(13,3),new Pair(14,3), new Pair(0,4),new Pair(3,4), new Pair(5,4),
            new Pair(6,4), new Pair(8,4),new Pair(9,4), new Pair(11,4),new Pair(14,4), new Pair(0,5),
            new Pair(1,5), new Pair(2,5),new Pair(3,5), new Pair(6,5),new Pair(8,5), new Pair(11,5),
            new Pair(12,5), new Pair(13,5),new Pair(14,5), new Pair(0,6),new Pair(3,6), new Pair(5,6),
            new Pair(6,6), new Pair(7,6),new Pair(8,6), new Pair(9,6),new Pair(11,6), new Pair(14,6),
            new Pair(0,7), new Pair(1,7),new Pair(3,7), new Pair(5,7),new Pair(7,7), new Pair(9,7),
            new Pair(11,7), new Pair(13,7),new Pair(14,7), new Pair(1,8),new Pair(3,8), new Pair(4,8),
            new Pair(5,8), new Pair(9,8),new Pair(10,8), new Pair(11,8),new Pair(13,8), new Pair(0,9),
            new Pair(1,9), new Pair(2,9),new Pair(3,9), new Pair(5,9),new Pair(9,9), new Pair(11,9),
            new Pair(12,9), new Pair(13,9),new Pair(14,9), new Pair(3,10),new Pair(5,10), new Pair(6,10),
            new Pair(7,10), new Pair(8,10),new Pair(9,10), new Pair(11,10),new Pair(1,11), new Pair(2,11),
            new Pair(3,11),new Pair(5,11), new Pair(9,11),new Pair(11,11),new Pair(12,11), new Pair(13,11),
            new Pair(1,12),new Pair(3,12), new Pair(4,12),new Pair(5,12),new Pair(6,12), new Pair(8,12),
            new Pair(9,12),new Pair(10,12), new Pair(11,12),new Pair(13,12),new Pair(1,13), new Pair(2,13),
            new Pair(3,13),new Pair(6,13), new Pair(8,13),new Pair(11,13),new Pair(12,13), new Pair(13,13),
            new Pair(2,14),new Pair(6,14), new Pair(7,14),new Pair(8,14),new Pair(12,14)
    };



    static void initGraph(){
        graph = new Graph(131);
        int vertices = 130;
        graph.addEgde(0, 1, 1);graph.addEgde(1, 2, 1);graph.addEgde(2, 3, 1);
        graph.addEgde(3, 4, 1);graph.addEgde(3, 14, 1);graph.addEgde(4, 5, 1);
        graph.addEgde(5, 6, 1);graph.addEgde(6, 15, 1);graph.addEgde(15, 22, 1);
        graph.addEgde(16, 7, 1);graph.addEgde(7, 8, 1);graph.addEgde(8, 9, 1);
        graph.addEgde(9, 10, 1);graph.addEgde(10, 17, 1);graph.addEgde(10, 11, 1);
        graph.addEgde(11, 12, 1);graph.addEgde(12, 13, 1);graph.addEgde(14, 19, 1);
        graph.addEgde(19, 20, 1);graph.addEgde(20, 21, 1);graph.addEgde(21, 22, 1);
        graph.addEgde(22, 23, 1);graph.addEgde(23, 24, 1);graph.addEgde(24, 16, 1);
        graph.addEgde(24, 25, 1);graph.addEgde(25, 26, 1);graph.addEgde(26, 27, 1);
        graph.addEgde(17, 27, 1);graph.addEgde(29, 18, 1);graph.addEgde(30, 29, 1);
        graph.addEgde(31, 30, 1);graph.addEgde(32, 31, 1);graph.addEgde(19, 32, 1);
        graph.addEgde(21, 33, 1);graph.addEgde(25, 34, 1);graph.addEgde(27, 35, 1);
        graph.addEgde(35, 36, 1);graph.addEgde(36, 37, 1);graph.addEgde(37, 38, 1);
        graph.addEgde(38, 28, 1);graph.addEgde(29, 39, 1);graph.addEgde(39, 47, 1);
        graph.addEgde(47, 48, 1);graph.addEgde(48, 49, 1);graph.addEgde(49, 50, 1);
        graph.addEgde(32, 40, 1);graph.addEgde(40, 50, 1);graph.addEgde(50, 58, 1);
        graph.addEgde(58, 68, 1);graph.addEgde(68, 76, 1);graph.addEgde(47, 57, 1);
        graph.addEgde(57, 66, 1);graph.addEgde(66, 67, 1);graph.addEgde(67, 75, 1);
        graph.addEgde(75, 84, 1);graph.addEgde(84, 83, 1);graph.addEgde(84, 85, 1);
        graph.addEgde(85, 86, 1);graph.addEgde(76, 86, 1);graph.addEgde(33, 41, 1);
        graph.addEgde(41, 42, 1);graph.addEgde(42, 51, 1);graph.addEgde(51, 60, 1);
        graph.addEgde(60, 61, 1);graph.addEgde(60, 59, 1);graph.addEgde(61, 70, 1);
        graph.addEgde(61, 62, 1);graph.addEgde(34, 44, 1);graph.addEgde(44, 43, 1);
        graph.addEgde(43, 52, 1);graph.addEgde(52, 62, 1);graph.addEgde(62, 63, 1);
        graph.addEgde(63, 71, 1);graph.addEgde(35, 45, 1);graph.addEgde(45, 53, 1);
        graph.addEgde(53, 54, 1);graph.addEgde(54, 55, 1);graph.addEgde(55, 56, 1);
        graph.addEgde(38, 46, 1);graph.addEgde(46, 56, 1);graph.addEgde(56, 65, 1);
        graph.addEgde(65, 74, 1);graph.addEgde(74, 73, 1);graph.addEgde(73, 82, 1);
        graph.addEgde(82, 91, 1);graph.addEgde(91, 92, 1);graph.addEgde(91, 90, 1);
        graph.addEgde(53, 64, 1);graph.addEgde(64, 72, 1);graph.addEgde(72, 81, 1);
        graph.addEgde(81, 89, 1);graph.addEgde(90, 89, 1);graph.addEgde(89, 99, 1);
        graph.addEgde(71, 79, 1);graph.addEgde(79, 80, 1);graph.addEgde(80, 81, 1);
        graph.addEgde(79, 88, 1);graph.addEgde(88, 98, 1);graph.addEgde(59, 69, 1);
        graph.addEgde(69, 78, 1);graph.addEgde(76, 77, 1);graph.addEgde(77, 78, 1);
        graph.addEgde(78, 87, 1);graph.addEgde(87, 94, 1);graph.addEgde(94, 95, 1);
        graph.addEgde(95, 96, 1);graph.addEgde(96, 97, 1);graph.addEgde(97, 98, 1);
        graph.addEgde(98, 104, 1);graph.addEgde(104, 114, 1);graph.addEgde(99, 105, 1);
        graph.addEgde(105, 106, 1);graph.addEgde(106, 107, 1);graph.addEgde(107, 117, 1);
        graph.addEgde(117, 125, 1);graph.addEgde(125, 124, 1);graph.addEgde(124, 130, 1);
        graph.addEgde(124, 123, 1);graph.addEgde(123, 116, 1);graph.addEgde(105, 116, 1);
        graph.addEgde(116, 115, 1);graph.addEgde(115, 114, 1);graph.addEgde(114, 113, 1);
        graph.addEgde(113, 122, 1);graph.addEgde(112, 129, 1);graph.addEgde(129, 128, 1);
        graph.addEgde(128, 127, 1);graph.addEgde(127, 121, 1);graph.addEgde(121, 112, 1);
        graph.addEgde(112, 111, 1);graph.addEgde(94, 103, 1);graph.addEgde(103, 111, 1);
        graph.addEgde(111, 110, 1);graph.addEgde(86, 93, 1);graph.addEgde(93, 102, 1);
        graph.addEgde(102, 101, 1);graph.addEgde(101, 100, 1);graph.addEgde(100, 108, 1);
        graph.addEgde(108, 118, 1);graph.addEgde(118, 119, 1);graph.addEgde(119, 126, 1);
        graph.addEgde(119, 120, 1);graph.addEgde(120, 109, 1);graph.addEgde(102, 109, 1);
        graph.addEgde(110, 109, 1);


        graph.addEgde(19, 14, 1);
        graph.addEgde(14, 3, 1);
        graph.addEgde(3, 2, 1);
        graph.addEgde(2, 1, 1);
        graph.addEgde(1, 0, 1);
        graph.addEgde(32, 19, 1);
        graph.addEgde(32, 31, 1);
        graph.addEgde(31, 30, 1);
        graph.addEgde(30, 29, 1);
        graph.addEgde(21, 20, 1);
        graph.addEgde(20, 19, 1);
        graph.addEgde(22, 21, 1);
        graph.addEgde(22, 15, 1);
        graph.addEgde(15, 6, 1);
        graph.addEgde(6, 5, 1);
        graph.addEgde(5, 4, 1);
        graph.addEgde(4, 3, 1);
        graph.addEgde(23, 22, 1);
        graph.addEgde(24, 23, 1);
        graph.addEgde(7, 16, 1);
        graph.addEgde(8, 7, 1);
        graph.addEgde(9, 8, 1);
        graph.addEgde(10, 9, 1);
        graph.addEgde(11, 10, 1);
        graph.addEgde(12, 11, 1);
        graph.addEgde(13, 12, 1);
        graph.addEgde(17, 10, 1);
        graph.addEgde(27, 17, 1);
        graph.addEgde(27, 26, 1);
        graph.addEgde(26, 25, 1);
        graph.addEgde(25, 24, 1);
        graph.addEgde(59, 60, 1);
        graph.addEgde(60, 51, 1);
        graph.addEgde(51, 42, 1);
        graph.addEgde(42, 41, 1);
        graph.addEgde(41, 33, 1);
        graph.addEgde(33, 21, 1);

        graph.addEgde(76, 68, 1);
        graph.addEgde(68, 58, 1);
        graph.addEgde(58, 50, 1);
        graph.addEgde(50, 40, 1);
        graph.addEgde(40, 32, 1);
        graph.addEgde(84, 75, 1);
        graph.addEgde(75, 67, 1);
        graph.addEgde(67, 66, 1);
        graph.addEgde(66, 57, 1);
        graph.addEgde(57, 47, 1);
        graph.addEgde(47, 39, 1);
        graph.addEgde(39, 29, 1);

        graph.addEgde(48, 47, 1);
        graph.addEgde(49, 48, 1);
        graph.addEgde(50, 49, 1);
        graph.addEgde(102, 93, 1);
        graph.addEgde(93, 86, 1);
        graph.addEgde(86, 76, 1);
        graph.addEgde(86, 85, 1);
        graph.addEgde(85, 84, 1);
        graph.addEgde(94, 87, 1);
        graph.addEgde(87, 78, 1);
        graph.addEgde(78, 77, 1);
        graph.addEgde(77, 76, 1);
        graph.addEgde(78, 69, 1);
        graph.addEgde(69, 59, 1);
        graph.addEgde(120, 119, 1);
        graph.addEgde(119, 118, 1);
        graph.addEgde(118, 108, 1);
        graph.addEgde(108, 100, 1);
        graph.addEgde(100, 101, 1);
        graph.addEgde(101, 102, 1);
        graph.addEgde(109, 102, 1);
        graph.addEgde(109, 110, 1);
        graph.addEgde(110, 111, 1);
        graph.addEgde(111, 103, 1);
        graph.addEgde(103, 94, 1);
        graph.addEgde(111, 112, 1);
        graph.addEgde(112, 121, 1);
        graph.addEgde(121, 127, 1);
        graph.addEgde(127, 128, 1);
        graph.addEgde(128, 129, 1);
        graph.addEgde(129, 122, 1);
        graph.addEgde(122, 113, 1);
        graph.addEgde(113, 114, 1);
        graph.addEgde(114, 104, 1);
        graph.addEgde(114, 98, 1);
        graph.addEgde(98, 97, 1);
        graph.addEgde(97, 96, 1);
        graph.addEgde(96, 95, 1);
        graph.addEgde(95, 94, 1);
        graph.addEgde(98, 88, 1);
        graph.addEgde(88, 79, 1);
        graph.addEgde(79, 71, 1);
        graph.addEgde(71, 63, 1);
        graph.addEgde(63, 62, 1);
        graph.addEgde(62, 61, 1);
        graph.addEgde(61, 60, 1);
        graph.addEgde(62, 52, 1);
        graph.addEgde(52, 43, 1);
        graph.addEgde(43, 44, 1);
        graph.addEgde(44, 34, 1);
        graph.addEgde(34, 25, 1);
        graph.addEgde(81, 80, 1);
        graph.addEgde(80, 79, 1);
        graph.addEgde(81, 72, 1);
        graph.addEgde(72, 64, 1);
        graph.addEgde(64, 53, 1);
        graph.addEgde(53, 45, 1);
        graph.addEgde(45, 35, 1);
        graph.addEgde(35, 27, 1);
        graph.addEgde(91, 82, 1);
        graph.addEgde(82, 73, 1);
        graph.addEgde(73, 74, 1);
        graph.addEgde(74, 65, 1);
        graph.addEgde(65, 56, 1);
        graph.addEgde(56, 55, 1);
        graph.addEgde(55, 54, 1);
        graph.addEgde(54, 53, 1);
        graph.addEgde(56, 46, 1);
        graph.addEgde(46, 38, 1);
        graph.addEgde(38, 37, 1);
        graph.addEgde(37, 36, 1);
        graph.addEgde(36, 35, 1);
        graph.addEgde(123, 124, 1);
        graph.addEgde(123, 124, 1);
        graph.addEgde(124, 125, 1);
        graph.addEgde(125, 117, 1);
        graph.addEgde(117, 107, 1);
        graph.addEgde(107, 106, 1);
        graph.addEgde(106, 105, 1);
        graph.addEgde(105, 99, 1);
        graph.addEgde(99, 89, 1);
        graph.addEgde(89, 81, 1);
        graph.addEgde(89, 90, 1);
        graph.addEgde(90, 91, 1);
        graph.addEgde(116, 123, 1);
        graph.addEgde(115, 116, 1);
        graph.addEgde(116, 105, 1);
        graph.addEgde(114, 115, 1);

    }

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
    boolean firstTimeDijkstra =true;
    boolean firstTimeBFS = true;
    boolean firstTimeAStar = true;


    private Image pacman1, pacman2up, pacman2left, pacman2right, pacman2down;
    private Image pacman3up, pacman3down, pacman3left, pacman3right;
    private Image pacman4up, pacman4down, pacman4left, pacman4right;

    private int pacman_x, pacman_y, pacmand_x, pacmand_y;
    private int req_dx, req_dy, view_dx, view_dy;

    private final short levelData[] = {
            11, 10, 10, 2,10, 10, 6, 7, 3, 10, 10, 2, 10, 10, 14,
            11, 2, 6,5, 11, 14, 5, 13, 5, 11, 14, 5, 3, 2, 14,
            7, 9, 12, 1, 10, 2, 8, 10, 8, 2, 10, 4, 9, 12, 7,
            1, 10, 10, 4, 7, 5, 11, 2, 14, 5, 7, 1, 10, 10, 4,
            5, 11, 14, 5, 5, 9, 6, 5, 3, 12, 5, 5, 11, 14,5,
            1, 10, 10, 4, 1, 14, 5, 13, 5, 11, 4, 1, 10, 10, 4,
            5, 11, 6, 5, 5, 3, 8, 2, 8, 6, 5, 5, 3, 14, 5,
            9, 6, 5, 5, 13, 5, 7, 13, 7, 5, 13, 5, 5, 3, 12,
            15, 5, 13, 1, 10, 4, 1, 2, 4, 1, 10, 4, 13, 5, 15,
            10, 8, 10, 4, 7, 5, 9, 8, 12, 5, 7, 1, 10, 8, 14,
            3, 10, 14, 5, 5, 1, 10, 10, 10, 4, 5, 5, 11, 10, 6,
            5, 3, 10, 4, 13, 5, 11, 2, 14, 5, 13, 1, 10, 6, 5,
            5, 5, 15, 1, 10, 8, 6, 5, 3, 8, 10, 4, 15, 5, 5,
            5, 9, 2, 12, 3, 6, 5, 13, 5, 3, 6, 9, 2, 12, 5,
            9, 14, 13,  11, 8, 12, 9, 10, 12, 9, 8, 14, 13, 11, 12
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

        initAll();

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
                pathGlDFS= dfs.DFS(graph, 96);
                System.out.println("Answer path: " + convertToVertex(pathGlDFS));
                dfs.showStatistics();
                firstTimeDFS = false;
            }

            if(firstTimeBFS){
                System.out.println("****************BFS****************");
                BFSSearch bfs = new BFSSearch();
                pathGlBFS= bfs.BFS(graph, 96);
                System.out.println("Answer path: " + convertToVertex(pathGlBFS));
                bfs.showStatistics();
                firstTimeBFS = false;
            }


            if(firstTimeAStar){
                System.out.println("****************AStar****************");
                AStarSearch aStar = new AStarSearch();
                pathGlAStar = aStar.AStar(graph, h);
                System.out.println("Answer path: " + convertToVertex(pathGlAStar));
                aStar.showStatistics();
                firstTimeAStar = false;
            }

            if(firstTimeDijkstra){
                System.out.println("***********Dijkstra***********");
                GreedyAlgo greedy = new GreedyAlgo(graph);
                Map<Integer,Integer> res = greedy.execute(0);
                pathGlDijkstra = greedy.getPath(96);
                System.out.println("Answer path: " + convertToVertex(pathGlDijkstra));
                greedy.showStatistics();
                firstTimeDijkstra = false;
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
                    int input = Pacman.inputNum;
                   try {
                       if(input == 1){
                           movePac(convertToVertex(pathGlDFS));
                       }else if(input == 2 ){
                           movePac(convertToVertex(pathGlBFS));
                       }else if(input == 3){
                           movePac(convertToVertex(pathGlAStar));
                       }else{
                           movePac(convertToVertex(pathGlDijkstra));
                       }

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
