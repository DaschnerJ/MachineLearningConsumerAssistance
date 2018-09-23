import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BedroomLayer extends BaseLayer {

    public BedroomLayer()
    {
        super("bedroom");
        input = generateLayer(5);
        ArrayList<Neuron> neurons = input.getNeurons();
        neurons.get(0).setWeight(0.1);
        neurons.get(1).setWeight(.2);
        neurons.get(2).setWeight(.7);
        neurons.get(3).setWeight(.5);
        neurons.get(4).setWeight(.2);


    }

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
     * Provides the predicted amount of bedrooms the machine thinks the user wants.
     * @return Returns an integer of the amount of bedrooms.
     */
    public int getSuggestedBedroomCount()
    {
        ArrayList<Double> list = new ArrayList<>();
        list.add(1.0);
        list.add(1.0);
        list.add(1.0);
        list.add(1.0);
        list.add(1.0);
        input(list);
        Neuron n = answer();
        return Integer.valueOf(n.getID())+1;
    }

    /**
     * A method that adjusts the neurons based on the choice the user made so it can better guess it next time.
     * @param c The number choice the user made.
     */
    public void choice(int c)
    {
        ArrayList<Double> list = new ArrayList<>();
        for(int i = 0; i < 5; i++)
        {
            if(i != c-1)
            {
                list.add(0.0);
            }
            else
            {
                list.add(1.0);
            }
        }
        adjust(list);
    }


}
