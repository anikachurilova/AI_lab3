
import java.util.ArrayList;
import java.util.List;

public class Node {
    private boolean isPoint;

    public Pair getNeighbour1() {
        return neighbour1;
    }

    public Pair getNeighbour2() {
        return neighbour2;
    }

    public Pair getNeighbour3() {
        return neighbour3;
    }

    public Pair getNeighbour4() {
        return neighbour4;
    }

    private Pair neighbour1;
    private Pair neighbour2;
    private Pair neighbour3;
    private Pair neighbour4;

    public boolean isPoint() {
        return isPoint;
    }


    public Node(boolean isPoint, Pair nb1, Pair nb2, Pair nb3, Pair nb4){
        this.isPoint = isPoint;
        this.neighbour1 = nb1;
        this.neighbour2 = nb2;
        this.neighbour3 = nb3;
        this.neighbour4 = nb4;
    }
    public Node(boolean isPoint, Pair nb1, Pair nb2, Pair nb3){
        this.isPoint = isPoint;
        this.neighbour1 = nb1;
        this.neighbour2 = nb2;
        this.neighbour3 = nb3;
        this.neighbour4 = null;
    }

    public Node(boolean isPoint, Pair nb1, Pair nb2){
        this.isPoint = isPoint;
        this.neighbour1 = nb1;
        this.neighbour2 = nb2;
        this.neighbour3 = null;
        this.neighbour4 = null;
    }

    public Node(boolean isPoint, Pair nb1){
        this.isPoint = isPoint;
        this.neighbour1 = nb1;
        this.neighbour2 = null;
        this.neighbour3 = null;
        this.neighbour4 = null;
    }

    public Node(){
        this.isPoint = false;
        this.neighbour1 = null;
        this.neighbour2 = null;
        this.neighbour3 = null;
        this.neighbour4 = null;
    }
}
