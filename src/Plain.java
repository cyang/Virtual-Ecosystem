import java.io.Serializable;

/**
 * @author      Christopher Yang <cyang001@citymail.cuny.edu>
 * @version     1.8
 * @since       2015-04-02
 */

public abstract class Plain implements Serializable {
    /** symbol holds the character */
    private char symbol;

    /** x coordinate */
    private int x;
    /** y coordinate */
    private int y;

    /**
     * Default constructor
     *
     * Constructs a random coordinate for x and y.
     *
     */
    public Plain(){
        setX((int)(Math.random()*32));
        setY((int)(Math.random()*32));
    }

    /**
     * Gets x
     *
     * @return x
     */
    public int getX() {
        return x;
    }

    /**
     * Sets x
     *
     * @param x is the new x coordinate
     */
    public void setX(int x) {
        // x cannot exceed 31
        if(x >= 31)
            this.x = 31;
            // x cannot go below 0
        else if (x <= 0)
            this.x = 0;
        else
            this.x = x;
    }

    /**
     * Gets y
     *
     * @return y
     */
    public int getY() {
        return y;
    }

    /**
     * Sets y
     *
     * @param y is the new x coordinate
     */
    public void setY(int y) {
        // x cannot exceed 31
        if(y >= 31)
            this.y = 31;
            // x cannot go below 0
        else if (y <= 0)
            this.y = 0;
        else
            this.y = y;
    }

    /**
     * Gets symbol
     *
     * @return symbol
     */
    public char getSymbol() {
        return symbol;
    }

    /**
     * Sets symbol
     *
     * @param symbol is the new x coordinate
     */
    public void setSymbol(char symbol) {this.symbol = symbol;}


    /**
     * Prints symbol
     */
    public void printSymbol(){
        System.out.print(this.symbol);
    }
}
