package projekt;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Random;

//Uppkoppling mot
public class receptDB {
    private static String url, username, password;

    private static void init(String filename){
        Properties props = new Properties();

        try(FileInputStream in = new FileInputStream(filename)){
            props.load(in);
            url = props.getProperty("jdbc.url");
            username = props.getProperty("jdbc.username");
            if(username == null){
                username = "";
            }
            password = props.getProperty("jdbc.password");
            if(password == null){
                password = "";
            }
        } catch (IOException ex){
            System.out.println("Something went wrong..." + ex.getMessage());
        }
    }

    public static Connection getConnection() throws SQLException{
        return DriverManager.getConnection(url, username, password);
    }

    public static void main(String[] args) {

    }

    public static ArrayList<Recept> getTopRecList() {
        init("src/database.properties");

        ArrayList<Recept> recNameList = new ArrayList<>();

        try (Connection conn = getConnection()) {

             Statement statement = conn.createStatement();
             ResultSet result = statement.executeQuery("SELECT Namn, Betyg, Beskrivning, Bild_url, Lank FROM recept ORDER BY Betyg DESC LIMIT 10");

             while (result.next()) {
                 recNameList.add(new Recept (result.getString("Namn"), result.getInt("Betyg"), result.getString("Beskrivning"), result.getString("Bild_url"), result.getString("Lank")));
             }

            return recNameList;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return recNameList;

    }

    public static ArrayList<Recept> getAllRecept() {
        init("src/database.properties");

        ArrayList<Recept> allRecList = new ArrayList<>();

        try (Connection conn = getConnection()) {
            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery("SELECT Namn, Betyg, Beskrivning, Bild_url, Lank FROM recept");
            while (result.next()) {
                allRecList.add(new Recept (result.getString("Namn"), result.getInt("Betyg"), result.getString("Beskrivning"), result.getString("Bild_url"), result.getString("Lank")));
            }
            return allRecList;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allRecList;

    }

    public static ArrayList<Recept> GetRndRec() {
        init("src/database.properties");

        ArrayList<Recept> recept = new ArrayList<>();
        Random rnd = new Random();

        try (Connection conn = getConnection()) {
            //Gör en variabel för att hålla max antal recept id
            Statement maxID = conn.createStatement();
            ResultSet maxIDN = maxID.executeQuery("SELECT MAX(Recept_ID) AS Recept_ID FROM recept");

            //maxid blir hela tiden tilldelad 49...måste vara för jag lagt till nio recept som test? //Cleas
            int maxid = 0;
            while(maxIDN.next()) {
                maxid = maxIDN.getInt("Recept_ID");
            }
            //Randomize från 1 och antalet receptid
            int RndRec = (rnd.nextInt(maxid) + 1);
            PreparedStatement statement1 = conn.prepareStatement("SELECT Namn, Betyg, Beskrivning, Bild_url, Lank FROM recept WHERE Recept_ID = ?");
            statement1.setInt(1, RndRec);
            ResultSet resultat2 = statement1.executeQuery();

            while (resultat2.next()) {
                recept.add(new Recept (resultat2.getString("Namn"), resultat2.getInt("Betyg"), resultat2.getString("Beskrivning"), resultat2.getString("Bild_url"), resultat2.getString("Lank")));
            }

            return recept;

        } catch (SQLException e) {
            e.printStackTrace();
            return recept;
        }

    }

    public static ArrayList<Recept> search(String input) {
        init("src/database.properties");

        String recName;
        int recID;
        ArrayList<Recept> recNameList = new ArrayList<>();

        try (Connection conn = getConnection()) {
            //Anropa procedur spSearchRec för att söka recept
            CallableStatement statement1 = conn.prepareCall("{CALL spSearchRecept(?)}");

            //Inparameter skickas in till proceduren
            statement1.setString(1, input);

            ResultSet result = statement1.executeQuery();


            if (!result.next()){
                recNameList.add(new Recept("Din sökning gav inga resultat"));
            } else {

                do {
                    recName = result.getString(1);
                    recID = result.getInt(2);
                    recNameList.add(new Recept(recName, recID));
                } while (result.next());
            }

            //Returnera sökresultaten
            return recNameList;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return recNameList;
    }

    public static ArrayList<String> fillHuvudComboBox() {
        init("src/database.properties");
        ArrayList<String> huvudIng = new ArrayList<>();

        try (Connection conn = getConnection()) {
            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery("select Pr_namn from protein");

            while(result.next()){
                huvudIng.add(result.getString(1));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return huvudIng;
    }

    public static ArrayList<String> fillKolhydratComboBox() {
        init("src/database.properties");
        ArrayList<String> kolhydrat = new ArrayList<>();

        try (Connection conn = getConnection()) {


            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery("select Kol_namn from kolhydrater");

            while(result.next()){
                kolhydrat.add(result.getString(1));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return kolhydrat;
    }

    public static ArrayList<String> fillGronsakComboBox() {
        init("src/database.properties");
        ArrayList<String> gronsak = new ArrayList<>();

        try (Connection conn = getConnection()) {

            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery("select Gr_namn from gronsak");

            while(result.next()){
                gronsak.add(result.getString(1));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return gronsak;
    }

    public static void insertRecipe(String url, String beskrivning, String namn, String hemsida, int betyg, String lank, String svgrad, String kol, String huvud, String gron){
        init("src/database.properties");

        try (Connection conn = getConnection()){
            CallableStatement recept = conn.prepareCall("{call spAddRecept(?, ? ,? ,? ,? ,? ,?)}");
            recept.setString(1, url);
            recept.setString(2, beskrivning);
            recept.setString(3, namn);
            recept.setString(4, hemsida);
            recept.setInt(5, betyg);
            recept.setString(6, lank);
            recept.setString(7, svgrad);
            //Exekverar inserten
            recept.executeUpdate();

            Statement maxID = conn.createStatement();
            ResultSet maxIDN = maxID.executeQuery("SELECT MAX(Recept_ID) AS Recept_ID FROM recept");

            //Denna variabel ska hålla senaste recept id:t som ska kunna kopplas till de andra kopplingstabellerna
            int maxid = 0;
            while(maxIDN.next()) {
                maxid = maxIDN.getInt("Recept_ID");
            }
            //Kopplar kolhydrat med senaste receptet
            PreparedStatement kolKoppling = conn.prepareStatement("insert into kolhydrater_tillhor values(?, ?)");
            kolKoppling.setString(1, kol);
            kolKoppling.setInt(2, maxid);

            //Kopplar kolhydrat med senaste receptet
            PreparedStatement huvudKoppling = conn.prepareStatement("insert into protein_tillhor values(?, ?)");
            huvudKoppling.setString(1, huvud);
            huvudKoppling.setInt(2, maxid);

            //Kopplar kolhydrat med senaste receptet
            PreparedStatement gronKoppling = conn.prepareStatement("insert into gronsak_tillhor values(?, ?)");
            gronKoppling.setString(1, gron);
            gronKoppling.setInt(2, maxid);

            kolKoppling.executeUpdate();
            huvudKoppling.executeUpdate();
            gronKoppling.executeUpdate();

        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }
}