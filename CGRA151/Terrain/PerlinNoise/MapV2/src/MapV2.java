import processing.core.PApplet;

/**
 * Possible Idea: Use a Triangle Strips ( a bunch of vertexs) to create a mesh
 *
 * Rounding issue atm, drawing the width and height of the rects
 *
 */
public class MapV2 extends PApplet {

    private PerlinNoise perlinNoise;



    public void setup() {
        frameRate(1);
        background(255);
        noLoop();
        perlinNoise = new PerlinNoise(this,width,height);

    }
    private int count = 0;
    private boolean odd = true;
    public void draw() {
        background(0);
        //perlinNoise = new PerlinNoise(this,width,height);
        //perlinNoise.perlinTest();
        //perlinNoise.drawMap();

        if(count >6) count =0;
        if(odd){
            perlinNoise.drawOctave(count);
            odd = false;
        } else{
            perlinNoise.boxBlur(count);
            odd = true;
            count++;
        }

    }

    public void debugging(){ text("("+mouseX+","+mouseY+")",mouseX,mouseY); }


    public void mousePressed(){
        loop();
    }
    public void mouseReleased(){
        noLoop();
    }
    public void settings() {
        //Window Width must equal the Window height.
        size(512, 512,P2D);
    } //Windows Size should be a multiply of 2^n
    public static void main(String[] args) {
        PApplet.main(new String[]{"MapV2"});
    }
}
