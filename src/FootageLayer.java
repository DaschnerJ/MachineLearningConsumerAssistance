import java.util.ArrayList;

public class FootageLayer extends BaseLayer {

    public FootageLayer()
    {
        super("footage");
        input = generateLayer(8);
        ArrayList<Neuron> neurons = input.getNeurons();
        //System.out.println(input.getNeurons().size());
        neurons.get(0).setWeight(0.1);
        neurons.get(1).setWeight(.1);
        neurons.get(2).setWeight(.7);
        neurons.get(3).setWeight(0.7);
        neurons.get(4).setWeight(.7);
        neurons.get(5).setWeight(.6);
        neurons.get(6).setWeight(.4);
        neurons.get(7).setWeight(.2);


    }

    @Override
    public Layer generateLayer(int count) {
        ArrayList<Neuron> neurons = new ArrayList<>();
        for(int i = 0; i < 8; i++)
        {
            neurons.add(new Neuron(i + "", .1, .5));
        }

        Layer l = new Layer();
        neurons.forEach(x -> l.addNeuron(x));
        return l;
    }

    /**
     * Provides an estimated square footage but note this number represents +-200 of what ever is given
     * So should be considered more a range than a specific value.
     * @return Returns a predicted footage number that the machine thinks the consumer wants.
     */
    public int getSuggestedFootageCount()
    {
        ArrayList<Double> list = new ArrayList<>();
        list.add(1.0);
        list.add(1.0);
        list.add(1.0);
        list.add(1.0);
        list.add(1.0);
        list.add(1.0);
        list.add(1.0);
        list.add(1.0);
        input(list);
        Neuron n = answer();
        return (Integer.valueOf(n.getID())+1*200)+400;
    }

    /**
     * A method that adjusts the neurons based on the choice the user made so it can better guess it next time.
     * @param c The number choice the user made.
     */
    public void choice(int c)
    {
        ArrayList<Double> list = new ArrayList<>();
        for(int i = 0; i < 8; i++)
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
