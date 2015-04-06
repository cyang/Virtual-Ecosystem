/**
 * @author      Christopher Yang <cyang001@citymail.cuny.edu>
 * @version     1.0
 * @since       2015-04-02
 */

public class FreeSpace extends Plain{

    /**
     * Default constructor with params
     *
     *
     * Implicitly calls Plain() and sets coordinates (x,y) and the symbol to '.'
     * @param x coordinate of x
     * @param y coordinate of y
     */
    public FreeSpace(int x, int y){
        setSymbol('.');
        setX(x);
        setY(y);
    }
}
