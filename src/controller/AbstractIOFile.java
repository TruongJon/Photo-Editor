package controller;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import model.IImage;
import model.IPixel;

/**
 * Class to represent IO functions for JPEG and PNG files.
 */
public class AbstractIOFile implements IOFile {

  @Override
  public String readImage(String filename) {
    BufferedImage input = null;
    StringBuilder fileString = new StringBuilder();
    fileString.append("P3\n");
    try {
      input = ImageIO.read(new File(filename));
    } catch (IOException e) {
      System.err.println("Could not read in script file.");
    }
    fileString.append(input.getWidth() + " " + input.getHeight() + "\n");
    fileString.append("255\n");
    for (int i = 0; i < input.getHeight(); i++) {
      for (int j = 0; j < input.getWidth(); j++) {
        Color c = new Color(input.getRGB(j, i));
        int red = c.getRed();
        int green = c.getGreen();
        int blue = c.getBlue();
        fileString.append(red).append("\n");
        fileString.append(green).append("\n");
        fileString.append(blue).append("\n");
      }
    }
    return fileString.toString();
  }

  @Override
  public void exportImage(IImage originalImage, String fileString, String fileName,
      FileType fileType, int width, int height) {
    BufferedImage image;

    try {
      IPixel[][] pixels = originalImage.getPixels();

      image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
      File exportFile = new File(fileName);

      for (int i = 0; i < height; i++) {
        for (int j = 0; j < width; j++) {
          IPixel p = pixels[j][i];
          int r = p.getR();
          int g = p.getG();
          int b = p.getB();
          int rgb = new Color(r, g, b).getRGB();
          image.setRGB(j, i, rgb);
        }
      }

      if (fileType == FileType.PNG) {
        ImageIO.write(image, "png", exportFile);
      } else {
        ImageIO.write(image, "jpeg", exportFile);
      }
    } catch (IOException e) {
      System.err.println("Could not export image properly.");
    }
  }
}
