package app.enumeration;

public enum OrderState {
    WAITING("en attente"),
    ACCEPTED("acceptée"),
    REJECTED("rejectée"),
    CANCELED("annulée");

    public String state;

    OrderState(String s){ state = s;}
}
