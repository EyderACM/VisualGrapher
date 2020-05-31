package client.components;

import client.States;
import processing.core.PApplet;
import schema.Node;

public class VNode extends Node {

    public float xPos, yPos, height, width;
    private PApplet parent;
    public boolean stroked = true;

    public VNode(Object data, String nodeName, float xPos, float yPos, float height, float width, PApplet parent) {
        super(data, nodeName);
        this.xPos = xPos;
        this.yPos = yPos;
        this.height = height;
        this.width = width;
        this.parent = parent;
    }

    public VNode(String nodeName, float xPos, float yPos, float height, float width, PApplet parent){
        super(nodeName, nodeName);
        this.xPos = xPos;
        this.yPos = yPos;
        this.height = height;
        this.width = width;
        this.parent = parent;
    }

    public void display(){
        parent.pushStyle();
        parent.fill(66, 237, 240);
        parent.noStroke();
        if(stroked) parent.stroke(125, 177, 255);
        if(States.deletionState) parent.stroke(255, 103, 92);
        Double increment = this.getNodeName().length() <= 1 ? 0 : this.getNodeName().length()*12.8;
        parent.ellipse(xPos, yPos, height+increment.floatValue(), width+increment.floatValue());

        parent.textAlign(PApplet.CENTER);
        parent.fill(360, 360, 360);
        parent.text(getNodeName(), xPos, yPos+10);
        parent.textSize(1);
        parent.popStyle();
    }

    public void onHover(){
        if(parent.dist(parent.mouseX, parent.mouseY, xPos, yPos) < height){
            parent.pushStyle();
            parent.stroke(125, 177, 255);
            parent.strokeWeight(2);
            parent.noFill();
            Double increment = this.getNodeName().length() <= 1 ? 0 : this.getNodeName().length()*12.8;
            parent.ellipse(xPos, yPos, height+increment.floatValue()+7, width+increment.floatValue()+7);
            parent.popStyle();
        }
    }
}
