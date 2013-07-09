package MidiParse;

import java.io.File;
import java.io.IOException;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.Track;

public class Startup
{
	public static void main(String[] args)
	{
		int START = 0x90; //144 decimal
		int STOP = 0x80; //128 decimal, what's in between
		
		Sequence seq;
		try {
			seq = MidiSystem.getSequence(new File("elise.mid"));
			for(Track track : seq.getTracks())
			{
				for(int eventIndex = 0; eventIndex < track.size(); eventIndex++)
				{
					MidiEvent event = track.get(eventIndex);
					
				}
			}
		} catch (InvalidMidiDataException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
