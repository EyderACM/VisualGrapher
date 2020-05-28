package client.components;

import processing.core.PApplet;
import schema.Node;

public class VNode extends Node {

    private float xPos, yPos, height, width;
    private PApplet parent;

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
        parent.fill(66, 237, 240);
        parent.noStroke();
        parent.ellipse(xPos, yPos, height, width);

        parent.pushStyle();
        parent.textAlign(PApplet.CENTER);
        parent.fill(0, 0, 0);
        parent.text(getNodeName(), xPos, yPos+10);
        parent.textSize(2);
        parent.popStyle();
    }
}
