import org.junit.Before;
import org.junit.Test;

import model.Pixel;
import model.PixelRGB;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Represents the testing class for the pixelRGB.
 */
public class PixelRGBTest {
  Pixel p1;
  Pixel p2;
  Pixel p3;
  Pixel p4;

  @Before
  public void initTest() {
    this.p1 = new PixelRGB(0,0,200, 187, 9);
    this.p2 = new PixelRGB(0,1,0, 255, 98);
    this.p3 = new PixelRGB(1,0,255, 82, 0);
    this.p4 = new PixelRGB(1,1,10, 26, 255);
  }

  @Test
  public void testConstructorInvalidPos() {
    try {
      new PixelRGB(-1,0,200,60,79);
    } catch (IllegalArgumentException e) {
      assertEquals("Position of the pixel can't be negative.", e.getMessage());
    }
    try {
      new PixelRGB(10,-2,210,255,0);
    } catch (IllegalArgumentException e) {
      assertEquals("Position of the pixel can't be negative.", e.getMessage());
    }
    try {
      new PixelRGB(-10,-20,90,26,255);
    } catch (IllegalArgumentException e) {
      assertEquals("Position of the pixel can't be negative.", e.getMessage());
    }
  }

  @Test
  public void testConstructorInvalidColorNeg() {
    try {
      new PixelRGB(0,0,-20, 9, 10);
      fail("Pixel is valid.");
    } catch (IllegalArgumentException e) {
      assertEquals("RGB components can't be negative or greater than 255.",
              e.getMessage());
    }
    try {
      new PixelRGB(2,10,19, -2, 0);
      fail("Pixel is valid.");
    } catch (IllegalArgumentException e) {
      assertEquals("RGB components can't be negative or greater than 255.",
              e.getMessage());
    }
    try {
      new PixelRGB(74,28,69, 201, -122);
      fail("Pixel is valid.");
    } catch (IllegalArgumentException e) {
      assertEquals("RGB components can't be negative or greater than 255.",
              e.getMessage());
    }
  }

  @Test
  public void testConstructorInvalidColorMax() {
    try {
      new PixelRGB(0,0,267, 93, 175);
      fail("Pixel is valid.");
    } catch (IllegalArgumentException e) {
      assertEquals("RGB components can't be negative or greater than 255.",
              e.getMessage());
    }
    try {
      new PixelRGB(5,2,23, 256, 94);
      fail("Pixel is valid.");
    } catch (IllegalArgumentException e) {
      assertEquals("RGB components can't be negative or greater than 255.",
              e.getMessage());
    }
    try {
      new PixelRGB(0,93,5, 90, 1000);
      fail("Pixel is valid.");
    } catch (IllegalArgumentException e) {
      assertEquals("RGB components can't be negative or greater than 255.",
              e.getMessage());
    }
  }


  @Test
  public void testGetColorComp() {
    assertEquals(200, this.p1.getColorComp(0));
    assertEquals(187, this.p1.getColorComp(1));
    assertEquals(9, this.p1.getColorComp(2));
    assertEquals(0, this.p2.getColorComp(0));
    assertEquals(255, this.p2.getColorComp(1));
    assertEquals(98, this.p2.getColorComp(2));
  }

  @Test
  public void testGetColorCompIllegal() {
    try {
      this.p1.getColorComp(-1);
    } catch (IllegalArgumentException e) {
      assertEquals("Color channel provided is not valid.", e.getMessage());
    }
  }

  @Test
  public void testGetPosX() {
    assertEquals(0, this.p1.getPosX());
    assertEquals(0, this.p2.getPosX());
    assertEquals(1, this.p3.getPosX());
    assertEquals(1, this.p4.getPosX());
  }

  @Test
  public void testSetPosX() {
    assertEquals(0, this.p1.getPosX());
    this.p1.setX(3);
    assertEquals(3, this.p1.getPosX());
  }

  @Test
  public void testGetPosY() {
    assertEquals(0, this.p1.getPosY());
    assertEquals(1, this.p2.getPosY());
    assertEquals(0, this.p3.getPosY());
    assertEquals(1, this.p4.getPosY());
  }

  @Test
  public void testSetPosY() {
    assertEquals(0, this.p1.getPosY());
    this.p1.setY(84);
    assertEquals(84, this.p1.getPosY());
  }

  @Test
  public void testAdd() {
    this.p1.add(56);
    assertEquals(255, this.p1.getColorComp(0));
    assertEquals(243, this.p1.getColorComp(1));
    assertEquals(65, this.p1.getColorComp(2));
    this.p2.add(-100);
    assertEquals(0, this.p2.getColorComp(0));
    assertEquals(155, this.p2.getColorComp(1));
    assertEquals(0, this.p2.getColorComp(2));
  }


  @Test
  public void setColors() {
    this.p3.setColors(46);
    assertEquals(46, this.p3.getColorComp(0));
    assertEquals(46, this.p3.getColorComp(1));
    assertEquals(46, this.p3.getColorComp(2));
  }

  @Test
  public void setOneColor() {
    this.p3.setOneColor(0, 45);
    assertEquals(45, this.p3.getColorComp(0));
    assertEquals(82, this.p3.getColorComp(1));
    assertEquals(0, this.p3.getColorComp(2));
  }

  @Test
  public void getColorCopy() {
    assertEquals(0, this.p2.getColorsCopy()[0]);
    assertEquals(255, this.p2.getColorsCopy()[1]);
    assertEquals(98, this.p2.getColorsCopy()[2]);
  }

  @Test
  public void testGetCopy() {
    assertEquals(1, this.p3.getPixelCopy().getPosX());
    assertEquals(0, this.p3.getPixelCopy().getPosY());
    assertEquals(255, this.p3.getPixelCopy().getColorComp(0));
    assertEquals(82, this.p3.getPixelCopy().getColorComp(1));
    assertEquals(0, this.p3.getPixelCopy().getColorComp(2));
  }


}