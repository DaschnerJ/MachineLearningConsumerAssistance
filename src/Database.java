import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.net.URLDecoder;
import java.util.ArrayList;

/**
 * This is a general class that handles most of the stored data and keeps a short track of already asked information.
 */
public class Database {

    ArrayList<House> houses = new ArrayList<>();

    ArrayList<House> removed = new ArrayList<>();

    ArrayList<House> liked = new ArrayList<>();

    public static int radius = 10;

    LayerHandler lh = new LayerHandler();

    public Database()
    {
        loadData();
        //System.out.println("Loaded: " + houses.size());
    }


    /**
     * Gets the external path to the jar.
     * @return Returns the String of an external path to the current location of the jar being ran.
     */
    public static String getJarLocation()
    {
        String path = Database.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        try {
            String decodedPath = URLDecoder.decode(path, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String[] s = path.split("/");
        String r = "";
        for(String x : s)
        {
            if (!x.contains("jar"))
            {
                r = r + x + "/";
            }
        }

        return r;
    }

    /**
     * Loads the data into memory from the spread sheet. This is the data that the consumer will be asked and their
     * choices compared to.
     */
    public void loadData()
    {
        try (BufferedReader br = new BufferedReader(new FileReader(getJarLocation()+"MLdata.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] split = line.split(",");
                //House(String address, int bedrooms, int bathrooms, int sqFt, double budget, double lon, double lat)
                House house = new House(split[1], Integer.valueOf(split[6]), Integer.valueOf(split[7]), Integer.valueOf(split[8]), Double.valueOf(split[5]), Double.valueOf(split[3]), Double.valueOf(split[2]));
                houses.add(house);

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * If the user dislikes a house, the house is rejected and not asked again until the queue is reset.
     * @param a The house that was rejected.
     */
    public void reject(House a)
    {
        houses.remove(a);
        removed.add(a);
    }

    /**
     * If the user likes a house, the house is saved and not asked again until the queue is reset. Weights are changed
     * accordingly on the successful choice.
     * @param a The house that was accepted.
     */
    public void accept(House a)
    {
        lh.choiceBathrooms(a.bathrooms);
        lh.choiceBedrooms(a.bedrooms);
        lh.choiceFootage(a.SqFt);
        houses.remove(a);
        liked.add(a);
    }

    /**
     * Resets the queues and puts the rejected and accepted houses back in the queue. Note this does not reset
     * the already learned values.
     */
    public void reset()
    {
        houses.addAll(removed);
        houses.addAll(liked);
        liked = new ArrayList<>();
        removed = new ArrayList<>();
    }

    /**
     * Attempts the match a house based on some information given.
     * @param bedrooms Can be -1 or +1. -1 being not specified and +1 being desired.
     * @param bathrooms Can be -1 or +1. -1 being not specified and +1 being desired.
     * @param budget Has to be some number > 0,
     * @return Returns null if no house is found or a house with data that the machine thinks the consumer wants.
     */
    public House match(int bedrooms, int bathrooms, double budget)
    {
        System.out.println("Matching: " + bedrooms + " - " + bathrooms);
        return match(new House(bedrooms, bathrooms, budget));
    }

    /**
     * Attempts the match a house based on some information given.
     * @param a The information given for a house that person desires and is limited by.
     * @return Returns null if no house is found or a house with data that the machine thinks the consumer wants.
     */
    public House match(House a)
    {
        int bathrooms;
        int bedrooms;
        int feet = lh.getFootage();

        if(a.bathrooms == -1)
            bathrooms = lh.getBathrooms();
        else
            bathrooms = a.bathrooms;
        if(a.bedrooms == -1)
            bedrooms = lh.getBedrooms();
        else
            bedrooms = a.bedrooms;

        System.out.println("Combination: " + bedrooms + "-" + bathrooms);
        for(House x : houses)
        {
            //We use approximation here and purposely give some lee way in the choices since the data set is small
            //and there is not that many options. If there was more data to go off of then this would be more restricted.
            if (a.budget > x.budget) {
                //if (a.inRadius(x, radius)) {
                    if (x.bathrooms < bathrooms + 1 || x.bathrooms > bathrooms - 1) {
                        if (x.bedrooms < bedrooms + 1 || x.bedrooms > bedrooms - 1) {
                            if (feet < x.highSqFt() && feet > x.lowSqFt()) {
                                return x;
                            }
                        }
                    }
                //}
            }
        }
        return null;
    }

    /**
     * Possible api.
     * @param json
     */
    public void recieve(File json)
    {
        // parsing file "JSONExample.json"
        Object obj = null;
        try {
            obj = new JSONParser().parse(new FileReader(json));
            JSONObject jo = (JSONObject) obj;

            String type = (String) jo.get("type");
            if(type.equals("accept"))
                accept(json);
            if(type.equals("reject"))
                reject(json);
            if(type.equals("match"))
                match(json);


        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    /**
     * Possible API.
     * @param json
     */
    public void accept(File json)
    {
        // parsing file "JSONExample.json"
        Object obj = null;
        try {
            obj = new JSONParser().parse(new FileReader(json));
            JSONObject jo = (JSONObject) obj;

            String address = (String) jo.get("address");

            House h = null;
            for(House hou : houses)
            {
                if(hou.address.equals(address))
                    h = hou;
            }
            accept(h);



        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * Possible API.
     * @param a
     * @return
     */
    public JSONObject createMatchJSON(House a)
    {
        JSONObject obj = new JSONObject();
        if(a.address == null)
            obj.put("address", "null");
        else
        {
            obj.put("address", a.address);
            obj.put("bathrooms", a.bathrooms);
            obj.put("bedrooms", a.bedrooms);
            obj.put("sqft", a.SqFt);
            obj.put("latitude", a.lat);
            obj.put("longitude", a.lon);
            obj.put("price", a.budget);
        }

        return obj;
    }

    /**
     * Possible API.
     * @param a
     */
    public void sendMatch(House a)
    {
        JSONObject json = createMatchJSON(a);
        //send it here....
    }

    /**
     * Possible API.
     * @param json
     */
    public void match(File json)
    {
        // parsing file "JSONExample.json"
        Object obj = null;
        try {
            obj = new JSONParser().parse(new FileReader(json));
            JSONObject jo = (JSONObject) obj;

            int bathrooms = (int)jo.get("bathrooms");
            int bedrooms = (int)jo.get("bedrooms");
            double budget = (double)jo.get("budget");
            double lon = (double)jo.get("longitude");
            double lat = (double)jo.get("latitude");

            sendMatch(match(bedrooms, bathrooms, budget));


        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * Possible API.
     * @param json
     */
    public void reject(File json)
    {
        // parsing file "JSONExample.json"
        Object obj = null;
        try {
            obj = new JSONParser().parse(new FileReader(json));
            JSONObject jo = (JSONObject) obj;

            String address = (String) jo.get("address");

            House h = null;
            for(House hou : houses)
            {
                if(hou.address.equals(address))
                    h = hou;
            }
            reject(h);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * Applies the reject for the database on the given address.
     * @param address The address of the house you wish to try and find and reject from the data set.
     */
    public void reject(String address)
    {
        House h = null;
        for(House hou : houses)
        {
            if(hou.address.equals(address))
                h = hou;
        }
        if(h != null)
        reject(h);
    }

    /**
     * Applies the accept for the database on the given address.
     * @param address The address of the house you wish to try and find and accept from the data set.
     */
    public void accept(String address)
    {
        House h = null;
        for(House hou : houses)
        {
            if(hou.address.equals(address))
                h = hou;
        }
        if(h != null)
        accept(h);
    }

}
