package mancala.common;

public enum MancalaGameMode {
    Classic("C"),
    Kalaha("K"),
    ;

    public final String abbreviation;
    MancalaGameMode(String abbreviation) {
        this.abbreviation = abbreviation;
    }

}
