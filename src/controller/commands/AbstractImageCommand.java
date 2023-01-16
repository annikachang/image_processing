package controller.commands;

import controller.ControllerCommands;
import model.ImageProcessorFilter;

/**
 * Represents an abstract class that holds fields and methods common to
 * all image commands.
 */
public abstract class AbstractImageCommand implements ControllerCommands {
  protected String imageName;
  protected String destImageName;

  /**
   * Creates an abstract image command object to be used by the other commands.
   *
   * @param imageName     image name
   * @param destImageName destination image name
   */
  protected AbstractImageCommand(String imageName, String destImageName) {
    this.imageName = imageName;
    this.destImageName = destImageName;
  }

  public abstract void execute(ImageProcessorFilter image);
}
