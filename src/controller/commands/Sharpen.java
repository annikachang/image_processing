package controller.commands;

import model.ImageProcessorFilter;

/**
 * Represents the fields and methods within the command to sharpen an image by applying a kernel.
 */
public class Sharpen extends AbstractImageCommand {

  /**
   * Creates a function object to represent the command.
   *
   * @param imageName     original image key in hashmap
   * @param destImageName key of copy of image, sharpened
   */
  public Sharpen(String imageName, String destImageName) {
    super(imageName, destImageName);
  }

  @Override
  public void execute(ImageProcessorFilter image) {
    image.sharpen(imageName, destImageName, imageName);
  }
}
