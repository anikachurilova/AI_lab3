import java.util.*;

public class GreedyAlgo implements Search {
    Integer amountOfSteps = 0;

    private final List<Integer> nodes;
    private final List<Edge>[] edges;
    private Set<Integer> settledNodes;
    private Set<Integer> unSettledNodes;
    private Map<Integer, Integer> predecessors;
    private Map<Integer, Integer> distance;

    public GreedyAlgo(Graph graph) {
        this.nodes = new ArrayList<Integer>();
        for(int i=0;i<graph.vertices;i++) {
            nodes.add(i);
        }
        this.edges = graph.adjacencylist;
//        this.graph.vertices = graph.vertices;
//        for(int i =0; i<graph.adjacencylist.length;i++) {
//            for (int j = 0; j < graph.adjacencylist[i].size(); j++) {
//                Edge a = graph.adjacencylist[i].get(j);
//                a = this.graph.adjacencylist[i].get(j);
//            }
//        }

    }

    public Map<Integer,Integer> execute(Integer source) {
        settledNodes = new HashSet<Integer>();
        unSettledNodes = new HashSet<Integer>();
        distance = new HashMap<Integer, Integer>();
        predecessors = new HashMap<Integer, Integer>();
        distance.put(source, 0);
        unSettledNodes.add(source);
        while (unSettledNodes.size() > 0) {
            Integer node = getMinimum(unSettledNodes);
            settledNodes.add(node);
            unSettledNodes.remove(node);
            findMinimalDistances(node);
        }
        return distance;
    }
    public List<Integer> getNeighbors(Integer node) {
        List<Integer> res = new ArrayList<>();
        for (int i = 0; i < edges.length; i++) {
            for (int j = 0; j < edges[i].size(); j++) {
                if (edges[i].get(j).source == node  && !isSettled(edges[i].get(j).destination)) {
                    res.add(edges[i].get(j).destination);
                }
            }
        }
        return res;
    }
    private void findMinimalDistances(Integer node) {
        List<Integer> adjacentNodes = getNeighbors(node);
        for (Integer target : adjacentNodes) {
            if (getShortestDistance(target) > getShortestDistance(node)
                    + getDistance(node, target)) {
                distance.put(target, getShortestDistance(node)
                        + getDistance(node, target));
                predecessors.put(target, node);
                unSettledNodes.add(target);
            }
        }

    }

    private int getDistance(Integer node, Integer target) {

        for (int i = 0; i < edges.length; i++) {
            for (int j = 0; j < edges[i].size(); j++) {
                if (edges[i].get(j).source == node && edges[i].get(j).destination == target) {
                    return edges[i].get(j).weight;
                }
            }
        }
        throw new RuntimeException("Should not happen");
    }

    private Integer getMinimum(Set<Integer> vertexes) {
        Integer minimum = null;
        for (Integer vertex : vertexes) {
            if (minimum == null) {
                minimum = vertex;
            } else {
                if (getShortestDistance(vertex) < getShortestDistance(minimum)) {
                    minimum = vertex;
                }
            }
        }
        return minimum;
    }

    private boolean isSettled(Integer vertex) {
        return settledNodes.contains(vertex);
    }

    private int getShortestDistance(Integer destination) {
        Integer d = distance.get(destination);
        if (d == null) {
            return Integer.MAX_VALUE;
        } else {
            return d;
        }
    }
    public LinkedList<Integer> getPath(Integer target) {
        LinkedList<Integer> path = new LinkedList<Integer>();
        Integer step = target;

        if (predecessors.get(step) == null) {
            return null;
        }
        path.add(step);
        while (predecessors.get(step) != null) {
            step = predecessors.get(step);
            path.add(step);
            amountOfSteps++;
        }

        Collections.reverse(path);
        return path;
    }


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

}

