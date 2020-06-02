package client;

import client.components.*;
import graphs.AdjacencyGraph;
import graphs.MatrixGraph;
import processing.core.*;

import java.util.ArrayList;
import java.util.Iterator;

public class Client extends PApplet {

    ArrayList<VNode> nodeList;
    ArrayList<VArch> archs;
    VNode[] toLink;

    AdjacencyGraph adjacencyGraph;
    MatrixGraph matrixGraph;

    SearchInput inputReference;
    int nodeHeight = 60; int nodeWidth = 60;

    public void settings(){
        size(1300, 600);
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
        createSearchPanel();
        textSize(30);

        for(VArch arch : archs) arch.display();
        for(VNode node : nodeList) {
            node.display();
            node.onHover();
        }
        if(States.archCreationState && !States.deletionState) createArchPreview();
        if(States.mouseClickedOnCanvas && !States.nameNodeState) createEllipsePreview();
        if(States.searchState && !States.deletionState && !States.nameNodeState) createSearchInput();
        if(States.invalidCreation) displayInvalidCreationAlert();
        if(States.deletionState) instructionText("Pick a node to delete");
        if(States.traverseState) instructionText("Pick the starting node");
        if(States.nameNodeState) instructionText("Name your node, press enter once finished");
    }

    public void instructionText(String msg){
        pushStyle();
        textAlign(PApplet.CENTER);
        fill(0);
        textSize(30);
        text(msg, width/2, 35);
        popStyle();
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

    public void createSearchPanel(){
        SearchPanel searchPanel = new SearchPanel(this);
        searchPanel.build();
    }

    public void createSearchInput(){
        SearchInput searchInput = new SearchInput(this, nodeList);
        inputReference = searchInput;
        searchInput.build();
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
        if(!exists) {
            String firstNodeName = toLink[0].getNodeName();
            String secondNodeName = toLink[1].getNodeName();
            archs.add(new VArch(toLink[0].xPos, toLink[0].yPos, toLink[1].xPos, toLink[1].yPos, firstNodeName, secondNodeName, this));
            try{
                adjacencyGraph.addArch(firstNodeName, secondNodeName);
                matrixGraph.addArch(firstNodeName, secondNodeName);
            }catch(Exception e){
                System.out.println("Unexpected exception");
            }
        }
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

    public void deleteNode(String nodeName){
        try{
            adjacencyGraph.removeVertex(nodeName);
            matrixGraph.removeVertex(nodeName);
        }catch (Exception e){
            System.out.println("Didn't found hehe");
        }
        for(Iterator<VNode> it = nodeList.iterator(); it.hasNext();){
            VNode node = it.next();
            if(node.getNodeName().equals(nodeName)) it.remove();
        }
        for(Iterator<VArch> it = archs.iterator(); it.hasNext();){
            VArch arch = it.next();
            if(arch.nodesName.contains(nodeName)) it.remove();
        }
    }

    public void traverse(VNode nodeNearby){
        try{
            SearchPanel.traverseStr = States.breadthTraverseState
                ? adjacencyGraph.breadthFirstTraversal(nodeNearby.getNodeName())
                : adjacencyGraph.deepFirstTraversal(nodeNearby.getNodeName());
        }catch(Exception e){}
        States.traverseState = false;
        States.breadthTraverseState = false;
        States.deepTraverseState = false;
    }

    public void mousePressed() {
        VNode nodeNearby = isNearbyNode();

        if(States.traverseState && ! States.deletionState && !States.nameNodeState)
            traverse(nodeNearby);

        if((States.breadthSearchHover || States.deepSearchHover) && !States.nameNodeState)
            States.searchState = !States.searchState;

        if(States.forDeletionHover && nodeNearby == null && !States.nameNodeState)
            States.deletionState = !States.deletionState;

        if(nodeNearby != null && !States.archCreationState && !States.deletionState && !States.nameNodeState){
            toLink[0] = nodeNearby;
            States.archCreationState = true;
        }

        if(States.deletionState && nodeNearby != null && !States.nameNodeState){
            deleteNode(nodeNearby.getNodeName());
            States.deletionState = false;
        }

        if((States.breadthTraversalHover || States.deepTraversalHover) && !States.nameNodeState){
            States.traverseState = true;
            if(States.breadthTraversalHover) States.breadthTraverseState = true;
            else States.deepTraverseState = true;
        }

        if(States.forCreation && nodeNearby == null)
            States.mouseClickedOnCanvas = true;

    }

    public void mouseReleased(){
        VNode nodeNearby = isNearbyNode();

        if(!States.nameNodeState && States.mouseClickedOnCanvas && mouseX > 325 && mouseX < width-325)
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
        if(States.searchState) inputReference.typeMessage();
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
                System.out.println(e);
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
