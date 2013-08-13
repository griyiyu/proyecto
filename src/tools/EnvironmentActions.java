package tools;

public enum EnvironmentActions {
	NO_ACTION(0), ADD(1), PAINT(2), DRAG(3), RUN(4), CLEAN(5);

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
		case 3:
			returnedValue = EnvironmentActions.DRAG;
			break;			
		case 4:
			returnedValue = EnvironmentActions.RUN;
			break;
		case 5:
			returnedValue = EnvironmentActions.CLEAN;
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
