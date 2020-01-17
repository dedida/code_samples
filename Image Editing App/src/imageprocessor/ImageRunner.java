package imageprocessor;

import java.io.IOException;

import imageprocessor.model.ImageModel;
import imageprocessor.controller.ImageController;

/**
 * The ImageRunner class creates the model and controller, then starts the controller.
 */
public class ImageRunner {

  /**
   * Main used to create the model and controller, and start the controller.
   *
   * @throws IOException if file cannot be read or written
   */
  public static void main(String[] args) throws IOException {
    ImageModel model = new ImageModel();
    ImageController imageController = new ImageController(model);
    imageController.executeFile(args[0]);
  }

}