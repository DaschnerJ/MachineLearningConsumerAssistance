import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Optional;
import java.util.Set;


public class Main extends Application {


    //Theme t;
    Database db;

    TextField address;
    NumberTextField bedrooms;
    NumberTextField bathrooms;
    NumberTextField budget;

    TextField rAddress;
    TextField rPrice;
    TextField rBedrooms;
    TextField rBathrooms;
    TextField rFeet;
    TextField rLongitude;
    TextField rLatitude;

    Button Find;

    Button Like;
    Button Dislike;
    Button Reset;


    @Override
    public void start(Stage primaryStage) throws Exception {
        //t = new Theme();
        db = new Database();
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();

        primaryStage.setX(bounds.getMinX());
        primaryStage.setY(bounds.getMinY());
        primaryStage.setWidth(bounds.getWidth());
        primaryStage.setHeight(bounds.getHeight());

        primaryStage.setTitle("Machine Learning In RealEstate");

        HBox bDivider = new HBox();
        Like = new Button("Like!");
        Dislike = new Button("Dislike!");
        Reset = new Button("Reset");



        bDivider.getChildren().addAll(Like, Dislike, Reset);

        bDivider.setSpacing(10);
        bDivider.setMinWidth(200.0);
        bDivider.setPadding(new Insets(15,20, 10,10));



        HBox divider = new HBox();
        VBox right = new VBox();
        VBox left = new VBox();
        divider.getChildren().add(left);
        divider.getChildren().add(right);
        left.setSpacing(10);
        left.setMinWidth(200.0);
        left.setPadding(new Insets(15,20, 10,10));

        //address = new TextField("Address");
        bedrooms = new NumberTextField();
        bedrooms.setText("0");
        bathrooms = new NumberTextField();
        bathrooms.setText("0");
        budget = new NumberTextField();
        budget.setText("2000");

        //left.getChildren().add(label(address, "Address: "));
        left.getChildren().add(label(bedrooms, "Bedrooms: "));
        left.getChildren().add(label(bathrooms, "Bathrooms: "));
        left.getChildren().add(label(budget, "Budget: "));

        rAddress = new TextField("");
        rPrice = new TextField("");
        rBedrooms = new TextField("");
        rBathrooms = new TextField("");
        rFeet = new TextField("");
        rLongitude = new TextField("" );
        rLatitude = new TextField("");

        rAddress.setEditable(false);
        rPrice.setEditable(false);
        rBedrooms.setEditable(false);
        rBathrooms.setEditable(false);
        rFeet.setEditable(false);
        rLongitude.setEditable(false);
        rLatitude.setEditable(false);

        right.setSpacing(10);
        right.setPadding(new Insets(15,20, 10,10));

        right.getChildren().addAll(label(rAddress, "Address: "), label(rPrice, "Cost: "), label(rBedrooms, "Bedrooms: "), label(rBathrooms, "Bathrooms: "), label(rFeet, "Square Feet: "),
                label(rLongitude, "Longitude: "), label(rLatitude, "Latitude: "), bDivider);

        Find = new Button("Search!");

        Find.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                press();
            }
        });

        Like.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                db.accept(rAddress.getText());
                press();
            }
        });

        Dislike.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                db.reject(rAddress.getText());
                press();
            }
        });

        Reset.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                db.reset();
                press();
            }
        });

        Find.setPrefWidth(bedrooms.getPrefWidth());
        Find.setMinWidth(bedrooms.getMinWidth());
        Find.setMaxWidth(bedrooms.getMaxWidth());
        left.getChildren().add(Find);


        Scene s = new Scene(divider, 300, 250);
        //System.out.println("Path: " + t.getThemes().get(3));
        s.getStylesheets().clear();
        s.getStylesheets().add(
                getClass().getResource("Flatter.css").toExternalForm());
        primaryStage.setScene(s);
        primaryStage.show();
    }

    private Slider getSlider(int min, int max)
    {
        Slider slider = new Slider();
        slider.setMin(min);
        slider.setMax(max);
        slider.setValue((min+max)/2);
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);
        slider.setMajorTickUnit(1);
        slider.setBlockIncrement(1);
        return slider;
    }

    private HBox label(Node n, String text)
    {
        HBox h = new HBox();
        Label l = new Label(text);
        l.setMaxWidth(200);
        l.setMinWidth(200);
        l.setPrefWidth(200);
        h.getChildren().addAll(l, n);
        return h;
    }

    private void press()
    {
        System.out.println("Pressed.");
        int bedroomsCount;
        int bathroomsCount;
        double budgetCount;

        int val = Integer.valueOf(bedrooms.getText());
        if(val == 0)
            bedroomsCount = -1;
        else
            bedroomsCount = val;

        val = Integer.valueOf(bathrooms.getText());
        if(val == 0)
            bathroomsCount = -1;
        else
            bathroomsCount = val;
        budgetCount = Double.parseDouble(budget.getText());
        House h = db.match(bedroomsCount, bathroomsCount, budgetCount);
        if(h == null) {
            rAddress.setText("Failed to find house. Please change inputs.");
            rBathrooms.setText("");
            rBedrooms.setText("");
            rFeet.setText("");
            rPrice.setText("");
            rLatitude.setText("");
            rLongitude.setText("");
        }
        else
        {
            rAddress.setText(h.address);
            rBathrooms.setText(h.bathrooms + "");
            rBedrooms.setText(h.bedrooms + "");
            rFeet.setText(h.SqFt + "");
            rPrice.setText(h.budget + "");
            rLatitude.setText(h.lat + "");
            rLongitude.setText(h.lon + "");
        }
        System.out.println("Released.");
    }

    public void getCoordinates(String address)
    {
        HttpURLConnection conn = null;
        StringBuilder jsonResults = new StringBuilder();
        try {


            System.out.println("Address: " + address);
            System.out.println("https://maps.googleapis.com/maps/api/geocode/json?address="+address.trim().replace(" ", "+")+"&key=AIzaSyDM0ujVlo__U38fkHzUoo64MSh1bRMs5YM");
            URL url = new URL("https://maps.googleapis.com/maps/api/geocode/json?address="+address.trim().replace(" ", "+")+"&key=AIzaSyDM0ujVlo__U38fkHzUoo64MSh1bRMs5YM");
            conn = (HttpURLConnection) url.openConnection();
            InputStreamReader in = new InputStreamReader(conn.getInputStream());

            int read;
            char[] buff = new char[1024];
            while ((read = in.read(buff)) != -1) {
                jsonResults.append(buff, 0, read);
                //System.out.println("Results: " + jsonResults.toString());

            }
            Object obj = new JSONParser().parse(jsonResults.toString());
            System.out.println(obj);
            JSONObject jo = (JSONObject) obj;
            System.out.println(jo.toJSONString());

            //System.out.println(j2.get("geometry"));
            //System.out.println(jo.toJSONString());
            //System.out.println(jo.toJSONString());
            //System.out.println((String)jo.get("location"));

            //System.out.print(jo);
//            JSONObject j2 = (JSONObject) jo.get("Result");
//            JSONObject j3 = (JSONObject) j2.get("geomtry");
//            JSONObject j4 = (JSONObject) j3.get("location");
//            double lat = (double) j4.get("lat");
//            double lon = (double) j4.get("lon");
//            System.out.println("Long: " + lon + " Lat: " + lat);
        } catch (MalformedURLException e) {

        } catch (IOException e) {

        } catch (ParseException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }





}
