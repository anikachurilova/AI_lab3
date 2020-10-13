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
        //    System.out.println(path);
            amountOfSteps++;
//            Integer x = fringe.get(fringe.size()-1).getFirst();
//            Integer y = fringe.get(fringe.size()-1).getSecond();
            Integer currentNodeIndex = fringe.size()-1;

            path.add(currentNodeIndex);
            visited.add(currentNodeIndex);

            if(currentNodeIndex == goal){
                return path;
            }

//            Pair nb1 = currentNode.getNeighbour1();
//            Pair nb2 = currentNode.getNeighbour2();
//            Pair nb3 = currentNode.getNeighbour3();
//            Pair nb4 = currentNode.getNeighbour4();

            for (int i = 0; i < graph.adjacencylist[currentNodeIndex].size(); i++) {
                int sonIndex = graph.adjacencylist[currentNodeIndex].get(i).destination;
                fringe.add(sonIndex);

            }
//            if(nb1 != null && !visited.contains(nb1)){
//                fringe.add(nb1);
//            }
//            if(nb2 != null && !visited.contains(nb2)){
//                fringe.add(nb2);
//            }
//            if(nb3 != null && !visited.contains(nb3)){
//                fringe.add(nb3);
//            }
//            if(nb4 != null && !visited.contains(nb4)){
//                fringe.add(nb4);
//            }

            fringe.remove(currentNodeIndex);

            Integer pSize = path.size();
            for(int i = pSize; i == 0; i--){
                Integer nodeIndex = path.size()-1;
//                Integer x1 = path.get(path.size()-1).getFirst();
//                Integer y1 = path.get(path.size()-1).getSecond();
           //     Node nodee = allPaths[x1][y1];
//                Pair nb1_1 = nodee.getNeighbour1();
//                Pair nb2_1 = nodee.getNeighbour2();
//                Pair nb3_1 = nodee.getNeighbour3();
//                Pair nb4_1 = nodee.getNeighbour4();
                boolean sonExists = false;
                for (int j = 0; j < graph.adjacencylist[nodeIndex].size(); j++) {
                    int sonIndex = graph.adjacencylist[nodeIndex].get(i).destination;
                    if(!visited.contains(sonIndex)){
                        sonExists = true;
                    }
                }
//                if(nb1_1 != null && !visited.contains(nb1_1)){
//                    break;
//                }
//                if(nb2_1 != null && !visited.contains(nb2_1)){
//                    break;
//                }
//                if(nb3_1 != null && !visited.contains(nb3_1)){
//                    break;
//                }
//                if(nb4_1 != null && !visited.contains(nb4_1)){
//                    break;
//                }
                if(!sonExists){
                    path.remove(nodeIndex);

                }
            }

        }
        return path;
    }
}
