import java.util.ArrayList;

public interface _DefinedLayer {

    Layer input = null;

    Layer generateLayer(int count);

    void input(ArrayList<Double> inputs);

    void inputLists(ArrayList<Double> inputs);

    ArrayList<Double> results();

    void adjust(ArrayList<Double> results);



}
