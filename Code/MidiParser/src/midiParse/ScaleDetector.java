package midiParse;

import java.util.HashMap;

public class ScaleDetector {
	//Supervised Learning
	
	HashMap<String, float[]> scale = new HashMap<String, float[]>();
	
	public void detectScale(String[] notes)
	{
		Note currentNote = null;
		Note previousNote = notes[0];
		float[] steps = new float[notes.length];
		
		for(int i = 1; i<notes.length; i++)
		{
			currentNote = notes[i];
			
			
		}
	}
}
