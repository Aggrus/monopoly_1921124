package Controller.Observer;

public interface Observer {
    public void notifyNumPlayers(Integer numPlayers);
    public void notifyTurn(Integer turn);
}