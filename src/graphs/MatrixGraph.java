package graphs;

import schema.Graph;
import schema.Node;

import java.util.*;

public class MatrixGraph<T> extends Graph {

    private ArrayList<Node> nodeList;
    public ArrayList<ArrayList<Integer>> adjacentMatrix;

    public MatrixGraph(){
        nodeList = new ArrayList<>();
        adjacentMatrix = new ArrayList<>();
    }

    public static void main(String[] args){
        MatrixGraph MG = new MatrixGraph();

        try {
            MG.newVertex("A");
            MG.newVertex("B");
            MG.newVertex("C");
            MG.newVertex("D");
            MG.newVertex("E");
            MG.newVertex("X");
            MG.newVertex("P");

            MG.addArch("A", "B"); MG.addArch("A", "C"); MG.addArch("A", "X");
            MG.addArch("B", "D"); MG.addArch("B", "E");
            MG.addArch("C", "D"); MG.addArch("C", "X");
            MG.addArch("D", "E"); MG.addArch("D", "P");
            MG.addArch("X", "P");
            System.out.println(MG.DFS("C"));

        }catch (Exception e){
            System.out.println(e);
        }
    }

    public int nodePosition(Node toFind){
        int position = -1;
        for (int i = 0; i < nodeList.size(); i++){
            if(nodeList.get(i).equals(toFind)) position = i;
        }
        return position;
    }

    @Override
    public void addArch(String firstNodeName, String secondNodeName) throws NodeNotFound, NodeExistsException {
        int firstNodePos = nodePosition(new Node(firstNodeName, firstNodeName));
        int secondNodePos = nodePosition(new Node(secondNodeName, secondNodeName));
        if(firstNodePos == -1 || secondNodePos == -1) throw new NodeNotFound();
        if(adjacentMatrix.get(firstNodePos).get(secondNodePos) == 1) throw new NodeExistsException();

        adjacentMatrix.get(firstNodePos).set(secondNodePos, 1);
        adjacentMatrix.get(secondNodePos).set(firstNodePos, 1);
    }

    @Override
    public void removeArch(String firstNodeName, String secondNodeName) throws NodeNotFound {
        int firstNodePos = nodePosition(new Node(firstNodeName, firstNodeName));
        int secondNodePos = nodePosition(new Node(secondNodeName, secondNodeName));
        if(firstNodePos == -1 || secondNodePos == -1) throw new NodeNotFound();

        adjacentMatrix.get(firstNodePos).set(secondNodePos, 0);
        adjacentMatrix.get(secondNodePos).set(firstNodePos, 0);
    }

    @Override
    public boolean isAdjacent(String firstNodeName, String secondNodeName) throws NodeNotFound {
        int firstNodePos = nodePosition(new Node(firstNodeName, firstNodeName));
        int secondNodePos = nodePosition(new Node(secondNodeName, secondNodeName));
        if(firstNodePos == -1 || secondNodePos == -1) throw new NodeNotFound();

        return adjacentMatrix.get(firstNodePos).get(secondNodePos) == 1;
    }

    @Override
    public void newVertex(String nodeName) throws NodeExistsException {
        newVertex(nodeName, (T) nodeName);
    }

    public void newVertex(String nodeName, T data) throws NodeExistsException {
        Node<T> newNode = new Node(data, nodeName);
        int nodePos = nodePosition(new Node(nodeName, nodeName));
        if(nodePos != -1) throw new NodeExistsException();

        nodeList.add(newNode);

        ArrayList newCol = new ArrayList();
        for(ArrayList<Integer> col : adjacentMatrix){
            col.add(0);
            newCol.add(0);
        }
        newCol.add(0);
        adjacentMatrix.add(newCol);
    }

    @Override
    public void removeVertex(String nodeName) throws NodeNotFound {
        int nodePos = nodePosition(new Node(nodeName, nodeName));
        if(nodePos == -1) throw new NodeNotFound();

        for (ArrayList<Integer> list : adjacentMatrix){
            list.remove(nodePos);
        }
        nodeList.remove(nodePos);
        adjacentMatrix.remove(nodePos);
    }

    @Override
    public boolean BFS(String nodeName) {
        if(nodeList.size() == 0) return false;
        Queue<Integer> toVisit = new LinkedList();
        HashSet<Integer> visited = new HashSet();

        toVisit.add(0);
        visited.add(0);
        while(visited.size() < nodeList.size()){
            // To consider lonely nodes
            if(toVisit.isEmpty()){
                for (Node node : nodeList){
                    int nodePos = nodePosition(node);
                    if(!visited.contains(nodePos)) {
                        visited.add(nodePos);
                        toVisit.add(nodePos);
                    }
                }
            }

            int lastToVisit = toVisit.peek();
            int pos = 0;
            for(Integer adjacentRel : adjacentMatrix.get(lastToVisit)){
                if(!visited.contains(pos) && adjacentRel == 1){
                    visited.add(pos);
                    toVisit.add(pos);
                }
                pos++;
            }
            toVisit.remove();
        }
        return visited.contains(nodePosition(new Node(nodeName, nodeName)));
    }

    @Override
    public String breadthFirstTraversal(String partingNode) throws NodeNotFound {
        int partingNodePos = nodePosition(new Node(partingNode, partingNode));
        if(partingNodePos == -1) throw new NodeNotFound();
        Queue<Integer> toVisit = new LinkedList();
        HashSet<Integer> visited = new HashSet();
        String result = partingNode;

        toVisit.add(partingNodePos);
        visited.add(partingNodePos);
        while(visited.size() < nodeList.size()){
            // To consider lonely nodes
            if(toVisit.isEmpty()){
                for (Node node : nodeList){
                    int nodePos = nodePosition(node);
                    if(!visited.contains(nodePos)) {
                        visited.add(nodePos);
                        toVisit.add(nodePos);
                        result += " -> " + node.getNodeName();
                    }
                }
            }

            int lastToVisit = toVisit.peek();
            int pos = 0;
            for(Integer adjacentRel : adjacentMatrix.get(lastToVisit)){
                if(!visited.contains(pos) && adjacentRel == 1){
                    visited.add(pos);
                    result += " -> " + nodeList.get(pos).getNodeName();
                    toVisit.add(pos);
                }
                pos++;
            }
            toVisit.remove();
        }
        return result;
    }

    @Override
    public boolean DFS(String nodeName) {
        if(nodeList.size() == 0) return false;
        Stack<Integer> toVisit = new Stack<Integer>();
        HashSet<Integer> visited = new HashSet();

        toVisit.add(0);
        visited.add(0);
        while(visited.size() < nodeList.size()){
            boolean found = false;

            // To consider lonely nodes
            if(toVisit.isEmpty()){
                for (Node node : nodeList){
                    int nodePos = nodePosition(node);
                    if(!visited.contains(nodePos) && !found) {
                        visited.add(nodePos);
                        toVisit.add(nodePos);
                        found = true;
                    }
                }
            }

            int lastToVisit = toVisit.peek();
            int pos = 0;
            for(Integer adjacentRel : adjacentMatrix.get(lastToVisit)){
                if(!visited.contains(pos) && !found && adjacentRel == 1){
                    visited.add(pos);
                    toVisit.add(pos);
                    found = true;
                }
                pos++;
            }
            if(!found) toVisit.pop();
        }
        return visited.contains(nodePosition(new Node(nodeName, nodeName)));
    }

    @Override
    public String deepFirstTraversal(String partingNode) throws NodeNotFound {
        int partingNodePos = nodePosition(new Node(partingNode, partingNode));
        if(partingNodePos == -1) throw new NodeNotFound();
        Stack<Integer> toVisit = new Stack<Integer>();
        HashSet<Integer> visited = new HashSet();
        String result = partingNode;

        toVisit.add(partingNodePos);
        visited.add(partingNodePos);
        while(visited.size() < nodeList.size()){
            boolean found = false;

            // To consider lonely nodes
            if(toVisit.isEmpty()){
                for (Node node : nodeList){
                    int nodePos = nodePosition(node);
                    if(!visited.contains(nodePos) && !found) {
                        visited.add(nodePos);
                        toVisit.add(nodePos);
                        result += " -> " + node.getNodeName();
                        found = true;
                    }
                }
            }

            int lastToVisit = toVisit.peek();
            int pos = 0;
            for(Integer adjacentRel : adjacentMatrix.get(lastToVisit)){
                if(!visited.contains(pos) && !found && adjacentRel == 1){
                    visited.add(pos);
                    toVisit.add(pos);
                    result += " -> " + nodeList.get(pos).getNodeName();
                    found = true;
                }
                pos++;
            }
            if(!found) toVisit.pop();
        }
        return result;
    }
}
