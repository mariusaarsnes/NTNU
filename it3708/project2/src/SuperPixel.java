import java.awt.*;
import java.util.ArrayList;

class SuperPixel {

    ImageParser imageParser;
    int id, x, y;
    int alphaTotal, redTotal, greenTotal, blueTotal, pixelCount;
    ArrayList<Integer> neighbours;
    ArrayList<SuperPixelEdge> edges;

    SuperPixel(ImageParser imageParser, int id, int y, int x, int pixelCount) {
        this.imageParser = imageParser;
        this.id = id;
        this.y = y;
        this.x = x;
        this.pixelCount = pixelCount;
        this.neighbours = new ArrayList<>();
        this.edges = new ArrayList<>();
        resetColor();
    }

    SuperPixel(ImageParser imageParser, int id, int y, int x) {
        this.imageParser = imageParser;
        this.id = id;
        this.y = y;
        this.x = x;
        this.pixelCount = 1;
        this.neighbours = new ArrayList<>();
        this.edges = new ArrayList<>();
        resetColor();
    }

    void moveToLowestGradient() {
        int lowestGradient = Integer.MAX_VALUE;
        int bestY = 0, bestX = 0;
        for (int yy = this.y - 1; yy < this.y + 1; yy++) {
            for (int xx = this.x - 1; xx < this.x + 1; xx++) {
                if (yy == 0 || yy == this.imageParser.getHeight() - 1 || xx == 0 || xx == this.imageParser.getWidth() - 1) {
                    continue;
                }
                int tempGradient = getGradient(yy, xx);
                if (tempGradient < lowestGradient) {
                    lowestGradient = tempGradient;
                    bestY = yy;
                    bestX = xx;
                }
            }
        }
        this.y = bestY;
        this.x = bestX;
        resetColor();
    }

    private int getGradient(int y, int x) {
        return (int) Math.round(Math.sqrt(this.imageParser.getArgbDistance(y, x + 1, y, x - 1))) +
                (int) Math.round(Math.sqrt(this.imageParser.getArgbDistance(y + 1, x, y - 1, x)));
    }

    public boolean addNeighbour(int neighbour) {
        if (this.neighbours.contains(neighbour)) {
            return false;
        }
        this.neighbours.add(neighbour);
        return true;

    }

    public void addEdge(SuperPixel V, double distance) {
        this.edges.add(new SuperPixelEdge(this, V, distance));
    }

    private void resetColor() {
        this.alphaTotal = 0;
        this.redTotal = 0;
        this.greenTotal = 0;
        this.blueTotal = 0;

    }

    public void updateColor(int y, int x) {
        Color color = this.imageParser.getPixelColor(y, x);
        this.alphaTotal += color.getAlpha();
        this.redTotal += color.getRed();
        this.greenTotal += color.getGreen();
        this.blueTotal += color.getBlue();
    }


    public Color getColor() {
        try {
            return new Color(this.redTotal / this.pixelCount, this.greenTotal / this.pixelCount, this.blueTotal / this.pixelCount, this.alphaTotal / this.pixelCount);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void resetCenter(int y, int x) {
        this.y = y;
        this.x = x;

    }
}
