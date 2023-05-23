package edu.school21.printer.logic;

import com.diogonunes.jcdp.color.ColoredPrinter;
import com.diogonunes.jcdp.color.api.Ansi;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;

public class ImageHandler {
    private Args jargs;
    private BufferedImage bufferedImage;

    public ImageHandler(Args jargs, Path path) throws IOException {
        this.jargs = jargs;
        bufferedImage = ImageIO.read(path.toFile());
    }
    public void printImage() {
        int weight = bufferedImage.getWidth();
        int height = bufferedImage.getHeight();
        ColoredPrinter cp = new ColoredPrinter();
        for(int i = 0; i < height; i++) {
            for(int j = 0; j < weight; j++) {
                int pixel = bufferedImage.getRGB(j, i);
                if(pixel == Color.WHITE.getRGB()) {
                    cp.print(" ", Ansi.Attribute.NONE, Ansi.FColor.NONE, Ansi.BColor.valueOf(jargs.getColor1()));
                } else if(pixel == Color.BLACK.getRGB()) {
                    cp.print(" ", Ansi.Attribute.NONE, Ansi.FColor.NONE, Ansi.BColor.valueOf(jargs.getColor2()));
                }
            }
            System.out.println();
        }
    }
}
