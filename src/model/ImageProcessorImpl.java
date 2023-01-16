package model;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class represents an implementation of the image processor interface. It stores
 * a hashmap where the key is the image name and the value is the 2D arraylists of pixels.
 * It contains methods to load the image to store it in the hashmap.
 * Additionally, once the image is loaded in, the image name can be used to refer to the 2D
 * arraylist of pixels and operations like brightening or grey-scaling the pixels can be applied.
 * Each operation creates a deep copy of the image and performs operations on to the copy to leave
 * the original one unchanged.
 */
public class ImageProcessorImpl implements ImageProcessor {
  protected Map<String, List<List<Pixel>>> imageStorage;

  /**
   * Constructs an image processor model object that initializes the hashmap or image storage
   * to empty.
   */
  public ImageProcessorImpl() {
    this.imageStorage = new HashMap<String, List<List<Pixel>>>();
  }

  @Override
  public void load(String imageName, List<List<Pixel>> pixels) {
    this.imageStorage.put(imageName, pixels);
  }

  /**
   * Gets a specific channel of one pixel in the original image determined by the index in the
   * array (channel to index relationship is detailed in the pixel class).
   * And sets that channel to a pixel in the copy created. Loops through the 2D
   * arraylist to do this for all pixels. Then, the manipulated 2D arraylist of pixels will
   * be stored in the hashmap under the destination image name, leaving the original one
   * unchanged.
   *
   * @param imageName     key of original image
   * @param destImageName destination image name
   * @param channel       index of array within pixel that corresponds to a specific channel
   */
  private void colorComponent(String imageName, String destImageName, int channel) {
    List<List<Pixel>> destImage = this.getImage(imageName);
    for (int i = 0; i < destImage.size(); i++) {
      for (int j = 0; j < destImage.get(i).size(); j++) {
        int colorComp = this.imageStorage.get(imageName).get(i).get(j).getColorComp(channel);
        destImage.get(i).get(j).setColors(colorComp);
      }
    }
    this.imageStorage.put(destImageName, destImage);
  }

  @Override
  public void redComponent(String imageName, String destImageName) {
    this.colorComponent(imageName, destImageName, 0);
  }

  @Override
  public void greenComponent(String imageName, String destImageName) {
    this.colorComponent(imageName, destImageName, 1);
  }

  @Override
  public void blueComponent(String imageName, String destImageName) {
    this.colorComponent(imageName, destImageName, 2);
  }


  @Override
  public void valueComponent(String imageName, String destImageName) {
    this.colorComponent(imageName, destImageName, 3);
  }

  @Override
  public void lumaComponent(String imageName, String destImageName) {
    this.colorComponent(imageName, destImageName, 4);
  }

  @Override
  public void intensityComponent(String imageName, String destImageName) {
    this.colorComponent(imageName, destImageName, 5);
  }


  @Override
  public void verticalFlip(String imageName, String destImageName) {
    List<List<Pixel>> destImage = this.getImage(imageName);
    for (int i = 0; i < destImage.size(); i++) {
      for (int j = 0; j < destImage.get(i).size(); j++) {
        destImage.get(i).get(j).setX(
                Math.abs(destImage.size() - 1 - destImage.get(i).get(j).getPosX()));
      }
    }
    Collections.reverse(destImage);
    imageStorage.put(destImageName, destImage);
  }

  @Override
  public void horizontalFlip(String imageName, String destImageName) {
    List<List<Pixel>> destImage = this.getImage(imageName);
    for (int i = 0; i < destImage.size(); i++) {
      for (int j = 0; j < destImage.get(i).size(); j++) {
        destImage.get(i).get(j).setY(
                Math.abs(destImage.get(i).size() - 1 - destImage.get(i).get(j).getPosY()));
      }
      Collections.reverse(destImage.get(i));
    }
    imageStorage.put(destImageName, destImage);
  }

  @Override
  public void brighten(int increment, String imageName, String destImageName) {
    List<List<Pixel>> destImage = this.getImage(imageName);
    for (int i = 0; i < destImage.size(); i++) {
      for (int j = 0; j < destImage.get(i).size(); j++) {
        destImage.get(i).get(j).add(increment);
      }
    }
    imageStorage.put(destImageName, destImage);
  }

  @Override
  public List<List<Pixel>> getImage(String imageName) {
    if (this.imageStorage.containsKey(imageName)) {
      List<List<Pixel>> copyOfImage = new ArrayList<>();
      for (int i = 0; i < imageStorage.get(imageName).size(); i++) {
        List<Pixel> row = new ArrayList<>();
        for (int j = 0; j < imageStorage.get(imageName).get(i).size(); j++) {
          row.add(this.imageStorage.get(imageName).get(i).get(j).getPixelCopy());
        }
        copyOfImage.add(row);
      }
      return copyOfImage;
    } else {
      throw new IllegalArgumentException("File not found.");
    }
  }


}

