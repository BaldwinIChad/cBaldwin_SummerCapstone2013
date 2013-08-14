package midiParse;

public enum NoteName implements Comparable<NoteName> {
	C(0),
	Cs(1),
	D(2),
	Ds(3),
	E(4),
	F(5),
	Fs(6),
	G(7),
	Gs(8),
	A(9),
	As(10),
	B(11);
	
	private final int index;
	
	NoteName(int index) {
		this.index = index;
	}
	
	int getIndex() {
		return index;
	}
	
	public int compareNote(NoteName compare) {
		int compareVal = 0;
		
		if(this.index > compare.index)
			compareVal = 1;
		else if(this.index < compare.index)
			compareVal = -1;
		
		return compareVal;
	}
}
