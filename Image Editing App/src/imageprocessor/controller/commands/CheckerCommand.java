package imageprocessor.controller.commands;

import java.awt.event.ActionEvent;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import imageprocessor.model.ImageModel;
import imageprocessor.view.ImageView;


/**
 * CheckerCommand class generates a checkerboard.
 */
public class CheckerCommand extends JFrame implements Commands {

  private ImageModel model;
  private ImageView view;

  public CheckerCommand(ImageModel model, ImageView view) {
    this.model = model;
    this.view = view;
  }

  /**
   * Executes CheckerCommand command.
   *
   * @param event (ActionEvent)
   */
  public void actionPerformed(ActionEvent event) {
    try {
      JLabel square = new JLabel("square size");
      square.setText(JOptionPane.showInputDialog("Enter the square size as an integer"));
      String num = square.getText();
      num = num.replaceAll("\\s", "");
      int squareSize = Integer.parseInt(num);
      model.createCheckerBoard(squareSize);
      model.tempSave();
      view.updateImage(model.getRGB(), model.getWidth(), model.getHeight());
    } catch (IOException ex) {
      ex.getMessage();
      view.setStatus(false);
    }
  }

}
