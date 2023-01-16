package controller.commands;


import model.ImageProcessorFilter;

/**
 * Represents the fields and methods within the command to horizontally flip an image.
 */
public class HorizontalFlip extends AbstractImageCommand {

  /**
   * Creates a function object to represent the horizontal flip command.
   *
   * @param imageName     original image key in hashmap
   * @param destImageName key of copy of image, flipped horizontally
   */
  public HorizontalFlip(String imageName, String destImageName) {
    super(imageName, destImageName);
  }

  @Override
  public void execute(ImageProcessorFilter image) {
    image.horizontalFlip(imageName, destImageName);
  }
}

