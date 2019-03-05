import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;

public class ImageParser {
    private int height;
    private int width;
    private int numPixels;
    private int[][] pixelArgb;
    private Color[][] pixelColor;

    public ImageParser(String fileName) throws IOException {

        BufferedImage image = ImageIO.read(new File(fileName));
        this.height = image.getHeight();
        this.width = image.getWidth();
        this.numPixels = this.height * this.width;
        System.out.println("\tStarting to read in new imageParser");
        this.pixelColor = new Color[this.height][this.width];
        this.pixelArgb = new int[this.height][this.width];
        parseImage(image);
        System.out.println("\tFinished reading in new imageParser");
    }

    private void parseImage(BufferedImage image) {
        byte[] pixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        int height = image.getHeight();
        int width = image.getWidth();
        boolean hasAlphaChannel = image.getAlphaRaster() != null;
        
        if (hasAlphaChannel) {
            final int pixelLength = 4;
            for (int pixel = 0, row = 0, col = 0; pixel < pixels.length; pixel += pixelLength) {
                int argb = 0;
                argb += (((int) pixels[pixel] & 0xff) << 24); // alpha
                argb += ((int) pixels[pixel + 1] & 0xff); // blue
                argb += (((int) pixels[pixel + 2] & 0xff) << 8); // green
                argb += (((int) pixels[pixel + 3] & 0xff) << 16); // red
                this.pixelArgb[row][col] = argb;
                this.pixelColor[row][col] = new Color(argb);
                col++;
                if (col == width) {
                    col = 0;
                    row++;
                }
            }
        } else {
            final int pixelLength = 3;
            for (int pixel = 0, row = 0, col = 0; pixel < pixels.length; pixel += pixelLength) {
                int argb = 0;
                argb += -16777216; // 255 alpha
                argb += ((int) pixels[pixel] & 0xff); // blue
                argb += (((int) pixels[pixel + 1] & 0xff) << 8); // green
                argb += (((int) pixels[pixel + 2] & 0xff) << 16); // red
                this.pixelArgb[row][col] = argb;
                this.pixelColor[row][col] = new Color(argb);
                col++;
                if (col == width) {
                    col = 0;
                    row++;
                }
            }
        }
    }


    public int getHeight() {
        return this.height;
    }

    public int getWidth() {
        return this.width;
    }

    public int getNumPixels() {
        return this.numPixels;
    }

    public int[][] getPixelArgb() {
        return pixelArgb;
    }

    public int getPixelArgb(int y, int x) {
        return this.pixelArgb[y][x];
    }

    public Color[][] getPixelColor() {
        return this.pixelColor;
    }

    public Color getPixelColor(int y, int x) {
        return this.pixelColor[y][x];
    }

    public double getArgbDistance(int y1, int x1, int y2, int x2) {
        Color color1 = this.getPixelColor(y1, x1), color2 = this.getPixelColor(y2, x2);
        return (color1.getAlpha() - color2.getAlpha()) * (color1.getAlpha() - color2.getAlpha()) +
                (color1.getBlue() - color2.getBlue()) * (color1.getBlue() - color2.getBlue()) +
                (color1.getRed() - color2.getRed()) * (color1.getRed() - color2.getRed()) +
                (color1.getGreen() - color2.getGreen()) * (color1.getGreen() - color2.getGreen());
    }
}
