import processing.core.PApplet;

import java.util.Random;

/**
 * Created by Calvin on 8/08/2016.
 */
public class Octave {
    PApplet app;

    private int windowSize;
    private int totalPixels;
    float noise[][];

    public Octave(PApplet thisApplet, float gridWidth, int totalPixels) {
        this.app = thisApplet;
        this.totalPixels = totalPixels;
        this.windowSize = Math.round(gridWidth);
        noise = new float[windowSize][windowSize];
        generateWhiteNoise();
        //smoothNoise();
    }

    public void generateWhiteNoise() {
        Random random = new Random();
        int multiplies = (windowSize/(totalPixels/2));
        app.println("multiples = "+multiplies);

        int amountOfPixelPerRect = (windowSize/multiplies);
        app.println("amount = "+amountOfPixelPerRect);
        //app.println(noise.length);
        //app.println(totalPixels);
        //app.println(totalPixels/2);
        int count = 0;
        for (int i = 0; i < (totalPixels/2); i++) {
            for (int j = 0; j < (totalPixels / 2); j++) {
                float randomNoise = (float) random.nextDouble() % 1;
                for(int l = 0; l<multiplies; l++)
                for (int k = 0; k < multiplies; k++) {
                    noise[l+(i*multiplies)][k+(j*multiplies)] = randomNoise;
                    count++;
                }
            }
        }
        app.println(count);
//        for (int i = 0; i < (noise.length); i++)
//            for (int j = 0; j < (noise.length); j++)
//                app.println(noise[i][j]);
    }

    public void drawMe(){
        for (int i = 0; i < noise.length; i++)
            for (int j = 0; j < noise.length; j++) {
                app.noStroke();
                float colorShade = (float)255*noise[i][j];
                app.stroke(colorShade);
                app.point(i,j);
            }
        //double timer = System.currentTimeMillis();
        //System.out.println((System.currentTimeMillis()-timer)/1000);
    }




    public float getPixel(int i, int j){ return noise[i][j]; }


    public void boxBlur(){
        float w = 1.0f/9.0f;
        float[] kernel ={
                w,w,w,
                w,w,w,
                w,w,w
        };
        float pixelAvg;
        float pixelTotal;
        //Note this method doesn't account for edges on the window....
        for (int i = 1; i < noise.length-1; i++)
            for (int j = 1; j < noise.length-1; j++) {
                pixelAvg = 0.0f;
                pixelTotal = 0.0f;
                for(int k = 0; k< 3; k++){
                    for(int h = 0; h<3; h++)
                    pixelTotal += noise[i-1 +k][j-1+h];
                }
                pixelAvg = pixelTotal/9;
                noise[i][j] = pixelAvg;
            }
    }
}
