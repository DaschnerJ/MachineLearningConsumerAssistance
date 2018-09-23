public class LayerHandler {

    private BedroomLayer bl;
    private BathroomLayer bt;
    private FootageLayer fl;

    /**
     * A simple handler to handle the various main and defined Layers. Allows for some more
     * simple and basic ways of accessing some important data and updating the data.
     */
    public LayerHandler()
    {
        bl = new BedroomLayer();
        bt = new BathroomLayer();
        fl = new FootageLayer();
    }

    public int getBedrooms()
    {
        return bl.getSuggestedBedroomCount();
    }

    public int getBathrooms()
    {
        System.out.println("Suggested Bathroom Count: " + bt.getSuggestedBathroomCount());
        return bt.getSuggestedBathroomCount();
    }

    public int getFootage()
    {
        return fl.getSuggestedFootageCount();
    }

    public void choiceBedrooms(int c)
    {
        bl.choice(c);
    }

    public void choiceBathrooms(int c)
    {
        bt.choice(c);
    }

    public void choiceFootage(int c)
    {
        fl.choice(c);
    }

}
