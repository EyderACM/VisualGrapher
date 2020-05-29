package client.components;

import client.States;
import processing.core.PApplet;

public class SidePanel {

    PApplet parent;

    public SidePanel(PApplet reference){
        parent = reference;
    }

    public void build(){
        createPanel();
        title();
        title();
        subtitle();

        divisor(135);
        createSideNode();
        sidePanelInstruction("Drag and drop the node", 190);
        divisor(310);
    }

    private void createPanel(){
        parent.pushStyle();
        parent.noStroke();
        parent.fill(360, 360, 360);
        parent.rect(-10,10,300, parent.height-20, 0, 30, 30, 0);
        parent.popStyle();
    }

    private void divisor(int yPos){
        parent.pushStyle();
        parent.noStroke();
        parent.fill(230, 230, 230);
        parent.rect(0, yPos,290, 3);
        parent.popStyle();
    }

    private void title(){
        parent.pushStyle();
        parent.textAlign(parent.CENTER);
        parent.fill(0);
        parent.textSize(40);
        parent.text("Visualgraph", 145, 75);
        parent.popStyle();
    }

    private void subtitle(){
        parent.pushStyle();
        parent.textAlign(parent.CENTER);
        parent.fill(0);
        parent.textSize(15);
        parent.text("By Eyder ACM", 88, 105);
        parent.popStyle();
    }

    private void sidePanelInstruction(String message, int yPos){
        parent.pushStyle();
        parent.textAlign(parent.CENTER);
        parent.fill(0);
        parent.textSize(18);
        parent.text(message, 145, yPos);
        parent.popStyle();
    }

    private void createSideNode(){
        parent.pushStyle();
        parent.fill(66, 237, 240);
        parent.noStroke();
        States.forCreation = false;
        int ellipseX = 145, ellipseY = 250;
        if(parent.dist(parent.mouseX, parent.mouseY, ellipseX, ellipseY) < 60){
            parent.stroke(125, 177, 255);
            States.forCreation = true;
        }
        parent.ellipse(ellipseX, ellipseY, 60, 60);
        parent.popStyle();
    }
}
