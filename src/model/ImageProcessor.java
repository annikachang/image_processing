package model;

import java.util.List;

/**
 * This interface represents the operations offered in the image processor program. Images can
 * be loaded and stored as a 2D array of pixels and referred to by a supplied name henceforth
 * in the program. Then, many operations to manipulate the image can be done and the resulting
 * image is stored in the program with a new name. The original image is left unchanged.
 */
public interface ImageProcessor {

  /**
   * Load an image from the specified path and refer it to henceforth
   * in the program by the given image name.
   * @param imageName key in hashmap for image loaded in
   * @param pixels representation of the image as a 2D array
   */
  void load(String imageName, List<List<Pixel>> pixels);


  /**
   * Creates a greyscale image with the red-component of
   * the image with the given name, and refer to it henceforth in the program by the given
   * destination name.
   * @param imageName key of original image
   * @param newImageName key of copy that is greyscaled
   */
  void redComponent(String imageName, String newImageName);

  /**
   * Creates a greyscale image with the green-component of
   * the image with the given name, and refer to it henceforth in the program by the given
   * destination name.
   * @param imageName key of original image
   * @param newImageName key of copy that is grayscaled
   */
  void greenComponent(String imageName, String newImageName);

  /**
   * Creates a greyscale image with the blue-component of
   * the image with the given name, and refer to it henceforth in the program by the given
   * destination name.
   * @param imageName key of original image
   * @param newImageName key of copy that is grayscaled
   */
  void blueComponent(String imageName, String newImageName);

  /**
   * Creates a greyscale image with the maximum component of
   * the image with the given name, and refer to it henceforth in the program by the given
   * destination name.
   * @param imageName key of original image
   * @param newImageName key of copy that is grayscaled
   */
  void valueComponent(String imageName, String newImageName);

  /**
   * Creates a greyscale image with the luma-component of
   * the image with the given name, and refer to it henceforth in the program by the given
   * destination name.
   * @param imageName key of original image
   * @param newImageName key of copy that is grayscaled
   */
  void lumaComponent(String imageName, String newImageName);

  /**
   * Creates a greyscale image with the average component of
   * the image with the given name, and refer to it henceforth in the program by the given
   * destination name.
   * @param imageName key of original image
   * @param newImageName key of copy that is grayscaled
   */
  void intensityComponent(String imageName, String newImageName);

  /**
   * Creates a horizontally flipped of
   * the image with the given name, and refer to it henceforth in the program by the given
   * destination name.
   * @param imageName key of original image
   * @param newImageName key of copy that is flipped
   */
  void horizontalFlip(String imageName, String newImageName);

  /**
   * Creates a vertically flipped of
   * the image with the given name, and refer to it henceforth in the program by the given
   * destination name.
   * @param imageName key of original image
   * @param newImageName key of copy that is flipped
   */
  void verticalFlip(String imageName, String newImageName);

  /**
   * Brightens or darkens the image by the given increment
   * to create a new image, referred to henceforth by the given destination name. The increment
   * may be positive (brightening) or negative (darkening)
   * @param increment Constant to change color components by
   * @param imageName key of original image
   * @param newImageName key of copy that is flipped
   */
  void brighten(int increment, String imageName, String newImageName);

  /**
   * Returns a deep copy of the 2D array of pixels given the supplied image name.
   * @param imageName key of original image
   * @return copy of image provided
   * @throws IllegalArgumentException if image name is not found in the hashmap
   */
  List<List<Pixel>> getImage(String imageName) throws IllegalArgumentException;


}
