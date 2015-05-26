/**
 * @author      Christopher Yang <cyang001@citymail.cuny.edu>
 * @version     1.8
 * @since       2015-04-02
 */

public class Carnivore extends Animal {
    /**
     * Default constructor
     *
     * Implicitly calls Animal() and sets the symbol to 'C'.
     *
     */
    public Carnivore(){
        setSymbol('C');
    }


    /**
     * Default constructor with params
     *
     * explicitly calls Animal(x,y) and sets the symbol to 'C'.
     * @param x coordinate of x
     * @param y coordinate of y
     */
    public Carnivore(int x, int y){
        super(x,y);
        setSymbol('C');
    }

}
