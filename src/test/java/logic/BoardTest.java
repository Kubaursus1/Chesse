package logic;

import logic.helpers.CoordinatesGenerator;
import logic.pieces.Pawn;
import logic.pieces.Piece;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import utils.Coordinates;
import utils.Directions;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;




class BoardTest {
    static Stream<Arguments> generate() {
        List<EnumSet<Directions>> directionSets = List.of(
                EnumSet.noneOf(Directions.class),
                EnumSet.of(Directions.DIAGONAL),
                EnumSet.of(Directions.HORIZONTAL),
                EnumSet.of(Directions.VERTICAL),
                EnumSet.of(Directions.DIAGONAL, Directions.HORIZONTAL),
                EnumSet.of(Directions.DIAGONAL, Directions.VERTICAL),
                EnumSet.of(Directions.HORIZONTAL, Directions.VERTICAL),
                EnumSet.of(Directions.DIAGONAL, Directions.HORIZONTAL, Directions.VERTICAL)
        );

        return IntStream.rangeClosed(0, 7)
                .boxed()
                .flatMap(x ->
                        IntStream.rangeClosed(0, 7)
                                .boxed()
                                .flatMap(y ->
                                        Stream.of(true, false)
                                                .flatMap(b ->
                                                        directionSets.stream()
                                                                .map(set ->
                                                                        Arguments.of(
                                                                                new Coordinates(x, y),
                                                                                b,
                                                                                set
                                                                        )
                                                                )
                                                )
                                )
                );
    }

    private static Stream<Arguments> getSquaresInRangeReturnsEmptyListWhenGivenZeroRange() {
       return generate().limit(100);
    }

    private static Stream<Arguments> getSquaresInRangeReturnsEmptyListOnEmptyBoardForCapture() {
        return IntStream.rangeClosed(0, 7)
                .boxed()
                .flatMap(range -> generate().map(s -> Arguments.of(s.get()[0],s.get()[1],s.get()[2],range)))
                .filter(a -> (boolean)a.get()[1]);
    }

    private static Stream<Arguments> getSquaresReturnCorrectResultsForMaxRange() {
        return IntStream.rangeClosed(0, 7)
                .boxed()
                .flatMap(x ->
                        IntStream.rangeClosed(0, 7)
                                .boxed()
                                .map(y -> new Coordinates(x,y))).flatMap(c -> Stream.of(Directions.VERTICAL,Directions.HORIZONTAL)
                        .map(d -> Arguments.of(c,d)));
    }
    // n! / (k!( (n-k)!
    // n = 8
//    (n 1) +  (n 2) + n(3) + n(4) + ... + n(n)

    public static Stream<Arguments> getSquaresReturnsAllOtherPlayerPiecesThatNeighbourCenter() {
       return Stream.of(Set.of(new Coordinates(3,4)),
                       Set.of(new Coordinates(4,3))
               )
               .map(s -> Arguments.of(s));
    }

    @ParameterizedTest
    @MethodSource
    public void getSquaresInRangeReturnsEmptyListOnEmptyBoardForCapture(Coordinates coord,boolean isCapture,
                                                                 EnumSet<Directions> directions,int maxRange)
    {
        var sut = new Board(Collections.emptyList());
        var player = new Player(Player.COLOR.BLACK);
        var piece = new Pawn(player,coord);

        Set<Coordinates> actual = sut.getSquaresInRange(piece, maxRange, directions, isCapture);

        assertTrue(actual.isEmpty());
    }

    @ParameterizedTest
    @MethodSource
    void getSquaresInRangeReturnsEmptyListWhenGivenZeroRange(Coordinates coord,boolean isCapture,
                                                             EnumSet<Directions> directions) {
        var sut = new Board(Collections.emptyList());

        var player = new Player(Player.COLOR.BLACK);
//        var coord = new Coordinates(0,0);
        var piece = new Pawn(player,coord);

        Set<Coordinates> actual = sut.getSquaresInRange(piece, 0, directions, isCapture);

        assertTrue(actual.isEmpty());

    }
    @Test
    void getSquaresReturnsCorrectResultsGivenRangeOfOne()
    {
        var sut = new Board(Collections.emptyList());

        var player = new Player(Player.COLOR.BLACK);
        var coord = new Coordinates(4,4);
        var piece = new Pawn(player,coord);
        EnumSet<Directions> directions = EnumSet.of(Directions.VERTICAL);
        boolean isCapture = false;

        Set<Coordinates> expected = List.of(
                new Coordinates(4,5),
                new Coordinates(4,3))

                .stream().collect(Collectors.toSet());

        Set<Coordinates> actual = sut.getSquaresInRange(piece, 1, directions, isCapture);

        assertEquals(expected,actual);
    }
    @Test
    void getSquaresReturnsCorrectResultsInCorner()
    {
        var sut = new Board(Collections.emptyList());

        var player = new Player(Player.COLOR.BLACK);
        var coord = new Coordinates(0,0);
        var piece = new Pawn(player,coord);
        EnumSet<Directions> directions = EnumSet.of(Directions.VERTICAL,Directions.HORIZONTAL,Directions.DIAGONAL );
        boolean isCapture = false;

        Set<Coordinates> expected = List.of(
                        new Coordinates(1,0),
                        new Coordinates(1,1),
                        new Coordinates(0,1)
                ).stream().collect(Collectors.toSet());

        Set<Coordinates> actual = sut.getSquaresInRange(piece, 1, directions, isCapture);

        assertTrue(expected.equals(actual));
    }

    @ParameterizedTest
    @MethodSource
    void getSquaresReturnCorrectResultsForMaxRange(Coordinates coordinates,Directions directions)
    {
        var sut = new Board(Collections.emptyList());

        var player = new Player(Player.COLOR.BLACK);
        var piece = new Pawn(player,coordinates);
        var directionsSet = EnumSet.of(directions);

        Set<Coordinates> actual = sut.getSquaresInRange(piece, Board.BOARD_SIZE, directionsSet, false);
        Set<Coordinates> res = CoordinatesGenerator.generate(coordinates, directions);
        res.remove(coordinates);
        Set<Coordinates> expected = res;
        assertEquals(expected, actual);
    }


    @ParameterizedTest
    @MethodSource
    void getSquaresReturnsAllOtherPlayerPiecesThatNeighbourCenter(Set<Coordinates> coordinates)
    {

        var playerOther = new Player(Player.COLOR.WHITE);
        var piecesOther = coordinates.stream().map(c -> (Piece)new Pawn(playerOther,c)).toList();
        var sut = new Board(piecesOther);

        var player = new Player(Player.COLOR.BLACK);
        var piece = new Pawn(player,new Coordinates(3,3));
        var directionsSet = EnumSet.of(Directions.DIAGONAL,Directions.HORIZONTAL,Directions.VERTICAL);

        Set<Coordinates> actual = sut.getSquaresInRange(piece, 1, directionsSet, true);
        Set<Coordinates> expected = coordinates;

        assertEquals(expected, actual);
    }
}