package midiParse;

public class Notes {
	private static final int NUMBEROFNOTES = 12;
	private static Note[] notes = {
		new Note("C"),
		new Note("C#"),
		new Note("D"),
		new Note("D#"), 
		new Note("E"), 
		new Note("F"), 
		new Note("F#"),
		new Note("G"),
		new Note("G#"), 
		new Note("A"),
		new Note("A#"),
		new Note("B")
	};
	
	public static Note getNote(int value) {
		return notes[value];
	}
	
	public static int getNoteIndex(String note) {
		int toReturn = -1;
		
		for(int i = 0; i < NUMBEROFNOTES; i++)
			if(notes[i].note().equalsIgnoreCase(note))
				toReturn = i;
		
		return toReturn;
	}
	
	public static String getNoteName(int value) {
		return notes[value].note();
	}
}
