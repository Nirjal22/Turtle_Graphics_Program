import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;
import javax.swing.*;

import uk.ac.leedsbeckett.oop.OOPGraphics;

public class TurtleGraphics extends OOPGraphics {
    JMenuBar menuBar;
    JMenu fileMenu;

    JMenuItem saveItem;
    JMenuItem loadItem;
    JMenuItem exitItem;

    private PrintWriter writer;

    //driver program
    public static void main(String[] args) {
        new TurtleGraphics(); // create instance of class that extends OOPGraphics
    }

    //constructor
    public TurtleGraphics() {
        JFrame MainFrame = new JFrame();
        MainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        MainFrame.setLayout(new FlowLayout());
        MainFrame.add(this);
        MainFrame.pack();
        //adding menuBar
        menuBar = new JMenuBar();
        fileMenu = new JMenu("File");
        menuBar.add(fileMenu);
        saveItem = new JMenuItem("Save");
        loadItem = new JMenuItem("Load");
        exitItem = new JMenuItem("Exit");
        fileMenu.add(saveItem);
        fileMenu.add(loadItem);
        fileMenu.add(exitItem);
        //adding actionListener
        loadItem.addActionListener(e -> loadFile());
        saveItem.addActionListener(e -> saveFile());
        exitItem.addActionListener(e -> exitProgram());
        MainFrame.setJMenuBar(menuBar);
        MainFrame.setVisible(true);

        about();

        //creates and writes on a file
        try{
            writer = new PrintWriter(new FileWriter("commands.txt"));
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    //loads the file
    private boolean loadFile() {
        JFileChooser fileChooser = new JFileChooser();
        int response = fileChooser.showOpenDialog(null);
        if (response == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            if (selectedFile != null) {
                try {
                    File file = new File(selectedFile.getAbsolutePath());
                    System.out.println(file);
                    if (!Desktop.isDesktopSupported()) {
                        System.out.println("Not supported.");
                        return false;
                    } else {
                        Desktop desktop = Desktop.getDesktop();
                        desktop.open(file);
                        return true;
                    }
                } catch (IOException ex) {
                    System.err.println("Error opening file: " + ex.getMessage());
                    ex.printStackTrace();
                    return false;
                }
            }
        }
        return false;
    }


    //this method is called when the user clicks on save
    private void saveFile(){
        //calling the methods
        saveCommandsToFile();
        saveDrawingToImage();
        displayMessage("saved files :)");
    }

    //create and writes on the command.txt file
    private void saveCommandsToFile(){
        writer.close();
        System.out.println("saved commands to file.");
    }

    //create and write on the drawing.png file
    private void saveDrawingToImage(){
        BufferedImage image = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();

        // Paint the drawing onto the BufferedImage
        paint(g2d);

        // Dispose of the Graphics2D object
        g2d.dispose();

        // Define the file name for the PNG image
        String fileName = "drawing.png";

        // Write the BufferedImage to the file in PNG format
        try {
            ImageIO.write(image, "png", new File(fileName));
            System.out.println("Saved drawing to " + fileName);
        } catch (IOException e) {
            System.err.println("Error: Couldn't save drawing to " + fileName);
            e.printStackTrace();
        }
    }

    //exits from the program when the user clicks on exit
    private void exitProgram(){
        System.exit(0);
    }

    @Override
    public void about() {
        super.about(); //calling parent class
        clear(); // clear the display
        printNIRJAL(); // calling the method.
        reset();
    }

//prints the name of the developer (That's me :) )
    public void printNIRJAL() {
        drawLetterN(); // Draw the letter "N"
        forward("20");
        drawLetterI(); // Draw the letter "I"
        drawLetterR(); // Draw the letter "R"
        drawLetterJ(); // Draw the letter "J"
        drawLetterA(); // Draw the letter "A"
        drawLetterL(); // Draw the letter "L"
    }

    private void drawLetterN() {
        turnRight(90);
        forward(300);
        turnRight(90);
        forward(135);
        turnRight(180);
        penDown();
        forward(100);
        penUp();
        turnRight(180);
        forward(100);
        turnRight(150);
        penDown();
        setPenColour(Color.white);
        forward(110);
        turnLeft(150);
        setPenColour(Color.BLUE);
        forward(100);
        penUp();
    }

    private void drawLetterI() {
        turnLeft(270);
        forward(50);
        turnLeft(270);
        forward(20);
        setPenColour(Color.white);
        penDown();
        forward(100);
        penUp();
    }

    private void drawLetterR() {
        turnRight(180);
        forward(100);
        turnLeft(270);
        forward(70);
        turnLeft(270);
        penDown();
        setPenColour(Color.blue);
        forward(100);
        penUp();
        turnLeft(180);
        forward(100);
        turnRight(90);
        penDown();
        forward(80);
        turnRight(150);
        forward(98);
        turnLeft(110);
        forward(80);
        penUp();

    }

    private void drawLetterJ() {
        reset();
        turnLeft(180);
        forward(140);
        setPenColour(Color.GREEN);
        turnLeft(180);
        penDown();
        forward(100);
        turnRight(90);
        forward(40);
        turnRight(90);
        forward(20);
        penUp();
    }

    private void drawLetterA() {
        turnRight(180);
        forward(20);
        turnLeft(90);
        forward(100);
        setPenColour(Color.RED);
        turnLeft(70);
        penDown();
        forward(100);
        turnRight(140);
        forward(100);
        turnRight(180);
        penUp();
        forward(50);
        turnLeft(70);
        penDown();
        forward(40);
        penUp();
    }

    private void drawLetterL() {
        setPenColour(Color.white);
        turnRight(180);
        forward(100);
        turnLeft(90);
        forward(40);
        penDown();
        turnLeft(180);
        forward(100);
        turnLeft(90);
        forward(50);
        penUp();
    }

    //method for square <size>
    private void drawSquare(int length){
        for (int i = 0;i<4;i++){
            forward(length);
            turnRight(90);
        }
    }

    //method for backward <size>
    private void backward(int length){
        turnRight(180);
        forward(length);
    }

    private void penWidth(int width){
        setStroke(width);
    }

    //method for triangle <size>
    private void drawTriangle(int height){
        double sideLength = height*Math.sqrt(3)/2;
        for (int i = height-3;i<height;i++){
            forward((int) sideLength);
            turnRight(120);
        }
    }


    //overloading drawTriangle() method
    private void drawTriangle(int side1, int side2, int side3) {
        if (side1 + side2 > side3 && side1 + side3 > side2 && side2 + side3 > side1) {
            forward(side1);
            turnRight(120);
            forward(side2);
            turnRight(120);
            forward(side3);
        } else {
            displayMessage("Error! Invalid sides for triangle.");
        }
    }


    //this command runs whenever something happens on jtextfield.
    public void processCommand(String command) {
        // Split the input command by spaces
        String[] commands = command.trim().split("\\s+");

        // Check if there are no commands
        if (commands.length == 0) {
            displayMessage("Error: No command entered.");
            return;
        }

        // Extract the command keyword
        String cmd = commands[0].toLowerCase(); //changing into lowercase

        // Process each command
        //switch case
        switch (cmd) {
            case "penwidth":
                if (commands.length>1){
                    try{
                        int width = Integer.parseInt(commands[1]);
                        penWidth(width);
                        displayMessage("penwidth set to "+width);
                        writer.println("penwidth "+width);
                    }catch (NumberFormatException e){
                        displayMessage("Error: Invalid parameter");
                    }
                }else {
                    displayMessage("Error! Missing parameter");
                }
                break;
            case "triangle":
                if (commands.length > 1) {
                    if (commands[1].contains(",")) {
                        String[] values = commands[1].split(",");
                        if (values.length == 3) {
                            // User provides three parameters (for triangle sides)
                            try {
                                int side1 = Integer.parseInt(values[0].trim());
                                int side2 = Integer.parseInt(values[1].trim());
                                int side3 = Integer.parseInt(values[2].trim());
                                drawTriangle(side1, side2, side3);
                                writer.println("triangle "+side1+", "+side2+", "+side3);
                                displayMessage("Triangle printed with specified sides");
                            } catch (NumberFormatException e) {
                                displayMessage("Invalid parameter! Please provide valid side lengths to draw the triangle.");
                            }
                        } else {
                            displayMessage("Error: Incorrect number of parameters. Please provide three side lengths separated by commas.");
                        }
                    } else {
                        // User provides one parameter (for triangle height)
                        try {
                            int height = Integer.parseInt(commands[1].trim());
                            drawTriangle(height);
                            writer.println("triangle " + height);
                            displayMessage("Triangle printed with specified height");
                        } catch (NumberFormatException e) {
                            displayMessage("Invalid parameter! Please provide a valid height to draw the triangle.");
                        }
                    }
                } else {
                    displayMessage("Error: Missing parameters for triangle command.");
                }
                break;
            case "penup":
                penUp();
                writer.println("penUp()"); //writes on the file if the command is valid.
                displayMessage("penup");
                break;
            case "pendown":
                penDown();
                writer.println("penDown()"); //writes on the file if the command is valid.
                displayMessage("Pendown");
                break;
            case "turnleft":
                if (commands.length > 1) {
                    try {
                        int degrees = Integer.parseInt(commands[1]);
                        turnLeft(degrees);
                        writer.println("turnLeft "+degrees); //writes on the file if the command is valid.
                        displayMessage("Turned left");
                    } catch (NumberFormatException e) {
                        displayMessage("Error: Invalid parameter for turnleft command.");
                    }
                } else {
                    displayMessage("Error: Missing parameter for turnleft command.");
                }
                break;
            case "turnright":
                if (commands.length > 1) {
                    try {
                        int degrees = Integer.parseInt(commands[1]);
                        turnRight(degrees);
                        writer.println("turnRight "+degrees); //writes on the file if the command is valid.
                        displayMessage("Turned right");
                    } catch (NumberFormatException e) {
                        displayMessage("Error: Invalid parameter for turnright command.");
                    }
                } else {
                    displayMessage("Error: Missing parameter for turnright command.");
                }
                break;
            case "forward":
                if (commands.length > 1) {
                    try {
                        int distance = Integer.parseInt(commands[1]);
                        forward(distance);
                        writer.println("forward "+distance); //writes on the file if the command is valid.
                        displayMessage("forward command runs successfully");
                    } catch (NumberFormatException e) {
                        displayMessage("Error: Invalid parameter for forward command.");
                    }
                } else {
                    displayMessage("Error: Missing parameter for forward command.");
                }
                break;
            case "black":
                setPenColour(Color.BLACK);
                writer.println("penColour BLACK"); //writes on the file if the command is valid.
                displayMessage("Black colour");
                break;
            case "green":
                setPenColour(Color.GREEN);
                writer.println("penColour GREEN"); //writes on the file if the command is valid.
                displayMessage("Green colour");
                break;
            case "red":
                setPenColour(Color.RED);
                writer.println("penColour RED"); //writes on the file if the command is valid.
                displayMessage("Red colour");
                break;
            case "white":
                setPenColour(Color.WHITE);
                writer.println("penColour WHITE"); //writes on the file if the command is valid.
                displayMessage("White colour");
                break;
            case "reset":
                reset();
                writer.println("reset()"); //writes on the file if the command is valid.
                displayMessage("Reset the position of turtle");
                break;
            case "clear":
                clear();
                displayMessage("Cleared the screen.");
                break;
            case "about":
                about();
                writer.println("about()"); //writes on the file if the command is valid.
                displayMessage("Displays about().");
                break;
            case "square":
                if (commands.length>1){
                    try {
                        int length = Integer.parseInt(commands[1]);
                        drawSquare(length);
                        writer.println("square "+length); //writes on the file if the command is valid.
                        displayMessage("Square command runs successfully!");
                    }catch (NumberFormatException e){
                        displayMessage("Error: Invalid parameters for square command.");
                    }
                }else {
                    displayMessage("Error: Missing parameters for square command.");
                }
                break;
            case "pencolour":
                if (commands.length > 1) {
                    try {
                        String[] rgbValues = commands[1].split(",");
                        if (rgbValues.length == 3) {
                            int red = Integer.parseInt(rgbValues[0]);
                            int green = Integer.parseInt(rgbValues[1]);
                            int blue = Integer.parseInt(rgbValues[2]);
                            setPenColour(new Color(red, green, blue));
                            writer.println("penColour "+red+", " +green+", " + blue); //writes on the file if the command is valid.
                            displayMessage("Changing the pen color!");
                        } else {
                            displayMessage("Error: Incorrect number of parameters for pencolour command.");
                        }
                    } catch (NumberFormatException e) {
                        displayMessage("Error: Invalid parameters for pencolour command.");
                    }
                } else {
                    displayMessage("Error: Missing parameters for pencolour command.");
                }
                break;
            case "backward":
                if (commands.length > 1) {
                    try {
                        int distance = Integer.parseInt(commands[1]);
                        backward(distance);
                        writer.println("backward "+distance); //writes on the file if the command is valid.
                        displayMessage("Backward command runs successfully!");
                    } catch (NumberFormatException e) {
                        displayMessage("Error: Invalid parameter for forward command.");
                    }
                } else {
                    displayMessage("Error: Missing parameter for forward command.");
                }
                break;
            default:
                displayMessage("Error: Unknown command.");
        }
    }
}
