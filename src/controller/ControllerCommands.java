package controller;

import model.ImageProcessorFilter;

/**
 * Interface for all the commands that the controller handles.
 */
public interface ControllerCommands {
  /**
   * Executes the command by calling the relevant method on the image model.
   *
   * @param image Image object to manipulate
   */
  void execute(ImageProcessorFilter image);
}
