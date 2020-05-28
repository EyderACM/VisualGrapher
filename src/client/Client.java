package client;

import client.components.*;
import processing.core.*;

import java.util.ArrayList;

public class Client extends PApplet {

    ArrayList<VNode> nodeList;
    Boolean mouseClickedOnCanvas = false;
    int nodeHeight = 50; int nodeWidth = 50;
    boolean nameNodeState = false;

    public void settings(){
        size(800, 600);
    }

    public void setup(){
        nodeList = new ArrayList<VNode>();
    }

    public void draw(){
        background(360,360,360);
        textSize(30);
        fill(0, 0, 0);

        for(VNode node : nodeList) node.display();
        if(mouseClickedOnCanvas) createEllipsePreview();
    }

    public void createEllipse(String name, float xPos, float yPos){
        VNode newNode = new VNode(name, xPos, yPos, nodeHeight, nodeWidth, this);
        nodeList.add(newNode);
        nameNodeState = true;
    }

    public void createEllipsePreview(){
        fill(66, 237, 240);
        noStroke();
        ellipse(mouseX, mouseY, nodeHeight, nodeWidth);
    }

    public void mousePressed() {
        mouseClickedOnCanvas = true;
    }

    public void mouseReleased(){
        mouseClickedOnCanvas = false;
        createEllipse("", mouseX, mouseY);
    }

    @Override
    public void keyReleased() {
        super.keyReleased();
        if(nameNodeState) nameNode(nodeList.get(nodeList.size()-1));
    }

    public void nameNode(VNode nodeToName){
        if(keyCode == ENTER){
            nameNodeState = false;
        }
        if(keyCode == BACKSPACE && nodeToName.getNodeName().length() > 0){
            nodeToName.setNodeName(nodeToName.getNodeName().substring(0, nodeToName.getNodeName().length()-1));
        }
        if((key >= 'a' && key <= 'z') || (key >= 'A' && key <= 'Z')){
            nodeToName.setNodeName(nodeToName.getNodeName().concat(str(key)));
        }
    }

    public static void main(String[] args){
        PApplet.main("client.Client");

        /*AdjacencyGraph myAdjacencyGraph = new AdjacencyGraph();
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
        }*/
    }
}
