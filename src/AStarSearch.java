import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AStarSearch implements Search{
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

    public List<Integer> AStar(Graph graph, Integer[] h){

        List<Integer> path = new ArrayList<>();
        List<Integer> f = new ArrayList<>();
        List<Integer> sons = new ArrayList<>();
        List<Integer> weights = new ArrayList<>();

        Integer totalWeight = 0;
        int nextVertex = 0;
        int currVertex = 0;
        boolean isPoint = false;
        path.add(currVertex);
        while(!isPoint) {
            System.out.println(path);
            if(h[currVertex] == 0){
                isPoint = true;
            }
            for (int i = 0; i < graph.adjacencylist[currVertex].size(); i++) {
                int sonIndex = graph.adjacencylist[currVertex].get(i).destination;
                if(!path.contains(sonIndex)){
                    sons.add(sonIndex);
                    int sonWeight= graph.adjacencylist[currVertex].get(i).destination;
                    weights.add(sonWeight);
                    int ff = graph.adjacencylist[currVertex].get(i).weight + totalWeight + h[sonIndex];
                    f.add(ff);
                }

            }
            nextVertex = sons.get(f.indexOf(Collections.min(f)));
            totalWeight += weights.get(f.indexOf(Collections.min(f)));
            path.add(nextVertex);
            f.clear();
            sons.clear();
            currVertex = nextVertex;
        }
        return path;
    }
}