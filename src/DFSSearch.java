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

    public List<Pair> DFS(Node[][] allPaths){
        List<Pair> visited = new ArrayList<>();
        List<Pair> path = new ArrayList<>();
        List<Pair> fringe = new ArrayList<>();

        fringe.add(new Pair(0,0));
        while (!fringe.isEmpty()){
            amountOfSteps++;
            Integer x = fringe.get(fringe.size()-1).getFirst();
            Integer y = fringe.get(fringe.size()-1).getSecond();
            //move pacman step to current node
            Node currentNode = allPaths[x][y];

            path.add(new Pair(x,y));
           // System.out.println("path: "+path);
            visited.add(new Pair(x,y));
           // System.out.println("Visited: "+visited);
          //  System.out.println("Fringe: "+fringe);


            if(isCandy(currentNode)){
                return path;
            }

            Pair nb1 = currentNode.getNeighbour1();
            Pair nb2 = currentNode.getNeighbour2();
            Pair nb3 = currentNode.getNeighbour3();
            Pair nb4 = currentNode.getNeighbour4();


            if(nb1 != null && !visited.contains(nb1)){
                fringe.add(nb1);
            }
            if(nb2 != null && !visited.contains(nb2)){
                fringe.add(nb2);
            }
           // System.out.println("dd "+visited.contains(nb1));
            if(nb3 != null && !visited.contains(nb3)){
                fringe.add(nb3);
            }
            if(nb4 != null && !visited.contains(nb4)){
                fringe.add(nb4);
            }

            fringe.remove(new Pair(x,y));

            Integer pSize = path.size();
            for(int i = pSize; i == 0; i--){
                Integer x1 = path.get(path.size()-1).getFirst();
                Integer y1 = path.get(path.size()-1).getSecond();
                Node nodee = allPaths[x1][y1];
                Pair nb1_1 = nodee.getNeighbour1();
                Pair nb2_1 = nodee.getNeighbour2();
                Pair nb3_1 = nodee.getNeighbour3();
                Pair nb4_1 = nodee.getNeighbour4();
                if(nb1_1 != null && !visited.contains(nb1_1)){
                    break;
                }
                if(nb2_1 != null && !visited.contains(nb2_1)){
                    break;
                }
                if(nb3_1 != null && !visited.contains(nb3_1)){
                    break;
                }
                if(nb4_1 != null && !visited.contains(nb4_1)){
                    break;
                }
                path.remove(new Pair(x1,y1));
                //move pacman one step back
            }

        }
        return path;
    }
}
