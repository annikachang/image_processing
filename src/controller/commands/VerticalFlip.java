package controller.commands;

import model.ImageProcessorFilter;

/**
 * Represents the fields and methods within the command to vertically flip an image.
 */
public class VerticalFlip extends AbstractImageCommand {

  /**
   * Creates a function object to represent the vertical flip command.
   *
   * @param imageName     original image key in hashmap
   * @param destImageName key of copy of image, flipped vertically
   */
  public VerticalFlip(String imageName, String destImageName) {
    super(imageName, destImageName);
  }

  @Override
  public void execute(ImageProcessorFilter image) {
    image.verticalFlip(imageName, destImageName);
  }
}

