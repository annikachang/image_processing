package controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

import model.Pixel;
import model.PixelRGB;

/**
 * This class represents an implementation of the image reader interface that supports
 * reading files from ppm files. It reads and sends the 2D array of pixels to the model to
 * be stored for any image processing to occur.
 */
public class PPMImageConverter extends AbstractImageConverter {

  /**
   * Constructs the ppm image reader by calling super from the abstract class.
   */
  public PPMImageConverter() {
    super();
  }

  /**
   * Allows the files to be read via a file path and information from that file to be sent
   * to the model.
   *
   * @param filePath path the image should be retrieved from
   * @throws IllegalArgumentException if file path is not found, file is not in the correct P3
   *                                  format for a ppm file or ppm file is invalid
   */
  public void reader(String filePath) {
    Scanner sc;
    try {
      sc = new Scanner(new FileInputStream(filePath));
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException("File is not found.");
    }
    StringBuilder builder = new StringBuilder();
    //read the file line by line, and populate a string. This will throw away any comment lines
    while (sc.hasNextLine()) {
      String s = sc.nextLine();
      if (s.length() > 0 && s.charAt(0) != '#') {
        builder.append(s + System.lineSeparator());
      }
    }
    sc = new Scanner(builder.toString());

    String token;
    try {
      token = sc.next();
      if (!token.equals("P3")) {
        throw new IllegalArgumentException("File is not in P3 format.");
      }
      this.width = sc.nextInt();
      this.height = sc.nextInt();
      int maxValue = sc.nextInt();
      for (int i = 0; i < height; i++) {
        List<Pixel> row = new ArrayList<>();
        for (int j = 0; j < width; j++) {
          int r = sc.nextInt();
          int g = sc.nextInt();
          int b = sc.nextInt();
          Pixel p = new PixelRGB(i, j, r, g, b);
          row.add(p);
        }
        this.pixelsList.add(row);
      }
    } catch (NoSuchElementException e) {
      throw new IllegalArgumentException("PPM file supplied is invalid.");
    }
  }

  /**
   * Saves the 2D arraylist of pixels by writing it to a file and saving the file to the
   * pathname. File exported will be in ppm format.
   *
   * @param pixelList 2D arraylist of pixels to be saved
   * @param pathName  path to save file to
   */
  public void save(List<List<Pixel>> pixelList, String pathName) {
    try {
      FileWriter newFile = new FileWriter(pathName);
      newFile.write("P3\n");
      newFile.write("# Created by IntelliJ\n");
      newFile.write(Integer.toString(pixelList.get(0).size()) + ' ' +
              pixelList.size() + "\n");
      newFile.write((this.getMax(pixelList)) + "\n");
      for (int i = 0; i < pixelList.size(); i++) {
        for (int j = 0; j < pixelList.get(i).size(); j++) {
          newFile.write(pixelList.get(i).get(j).getColorComp(0)
                  + "\n");
          newFile.write(pixelList.get(i).get(j).getColorComp(1)
                  + "\n");
          newFile.write(pixelList.get(i).get(j).getColorComp(2)
                  + "\n");
        }
      }
      newFile.close();
    } catch (IOException e) {
      throw new IllegalStateException("Output is not transmissible");
    }
  }

  /**
   * Returns the maximum color component found in the 2D arraylist of pixels.
   *
   * @param image 2D arraylist of pixels to be searched
   * @return maximum color component of image
   */
  private int getMax(List<List<Pixel>> image) {
    int max = 0;
    for (int i = 0; i < image.size(); i++) {
      for (int j = 0; j < image.get(i).size(); j++) {
        max = Math.max(max, image.get(i).get(j).getColorComp(0));
        max = Math.max(max, image.get(i).get(j).getColorComp(1));
        max = Math.max(max, image.get(i).get(j).getColorComp(2));
      }
    }
    return max;
  }


}



