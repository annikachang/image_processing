package controller;

import java.awt.image.BufferedImage;
import java.util.List;

import model.Pixel;

/**
 * This interface represents the functionality offered to load images into to the program
 * by reading the file and to save manipulated images.
 * It allows the image (extension type : ppm, jpg, png and bmp) to be read through
 * a valid file path and the ability to obtain a copy of the 2D array of pixels from the
 * read method to be sent to the model.
 * Additionally, it allows the 2D arraylist of pixels or image to be saved to all
 * extension types listed above.
 */
public interface ImageConverter {

  /**
   * Allows the file to be read given a valid filepath.
   *
   * @param filePath path the image should be retrieved from
   * @throws IllegalArgumentException if file path is incorrect or file is not in the correct
   *                                  format
   */
  void reader(String filePath) throws IllegalArgumentException;

  /**
   * Returns a copy of the 2D arraylist of pixels to be sent to the model.
   *
   * @return 2D array of pixels
   */
  List<List<Pixel>> getImage();

  /**
   * Saves the 2D arraylist of pixels by writing it out to a file and sending the file to
   * the path name supplied.
   *
   * @param pathName path to save file to
   * @param image    2D arraylist of pixels to be saved
   * @throws IllegalStateException if output is not transmissible
   */
  void save(List<List<Pixel>> image, String pathName) throws IllegalStateException;

  /**
   * Converts a 2D arraylist of pixels into a buffered image by getting the RGB of
   * all pixels in the 2D arraylist and then setting it in the buffered image.
   * @param pixelList 2D arraylist of pixels supplied
   * @return buffered image
   */
  BufferedImage getBufferedImage(List<List<Pixel>> pixelList);

}
