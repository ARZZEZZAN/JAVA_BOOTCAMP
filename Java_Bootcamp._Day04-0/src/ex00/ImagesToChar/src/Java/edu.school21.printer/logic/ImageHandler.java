package edu.school21.printer.logic;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;

public class ImageHandler {
    private char white;
    private char black;
    private BufferedImage bufferedImage;

    public ImageHandler(char white, char black, Path path) throws IOException {
        this.white = white;
        this.black = black;
        bufferedImage = ImageIO.read(path.toFile());
    }
    public void printImage() {
        int weight = bufferedImage.getWidth();
        int height = bufferedImage.getHeight();
        for(int i = 0; i < height; i++) {
            for(int j = 0; j < weight; j++) {
                Color color = new Color(bufferedImage.getRGB(j, i));
                if(color.equals(Color.WHITE)) {
                    System.out.print(white);
                } else if(color.equals(Color.BLACK)) {
                    System.out.print(black);
                }
            }
            System.out.println();
        }
    }
}
