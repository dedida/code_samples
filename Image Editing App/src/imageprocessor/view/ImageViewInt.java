package imageprocessor.view;

/**
 * The ImageViewInt interface represents the methods available for use by the GUI.
 */
public interface ImageViewInt {

  /**
   * Updates GUI with new image.
   *
   * @param newImage (int [][][])
   * @param width    (int)
   * @param height   (int)
   */
  void updateImage(int[][][] newImage, int width, int height);

  /**
   * Returns the batch text.
   *
   * @return the batch text (string).
   */
  String getBatchText();

  /**
   * Sets status to boolean entered in as input.
   *
   * @param status (boolean)
   */
  void setStatus(boolean status);

}
