package test;

import midiParse.Note;
import midiParse.NoteName;

public class TestDriver {
	public static void main(String[] args) {
		Note one = new Note(NoteName.A, 5);
		Note two = new Note(NoteName.G, 6);
		
		System.out.println(one.getSemitonesBetweenNote(two));
	}

}
