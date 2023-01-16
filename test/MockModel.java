import java.util.List;
import java.util.Objects;

import model.ImageProcessorFilter;
import model.Pixel;

/**
 * Mock model for testing purposes.
 */
public class MockModel implements ImageProcessorFilter {
  private final StringBuilder log;

  public MockModel(StringBuilder log) {
    this.log = Objects.requireNonNull(log);
  }

  @Override
  public void load(String imageName, List<List<Pixel>> pixels) {
    log.append(imageName);
  }


  @Override
  public void redComponent(String imageName, String newImageName) {
    appendToLog(imageName, newImageName);
  }

  @Override
  public void greenComponent(String imageName, String newImageName) {
    appendToLog(imageName, newImageName);
  }

  @Override
  public void blueComponent(String imageName, String newImageName) {
    appendToLog(imageName, newImageName);
  }

  @Override
  public void valueComponent(String imageName, String newImageName) {
    appendToLog(imageName, newImageName);
  }

  @Override
  public void lumaComponent(String imageName, String newImageName) {
    appendToLog(imageName, newImageName);
  }

  @Override
  public void intensityComponent(String imageName, String newImageName) {
    appendToLog(imageName, newImageName);
  }

  @Override
  public void horizontalFlip(String imageName, String newImageName) {
    appendToLog(imageName, newImageName);
  }

  @Override
  public void verticalFlip(String imageName, String newImageName) {
    appendToLog(imageName, newImageName);
  }

  @Override
  public void brighten(int increment, String imageName, String newImageName) {
    log.append(Integer.toString(increment) + " " + " " + imageName + " " + newImageName);
  }

  @Override
  public List<List<Pixel>> getImage(String imageName) {
    return null;
  }

  private void appendToLog(String imageName, String newImageName) {
    log.append(imageName + " " + newImageName + "\n");
  }

  @Override
  public void blur(String imageName, String newImageName, String originalImageName) {
    log.append(imageName + " " + newImageName + "\n");
  }

  @Override
  public void sharpen(String imageName, String newImageName, String originalImageName) {
    log.append(imageName + " " + newImageName + "\n");
  }


  @Override
  public void greyscale(String imageName, String newImageName, String originalImageName) {
    log.append(imageName + " " + newImageName + "\n");
  }

  @Override
  public void sepia(String imageName, String newImageName, String originalImageName) {
    log.append(imageName + " " + newImageName + "\n");
  }

  @Override
  public int[][] getHistogram(String imageName) {
    log.append(imageName);
    return null;
  }


  @Override
  public void partialImageManipulation(String imageName, String destImageName,
                                       List<List<Pixel>> mask, String command) {
    log.append(imageName + " " + destImageName + " " + command + "\n");


  }

  @Override
  public void recombine(List<List<Pixel>> originalImage, List<List<Pixel>> newManipulatedImage,
                        String destImageName, List<List<Pixel>> mask) {
    log.append(destImageName + "\n");
  }
}
