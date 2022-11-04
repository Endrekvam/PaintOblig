package com.example.paintoblig.Figurer;

import com.example.paintoblig.GUI;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;

import static com.example.paintoblig.GUI.velgErAktiv;


public class Sirkel extends Circle implements TegnFigur{

    double centerX;
    double centerY;

    public Sirkel(MouseEvent e) {
        super(e.getX(), e.getY(), 10);
        setStroke(GUI.linjeCP.getValue());
        setFill(GUI.fyllCP.getValue());
        setStrokeWidth((GUI.slider.getValue()));

        centerX = e.getX();
        centerY = e.getY();

        // Objektet lytter etter klikk, blir satt som "selected" når den klikkes på
        setOnMousePressed(this::select);
        setOnMouseDragged(event -> {
            if (velgErAktiv) {
                setCenterX(event.getX());
                setCenterY(event.getY());
                settInfo();
            }
        });
    }

    @Override
    public void onDrag(MouseEvent e) {
        // Setter radius
        setRadius(Math.sqrt((e.getX() - getCenterX()) * (e.getX() - getCenterX())+ (e.getY() - getCenterY()) * (e.getY() - getCenterY())));
        // sender info til GUI
        settInfo();
    }
    private void settInfo() {
        GUI.settInfo("Sirkel", String.valueOf(getFill()), String.valueOf(getStroke()), (int)getStrokeWidth(),
                (int)getCenterX(), (int)getCenterY(), (int)getRadius()/2,
                (int)getRadius()/2, (int)getRadius()*2);
    }

}
