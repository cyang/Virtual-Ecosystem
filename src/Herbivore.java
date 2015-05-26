/**
 * @author      Christopher Yang <cyang001@citymail.cuny.edu>
 * @version     1.8
 * @since       2015-04-02
 */

public class Herbivore extends Animal {
    /**
     * Default constructor
     *
     * Implicitly calls Animal() and sets the symbol to 'H'.
     *
     */
    public Herbivore() {
        setSymbol('H');
    }

    /**
     * Default constructor with params
     *
     * explicitly calls Animal(x,y) and sets the symbol to 'C'.
     * @param x coordinate of x
     * @param y coordinate of y
     */
    public Herbivore(int x, int y){
        super(x,y);
        setSymbol('H');
    }

}
