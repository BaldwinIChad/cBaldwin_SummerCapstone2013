package midiParse;

public class Notes {
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
	
	public static String getNoteName(int value) {
		return notes[value].note();
	}
}
