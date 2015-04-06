/**
 * @author      Christopher Yang <cyang001@citymail.cuny.edu>
 * @version     1.0
 * @since       2015-04-02
 */

import java.util.ArrayList; // to use ArrayList

public abstract class VirtualEcosystem {
    /**
     * Main Function
     *
     * Handles initialization, movement, feeding, reproduction, and printing of the virtual ecosystem.
     *
     */
    public static void main(String[] args) {

        // Adds the objects in respective ArrayLists
        ArrayList<Animal> carnivoreArr = new ArrayList<Animal>();
        for (int i = 0; i < 20; i++)
            carnivoreArr.add(new Carnivore());

        ArrayList<Animal> herbivoreArr = new ArrayList<Animal>();
        for (int i = 0; i < 20; i++)
            herbivoreArr.add(new Herbivore());

        ArrayList<Obstacle> obstacleArr = new ArrayList<Obstacle>();
        for (int i = 0; i < 20; i++)
            obstacleArr.add(new Obstacle());

        ArrayList<Plant> plantArr = new ArrayList<Plant>();
        for (int i = 0; i < 20; i++)
            plantArr.add(new Plant());

        // Create a 2D array that stores the super class
        Plain[][] plain = new Plain[32][32];

        // Initializes the 2D array with object FreeSoace
        for (int row = 0; row < 32; row++) {
            for (int column = 0; column < 32; column++)
                plain[row][column] = new FreeSpace(row, column);
        }

        // Go through the ArrayLists and add the objects into the 2D array only if the pointer contains
        // an instance of FreeSpace
        for (Animal c: carnivoreArr) {
            if (plain[c.getX()][c.getY()] instanceof FreeSpace)
                plain[c.getX()][c.getY()] = c;
        }
        for (Animal h: herbivoreArr) {
            if (plain[h.getX()][h.getY()] instanceof FreeSpace)
                plain[h.getX()][h.getY()] = h;

        }
        for (Obstacle o: obstacleArr) {
            if (plain[o.getX()][o.getY()] instanceof FreeSpace)
                plain[o.getX()][o.getY()] = o;
        }
        for (Plant p: plantArr){
            if (plain[p.getX()][p.getY()] instanceof FreeSpace)
                plain[p.getX()][p.getY()] = p;
        }

        // Main loop for iteration
        int k = 1;
        while(k <= 30) {

            System.out.println("\nIteration: " + k);

            // Print the characters of the plain
            for (int i = 0; i < 32; i++) {
                for (int j = 0; j < 32; j++) {
                    plain[i][j].printSymbol();
                }
                System.out.println();
            }

            // Loop that handles the interaction of Carnivores with other objects
            for (int i = 0; i < carnivoreArr.size(); i++) {

                // Reproduction occurs if energy level is 75 or greater and if the get is greater than 10
                if (carnivoreArr.get(i).getEnergy() >= 75 && carnivoreArr.get(i).getAge() >= 10){
                    Animal baby = new Carnivore(carnivoreArr.get(i).getX(), carnivoreArr.get(i).getY());

                    // Only reproduce if it is in the coordinates of a FreeSpace
                    if (plain[baby.getX()][baby.getY()] instanceof FreeSpace) {
                        plain[baby.getX()][baby.getY()] = baby;
                        carnivoreArr.add(baby);
                    }
                }

                // When the Carnivore is hungry their energy level is below 75
                if (carnivoreArr.get(i).getEnergy() < 75){
                    Animal current = carnivoreArr.get(i);
                    int position = 0;

                    // Checks all 9 coordinates that are one unit away from the Carnivore for a Herbivore to eat
                    // Once the Herbivore is eaten, the Herbivore is replaced with a FreeSpace
                    switch(position) {
                        case 0:
                            if (current.getX() + 1 < 32 && current.getY() + 1 < 32){
                                if (plain[current.getX() + 1][current.getY() + 1] instanceof Herbivore) {
                                    current.eat();
                                    plain[current.getX() + 1][current.getY() + 1] = new FreeSpace(current.getX() + 1, current.getY() + 1);
                                }
                            }
                        case 1:
                            if (current.getX() + 1 < 32) {
                                if (plain[current.getX() + 1][current.getY()] instanceof Herbivore) {
                                    current.eat();
                                    plain[current.getX() + 1][current.getY()] = new FreeSpace(current.getX() + 1, current.getY());
                                }
                            }
                        case 2:
                            if (current.getX() + 1 < 32 && current.getY() - 1 >= 0) {
                                if (plain[current.getX() + 1][current.getY() - 1] instanceof Herbivore) {
                                    current.eat();
                                    plain[current.getX() + 1][current.getY() - 1] = new FreeSpace(current.getX() + 1, current.getY() - 1);
                                }
                            }
                        case 3:
                            if (current.getY() + 1 < 32) {
                                if (plain[current.getX()][current.getY() + 1] instanceof Herbivore) {
                                    current.eat();
                                    plain[current.getX()][current.getY() + 1] = new FreeSpace(current.getX(), current.getY() + 1);
                                }
                            }
                        case 4:
                            if (plain[current.getX()][current.getY()] instanceof Herbivore ) {
                                current.eat();
                                plain[current.getX()][current.getY()] = new FreeSpace(current.getX(), current.getY());
                            }
                        case 5:
                            if (current.getY() - 1 >= 0) {
                                if (plain[current.getX()][current.getY() - 1] instanceof Herbivore) {
                                    current.eat();
                                    plain[current.getX()][current.getY() - 1] = new FreeSpace(current.getX(), current.getY() - 1);
                                }
                            }
                        case 6:
                            if (current.getX() - 1 >= 0 && current.getY() + 1 < 32) {
                                if (plain[current.getX() - 1][current.getY() + 1] instanceof Herbivore) {
                                    current.eat();
                                    plain[current.getX()-1][current.getY() + 1] = new FreeSpace(current.getX() - 1, current.getY() + 1);
                                }
                            }
                        case 7:
                            if (current.getX() - 1 >= 0) {
                                if (plain[current.getX() - 1][current.getY()] instanceof Herbivore) {
                                    current.eat();
                                    plain[current.getX() - 1][current.getY()] = new FreeSpace(current.getX() - 1, current.getY());
                                }
                            }
                        case 8:
                            if (current.getX() - 1 >= 0 && current.getY() - 1 >= 0) {
                                if (plain[current.getX() - 1][current.getY() - 1] instanceof Herbivore) {
                                    current.eat();
                                    plain[current.getX() - 1][current.getY() - 1] = new FreeSpace(current.getX() - 1, current.getY() - 1);
                                }
                            }
                    }
                }

                // Handles movement
                if(carnivoreArr.get(i).getAge() < 20 && carnivoreArr.get(i).getEnergy() > 0) {
                    Animal moved = carnivoreArr.get(i).move();

                    // Only move if it is a FreeSpace
                    while (plain[moved.getX()][moved.getY()] instanceof Plant ||
                            plain[moved.getX()][moved.getY()] instanceof Animal ||
                            plain[moved.getX()][moved.getY()] instanceof Obstacle) {
                        moved = carnivoreArr.get(i).move();
                    }

                    plain[moved.getX()][moved.getY()] = moved;
                    // Once it has moved replace its original coordinates with a FreeSpace
                    plain[carnivoreArr.get(i).getX()][carnivoreArr.get(i).getY()] =
                            new FreeSpace(carnivoreArr.get(i).getX(), carnivoreArr.get(i).getY());

                    // Every movement costs 1 year and 5 energy levels
                    moved.setAge(moved.getAge() + 1);
                    moved.setEnergy(moved.getEnergy() - 5);

                    carnivoreArr.set(i, moved);
                }
                else{ // If the Carnivore is at age 20 or their energy level is below 0, then they die and are replaced with FreeSpace
                    plain[carnivoreArr.get(i).getX()][carnivoreArr.get(i).getY()] =
                            new FreeSpace(carnivoreArr.get(i).getX(), carnivoreArr.get(i).getY());
                    // Remove the Carnivore from the ArrayList
                    carnivoreArr.remove(i);
                }



            }



            // Loop that handles the interaction of Herbivores with other objects
            for (int i = 0; i < herbivoreArr.size(); i++) {

                // Reproduction occurs if energy level is 75 or greater and if the get is greater than 10
                if (herbivoreArr.get(i).getEnergy() >= 75 && herbivoreArr.get(i).getAge() >= 10){
                    Animal baby = new Herbivore(herbivoreArr.get(i).getX(), herbivoreArr.get(i).getY());

                    // Only reproduce if it is in the coordinates of a FreeSpace
                    if (plain[baby.getX()][baby.getY()] instanceof FreeSpace) {
                        plain[baby.getX()][baby.getY()] = baby;
                        herbivoreArr.add(baby);
                    }
                }

                // When the Herbivore is hungry their energy level is below 75
                if (herbivoreArr.get(i).getEnergy() < 75){
                    Animal current = herbivoreArr.get(i);
                    int position = 0;

                    // Checks all 9 coordinates that are one unit away from the Herbivore for a Plant to eat
                    // Once the Plant is eaten, the Plant is replaced with a FreeSpace
                    switch(position) {
                        case 0:
                            if (current.getX() + 1 < 32 && current.getY() + 1 < 32){
                                if (plain[current.getX() + 1][current.getY() + 1] instanceof Plant) {
                                    current.eat();
                                    plain[current.getX() + 1][current.getY() + 1] = new FreeSpace(current.getX() + 1, current.getY() + 1);
                                }
                            }
                        case 1:
                            if (current.getX() + 1 < 32) {
                                if (plain[current.getX() + 1][current.getY()] instanceof Plant) {
                                    current.eat();
                                    plain[current.getX() + 1][current.getY()] = new FreeSpace(current.getX() + 1, current.getY());
                                }
                            }
                        case 2:
                            if (current.getX() + 1 < 32 && current.getY() - 1 >= 0) {
                                if (plain[current.getX() + 1][current.getY() - 1] instanceof Plant) {
                                    current.eat();
                                    plain[current.getX() + 1][current.getY() - 1] = new FreeSpace(current.getX() + 1, current.getY() - 1);
                                }
                            }
                        case 3:
                            if (current.getY() + 1 < 32) {
                                if (plain[current.getX()][current.getY() + 1] instanceof Plant) {
                                    current.eat();
                                    plain[current.getX()][current.getY() + 1] = new FreeSpace(current.getX(), current.getY() + 1);
                                }
                            }
                        case 4:
                            if (plain[current.getX()][current.getY()] instanceof Plant ) {
                                current.eat();
                                plain[current.getX()][current.getY()] = new FreeSpace(current.getX(), current.getY());
                            }
                        case 5:
                            if (current.getY() - 1 >= 0) {
                                if (plain[current.getX()][current.getY() - 1] instanceof Plant) {
                                    current.eat();
                                    plain[current.getX()][current.getY() - 1] = new FreeSpace(current.getX(), current.getY() - 1);
                                }
                            }
                        case 6:
                            if (current.getX() - 1 >= 0 && current.getY() + 1 < 32) {
                                if (plain[current.getX() - 1][current.getY() + 1] instanceof Plant) {
                                    current.eat();
                                    plain[current.getX()-1][current.getY() + 1] = new FreeSpace(current.getX() - 1, current.getY() + 1);
                                }
                            }
                        case 7:
                            if (current.getX() - 1 >= 0) {
                                if (plain[current.getX() - 1][current.getY()] instanceof Plant) {
                                    current.eat();
                                    plain[current.getX() - 1][current.getY()] = new FreeSpace(current.getX() - 1, current.getY());
                                }
                            }
                        case 8:
                            if (current.getX() - 1 >= 0 && current.getY() - 1 >= 0) {
                                if (plain[current.getX() - 1][current.getY() - 1] instanceof Plant) {
                                    current.eat();
                                    plain[current.getX() - 1][current.getY() - 1] = new FreeSpace(current.getX() - 1, current.getY() - 1);
                                }
                            }
                    }
                }

                // Handles movement
                if(herbivoreArr.get(i).getAge() < 20 && herbivoreArr.get(i).getEnergy() > 0) {
                    Animal moved = herbivoreArr.get(i).move();

                    // Only move if it is a FreeSpace
                    while (plain[moved.getX()][moved.getY()] instanceof Plant ||
                            plain[moved.getX()][moved.getY()] instanceof Animal ||
                            plain[moved.getX()][moved.getY()] instanceof Obstacle) {
                        moved = herbivoreArr.get(i).move();
                    }
                    plain[moved.getX()][moved.getY()] = moved;

                    // Once it has moved replace its original coordinates with a FreeSpace
                    plain[herbivoreArr.get(i).getX()][herbivoreArr.get(i).getY()] =
                            new FreeSpace(herbivoreArr.get(i).getX(), herbivoreArr.get(i).getY());

                    // Every movement costs 1 year and 5 energy levels
                    moved.setAge(moved.getAge() + 1);
                    moved.setEnergy(moved.getEnergy() - 5);

                    herbivoreArr.set(i, moved);
                }
                else{ // If the Herbivore is at age 20 or their energy level is below 0, then they die and are replaced with FreeSpace
                    plain[herbivoreArr.get(i).getX()][herbivoreArr.get(i).getY()] =
                            new FreeSpace(herbivoreArr.get(i).getX(), herbivoreArr.get(i).getY());

                    // Remove the Herbivore from the ArrayList
                    herbivoreArr.remove(i);
                }


            }

            // Handles Plants
            for (int i = 0; i < plantArr.size(); i++) {
                // Add the age by 1 after every iteration
                if(plantArr.get(i).getAge() < 20){
                    plantArr.get(i).setAge(plantArr.get(i).getAge() + 1);
                }
                else { // When the Plant is over age 20 then it will die
                    plain[plantArr.get(i).getX()][plantArr.get(i).getY()] =
                            new FreeSpace(plantArr.get(i).getX(), plantArr.get(i).getY());
                    plantArr.remove(i);
                }
            }

            // Randomly reproduce a new Plant
            plantArr.add(new Plant());

            // Only add the Plant into the plain if is a FreeSpace
            if (plain[plantArr.get(plantArr.size()-1).getX()][plantArr.get(plantArr.size()-1).getY()] instanceof FreeSpace)
                plain[plantArr.get(plantArr.size() - 1).getX()][plantArr.get(plantArr.size() - 1).getY()] = plantArr.get(plantArr.size() - 1);
            else
                plantArr.remove(plantArr.size()-1);


            // Iterate
            ++k;
        }
    }


}
