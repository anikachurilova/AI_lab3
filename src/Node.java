import java.util.List;

public class Node {
    private boolean isPoint;
    private List<Pair<Integer,Integer>> neighbours;

    public boolean isPoint() {
        return isPoint;
    }


    public List<Pair<Integer, Integer>> getNeighbours() {
        return neighbours;
    }

    public Node(boolean isPoint, List<Pair<Integer, Integer>> neighbours) {
        this.isPoint = isPoint;
        this.neighbours = neighbours;
    }
}
