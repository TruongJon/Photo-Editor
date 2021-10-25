package model;

import java.awt.image.BufferedImage;

/**
 * Represents an image that has the capability for various transformations to be applied to it. This
 * is done through calculating new red, green, and blue, 8-bit channel values for each individual
 * pixel within this image.
 */
public interface IImage {

  /**
   * Creates a new image based on the application of a kernel to this image. In this method only one
   * specified channel is transformed for each pixel within the image.
   *
   * @param transform a transform object representing the type of transformation desired for this
   *                  image
   * @param kernel    the 2D kernel matrix to be applied to this image
   * @param color     the channel of the pixel to apply the kernel to
   * @return a new image created by applying a given transformation and kernel to this image
   * @throws IllegalArgumentException if the transform, kernel, or color objects are null
   */
  IImage applyTransformation(ITransform transform, KernelMatrix kernel, Channel color)
      throws IllegalArgumentException;

  /**
   * Creates a new image based on the application of a kernel to this image. The kernel is applied
   * to all three channels of this image.
   *
   * @param transform a transform object representing the type of transformation desired for this
   *                  image
   * @param kernel    the 2D kernel matrix to be applied to this image
   * @return a new image created by applying a given transformation and kernel to this image
   * @throws IllegalArgumentException if transform or kernel are null
   */
  IImage applyTransformation(ITransform transform, KernelMatrix kernel)
      throws IllegalArgumentException;

  /**
   * Finds the neighboring pixels of the given pixel in this image and puts the neighboring pixels
   * channel color values into a KernelMatrix with specified size.
   *
   * @param pixel the target pixel to find the neighboring pixels of
   * @param color the target channel of the pixels being transformed
   * @param size  the size of the kernel matrix being created and returned
   * @return a matrix with values corresponding to the color values in neighboring pixels channels
   * @throws IllegalArgumentException when pixel is null or channel is either null or invalid
   */
  KernelMatrix findNeighbors(IPixel pixel, Channel color, int size)
      throws IllegalArgumentException;

  /**
   * Gets the pixels from this image object.
   *
   * @return the images pixels represented as a 2D array of IPixels
   */
  IPixel[][] getPixels();

  /**
   * Factory method to create an image object based on the original image before applying a
   * transformation.
   *
   * @param pixels the pixels of the image being created
   * @return a new image object created through dynamic dispatch to the proper IImage class
   */
  IImage getImage(IPixel[][] pixels);

  /**
   * Creates a string representation of this image.
   *
   * @return the string representation of this image
   */
  String sendToString();

  /**
   * Gets the visibility of this image.
   *
   * @return this images visibility
   */
  boolean getVisibility();

  /**
   * Sets the visibility of this image to new value given.
   *
   * @param newVisibility the new visibility of this image
   */
  void setVisibility(boolean newVisibility);

  /**
   * Gets the name of this image.
   *
   * @return this images name
   */
  String getName();

  /**
   * Sets the name of this image to new string given.
   *
   * @param newName the new name of this image
   * @throws IllegalArgumentException if the given new name is null
   */
  void setName(String newName) throws IllegalArgumentException;

  /**
   * Converts this image into a BufferedImage object. This is done in order to standardize the image
   * and make it more compatible with Java's built in classes.
   *
   * @return a BufferedImage representation of this image
   */
  BufferedImage convertImage();
}
