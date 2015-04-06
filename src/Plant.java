/**
 * @author      Christopher Yang <cyang001@citymail.cuny.edu>
 * @version     1.0
 * @since       2015-04-02
 */

public class Plant extends Plain {
    /** age of Plant */
    private int age;

    /**
     * Default constructor
     *
     * Implicitly calls Plain() and sets the symbol to '&'.
     *
     */
    public Plant(){
        age = 0;
        setSymbol('&');
    }

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
