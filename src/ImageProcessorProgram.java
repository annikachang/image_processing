import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

import controller.ProcessorController;
import controller.ProcessorControllerImpl;
import controller.ProcessorControllerImplGUI;
import model.ImageProcessorImplFilter;
import view.ProcessorView;
import view.ProcessorViewGUI;
import view.ProcessorViewGUIImpl;
import view.ProcessorViewImpl;

/**
 * Represents the main class.
 */

public class ImageProcessorProgram {
  /**
   * This main method represents the entry point of the image processing program. Three different
   * command line arguments are valid.
   * If a script file is provided (java -jar Program.jar -file path-of-script-file),
   * the script file will be opened via the file path and the commands in the file will be executed.
   * If 'java -jar Program.jar -text' is provided, the Readable takes in a System.in so that the
   * user can input instructions directly and the view is initialized to a System.out for its
   * Appendable so that the user can receive updates about whether the command properly executed.
   * The inputs from the user are sent to the model to processed.
   * If 'java -jar Program.jar' is provided, the program will open to the graphical user interface
   * or GUI.
   *
   * @param args takes in an array of Strings to perform the specific events stated above
   */
  public static void main(String[] args) {
    if (args.length == 0) {
      ProcessorViewGUI gui = new ProcessorViewGUIImpl("Image Manipulation");
      ProcessorControllerImplGUI controllerGUI =
              new ProcessorControllerImplGUI(new ImageProcessorImplFilter(),
                      gui);
    } else {
      ProcessorView view = new ProcessorViewImpl();
      ProcessorController controller;
      switch (args[0]) {
        case "-file":
          try {
            if (args.length > 1) {

              Readable in = new FileReader(args[1]);
              controller =
                      new ProcessorControllerImpl(in, new ImageProcessorImplFilter(), view);
              controller.control();
            }
          } catch (IOException e) {
            //Catches IOException
          }
          break;
        case "-text":
          Readable in = new InputStreamReader(System.in);
          controller =
                  new ProcessorControllerImpl(in, new ImageProcessorImplFilter(), view);
          controller.control();
          break;
        default:
          try {
            view.renderMessage("Please enter a valid command line argument");
          } catch (IOException e) {
            //catches IOException
          }
      }
    }
  }
}
