package io.project.weather_project;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.json.JSONObject;

public class HelloController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField city;

    @FXML
    private Button getData;

    @FXML
    private Text pressure;

    @FXML
    private Text temp_feels;

    @FXML
    private Text temp_info;

    @FXML
    private Text temp_max;

    @FXML
    private Text temp_min;

// For the application to work correctly, you must specify the API key instead of "XXX"
    @FXML
    void initialize() {
        getData.setOnAction(event ->{
            String getUserCity = city.getText().trim();
            if (!getUserCity.equals("")) {
                String output = getUrlContent("http://api.openweathermap.org/data/2.5/weather?q=" + getUserCity + "&lang=ru&appid=" + "XXX" + "&units=metric");
                if (!output.isEmpty()) {
                    JSONObject obj = new JSONObject(output);
                    temp_info.setText("Температура: " + (int)(obj.getJSONObject("main").getDouble("temp")));
                    temp_feels.setText("Ощущается: " + (int)(obj.getJSONObject("main").getDouble("feels_like")));
                    temp_max.setText("Максимум: " + (int)(obj.getJSONObject("main").getDouble("temp_max")));
                    temp_min.setText("Минимум: " + (int)(obj.getJSONObject("main").getDouble("temp_min")));
                    pressure.setText("Давление: " + (int)(obj.getJSONObject("main").getDouble("pressure")));
                }
            }
        });

    }

    private static String getUrlContent (String urlAdress) {
        StringBuffer content = new StringBuffer();
        try {
            URL url = new URL(urlAdress);
            URLConnection urlConn = url.openConnection();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                content.append((line + "\n"));
            }
            bufferedReader.close();
        }
        catch (Exception e) {
            System.out.println("Указанный город не найден");
        }
        return content.toString();
    }
}
