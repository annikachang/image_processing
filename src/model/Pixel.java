package model;

/**
 * Represents one pixel, which consists of a position (X, Y) to indicate where the pixel
 * is located on the image. The X is similar to the column number and the Y is similar to the
 * row number on a 2D board. Also stores the pixel color components in an array in the order of
 * red, green and blue, so an array at index 0 will return the red component.
 */
public interface Pixel {

  /**
   * Sets this Pixel's y position.
   *
   * @param y value to set y to
   */
  void setY(int y);

  /**
   * Sets this Pixel's x position.
   *
   * @param x value to set y to
   */
  void setX(int x);

  /**
   * Adds value to every color component. A positive or negative number can be supplied.
   * The resulting components are clamped at 255 which is the maximum possible value for
   * an 8-bit representation. Additionally, no resulting components can be less than 0 so all
   * components are clamped to 0 as well if constant is negative.
   *
   * @param constant Value to add
   */
  void add(int constant);

  /**
   * Sets all the color components (red, green and blue) to the given value.
   *
   * @param color value to set colors to
   */
  void setColors(int color);

  /**
   * Sets one color of the pixel's color components by providing an index for the color to set in
   * the array and the desired color component.
   *
   * @param colorComp index of color
   * @param color     color component
   */
  void setOneColor(int colorComp, int color);

  /**
   * Getter for the value of the channel. A color channel provided of 0-2 will return the
   * red, green and blue components, respectively. And a color channel provided of 3-5 will
   * return the value, luma and intensity component, respectively.
   *
   * @param colorChannel component you need value of
   * @return int value of that component
   * @throws IllegalArgumentException if color channel supplied is negative or greater than the
   *                                  array size
   */
  int getColorComp(int colorChannel) throws IllegalArgumentException;

  /**
   * Returns a copy of the color components as an array.
   *
   * @return array of colors
   */
  int[] getColorsCopy();

  /**
   * Getter to get X position of this pixel.
   *
   * @return x value
   */
  int getPosX();

  /**
   * Getter to get Y position of this pixel.
   *
   * @return y value
   */
  int getPosY();

  /**
   * Returns a copy of the pixel with its position, and color components (red, green and blue).
   *
   * @return copy of as a Pixel object
   */
  Pixel getPixelCopy();

}

