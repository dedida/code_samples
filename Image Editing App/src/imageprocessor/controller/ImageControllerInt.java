package imageprocessor.controller;

import java.io.IOException;

/**
 * THe ImageController interface represents the method available for use to execute commands from
 * the model to alter/generate images.
 */

public interface ImageControllerInt {

  /**
   * Executes the commands in a given file.
   *
   * @param uploadedFile String, the name of the file
   * @throws IOException if the commands cannot be read from the given file.
   */
  void executeFile(String uploadedFile) throws IOException;

  /**
   * Executes a single command line.
   *
   * @param command command String, the command input.
   * @throws IOException if the command cannot be executed.
   */
  void executeCommand(String command) throws IOException;

  void executeCommands(String command) throws IOException;

}
