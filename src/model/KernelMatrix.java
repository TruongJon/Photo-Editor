package model;

/**
 * Represents a "kernel" for applying a filter to a set of pixels within an image. A kernel is a
 * 2-dimensional array with double values that represent what values each pixel in an image must be
 * transformed with to create a new image.
 */
public class KernelMatrix {

  private final double[][] array;
  private final int size;

  /**
   * Builds a valid kernel of specified size dimensions. A kernel in code is a 2D-array of doubles
   * in which any given size results in a square shape. A kernel must have a middle, meaning that it
   * must have odd dimensions and be the same for height and width (i.e. 3 by 3).
   *
   * @param size the width and height of the kernel. Must be odd and positive.
   * @throws IllegalArgumentException when the size dimensions are not odd or lower than 1.
   */
  public KernelMatrix(int size) throws IllegalArgumentException {
    if (size % 2 == 0 || size < 0) {
      throw new IllegalArgumentException("Kernel must have odd dimensions.");
    }
    array = new double[size][size];
    this.size = size;
  }

  /**
   * Sets a value within this kernel at the given x and y position.
   *
   * @param position the x and y index within the kernel represented as a Posn (position)
   * @param value    the double value to be set
   * @throws IndexOutOfBoundsException when x or y of the position is out of bounds given the size
   *                                   of this KernelMatrix (i.e. must be positive and less than
   *                                   this kernel size)
   */
  public void setValue(Posn position, double value) throws IndexOutOfBoundsException {
    if (position.getX() >= size || position.getY() >= size || position.getX() < 0
        || position.getY() < 0) {
      throw new IndexOutOfBoundsException("Position must be within the bounds of the array.");
    }
    this.array[position.getX()][position.getY()] = value;
  }

  /**
   * Gets the final kernel after setting all necessary values. Does not check for incorrectly set
   * values within the kernel. Will return exactly what the user has set the kernel as.
   *
   * @return this array representing the kernel filter
   */
  public double[][] getKernel() {
    return this.array;
  }
}
