package Controller.Observer;

public interface GameObserver extends Observer{
    public void notifyNumPlayers(Integer numPlayers);
    public void notifyTurn(Integer turn);
    public void notifyHasBought(boolean hasBought);
}
