package imageprocessor.controller.commands;


import java.awt.event.ActionEvent;
import java.io.IOException;

import imageprocessor.model.ImageModel;
import imageprocessor.view.ImageView;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 * FrenchCommand class generates a French flag.
 */
public class FrenchCommand extends JFrame implements Commands {

  private ImageModel model;
  private ImageView view;

  /**
   * FrenchCommand constructor.
   *
   * @param model (ImageModel)
   * @param view  (ImageView)
   */
  public FrenchCommand(ImageModel model, ImageView view) {
    this.model = model;
    this.view = view;
  }

  /**
   * Executes French flag command.
   *
   * @param event (ActionEvent)
   */
  public void actionPerformed(ActionEvent event) {
    try {
      JLabel input = new JLabel("Width");
      input.setText(JOptionPane.showInputDialog("Enter the width for this flag as an integer"));
      String num = input.getText();
      num = num.replaceAll("\\s", "");
      int width = Integer.parseInt(num);
      model.createFrenchFlag(width);
      model.tempSave();
      view.updateImage(model.getRGB(), model.getWidth(), model.getHeight());
    } catch (IOException ex) {
      ex.getMessage();
      view.setStatus(false);
    }
  }

}
