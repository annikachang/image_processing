package controller.commands;


import model.ImageProcessorFilter;

/**
 * Represents the fields and methods within the command to apply a sepia color transformation on
 * an image through a matrix provided.
 */
public class SepiaTransformation extends AbstractImageCommand {

  /**
   * Creates a function object to represent the sepia component command.
   *
   * @param imageName     original image key in hashmap
   * @param destImageName key of copy of image, with sepia color transformation applied
   */
  public SepiaTransformation(String imageName, String destImageName) {
    super(imageName, destImageName);
  }

  @Override
  public void execute(ImageProcessorFilter image) {
    image.sepia(imageName, destImageName, imageName);
  }
}
