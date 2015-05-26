import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList; // to use ArrayList

/**
 * @author      Christopher Yang <cyang001@citymail.cuny.edu>
 * @version     1.8
 * @since       2015-04-02
 */

public class VirtualEcosystem extends JFrame implements Serializable{

    // Member fields
    private Plain[][] plain;
    private ArrayList<Animal> carnivoreArr;
    private ArrayList<Animal> herbivoreArr;
    private ArrayList<Plant> plantArr;
    private ArrayList<Obstacle> obstacleArr;
    private String output;
    private boolean running = false;

    private static VirtualEcosystem instance;
    private static JTextArea textArea;

    // State of the program
    private static String state = "Stop";

    public void populateWorld(){
        // Adds the objects in respective ArrayLists
        this.carnivoreArr = new ArrayList<Animal>();
        for (int i = 0; i < 20; i++)
            this.carnivoreArr.add(new Carnivore());

        this.herbivoreArr = new ArrayList<Animal>();
        for (int i = 0; i < 20; i++)
            this.herbivoreArr.add(new Herbivore());

        this.obstacleArr = new ArrayList<Obstacle>();
        for (int i = 0; i < 20; i++)
            this.obstacleArr.add(new Obstacle());

        this.plantArr = new ArrayList<Plant>();
        for (int i = 0; i < 20; i++)
            this.plantArr.add(new Plant());

        // Create a 2D array that stores the super class
        this.plain = new Plain[32][32];

        // Initializes the 2D array with object FreeSoace
        for (int row = 0; row < 32; row++) {
            for (int column = 0; column < 32; column++)
                this.plain[row][column] = new FreeSpace(row, column);
        }

        // Go through the ArrayLists and add the objects into the 2D array only if the pointer contains
        // an instance of FreeSpace
        for (Animal c: this.carnivoreArr) {
            if (this.plain[c.getX()][c.getY()] instanceof FreeSpace)
                this.plain[c.getX()][c.getY()] = c;
        }
        for (Animal h: this.herbivoreArr) {
            if (this.plain[h.getX()][h.getY()] instanceof FreeSpace)
                this.plain[h.getX()][h.getY()] = h;

        }
        for (Obstacle o: this.obstacleArr) {
            if (this.plain[o.getX()][o.getY()] instanceof FreeSpace)
                this.plain[o.getX()][o.getY()] = o;
        }
        for (Plant p: this.plantArr){
            if (this.plain[p.getX()][p.getY()] instanceof FreeSpace)
                this.plain[p.getX()][p.getY()] = p;
        }
    }

    private void runProgram(){
        this.running = true;

        while(this.running) {

            this.output = "";

            // Print the characters of the plain
            for (int i = 0; i < 32; i++) {
                for (int j = 0; j < 32; j++) {
                    this.output += this.plain[i][j].getSymbol();
                }
                this.output +="\n";
            }

            // Loop that handles the interaction of Carnivores with other objects
            for (int i = 0; i < this.carnivoreArr.size(); i++) {

                // Reproduction occurs if energy level is 75 or greater and if the get is greater than 10
                if (this.carnivoreArr.get(i).getEnergy() >= 75 && this.carnivoreArr.get(i).getAge() >= 10){
                    Animal baby = new Carnivore(this.carnivoreArr.get(i).getX(), this.carnivoreArr.get(i).getY());

                    // Only reproduce if it is in the coordinates of a FreeSpace
                    if (this.plain[baby.getX()][baby.getY()] instanceof FreeSpace) {
                        this.plain[baby.getX()][baby.getY()] = baby;
                        this.carnivoreArr.add(baby);
                    }
                }

                // When the Carnivore is hungry their energy level is below 75
                if (this.carnivoreArr.get(i).getEnergy() < 75){
                    Animal current = this.carnivoreArr.get(i);
                    int position = 0;

                    // Checks all 9 coordinates that are one unit away from the Carnivore for a Herbivore to eat
                    // Once the Herbivore is eaten, the Herbivore is replaced with a FreeSpace
                    switch(position) {
                        case 0:
                            if (current.getX() + 1 < 32 && current.getY() + 1 < 32){
                                if (this.plain[current.getX() + 1][current.getY() + 1] instanceof Herbivore) {
                                    current.eat();
                                    this.plain[current.getX() + 1][current.getY() + 1] = new FreeSpace(current.getX() + 1, current.getY() + 1);
                                }
                            }
                        case 1:
                            if (current.getX() + 1 < 32) {
                                if (this.plain[current.getX() + 1][current.getY()] instanceof Herbivore) {
                                    current.eat();
                                    this.plain[current.getX() + 1][current.getY()] = new FreeSpace(current.getX() + 1, current.getY());
                                }
                            }
                        case 2:
                            if (current.getX() + 1 < 32 && current.getY() - 1 >= 0) {
                                if (this.plain[current.getX() + 1][current.getY() - 1] instanceof Herbivore) {
                                    current.eat();
                                    this.plain[current.getX() + 1][current.getY() - 1] = new FreeSpace(current.getX() + 1, current.getY() - 1);
                                }
                            }
                        case 3:
                            if (current.getY() + 1 < 32) {
                                if (this.plain[current.getX()][current.getY() + 1] instanceof Herbivore) {
                                    current.eat();
                                    this.plain[current.getX()][current.getY() + 1] = new FreeSpace(current.getX(), current.getY() + 1);
                                }
                            }
                        case 4:
                            if (this.plain[current.getX()][current.getY()] instanceof Herbivore ) {
                                current.eat();
                                this.plain[current.getX()][current.getY()] = new FreeSpace(current.getX(), current.getY());
                            }
                        case 5:
                            if (current.getY() - 1 >= 0) {
                                if (this.plain[current.getX()][current.getY() - 1] instanceof Herbivore) {
                                    current.eat();
                                    this.plain[current.getX()][current.getY() - 1] = new FreeSpace(current.getX(), current.getY() - 1);
                                }
                            }
                        case 6:
                            if (current.getX() - 1 >= 0 && current.getY() + 1 < 32) {
                                if (this.plain[current.getX() - 1][current.getY() + 1] instanceof Herbivore) {
                                    current.eat();
                                    this.plain[current.getX()-1][current.getY() + 1] = new FreeSpace(current.getX() - 1, current.getY() + 1);
                                }
                            }
                        case 7:
                            if (current.getX() - 1 >= 0) {
                                if (this.plain[current.getX() - 1][current.getY()] instanceof Herbivore) {
                                    current.eat();
                                    this.plain[current.getX() - 1][current.getY()] = new FreeSpace(current.getX() - 1, current.getY());
                                }
                            }
                        case 8:
                            if (current.getX() - 1 >= 0 && current.getY() - 1 >= 0) {
                                if (this.plain[current.getX() - 1][current.getY() - 1] instanceof Herbivore) {
                                    current.eat();
                                    this.plain[current.getX() - 1][current.getY() - 1] = new FreeSpace(current.getX() - 1, current.getY() - 1);
                                }
                            }
                    }
                }

                // Handles movement
                if(this.carnivoreArr.get(i).getAge() < 20 && this.carnivoreArr.get(i).getEnergy() > 0) {
                    Animal moved = this.carnivoreArr.get(i).move();

                    // Only move if it is a FreeSpace
                    while (this.plain[moved.getX()][moved.getY()] instanceof Plant ||
                            this.plain[moved.getX()][moved.getY()] instanceof Animal ||
                            this.plain[moved.getX()][moved.getY()] instanceof Obstacle) {
                        moved = this.carnivoreArr.get(i).move();
                    }

                    this.plain[moved.getX()][moved.getY()] = moved;
                    // Once it has moved replace its original coordinates with a FreeSpace
                    this.plain[this.carnivoreArr.get(i).getX()][this.carnivoreArr.get(i).getY()] =
                            new FreeSpace(this.carnivoreArr.get(i).getX(), this.carnivoreArr.get(i).getY());

                    // Every movement costs 1 year and 5 energy levels
                    moved.setAge(moved.getAge() + 1);
                    moved.setEnergy(moved.getEnergy() - 5);

                    this.carnivoreArr.set(i, moved);
                }
                else{ // If the Carnivore is at age 20 or their energy level is below 0, then they die and are replaced with FreeSpace
                    this.plain[this.carnivoreArr.get(i).getX()][this.carnivoreArr.get(i).getY()] =
                            new FreeSpace(this.carnivoreArr.get(i).getX(), this.carnivoreArr.get(i).getY());
                    // Remove the Carnivore from the ArrayList
                    this.carnivoreArr.remove(i);
                }



            }



            // Loop that handles the interaction of Herbivores with other objects
            for (int i = 0; i < this.herbivoreArr.size(); i++) {

                // Reproduction occurs if energy level is 75 or greater and if the get is greater than 10
                if (this.herbivoreArr.get(i).getEnergy() >= 75 && this.herbivoreArr.get(i).getAge() >= 10){
                    Animal baby = new Herbivore(this.herbivoreArr.get(i).getX(), this.herbivoreArr.get(i).getY());

                    // Only reproduce if it is in the coordinates of a FreeSpace
                    if (this.plain[baby.getX()][baby.getY()] instanceof FreeSpace) {
                        this.plain[baby.getX()][baby.getY()] = baby;
                        this.herbivoreArr.add(baby);
                    }
                }

                // When the Herbivore is hungry their energy level is below 75
                if (this.herbivoreArr.get(i).getEnergy() < 75){
                    Animal current = this.herbivoreArr.get(i);
                    int position = 0;

                    // Checks all 9 coordinates that are one unit away from the Herbivore for a Plant to eat
                    // Once the Plant is eaten, the Plant is replaced with a FreeSpace
                    switch(position) {
                        case 0:
                            if (current.getX() + 1 < 32 && current.getY() + 1 < 32){
                                if (this.plain[current.getX() + 1][current.getY() + 1] instanceof Plant) {
                                    current.eat();
                                    this.plain[current.getX() + 1][current.getY() + 1] = new FreeSpace(current.getX() + 1, current.getY() + 1);
                                }
                            }
                        case 1:
                            if (current.getX() + 1 < 32) {
                                if (this.plain[current.getX() + 1][current.getY()] instanceof Plant) {
                                    current.eat();
                                    this.plain[current.getX() + 1][current.getY()] = new FreeSpace(current.getX() + 1, current.getY());
                                }
                            }
                        case 2:
                            if (current.getX() + 1 < 32 && current.getY() - 1 >= 0) {
                                if (this.plain[current.getX() + 1][current.getY() - 1] instanceof Plant) {
                                    current.eat();
                                    this.plain[current.getX() + 1][current.getY() - 1] = new FreeSpace(current.getX() + 1, current.getY() - 1);
                                }
                            }
                        case 3:
                            if (current.getY() + 1 < 32) {
                                if (this.plain[current.getX()][current.getY() + 1] instanceof Plant) {
                                    current.eat();
                                    this.plain[current.getX()][current.getY() + 1] = new FreeSpace(current.getX(), current.getY() + 1);
                                }
                            }
                        case 4:
                            if (this.plain[current.getX()][current.getY()] instanceof Plant ) {
                                current.eat();
                                this.plain[current.getX()][current.getY()] = new FreeSpace(current.getX(), current.getY());
                            }
                        case 5:
                            if (current.getY() - 1 >= 0) {
                                if (this.plain[current.getX()][current.getY() - 1] instanceof Plant) {
                                    current.eat();
                                    this.plain[current.getX()][current.getY() - 1] = new FreeSpace(current.getX(), current.getY() - 1);
                                }
                            }
                        case 6:
                            if (current.getX() - 1 >= 0 && current.getY() + 1 < 32) {
                                if (this.plain[current.getX() - 1][current.getY() + 1] instanceof Plant) {
                                    current.eat();
                                    this.plain[current.getX()-1][current.getY() + 1] = new FreeSpace(current.getX() - 1, current.getY() + 1);
                                }
                            }
                        case 7:
                            if (current.getX() - 1 >= 0) {
                                if (this.plain[current.getX() - 1][current.getY()] instanceof Plant) {
                                    current.eat();
                                    this.plain[current.getX() - 1][current.getY()] = new FreeSpace(current.getX() - 1, current.getY());
                                }
                            }
                        case 8:
                            if (current.getX() - 1 >= 0 && current.getY() - 1 >= 0) {
                                if (this.plain[current.getX() - 1][current.getY() - 1] instanceof Plant) {
                                    current.eat();
                                    this.plain[current.getX() - 1][current.getY() - 1] = new FreeSpace(current.getX() - 1, current.getY() - 1);
                                }
                            }
                    }
                }

                // Handles movement
                if(this.herbivoreArr.get(i).getAge() < 20 && this.herbivoreArr.get(i).getEnergy() > 0) {
                    Animal moved = this.herbivoreArr.get(i).move();

                    // Only move if it is a FreeSpace
                    while (this.plain[moved.getX()][moved.getY()] instanceof Plant ||
                            this.plain[moved.getX()][moved.getY()] instanceof Animal ||
                            this.plain[moved.getX()][moved.getY()] instanceof Obstacle) {
                        moved = this.herbivoreArr.get(i).move();
                    }
                    this.plain[moved.getX()][moved.getY()] = moved;

                    // Once it has moved replace its original coordinates with a FreeSpace
                    this.plain[this.herbivoreArr.get(i).getX()][this.herbivoreArr.get(i).getY()] =
                            new FreeSpace(this.herbivoreArr.get(i).getX(), this.herbivoreArr.get(i).getY());

                    // Every movement costs 1 year and 5 energy levels
                    moved.setAge(moved.getAge() + 1);
                    moved.setEnergy(moved.getEnergy() - 5);

                    this.herbivoreArr.set(i, moved);
                }
                else{ // If the Herbivore is at age 20 or their energy level is below 0, then they die and are replaced with FreeSpace
                    this.plain[this.herbivoreArr.get(i).getX()][this.herbivoreArr.get(i).getY()] =
                            new FreeSpace(this.herbivoreArr.get(i).getX(), this.herbivoreArr.get(i).getY());

                    // Remove the Herbivore from the ArrayList
                    this.herbivoreArr.remove(i);
                }


            }

            // Handles Plants
            for (int i = 0; i < this.plantArr.size(); i++) {
                // Add the age by 1 after every iteration
                if(this.plantArr.get(i).getAge() < 20){
                    this.plantArr.get(i).setAge(this.plantArr.get(i).getAge() + 1);
                }
                else { // When the Plant is over age 20 then it will die
                    this.plain[this.plantArr.get(i).getX()][this.plantArr.get(i).getY()] =
                            new FreeSpace(this.plantArr.get(i).getX(), this.plantArr.get(i).getY());
                    this.plantArr.remove(i);
                }
            }

            // Randomly reproduce a new Plant
            this.plantArr.add(new Plant());

            // Only add the Plant into the plain if is a FreeSpace
            if (this.plain[this.plantArr.get(this.plantArr.size()-1).getX()][this.plantArr.get(this.plantArr.size()-1).getY()] instanceof FreeSpace)
                this.plain[this.plantArr.get(this.plantArr.size() - 1).getX()][this.plantArr.get(this.plantArr.size() - 1).getY()] = this.plantArr.get(this.plantArr.size() - 1);
            else
                this.plantArr.remove(this.plantArr.size()-1);

            textArea.setText(this.output);

            // Iterate
            try {
                Thread.sleep(2000);
            } catch(InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private void saveProgram() throws IOException {

        // Write to saveFile.data with ObjectOutputStream of a serialized object: instance
        FileOutputStream f_out = new FileOutputStream("saveFile.data");
        ObjectOutputStream obj_out = new ObjectOutputStream (f_out);
        obj_out.writeObject (instance);
    }

    private void loadProgram() throws IOException, ClassNotFoundException{

        // Read object from saveFile.data
        FileInputStream f_in = new FileInputStream("saveFile.data");
        ObjectInputStream obj_in = new ObjectInputStream (f_in);
        Object obj = obj_in.readObject();

        // Cast and assign the object back to the instance
        instance = (VirtualEcosystem) obj;

        textArea.setText(instance.output);
    }

    // GUI
    private void initComponents() {


        JPanel contentPane = new JPanel();
        GridBagLayout layout = new GridBagLayout();

        contentPane.setLayout(layout);
        GridBagConstraints constraints = new GridBagConstraints();

        constraints.fill = GridBagConstraints.NORTH;
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.insets = new Insets(1,2,1,2);


        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 100;
        constraints.gridheight = 100;
        textArea = new JTextArea(33, 32);
        textArea.setAlignmentX(CENTER_ALIGNMENT);
        textArea.setFont(new Font("monospaced", Font.PLAIN, 12));
        textArea.setEditable(false);

        layout.setConstraints(textArea, constraints);
        contentPane.add(textArea);

        constraints.gridx = 1;
        constraints.gridy = 101;
        constraints.gridwidth = 3;
        constraints.gridheight = 1;
        constraints.insets = new Insets(10,0,0,0);

        JButton startB = new JButton("Start");
        startB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(state.equals("Stop")) {

                    // Use a thread so that the program does not remain stuck inside the loop of runProgram()
                    // Gives access to stopB
                    Thread t = new Thread() {
                        @Override
                        public void run() {
                            textArea.setBackground(new Color(142, 251, 153));
                            instance.runProgram();
                        }
                    };
                    t.start();
                    state = "Start";
                }
            }
        });

        layout.setConstraints(startB, constraints);
        contentPane.add(startB);

        constraints.gridx = 7;
        constraints.gridy = 101;
        constraints.gridwidth = 3;
        constraints.gridheight = 1;

        JButton stopB = new JButton("Stop");
        stopB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(state.equals("Start")) {
                    textArea.setBackground(new Color(251, 82, 67));
                    instance.running = false;
                    state = "Stop";
                }
            }
        });

        layout.setConstraints(stopB, constraints);
        contentPane.add(stopB);

        constraints.gridx = 11;
        constraints.gridy = 101;
        constraints.gridwidth = 3;
        constraints.gridheight = 1;

        JButton saveB = new JButton("Save");
        saveB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (state.equals("Stop")) {
                    try {
                        saveProgram();
                        JOptionPane.showMessageDialog(null, "Successfully saved into saveFile.data");
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
                else
                    JOptionPane.showMessageDialog(null, "Stop the program first before saving!");
            }
        });

        layout.setConstraints(saveB, constraints);
        contentPane.add(saveB);

        constraints.gridx = 15;
        constraints.gridy = 101;
        constraints.gridwidth = 3;
        constraints.gridheight = 1;

        JButton loadB = new JButton("Load");
        loadB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(state.equals("Stop")) {
                    try {
                        loadProgram();
                        JOptionPane.showMessageDialog(null, "Successfully loaded from saveFile.data");
                        textArea.setBackground(new Color(255, 255, 255));

                    } catch (IOException e1) {
                        e1.printStackTrace();
                    } catch (ClassNotFoundException e2) {
                        e2.printStackTrace();
                    }
                }
                else
                    JOptionPane.showMessageDialog(null, "Stop the program first before loading!");
            }
        });

        layout.setConstraints(loadB, constraints);
        contentPane.add(loadB);

        constraints.gridx = 19;
        constraints.gridy = 101;
        constraints.gridwidth = 3;
        constraints.gridheight = 1;

        JButton newB = new JButton("New Life");
        newB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(state.equals("Stop")) {
                    textArea.setText("");
                    instance.populateWorld();
                    JOptionPane.showMessageDialog(null, "Success! Press Start to begin the new life");
                    textArea.setBackground(new Color(255, 255, 255));
                }
                else
                    JOptionPane.showMessageDialog(null, "Stop the program first before beginning a new one!");
            }
        });

        layout.setConstraints(newB, constraints);
        contentPane.add(newB);

        contentPane.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        add(contentPane);


        contentPane.setBackground(new Color(32,178,170));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        pack();
        setVisible(true);


    }

    public VirtualEcosystem(){
        initComponents();
    }

    public static void main(String[] args) {
        instance = new VirtualEcosystem();
        instance.populateWorld();
    }

}
