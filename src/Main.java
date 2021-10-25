import controller.IController;
import controller.InteractiveController;
import model.IImageProcessor;
import model.SimpleImageProcessor;

/**
 * The main class for a user to interact with the program.
 */
public class Main {

  /**
   * The main method that can take in user input and interact with the program using strings given
   * to it.
   *
   * @param args An arbitrary number of inputs from a user
   */
  public static void main(String[] args) {
    IImageProcessor model = new SimpleImageProcessor();
    IController controller;
//    String argument = args[0];
//    switch (argument) {
//      case "script":
//        String pathOfScriptFile = args[1];
//        controller = new SimpleImageProcessorController(model, pathOfScriptFile, System.out);
//        break;
//      case "text":
//        controller = new SimpleImageProcessorController(model, new InputStreamReader(System.in), System.out);
//        break;
//       case "interactive":
    controller = new InteractiveController(model);

//        break;
//      default:
//        throw new IllegalStateException("Invalid command-line argument: " + argument);
//    }
    controller.startImageProcessor();
  }
}
