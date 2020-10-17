import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AStarSearch implements Search {
    Integer amountOfSteps = 0;

    @Override
    public boolean isCandy(Node node) {
        return false;
    }

    @Override
    public Integer getAmountOfSteps() {
        return amountOfSteps;
    }

    @Override
    public void showStatistics() {
        System.out.println("Amount of steps: " + getAmountOfSteps());
    }

    public List<Integer> AStar(Graph graph, Double[] h) {
        List<Integer> path = new ArrayList<>();
        List<Double> f = new ArrayList<>();
        List<Integer> sons = new ArrayList<>();
        List<Integer> weights = new ArrayList<>();

        Integer totalWeight = 0;
        int nextVertex = 0;
        int currVertex = 0;
        boolean isPoint = false;
        Double zero = 0.0;
        while (!isPoint) {
            path.add(currVertex);
            if (h[currVertex].equals(-1.0)) {
                isPoint = true;
                return path;
            }
            for (int i = 0; i < graph.adjacencylist[currVertex].size(); i++) {
                int sonIndex = graph.adjacencylist[currVertex].get(i).destination;
                if (!path.contains(sonIndex)) {

                    sons.add(sonIndex);
                    int sonWeight = graph.adjacencylist[currVertex].get(i).destination;
                    weights.add(sonWeight);
                    double ff = graph.adjacencylist[currVertex].get(i).weight + totalWeight + h[sonIndex];
                    f.add(ff);
                }
            }
            nextVertex = sons.get(f.indexOf(Collections.min(f)));
            totalWeight += weights.get(f.indexOf(Collections.min(f)));

            f.clear();
            sons.clear();
            currVertex = nextVertex;
            amountOfSteps++;
        }
        return path;
    }
}