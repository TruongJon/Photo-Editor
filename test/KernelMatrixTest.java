import static org.junit.Assert.assertEquals;

import model.KernelMatrix;
import model.Posn;
import org.junit.Test;

/**
 * Test class for KernelMatrix: Unit tests to ensure the accuracy of methods and constructors within
 * the KernelMatrix class.
 */
public class KernelMatrixTest {

  @Test(expected = IllegalArgumentException.class)
  public void testEvenSizedKernel() {
    new KernelMatrix(2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNegativeSizedKernel() {
    new KernelMatrix(-1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testZeroSizedKernel() {
    new KernelMatrix(0);
  }

  @Test
  public void setValue() {
    KernelMatrix testKernel = new KernelMatrix(3);

    // set specific indices in the 2D array kernel matrix to some values,
    // all un-set indices should remain 0.0
    testKernel.setValue(new Posn(0, 0), 3.0);
    testKernel.setValue(new Posn(1, 1), -2.0);
    testKernel.setValue(new Posn(2, 2), 3.0 / 10.0);

    assertEquals(3.0, testKernel.getKernel()[0][0], 0.001);
    assertEquals(-2.0, testKernel.getKernel()[1][1], 0.001);
    assertEquals(.3, testKernel.getKernel()[2][2], 0.001);

    // set a value already at zero to zero and re-set a value that has already been set
    testKernel.setValue(new Posn(0, 1), 0.0);
    testKernel.setValue(new Posn(2, 2), -1.0 / 8.0);

    assertEquals(0.0, testKernel.getKernel()[0][1], 0.001);
    assertEquals(-0.125, testKernel.getKernel()[2][2], 0.001);

    // a position in the 2D array that has not yet been set should remain as 0.0
    assertEquals(0.0, testKernel.getKernel()[1][2], 0.001);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void testOutOfBoundsHighXPosition() {
    KernelMatrix testKernel = new KernelMatrix(5);
    testKernel.setValue(new Posn(5, 2), 1.0);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void testOutOfBoundsLowXPosition() {
    KernelMatrix testKernel = new KernelMatrix(5);
    testKernel.setValue(new Posn(-1, 2), 1.0);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void testOutOfBoundsHighYPosition() {
    KernelMatrix testKernel = new KernelMatrix(5);
    testKernel.setValue(new Posn(2, 5), 1.0);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void testOutOfBoundsLowYPosition() {
    KernelMatrix testKernel = new KernelMatrix(5);
    testKernel.setValue(new Posn(2, -1), 1.0);
  }

  @Test
  public void getKernel() {
    KernelMatrix testKernel = new KernelMatrix(3);

    // set specific indices in the 2D array kernel matrix to some values,
    // all un-set indices should remain 0.0
    testKernel.setValue(new Posn(0, 1), 1.0);
    testKernel.setValue(new Posn(2, 1), -2.5);
    testKernel.setValue(new Posn(0, 2), 3.0 / 20.0);

    // the values at these positions in the 2D array should be as follows
    assertEquals(1.0, testKernel.getKernel()[0][1], 0.001);
    assertEquals(-2.5, testKernel.getKernel()[2][1], 0.001);
    assertEquals(.15, testKernel.getKernel()[0][2], 0.001);
  }
}