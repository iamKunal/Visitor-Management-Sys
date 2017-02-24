package visMan.utils;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.*;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class Desaturation {
    public static Image desaturate (Image image){

        ImageView original = new ImageView(image);

        ImageView desaturated = new ImageView(image);
        ColorAdjust desaturate = new ColorAdjust();
        desaturate.setSaturation(-1);
        desaturated.setEffect(desaturate);
        return desaturated.getImage();
    }
}