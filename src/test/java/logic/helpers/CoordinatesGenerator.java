package logic.helpers;

import logic.Board;
import utils.Coordinates;
import utils.Directions;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CoordinatesGenerator {
    public static Set<Coordinates> generate(Coordinates coordinates, Directions directions) {
        Stream<Integer> stream = Stream.iterate(0, i -> i < Board.BOARD_SIZE , i -> i + 1);
        switch (directions) {
            case HORIZONTAL -> {
                return stream.map(i -> new Coordinates(i, coordinates.row())).collect(Collectors.toSet());
            }
            case VERTICAL -> {
                return stream.map(i -> new Coordinates(coordinates.column(), i)).collect(Collectors.toSet());
            }
            case DIAGONAL -> {
                return Stream.concat(stream.map(i -> new Coordinates(i, i)),stream.map(i -> new Coordinates(-i, -i)))
                        . collect(Collectors.toSet());
            }
        }
        throw new IllegalArgumentException("direction does not exist");
    };
}
