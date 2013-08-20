package clustering;

public class Point {
	double x;
	double y;
	
	Point(double x, double y){
		this.x = x;
		this.y = y;
	}
	
	public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}
	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
	}
	
	public double getDistance(Point a) {
		double result = 0.0;
		
		result += Math.pow(this.getX() - a.getX(), 2);
		result += Math.pow(this.getY() - a.getY(), 2);
		
		result = Math.abs(Math.sqrt(result));
		
		return result;
	}
}
