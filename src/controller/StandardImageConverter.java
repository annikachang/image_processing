package controller;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import model.Pixel;
import model.PixelRGB;

/**
 * This class allows images to be read and exported through a valid file path.
 * This class supports the file path extensions jpg, png and bmp.
 * It does not support reading ppm files. Once the image is read, a 2D array of pixels will be sent
 * to the model for any processing to occur.
 */
public class StandardImageConverter extends AbstractImageConverter {

  /**
   * Constructs a standard image reader by calling super from the abstract class.
   */
  public StandardImageConverter() {
    super();
  }

  /**
   * Allows the file to be read via a file path and information from that file to be sent
   * to the model.
   *
   * @param filePath path the image should be retrieved from
   * @throws IllegalArgumentException if file is corrupted (empty or not in the proper format)
   */
  @Override
  public void reader(String filePath) {
    try {
      File file = new File(filePath);
      if (file.exists() && file.canRead()) {
        BufferedImage image = ImageIO.read(file);
        if (image != null) {
          this.width = image.getWidth();
          this.height = image.getHeight();
          for (int i = 0; i < height; i++) {
            List<Pixel> row = new ArrayList<>();
            for (int j = 0; j < width; j++) {
              Color rgb = new Color(image.getRGB(j, i));
              int red = rgb.getRed();
              int green = rgb.getGreen();
              int blue = rgb.getBlue();
              Pixel pixel = new PixelRGB(i, j, red, green, blue);
              row.add(pixel);
            }
            pixelsList.add(row);
          }
        } else {
          throw new IllegalArgumentException("File is empty.");
        }
      } else {
        throw new IllegalArgumentException("File is invalid.");
      }
    } catch (IOException e) {
      throw new IllegalStateException("File is not in proper format.");
    }
  }

  /**
   * Saves the 2D arraylist of pixels or image by writing out to a file.
   *
   * @param pixelList 2D arraylist of pixels to be saved
   * @param pathName  path to save file to
   * @throws IllegalStateException    if output is not transmissible
   */
  @Override
  public void save(List<List<Pixel>> pixelList, String pathName) {
    String fileExtension = pathName.substring(pathName.length() - 3);
    BufferedImage bufferedImage = this.getBufferedImage(pixelList);
    try {
      File file = new File(pathName);
      ImageIO.write(bufferedImage, fileExtension, file);
    } catch (IOException e) {
      throw new IllegalStateException("Output is not transmissible");
    }
  }

}
