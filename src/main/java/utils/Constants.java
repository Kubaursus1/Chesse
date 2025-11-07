package utils;

import logic.Player;

import java.util.Map;

public class Constants {
    public Map<Player.COLOR, Map<String, String>> piecesMap = Map.of(Player.COLOR.BLACK, Map.of(
            "knight", "",
            "queen", "",
            "king", "",
            "bishop", "",
            "pawn", ""
            ),
            Player.COLOR.BLACK, Map.of());
}
