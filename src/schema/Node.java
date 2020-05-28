package schema;

public class Node<T>{
    String nodeName;
    T data;

    public Node(T data, String nodeName){
        this.data = data;
        this.nodeName = nodeName;
    }

    public String getNodeName() {
        return nodeName;
    }

    public T getData() {
        return data;
    }

    public void setNodeName(String nodeName ){ this.nodeName = nodeName; }

    public boolean equals(Node toCompare){
        return nodeName.equals(toCompare.getNodeName());
    }

    public boolean equals(String toCompare){
        return nodeName.equals(toCompare);
    }
}
