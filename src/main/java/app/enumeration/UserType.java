package app.enumeration;

public enum UserType {
    ACCOUNT("ACCOUNT"),
    MEMBER("MEMBER"),
    CUSTOMER("CUSTOMER"),
    APPLICATION("APPLICATION");

    private String type;

    UserType(String t){ type = t;}
};
