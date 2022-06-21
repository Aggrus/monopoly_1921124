package Controller.Observer;

public interface PropertyObserver extends TileObserver 
{
    public void nofityNumBuildings(Integer value);
    public void nofityRent(Long rent);
    public void notifyHasHotel(boolean hasHotel);
}
