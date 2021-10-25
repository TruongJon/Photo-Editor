import static org.junit.Assert.assertEquals;

import java.awt.Color;
import model.Channel;
import model.ColorTransform;
import model.DefaultKernel;
import model.IImage;
import model.IPixel;
import model.ITransform;
import model.KernelMatrix;
import model.PPMImage;
import org.junit.Test;

/**
 * Test class for ColorTransform: Unit tests to ensure the accuracy of methods and constructors
 * within the ColorTransform class.
 */
public class ColorTransformTest {

  ITransform colorTransformObject = new ColorTransform();
  IImage checkerboard = new PPMImage(2, 2, Color.PINK, Color.YELLOW);
  KernelMatrix sharpKernel = DefaultKernel.SHARPEN.getDefaultKernel();
  KernelMatrix greyKernel = DefaultKernel.GREYSCALE.getDefaultKernel();
  KernelMatrix sepiaKernel = DefaultKernel.SEPIA.getDefaultKernel();

  // test for the apply method when using a greyscale color transformation
  @Test
  public void testApplyWithGreyscaleKernel() {

    IPixel[][] pixelsBefore = checkerboard.getPixels();

    // test the pixel color channels prior to applying the greyscale effect
    assertEquals(255, pixelsBefore[0][0].getR());
    assertEquals(255, pixelsBefore[1][0].getR());
    assertEquals(255, pixelsBefore[1][1].getR());
    assertEquals(0, pixelsBefore[2][0].getB());
    assertEquals(0, pixelsBefore[2][1].getB());
    assertEquals(0, pixelsBefore[3][1].getB());
    assertEquals(175, pixelsBefore[0][0].getG());
    assertEquals(255, pixelsBefore[2][0].getG());

    IImage redGreyscale = colorTransformObject.apply(checkerboard, greyKernel, Channel.RED);
    IImage greenGreyscale = colorTransformObject.apply(redGreyscale, greyKernel, Channel.GREEN);
    IImage allGreyscale = colorTransformObject.apply(greenGreyscale, greyKernel, Channel.BLUE);

    IPixel[][] greyscalePixels = allGreyscale.getPixels();

    assertEquals(192, greyscalePixels[0][0].getR());
    assertEquals(192, greyscalePixels[1][0].getR());
    assertEquals(192, greyscalePixels[1][1].getR());
    assertEquals(237, greyscalePixels[2][0].getB());
    assertEquals(237, greyscalePixels[2][1].getB());
    assertEquals(237, greyscalePixels[3][1].getB());
    assertEquals(192, greyscalePixels[0][0].getG());
    assertEquals(237, greyscalePixels[2][0].getG());
  }

  // test for apply method when using a sharpen filter
  @Test
  public void testApplyWithSharpKernel() {

    IPixel[][] pixelsBefore = checkerboard.getPixels();

    // test the pixel color channels prior to applying the sharpening effect
    assertEquals(255, pixelsBefore[0][0].getR());
    assertEquals(255, pixelsBefore[1][0].getR());
    assertEquals(255, pixelsBefore[1][1].getR());
    assertEquals(0, pixelsBefore[2][0].getB());
    assertEquals(0, pixelsBefore[2][1].getB());
    assertEquals(0, pixelsBefore[3][1].getB());
    assertEquals(175, pixelsBefore[0][0].getG());
    assertEquals(255, pixelsBefore[2][0].getG());

    IImage redSepia = colorTransformObject.apply(checkerboard, sepiaKernel, Channel.RED);
    IImage greenSepia = colorTransformObject.apply(redSepia, sepiaKernel, Channel.GREEN);
    IImage allSepia = colorTransformObject.apply(greenSepia, sepiaKernel, Channel.BLUE);

    IPixel[][] sepiaPixels = allSepia.getPixels();

    assertEquals(255, sepiaPixels[0][0].getR());
    assertEquals(255, sepiaPixels[1][0].getR());
    assertEquals(255, sepiaPixels[1][1].getR());
    assertEquals(236, sepiaPixels[2][0].getB());
    assertEquals(236, sepiaPixels[2][1].getB());
    assertEquals(236, sepiaPixels[3][1].getB());
    assertEquals(255, sepiaPixels[0][0].getG());
    assertEquals(255, sepiaPixels[2][0].getG());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullImage() {
    colorTransformObject.apply(null, greyKernel, Channel.RED);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullKernel() {
    colorTransformObject.apply(checkerboard, null, Channel.RED);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullChannel() {
    colorTransformObject.apply(checkerboard, greyKernel, null);
  }

  // kernel used in color transformation must have size 3 to be valid
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidKernel() {
    colorTransformObject.apply(checkerboard, sharpKernel, Channel.RED);
  }
}