package NavieBayes;
public class Registro {
	private String x1;
	private String x2;
	private String x3;
	private String x4;
	private String y; 
	
	
	public Registro(String x1, String x2, String x3, String x4, String y) {
		super();
		this.x1 = x1;
		this.x2 = x2;
		this.x3 = x3;
		this.x4 = x4;
		this.y = y;
	}

	public String getX1() {
		return x1;
	}

	public void setX1(String x1) {
		this.x1 = x1;
	}

	public String getX2() {
		return x2;
	}

	public void setX2(String x2) {
		this.x2 = x2;
	}

	public String getX3() {
		return x3;
	}

	public void setX3(String x3) {
		this.x3 = x3;
	}

	public String getX4() {
		return x4;
	}

	public void setX4(String x4) {
		this.x4 = x4;
	}

	public String getY() {
		return y;
	}

	public void setY(String y) {
		this.y = y;
	}
	public String getVariable(String variable) {
		switch (variable) {
		case "x1": return getX1();
		case "x2": return getX2();
		case "x3": return getX3();
		case "x4": return getX4();
		case "y": return getY();
		default:
			throw new IllegalArgumentException("Valor no existente: " + variable);
		}
	}

	@Override
	public String toString() {
		String info = "x1="+x1+
				" | x2="+x2+
				" | x3="+x3+
				" | x4="+x4+
				" | y="+y;
		return info;
	}
}
