package graphs;

import schema.Graph;
import schema.Node;

import java.util.*;

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
            myAdjacencyGraph.newVertex("A");
            myAdjacencyGraph.newVertex("B");
            myAdjacencyGraph.newVertex("C");
            myAdjacencyGraph.newVertex("D");
            myAdjacencyGraph.newVertex("E");
            myAdjacencyGraph.newVertex("X");
            myAdjacencyGraph.newVertex("P");

            myAdjacencyGraph.addArch("A", "B"); myAdjacencyGraph.addArch("A", "C"); myAdjacencyGraph.addArch("A", "X");
            myAdjacencyGraph.addArch("B", "D"); myAdjacencyGraph.addArch("B", "E");
            myAdjacencyGraph.addArch("C", "D"); myAdjacencyGraph.addArch("C", "X");
            myAdjacencyGraph.addArch("D", "E"); myAdjacencyGraph.addArch("D", "P");
            myAdjacencyGraph.addArch("X", "P");
            System.out.println(myAdjacencyGraph.deepFirstTraversal("C"));

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
    public boolean BFS(String nodeName) {
        if(nodeMap.isEmpty()) return false;
        Queue<String> toVisit = new LinkedList();
        HashSet<String> visited = new HashSet();
        String firstNode = nodeMap.entrySet().iterator().next().getKey();

        toVisit.add(firstNode);
        visited.add(firstNode);
        while(visited.size() < nodeMap.size()){
            // To consider lonely nodes
            if(toVisit.isEmpty()){
                for (Map.Entry<String, Node> entry : nodeMap.entrySet()){
                    if(!visited.contains(entry.getKey())) {
                        visited.add(entry.getValue().getNodeName());
                        toVisit.add(entry.getKey());
                    }
                }
            }

            for(Node node : adjListMap.get(toVisit.peek())){
                if(!visited.contains(node.getNodeName())){
                    visited.add(node.getNodeName());
                    toVisit.add(node.getNodeName());
                }
            }
            toVisit.remove();
        }
        return visited.contains(nodeName);
    }


    @Override
    public String breadthFirstTraversal(String partingNode) throws NodeNotFound {
        if(nodeMap.get(partingNode) == null) throw new NodeNotFound();
        Queue<String> toVisit = new LinkedList();
        HashSet<String> visited = new HashSet();
        String result = partingNode;

        toVisit.add(partingNode);
        visited.add(partingNode);
        while(visited.size() < nodeMap.size()){
            // To consider lonely nodes
            if(toVisit.isEmpty()){
                for (Map.Entry<String, Node> entry : nodeMap.entrySet()){
                    if(!visited.contains(entry.getKey())) {
                        visited.add(entry.getValue().getNodeName());
                        toVisit.add(entry.getKey());
                        result += " -> " + entry.getValue().getNodeName();
                    }
                }
            }

            for(Node node : adjListMap.get(toVisit.peek())){
                if(!visited.contains(node.getNodeName())){
                    visited.add(node.getNodeName());
                    result += " -> " + node.getNodeName();
                    toVisit.add(node.getNodeName());
                }
            }
            toVisit.remove();
        }
        return result;
    }

    @Override
    public boolean DFS(String nodeName) {
        return false;
    }

    @Override
    public String deepFirstTraversal(String partingNode) throws NodeNotFound {
        if(nodeMap.get(partingNode) == null) throw new NodeNotFound();
        Stack<String> toVisit = new Stack<String>();
        HashSet<String> visited = new HashSet();
        String result = partingNode;

        toVisit.add(partingNode);
        visited.add(partingNode);
        while(visited.size() < nodeMap.size()){
            boolean found = false;

            // To consider lonely nodes
            if(toVisit.isEmpty()){
                for (Map.Entry<String, Node> entry : nodeMap.entrySet()){
                    if(!visited.contains(entry.getKey()) && !found) {
                        visited.add(entry.getValue().getNodeName());
                        toVisit.add(entry.getKey());
                        result += " -> " + entry.getValue().getNodeName();
                        found = true;
                    }
                }
            }

            for(Node node : adjListMap.get(toVisit.peek())){
                if(!visited.contains(node.getNodeName()) && !found){
                    visited.add(node.getNodeName());
                    toVisit.add(node.getNodeName());
                    result += " -> " + node.getNodeName();
                    found = true;
                }
            }
            if(!found) toVisit.pop();
        }
        return result;
    }
}
