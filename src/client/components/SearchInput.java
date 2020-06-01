package client.components;

import client.States;
import processing.core.PApplet;

import java.util.ArrayList;

public class SearchInput {

    PApplet parent;
    static String message = "";
    ArrayList<VNode> nodeList;

    public SearchInput(PApplet reference, ArrayList<VNode> nodeList){
        parent = reference;
        this.nodeList = nodeList;
    }

    public void build(){
        inputLabel();
        inputArea();
    }

    public void inputLabel(){
        parent.pushStyle();
        parent.textAlign(parent.CENTER);
        parent.fill(0);
        parent.textSize(30);
        parent.text("Write searched node and press Enter", parent.width/2, 45);
        parent.popStyle();
    }

    public void inputArea(){
        parent.pushStyle();
        parent.noStroke();
        parent.fill(255, 255, 255);
        parent.rect((parent.width/2)-200, 65, 400, 50, 10);
        parent.popStyle();

        parent.pushStyle();
        parent.textAlign(parent.CENTER);
        parent.fill(0, 0, 0);
        parent.textSize(20);
        parent.text(message, (parent.width/2)+10, 95);
        parent.popStyle();
    }


    public void typeMessage(){
        if(parent.keyCode == parent.ENTER){
            try{
                States.searchState = false;
                VNode toFind = null;
                for (VNode node : nodeList){
                    if(node.getNodeName().equals(message)) toFind = node;
                }
                if(toFind != null){
                    toFind.found = true;
                }else{
                    System.out.println("No se encontró");
                }
            }catch (Exception e){
                System.out.println(e);
                States.invalidCreation = true;
            }
        }
        if(parent.keyCode == parent.BACKSPACE && message.length() > 0){
            message = message.substring(0, message.length()-1);
        }
        if(((parent.key >= 'a' && parent.key <= 'z') || (parent.key >= 'A' && parent.key <= 'Z')) && message.length() < 7){
            message += parent.key;
        }
    }

}
