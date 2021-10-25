package controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Objects;
import java.util.Scanner;
import model.IImage;

/**
 * Class to represent IO functions for PPM files.
 */
public class IOPPMFile implements IOFile {

  @Override
  public String readImage(String filename) {
    Scanner sc;

    try {
      sc = new Scanner(new FileInputStream(filename));
    } catch (FileNotFoundException e) {
      System.out.println("File " + filename + " not found!");
      return null;
    }
    StringBuilder builder = new StringBuilder();
    //read the file line by line, and populate a string. This will throw away any comment lines
    while (sc.hasNextLine()) {
      String s = sc.nextLine();
      if (s.charAt(0) != '#') {
        builder.append(s + System.lineSeparator());
      }
    }

    //now set up the scanner to read from the string we just built
    sc = new Scanner(builder.toString());

    String token;

    token = sc.next();
    if (!token.equals("P3")) {
      System.out.println("Invalid PPM file: plain RAW file should begin with P3");
    }
    int width = sc.nextInt();
    System.out.println("Width of image: " + width);
    int height = sc.nextInt();
    System.out.println("Height of image: " + height);
    int maxValue = sc.nextInt();
    System.out
        .println("Maximum value of a color in this file (usually 256 total values): " + maxValue);

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int r = sc.nextInt();
        int g = sc.nextInt();
        int b = sc.nextInt();
        System.out.println("Color of pixel (" + j + "," + i + "): " + r + "," + g + "," + b);
      }
    }
    sc.close();
    return builder.toString();
  }

  @Override
  public void exportImage(IImage originalImage, String fileString, String fileName,
      FileType fileType, int width, int height) {
    OutputStream output = null;
    try {
      output = new FileOutputStream(fileName + ".ppm");
    } catch (FileNotFoundException e) {
      System.err.println("Could not create initial file.");
    }
    try {
      Objects.requireNonNull(output).write(fileString.getBytes());
      output.close();
    } catch (IOException e) {
      System.err.println("Could not write to the file output.");
    }
  }
}
