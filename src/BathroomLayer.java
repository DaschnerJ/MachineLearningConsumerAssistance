import java.util.ArrayList;

public class BathroomLayer extends BaseLayer {

    public BathroomLayer()
    {
        super("bathroom");
        input = generateLayer(3);
        ArrayList<Neuron> neurons = input.getNeurons();
        neurons.get(0).setWeight(0.1);
        neurons.get(1).setWeight(.7);
        neurons.get(2).setWeight(.3);


    }

    @Override
    public Layer generateLayer(int count) {
        ArrayList<Neuron> neurons = new ArrayList<>();
        for(int i = 0; i < 3; i++)
        {
            neurons.add(new Neuron(i + "", .1, .5));
        }

        Layer l = new Layer();
        neurons.forEach(x -> l.addNeuron(x));
        return l;
    }

    /**
     * Provides an estimated bathroom count based on what the machine has learned.
     * @return Returns an integer of the ball park number where it thinks the user wants.
     */
    public int getSuggestedBathroomCount()
    {
        ArrayList<Double> list = new ArrayList<>();
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
        for(int i = 0; i < 3; i++)
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
