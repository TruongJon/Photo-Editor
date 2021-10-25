package model;

/**
 * Enumeration to represent different set kernels that can be applied to an image. <p>The BLUR and
 * SHARPEN default kernels are designed to be types of filters.</p><p>The GREYSCALE and SEPIA
 * default kernels are designed to be types of color transformations.</p>
 */
public enum DefaultKernel {

  BLUR(makeBlur()), SHARPEN(makeSharpen()), GREYSCALE(makeGreyscale()), SEPIA(makeSepia()), BLANK(
      makeBlank());

  private final KernelMatrix defaultKernel;

  /**
   * Creates a set kernel based on a filter or color transformation that can be applied to an
   * image.
   *
   * @param defaultKernel the KernelMatrix for the filter being applied to an image
   */
  DefaultKernel(KernelMatrix defaultKernel) {
    this.defaultKernel = defaultKernel;
  }

  /**
   * Accesses the KernelMatrix field of the DefaultKernel enumeration.
   *
   * @return the default kernel's kernel matrix for either filtering or color transforming
   */
  public KernelMatrix getDefaultKernel() {
    return this.defaultKernel;
  }

  private static KernelMatrix makeBlur() {
    KernelMatrix blurKernel = new KernelMatrix(3);
    blurKernel.setValue(new Posn(0, 0), 1.0 / 16.0);
    blurKernel.setValue(new Posn(1, 0), 1.0 / 8.0);
    blurKernel.setValue(new Posn(2, 0), 1.0 / 16.0);
    blurKernel.setValue(new Posn(0, 1), 1.0 / 8.0);
    blurKernel.setValue(new Posn(1, 1), 1.0 / 4.0);
    blurKernel.setValue(new Posn(2, 1), 1.0 / 8.0);
    blurKernel.setValue(new Posn(0, 2), 1.0 / 16.0);
    blurKernel.setValue(new Posn(1, 2), 1.0 / 8.0);
    blurKernel.setValue(new Posn(2, 2), 1.0 / 16.0);
    return blurKernel;
  }

  private static KernelMatrix makeSharpen() {
    KernelMatrix sharpenKernel = new KernelMatrix(5);
    sharpenKernel.setValue(new Posn(0, 0), -1.0 / 8.0);
    sharpenKernel.setValue(new Posn(1, 0), -1.0 / 8.0);
    sharpenKernel.setValue(new Posn(2, 0), -1.0 / 8.0);
    sharpenKernel.setValue(new Posn(3, 0), -1.0 / 8.0);
    sharpenKernel.setValue(new Posn(4, 0), -1.0 / 8.0);
    sharpenKernel.setValue(new Posn(0, 1), -1.0 / 8.0);
    sharpenKernel.setValue(new Posn(1, 1), 1.0 / 4.0);
    sharpenKernel.setValue(new Posn(2, 1), 1.0 / 4.0);
    sharpenKernel.setValue(new Posn(3, 1), 1.0 / 4.0);
    sharpenKernel.setValue(new Posn(4, 1), -1.0 / 8.0);
    sharpenKernel.setValue(new Posn(0, 2), -1.0 / 8.0);
    sharpenKernel.setValue(new Posn(1, 2), 1.0 / 4.0);
    sharpenKernel.setValue(new Posn(2, 2), 1);
    sharpenKernel.setValue(new Posn(3, 2), 1.0 / 4.0);
    sharpenKernel.setValue(new Posn(4, 2), -1.0 / 8.0);
    sharpenKernel.setValue(new Posn(0, 3), -1.0 / 8.0);
    sharpenKernel.setValue(new Posn(1, 3), 1.0 / 4.0);
    sharpenKernel.setValue(new Posn(2, 3), 1.0 / 4.0);
    sharpenKernel.setValue(new Posn(3, 3), 1.0 / 4.0);
    sharpenKernel.setValue(new Posn(4, 3), -1.0 / 8.0);
    sharpenKernel.setValue(new Posn(0, 4), -1.0 / 8.0);
    sharpenKernel.setValue(new Posn(1, 4), -1.0 / 8.0);
    sharpenKernel.setValue(new Posn(2, 4), -1.0 / 8.0);
    sharpenKernel.setValue(new Posn(3, 4), -1.0 / 8.0);
    sharpenKernel.setValue(new Posn(4, 4), -1.0 / 8.0);
    return sharpenKernel;
  }

  private static KernelMatrix makeGreyscale() {
    KernelMatrix greyscaleKernel = new KernelMatrix(3);
    greyscaleKernel.setValue(new Posn(0, 0), 0.2126);
    greyscaleKernel.setValue(new Posn(1, 0), 0.7152);
    greyscaleKernel.setValue(new Posn(2, 0), 0.0722);
    greyscaleKernel.setValue(new Posn(0, 1), 0.2126);
    greyscaleKernel.setValue(new Posn(1, 1), 0.7152);
    greyscaleKernel.setValue(new Posn(2, 1), 0.0722);
    greyscaleKernel.setValue(new Posn(0, 2), 0.2126);
    greyscaleKernel.setValue(new Posn(1, 2), 0.7152);
    greyscaleKernel.setValue(new Posn(2, 2), 0.0722);
    return greyscaleKernel;
  }

  private static KernelMatrix makeSepia() {
    KernelMatrix sepiaKernel = new KernelMatrix(3);
    sepiaKernel.setValue(new Posn(0, 0), 0.393);
    sepiaKernel.setValue(new Posn(1, 0), 0.769);
    sepiaKernel.setValue(new Posn(2, 0), 0.189);
    sepiaKernel.setValue(new Posn(0, 1), 0.349);
    sepiaKernel.setValue(new Posn(1, 1), 0.686);
    sepiaKernel.setValue(new Posn(2, 1), 0.168);
    sepiaKernel.setValue(new Posn(0, 2), 0.272);
    sepiaKernel.setValue(new Posn(1, 2), 0.534);
    sepiaKernel.setValue(new Posn(2, 2), 0.131);
    return sepiaKernel;
  }

  private static KernelMatrix makeBlank() {
    return new KernelMatrix(1);
  }
}
