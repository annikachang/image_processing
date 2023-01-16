package controller.commands;

import model.ImageProcessorFilter;

/**
 * Represents the fields and methods within the command to grayscale an image
 * by setting all values to value derived from luma formula.
 */
public class LumaComp extends AbstractImageCommand {

  /**
   * Creates a function object to represent the luma component command.
   *
   * @param imageName     original image key in hashmap
   * @param destImageName key of copy of image, grayscaled
   */
  public LumaComp(String imageName, String destImageName) {
    super(imageName, destImageName);
  }

  @Override
  public void execute(ImageProcessorFilter image) {
    image.lumaComponent(imageName, destImageName);
  }
}
