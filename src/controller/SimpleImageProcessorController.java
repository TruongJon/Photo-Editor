package controller;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;
import model.ColorTransform;
import model.DefaultKernel;
import model.DownSize;
import model.Filter;
import model.IImage;
import model.IImageProcessor;
import model.PPMImage;
import view.IImageProcessorView;
import view.SimpleImageProcessorView;

/**
 * Class to represent a controller of the MVC design pattern for the image processor.
 */
public class SimpleImageProcessorController implements IImageProcessorController {

  private final IImageProcessor model;
  private final Readable rd;
  private final Appendable ap;

  /**
   * Constructs a controller, specifically for line by line user inputs through the console.
   *
   * @param model the image processor model
   * @param rd    readable for inputs
   * @param ap    appendable for outputs
   * @throws IllegalArgumentException if any of the arguments are null
   */
  public SimpleImageProcessorController(IImageProcessor model, Readable rd, Appendable ap)
      throws IllegalArgumentException {
    if (model == null || rd == null || ap == null) {
      throw new IllegalArgumentException("Fields can't be null.");
    }
    this.model = model;
    this.rd = rd;
    this.ap = ap;
  }

  /**
   * Constructs a controller using a text file to perform batch commands.
   *
   * @param model the image processor model
   * @param file  file to be read in
   * @param ap    appendable for outputs
   * @throws IllegalArgumentException if any of the arguments are null
   */
  public SimpleImageProcessorController(IImageProcessor model, String file, Appendable ap)
      throws IllegalArgumentException {
    if (model == null || file == null || ap == null) {
      throw new IllegalArgumentException("Fields can't be null.");
    }
    this.model = model;
    this.rd = parseInput(file);
    this.ap = ap;
  }

  private Readable parseInput(String file) throws IllegalArgumentException {
    StringBuilder text = new StringBuilder();
    Scanner sc;
    try {
      sc = new Scanner(new File(file));
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException("Batch command script file could not be found.");
    }
    try {
      text.append(new String(Files.readAllBytes(Paths.get(file))));
    } catch (IOException e) {
      throw new IllegalArgumentException("Batch command script file could not be found.");
    }
    return new StringReader(text.toString());
  }

  @Override
  public void startImageProcessor() {
    // process readable inputs
    this.processInputs(this.model, null, false);
  }

  @Override
  public void startImageProcessor(String currentLayer) throws IllegalArgumentException {
    if (currentLayer == null) {
      throw new IllegalArgumentException("The current layer can't be null here.");
    }
    this.processInputs(this.model, currentLayer, true);
  }

  private void processInputs(IImageProcessor model, String curLayer, boolean specifiedLayer) {
    // start a brand new image processor
    SimpleImageProcessorView view = new SimpleImageProcessorView(this.ap);
    Scanner sc = new Scanner(this.rd);
    String currentLayer;
    if (specifiedLayer) {
      currentLayer = curLayer;
    } else {
      currentLayer = null;
    }
    String fileName;
    String[] delimitedString;
    String fileType;
    IImage image;

    while (sc.hasNextLine()) {
      String input = sc.next();
      switch (input) {
        case "create":
          IImage layer = new PPMImage();
          layer.setName(sc.next());
          model.addLayer(layer);
          this.outputMessage(view, "Layer successfully created. \n");
          sc.nextLine();
          break;
        case "current":
          String temp = sc.next();
          try {
            temp = model.getLayer(temp).getName();
            currentLayer = temp;
            this.outputMessage(view, "Current layer set. \n");
          } catch (IllegalStateException e) {
            this.outputMessage(view, "Layer does not exist. \n");
          }
          sc.nextLine();
          break;
        case "remove":
          fileName = sc.next();
          model.removeLayer(model.getLayer(fileName));
          this.outputMessage(view, "Layer successfully removed. \n");
          sc.nextLine();
          break;
        case "save":
          if (currentLayer == null) {
            this.outputMessage(view, "Need a currently active layer to save image. \n");
          }
          String next = sc.next();
          int width = model.findTopMostLayer().getPixels().length;
          int height = model.findTopMostLayer().getPixels()[0].length;
          if (next.equals("all")) {
            String directoryName = sc.next();
            fileType = sc.next();
            this.saveMultiLayer(directoryName, findFileType(fileType.toLowerCase()), width, height);
            this.outputMessage(view, "Successfully saved multi-layer image. \n");
          } else {
            fileName = next;
            delimitedString = fileName.split("\\.");
            fileType = delimitedString[delimitedString.length - 1];
            try {
              this.exportImage(model.findTopMostLayer(), findFileType(fileType.toLowerCase()),
                  fileName, width, height);
              this.outputMessage(view, "Successfully saved single layer. \n");
            } catch (IllegalStateException e) {
              this.outputMessage(view, "No topmost visible layer found. \n");
            } catch (IllegalArgumentException e) {
              this.outputMessage(view, "Failed to save. \n");
            }
          }
          sc.nextLine();
          break;
        case "load":
          if (currentLayer == null) {
            this.outputMessage(view, "Need a currently active layer to load image. \n");
          } else {
            try {
              fileName = sc.next();
              delimitedString = fileName.split("\\.");
              fileType = delimitedString[delimitedString.length - 1];
              image = this.readImage(fileName, findFileType(fileType.toLowerCase()));
              image.setName(currentLayer);
              model.replaceLayer(model.getLayer(currentLayer), image);
              this.outputMessage(view, "Layer successfully loaded. \n");
            } catch (IllegalArgumentException e) {
              this.outputMessage(view, "Failed to load. \n");
            }
          }
          sc.nextLine();
          break;
        case "visible":
          if (currentLayer == null) {
            this.outputMessage(view, "Need a currently active layer to render visible. \n");
          } else {
            model.getLayer(currentLayer).setVisibility(true);
            this.outputMessage(view, "Layer set to visible. \n");
          }
          sc.nextLine();
          break;
        case "invisible":
          if (currentLayer == null) {
            this.outputMessage(view, "Need a currently active layer to render visible. \n");
          } else {
            model.getLayer(currentLayer).setVisibility(false);
            this.outputMessage(view, "Layer set to invisible. \n");
          }
          sc.nextLine();
          break;
        case "blur":
          if (currentLayer == null) {
            this.outputMessage(view, "Need a currently active layer to apply blur. \n");
          }
          try {
            image = model.getLayer(currentLayer);
            model.transformLayer(image, new Filter(), DefaultKernel.BLUR.getDefaultKernel(), true,
                currentLayer);
            this.outputMessage(view, "Blur successfully applied. \n");
          } catch (IllegalArgumentException e) {
            this.outputMessage(view, "Layer does not exist. \n");
          }
          sc.nextLine();
          break;
        case "sharpen":
          if (currentLayer == null) {
            this.outputMessage(view, "Need a currently active layer to apply sharpen. \n");
          }
          try {
            image = model.getLayer(currentLayer);
            model.transformLayer(image, new Filter(), DefaultKernel.SHARPEN.getDefaultKernel(),
                true, currentLayer);
            this.outputMessage(view, "Sharpen successfully applied. \n");
          } catch (IllegalArgumentException e) {
            this.outputMessage(view, "Layer does not exist. \n");
          }
          sc.nextLine();
          break;
        case "greyscale":
          if (currentLayer == null) {
            this.outputMessage(view, "Need a currently active layer to apply greyscale. \n");
          }
          try {
            image = model.getLayer(currentLayer);
            model.transformLayer(image, new ColorTransform(),
                DefaultKernel.GREYSCALE.getDefaultKernel(), true, currentLayer);
            this.outputMessage(view, "Greyscale successfully applied. \n");
          } catch (IllegalArgumentException e) {
            this.outputMessage(view, "Layer does not exist. \n");
          }
          sc.nextLine();
          break;
        case "sepia":
          if (currentLayer == null) {
            this.outputMessage(view, "Need a currently active layer to apply sepia. \n");
          }
          try {
            image = model.getLayer(currentLayer);
            model
                .transformLayer(image, new ColorTransform(), DefaultKernel.SEPIA.getDefaultKernel(),
                    true, currentLayer);
            this.outputMessage(view, "Sepia successfully applied. \n");
          } catch (IllegalArgumentException e) {
            this.outputMessage(view, "Layer does not exist. \n");
          }
          sc.nextLine();
          break;
        case "downsize":
          double ratio = 0;
          if (currentLayer == null) {
            this.outputMessage(view, "Need a currently active layer to apply downsize. \n");
          }
            try {
              ratio = sc.nextDouble();
            } catch (NoSuchElementException e) {
              this.outputMessage(view, "No ratio given to down-size. \n");
            }
          try {
            image = model.getLayer(currentLayer);
            model.transformLayer(image, new DownSize(ratio),
                DefaultKernel.BLANK.getDefaultKernel(), true, currentLayer);
            this.outputMessage(view, "Downsize successfully applied. \n");
          } catch (IllegalArgumentException e) {
            this.outputMessage(view, "Layer does not exist. \n");
          }

          sc.nextLine();
          break;
        case "checkerboard":
          int tileSize = 0;
          int numTiles = 0;
          Color color1 = null;
          Color color2 = null;
          try {
            tileSize = sc.nextInt();
            numTiles = sc.nextInt();
            color1 = parseStringIntoColor(sc.next().toLowerCase());
            color2 = parseStringIntoColor(sc.next().toLowerCase());
          } catch (NoSuchElementException e) {
            this.outputMessage(view, "Arguments missing to create checkerboard. \n");
          }
          image = new PPMImage(tileSize, numTiles, color1, color2);
          image.setName(model.getLayer(currentLayer).getName());
          model.replaceLayer(model.getLayer(currentLayer), image);
          this.outputMessage(view, "Checkerboard image has been created. \n");
          sc.nextLine();
          break;
        case "close":
          this.outputMessage(view, "Closing... \n");
          sc.close();
          return;
        default:
          this.outputMessage(view, "Unrecognized input. Try again. \n");
          sc.nextLine();
          break;
      }
    }
  }

  private void outputMessage(IImageProcessorView view, String message) {
    try {
      view.renderMessage(message);
    } catch (IOException e) {
      System.err.println("Failed to output message.");
    }
  }

  private FileType findFileType(String fileType) throws IllegalArgumentException {
    switch (fileType) {
      case "ppm":
        return FileType.PPM;
      case "jpg":
      case "jpeg":
        return FileType.JPEG;
      case "png":
        return FileType.PNG;
      default:
        throw new IllegalArgumentException("FileType not found.");
    }
  }

  private Color parseStringIntoColor(String color) {
    switch (color) {
      case "red":
        return Color.RED;
      case "orange":
        return Color.ORANGE;
      case "yellow":
        return Color.YELLOW;
      case "green":
        return Color.GREEN;
      case "blue":
        return Color.BLUE;
      case "cyan":
        return Color.CYAN;
      case "magenta":
        return Color.MAGENTA;
      case "pink":
        return Color.PINK;
      case "black":
        return Color.BLACK;
      case "white":
        return Color.WHITE;
      case "gray":
        return Color.GRAY;
      default:
        return null;
    }
  }

  @Override
  public IImage readImage(String filename, FileType fileType) throws IllegalArgumentException {
    if (filename == null) {
      throw new IllegalArgumentException("Filename can't be null.");
    }
    String imageString;
    IOFile ioFileType;
    if (fileType == FileType.PPM) {
      ioFileType = new IOPPMFile();
    } else {
      ioFileType = new AbstractIOFile();
    }
    imageString = ioFileType.readImage(filename);
    return new PPMImage(imageString);
  }

  @Override
  public void exportImage(IImage image, FileType fileType, String fileName, int width, int height)
      throws IllegalArgumentException {
    if (image == null || fileName == null) {
      throw new IllegalArgumentException("Fields can't be null.");
    }
    String fileString = image.sendToString();
    IOFile ioFileType;
    if (fileType == FileType.PPM) {
      ioFileType = new IOPPMFile();
    } else {
      ioFileType = new AbstractIOFile();
    }
    ioFileType.exportImage(image, fileString, fileName, fileType, width, height);
  }

  @Override
  public void saveMultiLayer(String newDirectory, FileType fileType, int width, int height) {
    new File(newDirectory).mkdir();
    StringBuilder text = new StringBuilder();
    text.append("Images that can be found in ").append(newDirectory).append(":\n");
    OutputStream output = null;
    try {
      output = new FileOutputStream(newDirectory + "/ImageLocations.txt");
    } catch (FileNotFoundException e) {
      System.err.println("File could not be created.");
    }

    ArrayList<IImage> images = this.model.getAllImages();
    for (IImage image : images) {
      this.exportImage(image, fileType,
          newDirectory + "/" + image.getName() + "." + fileType.toString(), width, height);
      text.append(newDirectory).append("/").append(image.getName()).append(".")
          .append(fileType.toString()).append("\n");
    }
    try {
      output.write(text.toString().getBytes());
    } catch (IOException e) {
      System.err.println("Text file could not be written to.");
    }
  }
}
