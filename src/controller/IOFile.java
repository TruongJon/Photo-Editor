package controller;

import model.IImage;

/**
 * Interface to represent the input/output functionality for various image file types.
 */
public interface IOFile {

  /**
   * Parses through an image file and creates a single string that can be interpreted by the model
   * and initialized as an image object.
   *
   * @param filename the image file name and/or path directory leading to the image file
   * @return a string representation of the image that can be interpreted by the model
   */
  String readImage(String filename);

  /**
   * Exports this image to the local computer either to the specified directory or to the same level
   * as the source folder if directory is not specified.
   *
   * @param originalImage the original image being exported
   * @param fileString    the image represented as a parsable string
   * @param fileName      the name of the file to be exported
   * @param fileType      the type of file being exported
   * @param width         the width of the image to be exported
   * @param height        the height of the image to be exported
   */
  void exportImage(IImage originalImage, String fileString, String fileName, FileType fileType,
      int width, int height);

}
