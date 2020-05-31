package client;

import client.components.*;
import graphs.AdjacencyGraph;
import graphs.MatrixGraph;
import org.w3c.dom.Text;
import processing.core.*;

import java.util.ArrayList;

public class Client extends PApplet {

    ArrayList<VNode> nodeList;
    ArrayList<VArch> archs;
    VNode[] toLink;

    AdjacencyGraph adjacencyGraph;
    MatrixGraph matrixGraph;
    int nodeHeight = 60; int nodeWidth = 60;

    public void settings(){
        size(1200, 600);
    }

    public void setup(){
        toLink = new VNode[2];
        adjacencyGraph = new AdjacencyGraph();
        matrixGraph = new MatrixGraph();
        frameRate(120);
        nodeList = new ArrayList<VNode>();
        archs = new ArrayList<VArch>();
    }

    public void draw(){
        background(235,235,235);
        createSidePanel();
        textSize(30);

        for(VArch arch : archs) arch.display();
        for(VNode node : nodeList) {
            node.display();
            node.onHover();
        }
        if(States.archCreationState) createArchPreview();
        if(States.mouseClickedOnCanvas && !States.nameNodeState) createEllipsePreview();
        if(States.invalidCreation) displayInvalidCreationAlert();
        if(States.nameNodeState) {
            pushStyle();
            textAlign(PApplet.CENTER);
            fill(0);
            text("Name your node, press enter once finished", width/2+50, 35);
            popStyle();
        }
    }

    public void displayInvalidCreationAlert(){
        pushStyle();
        fill(360, 360, 360);
        rect(width-325, height-70, 350, 50, 15);
        popStyle();

        pushStyle();
        fill(0);
        textSize(20);
        text("¡¡Please input a new node!!", width-300, height-40);
        popStyle();
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

    public void createArch(){
        boolean exists = false;
        for(VArch arch : archs){
            if(arch.nodesName.contains(toLink[0].getNodeName()) && arch.nodesName.contains(toLink[1].getNodeName()))
                exists = true;
        }
        if(!exists)
            archs.add(new VArch(toLink[0].xPos, toLink[0].yPos, toLink[1].xPos, toLink[1].yPos, toLink[0].getNodeName(), toLink[1].getNodeName(), this));
        States.archCreationState = false;
        toLink[0] = null;
        toLink[1] = null;
    }

    public void createEllipsePreview(){
        fill(66, 237, 240);
        noStroke();
        ellipse(mouseX, mouseY, nodeHeight, nodeWidth);
    }

    public void createArchPreview(){
        pushStyle();
        stroke(66, 237, 240);
        strokeWeight(5);
        line(toLink[0].xPos, toLink[0].yPos, mouseX, mouseY);
        popStyle();
    }

    public void mousePressed() {
        VNode nodeNearby = isNearbyNode();

        if(States.forCreation && nodeNearby == null)
            States.mouseClickedOnCanvas = true;

        if(nodeNearby != null && !States.archCreationState){
            toLink[0] = nodeNearby;
            States.archCreationState = true;
        }
    }

    public void mouseReleased(){
        VNode nodeNearby = isNearbyNode();

        if(!States.nameNodeState && States.mouseClickedOnCanvas && mouseX > 300)
            createEllipse("", mouseX, mouseY);

        if(toLink[0] != null && nodeNearby != toLink[0] && nodeNearby != null){
            toLink[1] = nodeNearby;
            createArch();
        }

        States.archCreationState = false;
        States.mouseClickedOnCanvas = false;
    }

    public VNode isNearbyNode(){
        for(VNode node : nodeList){
            if(dist(mouseX, mouseY, node.xPos, node.yPos) < nodeHeight){
                return node;
            }
        }
        return null;
    }

    @Override
    public void keyReleased() {
        super.keyReleased();
        if(States.nameNodeState) nameNode(nodeList.get(nodeList.size()-1));
    }

    public void nameNode(VNode nodeToName){
        if(keyCode == ENTER){
            try{
                matrixGraph.newVertex(nodeToName.getNodeName());
                adjacencyGraph.newVertex(nodeToName.getNodeName());
                States.nameNodeState = false;
                nodeToName.stroked = false;
                States.invalidCreation = false;
            }catch (Exception e){
                States.invalidCreation = true;
            }
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
