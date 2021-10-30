package client.view.components;

import javafx.scene.canvas.GraphicsContext;

public interface Drawable {
    void draw(GraphicsContext context);
    boolean modelAssociated(Object model);
    void setColourOverride(String colour);
}
