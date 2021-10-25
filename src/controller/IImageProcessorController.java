package controller;

import model.IImage;

/**
 * Interface for the image processor controller. An implementation will work with the
 * IImageProcessor interface to create a working image processor.
 */
public interface IImageProcessorController extends IController {

  void startImageProcessor(String currentLayer) throws IllegalArgumentException;

  /**
   * Parses through an image file and creates a single string that can be interpreted by the model
   * and initialized as an image object.
   *
   * @param filename the image file name and/or path directory leading to the image file
   * @param fileType the type of image file being read
   * @return a string representation of the image that can be interpreted by the model
   * @throws IllegalArgumentException if filename is null or fileType is unrecognized
   */
  IImage readImage(String filename, FileType fileType) throws IllegalArgumentException;

  /**
   * Exports this image to the local computer either to the specified directory or to the same level
   * as the source folder if directory is not specified.
   *
   * @param image    the image to be exported
   * @param fileType the type of file the image should be exported as
   * @param fileName the name of the file to be exported
   * @param width    the width of the image to be exported
   * @param height   the height of the image to be exported
   * @throws IllegalArgumentException if filename is null or fileType is unrecognized
   */
  void exportImage(IImage image, FileType fileType, String fileName, int width, int height)
      throws IllegalArgumentException;

  /**
   * Saves and exports the multi-layered image.
   *
   * @param newDirectory the name of the path directory being created for the new folder of layers
   *                     and text file with the images location to go into
   * @param fileType     the type of files being exported (one of ppm, png, or jpg)
   * @param width    the width of the image to be exported
   * @param height   the height of the image to be exported
   */
  void saveMultiLayer(String newDirectory, FileType fileType, int width, int height);
}
