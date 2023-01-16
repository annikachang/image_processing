package controller;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import model.Pixel;

/**
 * This class represents the common functionalities of all image converters supported in this
 * program. The extension types currently supported are listed in the ImageConverter interface.
 * Different types of file extension for loading and saving can occur (ex. users can import
 * a ppm and save it as a jpg).
 */
public abstract class AbstractImageConverter implements ImageConverter {
  protected List<List<Pixel>> pixelsList;
  protected int width;
  protected int height;

  /**
   * Initializes the pixel list to be read from the file to an empty 2D arraylist.
   */
  protected AbstractImageConverter() {
    this.pixelsList = new ArrayList<>();
  }

  public abstract void reader(String filePath);

  @Override
  public List<List<Pixel>> getImage() {
    List<List<Pixel>> copyOfPixelList = new ArrayList<>();
    for (int i = 0; i < this.height; i++) {
      List<Pixel> row = new ArrayList<>();
      for (int j = 0; j < this.width; j++) {
        row.add(this.pixelsList.get(i).get(j));
      }
      copyOfPixelList.add(row);
    }
    return copyOfPixelList;
  }

  @Override
  public BufferedImage getBufferedImage(List<List<Pixel>> pixelList) {
    BufferedImage image = new BufferedImage(pixelList.get(0).size(),
            pixelList.size(), BufferedImage.TYPE_INT_RGB);
    for (int i = 0; i < pixelList.size(); i++) {
      for (int j = 0; j < pixelList.get(i).size(); j++) {
        int rgb = new Color(pixelList.get(i).get(j).getColorComp(0),
                pixelList.get(i).get(j).getColorComp(1),
                pixelList.get(i).get(j).getColorComp(2)).getRGB();
        image.setRGB(j, i, rgb);
      }
    }
    return image;
  }
}
