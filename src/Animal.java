/**
 * @author      Christopher Yang <cyang001@citymail.cuny.edu>
 * @version     1.8
 * @since       2015-04-02
 */

public class Animal extends Plain {
    /** age of animal */
    private int age;
    /** energy level of animal */
    private int energy;


    /**
     * Default constructor
     *
     * Initializes age and energy.
     *
     */
    public Animal(){
        age = 0;
        energy = 100;
    }

    /**
     * Default constructor used when reproducing an an Animal one unit away from the position at (x,y).
     *
     * @param x refers to the x coordinate.
     * @param y refers to the y coordinate.
     */
    public Animal(int x, int y){
        age = 0;
        energy = 100;
        int rand = (int)(Math.random()*8);

        // Decides a position based on random
        switch (rand){
            case 0:
                this.setX(x+1);
                this.setY(y+1);
                break;
            case 1:
                this.setX(x+1);
                this.setY(y);
                break;
            case 2:
                this.setX(x+1);
                this.setY(y-1);
                break;
            case 3:
                this.setX(x);
                this.setY(y+1);
                break;
            case 4:
                this.setX(x);
                this.setY(y-1);
                break;
            case 5:
                this.setX(x-1);
                this.setY(y+1);
                break;
            case 6:
                this.setX(x-1);
                this.setY(y);
                break;
            case 7:
                this.setX(x-1);
                this.setY(y-1);
                break;
        }
    }

    /**
     * Moves the animal.
     */
    public Animal move(){
        Animal moved = new Animal();
        int rand = (int)(Math.random()*8);

        // Decides a position based on random
        switch (rand){
            case 0:
                moved.setX(this.getX()+1);
                moved.setY(this.getY()+1);
                break;
            case 1:
                moved.setX(this.getX()+1);
                moved.setY(this.getY());
                break;
            case 2:
                moved.setX(this.getX()+1);
                moved.setY(this.getY()-1);
                break;
            case 3:
                moved.setX(this.getX());
                moved.setY(this.getY()+1);
                break;
            case 4:
                moved.setX(this.getX());
                moved.setY(this.getY()-1);
                break;
            case 5:
                moved.setX(this.getX()-1);
                moved.setY(this.getY()+1);
                break;
            case 6:
                moved.setX(this.getX()-1);
                moved.setY(this.getY());
                break;
            case 7:
                moved.setX(this.getX()-1);
                moved.setY(this.getY()-1);
                break;
        }


        // After moving it should contain the same properties
        moved.setSymbol(this.getSymbol());
        moved.setAge(this.getAge());
        moved.setEnergy(this.getEnergy());
        return moved;
    }


    /**
     * Eating gains energy back.
     */
    public void eat(){
        this.setEnergy(100);
    }

    /**
     * Gets energy
     *
     * @return energy
     */
    public int getEnergy() {
        return energy;
    }

    /**
     * Sets energy
     *
     * @param energy is the energy level for the animal
     */
    public void setEnergy(int energy){this.energy = energy;}

    /**
     * Gets age
     *
     * @return age
     */
    public int getAge() {
        return age;
    }

    /**
     * Sets age
     *
     * @param age is the age of the animal
     */
    public void setAge(int age) {
        this.age = age;
    }
}
