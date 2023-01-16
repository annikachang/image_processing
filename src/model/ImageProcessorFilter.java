package model;

import java.util.List;

/**
 * This interface represents the operations offered in the image processor program which now
 * includes filter operations (blur and sharpen) and color transformations (greyscale and sepia).
 * Additionally, this interface still supports the original operations offered in the interface
 * ImageProcessor.
 * Similarly to before, images can be loaded in and stored as a 2D array of pixels and referred
 * to as a name throughout the program. If any operation listed above are performed on the image,
 * a new image will be created to reflect those changes and the original image will remain
 * the same.
 */
public interface ImageProcessorFilter extends ImageProcessor {

  /**
   * Creates a new blurred image by applying a "Gaussian blur" to the image with the given name.
   * A kernel is applied to each pixel to achieve the slight blurred filter.
   * The blurred image will be referred to by the new image name supplied and this image
   * can be blurred multiple times to gain the desired result.
   *
   * @param imageName    key of original image
   * @param newImageName key of copy that is blurred
   */
  void blur(String imageName, String newImageName, String originalImageName);

  /**
   * Creates a new sharpened image to the image with the given name by applying a kernel to
   * each pixel.
   * The sharpened image will be referred to by the new image name supplied and this image,
   * like blur effect, can be sharpened multiple times to gain the desired result.
   *
   * @param imageName         key of image to change
   * @param newImageName      key or copy that is sharpened
   * @param originalImageName key of original image
   *                          If partial image manipulation is not performed, the image name and
   *                          original image name
   *                          will remain the same. Otherwise, the image name will be the name for
   *                          the pixels to change.
   */
  void sharpen(String imageName, String newImageName, String originalImageName);


  /**
   * Creates a new greyscaled image by applying a matrix to the color components of each pixel
   * in the image. In this case, the image is greyscaled by calculating the luma component of
   * the pixel and setting that component to each color component in the pixel
   * The new greyscaled image will be under the new image name and the original will be unchanged.
   *
   * @param imageName         key of image to change
   * @param newImageName      key or copy that is greyscaled
   * @param originalImageName key of original image
   *                          If partial image manipulation is not performed, the image name and
   *                          original image name will remain the same. Otherwise, the image name
   *                          will be the name for the pixels to change.
   */
  void greyscale(String imageName, String newImageName, String originalImageName);

  /**
   * Creates a new image with the sepia color transformation by applying a matrix to the color
   * components of each pixel in the image.
   * The new sepia image will be under the new image name and the original will be unchanged.
   *
   * @param imageName         key of original image
   * @param newImageName      key or copy that is sepia ed
   * @param originalImageName key of original image
   *                          If partial image manipulation is not performed, the image name and
   *                          original image name will remain the same. Otherwise, the image name
   *                          will be the name for the pixels to change.
   */
  void sepia(String imageName, String newImageName, String originalImageName);

  /**
   * Creates a histogram or a 2D array where there are 4 arrays to represent the frequency
   * of pixels found at each value (0 - 256). These arrays are created for all the pixels
   * red, blue, green and intensity components which make up the 4 arrays.
   *
   * @param imageName key of the image
   * @return 2D array of colors and values
   */
  int[][] getHistogram(String imageName);

  /**
   * Checks the mask image for any black pixels (red = 0, green = 0, blue = 0) and adds the
   * pixels from the original image to add to the pixels to change and apply the specific
   * command also supplied. Adds this pixel list to the hashmap to be processed with the
   * command.
   *
   * @param command       command or operations
   * @param imageName     key of the original image
   * @param mask          mask image that is the same as original image with some black pixels
   * @param destImageName key for the destination image
   */
  void partialImageManipulation(String command, String imageName,
                                List<List<Pixel>> mask, String destImageName);

  /**
   * Recombines the original image and the newly processed image to create a new entry for
   * the destination image name. If the mask's pixel is black, then a copy of the original
   * image is changed to the pixels in the newly processed image. Otherwise, original
   * image's pixels are left. Newly combined image is added to the hashmap with the key of the
   * destination image name.
   *
   * @param originalImage       2D arraylist of pixels that represents the original image
   * @param newManipulatedImage 2D arraylist of pixels that represents the newly processed image
   * @param destImageName       key for the destination image
   * @param mask                2D arraylist of pixels that represents the mask
   */
  void recombine(List<List<Pixel>> originalImage, List<List<Pixel>> newManipulatedImage,
                 String destImageName, List<List<Pixel>> mask);


}
