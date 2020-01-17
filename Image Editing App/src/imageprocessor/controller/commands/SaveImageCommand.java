package imageprocessor.controller.commands;

import java.awt.event.ActionEvent;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import imageprocessor.model.ImageModel;
import imageprocessor.view.ImageView;

/**
 * SaveImageCommand lets the user save the image.
 */
public class SaveImageCommand extends JFrame implements Commands {

  private ImageModel model;
  private ImageView view;

  public SaveImageCommand(ImageModel model, ImageView view) {
    this.model = model;
    this.view = view;
  }

  /**
   * Executes SaveImage command.
   *
   * @param event (ActionEvent)
   */
  public void actionPerformed(ActionEvent event) {
    try {
      JLabel inputSave = new JLabel("Image Name");
      inputSave.setText(JOptionPane.showInputDialog("Enter name you want to save the image as"));
      String sName = inputSave.getText();
      model.setSaveAsName(sName);
      model.saveAs();
    } catch (IOException e) {
      e.getMessage();
      view.setStatus(false);
    } catch (ArrayIndexOutOfBoundsException a) {
      System.out.print("Cannot save given image");
      view.setStatus(false);
    }
  }

}
