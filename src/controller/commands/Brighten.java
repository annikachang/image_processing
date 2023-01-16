package controller.commands;


import model.ImageProcessorFilter;

/**
 * Represents the fields and methods within the command to brighten/darken an image
 * by increasing all color components by the increment given.
 */
public class Brighten extends AbstractImageCommand {
  private int increment;

  /**
   * Creates a function object to represent the command.
   *
   * @param increment     number to increment color values
   * @param imageName     original image key in hashmap
   * @param destImageName key of copy of image, brightened
   */
  public Brighten(int increment, String imageName, String destImageName) {
    super(imageName, destImageName);
    this.increment = increment;
  }

  @Override
  public void execute(ImageProcessorFilter image) {
    image.brighten(increment, imageName, destImageName);
  }
}
