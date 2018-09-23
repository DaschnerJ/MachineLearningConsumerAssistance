/**
 * A basic object to hold useful data and some useful functions pertaining to Houses.
 */
public class House {

    String address;
    int bedrooms = -1;
    int bathrooms = -1;
    int SqFt = -1;
    double budget = -1;
    double lon;
    double lat;

    /**
     * This function is meant to be applied to a house already in the data set.
     */
    public House(String address, int bedrooms, int bathrooms, int sqFt, double budget, double lon, double lat)
    {
        this.address = address;
        this.bedrooms = bedrooms;
        this.bathrooms = bathrooms;
        this.SqFt = sqFt;
        this.budget = budget;
        this.lon = lon;
        this.lat = lat;
    }

    /**
     * This function is meant to be applied to a house that we think another house is like from the data set.
     */
    public House(int bedrooms, int bathrooms, double budget)
    {
        this.bedrooms = bedrooms;
        this.bathrooms = bathrooms;
        this.budget = budget;
        //this.lon = lon;
        //this.lat = lat;
    }

//    /**
//     * Fix units for radius
//     * @param h
//     * @param radius
//     * @return
//     */
//    public boolean inRadius(House h, double radius)
//    {
//        double diffX = h.lat - lat;
//        double diffY = h.lon - lon;
//
//        double diffC = Math.sqrt(Math.pow(diffX, 2) + Math.pow(diffY, 2));
//        if(diffC - radius < 0)
//        {
//            return true;
//        }
//        return false;
//    }


    /**
     * Checks if the house is actually valid.
     * @return True if the house is valid and has an address.
     */
    public boolean available()
    {
        return address != null;
    }

    /**
     * Checks if the data given includes bedrooms.
     * @return Returns true if the data contains bedrooms, false if it does not.
     */
    public boolean filledBedrooms()
    {
        if(bedrooms == -1)
            return false;
        else return true;
    }

    /**
     * Checks if the data given includes bathrooms.
     * @return Returns true if the data contains bathrooms, false if it does not.
     */
    public boolean filledBathrooms()
    {
        if(bathrooms == -1)
            return false;
        else return true;
    }

    /**
     * Checks if the data given includes footage.
     * @return Returns true if the data contains footage, false if it does not.
     */
    public boolean filledSqFt()
    {
        if(SqFt == -1)
            return false;
        else return true;
    }

    /**
     * Checks if the data given includes a price/budget.
     * @return Returns true if the data contains price/budget, false if it does not.
     */
    public boolean filledBudget()
    {
        if(budget == -1)
            return false;
        else return true;
    }

    /**
     * Allows to set the house's bedroom count.
     * @param b The amount of bedrooms the house has.
     */
    public void setBedrooms(int b)
    {
        bedrooms = b;
    }

    /**
     * Allows to set the house's bathroom count.
     * @param b The amount of bathroom the house has.
     */
    public void setBathrooms(int b)
    {
        bathrooms = b;
    }

    /**
     * Allows the footage to be set for the house.
     * @param s The footage of the house.
     */
    public void setSqFt(int s)
    {
        SqFt = s;
    }

    /**
     * Even though this is named budget, it is more accurately the price of the house.
     * @param b The price of the house.
     */
    public void setBudget(double b)
    {
        budget = b;
    }

    /**
     * Provides the lower bound of the footage since it technically a range.
     * @return Returns the lower bound of the footage.
     */
    public int lowSqFt()
    {
        return SqFt - 200;
    }

    /**
     * Provides the upper bound of the footage since it technically a range.
     * @return Returns the upper bound of the footage.
     */
    public int highSqFt()
    {
        return SqFt + 200;
    }

}
