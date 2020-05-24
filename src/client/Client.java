package client;

import client.components.*;
import processing.core.*;

import java.util.ArrayList;

public class Client extends PApplet {

    ArrayList<VNode> nodeList;
    Boolean mouseClickedOnCanvas = false;
    int nodeHeight = 40; int nodeWidth = 40;

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
        createEllipse("Ellipse", mouseX, mouseY);
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
