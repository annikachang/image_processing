package controller.commands;

import model.ImageProcessorFilter;

/**
 * Represents the fields and methods within the command to grayscale an image
 * by setting all values to average of the color components.
 */
public class IntensityComp extends AbstractImageCommand {

  /**
   * Creates a function object to represent the intensity component command.
   *
   * @param imageName     original image key in hashmap
   * @param destImageName key of copy of image, grayscaled
   */
  public IntensityComp(String imageName, String destImageName) {
    super(imageName, destImageName);
  }

  @Override
  public void execute(ImageProcessorFilter image) {
    image.intensityComponent(imageName, destImageName);
  }
}
