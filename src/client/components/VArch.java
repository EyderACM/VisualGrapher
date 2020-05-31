package client.components;

import processing.core.PApplet;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class VArch {

    PApplet parent;
    public float originX, originY, destinationX, destinationY;
    public Set nodesName;

    public VArch(float originX, float originY, float destinationX, float destinationY, String nodeOne, String nodeTwo, PApplet reference){
        parent = reference;
        this.originX = originX;
        this.originY = originY;
        this.destinationX = destinationX;
        this.destinationY = destinationY;
        nodesName = new HashSet<>();
        nodesName.add(nodeOne);
        nodesName.add(nodeTwo);
    }

    public void display(){
        parent.pushStyle();
        parent.stroke(66, 237, 240);
        parent.strokeWeight(5);
        parent.line(originX, originY, destinationX, destinationY);
        parent.popStyle();
    }

}
