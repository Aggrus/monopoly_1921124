package Controller;

import java.util.ArrayList;
import java.util.List;

import Controller.Observer.Observer;
import enums.BuildingEnum;
import model.ApplyRules;
import model.Game;

public class GameController {
    private static GameController game_controller = null;

    private GameController() {}

    public static GameController getInstance() {
        if (game_controller == null) {
            game_controller = new GameController();
        } 
        return game_controller;
    }

    public void createPlayers(Observer o)
    {
        ApplyRules.createPlayers(o);
    }

    public List<Integer> moveAction(Integer currentPlayer, Observer o)
    {
        List<Integer> moveResult = new ArrayList<Integer>();
        System.out.printf("Clicou em rolar dados\n");
        List<Integer> dice = ApplyRules.moveRollDice(currentPlayer, o);
        System.out.printf("Dados: ");
        dice.stream().forEach(dado -> System.out.printf("%d, ", dado));
        Integer cardId = ApplyRules.applyTileRuleToPlayerById(currentPlayer, o);
        moveResult.add(cardId);
        moveResult.addAll(dice);
        System.out.println("\npositions: " + currentPlayer + "\n");

        return moveResult;
    }

    public boolean canBuyTile(final Integer boardPosition)
    {
        return ApplyRules.canBuyTile(boardPosition);
    }

    public boolean canBuyHouse(final Integer playerId, final Integer boardPosition)
    {
        return ApplyRules.canBuyHouse(playerId, boardPosition);
    }

    public boolean canBuyHotel(final Integer playerId, final Integer boardPosition)
    {
        return ApplyRules.canBuyHotel(playerId, boardPosition);
    }

    public Integer getColorIndexByPlayerTurn(Integer playerTurn)
    {
        return ApplyRules.getColorIndexByPlayerTurn(playerTurn);
    }

    public void nextTurn(Observer o)
    {
        ApplyRules.nextTurn(o);
    }

    public void resetHasBought(Observer o)
    {
        Game.setHasBought(false);
        Game.getInstance().update(o);
    }

    public String buyHotel(Integer playerId, Integer boardPosition, Observer o)
    {
        return ApplyRules.buyBuilding(playerId, boardPosition, BuildingEnum.HOTEL, o);
    }

    public String buyHouse(Integer playerId, Integer boardPosition, Observer o)
    {
        return ApplyRules.buyBuilding(playerId, boardPosition, BuildingEnum.HOUSE, o);
    }

    public String buyTile(Integer playerId, Observer o)
    {
        return ApplyRules.buyTile(playerId, o);
    }

}
