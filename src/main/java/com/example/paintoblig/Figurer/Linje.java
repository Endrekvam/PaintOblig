package com.example.paintoblig.Figurer;

import com.example.paintoblig.GUI;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Line;

import static com.example.paintoblig.GUI.velgErAktiv;

public class Linje extends Line implements TegnFigur {

    int startX;
    int startY;

    public Linje(MouseEvent e) {
        super(e.getX(), e.getY(), e.getX(), e.getY());
        setStroke(GUI.linjeCP.getValue());
        setStrokeWidth((GUI.slider.getValue()));

        startX = (int)e.getX();
        startY = (int)e.getY();

        // Objektet lytter etter klikk, blir satt som "selected" når den klikkes på
        setOnMousePressed(this::select);
        setOnMouseDragged(event -> {
            if (velgErAktiv) {
                double bredde;
                double hoyde;
                bredde = getEndX()-getStartX();
                hoyde = getEndY()-getStartY();
                setStartX(event.getX() - bredde / 2);
                setStartY(event.getY() - hoyde / 2);
                setEndX(getStartX() + bredde);
                setEndY(getStartY() + hoyde);
                settInfo();
            }
        });
    }
    @Override
    public void onDrag(MouseEvent e) {
       setEndX(e.getX());
       setEndY(e.getY());
       // Setter informasjon når figuren dragges
       settInfo();
    }
    private void settInfo() {
        GUI.settInfo("Linje", "Ingen", String.valueOf(getStroke()), (int)getStrokeWidth(), (int)getStartX(), (int)getStartY(), (int)getEndX(), (int)getEndY(),
                (int)Math.sqrt((getEndY() - getStartY()) * (getEndY() - getStartY()) + (getEndX() - getStartX()) * (getEndX() - getStartX())));
    }
}
