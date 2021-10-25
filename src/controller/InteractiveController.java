package controller;

import java.awt.image.BufferedImage;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import javax.swing.JFrame;
import model.IImage;
import model.IImageProcessor;
import view.GuiView;
import view.IViewListener;
import view.IView;

public class InteractiveController implements IController, IViewListener {

  private final IImageProcessor model;
  private final IView view;
  private StringBuilder stringBuilder;
  private IImageProcessorController mainController;
  private Readable readable;
  private String currentLayer = "";
  private String transformation;
  private String f1;
  private String f2;
  private String c1;
  private String c2;
  private String checkerboardFilepath;

  public InteractiveController(IImageProcessor model) {
    this.model = Objects.requireNonNull(model);
    GuiView frame = new GuiView(this.model);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);
    this.view = frame;
  }

  @Override
  public void startImageProcessor() {
    this.view.registerViewEventListener(this);
    stringBuilder = new StringBuilder();
    readable = new StringReader(stringBuilder.toString());
    mainController = new SimpleImageProcessorController(this.model, readable, System.out);
    mainController.startImageProcessor();
  }

  @Override
  public void handleLoadEvent(String filepath) throws IllegalStateException {
    stringBuilder = new StringBuilder();
    switch (this.model.getAllImages().size()) {
      case 0:
        currentLayer = "first";
        stringBuilder.append("create first \ncurrent first\n");
        break;
      case 1:
        currentLayer = "second";
        stringBuilder.append("create second \ncurrent second\n");
        break;
      case 2:
        currentLayer = "third";
        stringBuilder.append("create third \ncurrent third\n");
        break;
      case 3:
        currentLayer = "fourth";
        stringBuilder.append("create fourth \ncurrent fourth\n");
        break;
      case 4:
        currentLayer = "fifth";
        stringBuilder.append("create fifth \ncurrent fifth\n");
        break;
      default:
        throw new IllegalStateException("Only five layers at a time are supported.");
    }
    stringBuilder.append("load ").append(filepath).append("\nclose");
    readable = new StringReader(stringBuilder.toString());
    mainController = new SimpleImageProcessorController(this.model, readable, System.out);
    mainController.startImageProcessor(currentLayer);
  }

  @Override
  public void handleSaveEvent(String filepath) {
    stringBuilder = new StringBuilder();
    stringBuilder.append("save ").append(filepath).append("\nclose");
    readable = new StringReader(stringBuilder.toString());
    mainController = new SimpleImageProcessorController(this.model, readable, System.out);
    mainController.startImageProcessor(currentLayer);
  }

  @Override
  public void handleSaveAllEvent(String filepath, String fileType) {
    stringBuilder = new StringBuilder();
    stringBuilder.append("save all ").append(filepath).append(" ").append(fileType.toLowerCase())
        .append("\nclose");
    readable = new StringReader(stringBuilder.toString());
    mainController = new SimpleImageProcessorController(this.model, readable, System.out);
    mainController.startImageProcessor(currentLayer);
  }


  @Override
  public void handleTransformationEvent() throws IllegalStateException {
    stringBuilder = new StringBuilder();
    String lowerCaseCommand = transformation.toLowerCase();
    switch (lowerCaseCommand) {
      case "blur":
        stringBuilder.append("blur\n");
        break;
      case "sharpen":
        stringBuilder.append("sharpen\n");
        break;
      case "greyscale":
        stringBuilder.append("greyscale\n");
        break;
      case "sepia":
        stringBuilder.append("sepia\n");
        break;
      case "downsize":
        stringBuilder.append("downsize ").append(f1).append("\n");
        break;
      case "mosaic":
        // TODO: not currently implemented in model...
        break;
      case "checkerboard":
        switch (currentLayer) {
          case "":
            currentLayer = "first";
            stringBuilder.append("create first\n").append("current first\n");
            break;
          case "first":
            currentLayer = "second";
            stringBuilder.append("create second\n").append("current second\n");
            break;
          case "second":
            currentLayer = "third";
            stringBuilder.append("create third\n").append("current third\n");
            break;
          case "third":
            currentLayer = "fourth";
            stringBuilder.append("create fourth\n").append("current fourth\n");
            break;
          case "fourth":
            currentLayer = "fifth";
            stringBuilder.append("create fifth\n").append("current fifth\n");
            break;
          default:
            throw new IllegalStateException("Only five images can be stored.");
        }
        stringBuilder.append(constructCheckerboardCall());
        stringBuilder.append("save ").append(checkerboardFilepath);
        break;
      default:
        throw new IllegalStateException("Unrecognized button press.");
    }
    stringBuilder.append("\nclose");
    readable = new StringReader(stringBuilder.toString());
    mainController = new SimpleImageProcessorController(this.model, readable, System.out);
    mainController.startImageProcessor(currentLayer);
  }

  private String constructCheckerboardCall() {
    return transformation.toLowerCase() + " " + f1 + " " + f2 + " "
        + c1 + " " + c2 + "\n";
  }

  @Override
  public void handleVisibilityEvent(String layer, boolean visible) {
    stringBuilder = new StringBuilder();
    stringBuilder.append("current ").append(layer).append("\n");
    if (visible) { ;
      stringBuilder.append("visible ").append(layer).append("\n");
    } else {
      stringBuilder.append("invisible ").append(layer).append("\n");
    }
    stringBuilder.append("close");
    readable = new StringReader(stringBuilder.toString());
    mainController = new SimpleImageProcessorController(this.model, readable, System.out);
    mainController.startImageProcessor(currentLayer);
  }

  @Override
  public BufferedImage handleImageConversionEvent(IImage image) {
    return image.convertImage();
  }

  @Override
  public void setFields(String... fields) {
    ArrayList<String> listOfFields = new ArrayList<>(Arrays.asList(fields));
    transformation = listOfFields.get(0);
    f1 = listOfFields.get(1);
    f2 = listOfFields.get(2);
    c1 = listOfFields.get(3);
    c2 = listOfFields.get(4);
    checkerboardFilepath = listOfFields.get(5);
  }
}
