package br.dayanelima.voudebike;

public interface IAppNavigation {

    void openBikesList();

    void openBikeDetails(int bikeID);

    void openClientsList();

    void openClientDetails(int id);

    void openBookingsList();
}
