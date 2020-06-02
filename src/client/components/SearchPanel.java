package client.components;

import client.States;
import processing.core.PApplet;

public class SearchPanel {

    PApplet parent;
    public static String traverseStr = "";

    public SearchPanel(PApplet reference){
        parent = reference;
    }

    public void build(){
        createPanel();
        sidePanelInstruction("Search Node", 75);
        deepSearchButton(false, parent.width-245, 100);
        breadthSearchButton(false, parent.width-245, 170);
        sidePanelInstruction("Traverse Node", 275);
        deepSearchButton(true, parent.width-245, 300);
        breadthSearchButton(true, parent.width-245, 370);
        traversePanel();

    }

    public void createPanel(){
        parent.pushStyle();
        parent.noStroke();
        parent.fill(360, 360, 360);
        parent.rect(parent.width-300,10,300, parent.height-20, 30, 0, 0, 30);
        parent.popStyle();
    }

    public void traversePanel(){
        parent.pushStyle();
        parent.noStroke();
        parent.fill(242, 242, 242);
        parent.rect(parent.width-245, 440, 190, 120, 10);
        parent.popStyle();

        parent.pushStyle();
        parent.fill(0, 0, 0);
        parent.textSize(12);
        parent.text(traverseStr, parent.width-230, 450, 180, 120);
        parent.popStyle();
    }

    private void sidePanelInstruction(String message, int yPos){
        parent.pushStyle();
        parent.textAlign(parent.CENTER);
        parent.fill(0);
        parent.textSize(18);
        parent.text(message, parent.width-150, yPos);
        parent.popStyle();
    }

    private void deepSearchButton(boolean traversal, int posX, int posY){
        parent.pushStyle();
        parent.noStroke();
        parent.fill(66, 135, 245);

        if(!traversal) States.deepSearchHover = false;
        else States.deepTraversalHover = false;

        if(isOverButton(posY, 50)){
            parent.stroke(65, 98, 150);
            if(!traversal) States.deepSearchHover = true;
            else States.deepTraversalHover = true;
        }
        parent.rect(posX, posY, 190, 50, 10);
        parent.popStyle();

        buttonText("Deep First", posY+30, 190);
    }

    private void breadthSearchButton(boolean traversal, int posX, int posY){
        parent.pushStyle();
        parent.noStroke();
        parent.fill(66, 135, 245);

        if(!traversal) States.breadthSearchHover = false;
        else States.breadthTraversalHover = false;

        if(isOverButton(posY, 50)){
            parent.stroke(65, 98, 150);
            if(!traversal) States.breadthSearchHover = true;
            else States.breadthTraversalHover = true;
        }
        parent.rect(posX, posY, 190, 50, 10);
        parent.popStyle();

        buttonText("Breadth First", posY+32, 200);
    }

    public void buttonText(String txt, int posY, int posX){
        parent.pushStyle();
        parent.fill(360, 360, 360);
        parent.textSize(16);
        parent.text(txt, parent.width-posX, posY);
        parent.popStyle();
    }

    private boolean isOverButton(int posY, int size){
        return parent.mouseX >= parent.width - 245 && parent.mouseX <= parent.width - 245 + 190 && parent.mouseY >= posY && parent.mouseY <= posY + size;
    }
}
