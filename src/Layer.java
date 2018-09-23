import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Layer {

    String id;

    ArrayList<Neuron> neurons;

    Random r;

    private boolean maxType = true;

    /**
     * A layer is a basic set of nodes.
     */
    public Layer()
    {
        r = new Random();
        this.neurons = new ArrayList<>();
    }

    /**
     * This is here incase you want to inverse the results. When the layer is max type, the layer will pick
     * the node with the highest density in the decision. If it is mintype (not maxtype) then the layer will
     * pick the node with the least amount of density in the decision.
     * @param type True for max type, false for min type.
     */
    public void setMaxType(boolean type)
    {
        this.maxType = type;
    }

    /**
     * Add a neuron the the layer. (Ensures it is unique).
     * @param n The neuron to be added to the layer.
     */
    public void addNeuron(Neuron n)
    {
        if(!neurons.contains(n))
        neurons.add(n);
    }

    /**
     * Removes a neuron from the layer.
     * @param n The neuron to be removed.
     */
    public void removeNeuron(Neuron n)
    {
        neurons.remove(n);
    }

    /**
     * Gets a neuron by it's ID.
     * @param id The ID of the neuron that is needed.
     * @return Returns a neuron with the ID or null if it doesn't exist.
     */
    public Neuron getNeuron(String id)
    {
        for(Neuron n : neurons)
        {
            if(n.getID().equals(id))
                return n;
        }

        return null;
    }

    /**
     * Gets a neuron a partial match of ID.
     * @param partial The partial ID of the neuron that is needed.
     * @return Returns a neuron with the ID or null if it doesn't exist.
     */
    public ArrayList<Neuron> getPartialNeuron(String partial)
    {
        ArrayList<Neuron> found = new ArrayList<>();
        neurons.forEach(x -> {
            if(x.getID().contains(partial))
            {
                found.add(x);
            }
        });
        return found;
    }

    /**
     * Puts data through each neuron. THe neuron then makes a decision which is stored in it's result.
     * @param data The set for the neurons.
     */
    public void activateNeurons(ArrayList<Double> data)
    {
        for(Neuron n : neurons)
        {
            for (Double d : data) {
                n.activation(d);
            }
        }
    }

    /**
     * Gets the list of choices the neurons have made after a decision.
     * @return The array od doubles that represent the weight choices and probability.
     */
    public ArrayList<Double> getChoices()
    {
        ArrayList<Double> list = new ArrayList<>();
        neurons.forEach(x -> list.add(x.getChoice()));
        return list;
    }

    /**
     * Gets the Neurons within the layer.
     * @return The list of Neurons in the layer.
     */
    public ArrayList<Neuron> getNeurons()
    {
        return neurons;
    }

    /**
     * Gets the Neuron with the highest density in the layer.
     * @return Returns the Neuron with the highest density.
     */
    public Neuron getMax()
    {
        return Collections.max(neurons, Comparator.comparing(s -> s.getChoice()));
    }

    /**
     * Gets the Neuron with the lower density in the layer.
     * @return Returns the Neuron with the lowest Density.
     */
    public Neuron getMin()
    {
        return Collections.min(neurons, Comparator.comparing(s -> s.getChoice()));
    }

    /**
     * Determines if a max or min Neuron is requied to be returned.
     * @return
     */
    public Neuron getType()
    {
        if(maxType)
        {
            return getMax();
        }
        else
        {
            return getMin();
        }
    }


    /**
     * A way to disrupt the Neuron's choice and provide some randomness in case it is needed.
     * Also this would mainly be applied during the hidden layer.
     * @param min THe min possible disruption.
     * @param max The max possible disruption.
     */
    public void disrupt(double min, double max)
    {
        for(Neuron n : neurons)
        {
            Double weight = n.getWeight();
                n.setWeight((weight + (Math.random() * max) - min));
        }

    }

}
