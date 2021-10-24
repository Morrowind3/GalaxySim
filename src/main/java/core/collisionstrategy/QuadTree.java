package core.collisionstrategy;

import core.CelestialBody;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class QuadTree implements Cloneable {
    private final int level;

    //config
    private static final int MAX_ENTITIES = 4;
    private static final int MAX_DEPTH = 10;

    private final QuadRectangle boundary;
    private ArrayList<Hitbox> entities = new ArrayList<>();

    private QuadTree northWest;
    private QuadTree northEast;
    private QuadTree southWest;
    private QuadTree southEast;

    public QuadTree(QuadRectangle boundary){
        this.boundary = boundary;
        level = 0;
    }

    private QuadTree(QuadRectangle boundary, int level){
        this.boundary = boundary;
        this.level = level;
    }

    private void insertAll(List<CelestialBody> entities){
        for(Hitbox entity: entities){
            insert(entity);
        }
    }

    public QuadRectangle getBoundary(){
        return boundary;
    }

    public List<QuadRectangle> getAllQuads(){
        List<QuadRectangle> quads = new ArrayList<>();
        quads.add(boundary);
        if(northWest == null) return quads;
        quads.addAll(northWest.getAllQuads());
        quads.addAll(northEast.getAllQuads());
        quads.addAll(southWest.getAllQuads());
        quads.addAll(southEast.getAllQuads());
        return quads;

    }

    public void rebuildTree(List<CelestialBody> entities){
        northWest = null;
        northEast = null;
        southEast = null;
        southWest = null;
        insertAll(entities);
    }

    public boolean insert(Hitbox entity){
        // Ignore objects that do not belong in this quad tree
        if (!boundary.contains(entity)) {
            return false;
        }

        // If there is space in this quad tree and if doesn't have subdivisions, add the object here
        if(northWest == null){
            if (entities.size() < MAX_ENTITIES)
            {
                entities.add(entity);
                return true;
            } else if(level < MAX_DEPTH){
                subdivide();
            }
        }
        if(northWest.insert(entity)) return true;
        if(northEast.insert(entity)) return true;
        if(southEast.insert(entity)) return true;
        if(southWest.insert(entity)) return true;


        return false;
    }

    public void subdivide(){
        northWest = new QuadTree(new QuadRectangle(boundary.getPositionX(), boundary.getPositionY(), boundary.getWidth()/2, boundary.getHeight()/2), level+1);
        northEast = new QuadTree(new QuadRectangle(boundary.getPositionX() + boundary.getWidth()/2f, boundary.getPositionY(), boundary.getWidth()/2, boundary.getHeight()/2), level+1);
        southWest = new QuadTree(new QuadRectangle(boundary.getPositionX(), boundary.getPositionY() + boundary.getHeight()/2f, boundary.getWidth()/2, boundary.getHeight()/2), level+1);
        southEast = new QuadTree(new QuadRectangle(boundary.getPositionX() + boundary.getWidth()/2f, boundary.getPositionY() + boundary.getHeight()/2f, boundary.getWidth()/2, boundary.getHeight()/2), level+1);
    }

    public List<QuadRectangle> getIntersectedQuads(Hitbox entity){
        List<QuadRectangle> intersected = new ArrayList<>();

        if(northWest != null){
            intersected.addAll(northWest.getIntersectedQuads(entity));
        }
        if(northEast != null){
            intersected.addAll(northEast.getIntersectedQuads(entity));
        }
        if(southWest != null){
            intersected.addAll(southWest.getIntersectedQuads(entity));
        }
        if(southEast != null){
            intersected.addAll(southEast.getIntersectedQuads(entity));
        }

        //only add if there are no further children to ensure smallest possible range
        if(northWest == null && boundary.contains(entity)){
            intersected.add(boundary);
        }
        return intersected;
    }

    public List<Hitbox> getQuadEntities(QuadRectangle range){
        // Prepare an array of results
        List<Hitbox> inRange = new ArrayList<>();

        // Automatically abort if the range does not intersect this quad
        if (!range.contains(boundary)){
            return inRange; // empty list
        }

        // Check objects at this quad level
        for(Hitbox entity : entities){
                if (range.contains(entity)){
                    inRange.add(entity);
                }
        }
        // Terminate here, if there are no children
        if (northWest == null){
            return inRange;
        }

        // Otherwise, add the points from the children
        inRange.addAll(northWest.getQuadEntities(range));
        inRange.addAll(northEast.getQuadEntities(range));
        inRange.addAll(southWest.getQuadEntities(range));
        inRange.addAll(southEast.getQuadEntities(range));

        return inRange;
    }

    @Override
    public QuadTree clone(){
        QuadTree clone = new QuadTree(boundary);
        clone.entities = entities;

        if(northWest != null){
            clone.northWest = northWest.clone();
            clone.northEast = northEast.clone();
            clone.southWest = southWest.clone();
            clone.southEast = southEast.clone();
        }
        return clone;
    }
}
