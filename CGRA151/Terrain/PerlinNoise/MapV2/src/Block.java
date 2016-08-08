import processing.core.PApplet;

/**
 * Created by Calvin on 3/08/2016.
 */
public class Block {

    private PApplet app;
    private String pattern;
    private int blockWidth = 5;
    private int blockHeight = 5;
    private int patternID;

    public Block(PApplet thisApplet) {
        this.app = thisApplet;
        this.patternID = randomNumber(1,5);
        this.pattern = generateBlock();
    }

    public void drawMe(int startX, int startY) {
        setPattern();
        app.rect(startX, startX, blockWidth, blockHeight);

    }

    public void setPattern() {
        if (pattern.contains("land")) {
            if (pattern.contains("land") && patternID == 1)
                app.fill(0, 51, 0);
            else if(pattern.contains("land")&& patternID == 2)
                app.fill(0, 80, 0);
            else if(pattern.contains("land")&& patternID == 3)
                app.fill(25, 51, 0);
            else if(pattern.contains("land")&& patternID == 4)
                app.fill(51, 51, 0);
            else if(pattern.contains("land")&& patternID == 5)
                app.fill(80, 80, 0);
        } else if (pattern.contains("sea")) {
            if(pattern.equals("sea")&& patternID == 1)
                app.fill(0, 0, 51);
            else if(pattern.contains("sea")&& patternID == 2)
                app.fill(0, 0, 80);
            else if(pattern.contains("sea")&& patternID == 3)
                app.fill(0, 25, 51);
            else if(pattern.contains("sea")&& patternID == 4)
                app.fill(0, 51, 51);
            else if(pattern.contains("sea")&& patternID == 5)
                app.fill(0, 80, 80);
        }
    }

    public String generateBlock(){
        String blockType = "land";



        return blockType;
    }

    private int randomNumber(float variable1, float variable2) {
        float temp = app.random(variable1, variable2);
        int rounded = Math.round(temp);
        return rounded;
    }
}
