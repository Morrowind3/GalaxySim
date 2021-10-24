package client.view.components;

import client.Mediator;
import client.SimulationController;
import core.collisionstrategy.QuadRectangle;
import core.collisionstrategy.QuadTree;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class GridComponent implements Drawable, Component{

    private SimulationController controller;
    private QuadTree tree;

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
        context.setStroke(Color.SADDLEBROWN);
        context.setFill(Color.TRANSPARENT);
        context.setLineWidth(3);
        for(QuadRectangle cell : tree.getAllQuads()){
            context.fillRect(cell.getPositionX(), cell.getPositionY(), cell.getWidth(), cell.getHeight());

            context.strokeRect(cell.getPositionX(), cell.getPositionY(), cell.getWidth(), cell.getHeight());
        }
    }
}
