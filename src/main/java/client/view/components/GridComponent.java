package client.view.components;

import client.Mediator;
import client.SimulationController;
import core.collisionstrategy.QuadRectangle;
import core.collisionstrategy.QuadTree;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class GridComponent implements Drawable, Component{

    private SimulationController controller;
    private QuadTree tree;
    private Color colour = Color.SADDLEBROWN;

    public GridComponent(QuadTree tree){
        this.tree = tree;
    }

    @Override
    public void setMediator(Mediator mediator) {
        this.controller = (SimulationController) mediator;
    }

    @Override
    public String getName() {
        return "GridCell";
    }

    @Override
    public void draw(GraphicsContext context) {
        context.setStroke(colour);
        context.setFill(Color.TRANSPARENT);
        context.setLineWidth(3);
        for(QuadRectangle cell : tree.getAllQuads()){
            context.fillRect(cell.getPositionX(), cell.getPositionY(), cell.getWidth(), cell.getHeight());

            context.strokeRect(cell.getPositionX(), cell.getPositionY(), cell.getWidth(), cell.getHeight());
        }
    }
    @Override
    public boolean modelAssociated(Object model){
        return(model == this.tree);
    }

    @Override
    public void setColourOverride(String colour) {
        this.colour = colour == null ? Color.SADDLEBROWN : Color.web(colour);
    }
}
