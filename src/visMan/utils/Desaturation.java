package visMan.utils;

import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.*;

public class Desaturation {
    public static Image desaturate (Image image){

//        ImageView original = new ImageView(image);

        ImageView desaturated = new ImageView(image);
        ColorAdjust desaturate = new ColorAdjust();
        desaturate.setSaturation(-1);
        desaturated.setEffect(desaturate);
        return desaturated.getImage();
    }
}