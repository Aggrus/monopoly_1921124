package Controller;

import model.Game;

import View.MainFrame;

public class MenuController {
    private static MenuController menu_controller = null;

    private MenuController() {}

    public static MenuController getInstance() {
        if (menu_controller == null) {
            menu_controller = new MenuController();
        } 
        return menu_controller;
    }


    public void set_num_players(int num_players) {
        Game.setNumPlayers(num_players);
        Game.getInstance().add(MainFrame.getInstance());
    }
}