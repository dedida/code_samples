package imageprocessor.model;

import java.awt.Point;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.List;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

/**
 * The ImageModel class represents an image that can be created or filtered. This class implements
 * the ImageModelInt interface.
 */
public class ImageModel implements ImageModelInt {
  private String type;
  private String imageName;
  private int squareSize;
  private int length;
  private int height;
  private int width;
  private int[][][] rgb;
  private String saveName;

  private int placeholder;
  private ArrayList<int[][][]> operationArray;


  /**
   * ImageModel constructor used for creating objects to be filtered.
   */
  public ImageModel() {
    this.type = "";
    this.imageName = "";
    this.squareSize = 0;
    this.length = 0;
    this.height = 0;
    this.width = 0;
    this.rgb = new int[height][width][3];
    this.saveName = "";
    this.placeholder = -1;
    this.operationArray = new ArrayList<int[][][]>();
  }

  /**
   * Creates a jpeg of a rainbow with 7 stripes either vertical or horizontal based on the value of
   * the String type field. If it is vertical it will create a vertically striped rainbow, if it is
   * horizontal the rainbow will have horizontal stripes, else it will not create a rainbow. This
   * function works by adding the rgb values into the 3 dimensional array stripe by stripe.
   *
   * @param heightInput integer, the height of the image.
   * @param widthInput  integer, the width of the image.
   * @param typeInput   String, the direction of the rainbow, horizontal or vertical).
   * @throws IllegalArgumentException if the given width is equal to or less than 0, and when
   *                                  rainbow type is not "horizontal" or "vertical"
   */
  @Override
  public void createRainbow(int heightInput, int widthInput, String typeInput)
          throws IllegalArgumentException {
    if (!typeInput.equals("horizontal") && !typeInput.equals("vertical")) {
      throw new IllegalArgumentException("Type of rainbow not specified.");
    }
    if (widthInput <= 0 | heightInput <= 0) {
      throw new IllegalArgumentException("Size must be greater than 0");
    }
    this.height = heightInput;
    this.width = widthInput;
    this.type = typeInput;
    this.rgb = new int[height][width][3];

    int[] red = {255, 0, 0};
    int[] orange = {255, 127, 0};
    int[] yellow = {255, 255, 0};
    int[] green = {0, 255, 0};
    int[] blue = {0, 0, 255};
    int[] indigo = {34, 0, 102};
    int[] violet = {139, 0, 255};
    int[][] colors = {red, orange, yellow, green, blue, indigo, violet};

    if (type.equals("vertical")) {
      for (int i = 1; i < 8; i++) {
        rgb = this.addVerticalStripe(rgb, width, height, i, (i - 1)
                * (width / 7), colors[i - 1]);
      }
    } else {
      for (int i = 1; i < 8; i++) {
        rgb = this.addHorizontalStripe(rgb, width, height, i, (i - 1)
                * (height / 7), colors[i - 1]);
      }
    }
    try {
      this.updateArrayList();
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  /**
   * Creates a jpeg of an 8 by 8 black and white checkerboard. Each square's length is determined by
   * the value of the squareSize field (int) passed in by the user. This function works by adding
   * the rgb values into the 3 dimensional array square by square.
   *
   * @param squareSizeInput integer, the size of the length/width for the board.
   * @throws IllegalArgumentException if the given width is equal to or less than 0.
   */
  @Override
  public void createCheckerBoard(int squareSizeInput) throws IllegalArgumentException, IOException {
    if (squareSizeInput <= 0) {
      throw new IllegalArgumentException("Square size is set to 0.");
    }

    this.squareSize = squareSizeInput;
    this.length = 8 * squareSizeInput;
    this.rgb = new int[length][length][3];
    this.width = this.length;
    this.height = this.length;

    int[] black = {0, 0, 0};
    int[] white = {255, 255, 255};
    int[][] colors = {black, white};

    for (int i = 1; i < 65; i++) {
      if ((i - 1) / 8 % 2 == 0) {
        if (i % 2 == 0) {
          rgb = this.addSquare(rgb, squareSize, squareSize, (i - 1) / 8 * squareSize,
                  (i - 1) % 8 * squareSize, colors[0]);
        } else {
          rgb = this.addSquare(rgb, squareSize, squareSize, (i - 1) / 8 * squareSize,
                  (i - 1) % 8 * squareSize, colors[1]);
        }
      } else {
        if (i % 2 == 0) {
          rgb = this.addSquare(rgb, squareSize, squareSize, (i - 1) / 8 * squareSize,
                  (i - 1) % 8 * squareSize, colors[1]);
        } else {
          rgb = this.addSquare(rgb, squareSize, squareSize, (i - 1) / 8 * squareSize,
                  (i - 1) % 8 * squareSize, colors[0]);
        }
      }
    }
    this.updateArrayList();
  }

  /**
   * Creates a jpeg of the French flag based on the width specified by the user. This function works
   * by breaking the flag down into stripes. Then it fills the rgb values for the pixels, stripe by
   * stripe.
   *
   * @param widthInput integer, the width of the flag in pixels.
   * @throws IllegalArgumentException if the given width is equal to or less than 0.
   */
  @Override
  public void createFrenchFlag(int widthInput) throws IllegalArgumentException, IOException {
    if (widthInput <= 0) {
      throw new IllegalArgumentException("Size must be greater than 0");
    }
    this.width = widthInput;
    this.height = 2 * (widthInput / 3);
    this.rgb = new int[this.height][this.width][3];

    int[] blue = {0, 85, 164};
    int[] white = {255, 255, 255};
    int[] red = {239, 65, 53};
    int[][] colors = {blue, white, red};

    for (int i = 1; i < 4; i++) {
      rgb = this.addVerticalStripeFlag(rgb, width, height, i, 3,
              (i - 1) * (width / 3), colors[i - 1]);
    }
    this.updateArrayList();
  }

  /**
   * Creates a jpeg of the Swiss flag based on the width specified by the user. This function works
   * by breaking the flag down into blocks. Then it fills the rgb values for the pixels, block by
   * block.
   *
   * @param widthInput integer, the width of the flag in pixels.
   * @throws IllegalArgumentException if the given width is equal to or less than 0.
   */
  @Override
  public void createSwissFlag(int widthInput) throws IllegalArgumentException, IOException {
    if (widthInput <= 0) {
      throw new IllegalArgumentException("Size must be greater than 0");
    }

    int sideLength1 = (int) (3 * (widthInput / 16));
    int sideLength2 = (int) (7 * (widthInput / 32));
    this.width = (3 * sideLength1) + (2 * sideLength2);
    this.height = (3 * sideLength1) + (2 * sideLength2);
    this.rgb = new int[this.height][this.width][3];

    int rowStart = 0;
    int columnStart = 0;
    int blockCount = 1;
    int[] color;

    for (int i = 0; i < 5; i++) {
      for (int j = 0; j < 5; j++) {
        color = this.giveSwissBlockColor(blockCount);
        if (i % 2 == 0 && j % 2 == 0) {
          rgb = this.addSquare(rgb, sideLength1, sideLength1, rowStart, columnStart, color);
          columnStart = columnStart + sideLength1;
        } else if (i % 2 == 0 && j % 2 != 0) {
          rgb = this.addSquare(rgb, sideLength1, sideLength2, rowStart, columnStart, color);
          columnStart = columnStart + sideLength2;
        } else if (i % 2 != 0 && j % 2 == 0) {
          rgb = this.addSquare(rgb, sideLength2, sideLength1, rowStart, columnStart, color);
          columnStart = columnStart + sideLength1;
        } else {
          rgb = this.addSquare(rgb, sideLength2, sideLength2, rowStart, columnStart, color);
          columnStart = columnStart + sideLength2;
        }
        blockCount++;
      }
      columnStart = 0;
      if (i == 0 | i == 2 | i == 4) {
        rowStart = rowStart + sideLength1;
      } else {
        rowStart = rowStart + sideLength2;
      }
    }
    this.updateArrayList();

  }

  /**
   * Creates a jpeg of the Greek flag based on the width specified by the user. This function works
   * by breaking down the flag into squares and stripes. Then it fills the rgb values for the pixels
   * in these squares and stripes one at a time.
   *
   * @param widthInput integer, the width of the flag in pixels.
   * @throws IllegalArgumentException when given width is less than or equal to zero.
   */
  @Override
  public void createGreekFlag(int widthInput) throws IllegalArgumentException, IOException {
    if (widthInput <= 0) {
      throw new IllegalArgumentException("Size must be greater than 0");
    }

    int[] blue = {13, 94, 175};
    int[] white = {255, 255, 255};

    this.width = widthInput;
    this.squareSize = (width / 13);
    this.height = 9 * squareSize;
    this.rgb = new int[height][width][3];

    for (int i = 1; i < 10; i++) {
      innerLoop:
      for (int j = 1; j < 14; j++) {
        if ((i == 3 | j == 3) && i < 6 && j < 6) {
          rgb = this.addSquare(rgb, squareSize, squareSize, (i - 1) * squareSize,
                  (j - 1) * squareSize, white);
        } else if (i <= 5 && j <= 5) {
          rgb = this.addSquare(rgb, squareSize, squareSize, (i - 1) * squareSize,
                  (j - 1) * squareSize, blue);
        } else if ((i == 1 | i == 3 | i == 5 | i == 7 | i == 9)) {
          rgb = this.addHorizontalStripeFlag(rgb, width, height, i,
                  (i - 1) * squareSize, (j - 1) * squareSize, squareSize, blue);
          break innerLoop;
        } else {
          rgb = this.addHorizontalStripeFlag(rgb, width, height, i,
                  (i - 1) * squareSize, (j - 1) * squareSize, squareSize, white);
          break innerLoop;
        }
      }
    }
    this.updateArrayList();
  }

  /**
   * Filters an image and outputs a Sepia version of it by altering the rgb values for every pixel
   * by multiplying them by the appropriate number.
   */
  @Override
  public void filterSepia() throws IOException {
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        rgb[i][j] = toSepia(rgb, i, j);
      }
    }
    this.updateArrayList();
  }

  /**
   * Filters an image and outputs a GreyScale version of it by altering the rgb values for every
   * pixel by multiplying them by the appropriate number.
   */
  @Override
  public void filterGreyscale() throws IOException {
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        this.rgb[i][j] = toGreyscale(rgb, i, j);
      }
    }
    this.updateArrayList();
  }

  /**
   * Outputs a sharpened image by applying a 3 by 3 filter to each pixel, by aligning the kernel
   * with the appropriate value, and doing matrix multiplication.
   */
  @Override
  public void filterSharpen() throws IOException {
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        rgb[i][j] = sharpen(rgb, width, height, i, j);
      }
    }
    this.updateArrayList();
  }

  /**
   * Outputs a blurred image by 5 by 5 filter to each pixel, by aligning the kernel with the
   * appropriate value, and doing matrix multiplication.
   */
  @Override
  public void filterBlur() throws IOException {
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        rgb[i][j] = blur(rgb, width, height, i, j);
      }
    }
    this.updateArrayList();
  }

  /**
   * Filters an image and outputs a dithered version of that image. Greyscales the image first and
   * then reassigns r,g,b values of each pixel to 0 or 255 depending on the current r,g,b values
   * proximity to it, and then takes that value and multiples it by a color multiplier, and adds an
   * approximate error value to it.
   */
  @Override
  public void filterDither() throws IOException {
    this.filterGreyscale();
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        this.rgb[i][j] = this.ditherHelper(rgb, i, j);
      }
    }
    this.updateArrayList();
  }

  /**
   * Filters an image and outputs a mosaic. The number of panes(seeds) is the determined by user
   * input. Then k-means clustering is used to group the pixels. The average r,g,b values is
   * determined for each cluster, and then each pixel in the cluser has it's r,g,b values updated to
   * the average.
   *
   * @param seeds integer, the number of panels (or clusters) in the mosaic image.
   * @throws IllegalArgumentException if the number of seeds is greater than the number of pixels in
   *                                  the image or if number is 0 or negative.
   */
  @Override
  public void filterMosaic(int seeds) throws IllegalArgumentException, IOException {

    int numOfPixels = this.height * this.width;

    if (seeds > numOfPixels) {
      throw new IllegalArgumentException("The number of seeds cannot be greater than "
              + "the number of pixels in the image");
    }

    if (seeds <= 0) {
      throw new IllegalArgumentException("Can not be negative.");
    }

    List<Integer> seedList = findSeeds(seeds, numOfPixels);

    HashSet<Point> points = this.seedLocation(seedList);

    int[][] clusters = this.getClusters(points);

    int[][] averageColors = this.averageColor(points, clusters);

    this.update3dArray(points, clusters, averageColors);
    this.updateArrayList();

  }


  // Save/Load Methods

  /**
   * Set the saveName for an object.
   *
   * @param updatedName String
   */
  @Override
  public void setSaveAsName(String updatedName) {
    this.saveName = updatedName;
  }

  /**
   * Updates model fields for a new file.
   *
   * @param uploaded String, image file name
   * @throws IOException if the image file cannot be read or written
   */
  @Override
  public void setImageName(String uploaded) throws IOException {
    try {
      this.imageName = uploaded;
      this.height = ImageUtil.getHeight(uploaded);
      this.width = ImageUtil.getWidth(uploaded);
      this.rgb = ImageUtil.readImage(uploaded);
      this.updateArrayList();
    } catch (IOException e) {
      throw new IOException("Cannot load image");
    }
  }

  /**
   * Writes new image and saves it as given name.
   *
   * @throws IOException if file cannot be written
   */
  @Override
  public void saveAs() throws IOException {
    ImageUtil.writeImage(this.rgb, this.width, this.height, this.saveName);
  }

  /**
   * Temporarily saves the current image.
   *
   * @throws IOException if the file cannot be written
   */
  @Override
  public void tempSave() throws IOException {
    ImageUtil.writeImage(this.rgb, this.width, this.height, "tempImage.jpeg");
  }

  /**
   * Changes current image to a later one, by retrieving a "newer" RGB array from an arrayList
   * containing all RGB changes.
   *
   * @throws IllegalArgumentException if there is no previous RGB 3D array the user can go back to
   */

  public void redo() throws IllegalArgumentException, IOException {
    if (placeholder == (operationArray.size() - 1)) {
      throw new IllegalArgumentException("Nothing to redo to");
    }
    placeholder++;
    this.rgb = operationArray.get(placeholder);
    this.tempSave();

  }

  /**
   * Changes current image to a previous one, by retrieving an "older" RGB array from an arrayList
   * containing all RGB changes.
   *
   * @throws IllegalArgumentException if there is no previous RGB 3D array the user can go back to
   */

  public void undo() throws IllegalArgumentException, IOException {
    if (placeholder == 0) {
      throw new IllegalArgumentException("Nothing to undo to");
    }
    placeholder = placeholder - 1;
    this.rgb = operationArray.get(placeholder);
    this.tempSave();

  }

  public int[][][] getRGB() {
    return this.rgb;
  }

  public int getHeight() {
    return height;
  }

  public int getWidth() {
    return width;
  }

  //Helper Methods

  /**
   * Returns an updated 3D array, by adding a vertical "stripe" to the array for a rainbow with 7
   * stripes.
   *
   * @param rgb       3D array of r,g,b values
   * @param width     (int) of array
   * @param height    (int) of array
   * @param stripeNum (int) stripe being worked on
   * @param start     (int) column to add stripe to
   * @param color     int[] of r,g,b values for a specific color
   * @return updated 3D array
   */
  private int[][][] addVerticalStripe(int[][][] rgb, int width, int height, int stripeNum,
                                      int start, int[] color) {
    for (int i = 0; i < height; i++) {
      if (stripeNum != 7) {
        for (int j = start; j < stripeNum * (width / 7); j++) {
          rgb[i][j] = color;
        }
      } else {
        for (int j = start; j < width; j++) {
          rgb[i][j] = color;
        }
      }
    }
    return rgb;
  }

  /**
   * Returns an updated 3D array, by adding a horizontal "stripe" to the array for a rainbow with 7
   * stripes.
   *
   * @param rgb       3D array of r,g,b values
   * @param width     (int) of array
   * @param height    (int) of array
   * @param stripeNum (int) stripe being worked on
   * @param start     (int) row to add stripe to
   * @param color     int[] of r,g,b values for a specific color
   * @return updated 3D array
   */
  private int[][][] addHorizontalStripe(int[][][] rgb, int width, int height, int stripeNum,
                                        int start, int[] color) {
    if (stripeNum != 7) {
      for (int i = start; i < stripeNum * (height / 7); i++) {
        for (int j = 0; j < width; j++) {
          rgb[i][j] = color;
        }
      }
    } else {
      for (int i = start; i < height; i++) {
        for (int j = 0; j < width; j++) {
          rgb[i][j] = color;
        }
      }

    }
    return rgb;
  }

  /**
   * Returns an updated 3D array, by adding a vertical "stripe" to the array for a flag with 3
   * stripes.
   *
   * @param rgb        3D array of r,g,b values
   * @param width      (int) of array
   * @param height     (int) of array
   * @param stripeNum  (int) stripe being worked on
   * @param lastStripe (int) stripe being worked on
   * @param start      (int) column to add stripe to
   * @param color      int[] of r,g,b values for a specific color
   * @return updated 3D array
   */
  private int[][][] addVerticalStripeFlag(int[][][] rgb, int width, int height, int stripeNum,
                                          int lastStripe, int start, int[] color) {
    for (int i = 0; i < height; i++) {
      if (stripeNum != lastStripe) {
        for (int j = start; j < stripeNum * (width / 3); j++) {
          rgb[i][j] = color;
        }
      } else {
        for (int j = start; j < width; j++) {
          rgb[i][j] = color;
        }
      }
    }
    return rgb;

  }

  /**
   * Returns an updated 3D array, by adding a "square" of values to the array.
   *
   * @param rgb    3D array of r,g,b values
   * @param height (int) height of square
   * @param width  (int) width of square
   * @param row    (int) currently on
   * @param column (int) currently on
   * @param color  int[] of r,g,b values for a specific color
   * @return updated 3D array
   */
  private int[][][] addSquare(int[][][] rgb, int height, int width, int row, int column,
                              int[] color) {
    for (int i = row; i < row + height; i++) {
      for (int j = column; j < column + width; j++) {
        rgb[i][j] = color;
      }
    }
    return rgb;
  }

  /**
   * Returns int[] which represents the color of the block being worked on.
   *
   * @param block (int) block being worked on.
   * @return int[] which represents the color of the block being worked on.
   */
  private int[] giveSwissBlockColor(int block) {
    int[] red = {255, 0, 0};
    int[] white = {255, 255, 255};

    if (block == 8 | block == 12 | block == 13 | block == 14 | block == 18) {
      return white;
    } else {
      return red;
    }

  }

  /**
   * Returns updated 3D array with the r,g,b pixels values for a stripe.
   *
   * @param rgb          array of r,g,b values
   * @param width        (int) of rgb array
   * @param height       (int) of rgb array
   * @param stripeNum    (int) stripe currently on
   * @param row          (int) currently on
   * @param column       (int) currently on
   * @param squareLength (int) length of a single square in the flag
   * @param color        int[] of r,g,b values for a specific color
   * @return updated 3D array
   */
  private int[][][] addHorizontalStripeFlag(int[][][] rgb, int width, int height, int stripeNum,
                                            int row, int column, int squareLength, int[] color) {
    if (stripeNum != 9) {
      for (int i = row; i < row + squareLength; i++) {
        for (int j = column; j < width; j++) {
          rgb[i][j] = color;
        }
      }
    } else {
      for (int i = row; i < height; i++) {
        for (int j = 0; j < width; j++) {
          rgb[i][j] = color;
        }
      }
    }
    return rgb;
  }

  /**
   * Returns a clamped value, if values is less than 0 it is set to 0, if value is greater than 255
   * it is set to 255.
   *
   * @param clamped (int) value to be clamped
   * @return clamped value
   */
  private int clamp(int clamped) {
    if (clamped > 255) {
      clamped = 255;
    } else if (clamped < 0) {
      clamped = 0;
    }
    return clamped;
  }

  /**
   * Returns a color array with r,g,b values that are clamped and casted to integers.
   *
   * @param r (double)
   * @param g (double)
   * @param b (double)
   * @return int[] of rgb values that are clamped
   */
  private int[] clampHelper(double r, double g, double b) {
    int red = clamp((int) r);
    int green = clamp((int) g);
    int blue = clamp((int) b);

    return new int[]{red, green, blue};

  }

  /**
   * Returns an array of the r,g,b sepia values for a pixel.
   *
   * @param rgb array of r,g,b values
   * @param i   (int) row number
   * @param j   (int) column number
   * @return an array of the r,g,b sepia values for a pixel.
   */
  private int[] toSepia(int[][][] rgb, int i, int j) {
    int red = clamp((int) (((0.393) * rgb[i][j][0]) + ((0.769) * rgb[i][j][1])
            + ((0.189) * rgb[i][j][2])));

    int green = clamp((int) ((0.349) * rgb[i][j][0] + (0.686) * rgb[i][j][1]
            + (0.168) * rgb[i][j][2]));

    int blue = clamp((int) (((0.272) * rgb[i][j][0]) + ((0.534) * rgb[i][j][1])
            + ((0.131) * rgb[i][j][2])));

    return new int[]{red, green, blue};

  }

  /**
   * Returns an array of the r,g,b greyscale values for a pixel.
   *
   * @param rgb array of r,g,b values
   * @param i   (int) row number
   * @param j   (int) column number
   * @return an array of the r,g,b greyscale values for a pixel.
   */
  private int[] toGreyscale(int[][][] rgb, int i, int j) {
    int newRgb = clamp((int) ((0.2126) * rgb[i][j][0] + (0.7152) * rgb[i][j][1]
            + (0.0722) * rgb[i][j][2]));

    return new int[]{newRgb, newRgb, newRgb};

  }

  /**
   * Returns int[] of r,g,b values, this function calculates these values based on the location of
   * the pixel and its relative location to the kernel and the sharpen matrix.
   *
   * @param rgb    array of r,g,b values
   * @param width  (int) of rgb array
   * @param height (int) of rgb array
   * @param i      (int) row number
   * @param j      (int) column number
   * @return int[] of r,g,b values
   */
  private int[] sharpen(int[][][] rgb, int width, int height, int i, int j) {

    double r = 0;
    double g = 0;
    double b = 0;

    int p = 0;
    for (int row = i - 2; row < i + 3; row++) {
      for (int col = j - 2; col < j + 3; col++) {
        if ((row < 0) || (row >= height) || (col < 0) || (col >= width)) {
          p++;
        } else {
          if (p == 12) {
            r = r + rgb[row][col][0];
            g = g + rgb[row][col][1];
            b = b + rgb[row][col][2];
            p++;
          } else if ((p == 6) || (p == 7) || (p == 8) || (p == 11) || (p == 13) || (p == 16)
                  || (p == 17) || (p == 18)) {
            r = r + (0.25 * rgb[row][col][0]);
            g = g + (0.25 * rgb[row][col][1]);
            b = b + (0.25 * rgb[row][col][2]);
            p++;
          } else {
            r = r + ((-0.125) * rgb[row][col][0]);
            g = g + ((-0.125) * rgb[row][col][1]);
            b = b + ((-0.125) * rgb[row][col][2]);
            p++;
          }
        }
      }
    }
    return clampHelper(r, g, b);

  }

  /**
   * Returns int[] of r,g,b values, this function calculates these values based on the location of
   * the pixel and its relative location to the kernel and the blur matrix.
   *
   * @param rgb    array of r,g,b values
   * @param width  (int) of rgb array
   * @param height (int) of rgb array
   * @param i      (int) row number
   * @param j      (int) column number
   * @return int[] of r,g,b values
   */
  private int[] blur(int[][][] rgb, int width, int height, int i, int j) {

    double r = 0;
    double g = 0;
    double b = 0;

    int row;
    int col;
    int p = 0;
    for (row = i - 1; row < i + 2; row++) {
      for (col = j - 1; col < j + 2; col++) {
        if ((row < 0) || (row >= height) || (col < 0) || (col >= width)) {
          p++;
        } else {
          if ((row == i) && (col == j)) {
            r = r + (.25 * rgb[row][col][0]);
            g = g + (.25 * rgb[row][col][1]);
            b = b + (.25 * rgb[row][col][2]);
            p++;
          } else if ((p % 2) == 0) {
            r = r + (0.0625 * rgb[row][col][0]);
            g = g + (0.0625 * rgb[row][col][1]);
            b = b + (0.0625 * rgb[row][col][2]);
            p++;
          } else {
            r = r + (0.125 * rgb[row][col][0]);
            g = g + (0.125 * rgb[row][col][1]);
            b = b + (0.125 * rgb[row][col][2]);
            p++;
          }
        }
      }
    }
    return clampHelper(r, g, b);
  }

  /**
   * Returns new array of r,g,b values (int) for a pixel after it goes through the dithering
   * process.
   *
   * @param rgb 3D array of integers (r,g,b values)
   * @param i   integer row number
   * @param j   integer column number
   * @return    new array of r,g,b values (int) for a pixel after it goes through the dithering
   *            process.
   */
  private int[] ditherHelper(int[][][] rgb, int i, int j) {

    int red = rgb[i][j][0];
    int new_red = newVal(rgb[i][j][0]);
    int red_error = red - new_red;
    this.addToPixel(0, red_error, i, j);

    int green = rgb[i][j][1];
    int new_green = newVal(rgb[i][j][1]);
    int green_error = green - new_green;
    this.addToPixel(1, green_error, i, j);

    int blue = rgb[i][j][2];
    int new_blue = newVal(rgb[i][j][2]);
    int blue_error = blue - new_blue;
    this.addToPixel(2, blue_error, i, j);

    return new int[]{new_red, new_green, new_blue};
  }

  /**
   * Returns 255 or 0 depending on which is closer to the current r,g,b value.
   *
   * @param val (int) current r,g, b value
   * @return 255 or 0 value depending on which is closer to the current r,g,b value
   */
  private int newVal(int val) {
    if (val < 255 - val) {
      return 0;
    } else {
      return 255;
    }
  }

  /**
   * Updates r,g,b values for each pixel, with error value, and r,g,b multiplier value.
   *
   * @param rgbPos integer
   * @param error  integer
   * @param i      integer
   * @param j      integer
   */
  private void addToPixel(int rgbPos, int error, int i, int j) {
    if ((i + 1) <= (this.height - 1) && (j - 1) >= 0 && (j + 1) <= (this.width - 1)) {
      int x = 7 * (error / 16);
      this.rgb[i][j + 1][rgbPos] = clamp(x + this.rgb[i][j + 1][rgbPos]);

      int y = 3 * (error / 16);
      this.rgb[i + 1][j - 1][rgbPos] = clamp(y + this.rgb[i + 1][j - 1][rgbPos]);

      int z = 5 * (error / 16);
      this.rgb[i + 1][j][rgbPos] = clamp(z + this.rgb[i + 1][j][rgbPos]);

      int a = (error / 16);
      this.rgb[i + 1][j + 1][rgbPos] = clamp(a + this.rgb[i + 1][j + 1][rgbPos]);
    }
  }

  /**
   * Returns list of unique pixel locations that will be the seeds.
   *
   * @param seeds       (int) number of panes in the mosaic
   * @param numOfPixels (int) total number of pixels in the photo
   * @return list of unique pixel locations that will be the seeds.
   */
  private List<Integer> findSeeds(int seeds, int numOfPixels) {
    List<Integer> pixelPlaces = new ArrayList<>();
    Random rand = new Random();

    for (int i = 0; i < seeds; i++) {
      int temp = rand.nextInt(numOfPixels);
      while (!pixelPlaces.contains(temp)) {
        pixelPlaces.add(temp);
      }
    }

    return pixelPlaces;
  }

  /**
   * Returns euclidean distance between 2 points (double).
   *
   * @param oneX integer
   * @param oneY integer
   * @param twoX integer
   * @param twoY integer
   * @return euclidean distance between 2 points (double)
   */
  private double euclideanDist(int oneX, int oneY, int twoX, int twoY) {
    double xVal = pow((oneX - twoX), 2);
    double yVal = pow((oneY - twoY), 2);

    return sqrt(xVal + yVal);
  }


  /**
   * Returns points hashset of seed location, location being row and column number.
   *
   * @param seeds list of pixel numbers (int)
   * @return points hashset of seed location, location being row and column number.
   */
  private HashSet<Point> seedLocation(List<Integer> seeds) {
    HashSet<Point> points = new HashSet<Point>();
    int k = 0;
    for (int x = 0; x < seeds.size(); x++) {
      for (int i = 0; i < this.height; i++) {
        for (int j = 0; j < this.width; j++) {
          if (k == seeds.get(x)) {
            points.add(new Point(i, j));
          }
          k++;
        }
      }
      k = 0;
    }
    return points;
  }

  /**
   * Returns 2D array of each pixel's closest seed location.
   *
   * @param points hashset of selected seed locations
   * @return 2D array of each pixel's closest seed location.
   */
  private int[][] getClusters(HashSet<Point> points) {
    int[][] clusters = new int[this.width * this.height][2];
    int x = 0;
    for (int i = 0; i < this.height; i++) {
      for (int j = 0; j < this.width; j++) {
        clusters[x] = findClosest(points, i, j);
        x++;
      }
    }
    return clusters;
  }

  /**
   * Returns a list of row and column value of closest seed for a specific pixel.
   *
   * @param points hashset of selected seed locations
   * @param i      (int) row number
   * @param j      (int) column number
   * @return list of row and column value of closest seed for a specific pixel
   */
  private int[] findClosest(HashSet<Point> points, int i, int j) {
    Iterator value = points.iterator();
    Point temp = (Point) value.next();

    int currentClosestX = (int) temp.getX();
    int currentClosetY = (int) temp.getY();

    int xVal = (int) temp.getX();
    int yVal = (int) temp.getY();

    double dist = euclideanDist(i, j, xVal, yVal);
    while (value.hasNext()) {
      temp = (Point) value.next();
      xVal = (int) temp.getX();
      yVal = (int) temp.getY();

      if (euclideanDist(i, j, xVal, yVal) < dist) {
        currentClosestX = (int) temp.getX();
        currentClosetY = (int) temp.getY();
        dist = euclideanDist(i, j, xVal, yVal);

      }
    }

    return new int[]{currentClosestX, currentClosetY};
  }

  /**
   * Returns a 2D array of the average r,g,b values for each seed's cluster.
   *
   * @param points   hashet of selected seed locations
   * @param clusters 2D array of each pixel's cluster location
   * @return 2D array of the average r,g,b values for each seed's cluster
   */
  private int[][] averageColor(HashSet<Point> points, int[][] clusters) {
    int[][] colors = new int[points.size()][3];
    int red = 0;
    int green = 0;
    int blue = 0;
    int counter = 0;
    int x = 0;
    Iterator value = points.iterator();

    Point temp;
    int xVal;
    int yVal;

    while (value.hasNext()) {
      temp = (Point) value.next();
      xVal = (int) temp.getX();
      yVal = (int) temp.getY();
      for (int i = 0; i < (this.width * this.height); i++) {
        if (clusters[i][0] == xVal && clusters[i][1] == yVal) {
          red = red + this.rgb[xVal][yVal][0];
          green = green + this.rgb[xVal][yVal][1];
          blue = blue + this.rgb[xVal][yVal][2];
          counter++;
        }
      }
      colors[x] = new int[]{red / counter, green / counter, blue / counter};
      x++;
      counter = 0;
      red = 0;
      green = 0;
      blue = 0;
    }
    return colors;
  }

  /**
   * Updates pixels to average r,g,b values of its cluster.
   *
   * @param points   hashset of selected seed locations
   * @param clusters 2D array of each pixel's cluster location
   * @param colors   2D array of average r,g,b values for each seed
   */
  private void update3dArray(HashSet<Point> points, int[][] clusters, int[][] colors) {
    Iterator value = points.iterator();
    Point temp;
    int xVal;
    int yVal;
    int counter = 0;

    while (value.hasNext()) {
      temp = (Point) value.next();
      xVal = (int) temp.getX();
      yVal = (int) temp.getY();

      int x = 0;
      for (int i = 0; i < this.height; i++) {
        for (int j = 0; j < this.width; j++) {
          if (clusters[x][0] == xVal && clusters[x][1] == yVal) {
            this.rgb[i][j] = colors[counter];
          }
          x++;
        }
      }
      counter++;
    }
  }

  private void updateArrayList() throws IOException {
    if (placeholder != (operationArray.size() - 1)) {
      while (placeholder != (operationArray.size() - 1)) {
        operationArray.remove((operationArray.size() - 1));
      }
    }
    int[][][] clone = new int[this.height][this.width][3];

    for (int i = 0; i < this.height; i++) {
      for (int j = 0; j < this.width; j++) {
        clone[i][j] = this.rgb[i][j];
      }
    }

    operationArray.add(clone);
    this.placeholder = this.placeholder + 1;

  }

}