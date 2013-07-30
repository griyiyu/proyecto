package tools;

public enum EnvironmentActions {
	NO_ACTION(0), ADD(1), PAINT(2);

	private int code;

	public static EnvironmentActions getState(int value) {
		EnvironmentActions returnedValue = ADD;
		switch (value) {
		case 1: 
			returnedValue = EnvironmentActions.ADD;
			break;
		case 2:
			returnedValue = EnvironmentActions.PAINT;
			break;
		default:
			returnedValue = EnvironmentActions.NO_ACTION;
			break;
		};	
		return returnedValue;
	}
	
	private EnvironmentActions(int a) {
		code = a;
	}

	public Integer getCode() {
		return code;
	}
}
