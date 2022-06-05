package core;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;


public class RouteCalculatorTests {

    @Test
    @DisplayName("Quickest Route algorithm")
    void When_QuickestRoute_Then_HighNodesLowDistanceIsQuickerThanFewNodesHighDistance() {
        //Arrange
        CelestialBody source =  new CelestialBody("Source", "00000", CelestialBodyTypes.PLANET),
                trueTarget  =  new CelestialBody("TrueTarget", "00000", CelestialBodyTypes.PLANET),
                decoyA =  new CelestialBody("DecoyA", "00000", CelestialBodyTypes.PLANET),
                decoyB =  new CelestialBody("DecoyB", "00000", CelestialBodyTypes.PLANET),
                decoyC =  new CelestialBody("DecoyC", "00000", CelestialBodyTypes.PLANET),
                decoyD =  new CelestialBody("DecoyD", "00000", CelestialBodyTypes.PLANET),
                decoyE =  new CelestialBody("DecoyE", "00000", CelestialBodyTypes.PLANET),
                decoyF =  new CelestialBody("DecoyF", "00000", CelestialBodyTypes.PLANET),
                decoyG =  new CelestialBody("DecoyG", "00000", CelestialBodyTypes.PLANET),
                decoyH = new CelestialBody("DecoyH", "00000", CelestialBodyTypes.PLANET);

        source.setPosition(0F, 0F);
        trueTarget.setPosition(5F, 2.2F);
        decoyA.setPosition(100F, 50F);
        decoyB.setPosition(10F, 2F);
        decoyC.setPosition(5F, 2.2F);
        decoyD.setPosition(4.4F, 2F);
        decoyE.setPosition(2F, 4F);
        decoyF.setPosition(1.2F, 3.1F);
        decoyG.setPosition(4F, 0.2F);
        decoyH.setPosition(1F, 0.5F);

        source.setRadius(1);
        trueTarget.setRadius(1);
        decoyA.setRadius(1);
        decoyB.setRadius(1);
        decoyC.setRadius(1);
        decoyD.setRadius(1);
        decoyE.setRadius(1);
        decoyF.setRadius(1);
        decoyG.setRadius(1);
        decoyH.setRadius(1);

        //Shortest should be: Source -> H -> G -> D -> True, NOT Source -> A -> True
        Hyperlane AB = new Hyperlane(decoyA, decoyB),
                AC = new Hyperlane(decoyA, decoyC),
                ATrue = new Hyperlane(decoyA, trueTarget),
                AG = new Hyperlane(decoyA, decoyG),
                GB = new Hyperlane(decoyG, decoyB),
                GD = new Hyperlane(decoyG, decoyD),
                DTrue = new Hyperlane(decoyD, trueTarget),
                GE = new Hyperlane(decoyG, decoyE),
                GH = new Hyperlane(decoyG, decoyH),
                HSource = new Hyperlane(decoyH, source),
                GC = new Hyperlane(decoyG, decoyC),
                ASourceLong = new Hyperlane(decoyA, source);

        decoyA.addHyperlane(AB);
        decoyB.addHyperlane(AB);
        decoyA.addHyperlane(AC);
        decoyC.addHyperlane(AC);
        decoyA.addHyperlane(ATrue);
        trueTarget.addHyperlane(ATrue);
        decoyA.addHyperlane(AG);
        decoyG.addHyperlane(AG);
        decoyB.addHyperlane(GB);
        decoyG.addHyperlane(GB);
        decoyD.addHyperlane(GD);
        decoyG.addHyperlane(GD);
        decoyD.addHyperlane(DTrue);
        trueTarget.addHyperlane(DTrue);
        decoyG.addHyperlane(GE);
        decoyE.addHyperlane(GE);
        decoyG.addHyperlane(GH);
        decoyH.addHyperlane(GH);
        source.addHyperlane(HSource);
        decoyH.addHyperlane(HSource);
        decoyG.addHyperlane(GC);
        decoyC.addHyperlane(GC);
        decoyA.addHyperlane(ASourceLong);
        source.addHyperlane(ASourceLong);

        //Act
        List<Hyperlane> route = RouteCalculator.quickestRouteTo(source, trueTarget);
        //Assert

        List<Hyperlane> expected = new ArrayList<>() {
            {
                add(HSource);
                add(GH);
                add(GD);
                add(DTrue);
            }
        };
        assertIterableEquals(route, expected);
    }


}
