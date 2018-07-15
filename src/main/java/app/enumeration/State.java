package app.enumeration;

public enum State {
    WAITING("en attente"),
    INPROGRESS("en progression"),
    CARRIEDOUT("terminée"),
    CANCELED("annulé(e)");

    private String state;

    State(String s){ state = s;}
}
