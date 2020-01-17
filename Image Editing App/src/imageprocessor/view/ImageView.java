package imageprocessor.view;

import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import imageprocessor.controller.ImageController;
import imageprocessor.controller.commands.BlurCommand;
import imageprocessor.controller.commands.CheckerCommand;
import imageprocessor.controller.commands.CreateBatchCommand;
import imageprocessor.controller.commands.DitherCommand;
import imageprocessor.controller.commands.ExecuteScriptCommand;
import imageprocessor.controller.commands.FrenchCommand;
import imageprocessor.controller.commands.GreekCommand;
import imageprocessor.controller.commands.GreyscaleCommand;
import imageprocessor.controller.commands.LoadImageCommand;
import imageprocessor.controller.commands.MosaicCommand;
import imageprocessor.controller.commands.RainbowCommand;
import imageprocessor.controller.commands.RedoCommand;
import imageprocessor.controller.commands.SaveImageCommand;
import imageprocessor.controller.commands.SepiaCommand;
import imageprocessor.controller.commands.SharpenCommand;
import imageprocessor.controller.commands.SwissCommand;
import imageprocessor.controller.commands.UndoCommand;
import imageprocessor.controller.commands.UploadFileCommand;
import imageprocessor.model.ImageModel;
import imageprocessor.model.ImageUtil;


import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.KeyStroke;
import javax.swing.JTextArea;

/**
 * The ImageView class represents the GUI design it extends JFrame and implements ImageViewInt.
 */
public class ImageView extends JFrame implements ImageViewInt {

  private ImageModel model;
  private ImageController controller;
  private boolean status;
  private JMenuBar menuBar;
  private JLabel imageLabel;
  private ImageIcon iconImage;
  private JTextArea inputText;

  /**
   * ImageView constructor used for creating the GUI.
   *
   * @param model      (ImageModel)
   * @param controller (ImageController)
   * @param title      (String)
   */
  public ImageView(ImageModel model, ImageController controller, String title) {
    super(title);
    this.model = model;
    this.controller = controller;
    this.status = true;
    this.setSize(750, 700);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setResizable(true);
    this.setMinimumSize(new Dimension(810, 690));

    this.getContentPane().setLayout(new GridBagLayout());

    //menuBar
    this.menuBar = new JMenuBar();
    this.createMenu();
    this.setJMenuBar(menuBar);

    // commandPanel
    commandButtons();

    // imagePanel
    imageDisplay();

    this.pack();
  }

  private void imageDisplay() {
    JPanel imagePanel = new JPanel();
    imagePanel.setBorder(BorderFactory.createTitledBorder("Image Viewer"));

    this.iconImage = new ImageIcon("defaultImage.jpeg");

    this.imageLabel = new JLabel(iconImage, JLabel.CENTER);
    JScrollPane imageScrollPane = new JScrollPane(imageLabel);
    imageScrollPane.setPreferredSize(new Dimension(500, 500));
    imagePanel.add(imageScrollPane);

    this.add(imagePanel);
  }


  /**
   * Updates GUI with new image.
   *
   * @param newImage (int [][][])
   * @param width    (int)
   * @param height   (int)
   */
  @Override
  public void updateImage(int[][][] newImage, int width, int height) {
    this.iconImage.setImage(ImageUtil.getBufferedImage(newImage, width, height));
    this.imageLabel.updateUI();
  }

  /**
   * Returns the batch text.
   *
   * @return the batch text (string).
   */
  @Override
  public String getBatchText() {
    return this.inputText.getText();
  }

  private void commandButtons() {
    JPanel commandPanel = new JPanel();
    commandPanel.setBorder(BorderFactory.createTitledBorder("Image Tools"));
    commandPanel.setLayout(new BoxLayout(commandPanel, BoxLayout.PAGE_AXIS));

    JButton loadButton = new JButton("Load an image");
    loadButton.setActionCommand("Load an image");
    loadButton.addActionListener(new LoadImageCommand(model, this));
    commandPanel.add(loadButton);

    JButton saveButton = new JButton("Save image as");
    saveButton.setActionCommand("save as");
    saveButton.addActionListener(new SaveImageCommand(model, this));
    commandPanel.add(saveButton);

    JButton uploadFileButton = new JButton("Upload script");
    uploadFileButton.setActionCommand("upload script");
    uploadFileButton.addActionListener(new UploadFileCommand(controller, this));
    commandPanel.add(uploadFileButton);

    JButton undoButton = new JButton("Undo");
    undoButton.setActionCommand("undo command");
    undoButton.addActionListener(new UndoCommand(model, this));
    commandPanel.add(undoButton);

    JButton redoButton = new JButton("Redo");
    redoButton.setActionCommand("redo command");
    redoButton.addActionListener(new RedoCommand(model, this));
    commandPanel.add(redoButton);

    JButton blurButton = new JButton("blur");
    blurButton.setActionCommand("blur filter");
    blurButton.addActionListener(new BlurCommand(model, this));
    commandPanel.add(blurButton);

    JButton sharpenButton = new JButton("sharpen");
    sharpenButton.setActionCommand("sharpen filter");
    sharpenButton.addActionListener(new SharpenCommand(model, this));
    commandPanel.add(sharpenButton);

    JButton sepiaButton = new JButton("sepia");
    sepiaButton.setActionCommand("sepia filter");
    sepiaButton.addActionListener(new SepiaCommand(model, this));
    commandPanel.add(sepiaButton);

    JButton greyscaleButton = new JButton("greyscale");
    greyscaleButton.setActionCommand("greyscale filter");
    greyscaleButton.addActionListener(new GreyscaleCommand(model, this));
    commandPanel.add(greyscaleButton);

    JButton ditherButton = new JButton("dither");
    ditherButton.setActionCommand("dither filter");
    ditherButton.addActionListener(new DitherCommand(model, this));
    commandPanel.add(ditherButton);

    JButton mosaicButton = new JButton("mosaic");
    mosaicButton.setActionCommand("mosaic filter");
    mosaicButton.addActionListener(new MosaicCommand(model, this));
    commandPanel.add(mosaicButton);

    JButton frenchButton = new JButton("create French flag");
    frenchButton.setActionCommand("generate french flag");
    frenchButton.addActionListener(new FrenchCommand(model, this));
    commandPanel.add(frenchButton);

    JButton swissButton = new JButton("create Swiss flag");
    swissButton.setActionCommand("generate swiss flag");
    swissButton.addActionListener(new SwissCommand(model, this));
    commandPanel.add(swissButton);

    JButton greekButton = new JButton("create Greek flag");
    greekButton.setActionCommand("generate greek flag");
    greekButton.addActionListener(new GreekCommand(model, this));
    commandPanel.add(greekButton);

    JButton checkerButton = new JButton("create checkerboard");
    checkerButton.setActionCommand("generate checkerboard");
    checkerButton.addActionListener(new CheckerCommand(model, this));
    commandPanel.add(checkerButton);

    JButton rainbowButton = new JButton("create rainbow");
    rainbowButton.setActionCommand("generate rainbow");
    rainbowButton.addActionListener(new RainbowCommand(model, this));
    commandPanel.add(rainbowButton);

    this.inputText = new JTextArea(5, 15);
    JScrollPane scrollTextPane = new JScrollPane(inputText);
    inputText.setLineWrap(true);
    scrollTextPane.setBorder(BorderFactory.createTitledBorder("Input Script"));
    commandPanel.add(scrollTextPane);

    JButton executeBatch = new JButton("execute input script");
    executeBatch.setActionCommand("execute input script");
    executeBatch.addActionListener(new ExecuteScriptCommand(model, controller, this));
    commandPanel.add(executeBatch);

    this.add(commandPanel);
  }

  private void createMenu() {
    JMenu mainMenu = new JMenu("Main Menu");
    mainMenu.setMnemonic(KeyEvent.VK_X);
    mainMenu.getAccessibleContext().setAccessibleDescription("Shows main load and save options");
    JMenuItem mMenuItem = new JMenuItem("load an image", KeyEvent.VK_L);
    mMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, InputEvent.CTRL_MASK));
    mMenuItem.getAccessibleContext().setAccessibleDescription("load file");
    mMenuItem.addActionListener(new LoadImageCommand(model, this));
    mainMenu.add(mMenuItem);
    mMenuItem = new JMenuItem("save image as", KeyEvent.VK_S);
    mMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
    mMenuItem.getAccessibleContext().setAccessibleDescription("save as");
    mMenuItem.addActionListener(new SaveImageCommand(model, this));
    mainMenu.add(mMenuItem);
    mMenuItem = new JMenuItem("upload script", KeyEvent.VK_A);
    mMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_MASK));
    mMenuItem.getAccessibleContext().setAccessibleDescription("upload script");
    mMenuItem.addActionListener(new UploadFileCommand(controller, this));
    mainMenu.add(mMenuItem);
    mMenuItem = new JMenuItem("create new script", KeyEvent.VK_N);
    mMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_MASK));
    mMenuItem.getAccessibleContext().setAccessibleDescription("create new script");
    mMenuItem.addActionListener(new CreateBatchCommand(model, controller, this));
    mainMenu.add(mMenuItem);
    mMenuItem = new JMenuItem("execute script", KeyEvent.VK_O);
    mMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK));
    mMenuItem.getAccessibleContext().setAccessibleDescription("execute new script");
    mMenuItem.addActionListener(new ExecuteScriptCommand(model, controller, this));
    mainMenu.add(mMenuItem);
    mMenuItem = new JMenuItem("undo", KeyEvent.VK_U);
    mMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U, InputEvent.CTRL_MASK));
    mMenuItem.getAccessibleContext().setAccessibleDescription("undo command");
    mMenuItem.addActionListener(new UndoCommand(model, this));
    mainMenu.add(mMenuItem);
    mMenuItem = new JMenuItem("redo", KeyEvent.VK_R);
    mMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.CTRL_MASK));
    mMenuItem.getAccessibleContext().setAccessibleDescription("redo command");
    mMenuItem.addActionListener(new RedoCommand(model, this));
    mainMenu.add(mMenuItem);
    menuBar.add(mainMenu);

    JMenu filterMenu = new JMenu("Filter Operations");
    filterMenu.setMnemonic(KeyEvent.VK_Y);
    filterMenu.getAccessibleContext().setAccessibleDescription("Shows all filter options");
    JMenuItem fMenuItem = new JMenuItem("sepia", KeyEvent.VK_P);
    fMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.CTRL_MASK));
    fMenuItem.getAccessibleContext().setAccessibleDescription("sepia filter");
    fMenuItem.addActionListener(new SepiaCommand(model, this));
    filterMenu.add(fMenuItem);
    fMenuItem = new JMenuItem("greyscale", KeyEvent.VK_G);
    fMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, InputEvent.CTRL_MASK));
    fMenuItem.getAccessibleContext().setAccessibleDescription("greyscale filter");
    fMenuItem.addActionListener(new GreyscaleCommand(model, this));
    filterMenu.add(fMenuItem);
    fMenuItem = new JMenuItem("sharpen", KeyEvent.VK_E);
    fMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.CTRL_MASK));
    fMenuItem.getAccessibleContext().setAccessibleDescription("sharpen filter");
    fMenuItem.addActionListener(new SharpenCommand(model, this));
    filterMenu.add(fMenuItem);
    fMenuItem = new JMenuItem("blur", KeyEvent.VK_B);
    fMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B, InputEvent.CTRL_MASK));
    fMenuItem.getAccessibleContext().setAccessibleDescription("blur filter");
    fMenuItem.addActionListener(new BlurCommand(model, this));
    filterMenu.add(fMenuItem);
    fMenuItem = new JMenuItem("dither", KeyEvent.VK_D);
    fMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, InputEvent.CTRL_MASK));
    fMenuItem.getAccessibleContext().setAccessibleDescription("dither filter");
    fMenuItem.addActionListener(new DitherCommand(model, this));
    filterMenu.add(fMenuItem);
    fMenuItem = new JMenuItem("mosaic", KeyEvent.VK_M);
    fMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M, InputEvent.CTRL_MASK));
    fMenuItem.getAccessibleContext().setAccessibleDescription("mosaic filter");
    fMenuItem.addActionListener(new MosaicCommand(model, this));
    filterMenu.add(fMenuItem);
    menuBar.add(filterMenu);

    JMenu generatorMenu = new JMenu("Generator Operations");
    generatorMenu.setMnemonic(KeyEvent.VK_Z);
    generatorMenu.getAccessibleContext().setAccessibleDescription("Shows all generator options");
    JMenuItem gMenuItem = new JMenuItem("french", KeyEvent.VK_F);
    gMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, InputEvent.CTRL_MASK));
    gMenuItem.getAccessibleContext().setAccessibleDescription("create french flag");
    gMenuItem.addActionListener(new FrenchCommand(model, this));
    generatorMenu.add(gMenuItem);
    gMenuItem = new JMenuItem("swiss", KeyEvent.VK_W);
    gMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, InputEvent.CTRL_MASK));
    gMenuItem.getAccessibleContext().setAccessibleDescription("create swiss flag");
    gMenuItem.addActionListener(new SwissCommand(model, this));
    generatorMenu.add(gMenuItem);
    gMenuItem = new JMenuItem("greek", KeyEvent.VK_K);
    gMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_K, InputEvent.CTRL_MASK));
    gMenuItem.getAccessibleContext().setAccessibleDescription("create greek flag");
    gMenuItem.addActionListener(new GreekCommand(model, this));
    generatorMenu.add(gMenuItem);
    gMenuItem = new JMenuItem("checker", KeyEvent.VK_C);
    gMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_MASK));
    gMenuItem.getAccessibleContext().setAccessibleDescription("create checker board");
    gMenuItem.addActionListener(new CheckerCommand(model, this));
    generatorMenu.add(gMenuItem);
    gMenuItem = new JMenuItem("rainbow", KeyEvent.VK_I);
    gMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, InputEvent.CTRL_MASK));
    gMenuItem.getAccessibleContext().setAccessibleDescription("create rainbow");
    gMenuItem.addActionListener(new RainbowCommand(model, this));
    generatorMenu.add(gMenuItem);
    menuBar.add(generatorMenu);
  }

  /**
   * Sets status to boolean entered in as input.
   *
   * @param status (boolean)
   */
  @Override
  public void setStatus(boolean status) {
    this.status = status;
  }

}
