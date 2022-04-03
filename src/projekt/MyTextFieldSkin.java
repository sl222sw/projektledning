package projekt;

import javafx.animation.FadeTransition;
import javafx.scene.control.TextField;
import javafx.scene.control.skin.TextFieldSkin;
import javafx.util.Duration;

public class MyTextFieldSkin extends TextFieldSkin {
    public MyTextFieldSkin(TextField control) {
        super(control);

        final FadeTransition fadeIn = new FadeTransition(Duration.millis(200));
        fadeIn.setNode(control);
        fadeIn.setToValue(1);
        control.setOnMouseEntered(e -> fadeIn.playFromStart());

        final FadeTransition fadeOut = new FadeTransition(Duration.millis(200));
        fadeOut.setNode(control);
        fadeOut.setToValue(0.5);
        control.setOnMouseExited(e -> fadeOut.playFromStart());

        control.setOpacity(0.5);
    }
}
