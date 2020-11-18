package action;

import main.Database;

public abstract class Action {
	protected String message;
	private int actionId;
	private String actionType;
	/**
     * For queries type is object_type
     */
	private String type;
	
	public Action(int actionId, String actionType, String type) {
		super();
		this.actionId = actionId;
		this.actionType = actionType;
		this.type = type;
	}

	public int getActionId() {
		return actionId;
	}

	public void setActionId(int actionId) {
		this.actionId = actionId;
	}

	public String getActionType() {
		return actionType;
	}

	public void setActionType(String actionType) {
		this.actionType = actionType;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	/**
     * Executa actiunea aferenta obiectului
     */
	public abstract void execute(Database db);
}