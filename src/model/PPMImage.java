package model;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.Scanner;

/**
 * A class to represent an image with a ".ppm" filetype. A PPM image is constructed through a
 * 2-dimensional array of IPixels where each pixel has 3 color channels RGB. Each image can be
 * either visible or transparent.
 */
public class PPMImage implements IImage {

  private final IPixel[][] pixels;
  private String name;
  private boolean visible;

  /**
   * Constructor to create a PPMImage based on a file being imported into the program.
   *
   * @param file the ppm image file represented as a string
   * @throws IllegalArgumentException if the file is null
   */
  public PPMImage(String file) throws IllegalArgumentException {
    if (file == null) {
      throw new IllegalArgumentException("Filename cannot be null.");
    }
    Scanner sc = new Scanner(file);

    String token = sc.next();
    int width = sc.nextInt();
    int height = sc.nextInt();
    int maxValue = sc.nextInt();
    IPixel[][] pixels = new IPixel[width][height];
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int r = sc.nextInt();
        int g = sc.nextInt();
        int b = sc.nextInt();
        IPixel pixel = new Pixel(new Posn(j, i), r, g, b);
        pixels[j][i] = pixel;
      }
    }
    sc.close();
    this.pixels = pixels;
    this.name = null;
    this.visible = true;
  }

  /**
   * Constructor to create a PPMImage based on a 2D array of IPixels programmatically created from
   * scratch.
   *
   * @param pixels the 2D array of pixels
   * @throws IllegalArgumentException if the 2D array of IPixels is null
   */
  public PPMImage(IPixel[][] pixels) throws IllegalArgumentException {
    if (pixels == null) {
      throw new IllegalArgumentException("Image pixels cannot be null.");
    }
    this.pixels = pixels;
    this.name = null;
    this.visible = true;
  }

  /**
   * Default constructor for a PPM image.
   */
  public PPMImage() {
    this.pixels = null;
    this.name = "";
    this.visible = false;
  }

  /**
   * Programmatically creates and constructs a PPM format checkerboard image. Can be any positive
   * number of tiles in the checkerboard.
   *
   * @param tileSize number of pixels length wise of a single tile (must be positive)
   * @param numTiles number of tiles across a single row of the checkerboard image (must be
   *                 positive)
   * @param color1   the first color to be used in this checkerboard image
   * @param color2   the second color to be used in this checkerboard image
   * @throws IllegalArgumentException when tileSize or numTiles is not positive or when either color
   *                                  object given is null
   */
  public PPMImage(int tileSize, int numTiles, Color color1, Color color2)
      throws IllegalArgumentException {
    if (tileSize < 1 || numTiles < 1) {
      throw new IllegalArgumentException("Size of checkerboard must be positive.");
    }
    if (color1 == null || color2 == null) {
      throw new IllegalArgumentException("Colors must not be null.");
    }
    int boardSize = tileSize * numTiles;
    IPixel[][] newPixels = new IPixel[boardSize][boardSize];
    int tile1R = color1.getRed();
    int tile1G = color1.getGreen();
    int tile1B = color1.getBlue();
    int tile2R = color2.getRed();
    int tile2G = color2.getGreen();
    int tile2B = color2.getBlue();
    // to switch colors after every full row of this tile is completed
    int colorIndicator = 0;
    for (int i = 0; i < boardSize; i++) {
      for (int j = 0; j < boardSize; j++) {
        // checks if this checkerboard has even or odd tile dimensions
        if (numTiles % 2 != 0) {
          // effectively switches colors when numTiles is odd
          if ((j % tileSize == 0 && j != 0) || (i % tileSize == 0 && j % tileSize == 0)) {
            colorIndicator++;
          }
        } else {
          if (j % tileSize == 0 && !(j == 0 && i % tileSize == 0)) {
            colorIndicator++;
          }
        }
        IPixel pixel;
        if (colorIndicator % 2 == 0) {
          pixel = new Pixel(new Posn(j, i), tile1R, tile1G, tile1B);
        } else {
          pixel = new Pixel(new Posn(j, i), tile2R, tile2G, tile2B);
        }
        newPixels[j][i] = pixel;
      }
    }
    this.pixels = newPixels;
    this.name = null;
    this.visible = true;
  }

  @Override
  public IImage applyTransformation(ITransform transform, KernelMatrix kernel, Channel color)
      throws IllegalArgumentException {
    if (transform == null || kernel == null || color == null) {
      throw new IllegalArgumentException("Fields cannot be null.");
    }
    return transform.apply(this, kernel, color);
  }

  @Override
  public IImage applyTransformation(ITransform transform, KernelMatrix kernel)
      throws IllegalArgumentException {
    if (transform == null || kernel == null) {
      throw new IllegalArgumentException("Transform object or kernel cannot be null.");
    }
    if (transform instanceof Filter) {
      IImage newImage = this.applyTransformation(transform, kernel, Channel.RED);
      IImage newImageV2 = newImage.applyTransformation(transform, kernel, Channel.GREEN);
      return newImageV2.applyTransformation(transform, kernel, Channel.BLUE);
    } else {

      // do this when transform is a color transformation since channel does not matter and we only
      // need to apply the transformation once
      return this.applyTransformation(transform, kernel, Channel.RED);
    }
  }

  @Override
  public KernelMatrix findNeighbors(IPixel pixel, Channel color, int size)
      throws IllegalArgumentException {
    if (pixel == null || color == null) {
      throw new IllegalArgumentException("Pixel and/or color cannot be null.");
    }
    Posn position = pixel.getPosition();
    int middleX = position.getX();
    int middleY = position.getY();
    KernelMatrix neighborsMatrix = new KernelMatrix(size);
    int radius = size / 2;

    // iterates through all values in 2D array
    for (int i = radius * -1; i <= radius; i++) {
      for (int j = radius * -1; j <= radius; j++) {
        try {
          IPixel neighborPixel = this.pixels[middleX + j][middleY + i];
          double value;
          switch (color) {
            case RED:
              value = neighborPixel.getR();
              break;
            case GREEN:
              value = neighborPixel.getG();
              break;
            case BLUE:
              value = neighborPixel.getB();
              break;
            default:
              throw new IllegalArgumentException("Must be a valid 8-bit color channel.");
          }
          neighborsMatrix.setValue(new Posn(j + size / 2, i + size / 2), value);
        } catch (IndexOutOfBoundsException e) {
          // left intentionally blank to explicitly do nothing. KernelMatrix will be left as 0.0
          // which is what we want in the case of no neighbor being present.
        }
      }
    }
    return neighborsMatrix;
  }

  @Override
  public IPixel[][] getPixels() {
    return this.pixels;
  }

  @Override
  public IImage getImage(IPixel[][] pixels) {
    return new PPMImage(pixels);
  }

  @Override
  public String sendToString() {
    StringBuilder stringOutput = new StringBuilder();

    if (this.pixels != null) {
      int width = this.pixels.length;
      int height = this.pixels[0].length;

      stringOutput.append("P3 \n");
      stringOutput.append("# Created by Christopher Burke and Jonathan Truong \n");
      stringOutput.append(width).append(" ");
      stringOutput.append(height).append(" \n");
      stringOutput.append("255 \n");

      for (int i = 0; i < height; i++) {
        for (IPixel[] iPixels : this.pixels) {
          IPixel pixel = iPixels[i];
          stringOutput.append(pixel.getR()).append(" \n");
          stringOutput.append(pixel.getG()).append(" \n");
          stringOutput.append(pixel.getB()).append(" \n");
        }
      }
    }
    return stringOutput.toString();
  }

  @Override
  public boolean getVisibility() {
    return this.visible;
  }

  @Override
  public void setVisibility(boolean newVisibility) {
    if (visible != newVisibility) {
      this.visible = newVisibility;
    }
  }

  @Override
  public String getName() {
    return this.name;
  }

  @Override
  public void setName(String newName) throws IllegalArgumentException {
    if (newName == null) {
      throw new IllegalArgumentException("New name can't be null.");
    }
    this.name = newName;
  }

  @Override
  public BufferedImage convertImage() {
    BufferedImage newImage;
    IPixel[][] pixels = this.getPixels();
    int imageWidth = pixels.length;
    int imageHeight = pixels[0].length;
    newImage = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);

    for (int i = 0; i < imageHeight; i++) {
      for (int j = 0; j < imageWidth; j++) {
        IPixel p = pixels[j][i];
        int r = p.getR();
        int g = p.getG();
        int b = p.getB();
        int rgb = new Color(r, g, b).getRGB();
        newImage.setRGB(j, i, rgb);
      }
    }
    return newImage;
  }
}
