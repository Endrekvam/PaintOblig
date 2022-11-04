package com.example.paintoblig;

import com.example.paintoblig.Figurer.*;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.geometry.Insets;

import java.util.ArrayList;
import java.util.Collections;

import static com.example.paintoblig.Figurer.FigurListe.SELECT;
import static com.example.paintoblig.Figurer.FigurListe.TEXT;


public class GUI {
    BorderPane bp;
    public static Pane canvasPane = new Pane();
    public final int bredde = 1400;
    public final int hoyde = 900;
    public static String infoTekst;
    public static ColorPicker redigerFyllCP = new ColorPicker();
    public static ColorPicker redigerLinjeCP = new ColorPicker();
    public static ColorPicker fyllCP = new ColorPicker();
    public static ColorPicker linjeCP = new ColorPicker();
    public static ToggleGroup tg = new ToggleGroup();
    public static RadioButton velg = new RadioButton("Velg figur");
    public static Shape figur;
    private static Shape selected;
    private Pane tegnePane;
    public static boolean velgErAktiv;
    public static ListView<String> lv = new ListView<>();
    public static ArrayList<Shape> shapeList = new ArrayList<>();
    public static ArrayList<String> figurInfo = new ArrayList<>();
    public static TextArea ta = new TextArea();
    public static TextField tf = new TextField();
    public static Slider slider = new Slider();

    public GUI(Stage stage) throws Exception {

        bp = new BorderPane();
        tegnePane = canvasPane();
        // Viser tegnePane først for at tegningene skal "bak" infoBox og toolBox
        bp.setCenter(tegnePane);
        bp.setRight(infoBox());
        bp.setLeft(toolBox());

        bp.getChildren().addAll();
        bp.setStyle("-fx-border-color: black");

        Scene scene = new Scene(bp, bredde, hoyde);
        stage.setResizable(false);
        stage.setTitle("Tegneprogram hipp hurra");
        stage.setScene(scene);
        stage.show();
    }

    public VBox toolBox() {
        // Oppretter og styler knapper og slider
        VBox toolBox = new VBox(15);
        toolBox.setPadding(new Insets(100, 10, 15, 15));
        toolBox.setStyle("-fx-border-color: black");
        toolBox.setStyle("-fx-background-color: linear-gradient(to right, black, grey)");
        toolBox.setAlignment(Pos.TOP_LEFT);

        Button flyttBak = new Button("Flytt bak");
        flyttBak.setOnAction(e -> {
            flyttBakover();
        });

        Button flyttFrem = new Button("Flytt frem");
        flyttFrem.setOnAction(e -> {
            flyttFremover();
        });

        Button blankUt = new Button("Blank ut");
        blankUt.setOnAction(e -> {
            tegnePane.getChildren().clear();
            ta.setText("");
            shapeList.clear();
            figurInfo.clear();
            lv.getItems().clear();
        });

        velg.setUserData(SELECT);
        velg.setToggleGroup(tg);
        velg.setTextFill(Color.WHITE);
        velg.setOnAction( e -> {
            velgErAktiv = true;
        });

        RadioButton linjeBtn = new RadioButton("Linje");
        linjeBtn.setUserData(FigurListe.LINE);
        linjeBtn.setToggleGroup(tg);
        linjeBtn.setSelected(true);
        linjeBtn.setTextFill(Color.WHITE);
        linjeBtn.setOnAction( e -> {
            // Forsikrer at "velg" blir inaktiv ved bruk av andre funksjoner
            velgErAktiv = false;
        });

        RadioButton sirkelBtn = new RadioButton("Sirkel");
        sirkelBtn.setUserData(FigurListe.CIRCLE);
        sirkelBtn.setToggleGroup(tg);
        sirkelBtn.setTextFill(Color.WHITE);
        sirkelBtn.setOnAction( e -> {
            velgErAktiv = false;
        });

        RadioButton firkantBtn = new RadioButton("Firkant");
        firkantBtn.setUserData(FigurListe.RECTANGLE);
        firkantBtn.setToggleGroup(tg);
        firkantBtn.setTextFill(Color.WHITE);
        firkantBtn.setOnAction( e -> {
            velgErAktiv = false;
        });

        RadioButton tekstBtn = new RadioButton("Tekst");
        tekstBtn.setUserData(TEXT);
        tekstBtn.setToggleGroup(tg);
        tekstBtn.setTextFill(Color.WHITE);
        tf.setPromptText("Skriv tekst her");
        tekstBtn.setOnAction( e -> {
            velgErAktiv = false;
        });

        Text sliderTekst = new Text("Tykkelse kantlinje:");
        sliderTekst.setFont(Font.font(15));
        sliderTekst.setFill(Color.WHITE);

        Text fyllFargeTxt = new Text("Velg fyllfarge:");
        fyllFargeTxt.setFill(Color.WHITE);
        Text linjeFargeTxt = new Text("Velg linjefarge:");
        linjeFargeTxt.setFill(Color.WHITE);

        fyllCP.setValue(Color.TRANSPARENT);
        linjeCP.setValue(Color.BLACK);

        slider.setMin(1);
        slider.setMax(40);
        slider.setValue(3);
        slider.setOnMouseDragged(e -> {
            if (velgErAktiv) {
                selected.setStrokeWidth(slider.getValue());
            }
        });

        toolBox.getChildren().addAll(velg, flyttBak, flyttFrem, linjeBtn, sirkelBtn, firkantBtn,
                tekstBtn, tf, sliderTekst, slider, blankUt, fyllFargeTxt, fyllCP, linjeFargeTxt, linjeCP);

        return toolBox;
    }

    public VBox infoBox() {
        // Oppretter textarea og listview, knapper osv
        VBox infoBox = new VBox(15);
        infoBox.setPadding(new Insets(100, 15, 15, 15));
        infoBox.setStyle("-fx-border-color: black");
        infoBox.setStyle("-fx-background-color: linear-gradient(to left, black, grey)");
        infoBox.setAlignment(Pos.TOP_CENTER);

        Text fyllTekst = new Text("Velg fyllfarge:");
        fyllTekst.setFill(Color.WHITE);
        redigerFyllCP.setValue(Color.TRANSPARENT);
        redigerFyllCP.setOnAction(e-> {
            if (velgErAktiv)
            selected.setFill(redigerFyllCP.getValue());
        });

        Text linjeTekst = new Text("Velg linjefarge:");
        linjeTekst.setFill(Color.WHITE);
        redigerLinjeCP.setValue(Color.BLACK);
        redigerLinjeCP.setOnAction(e-> {
            if (velgErAktiv)
            selected.setStroke(redigerLinjeCP.getValue());
        });

        Text infoOverskrift = new Text("Informasjon:");
        infoOverskrift.setFont(Font.font(20));
        infoOverskrift.setFill(Color.WHITE);

        ta.setEditable(false);
        ta.setPrefColumnCount(10);
        ta.setPrefRowCount(12);

        lv.setEditable(false);
        lv.setPrefWidth(70);
        lv.setPrefHeight(300);
        lv.setOnMouseClicked( e-> {
            lvSelect();
        });
        lv.setOnKeyPressed(e -> {
            // Navigerer ListView med knapper
            if (!lv.getItems().isEmpty()) {
                switch (e.getCode()) {
                    case UP, DOWN:
                        lvSelect();
                        break;
                    case BACK_SPACE:
                        slettFigur();
                        break;
                }
                 // CTRL + opp- eller nedpiltast flytter på figurene
                if (e.getCode() == KeyCode.UP && e.isControlDown()) {
                    flyttBakover();
                } else if (e.getCode() == KeyCode.DOWN && e.isControlDown()) {
                    flyttFremover();
                }
            }
        });

        Text listeOverskrift = new Text("Figurliste:");
        listeOverskrift.setFont(Font.font(15));
        listeOverskrift.setFill(Color.WHITE);


        infoBox.getChildren().addAll(infoOverskrift, ta, fyllTekst, redigerFyllCP, linjeTekst, redigerLinjeCP, listeOverskrift, lv);
        return infoBox;
    }

    public Pane canvasPane() {
        // Finner verdi fra Radiobuttons og oppretter ny figur
        canvasPane.setOnMousePressed(e -> {
            FigurListe figurType = (FigurListe) tg.getSelectedToggle().getUserData();
            switch(figurType) {
                case LINE -> {
                    figur = new Linje(e);
                    canvasPane.getChildren().add(figur);
                }
                case RECTANGLE -> {
                    figur = new Rektangel(e);
                    canvasPane.getChildren().add(figur);
                }
                case CIRCLE -> {
                    figur = new Sirkel(e);
                    canvasPane.getChildren().add(figur);
                }
                case TEXT -> {
                    if (!tf.getText().trim().isEmpty()) {
                        figur = new Tekst(e);
                        canvasPane.getChildren().add(figur);
                    }
                    else {
                        ta.setText("Skriv noe først.");
                    }
                }
               case SELECT -> {

                }
            }
        });

        canvasPane.setOnMouseDragged(e -> {
            FigurListe figurType = (FigurListe) tg.getSelectedToggle().getUserData();
            switch(figurType) {
                case LINE, CIRCLE, RECTANGLE -> {
                    ((TegnFigur) figur).onDrag(e);
                }
                case TEXT -> {
                    if (!tf.getText().trim().isEmpty()) {
                        ((TegnFigur) figur).onDrag(e);
                    }
                }
            }
        });

       canvasPane.setOnMouseReleased(e -> {
           FigurListe figurType = (FigurListe) tg.getSelectedToggle().getUserData();
           // Setter opacity tilbake til 1 på alle objekter når musen slippes
           int i = shapeList.size() + 1;
           for (int j = 0; j < shapeList.size(); j++) {
               shapeList.get(j).setOpacity(1.0);
           }
           switch(figurType) {
               case LINE, CIRCLE, RECTANGLE -> {
                   // Finner figurnummer i ListView og "selekterer" den, og scroller til det nyeste objektet når det opprettes
                   shapeList.add(figur);
                   lv.getItems().addAll("Figur " + i + ": " + shapeList.get(i-1).getClass().getSimpleName());
                   lv.getSelectionModel().clearSelection();
                   lv.getSelectionModel().select(i-1);
                   lv.scrollTo(i-1);
                   // legger til infotekst fra "Informasjon" i figurInfo-lista
                   infoTekst = ta.getText();
                   figurInfo.add(infoTekst);
               }
               case TEXT -> {
                   if (!tf.getText().trim().isEmpty()) {
                       // Samme kode her som i LINE, CIRCLE og RECTANGLE
                       for (int j = 0; j < shapeList.size(); j++) {
                           shapeList.get(j).setOpacity(1);
                       }
                       shapeList.add(figur);
                       lv.getItems().addAll("Figur " + i + ": " + shapeList.get(i-1).getClass().getSimpleName());
                       lv.getSelectionModel().clearSelection();
                       lv.getSelectionModel().select(i-1);
                       lv.scrollTo(i-1);
                       infoTekst = ta.getText();
                       figurInfo.add(infoTekst);
                   }
               }
           }
       });

        return canvasPane;
    }

    public static void settInfo(String type, String fyllfarge, String linjefarge, int tykkelse, int startX, int startY, int sluttX, int sluttY, int verdi) {

        switch (type) {
            case "Linje":
                ta.setText("Type: " + type  + "\nFyllfarge: " + fyllfarge + "\nLinjefarge: " +
                        linjefarge + "\nLinjetykkelse: " + tykkelse + "\nStartX: " + startX + "px" +
                        "\nStartY: " + startY + "px" + "\nSluttX: " + sluttX + "px" + "\nSluttY: " +
                        sluttY + "px" + "\nLengde: " + verdi + "px");
                break;
            case "Tekst":
                if (!tf.getText().trim().isEmpty()) {
                    // Høyde ikke nøyaktig
                    ta.setText("Type: " + type + "\nFyllfarge: " + fyllfarge + "\nLinjefarge: " +
                            linjefarge + "\nLinjetykkelse: " + tykkelse + "\nStartX: " + startX + "px" +
                            "\nStartY: " + startY + "px" + "\nBredde: " + sluttX + "px" + "\nHøyde: " +
                            sluttY + "px" + "\nFontstørrelse: " + verdi);
                }

                break;
            case "Sirkel":
                ta.setText("Type: " + type + "\nFyllfarge: " + fyllfarge + "\nLinjefarge: " +
                        linjefarge + "\nLinjetykkelse: " + tykkelse + "\nSenterX: " + startX + "px" +
                        "\nSenterY: " + startY + "px" + "\nRadius: " + (sluttX + sluttY) + "px" + "\nDiameter: " + verdi +"px");
                break;

        }

    }
    // Legger informasjon om rektangel i Informasjon - Center viser ikke nøyaktig når flyttes
    public static void settInfo2(String type, String fyllfarge, String linjefarge, int tykkelse, int startX,
                                int startY, int senterX, int senterY, int areal) {
        ta.setText("Type: " + type + "\nFyllfarge: " + fyllfarge + "\nLinjefarge: " +
                linjefarge + "\nLinjetykkelse: " + tykkelse + "\nStartX: " + startX + "px" +
                "\nStartY: " + startY + "px" + "\nSenterX: " + senterX + "px" +
                "\nSenterY: " + senterY + "px\nAreal: " + areal + "px²");
    }

    public static void setSelected(Shape selected) {
        GUI.selected = selected;
        ta.setText(figurInfo.get(canvasPane.getChildren().indexOf(selected)));

        for (int i = 0; i < shapeList.size(); i++) {
            shapeList.get(i).setOpacity(1);
        }
        // Setter opacity på selektert figur til 0.5, for en bedre visuell opplevelse
        selected.setOpacity(0.5);
    }

    public static void lvSelect() {
        // Finner listnr til selektert figur, setter riktig informasjon i "informasjon"
        int listeNr = Integer.parseInt(lv.getSelectionModel().getSelectedItem().replaceAll("[^0-9]", ""));
        GUI.setSelected(shapeList.get(listeNr-1));
        velg.setSelected(true);
        velgErAktiv = true;

    }
    public static void slettFigur() {
        // FUNGERER KUN PÅ SISTE OBJEKT, prøvde flere metoder, men denne funker greit på siste objekt i canvasPane
        int listeNr = Integer.parseInt(lv.getSelectionModel().getSelectedItem().replaceAll("[^0-9]", ""));

        shapeList.remove(listeNr-1);
        lv.getItems().remove(listeNr-1);
        figurInfo.remove(listeNr-1);
        canvasPane.getChildren().remove(listeNr-1);

        ta.setText("Figur nr. " + (listeNr) + " er slettet.");

    }
    public void flyttBakover() {
        if (velgErAktiv) {
            // finner index til selektert figur i "shapeList", swapper verdiene med objektet bak, fjerner childrens og legger til på nytt i pane, fra shapelist.
            int index = shapeList.indexOf(selected);
            if (canvasPane().getChildren().size() > 1 && index > 0) {
                Collections.swap(shapeList, index, index - 1);
                Collections.swap(figurInfo, index, index - 1);
                canvasPane().getChildren().clear();
                canvasPane().getChildren().addAll(shapeList);
                // Fjerner alt fra listView og legger til på nytt med de nye verdiene
                lv.getItems().clear();
                for (int i = 0; i < shapeList.size(); i++) {
                    lv.getItems().addAll("Figur " + (i+1) + ": " + shapeList.get(i).getClass().getSimpleName());
                }
                // Highlighter riktig objekt i listview
                lv.getFocusModel().focus(index-1);
                lv.getSelectionModel().select(index-1);
                ta.setText("Du flyttet figur " + (index + 1) + "\nbakover.\nNy posisjon: " + (index));
            } else {
                ta.setText("Figuren kan ikke\nflyttes lengre bak.");
            }
            selected.setOpacity(0.5);
        }
    }
    public void flyttFremover() {
        if (velgErAktiv) {

            int index = shapeList.indexOf(selected);
            if (canvasPane().getChildren().size() > 1 && index < shapeList.size()-1) {
                Collections.swap(shapeList, index, index + 1);
                Collections.swap(figurInfo, index, index + 1);
                canvasPane().getChildren().clear();
                canvasPane().getChildren().addAll(shapeList);
                lv.getItems().clear();
                for (int i = 0; i < shapeList.size(); i++) {
                    lv.getItems().addAll("Figur " + (i+1) + ": " + shapeList.get(i).getClass().getSimpleName());
                }
                lv.getFocusModel().focus(index+1);
                lv.getSelectionModel().select(index+1);
                ta.setText("Du flyttet figur " + (index + 1) + "\nfremover.\nNy posisjon: " + (index+2));
            } else {
                ta.setText("Figuren kan ikke\nflyttes lengre frem.");
            }
            selected.setOpacity(0.5);
        }
    }
}
