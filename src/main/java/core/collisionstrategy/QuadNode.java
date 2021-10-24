package core.collisionstrategy;

import core.CelestialBody;

public class QuadNode {
    QuadRectangle r;
    CelestialBody element;

    public QuadNode(QuadRectangle r, CelestialBody element) {
        this.r = r;
        this.element = element;
    }

//    @Override
//    public String toString() {
//        return r.toString();
//    }
}