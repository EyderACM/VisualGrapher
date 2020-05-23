package graphs;

import schema.Graph;
import schema.Node;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
            MG.newVertex("Patito");
            MG.newVertex("patitote");
            MG.newVertex("patitoteee");

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
        adjacentMatrix.remove(nodePos);
    }

    @Override
    public boolean BFS(String nodeName) {
        return false;
    }

    @Override
    public boolean DFS(String nodeName) {
        return false;
    }

    @Override
    public String breadthFirstTraversal(String partingNode) throws NodeNotFound {
        return null;
    }

    @Override
    public String deepFirstTraversal(String partingNode) throws NodeNotFound {
        return null;
    }
}
