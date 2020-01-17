package imageprocessor.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import imageprocessor.model.ImageModel;
import imageprocessor.view.ImageView;

/**
 * This class represents the controller which execute commands written in a file and implements the
 * ImageController interface.
 */
public class ImageController implements ImageControllerInt {

  private ImageModel model;
  private ImageView view;
  private String[] command;
  private List<String> executedCommands;
  private boolean successfulStatus;
  private HashMap<String, Runnable> commandList;

  /**
   * ImageController constructor used for creating the controller that will read batch file of
   * commands.
   *
   * @param model (ImageModel)
   */
  public ImageController(ImageModel model) {
    this.model = model;
    this.view = new ImageView(model, this, "Image Processor");
    this.command = new String[20];
    this.executedCommands = new ArrayList<>();
    this.successfulStatus = true;
    this.commandList = new HashMap<>();

    commandList.put("load", new LoadImage());
    commandList.put("save", new SaveAs());
    commandList.put("sepia", new FilterSepia());
    commandList.put("greyscale", new FilterGreyscale());
    commandList.put("sharpen", new FilterSharpen());
    commandList.put("blur", new FilterBlur());
    commandList.put("dither", new FilterDither());
    commandList.put("mosaic", new FilterMosaic());
    commandList.put("french", new FlagFrench());
    commandList.put("swiss", new FlagSwiss());
    commandList.put("greek", new FlagGreek());
    commandList.put("checker", new CheckerBoard());
    commandList.put("rainbow", new Rainbow());
  }

  /**
   * Executes the commands in a given file.
   *
   * @param uploadedFile String, the name of the file
   * @throws IOException if the commands cannot be read from the given file
   */
  @Override
  public void executeFile(String uploadedFile) throws IOException {
    if (uploadedFile.equals("-interactive")) {
      view.setVisible(true);
    } else {
      try {
        Scanner reader = new Scanner(new File(uploadedFile));

        if (new File(uploadedFile).length() == 0) {
          throw new IOException("File is empty.");
        }
        while (reader.hasNext()) {
          String line = reader.nextLine();
          this.executeCommand(line);
        }
      } catch (IOException e) {
        throw new IOException("Cannot read image file");
      }
    }
  }

  @Override
  public void executeCommand(String command) throws IOException {
    String[] currentCommand = command.split(" ");
    this.command = currentCommand;

    if (this.commandList.containsKey(currentCommand[0])) {
      this.executedCommands.add(currentCommand[0]);
      Runnable execute = this.commandList.get(currentCommand[0]);
      execute.run();
      if (!successfulStatus) {
        throw new IOException("There is an invalid command in the file.");
      }
    }
  }

  @Override
  public void executeCommands(String command) throws IOException {
    String[] currentCommands = command.split("\n");

    if (currentCommands.length == 1) {
      executeCommand(currentCommands[0]);
    } else {
      int num = currentCommands.length;
      for (int i = 0; i < num; i++) {
        executeCommand(currentCommands[i]);
      }
    }

  }

  /**
   * Returns the status of the command being executed as a boolean.
   *
   * @return boolean - true if the command was executed successfully, false otherwise.
   */
  protected boolean getStatus() {
    return this.successfulStatus;
  }

  /**
   * Executes the 'load' command to load an image.
   */
  class LoadImage implements Runnable {
    public void run() {
      try {
        model.setImageName(command[1]);
      } catch (IOException e) {
        e.getMessage();
        successfulStatus = false;
      } catch (ArrayIndexOutOfBoundsException a) {
        System.out.print("Cannot load given image");
        successfulStatus = false;
      }
    }
  }

  /**
   * Executes the 'save' command to save an image.
   */
  class SaveAs implements Runnable {
    public void run() {
      try {
        model.setSaveAsName(command[1]);
        model.saveAs();
      } catch (IOException e) {
        e.getMessage();
        successfulStatus = false;
      } catch (ArrayIndexOutOfBoundsException a) {
        System.out.print("Cannot save given image");
        successfulStatus = false;
      }
    }
  }

  /**
   * Executes the 'sepia' command to apply the sepia filter on an image.
   */
  class FilterSepia implements Runnable {
    public void run() {
      try {
        model.filterSepia();
      } catch (IOException e) {
        e.getMessage();
        successfulStatus = false;
      }
    }
  }

  /**
   * Executes the 'greyscale' command to apply the greyscale filter on an image.
   */
  class FilterGreyscale implements Runnable {
    public void run() {
      try {
        model.filterGreyscale();
      } catch (IOException e) {
        e.getMessage();
        successfulStatus = false;
      }
    }
  }

  /**
   * Executes the 'sharpen' command to apply the sharpen filter on an image.
   */
  class FilterSharpen implements Runnable {
    public void run() {
      try {
        model.filterSharpen();
      } catch (IOException e) {
        e.getMessage();
        successfulStatus = false;
      }
    }
  }

  /**
   * Executes the 'blur' command to apply the blur filter on an image.
   */
  class FilterBlur implements Runnable {
    public void run() {
      try {
        model.filterBlur();
      } catch (IOException e) {
        e.getMessage();
        successfulStatus = false;
      }
    }
  }

  /**
   * Executes the 'dither' command to apply the dither filter on an image.
   */
  class FilterDither implements Runnable {
    public void run() {
      try {
        model.filterDither();
      } catch (IOException e) {
        e.getMessage();
        successfulStatus = false;
      }
    }
  }

  /**
   * Executes the 'mosaic' command to apply the mosaic filter on an image.
   */
  class FilterMosaic implements Runnable {
    public void run() {
      try {
        int seeds = Integer.parseInt(command[1]);
        model.filterMosaic(seeds);
      } catch (ArrayIndexOutOfBoundsException a) {
        System.out.print("Cannot execute mosaic command with given parameters");
        successfulStatus = false;
      } catch (IOException e) {
        e.getMessage();
        successfulStatus = false;
      }
    }
  }

  /**
   * Executes the 'french' command to generate the French flag.
   */
  class FlagFrench implements Runnable {
    public void run() {
      try {
        int width = Integer.parseInt(command[1]);
        model.createFrenchFlag(width);
      } catch (ArrayIndexOutOfBoundsException a) {
        System.out.print("Cannot execute french command with given parameters");
        successfulStatus = false;
      } catch (IOException e) {
        e.getMessage();
        successfulStatus = false;
      }
    }
  }

  /**
   * Executes the 'swiss' command to generate a Swiss flag.
   */
  class FlagSwiss implements Runnable {
    public void run() {
      try {
        int width = Integer.parseInt(command[1]);
        model.createSwissFlag(width);
      } catch (ArrayIndexOutOfBoundsException a) {
        System.out.print("Cannot execute swiss command with given parameters");
        successfulStatus = false;
      } catch (IOException e) {
        e.getMessage();
        successfulStatus = false;
      }
    }
  }

  /**
   * Executes the 'greek' command to generate a Greek flag.
   */
  class FlagGreek implements Runnable {
    public void run() {
      try {
        int width = Integer.parseInt(command[1]);
        model.createGreekFlag(width);
      } catch (ArrayIndexOutOfBoundsException a) {
        System.out.print("Cannot execute greek command with given parameters");
        successfulStatus = false;
      } catch (IOException e) {
        e.getMessage();
        successfulStatus = false;
      }
    }
  }

  /**
   * Executes the 'checker' command to generate a checker board.
   */
  class CheckerBoard implements Runnable {
    public void run() {
      try {
        int squareSize = Integer.parseInt(command[1]);
        model.createCheckerBoard(squareSize);
      } catch (ArrayIndexOutOfBoundsException a) {
        System.out.print("Cannot execute checker command with given parameters");
        successfulStatus = false;
      } catch (IOException e) {
        e.getMessage();
        successfulStatus = false;
      }
    }
  }

  /**
   * Executes the 'rainbow' command to generate a rainbow.
   */
  class Rainbow implements Runnable {
    public void run() {
      try {
        int height = Integer.parseInt(command[1]);
        int width = Integer.parseInt(command[2]);
        model.createRainbow(height, width, command[3]);
      } catch (ArrayIndexOutOfBoundsException a) {
        System.out.print("Cannot execute rainbow command with given parameters");
        successfulStatus = false;
      } catch (NumberFormatException n) {
        System.out.print("Cannot execute rainbow command with given width/height parameters");
        successfulStatus = false;
      }
    }
  }

}
