package controller;

/**
 * This interface represents the operation offered by controller of the ImageProcessor,
 * which communicates with the ImageProcessorFilter and ProcessorView.
 * It also receives input from the user and performs specific commands based on the supplied
 * user input.
 */
public interface ProcessorController {

  /**
   * Parses through user input and executes commands through model and view.
   */
  void control();

}
