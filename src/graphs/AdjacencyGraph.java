package graphs;

import schema.Graph;
import schema.Node;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AdjacencyGraph<T> extends Graph {

    private Map<String, Node> nodeMap;
    private Map<String, ArrayList<Node>> adjListMap;

    public AdjacencyGraph(){
        nodeMap = new HashMap<>();
        adjListMap = new HashMap<>();
    }

    public static void main(String [] args){
        AdjacencyGraph myAdjacencyGraph = new AdjacencyGraph();
        try{
            myAdjacencyGraph.newVertex("vertex1", 23);
            myAdjacencyGraph.newVertex("vertex2", 24);
            myAdjacencyGraph.newVertex("vertex3", 25);
            myAdjacencyGraph.addArch("vertex1", "vertex2");
            myAdjacencyGraph.addArch("vertex1", "vertex3");
        }catch (Exception e){
            System.out.println(e);
        }
    }

    public Node getVertex(String nodeName) throws NodeNotFound {
        if(nodeMap.get(nodeName) == null) throw new NodeNotFound();
        return nodeMap.get(nodeName);
    }

    @Override
    public void addArch(String firstNodeName, String secondNodeName) throws NodeNotFound, NodeExistsException {
        if(nodeMap.get(firstNodeName) == null || nodeMap.get(secondNodeName) == null) throw new NodeNotFound();
        if(isAdjacent(firstNodeName, secondNodeName)) throw new NodeExistsException();

        adjListMap.get(firstNodeName).add(nodeMap.get(secondNodeName));
        adjListMap.get(secondNodeName).add(nodeMap.get(firstNodeName));
    }

    @Override
    public void removeArch(String firstNodeName, String secondNodeName) throws NodeNotFound {
        if(!isAdjacent(firstNodeName, secondNodeName)) return;
        if(nodeMap.get(firstNodeName) == null || nodeMap.get(secondNodeName) == null) throw new NodeNotFound();

        int indexOfFirstArch = 0, indexOfSecondArch = 0;
        for(Node node : adjListMap.get(firstNodeName)){
            if(node.equals(secondNodeName)) break;
            indexOfFirstArch++;
        }

        for(Node node : adjListMap.get(secondNodeName)){
            if(node.equals(firstNodeName)) break;
            indexOfSecondArch++;
        }

        adjListMap.get(firstNodeName).remove(indexOfFirstArch);
        adjListMap.get(secondNodeName).remove(indexOfSecondArch);
    }

    @Override
    public boolean isAdjacent(String firstNodeName, String secondNodeName) throws NodeNotFound {
        if(nodeMap.get(firstNodeName) == null || nodeMap.get(secondNodeName) == null) throw new NodeNotFound();
        for (Node node : adjListMap.get(firstNodeName)){
            if(node.equals( secondNodeName)) return true;
        }
        return false;
    }

    @Override
    public void newVertex(String nodeName) throws NodeExistsException {
        newVertex(nodeName, (T) nodeName);
    }
    
    public void newVertex(String nodeName, T data) throws NodeExistsException {
        Node<T> newNode = new Node(data, nodeName);
        if(nodeMap.get(nodeName) != null) throw new NodeExistsException();

        nodeMap.put(nodeName, newNode);
        adjListMap.put(nodeName, new ArrayList<>());
    }

    @Override
    public void removeVertex(String nodeName) throws NodeNotFound {
        //Remove all archs related to that node
        for (Map.Entry<String, Node> entry : nodeMap.entrySet() ){
            removeArch(nodeName, entry.getKey());
        }
        //Remove node instance
        adjListMap.remove(nodeName);
        nodeMap.remove(nodeName);
    }


    @Override
    public boolean BSF(String nodeName) {
        return false;
    }

    @Override
    public boolean DFS(String nodeName) {
        return false;
    }

    @Override
    public void breadthFirstTraversal() {

    }

    @Override
    public void deepFirstTraversal() {

    }
}
