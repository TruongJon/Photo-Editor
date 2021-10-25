package controller;

/**
 * Enumeration to represent the file types. Each enumeration represents a file type of an image that
 * is currently supported.
 */
public enum FileType {
  PPM, JPEG, PNG;

  @Override
  public String toString() {
    String fileType;
    switch (this) {
      case PPM:
        fileType = "ppm";
        break;
      case JPEG:
        fileType = "jpeg";
        break;
      case PNG:
        fileType = "png";
        break;
      default:
        fileType = "";
    }
    return fileType;
  }
}
