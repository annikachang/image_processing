package controller.commands;

import model.ImageProcessorFilter;

/**
 * Represents the fields and methods within the command to blur an image by applying a Gaussian
 * blur via a kernel.
 */
public class Blur extends AbstractImageCommand {

  /**
   * Creates a function object to represent the command.
   *
   * @param imageName     original image key in hashmap
   * @param destImageName key of copy of image, blurred
   */
  public Blur(String imageName, String destImageName) {
    super(imageName, destImageName);
  }

  @Override
  public void execute(ImageProcessorFilter image) {
    image.blur(imageName, destImageName, imageName);
  }
}
