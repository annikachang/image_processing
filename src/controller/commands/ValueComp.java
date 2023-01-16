package controller.commands;

import model.ImageProcessorFilter;

/**
 * Represents the fields and methods within the command to grayscale an image
 * by setting all values to the red color component.
 */
public class ValueComp extends AbstractImageCommand {

  /**
   * Creates a function object to represent the value component command.
   *
   * @param imageName     original image key in hashmap
   * @param destImageName key of copy of image, grayscaled
   */
  public ValueComp(String imageName, String destImageName) {
    super(imageName, destImageName);
  }

  @Override
  public void execute(ImageProcessorFilter image) {
    image.valueComponent(imageName, destImageName);
  }
}
