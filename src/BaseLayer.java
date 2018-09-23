import java.util.ArrayList;

public class BaseLayer implements _DefinedLayer{

    Layer input;
    String type;

    /**
     * Base is a basic layer which other layers will based off of. Serves as a generic Layer.
     * @param type Type is a way of identifying the layers which can provide sort of an ID for each.
     */
    public BaseLayer(String type)
    {
        this.type = type;
        //input = generateLayer(5);
        //output = generateLayer(1);
    }

    /**
     * Generates a generic layer with x many nodes in that layer.
     * @param count The count can be any reasonable number of nodes.
     * @return Returns a layer with generated nodes.
     */
    @Override
    public Layer generateLayer(int count) {
        ArrayList<Neuron> neurons = new ArrayList<>();
        for(int i = 0; i < 5; i++)
        {
            neurons.add(new Neuron(i + "", .1, .5));
        }

        Layer l = new Layer();
        neurons.forEach(x -> l.addNeuron(x));
        return l;
    }

    /**
     * Way of inputting the inputs into the layer for calculation.
     * @param inputs The inputs that must go to each node.
     */
    @Override
    public void input(ArrayList<Double> inputs) {
        inputLists(inputs);
    }

    /**
     * Another method for inputting an array of inputs. This one is slightly different and was mainly in testing.
     * A gentleman said that he found it annoying that Netflix never offered him anything "New" out of his
     * general liking. partly this can be solved by adding a hidden layer (aka the disrupt) which randomizes
     * the choice a bit more to hopefully achieve variance.
     * @param inputs The inputs that need to go to each node.
     */
    @Override
    public void inputLists(ArrayList<Double> inputs) {

        ArrayList<Neuron> neurons = this.input.getNeurons();
        for(int i = 0; i < neurons.size(); i++)
        {
            neurons.get(i).activation(inputs.get(i));
        }
        //input.disrupt(0.0, 0.01);

    }

    /**
     * Gives the chosen results of each nodes from the previous inputs.
     * @return Gives the choices mode by each node.
     */
    @Override
    public ArrayList<Double> results() {

        ArrayList<Neuron> neurons = this.input.getNeurons();
        ArrayList<Double> lists = new ArrayList<>();
        neurons.forEach(x -> lists.add(x.getChoice()));
        return lists;
    }

    /**
     * Adjust the weights of each node based on the results given back.
     * @param results The results chosen, 0 for not, 1 for chosen.
     */
    @Override
    public void adjust(ArrayList<Double> results) {
        ArrayList<Neuron> neurons = this.input.getNeurons();
        for(int i = 0; i < neurons.size(); i++)
        {
            neurons.get(i).adjust(results.get(i));
        }
    }

    /**
     * A simple method to get the neuron with the most weight and resulting decision. This is mainly a simple way
     * to figure out which Neuron had the most "choice" in the matter. Most likely the desired choice.
     * @return Returns the most decisive Neuron.
     */
    public Neuron answer()
    {
        return input.getMax();
    }

    /**
     * Gets the Layer's ID to define it's type.
     * @return Returns the Layer's ID, aka type.
     */
    public String getLayerType()
    {
        return type;
    }
}
