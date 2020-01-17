package imageprocessor.controller.commands;

import java.awt.event.ActionEvent;
import java.io.IOException;

import imageprocessor.controller.ImageController;
import imageprocessor.model.ImageModel;
import imageprocessor.view.ImageView;

import javax.swing.JFrame;

/**
 * ExecuteScriptCommand class executes the script written in the textarea on the GUI.
 */
public class ExecuteScriptCommand extends JFrame implements Commands {

  private ImageModel model;
  private ImageController controller;
  private ImageView view;

  /**
   * Constructor for the ExecuteScriptCommand.
   * @param model (ImageModel)
   * @param controller (ImageController)
   * @param view (ImageView)
   */
  public ExecuteScriptCommand(ImageModel model, ImageController controller, ImageView view) {
    this.controller = controller;
    this.model = model;
    this.view = view;
  }

  /**
   * Executes ExecuteScript command.
   *
   * @param event (ActionEvent)
   */
  public void actionPerformed(ActionEvent event) {
    try {
      String entered = view.getBatchText();
      controller.executeCommands(entered);
      view.updateImage(model.getRGB(), model.getWidth(), model.getHeight());
    } catch (IOException ex) {
      ex.getMessage();
      view.setStatus(false);
    }
  }

}