package action;

import main.Database;

public abstract class Action {
    protected String message;
    private int actionId;
    private String actionType;
    /**
     * Pentru interogari type-ul este tipul obiectului de interogat
     */
    private String type;

    public Action(final int actionId, final String actionType, final String type) {
        this.actionId = actionId;
        this.actionType = actionType;
        this.type = type;
    }

    public Action() {

    }

    /**
     * Intoarce ID-ul actiunii
     */
    public int getActionId() {
        return actionId;
    }

    /**
     * Intoarce tipul actiunii:command, recommendation sau query
     */
    public String getActionType() {
        return actionType;
    }

    /**
     * Intoarce tipul specific actiunii: most_viewed, rating etc.
     */
    public String getType() {
        return type;
    }

    /**
     * Intoarce mesajul de iesire al actiunii
     */
    public String getMessage() {
        return message;
    }

    /**
     * Executa actiunea aferenta obiectului
     */
    public abstract void execute(Database db);
}
