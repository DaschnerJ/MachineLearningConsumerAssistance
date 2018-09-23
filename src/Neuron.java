import java.util.ArrayList;

public class Neuron {

    //The identifier of this neuron.
    private String id;

    //The current density in decisions that this Neuron makes.
    private double weight;

    //The rate of change that this Neuron is allowed to adjust itself at.
    private double changeRate;

    //The choice the Neuron had made from the previous inputs.
    private double choice;

    public Neuron(String id, double changeRate, Double weight)
    {
        this.id = id;
        this.weight = weight;
        this.changeRate = changeRate;
        choice = weight;
    }

    /**
     * Get the previous choice the Neuron had made.
     * @return The previous choice the Neuron had made.
     */
    public double getChoice()
    {
        return choice;
    }

    /**
     * Gets the rate of change the Neuron is allowed to change at.
     * @return The rate of change of the Neuron.
     */
    public double getChangeRate()
    {
        return changeRate;
    }

    /**
     * Gets the weight and density of this Neuron in decision making.
     * @return The choice weight of this Neuron.
     */
    public double getWeight()
    {
        return weight;
    }

    /**
     * Gets the Identifier of this Neuron.
     * @return The String ID that Identifies this Neuron.
     */
    public String getID()
    {
        return id;
    }

    /**
     * Allows changing the Neuron's ID if needed.
     * @param id The Neuron's new assigned ID.
     */
    public void setID(String id)
    {
        this.id = id;
    }

    /**
     * Allows changing the rate of learning for the Neuron.
     * @param changeRate THe rate of change that the Neuron is allowed to learn at.
     */
    public void setChangeRate(double changeRate)
    {
        this.changeRate = changeRate;
    }

    /**
     * Allows setting the weight of this Neuron for adjustments and other needed functions such as training.
     * @param weight The new weight the Neuron will be set to.
     */
    public void setWeight(double weight){
        this.weight = weight;
    }


    /**
     * The activation function of this Neuron. The simple basic formula the Neuron uses to decide.
     * @param input The input the Neuron receives to make a decision on.
     * @return Returns the decision the Neuron had made. (Mostly for rounding activation functions.)
     */
    public double activation(double input)
    {
                choice = input*weight;
                return choice;
    }

    /**
     * Same thing as an activation function but this function allows rounding the number to the nearest hole number if needed.
     * @param input THe input the Neuron receives to make a decision on.
     * @return Returns the decision the Neuron had made.
     */
    public double roundActivation(Double input)
    {
        return Math.round(activation(input));
    }

    /**
     * The calculate error from what the Neuron had decided to what was the actual result.
     * @param result The actual result.
     * @return The difference between the actual and predicted which shows the range of error.
     */
    public double error(double result)
    {
        return result - choice;
    }

    /**
     * Slightly adjusts the Neuron based on how much error calculated.
     * @param result The result is the actual result that is given.
     */
    public void adjust(double result)
    {
        double error = error(result);
//        System.out.println("Error: " + error);
//        System.out.println("Change: " +changeRate);
//        System.out.println("Change: " + error*changeRate);
        weight = weight + error*changeRate;
    }


}
