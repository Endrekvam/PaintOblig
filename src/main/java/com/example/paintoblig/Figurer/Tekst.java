package com.example.paintoblig.Figurer;

import com.example.paintoblig.GUI;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import static com.example.paintoblig.GUI.ta;
import static com.example.paintoblig.GUI.velgErAktiv;

public class Tekst extends Text implements TegnFigur{

    int startX;
    int startY;
    public Tekst(MouseEvent e) {
        // Sjekker om TextArea er tom
        super(e.getX(), e.getY(), GUI.tf.getText());
        if (GUI.tf.getText().trim().isEmpty()) {
            ta.setText("Skriv noe først.");
        }
        else {
            setFill(GUI.fyllCP.getValue());
            setStroke(GUI.linjeCP.getValue());
            setStrokeWidth((GUI.slider.getValue()));

            startX = (int) e.getX();
            startY = (int) e.getY();
        }

        // Objektet lytter etter klikk, blir satt som "selected" når den klikkes på
        setOnMousePressed(this::select);
        setOnMouseDragged(event -> {
            if (velgErAktiv) {
                setX(event.getX() - (this.getBoundsInLocal().getWidth() / 2));
                setY(event.getY() + (this.getBoundsInLocal().getHeight() / 4));
                settInfo();
            }
        });
    }

    @Override
    public void onDrag(MouseEvent e) {
        setFont(Font.font((e.getX() - getX())/3));

        settInfo();
    }
    private void settInfo() {
        Font font = getFont();
        // Høyde ikke nøyaktig
        GUI.settInfo("Tekst", String.valueOf(getFill()),
                String.valueOf(getStroke()), (int)getStrokeWidth(), (int)getX(), (int)getY(),
                (int)this.getBoundsInLocal().getWidth(), (int)getBoundsInLocal().getHeight(), (int)font.getSize());
    }
}
