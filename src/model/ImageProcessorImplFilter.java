package model;

import java.util.ArrayList;
import java.util.List;


/**
 * This class represents an implementation of the ImageProcessorFilter interface. It offers the
 * same operations as the ImageProcessorImpl class but adds four new operations (blur, sharpen,
 * greyscale and sepia). When images are loaded, the supplied name and the 2D arraylist of pixels
 * are stored in the original class to maintain one uniform place of storage.
 */
public class ImageProcessorImplFilter extends ImageProcessorImpl implements ImageProcessorFilter {

  /**
   * Constructs an image processor filter model object that calls the constructor in the
   * ImageProcessorImpl class. Thus, any images created from either class will be saved to
   * the same hashmap initialized.
   */
  public ImageProcessorImplFilter() {
    super();
  }

  @Override
  public void load(String imageName, List<List<Pixel>> pixels) {
    super.load(imageName, pixels);
  }

  @Override
  public void redComponent(String imageName, String newImageName) {
    super.redComponent(imageName, newImageName);
  }

  @Override
  public void greenComponent(String imageName, String newImageName) {
    super.greenComponent(imageName, newImageName);
  }

  @Override
  public void blueComponent(String imageName, String newImageName) {
    super.blueComponent(imageName, newImageName);
  }

  @Override
  public void valueComponent(String imageName, String newImageName) {
    super.valueComponent(imageName, newImageName);
  }

  @Override
  public void lumaComponent(String imageName, String newImageName) {
    super.lumaComponent(imageName, newImageName);
  }

  @Override
  public void intensityComponent(String imageName, String newImageName) {
    super.intensityComponent(imageName, newImageName);
  }

  @Override
  public void horizontalFlip(String imageName, String newImageName) {
    super.horizontalFlip(imageName, newImageName);
  }

  @Override
  public void verticalFlip(String imageName, String newImageName) {
    super.verticalFlip(imageName, newImageName);
  }

  @Override
  public void brighten(int increment, String imageName, String newImageName) {
    super.brighten(increment, imageName, newImageName);
  }

  @Override
  public List<List<Pixel>> getImage(String imageName) throws IllegalArgumentException {
    return super.getImage(imageName);
  }


  @Override
  public void blur(String imageName, String destImageName, String originalImageName) {
    List<List<Pixel>> destImage = getImage(imageName);
    List<List<Pixel>> image = getImage(imageName);
    List<List<Pixel>> originalImage = getImage(originalImageName);
    double[][] kernel = new double[][]{
            {0.0625, 0.125, 0.0625},
            {0.125, 0.25, 0.125}, {0.0625, 0.125, 0.0625}};

    for (int i = 0; i < image.size(); i++) {
      for (int j = 0; j < image.get(i).size(); j++) {
        filterHelper(i, j, originalImage, destImage, kernel);
      }
    }
    imageStorage.put(destImageName, destImage);
  }

  @Override
  public void sharpen(String imageName, String destImageName, String originalImageName) {
    List<List<Pixel>> destImage = getImage(imageName);
    List<List<Pixel>> image = getImage(imageName);
    List<List<Pixel>> orginalImage = getImage(originalImageName);
    double[][] kernel = new double[][]{
            {-0.125, -0.125, -0.125, -0.125, -0.125},
            {-0.125, 0.25, 0.25, 0.25, -0.125}, {-0.125, 0.25, 1, 0.25, -0.125},
            {-0.125, 0.25, 0.25, 0.25, -0.125}, {-0.125, -0.125, -0.125, -0.125, -0.125}};
    for (int i = 0; i < image.size(); i++) {
      for (int j = 0; j < image.get(i).size(); j++) {
        filterHelper(i, j, orginalImage, destImage, kernel);
      }
    }
    imageStorage.put(destImageName, destImage);
  }

  /**
   * Applies a kernel to each pixel depending on the pixel's position (i,j). If neighboring pixels
   * from that center pixel is outside the kernel or the image, then that kernel's number will
   * not be included in the final calculation for the center pixel.
   * The center pixel is then set to the red, green and blue sum calculated if values are between
   * 0 and 255, otherwise they are clamped to 0 or 255.
   *
   * @param i         column number of pixel
   * @param j         row number of pixel
   * @param image     2D arraylist of pixels
   * @param destImage 2D arraylist of pixels for the destination image
   * @param kernel    specific kernel that filters the pixel
   */
  private void filterHelper(int i, int j, List<List<Pixel>> image,
                            List<List<Pixel>> destImage, double[][] kernel) {
    int kernelSize = kernel.length / 2;
    double redSum = 0;
    double greenSum = 0;
    double blueSum = 0;
    int indexRow = 0;
    for (int m = i - kernelSize; m <= i + kernelSize; m++) {
      int indexCol = 0;
      for (int n = j - kernelSize; n <= j + kernelSize; n++) {

        if (!(m < 0 || m >= image.size() || n < 0 || n >= image.get(m).size())) {
          redSum += image.get(m).get(n).getColorComp(0) * kernel[indexRow][indexCol];
          greenSum += image.get(m).get(n).getColorComp(1) * kernel[indexRow][indexCol];
          blueSum += image.get(m).get(n).getColorComp(2) * kernel[indexRow][indexCol];
        }

        indexCol++;
      }
      indexRow++;
    }
    destImage.get(i).get(j).setOneColor(0,
            Math.min(Math.max(0, (int) Math.round(redSum)), 255));
    destImage.get(i).get(j).setOneColor(1,
            Math.min(Math.max(0, (int) Math.round(greenSum)), 255));
    destImage.get(i).get(j).setOneColor(2,
            Math.min(Math.max(0, (int) Math.round(blueSum)), 255));
  }


  @Override
  public void greyscale(String imageName, String newImageName, String originalImageName) {
    List<List<Pixel>> destImage = this.getImage(imageName);
    List<List<Pixel>> originalImage = getImage(originalImageName);
    double[][] greyscaleMatrix = {
            {.2126, .7152, .0722},
            {.2126, .7152, .0722},
            {.2126, .7152, .0722}};
    for (int i = 0; i < destImage.size(); i++) {
      for (int j = 0; j < destImage.get(i).size(); j++) {
        this.applyMatrix(i, j, greyscaleMatrix, originalImage, destImage);
      }
    }
    this.imageStorage.put(newImageName, destImage);
  }

  @Override
  public void sepia(String imageName, String newImageName, String originalImageName) {
    List<List<Pixel>> destImage = this.getImage(imageName);
    List<List<Pixel>> originalImage = getImage(originalImageName);
    double[][] sepiaMatrix = {
            {.393, .769, .189},
            {.349, .686, .168},
            {.272, .534, .131}};
    for (int i = 0; i < destImage.size(); i++) {
      for (int j = 0; j < destImage.get(i).size(); j++) {
        this.applyMatrix(i, j, sepiaMatrix, originalImage, destImage);
      }
    }
    this.imageStorage.put(newImageName, destImage);
  }

  /**
   * Applies a matrix to perform the color transformation. A copy of the array of color components
   * is obtained and then multiplied with the matrix. The red, green and blue components of
   * that pixel is set to the newly calculated components from that matrix multiplication.
   *
   * @param matrix matrix for color transformation
   */
  private void applyMatrix(int i, int j, double[][] matrix, List<List<Pixel>> image,
                           List<List<Pixel>> destImage) {
    int[] colorCompMatrix = image.get(i).get(j).getColorsCopy();
    List<Integer> sum = new ArrayList<>();
    for (int k = 0; k < matrix.length; k++) {
      for (int l = 0; l < matrix.length; l++) {
        sum.add((int) Math.round(matrix[k][l] * colorCompMatrix[l]));
      }
    }
    destImage.get(i).get(j).setOneColor(0, this.findSum(sum.subList(0, 3)));
    destImage.get(i).get(j).setOneColor(1, this.findSum(sum.subList(3, 6)));
    destImage.get(i).get(j).setOneColor(2, this.findSum(sum.subList(6, 9)));
  }

  /**
   * Adds the integers in the list together and clamps the value so that any sum won't be
   * greater than 255 or less than 0.
   *
   * @param list list of integers
   * @return sum
   */
  private int findSum(List<Integer> list) {
    int sum = 0;
    for (int i = 0; i < list.size(); i++) {
      sum += list.get(i);
    }
    return Math.max(Math.min(sum, 255), 0);
  }

  @Override
  public int[][] getHistogram(String imageName) {
    List<List<Pixel>> image = getImage(imageName);
    int[] redFrequencies = new int[256];
    int[] greenFrequencies = new int[256];
    int[] blueFrequencies = new int[256];
    int[] intensityFrequencies = new int[256];
    for (int i = 0; i < image.size(); i++) {
      for (int j = 0; j < image.get(i).size(); j++) {
        redFrequencies[image.get(i).get(j).getColorComp(0)] += 1;
        greenFrequencies[image.get(i).get(j).getColorComp(1)] += 1;
        blueFrequencies[image.get(i).get(j).getColorComp(2)] += 1;
        intensityFrequencies[image.get(i).get(j).getColorComp(5)] += 1;
      }
    }
    int[][] imageHistogram = new int[][]{redFrequencies, greenFrequencies, blueFrequencies,
                                         intensityFrequencies};
    return imageHistogram;
  }


  @Override
  public void partialImageManipulation(String command, String imageName,
                                       List<List<Pixel>> mask, String destImageName) {
    List<List<Pixel>> originalImage = getImage(imageName);
    List<List<Pixel>> pixelsToChange = new ArrayList<>();
    for (int i = 0; i < mask.size(); i++) {
      List<Pixel> row = new ArrayList<>();

      for (int j = 0; j < mask.get(i).size(); j++) {
        if (mask.get(i).get(j).getColorComp(0) == 0 &&
                mask.get(i).get(j).getColorComp(1) == 0 &&
                mask.get(i).get(j).getColorComp(2) == 0) {
          row.add(originalImage.get(i).get(j));
        }
      }
      if (row.size() > 0) {
        pixelsToChange.add(row);
      }
    }
    imageStorage.put("PartialImage", pixelsToChange);
  }

  @Override
  public void recombine(List<List<Pixel>> originalImage, List<List<Pixel>> newManipulatedImage,
                        String destImageName, List<List<Pixel>> mask) {
    int indexRow = 0;
    int indexCol = 0;
    for (int i = 0; i < originalImage.size(); i++) {
      for (int j = 0; j < originalImage.get(i).size(); j++) {
        if (mask.get(i).get(j).getColorComp(0) == 0 &&
                mask.get(i).get(j).getColorComp(1) == 0 &&
                mask.get(i).get(j).getColorComp(2) == 0) {

          originalImage.get(i).get(j).setOneColor(0,
                  newManipulatedImage.get(indexRow).get(indexCol).getColorComp(0));
          originalImage.get(i).get(j).setOneColor(1,
                  newManipulatedImage.get(indexRow).get(indexCol).getColorComp(1));
          originalImage.get(i).get(j).setOneColor(2,
                  newManipulatedImage.get(indexRow).get(indexCol).getColorComp(2));
          if (indexCol + 1 < newManipulatedImage.get(indexRow).size()) {
            indexCol += 1;
          } else {
            indexCol = 0;
            indexRow += 1;
          }


        }
      }
    }
    imageStorage.put(destImageName, originalImage);
  }


}
