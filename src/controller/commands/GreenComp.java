package controller.commands;

import model.ImageProcessorFilter;

/**
 * Represents the fields and methods within the command to grayscale an image
 * by setting all values to the green color component.
 */

public class GreenComp extends AbstractImageCommand {

  /**
   * Creates a function object to represent the green component command.
   *
   * @param imageName     original image key in hashmap
   * @param destImageName key of copy of image, greyscaled
   */

  public GreenComp(String imageName, String destImageName) {
    super(imageName, destImageName);
  }

  @Override
  public void execute(ImageProcessorFilter image) {
    image.greenComponent(imageName, destImageName);
  }
}

