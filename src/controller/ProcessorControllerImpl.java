package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;


import controller.commands.BlueComp;
import controller.commands.Blur;
import controller.commands.Brighten;
import controller.commands.GreenComp;
import controller.commands.GreyscaleTransformation;
import controller.commands.HorizontalFlip;
import controller.commands.IntensityComp;
import controller.commands.LumaComp;
import controller.commands.RedComp;
import controller.commands.SepiaTransformation;
import controller.commands.Sharpen;
import controller.commands.ValueComp;
import controller.commands.VerticalFlip;
import model.ImageProcessorFilter;
import model.Pixel;
import view.ProcessorView;

/**
 * This class represents an implementation of the Processor Controller interface that supports
 * the interactive text mode (user can type and execute a certain command one line at a time).
 * Thus, it's able to receive inputs from the user through the Readable and transmits these inputs
 * to the model and then also to the view where the user is told whether the command was
 * successfully completed or produced an error (ex. file does not exist ).
 */
public class ProcessorControllerImpl implements ProcessorController {
  private Readable readable;
  private ImageProcessorFilter image;

  private ProcessorView view;

  private int brightenIncrement;

  private List<String> commands;

  /**
   * Creates a controller object with a readable for the inputs, an Image Processor Filter
   * for the model and a ProcessorView as the view.
   *
   * @param readable Readable object where user inputs commands
   * @param image    Model to execute commands upon
   * @param view     Where to display output
   * @throws IllegalArgumentException if any of the supplied inputs are null
   */
  public ProcessorControllerImpl(Readable readable,
                                 ImageProcessorFilter image, ProcessorView view) {
    if (readable == null || image == null || view == null) {
      throw new IllegalArgumentException("Model, view and/or readable supplied is invalid.");
    }
    this.readable = readable;
    this.image = image;
    this.view = view;
    this.commands = new ArrayList<>();
  }

  /**
   * When the user enters a new line, takes all the inputs that the user has provided
   * to split up the inputs into an array. Depending on the inputs in the array and the
   * array's size, performs the parses through the inputs and sends the inputs to be
   * executed in the model.
   */
  @Override
  public void control() {
    Scanner sc = new Scanner(this.readable);
    try {
      boolean quit = false;
      this.view.renderMessage(this.writeMenu());
      while (!quit && sc.hasNext()) {
        String line = sc.nextLine();
        String[] inputs = line.split(" ");
        commands = Arrays.asList(inputs);
        String userInstruction = commands.get(0);
        if (userInstruction.equals("quit") || userInstruction.equals("q")) {
          quit = true;
          this.view.renderMessage("Program has quit.");
        } else if (userInstruction.equals("brighten")) {
          List<String> inputs2 = this.parseInputs(commands);
          commands = inputs2;
        }

        if (commands.size() == 3) {
          processCommand(sc, image, commands);
        } else if (commands.size() > 3) {
          String filePath = commands.get(2);
          ImageConverter converter = this.createConverter(filePath);
          try {
            if (converter != null) {
              converter.reader(filePath);
            }
          } catch (IllegalArgumentException e) {
            view.renderMessage(e.getMessage());
          }
          List<List<Pixel>> maskImage = converter.getImage();
          this.image.partialImageManipulation(userInstruction,
                  commands.get(1), maskImage, commands.get(3));
          List<String> partialInput = List.of(commands.get(0),
                  "PartialImage", commands.get(3));
          processCommand(sc, image, partialInput);
          List<List<Pixel>> pixelsChanged = this.image.getImage(commands.get(3));
          this.image.recombine(this.image.getImage(commands.get(1)), pixelsChanged,
                  commands.get(3), maskImage);
        }
      }
    } catch (IOException e) {
      throw new IllegalStateException("Output is not transmittable.");
    }
  }

  /**
   * Takes in the supplied inputs and parses through the inputs to update the
   * brighten increment and also keep all the other inputs to be passed to the model later.
   * @param inputs supplied list of inputs
   * @return parsed inputs list
   */
  private List<String> parseInputs(List<String> inputs) {
    List<String> result = new ArrayList<>();
    for (int i = 0; i < inputs.size(); i++) {
      try {
        int bi = Integer.parseInt(inputs.get(i));
        brightenIncrement = bi;
      } catch (NumberFormatException e) {
        result.add(inputs.get(i));
      }
    }
    return result;
  }


  /**
   * Processes the command based on the user instruction provided. The command is first initialized
   * to null and then if a valid user instruction is passed in, the inputs will then be sent to the
   * model through the command classes.
   * Invalid errors that occur like spelling the command incorrectly will be sent to the view
   * to display the error to the user.
   *
   * @param sc              scanner object
   * @param image           model
   * @throws IllegalStateException if appendable is corrupted
   */
  private void processCommand(Scanner sc, ImageProcessorFilter image, List<String> elements) {
    ControllerCommands command = null;
    try {
      switch (elements.get(0)) {
        case "load":
          this.loadFile(elements.get(1), elements.get(2));
          break;
        case "save":
          this.save(elements.get(1), elements.get(2));
          break;
        case "brighten":
          command = new Brighten(brightenIncrement, elements.get(1), elements.get(2));
          break;
        case "vertical-flip":
          command = new VerticalFlip(elements.get(1), elements.get(2));
          break;
        case "horizontal-flip":
          command = new HorizontalFlip(elements.get(1), elements.get(2));
          break;
        case "red-component":
          command = new RedComp(elements.get(1), elements.get(2));
          break;
        case "blue-component":
          command = new BlueComp(elements.get(1), elements.get(2));
          break;
        case "green-component":
          command = new GreenComp(elements.get(1), elements.get(2));
          break;
        case "value-component":
          command = new ValueComp(elements.get(1), elements.get(2));
          break;
        case "luma-component":
          command = new LumaComp(elements.get(1), elements.get(2));
          break;
        case "intensity-component":
          command = new IntensityComp(elements.get(1), elements.get(2));
          break;
        case "sepia":
          command = new SepiaTransformation(elements.get(1), elements.get(2));
          break;
        case "greyscale":
          command = new GreyscaleTransformation(elements.get(1), elements.get(2));
          break;
        case "blur":
          command = new Blur(elements.get(1), elements.get(2));
          break;
        case "sharpen":
          command = new Sharpen(elements.get(1), elements.get(2));
          break;
        default:
          try {
            this.view.renderMessage("Command is not supported yet :).\n");
            sc.nextLine();
          } catch (IOException e) {
            throw new IllegalStateException("Output is not transmissible.");
          }
      }
      if (command != null) {
        try {
          command.execute(image);
          this.view.renderMessage("Operation performed successfully.\n");
        } catch (IllegalArgumentException e) {
          this.view.renderMessage(e.getMessage() + "\n");
        }
      }
    } catch (NoSuchElementException | IOException e) {
      try {
        this.view.renderMessage("Not enough information, please refer to the menu.\n");
        sc.nextLine();
      } catch (IOException m) {
        throw new IllegalStateException("Output is not transmissible.");
      }
    }
  }


  /**
   * Loads the file via a supplied valid file path. The file is referred to henceforth
   * by the image name supplied. Currently, a file path with the extension ppm, jpg, png or bmp
   * is supported by this program.
   *
   * @param filePath  file path
   * @param imageName desired image name
   * @throws IllegalStateException if appendable is corrupted
   */
  private void loadFile(String filePath, String imageName) {
    try {
      ImageConverter converter = this.createConverter(filePath);
      if (converter != null) {
        try {
          converter.reader(filePath);
          image.load(imageName, converter.getImage());
          this.view.renderMessage("'" + imageName + "'" + " was loaded into program " +
                  "from path " + filePath + ".\n");
        } catch (IllegalArgumentException | IllegalStateException e) {
          this.view.renderMessage(e.getMessage() + "\n");
        }
      }
    } catch (IOException e) {
      throw new IllegalStateException("Output is not transmissible.");
    }
  }

  /**
   * Obtains the 2D arraylist of pixels to be saved through the model. If no image name is
   * found in the model's hashmap, then a message will be sent to the view stating that
   * no file was found. The appropriate converter created based on the file path will then
   * write to the file.
   *
   * @param filePath  file path where the image will be saved to
   * @param imageName name image will be saved under
   * @throws IllegalStateException if output is not transmissible
   */
  public void save(String filePath, String imageName) {
    try {
      ImageConverter converter = this.createConverter(filePath);
      if (converter != null) {
        try {
          List<List<Pixel>> saveImage = image.getImage(imageName);
          converter.save(saveImage, filePath);
          this.view.renderMessage("'" + imageName + "'" + " was saved " +
                  "to path " + filePath + ".\n");
        } catch (IllegalArgumentException | IllegalStateException e) {
          this.view.renderMessage(e.getMessage() + "\n");
        }
      }
    } catch (IOException e) {
      throw new IllegalStateException("Output is not transmissible.");
    }
  }

  /**
   * Creates the correct converter type for the specific file path supplied as files with a
   * ppm extension are processed differently than standard file extension like jpg. If
   * file path extension are not ppm, jpg, bmp or png, then converter will remain null and
   * a message will be sent to the view.
   *
   * @param filePath file path supplied where the image will be loaded from or saved to
   * @return an appropriate ImageConverter
   */
  private ImageConverter createConverter(String filePath) {
    ImageConverter converter = null;
    try {
      switch (filePath.substring(filePath.length() - 3)) {
        case "ppm":
          converter = new PPMImageConverter();
          break;
        case "jpg":
        case "png":
        case "bmp":
          converter = new StandardImageConverter();
          break;
        default:
          this.view.renderMessage("File extension is not supported.\n");
      }
    } catch (IOException e) {
      throw new IllegalStateException("Output is not transmissible.");
    }
    return converter;
  }

  /**
   * Returns the menu for the program that states the instructions for the multiple commands
   * and how to end the program.
   *
   * @return a menu as a String
   */
  private String writeMenu() {
    return "Welcome! This image processing program supports many operations to load, manipulate " +
            "and save image.\n" +
            "To load an image : 'load image-path image-name'\n" +
            "To flip an image horizontally or vertically : 'horizontal-flip image-name " +
            "dest-image-name' " +
            "or 'vertical-flip image-name dest-image-name\n" +
            "To brighten an image : 'brighten increment image-name dest-image-name'\n" +
            "To grey-scale an image based on a channel: 'channel-component image-name " +
            "dest-image-name'\n" + "(channels : red, green, blue, value, intensity and luma)\n" +
            "Example : 'red-component image-name dest-image-name'\n" +
            "To perform a color transformation : 'transformation image-name dest-image-name'" +
            "(transformations : greyscale, sepia)\nTo filter : 'filter image-name dest-image-" +
            "name (filters : blur, sharpen)\n" +
            "To save an image : 'save image-path image-name'\n" + "To quit the program : " +
            "'quit' or 'q'\n";
  }


}
