package view;

import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import model.IImage;
import model.IImageProcessorState;

public class GuiView extends JFrame implements IView, ActionListener, ItemListener {

  private final IImageProcessorState model;

  private final JPanel mainPanel;
  private final JScrollPane mainScrollPane;
  private JLabel mainImageLabel;
  private final JPanel imagePanel;
  private final JLabel checkboxDisplay;
  private final JLabel fileOpenDisplay;
  private final JLabel fileSaveDisplay;
  private final JLabel fileSaveAllDisplay;
  private final JLabel saveAllFileType;
  private final JLabel optionDisplay;
  private final JLabel firstFieldDisplay;
  private final JLabel secondFieldDisplay;
  private final JLabel thirdFieldDisplay;
  private final JLabel fourthFieldDisplay;
  private final JLabel fifthFieldDisplay;
  private final JButton setFieldsButton;
  private final JCheckBox[] checkBoxes;
  private IViewListener viewListener;

  private JMenu menu;
  private JMenu submenu;
  private JMenuItem openItem;
  private JMenuItem saveItem;
  private JMenuItem saveAllItem;
  private JMenuItem blurItem;
  private JMenuItem sharpenItem;
  private JMenuItem greyscaleItem;
  private JMenuItem sepiaItem;
  private JMenuItem downsizeItem;
  private JMenuItem mosaicItem;
  private JMenuItem checkerboardItem;

  public GuiView(IImageProcessorState model) throws IllegalArgumentException {
    super();
    if (model == null) {
      throw new IllegalArgumentException("Model can't be null");
    }
    this.model = model;

    setTitle("Image Processing Application: Made by Christopher Burke and Jonathan Truong");
    setSize(800, 700);

    mainPanel = new JPanel();
    //for elements to be arranged vertically within this panel
    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
    //scroll bars around this main panel
    mainScrollPane = new JScrollPane(mainPanel);
    add(mainScrollPane);

    //show an image with a scrollbar
    imagePanel = new JPanel();
    //a border around the panel with a caption
    imagePanel.setBorder(BorderFactory.createTitledBorder("Showing an image"));
    imagePanel.setLayout(new GridLayout(1, 0, 10, 10));
    //imagePanel.setMaximumSize(null);
    mainPanel.add(imagePanel);

    //checkboxes for layer visibility
    JPanel checkBoxPanel = new JPanel();
    checkBoxPanel.setBorder(BorderFactory.createTitledBorder("Set layer visibility"));

    checkBoxPanel.setLayout(new BoxLayout(checkBoxPanel, BoxLayout.PAGE_AXIS));

    checkBoxes = new JCheckBox[5];
    for (int i = 0; i < checkBoxes.length; i++) {
      checkBoxes[i] = new JCheckBox("Layer " + (i + 1));
      checkBoxes[i].setSelected(false);
      checkBoxes[i].setActionCommand("L" + (i + 1));
      checkBoxes[i].addItemListener(this);
      checkBoxPanel.add(checkBoxes[i]);
    }
    checkboxDisplay = new JLabel("Select a layer to interact with");
    checkBoxPanel.add(checkboxDisplay);
    mainPanel.add(checkBoxPanel);

    //multi-layer image transformations
    JPanel dialogBoxesPanel = new JPanel();
    dialogBoxesPanel
        .setBorder(BorderFactory.createTitledBorder("Multi-layer image processing options"));
    dialogBoxesPanel.setLayout(new BoxLayout(dialogBoxesPanel, BoxLayout.PAGE_AXIS));
    mainPanel.add(dialogBoxesPanel);

    //button to load in a layer
    JPanel fileOpenPanel = new JPanel();
    fileOpenPanel.setLayout(new FlowLayout());
    dialogBoxesPanel.add(fileOpenPanel);
    JButton fileOpenButton = new JButton("Open a file");
    fileOpenButton.setActionCommand("Open file");
    fileOpenButton.addActionListener(this);
    fileOpenPanel.add(fileOpenButton);
    fileOpenDisplay = new JLabel("File path for opening a file will appear here");
    fileOpenPanel.add(fileOpenDisplay);

    //button to save topmost visible layer
    JPanel fileSavePanel = new JPanel();
    fileSavePanel.setLayout(new FlowLayout());
    dialogBoxesPanel.add(fileSavePanel);
    JButton fileSaveButton = new JButton("Save a file");
    fileSaveButton.setActionCommand("Save file");
    fileSaveButton.addActionListener(this);
    fileSavePanel.add(fileSaveButton);
    fileSaveDisplay = new JLabel("File path for saving a file will appear here");
    fileSavePanel.add(fileSaveDisplay);

    //button to save all
    JPanel saveAllPanel = new JPanel();
    saveAllPanel.setLayout(new FlowLayout());
    dialogBoxesPanel.add(saveAllPanel);
    JButton saveAllButton = new JButton("Save all files");
    saveAllButton.setActionCommand("Save all");
    saveAllButton.addActionListener(this);
    saveAllPanel.add(saveAllButton);
    fileSaveAllDisplay = new JLabel("Name of folder images can be found in will appear here ");
    saveAllFileType = new JLabel("Image file type");
    saveAllPanel.add(fileSaveAllDisplay);
    saveAllPanel.add(saveAllFileType);

    //JOptionsPane options dialog
    JPanel optionsDialogPanel = new JPanel();
    optionsDialogPanel.setLayout(new FlowLayout());
    dialogBoxesPanel.add(optionsDialogPanel);

    JButton transformationsButton = new JButton("Click to enter options");
    transformationsButton.setActionCommand("Transformations");
    transformationsButton.addActionListener(this);
    optionsDialogPanel.add(transformationsButton);

    optionDisplay = new JLabel("No transformation selected");
    optionsDialogPanel.add(optionDisplay);

    //button to set the options, if needed, for a transformation
    JButton SetOptionsButton = new JButton("Set Options");
    SetOptionsButton.setActionCommand("Set Options");
    SetOptionsButton.addActionListener(this);
    optionsDialogPanel.add(SetOptionsButton);

    firstFieldDisplay = new JLabel("Field 1");
    secondFieldDisplay = new JLabel("Field 2");
    thirdFieldDisplay = new JLabel("Field 3");
    fourthFieldDisplay = new JLabel("Field 4");
    fifthFieldDisplay = new JLabel("Field 5");
    optionsDialogPanel.add(firstFieldDisplay);
    optionsDialogPanel.add(secondFieldDisplay);
    optionsDialogPanel.add(thirdFieldDisplay);
    optionsDialogPanel.add(fourthFieldDisplay);
    optionsDialogPanel.add(fifthFieldDisplay);

    //button to execute a transformation
    JButton executeTransformationButton = new JButton("Execute");
    executeTransformationButton.setActionCommand("Execute");
    executeTransformationButton.addActionListener(this);
    optionsDialogPanel.add(executeTransformationButton);

    setFieldsButton = new JButton("set fields");
    setFieldsButton.setActionCommand("Set Fields");
    setFieldsButton.addActionListener(this);

    //to show a menu containing all functionality for image processing
    JMenuBar menuBar = new JMenuBar();
    menu = new JMenu("Image Options");
    openItem = new JMenuItem("Open");
    saveItem = new JMenuItem("Save");
    saveAllItem = new JMenuItem("Save all");
    submenu = new JMenu("Transformations");
    blurItem = new JMenuItem("Blur");
    sharpenItem = new JMenuItem("Sharpen");
    greyscaleItem = new JMenuItem("Greyscale");
    sepiaItem = new JMenuItem("Sepia");
    downsizeItem = new JMenuItem("Downsize");
    mosaicItem = new JMenuItem("Mosaic");
    checkerboardItem = new JMenuItem("Checkerboard");

    menu.add(openItem);
    menu.add(saveItem);
    menu.add(saveAllItem);
    submenu.add(blurItem);
    submenu.add(sharpenItem);
    submenu.add(greyscaleItem);
    submenu.add(sepiaItem);
    submenu.add(downsizeItem);
    submenu.add(mosaicItem);
    submenu.add(checkerboardItem);
    menu.add(submenu);
    menuBar.add(menu);

    setJMenuBar(menuBar);

    //on break to eat lunch
  }



  @Override
  public void actionPerformed(ActionEvent arg0) throws IllegalArgumentException {
    switch (arg0.getActionCommand()) {
      case "Open file":
        final JFileChooser loadFileChooser = new JFileChooser(".");
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
            "JPG, PNG Images and TXT files", "jpg", "png", "txt");
        loadFileChooser.setFileFilter(filter);
        int loadValue = loadFileChooser.showOpenDialog(this);
        if (loadValue == JFileChooser.APPROVE_OPTION) {
          File f = loadFileChooser.getSelectedFile();
          fileOpenDisplay.setText(f.getAbsolutePath());
        }

        String image = fileOpenDisplay.getText();

        if (model.getAllImages().size() == 0) {
          JScrollPane imageScrollPane;
          mainImageLabel = new JLabel();
          imageScrollPane = new JScrollPane(mainImageLabel);
          mainImageLabel.setIcon(new ImageIcon(image));
          imageScrollPane.setPreferredSize(new Dimension(100, 400));
          imagePanel.add(imageScrollPane);
        } else {
          mainImageLabel.setIcon(new ImageIcon(image));
        }
        viewListener.handleLoadEvent(image);
        checkBoxes[model.getAllImages().size() - 1].doClick();
        break;

      case "Save file":
        final JFileChooser saveFileChooser = new JFileChooser(".");
        int saveValue = saveFileChooser.showSaveDialog(this);
        if (saveValue == JFileChooser.APPROVE_OPTION) {
          File f = saveFileChooser.getSelectedFile();
          fileSaveDisplay.setText(f.getAbsolutePath());
        }
        viewListener.handleSaveEvent(fileSaveDisplay.getText());
        break;

      case "Save all":
        fileSaveAllDisplay
            .setText(JOptionPane.showInputDialog("Please enter a folder name to put images in."));
        String[] fileTypeOptions = {"JPG", "PNG", "PPM"};
        JComboBox<String> combobox = new JComboBox<>();
        for (String option : fileTypeOptions) {
          combobox.addItem(option);
        }
        int fileType =
            JOptionPane.showOptionDialog(this, "Select a first color for the checkerboard image.",
                "Colors",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.INFORMATION_MESSAGE, null, fileTypeOptions, combobox);
        saveAllFileType.setText(fileTypeOptions[fileType]);
        if (model.getAllImages().size() == 1) {
          JOptionPane.showMessageDialog(this, "One layer has been saved.",
              "Multi-Layer Save Feature", JOptionPane.INFORMATION_MESSAGE);
        } else {
          JOptionPane
              .showMessageDialog(this, model.getAllImages().size() + " layers have been saved.",
                  "Multi-Layer Save Feature", JOptionPane.INFORMATION_MESSAGE);
        }
        viewListener.handleSaveAllEvent(fileSaveAllDisplay.getText(), saveAllFileType.getText());
        break;

      case "Transformations":
        String[] options = {"Blur", "Sharpen", "Greyscale", "Sepia", "Downsize", "Mosaic",
            "Checkerboard"};
        int retvalue = JOptionPane
            .showOptionDialog(this, "Please choose a transformation", "Transformations",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.INFORMATION_MESSAGE, null, options, options[6]);
        optionDisplay.setText(options[retvalue]);
        break;

      case "Set Options":
        this.setOptions(optionDisplay.getText());
        break;

      case "Execute":
        setFieldsButton.doClick();
        viewListener.handleTransformationEvent();
        updateImage();
        if (optionDisplay.getText().equals("Checkerboard")) {
          checkBoxes[model.getAllImages().size() - 1].doClick();
        }
        break;

      case "Set Fields":
        String transformation = optionDisplay.getText();
        String field1 = firstFieldDisplay.getText();
        String field2 = secondFieldDisplay.getText();
        String color1 = thirdFieldDisplay.getText();
        String color2 = fourthFieldDisplay.getText();
        String filepath = fifthFieldDisplay.getText();

        viewListener.setFields(transformation, field1, field2, color1, color2, filepath);
        break;
      default:
        throw new IllegalArgumentException("Unknown button was pressed.");
    }
  }

  private void setOptions(String command) {
    switch (command) {
      case "Blur":
        JOptionPane.showMessageDialog(this, "No options for blur. Please hit execute.",
            "Blur transformation ready", JOptionPane.INFORMATION_MESSAGE);
        break;
      case "Sharpen":
        JOptionPane.showMessageDialog(this, "No options for sharpen. Please hit execute.",
            "Sharpen transformation ready", JOptionPane.INFORMATION_MESSAGE);
        break;
      case "Greyscale":
        JOptionPane.showMessageDialog(this, "No options for greyscale. Please hit execute.",
            "Greyscale transformation ready", JOptionPane.INFORMATION_MESSAGE);
        break;
      case "Sepia":
        JOptionPane.showMessageDialog(this, "No options for sepia. Please hit execute.",
            "Sepia transformation ready", JOptionPane.INFORMATION_MESSAGE);
        break;
      case "Downsize":
        firstFieldDisplay.setText(JOptionPane.showInputDialog(
            "Please enter desired ratio, must be between 0 and 1 (non-inclusive). Must be a double value."));
        double downSizeRatio = parseDouble(firstFieldDisplay.getText());
        break;
      case "Mosaic":
        firstFieldDisplay
            .setText(JOptionPane.showInputDialog(
                "Please enter desired number of seeds. Must be a positive integer."));
        int mosaicSeeds = parseInt(firstFieldDisplay.getText());
        break;
      case "Checkerboard":
        firstFieldDisplay
            .setText(JOptionPane.showInputDialog("Please enter a positive number for tile size."));
        secondFieldDisplay
            .setText(JOptionPane.showInputDialog(
                "Please enter a positive number of tiles across the checkerboard."));

        String[] options = {"Red", "Orange", "Yellow", "Green", "Blue", "Cyan", "Magenta", "Pink",
            "Black", "White", "Gray"};
        JComboBox<String> combobox = new JComboBox<>();

        for (String option : options) {
          combobox.addItem(option);
        }

        int firstColor =
            JOptionPane.showOptionDialog(this, "Select a first color for the checkerboard image.",
                "Colors",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.INFORMATION_MESSAGE, null, options, combobox);
        thirdFieldDisplay.setText(options[firstColor]);

        int secondColor =
            JOptionPane.showOptionDialog(this, "Select a second color for the checkerboard image.",
                "Colors",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.INFORMATION_MESSAGE, null, options, combobox);
        fourthFieldDisplay.setText(options[secondColor]);

        fifthFieldDisplay
            .setText(JOptionPane.showInputDialog(
                "Please enter a valid JPG or PNG type filepath to save the checkerboard image to."));
    }
  }

  private void updateImage() {
    int topMostVisibleLayer = 0;
    for (JCheckBox box : checkBoxes) {
      if (box.isSelected()) {
        topMostVisibleLayer = Character.getNumericValue(box.getText().charAt(6));
      }
    }
    IImage image = model.findTopMostLayer();
    BufferedImage bufferedImage = viewListener.handleImageConversionEvent(image);
    mainImageLabel.setIcon(new ImageIcon(bufferedImage));
  }

  @Override
  public void itemStateChanged(ItemEvent arg0) throws IllegalArgumentException {
    String selected = ((JCheckBox) arg0.getItemSelectable()).getActionCommand();
    try {
      switch (selected) {
        case "L1":
          if (arg0.getStateChange() == ItemEvent.SELECTED) {
            viewListener.handleVisibilityEvent("first", true);
            checkboxDisplay.setText("Layer 1 was made visible");
          } else {
            viewListener.handleVisibilityEvent("first", false);
            checkboxDisplay.setText("Layer 1 was made invisible");
          }
          break;
        case "L2":
          if (arg0.getStateChange() == ItemEvent.SELECTED) {
            viewListener.handleVisibilityEvent("second", true);
            checkboxDisplay.setText("Layer 2 was made visible");
          } else {
            viewListener.handleVisibilityEvent("second", false);
            checkboxDisplay.setText("Layer 2 was made invisible");
          }
          break;
        case "L3":
          if (arg0.getStateChange() == ItemEvent.SELECTED) {
            viewListener.handleVisibilityEvent("third", true);
            checkboxDisplay.setText("Layer 3 was made visible");
          } else {
            viewListener.handleVisibilityEvent("third", false);
            checkboxDisplay.setText("Layer 3 was made invisible");
          }
          break;
        case "L4":
          if (arg0.getStateChange() == ItemEvent.SELECTED) {
            viewListener.handleVisibilityEvent("fourth", true);
            checkboxDisplay.setText("Layer 4 was made visible");
          } else {
            viewListener.handleVisibilityEvent("fourth", false);
            checkboxDisplay.setText("Layer 4 was made invisible");
          }
          break;
        case "L5":
          if (arg0.getStateChange() == ItemEvent.SELECTED) {
            viewListener.handleVisibilityEvent("fifth", true);
            checkboxDisplay.setText("Layer 5 was made visible");
          } else {
            viewListener.handleVisibilityEvent("fifth", false);
            checkboxDisplay.setText("Layer 5 was made invisible");
          }
          break;
        default:
          throw new IllegalArgumentException("Not a valid checkbox click.");
      }
    } catch (IllegalStateException e) {
      JOptionPane.showMessageDialog(this, "Can't change visibility of non-present image!",
          "Please don't do that", JOptionPane.INFORMATION_MESSAGE);
      return;
    }
    try {
      updateImage();
    } catch (IndexOutOfBoundsException e) {

      // this is done such that at least one layer is always visible at all times after initial load
      JOptionPane.showMessageDialog(this, "The image has not been updated.",
          "Please try again", JOptionPane.INFORMATION_MESSAGE);
    }
  }

  @Override
  public void registerViewEventListener(IViewListener listener) {
    this.viewListener = listener;
  }
}