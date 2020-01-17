package imageprocessor.controller.commands;

import javax.swing.JFrame;
import java.awt.event.ActionEvent;
import imageprocessor.controller.ImageController;
import imageprocessor.model.ImageModel;
import imageprocessor.view.ImageView;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.JOptionPane;


/**
 * This class allows the user to create a batch command file and run it.
 */
public class CreateBatchCommand extends JFrame implements Commands {
  private ImageModel model;
  private ImageController controller;
  private ImageView view;

  /**
   * Constructor for the CreateBatchCommand.
   * @param model (ImageModel)
   * @param controller (ImageController)
   * @param view (ImageView)
   */
  public CreateBatchCommand(ImageModel model, ImageController controller, ImageView view) {
    this.model = model;
    this.controller = controller;
    this.view = view;
  }

  /**
   * Executes create batch command.
   *
   * @param event (ActionEvent)
   */
  public void actionPerformed(ActionEvent event) {
    try {
      JPanel batchPanel = new JPanel(new BorderLayout());
      batchPanel.setLayout(new GridLayout(1,1));

      batchPanel.setBorder(BorderFactory.createEtchedBorder());

      JTextArea display = new JTextArea(16, 30);
      display.setEditable(true); // set textArea non-editable
      JScrollPane scroll = new JScrollPane(display);
      scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
      batchPanel.add(scroll);


      JOptionPane.showConfirmDialog(this, batchPanel, "Create Script",
              JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);

      String entered = display.getText();
      controller.executeCommands(entered);
      view.updateImage(model.getRGB(), model.getWidth(), model.getHeight());
    }
    catch (IOException ex) {
      ex.getMessage();
      view.setStatus(false);
    }
  }

}
