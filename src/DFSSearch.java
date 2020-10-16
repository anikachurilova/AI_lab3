import java.util.ArrayList;
import java.util.List;

public class DFSSearch implements Search {
    Integer amountOfSteps = 0;

    @Override
    public boolean isCandy(Node node){
        return node.isPoint();
    }

    @Override
    public Integer getAmountOfSteps() {
        return amountOfSteps;
    }

    @Override
    public void showStatistics() {
        System.out.println("Amount of steps: " + getAmountOfSteps());
    }

    public List<Integer> DFS(Graph graph, Integer goal){
        List<Integer> visited = new ArrayList<>();
        List<Integer> path = new ArrayList<>();
        List<Integer> fringe = new ArrayList<>();

        fringe.add(0);
        while (!fringe.isEmpty()){
            amountOfSteps++;
            Integer currentNodeIndex = fringe.get(fringe.size()-1);
            path.add(currentNodeIndex);
            visited.add(currentNodeIndex);
            if(currentNodeIndex == goal){
                return path;
            }
            for (int i = 0; i < graph.adjacencylist[currentNodeIndex].size(); i++) {
                int sonIndex = graph.adjacencylist[currentNodeIndex].get(i).destination;
                if(!visited.contains(sonIndex)){
                    fringe.add(sonIndex);
                }

            }
            fringe.remove(currentNodeIndex);
            Integer pSize = path.size();
            for(int i = pSize; i == 0; i--){
                Integer nodeIndex = path.size()-1;
                boolean sonExists = false;
                for (int j = 0; j < graph.adjacencylist[nodeIndex].size(); j++) {
                    int sonIndex = graph.adjacencylist[nodeIndex].get(i).destination;
                    if(!visited.contains(sonIndex)){
                        sonExists = true;
                    }
                }
                if(!sonExists){
                    path.remove(nodeIndex);

                }
            }

        }
        return path;
    }
}
