package imageprocessor.model;

import java.io.IOException;

/**
 * The ImageModelInt interface represents the methods available for use to filter an image or
 * generate an image.
 */

public interface ImageModelInt {

  /**
   * Creates a jpeg of a rainbow with 7 stripes either vertical or horizontal based on the value of
   * the String type field. If it is vertical it will create a vertically striped rainbow, if it is
   * horizontal the rainbow will have horizontal stripes, else it will not create a rainbow. This
   * function works by adding the rgb values into the 3 dimensional array stripe by stripe.
   *
   * @param heightInput integer, the height of the image.
   * @param widthInput  integer, the width of the image.
   * @param typeInput   String, the direction of the rainbow, horizontal or vertical).
   * @throws IllegalArgumentException when rainbow type is not "horizontal" or "vertical"
   */
  void createRainbow(int heightInput, int widthInput, String typeInput)
          throws IllegalArgumentException;

  /**
   * Creates a jpeg of an 8 by 8 black and white checkerboard. Each square's length is determined by
   * the value of the squareSize field (int) passed in by the user. This function works by adding
   * the rgb values into the 3 dimensional array square by square.
   *
   * @param squareSizeInput integer, the size of the length/width for the board.
   * @throws IllegalArgumentException when square size is <= 0
   */
  void createCheckerBoard(int squareSizeInput) throws IOException;

  /**
   * Creates a jpeg of the French flag based on the width specified by the user. This function works
   * by breaking the flag down into stripes. Then it fills the rgb values for the pixels, stripe by
   * stripe.
   *
   * @param widthInput integer, the width of the flag in pixels.
   */
  void createFrenchFlag(int widthInput) throws IOException;

  /**
   * Creates a jpeg of the Swiss flag based on the width specified by the user. This function works
   * by breaking the flag down into blocks. Then it fills the rgb values for the pixels, block by
   * block.
   *
   * @param widthInput integer, the width of the flag in pixels.
   */
  void createSwissFlag(int widthInput) throws IOException;

  /**
   * Creates a jpeg of the Greek flag based on the width specified by the user. This function works
   * by breaking down the flag into squares and stripes. Then it fills the rgb values for the pixels
   * in these squares and stripes one at a time.
   *
   * @param widthInput integer, the width of the flag in pixels.
   * @throws IllegalArgumentException when given width is less than or equal to zero.
   */
  void createGreekFlag(int widthInput) throws IOException;

  /**
   * Filters an image and outputs a Sepia version of it by altering the rgb values for every pixel
   * by multiplying them by the appropriate number.
   */
  void filterSepia() throws IOException;

  /**
   * Filters an image and outputs a GreyScale version of it by altering the rgb values for every
   * pixel by multiplying them by the appropriate number.
   */
  void filterGreyscale() throws IOException;

  /**
   * Outputs a sharpened image by applying a 3 by 3 filter to each pixel, by aligning the kernel
   * with the appropriate value, and doing matrix multiplication.
   */
  void filterSharpen() throws IOException;

  /**
   * Outputs a blurred image by 5 by 5 filter to each pixel, by aligning the kernel with the
   * appropriate value, and doing matrix multiplication.
   */
  void filterBlur() throws IOException;

  /**
   * Filters an image and outputs a dithered version of that image. Greyscales the image first and
   * then reassigns r,g,b values of each pixel to 0 or 255 depending on the current r,g,b values
   * proximity to it, and then takes that value and multiples it by a color multiplier, and adds an
   * approximate error value to it.
   */
  void filterDither() throws IOException;

  /**
   * Filters an image and outputs a mosaic. The number of panes(seeds) is the determined by user
   * input. Then k-means clustering is used to group the pixels. The average r,g,b values is
   * determined for each cluster, and then each pixel in the cluser has it's r,g,b values updated to
   * the average.
   *
   * @param seeds integer, the number of panels (or clusters) in the mosaic image.
   * @throws IllegalArgumentException if the number of seeds is greater than the number of pixels in
   *                                  the image.
   */
  void filterMosaic(int seeds) throws IllegalArgumentException, IOException;

  /**
   * Set the saveName for an ImageModel object.
   *
   * @param updatedName String
   */
  void setSaveAsName(String updatedName);

  /**
   * Updates model fields for a new file.
   *
   * @param uploaded String, image file name
   * @throws IOException if the image file cannot be read or written
   */
  void setImageName(String uploaded) throws IOException;

  /**
   * Writes new image and saves it as given name.
   *
   * @throws IOException if file cannot be written
   */
  void saveAs() throws IOException;

  /**
   * Temporarily saves the current image.
   *
   * @throws IOException if the file cannot be written
   */
  void tempSave() throws IOException;

  /**
   * Changes current image to a later one, by retrieving a "newer" RGB array from an arrayList
   * containing all RGB changes.
   * @throws IllegalArgumentException if there is no previous RGB 3D array the user can go back to
   */
  void redo() throws IllegalArgumentException, IOException;


  /**
   * Changes current image to a previous one, by retrieving an "older" RGB array from an arrayList
   * containing all RGB changes.
   * @throws IllegalArgumentException if there is no previous RGB 3D array the user can go back to
   */
  void undo() throws IllegalArgumentException, IOException;
}
