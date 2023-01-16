package controller.commands;

import model.ImageProcessorFilter;

/**
 * Represents the fields and methods within the command to greyscale by applying a matrix
 * (with the luma calculation) on an image by setting all values to this calculated component.
 */
public class GreyscaleTransformation extends AbstractImageCommand {

  /**
   * Creates a function object to represent the greyscale (luma calculation) component command.
   *
   * @param imageName     original image key in hashmap
   * @param destImageName key of copy of image, greyscaled
   */
  public GreyscaleTransformation(String imageName, String destImageName) {
    super(imageName, destImageName);
  }

  @Override
  public void execute(ImageProcessorFilter image) {
    image.greyscale(imageName, destImageName, imageName);
  }
}
