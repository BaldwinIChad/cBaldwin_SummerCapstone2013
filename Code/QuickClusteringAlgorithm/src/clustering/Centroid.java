package clustering;

import java.util.ArrayList;

public class Centroid extends Point {
	ArrayList<Point> grouping = new ArrayList<Point>();
	Centroid(double x, double y) {
		super(x, y);
	}
	
	public void addPoint(Point p) {
		grouping.add(p);
		reposition();
	}
	
	public void reposition(){
		double[] coords = getAverageCoordinates();
		setX(coords[0]);
		setY(coords[1]);
	}
	
	public double[] getAverageCoordinates() {
		double[] co = {
				x,
				y
		};
		
		for(Point p : grouping){
			co[0] += p.getX();
			co[1] += p.getY();
		}
		int size = grouping.size();
		
		co[0] = co[0]/size;
		co[1] = co[1]/size;
		
		return co;
	}
}
