import static org.junit.Assert.assertEquals;

import model.DefaultKernel;
import model.KernelMatrix;
import org.junit.Before;
import org.junit.Test;


/**
 * Test class for DefaultKernel: Unit tests to ensure the accuracy of methods and constructors
 * within the DefaultKernel class.
 */
public class DefaultKernelTest {

  KernelMatrix blurKernel;
  KernelMatrix sharpKernel;
  KernelMatrix greyKernel;
  KernelMatrix sepiaKernel;

  @Before
  public void init() {
    blurKernel = DefaultKernel.BLUR.getDefaultKernel();
    sharpKernel = DefaultKernel.SHARPEN.getDefaultKernel();
    greyKernel = DefaultKernel.GREYSCALE.getDefaultKernel();
    sepiaKernel = DefaultKernel.SEPIA.getDefaultKernel();
  }

  @Test
  public void testGetDefaultKernel() {
    init();

    assertEquals(0.0625, blurKernel.getKernel()[0][0], 0.001);
    assertEquals(0.125, blurKernel.getKernel()[1][0], 0.001);
    assertEquals(0.25, blurKernel.getKernel()[1][1], 0.001);

    assertEquals(-0.125, sharpKernel.getKernel()[0][0], 0.001);
    assertEquals(0.25, sharpKernel.getKernel()[1][1], 0.001);
    assertEquals(0.25, sharpKernel.getKernel()[1][3], 0.001);
    assertEquals(1.0, sharpKernel.getKernel()[2][2], 0.001);
    assertEquals(0.25, sharpKernel.getKernel()[1][3], 0.001);
    assertEquals(0.25, sharpKernel.getKernel()[3][3], 0.001);
    assertEquals(-0.125, sharpKernel.getKernel()[4][3], 0.001);

    assertEquals(0.2126, greyKernel.getKernel()[0][0], 0.001);
    assertEquals(0.7152, greyKernel.getKernel()[1][1], 0.001);
    assertEquals(0.0722, greyKernel.getKernel()[2][2], 0.001);

    assertEquals(0.393, sepiaKernel.getKernel()[0][0], 0.001);
    assertEquals(0.686, sepiaKernel.getKernel()[1][1], 0.001);
    assertEquals(0.131, sepiaKernel.getKernel()[2][2], 0.001);
  }
}
