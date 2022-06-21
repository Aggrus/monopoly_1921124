package Controller.Observer;

import enums.PlayerColorEnum;
import enums.TileColorEnum;

public interface TileObserver extends Observer 
{
    public void notifyBoardPosition(Integer boardPosition);
    public void notifyCanPurchase(boolean canPurchase);
    public void notifyGroup(TileColorEnum group);
    public void notifyOwner(PlayerColorEnum color);
    public void nofityValue(Long value);
}
