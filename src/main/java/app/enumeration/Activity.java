package app.enumeration;

public enum Activity {
    WAITING     ("WAITING"),
    ACTIVE      ("ACTIVE"),
    INACTIVE    ("INACTIVE"),
    BLOCKED     ("BLOCKED");

    public String activity;

    Activity(String s){ activity = s;}
}
