package controller.commands;

import model.ImageProcessorFilter;

/**
 * Represents the fields and methods within the command to grayscale an image
 * by setting all values to the blue color component.
 */
public class BlueComp extends AbstractImageCommand {

  /**
   * Creates a function object to represent the blue component command.
   *
   * @param imageName     original image key in hashmap
   * @param destImageName key of copy of image, grayscaled
   */
  public BlueComp(String imageName, String destImageName) {
    super(imageName, destImageName);
  }

  @Override
  public void execute(ImageProcessorFilter image) {
    image.blueComponent(imageName, destImageName);
  }
}
