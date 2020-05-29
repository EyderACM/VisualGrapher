package client;

import client.components.*;
import org.w3c.dom.Text;
import processing.core.*;

import java.util.ArrayList;

public class Client extends PApplet {

    ArrayList<VNode> nodeList;
    int nodeHeight = 60; int nodeWidth = 60;

    public void settings(){
        size(1200, 600);
    }

    public void setup(){

        frameRate(120);
        nodeList = new ArrayList<VNode>();
    }

    public void draw(){
        background(235,235,235);
        createSidePanel();
        textSize(30);

        for(VNode node : nodeList) {
            node.display();
            node.onHover();
        }
        if(States.mouseClickedOnCanvas && !States.nameNodeState) createEllipsePreview();
        if(States.nameNodeState) {
            pushStyle();
            textAlign(PApplet.CENTER);
            fill(0);
            text("Name your node, press enter once finished", width/2, 35);
            textSize(1);
            popStyle();
        }
    }

    public void createSidePanel(){
        SidePanel sPanel = new SidePanel(this);
        sPanel.build();
    }

    public void createEllipse(String name, float xPos, float yPos){
        VNode newNode = new VNode(name, xPos, yPos, nodeHeight, nodeWidth, this);
        nodeList.add(newNode);
        States.nameNodeState = true;
    }

    public void createEllipsePreview(){
        fill(66, 237, 240);
        noStroke();
        ellipse(mouseX, mouseY, nodeHeight, nodeWidth);
    }

    public void mousePressed() {
        if(States.forCreation)
            States.mouseClickedOnCanvas = true;
    }

    public void mouseReleased(){
        if(!States.nameNodeState && States.mouseClickedOnCanvas && mouseX > 300)
            createEllipse("", mouseX, mouseY);
        States.mouseClickedOnCanvas = false;
    }

    @Override
    public void keyReleased() {
        super.keyReleased();
        if(States.nameNodeState) nameNode(nodeList.get(nodeList.size()-1));
    }

    public void nameNode(VNode nodeToName){
        if(keyCode == ENTER){
            States.nameNodeState = false;
            nodeToName.stroked = false;
        }
        if(keyCode == BACKSPACE && nodeToName.getNodeName().length() > 0){
            nodeToName.setNodeName(nodeToName.getNodeName().substring(0, nodeToName.getNodeName().length()-1));
        }
        if(((key >= 'a' && key <= 'z') || (key >= 'A' && key <= 'Z')) && nodeToName.getNodeName().length() < 7){
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
