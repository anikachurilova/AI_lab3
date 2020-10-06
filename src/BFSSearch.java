import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class BFSSearch implements Search {
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

    public LinkedList<Pair> BFS(Node[][] allPaths) throws InterruptedException {
        LinkedList<Pair> queue = new LinkedList<Pair>();
        List<Pair> visited = new ArrayList<>();
        LinkedList<Pair> fringe = new LinkedList<Pair>();

        queue.add(new Pair(0,0));
        fringe.add(new Pair(0,0));
        visited.add(new Pair(0,0));

        while (queue.size() != 0){
            amountOfSteps++;
            Integer x = fringe.get(0).getFirst();
            Integer y = fringe.get(0).getSecond();

            Node currentNode = allPaths[x][y];
//
//            if(currentNode.isPoint()){
//                return queue;
//            }

            Pair nb1 = currentNode.getNeighbour1();
            Pair nb2 = currentNode.getNeighbour2();
            Pair nb3 = currentNode.getNeighbour3();
            Pair nb4 = currentNode.getNeighbour4();

            if(!visited.contains(nb1) && nb1 != null && !nb1.equals(new Pair(x,y))){
                visited.add(nb1);
                queue.add(nb1);
                fringe.add(nb1);
                Node s = allPaths[nb1.getFirst()][nb1.getSecond()];
                if(s.isPoint()){
                    break;
                }
            }
            if( !visited.contains(nb2) && nb2 != null && !nb2.equals(new Pair(x,y))){
                visited.add(nb2);
                queue.add(nb2);
                fringe.add(nb2);
                Node s = allPaths[nb2.getFirst()][nb2.getSecond()];
                if(s.isPoint()){
                    break;
                }
            }
            if(!visited.contains(nb3) && nb3 != null && !nb3.equals(new Pair(x,y))){
                visited.add(nb3);
                queue.add(nb3);
                fringe.add(nb3);
                Node s = allPaths[nb3.getFirst()][nb3.getSecond()];
                if(s.isPoint()){
                    break;
                }
            }
            if(!visited.contains(nb4) && nb4 != null && !nb4.equals(new Pair(x,y))){
                visited.add(nb4);
                queue.add(nb4);
                fringe.add(nb4);
                Node s = allPaths[nb4.getFirst()][nb4.getSecond()];
                if(s.isPoint()){
                    break;
                }
            }
//            System.out.println("path: "+queue);
//            System.out.println("Visited: "+visited);
//            System.out.println("Fringe: "+fringe);
          //  Thread.sleep(1000);
            fringe.remove(0);
        }
        return queue;
    }

}
