package Controller;

import java.util.ArrayList;
import java.util.List;

import Controller.Observer.Observer;
import model.ApplyRules;

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
        Integer cardId = ApplyRules.applyTileRuleToPlayerById(currentPlayer);
        moveResult.add(cardId);
        moveResult.addAll(dice);
        System.out.println("\npositions: " + currentPlayer + "\n");

        return moveResult;
    }

    public Integer getColorIndexByPlayerTurn(Integer playerTurn)
    {
        return ApplyRules.getColorIndexByPlayerTurn(playerTurn);
    }

    public void nextTurn(Observer o)
    {
        ApplyRules.nextTurn(o);
    }

}
