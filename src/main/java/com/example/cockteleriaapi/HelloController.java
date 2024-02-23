package com.example.cockteleriaapi;

import Modelos.Bebida;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import org.json.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class HelloController implements Initializable {

    @FXML
    private ComboBox<Bebida> cmbCocktail;
    @FXML
    private ComboBox<String> cmbFiltro;
    @FXML
    private AnchorPane fondo;
    @FXML
    private ImageView imgCocktail;
    @FXML
    private TableView tablaBebidas;
    @FXML
    private TableColumn colId;
    @FXML
    private TableColumn colIngredientes;
    @FXML
    private TableColumn colNombre;
    @FXML
    private TextField txtFiltro;

    private ObservableList<Bebida> listaBebidas;
    private ObservableList<Bebida> listaBebidasAlc;
    private ObservableList<Bebida> listaBebidasNoAlc;
    private ObservableList<Bebida> todasBebidasAlcoholicas;
    private ObservableList<Bebida> todasBebidasNoAlcoholicas;
    private ObservableList<Bebida> todasBebidas;
    private ObservableList<Bebida> filtroBebida;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        listaBebidasNoAlc = FXCollections.observableArrayList();
        listaBebidasAlc = FXCollections.observableArrayList();
        listaBebidas = FXCollections.observableArrayList();

        todasBebidasAlcoholicas = FXCollections.observableArrayList();
        todasBebidasNoAlcoholicas = FXCollections.observableArrayList();
        todasBebidas = FXCollections.observableArrayList();

        filtroBebida = FXCollections.observableArrayList();

        ObservableList <String> combo = FXCollections.observableArrayList();
        combo.addAll("Todas", "Bebidas alcohólicas", "Bebidas no alcohólicas");
        cmbFiltro.setItems(combo);
        cmbFiltro.setValue("Todas");

        alcoholicas();
        noAlcoholicas();

        cmbCocktail.setItems(todasBebidas);

        colId.setCellValueFactory(new PropertyValueFactory("id"));;
        colNombre.setCellValueFactory(new PropertyValueFactory("nombre"));;
        colIngredientes.setCellValueFactory(new PropertyValueFactory("ingredientes"));

    }

    public void alcoholicas (){
        String url = "https://www.thecocktaildb.com/api/json/v1/1/filter.php?a=Alcoholic";
        InputStream inputStream = null;
        try {
            inputStream = new URL(url).openStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String response = null;
        try {
            response = bufferedReader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        JSONObject jsonObject = new JSONObject(response);
        JSONArray obj = jsonObject.getJSONArray("drinks");

        for (int i=0; i<obj.length();i++){
            JSONObject objeto = obj.getJSONObject(i);

            String nombre = objeto.getString("strDrink");
            String id = objeto.getString("idDrink");

            Bebida bebida = new Bebida(Integer.parseInt(id), nombre);
            todasBebidas.add(bebida);
            todasBebidasAlcoholicas.add(bebida);
        }
    }

    public void noAlcoholicas (){
        String url = "https://www.thecocktaildb.com/api/json/v1/1/filter.php?a=Non_Alcoholic";
        InputStream inputStream = null;
        try {
            inputStream = new URL(url).openStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String response = null;
        try {
            response = bufferedReader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        JSONObject jsonObject = new JSONObject(response);
        JSONArray obj = jsonObject.getJSONArray("drinks");
        for (int i=0; i<obj.length();i++){
            JSONObject objeto = obj.getJSONObject(i);

            String nombre = objeto.getString("strDrink");
            String id = objeto.getString("idDrink");

            Bebida bebida = new Bebida(Integer.parseInt(id), nombre);
            todasBebidas.add(bebida);
            todasBebidasNoAlcoholicas.add(bebida);
        }
    }

    public String obtenerIngredientes (String nombre){
        String url = "https://www.thecocktaildb.com/api/json/v1/1/search.php?s=" + nombre;
        InputStream inputStream = null;
        try {
            inputStream = new URL(url).openStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String response = null;
        try {
            response = bufferedReader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        JSONObject jsonObject = new JSONObject(response);
        JSONArray obj = jsonObject.getJSONArray("drinks");
        String preparacion = "";
        for (int i=0; i<obj.length();i++){
            JSONObject objeto = obj.getJSONObject(i);

            preparacion = objeto.getString("strInstructions");

        }

        return preparacion;
    }

    public void cargarImgBebida (){
        if (cmbCocktail.getSelectionModel().getSelectedIndex()>1){
            String url = "https://www.thecocktaildb.com/api/json/v1/1/search.php?s=" + cmbCocktail.getSelectionModel().getSelectedItem();
            InputStream inputStream = null;
            try {
                inputStream = new URL(url).openStream();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String response = null;
            try {
                response = bufferedReader.readLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            JSONObject jsonObject = new JSONObject(response);
            JSONArray obj = jsonObject.getJSONArray("drinks");
            for (int i=0; i<obj.length();i++){
                JSONObject objeto = obj.getJSONObject(i);
                try {
                    String bebida = objeto.getString("strDrinkThumb");
                    imgCocktail.setImage(new Image(bebida));
                } catch (NullPointerException e) {
                    System.out.println("la bebida no existe");
                }
            }
        }
    }

    @FXML
    public void meterDatosTabla (ActionEvent event){
        if (cmbCocktail.getSelectionModel().getSelectedIndex()>1){
            cargarImgBebida();
            Bebida b = cmbCocktail.getSelectionModel().getSelectedItem();

            if (!listaBebidas.contains(b)){
                b.setIngredientes(obtenerIngredientes(b.getNombre()));

                listaBebidas.add(b);

                if (todasBebidasAlcoholicas.contains(b)){
                    listaBebidasAlc.add(b);
                }

                if (todasBebidasNoAlcoholicas.contains(b)){
                    listaBebidasNoAlc.add(b);
                }

                filtrar();
            }

        }
    }

    @FXML
    public ObservableList filtrar (){
        if (cmbFiltro.getSelectionModel().getSelectedItem().equalsIgnoreCase("Todas")){
            tablaBebidas.setItems(listaBebidas);
            return listaBebidas;
        }else if (cmbFiltro.getSelectionModel().getSelectedItem().equalsIgnoreCase("Bebidas alcohólicas")){
            tablaBebidas.setItems(listaBebidasAlc);
            return listaBebidasAlc;
        }else {
            tablaBebidas.setItems(listaBebidasNoAlc);
            return listaBebidasNoAlc;
        }
    }

    @FXML
    public void buscarBebidas (KeyEvent event){
        String filtro = this.txtFiltro.getText().toLowerCase();

        if (filtro.isEmpty()){
            filtrar();
        }else {
            filtroBebida.clear();
            ObservableList<Bebida> s = filtrar();
            for (Bebida b : s){
                if (b.getNombre().toLowerCase().contains(filtro)){
                    filtroBebida.add(b);
                }
            }

            tablaBebidas.setItems(filtroBebida);
        }
    }
}