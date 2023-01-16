package model;


/**
 * This class represents the most common representation of a pixel which is the red-green-blue
 * or RGB model. Each of these channels have a number or component attached and the number
 * is stored in an array summarized in the interface. Additionally, this pixel also
 * stores the value, luma and intensity component of a pixel so grey-scaling, if wanted, is
 * easier. The value, luma and intensity components are retrieved from an array through index
 * 3, 4, and 5, respectively.
 * Each pixel's color on an image is composed of these three channels. For example, a pixel with
 * a red, green and blue component of 0,0,0 would create black.
 */
public class PixelRGB implements Pixel {
  private int x;
  private int y;

  private final int[] channel = new int[6];

  /**
   * This constructor allows the pixel objects to be created. The RGB components are stored
   * into an array where index 0 is the red component, index 1 is the green component and
   * index 2 is the blue component. Additional components (value, luma and intensity) are stored
   * in the same array, respectively.
   * The pixels row and column position has to be positive. Additionally, each RGB component has
   * to be positive ad less than or equal to 255. An exception will be thrown otherwise.
   *
   * @param x     row number
   * @param y     column number
   * @param rComp red component of pixel
   * @param gComp green component of pixel
   * @param bComp blue component of pixel
   * @throws IllegalArgumentException if pixel does not meet conditions stated above
   */
  public PixelRGB(int x, int y, int rComp, int gComp, int bComp) {
    if (x < 0 || y < 0) {
      throw new IllegalArgumentException("Position of the pixel can't be negative.");
    }

    if (rComp < 0 || rComp > 255 || gComp < 0 || gComp > 255
            || bComp < 0 || bComp > 255) {
      throw new IllegalArgumentException("RGB components can't be negative or greater than 255.");
    }
    this.x = x;
    this.y = y;
    this.channel[0] = rComp;
    this.channel[1] = gComp;
    this.channel[2] = bComp;
    this.setValue();
    this.setLuma();
    this.setIntensity();
  }

  @Override
  public int getColorComp(int colorChannel) {
    if (colorChannel < 0 || colorChannel > this.channel.length) {
      throw new IllegalArgumentException("Color channel provided is not valid.");
    }
    return this.channel[colorChannel];
  }

  @Override
  public int[] getColorsCopy() {
    int[] colorResult = new int[3];
    for (int i = 0; i < colorResult.length; i++) {
      colorResult[i] = channel[i];
    }
    return colorResult;
  }

  @Override
  public int getPosX() {
    return this.x;
  }

  @Override
  public void setX(int x) {
    this.x = x;
  }

  @Override
  public int getPosY() {
    return this.y;
  }

  @Override
  public void setY(int y) {
    this.y = y;
  }

  @Override
  public void add(int constant) {
    if (constant >= 0) {
      this.channel[0] = Math.min(this.getColorComp(0) + constant, 255);
      this.channel[1] = Math.min(this.getColorComp(1) + constant, 255);
      this.channel[2] = Math.min(this.getColorComp(2) + constant, 255);
    } else if (constant < 0) {
      this.channel[0] = Math.max(this.getColorComp(0) + constant, 0);
      this.channel[1] = Math.max(this.getColorComp(1) + constant, 0);
      this.channel[2] = Math.max(this.getColorComp(2) + constant, 0);
    }
  }

  /**
   * Finds the maximum value of all the color components (red, green and blue). Sets the
   * maximum value found to the array list at index 3.
   */
  private void setValue() {
    int value = Math.max(Math.max(this.getColorComp(0),
                    this.getColorComp(1)),
            this.getColorComp(2));
    this.channel[3] = value;
  }


  /**
   * Calculates the luma component of the pixel from the color components through
   * the luma formula (.2126r + .7152g + .0722b) where r, g and b stand for the red, green and
   * blue components of a pixel. The resulting luma calculation is rounded and then made into an
   * int, then set to the fourth index in the channel array.
   */
  private void setLuma() {
    int luma = (int) Math.round(.2126 * this.getColorComp(0) +
            .7152 * this.getColorComp(1) +
            .0722 * this.getColorComp(2));
    this.channel[4] = luma;
  }

  /**
   * Calculates the average of all components (red, green and blue) in the pixel. Sets the
   * calculated intensity component to index 5 in the channel array.
   */
  private void setIntensity() {
    int intensity = (this.getColorComp(0) + this.getColorComp(1)
            + this.getColorComp(2)) / 3;
    this.channel[5] = intensity;
  }

  @Override
  public void setColors(int color) {
    this.channel[0] = color;
    this.channel[1] = color;
    this.channel[2] = color;
  }

  @Override
  public void setOneColor(int colorComp, int color) {
    this.channel[colorComp] = color;
  }

  @Override
  public Pixel getPixelCopy() {
    return new PixelRGB(this.x, this.y, this.getColorComp(0),
            this.getColorComp(1), this.getColorComp(2));
  }


}


