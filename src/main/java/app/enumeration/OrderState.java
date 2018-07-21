package app.enumeration;

public enum OrderState {
    WAITING("en attente"),
    ACCEPTED("acceptée"),
    REJECTED("rejectée"),
    CANCELED("annulée");

    private String state;

    OrderState(String s){ state = s;}
}
