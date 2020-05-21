import processing.core.PApplet;

public class Client extends PApplet {

    public void settings(){
        size(800, 600);
    }

    public void setup(){

    }

    public void draw(){
        background(360,360,360);
        textSize(30);
        text("Funcionó bien bonito", 300, 300);
        fill(0, 0, 0);
    }

    public static void main(String[] args){
        PApplet.main("Client");
    }
}
