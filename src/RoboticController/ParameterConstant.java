/**
 * 
 */
package RoboticController;

/**
 * @author yogesh
 *
 * Configuration file for GA parameters
 */
public final class ParameterConstant {

    private ParameterConstant() {
            // restrict instantiation
    }

    public static final int POPULATIONSIZE = 200;
    public static final double MUTATIONSIZE = 0.01;
    public static final double CROSSOVERRATE = 0.9;
    public static final int ELITSMCOUNT = 2;
    public static final int TOURNAMENTSIZE = 10;
    public static final int MAXGENERATION = 500;
}