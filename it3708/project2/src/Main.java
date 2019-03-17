import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.io.File;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        MOEA moea = new MOEA("./176035/Test image.jpg",
                100, 50,
                0.5, 0.5, 0.1,
                1, 1, 1,
                8, 30, 2, 35, 1000, false, true);
        moea.run();
        drawImage(moea.slic, moea);
        System.out.println("Done");

        primaryStage.close();
    }


    private void drawImage(SLIC slic, MOEA moea) {

        final WritableImage image = new WritableImage(slic.imageWidth, slic.imageHeight);
        final PixelWriter pixelWriter = image.getPixelWriter();

        for (SuperPixel sp : slic.superPixels) {
            for (Pixel pixel : sp.pixels) {
                pixelWriter.setArgb(pixel.x, pixel.y, sp.getColor().getRGB());
            }

        }
        /*
        for (int y = 0; y < slic.imageHeight; y++) {
            for (int x = 0; x < slic.imageWidth; x++) {
                pixelWriter.setArgb(x, y, slic.superPixels.get(slic.label[y][x]).getColor().getRGB());
            }
        }
        */
        File testFile = new File("test.png");

        try {
            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", testFile);
        } catch (Exception e) {
            e.printStackTrace();
        }


        final WritableImage image2 = new WritableImage(slic.imageWidth, slic.imageHeight);
        final PixelWriter pixelWriter2 = image2.getPixelWriter();

        for (int y = 0; y < slic.imageHeight; y++) {
            for (int x = 0; x < slic.imageWidth; x++) {
                try {
                    pixelWriter2.setArgb(x, y, moea.population[moea.population.length - 1].visitedPixels.get(slic.superPixels.get(slic.label[y][x])).getArgb());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        File testFile2 = new File("test2.png");

        try {
            ImageIO.write(SwingFXUtils.fromFXImage(image2, null), "png", testFile2);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
