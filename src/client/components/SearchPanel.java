package client.components;

import client.States;
import processing.core.PApplet;

public class SearchPanel {

    PApplet parent;

    public SearchPanel(PApplet reference){
        parent = reference;
    }

    public void build(){
        createPanel();
        sidePanelInstruction("Search Node", 75);
        deepSearchButton();
        breadthSearchButton();
    }

    public void createPanel(){
        parent.pushStyle();
        parent.noStroke();
        parent.fill(360, 360, 360);
        parent.rect(parent.width-300,10,300, parent.height-20, 30, 0, 0, 30);
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

    private void deepSearchButton(){
        parent.pushStyle();
        parent.noStroke();
        parent.fill(66, 135, 245);
        States.deepSearchHover = false;
        if(isOverDeepSearchButton()){
            parent.stroke(65, 98, 150);
            States.deepSearchHover = true;
        }
        parent.rect(parent.width-245, 100, 190, 50, 10);
        parent.popStyle();

        parent.pushStyle();
        parent.fill(360, 360, 360);
        parent.textSize(16);
        parent.text("Deep First", parent.width-190, 130);
        parent.popStyle();
    }

    private void breadthSearchButton(){
        parent.pushStyle();
        parent.noStroke();
        parent.fill(66, 135, 245);
        States.breadthSearchHover = false;
        if(isOverBreadthSearchButton()){
            parent.stroke(65, 98, 150);
            States.breadthSearchHover = true;
        }
        parent.rect(parent.width-245, 170, 190, 50, 10);
        parent.popStyle();

        parent.pushStyle();
        parent.fill(360, 360, 360);
        parent.textSize(16);
        parent.text("Breadth First", parent.width-200, 202);
        parent.popStyle();
    }

    private boolean isOverDeepSearchButton(){
        return parent.mouseX >= parent.width - 245 && parent.mouseX <= parent.width - 245 + 190 && parent.mouseY >= 100 && parent.mouseY <= 150;
    }

    private boolean isOverBreadthSearchButton(){
        return parent.mouseX >= parent.width - 245 && parent.mouseX <= parent.width - 245 + 190 && parent.mouseY >= 170 && parent.mouseY <= 220;
    }
}
