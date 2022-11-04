package com.example.paintoblig.Figurer;

import com.example.paintoblig.GUI;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.shape.Shape;

import static com.example.paintoblig.GUI.*;

// Oppretter interface for å forsikre at samme metode blir brukt i alle objekter (onDrag og select);
public interface TegnFigur {
    void onDrag(MouseEvent e);

    default void select(MouseEvent e) {

        // Forsikrer at "Velg figur" er valgt
        if (velgErAktiv) {
            GUI.setSelected((Shape) this);
            // Setter opacity til alle andre figurer til 1
            for (int i = 0; i < shapeList.size(); i++) {
                shapeList.get(i).setOpacity(1);
            }
            ((Shape) this).setOpacity(0.5);
            lv.getSelectionModel().select(shapeList.indexOf(this));
            lv.scrollTo(shapeList.indexOf(this));

            // Prøvde på en scrollefunksjon, fungerer ikke optimalt
            ((Shape) this).setOnScroll((ScrollEvent event) -> {
                double zoom = 1.05;
                double deltaY = event.getDeltaY();
                if (deltaY < 0) {
                    zoom = 2.0 - zoom;
                }
                ((Shape) this).setScaleX(((Shape) this).getScaleX() * zoom);
                ((Shape) this).setScaleY(((Shape) this).getScaleY() * zoom);
            });
        }
    }

}
