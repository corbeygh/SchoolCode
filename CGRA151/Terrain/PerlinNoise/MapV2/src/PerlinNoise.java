import processing.core.PApplet;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/** I am attempting to generate terrain by using Perlin Noise
 * This is done by sampling multiple different frequencies of noise
 * I save these samples in an Array and apply a Gaussian blur to smooth out the hard edges of the rectangles (I may change the shapes drawn to pentagons)
 *
 */
public class PerlinNoise {
    private PApplet app;
    private int gridWidth;
    private int gridHeight;
    private int cols;
    private int rows;
    //You can change noiseSampling to 2,4,8,16,32,64,128,256,512. Max = window size.
    private int noiseSampling = 2; //Amount of noise samples. (2^n)
/** noiseSampling with only 1 pixel will cause the gaussianBlurWeight to be too large and the Application will not be able to handle it.
 * Increasing the amount of noiseSampling decreases the cost of and time taken to blur the image
 * This will also make the Noise alot more dense as your drawing alot more Shapes in the given window space.
 *
 */

    private float[][] finalFrame; //This is the final Frame consisting of all the octaves in one.
    private List<Octave> octaves = new ArrayList<>();


    private int gaussianBlurWeight; //Gaussian blur, used to average every pixel in the sampled noise kernel.
    private Block [][]blocks;
    private float[][] noise;

    public PerlinNoise(PApplet thisApplet, int x, int y){
        this.app = thisApplet;
        this.gridWidth = x;
        this.gridHeight = y;
        finalFrame = new float[gridWidth][gridHeight];
        initOctaves();

        this.gaussianBlurWeight = 128/noiseSampling;
        this.noiseSampling = gridWidth/noiseSampling;
        this.cols = this.gridWidth/ noiseSampling;
        this.rows = this.gridHeight/ noiseSampling;
        //blocks = new Block[cols][rows];

        noise = new float[cols][rows];
        generateWhiteNoise();
        //Smooths noise:
        //generateSmoothNoise();
    }

    public void generateWhiteNoise() {
        Random random = new Random();
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < cols; j++)
                noise[i][j] = (float) random.nextDouble() % 1;
    }

    public void drawOctave(int i){
        octaves.get(i).drawMe();
    }

    public void boxBlur(int i){
        octaves.get(i).boxBlur();
        drawOctave(i);
    }


    public void perlinTest(){
        float persistence = .4f; //Persistence, the ratio of one octave to the next octave
            // frequency = (float) Math.pow(2f,i)/128f;
            //float amplitude = (float) Math.pow(i,persistence)/128f; //Amount of the sample added to the picture, dependent on the Persistence
            //For example if the amplitude was very low your sampling points which are very far apart.

            //Finally we combine the Octave to the total
            //I am using linear interpolation, however cosine interpolation would be better. - It would produce more of a smoother final picture when adding the octaves together.

        //Copy all of the pixels from first octave:
        for (int i = 0; i < gridHeight; i++)
            for (int j = 0; j < gridWidth; j++)
                finalFrame[i][j] = octaves.get(0).getPixel(i,j);

        //Now we use Linear Interpolation to merge all the octaves into one single frame.

        for(int k = 1; k <octaves.size(); k++){//This for Loop makes frequency and amplitude dependent on the octave number.
            for (int i = 0; i < gridHeight; i++) {
                for (int j = 0; j < gridWidth; j++) {
                    float octavePixel = octaves.get(k).getPixel(i,j);
                    finalFrame[i][j] = lerp(finalFrame[i][j], octavePixel, (persistence/i));
                }
            }
        }
    }

    /**
     *The lerp function uses linear Interpolation to average two pixels with a ratio of 1:0.4.
     * The strength of the first pixel (a) is greater than the 2nd (b).
     */
    private float lerp(float finalFrame, float octave, float persistence){ return (finalFrame *(1.0f-persistence)) + (octave*persistence);}


    public void drawMap(){

        for (int i = 0; i < rows; i++)
            for (int j = 0; j < cols; j++) {
                app.noStroke();
                float colorShade = (float)255*noise[i][j];
                app.fill(colorShade);
                app.rect(i* noiseSampling,j* noiseSampling, noiseSampling, noiseSampling);
            }
        double timer = System.currentTimeMillis();
        app.filter(app.BLUR, 64);
        System.out.println((System.currentTimeMillis()-timer)/1000);
    }

    private void initOctaves(){
        int i = 4;
        while( i< gridWidth){
            octaves.add(new Octave(app,gridWidth,i));
            i = i*2;
        }
    }

    private int randomNumber(float variable1, float variable2) {
        float temp = app.random(variable1, variable2);
        int rounded = Math.round(temp);
        return rounded;
    }
}
