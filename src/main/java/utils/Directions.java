package utils;

import java.util.Set;

public enum Directions {

    HORIZONTAL(Set.of(new Coordinates(1,0),new Coordinates(-1, 0))),
    VERTICAL(Set.of(new  Coordinates(0,1),new Coordinates(0,-1))),
    DIAGONAL(Set.of(
            new Coordinates(1,1),
            new Coordinates(-1,-1),
            new Coordinates(1,-1),
            new Coordinates(-1,1)));

    Directions(Set<Coordinates> vectors) {
        this.vectors = vectors;
    }

    private Set<Coordinates> vectors;

    public Set<Coordinates> getVectors() {
        return vectors;
    }

    //    veritcal -> (0,1) (0,-1)
//    veritcal -> (1,0) (-1,1)

}
