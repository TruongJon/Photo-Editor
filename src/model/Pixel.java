package model;

/**
 * Represents a pixel within an image. This pixel representation uses an 8-bit format with 3 color
 * channels (Red, Green, Blue).
 */
public class Pixel implements IPixel {

  private final Posn position;
  private final int r;
  private final int g;
  private final int b;

  /**
   * Constructs a pixel within an image. Pixels are always "clamped" meaning that any given channel
   * value must be represented as an 8-bit number. This allowed for at most 256 int values
   * (enforcing channel values to be between 0 and 255).
   *
   * @param position the pixels x and y position within the image
   * @param r        amount representing red between 0 and 255 inclusive
   * @param g        amount representing green between 0 and 255 inclusive
   * @param b        amount representing blue between 0 and 255 inclusive
   */
  public Pixel(Posn position, int r, int g, int b) {
    this.position = position;
    // clamps channel value for r to be within specified range
    if (r < 0) {
      this.r = 0;
    } else if (r > 255) {
      this.r = 255;
    } else {
      this.r = r;
    }

    // clamps channel value for g to be within specified range
    if (g < 0) {
      this.g = 0;
    } else if (g > 255) {
      this.g = 255;
    } else {
      this.g = g;
    }

    // clamps channel value for b to be within specified range
    if (b < 0) {
      this.b = 0;
    } else if (b > 255) {
      this.b = 255;
    } else {
      this.b = b;
    }
  }

  @Override
  public IPixel applyKernelFilter(KernelMatrix kernel, KernelMatrix neighbors, Channel color) {
    double[][] filter = kernel.getKernel();
    double[][] neighborMatrix = neighbors.getKernel();
    int newR = this.r;
    int newG = this.g;
    int newB = this.b;
    int newVal = 0;

    // iterates through all values in 2D array
    for (int i = 0; i < filter.length; i++) {
      for (int j = 0; j < filter.length; j++) {
        newVal += (int) Math.round(filter[j][i] * neighborMatrix[j][i]);
      }
    }

    switch (color) {
      case RED:
        newR = newVal;
        break;
      case GREEN:
        newG = newVal;
        break;
      case BLUE:
        newB = newVal;
        break;
      default:
        throw new IllegalArgumentException("Must be a valid 8-bit color channel.");
    }
    return new Pixel(this.position, newR, newG, newB);
  }

  @Override
  public IPixel applyKernelColor(KernelMatrix kernel) {
    int newR;
    int newG;
    int newB;
    double[][] kernelArr = kernel.getKernel();

    // creates a new r value after applying the linear color transformation
    newR = (int) Math.round(
        kernelArr[0][0] * this.getR() + kernelArr[1][0] * this.getG() + kernelArr[2][0] * this
            .getB());

    // creates a new g value after applying the linear color transformation
    newG = (int) Math.round(
        kernelArr[0][1] * this.getR() + kernelArr[1][1] * this.getG() + kernelArr[2][1] * this
            .getB());

    // creates a new b value after applying the linear color transformation
    newB = (int) Math.round(
        kernelArr[0][2] * this.getR() + kernelArr[1][2] * this.getG() + kernelArr[2][2] * this
            .getB());

    return new Pixel(this.getPosition(), newR, newG, newB);
  }

  @Override
  public Posn getPosition() {
    return this.position;
  }

  @Override
  public int getR() {
    return this.r;
  }

  @Override
  public int getG() {
    return this.g;
  }

  @Override
  public int getB() {
    return this.b;
  }
}
