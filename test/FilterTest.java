import static org.junit.Assert.assertEquals;

import java.awt.Color;
import model.Channel;
import model.DefaultKernel;
import model.Filter;
import model.IImage;
import model.IPixel;
import model.ITransform;
import model.KernelMatrix;
import model.PPMImage;
import org.junit.Test;

/**
 * Test class for Filter: Unit tests to ensure the accuracy of methods and constructors within
 * the Filter class.
 */
public class FilterTest {

  ITransform filterObject = new Filter();
  IImage checkerboard = new PPMImage(2, 2, Color.RED, Color.BLUE);
  IImage checkerboardMixColors = new PPMImage(2, 2, Color.PINK, Color.YELLOW);
  KernelMatrix blurKernel = DefaultKernel.BLUR.getDefaultKernel();
  KernelMatrix sharpKernel = DefaultKernel.SHARPEN.getDefaultKernel();

  // test for the apply method when using a blurring filter
  @Test
  public void testApplyWithBlurKernel() {

    IPixel[][] pixelsBefore = checkerboard.getPixels();

    // test the pixel color channels prior to applying the blurring effect
    assertEquals(255, pixelsBefore[0][0].getR());
    assertEquals(255, pixelsBefore[1][0].getR());
    assertEquals(255, pixelsBefore[1][1].getR());
    assertEquals(255, pixelsBefore[2][0].getB());
    assertEquals(255, pixelsBefore[2][1].getB());
    assertEquals(255, pixelsBefore[3][1].getB());
    assertEquals(0, pixelsBefore[0][0].getG());
    assertEquals(0, pixelsBefore[2][0].getG());

    IImage redBlur = filterObject.apply(checkerboard, blurKernel, Channel.RED);
    IImage greenBlur = filterObject.apply(redBlur, blurKernel, Channel.GREEN);
    IImage allBlur = filterObject.apply(greenBlur, blurKernel, Channel.BLUE);

    IPixel[][] blurredPixels = allBlur.getPixels();

    assertEquals(144, blurredPixels[0][0].getR());
    assertEquals(144, blurredPixels[1][0].getR());
    assertEquals(160, blurredPixels[1][1].getR());
    assertEquals(144, blurredPixels[2][0].getB());
    assertEquals(160, blurredPixels[2][1].getB());
    assertEquals(144, blurredPixels[3][1].getB());
    assertEquals(0, blurredPixels[0][0].getG());
    assertEquals(0, blurredPixels[2][0].getG());
  }

  // test for apply method when using a sharpen filter
  @Test
  public void testApplyWithSharpKernel() {

    IPixel[][] pixelsBefore = checkerboardMixColors.getPixels();

    // test the pixel color channels prior to applying the sharpening effect
    assertEquals(255, pixelsBefore[0][0].getR());
    assertEquals(255, pixelsBefore[1][0].getR());
    assertEquals(255, pixelsBefore[1][1].getR());
    assertEquals(0, pixelsBefore[2][0].getB());
    assertEquals(0, pixelsBefore[2][1].getB());
    assertEquals(0, pixelsBefore[3][1].getB());
    assertEquals(175, pixelsBefore[0][0].getG());
    assertEquals(255, pixelsBefore[2][0].getG());

    IImage redSharp = filterObject.apply(checkerboardMixColors, sharpKernel, Channel.RED);
    IImage greenSharp = filterObject.apply(redSharp, sharpKernel, Channel.GREEN);
    IImage allSharp = filterObject.apply(greenSharp, sharpKernel, Channel.BLUE);

    IPixel[][] blurredPixels = allSharp.getPixels();

    assertEquals(255, blurredPixels[0][0].getR());
    assertEquals(255, blurredPixels[1][0].getR());
    assertEquals(255, blurredPixels[1][1].getR());
    assertEquals(0, blurredPixels[2][0].getB());
    assertEquals(88, blurredPixels[2][1].getB());
    assertEquals(0, blurredPixels[3][1].getB());
    assertEquals(157, blurredPixels[0][0].getG());
    assertEquals(255, blurredPixels[2][0].getG());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullImage() {
    filterObject.apply(null, blurKernel, Channel.RED);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullKernel() {
    filterObject.apply(checkerboard, null, Channel.RED);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullChannel() {
    filterObject.apply(checkerboard, blurKernel, null);
  }

}
