package com.example.paintoblig.Figurer;

import com.example.paintoblig.GUI;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;

import static com.example.paintoblig.GUI.velgErAktiv;


public class Rektangel extends Rectangle implements TegnFigur{

    int originalX;
    int originalY;

    public Rektangel(MouseEvent e) {
        super(e.getX(), e.getY(), 0, 0);
        setStroke(GUI.linjeCP.getValue());
        setFill(GUI.fyllCP.getValue());
        setStrokeWidth((GUI.slider.getValue()));

        originalX = (int)e.getX();
        originalY = (int)e.getY();

        // Objektet lytter etter klikk, blir satt som "selected" når den klikkes på
        // "selected" blir altså det øverste dersom man klikker på flere på én gang
        setOnMousePressed(this::select);
        // Flytter på objektet
        setOnMouseDragged(event -> {
            if (velgErAktiv) {
                setX(event.getX() - (getWidth() / 2));
                setY(event.getY() - (getHeight() / 2));
                settInfo();
            }
        });
    }

    @Override
    public void onDrag(MouseEvent e) {
        // Forandrer størrelse på figuren når den tegnes
        double avstandX = e.getX() - originalX;
        double avstandY = e.getY() - originalY;

        if (avstandX > 0) {
            setWidth(avstandX);
            setX(originalX);
        }
        else {
            setX(e.getX());
            setWidth(-avstandX);
        }
        if (avstandY > 0) {
            setHeight(avstandY);
            setY(originalY);
        }
        else {
            setY(e.getY());
            setHeight(-avstandY);
        }
        settInfo();
    }
    private void settInfo() {
        // Sender informasjon til "settInfo2" i GUI
        int senterX = (int)getWidth() / 2;
        int senterY = (int)getHeight() / 2;
        int areal = (int)getHeight()*(int)getWidth();
        GUI.settInfo2("Rektangel", String.valueOf(getFill()), String.valueOf(getStroke()), (int)getStrokeWidth(),
                (int)getX(), (int)getY(), senterX, senterY, areal);
    }
}
