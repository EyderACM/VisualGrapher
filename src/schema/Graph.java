package schema;

import graphs.NodeExistsException;
import graphs.NodeNotFound;

public abstract class Graph {
    public abstract void addArch(String firstNodeName, String secondNodeName) throws NodeNotFound, NodeExistsException;
    public abstract void removeArch(String firstNodeName, String secondNodeName) throws NodeNotFound;
    public abstract boolean isAdjacent(String firstNodeName, String secondNodeName) throws NodeNotFound;
    public abstract void newVertex(String nodeName) throws NodeExistsException;
    public abstract void removeVertex(String nodeName) throws NodeNotFound;
    public abstract boolean BFS(String nodeName); //Breadth First Search
    public abstract boolean DFS(String nodeName); //Depth First Search
    public abstract String breadthFirstTraversal(String partingNode) throws NodeNotFound;
    public abstract String deepFirstTraversal(String partingNode) throws NodeNotFound;
}
