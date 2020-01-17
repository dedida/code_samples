package imageprocessor.controller.commands;

import java.awt.event.ActionEvent;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import imageprocessor.model.ImageModel;
import imageprocessor.view.ImageView;

/**
 * MosaicCommand class executes the mosaic filter on the image.
 */
public class MosaicCommand extends JFrame implements Commands {

  private ImageModel model;
  private ImageView view;

  public MosaicCommand(ImageModel model, ImageView view) {
    this.model = model;
    this.view = view;
  }

  /**
   * Executes mosaic command.
   *
   * @param event (ActionEvent)
   */
  public void actionPerformed(ActionEvent event) {
    try {
      JLabel inputDisplay = new JLabel("Width");
      inputDisplay.setText(JOptionPane.showInputDialog("Enter the number of panels for the "
              + "mosaic filter as an integer"));
      String seedNum = inputDisplay.getText().replaceAll("\\s", "");
      int seeds = Integer.parseInt(seedNum);
      model.filterMosaic(seeds);
      model.tempSave();
      view.updateImage(model.getRGB(), model.getWidth(), model.getHeight());
    } catch (IOException ex) {
      ex.getMessage();
      view.setStatus(false);
    }
  }

}
