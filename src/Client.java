import graphs.AdjacencyGraph;
import processing.core.PApplet;

public class Client extends PApplet {

    public void settings(){
        size(800, 600);
    }

    public void setup(){

    }

    public void draw(){
        background(360,360,360);
        textSize(30);
        text("Funcionó bien bonito", 300, 300);
        fill(0, 0, 0);
    }

    public static void main(String[] args){
        //PApplet.main("Client");

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
            System.out.println(myAdjacencyGraph.DFS("C"));

        }catch (Exception e){
            System.out.println(e);
        }
    }
}
