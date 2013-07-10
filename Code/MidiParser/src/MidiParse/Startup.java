package MidiParse;

import java.io.File;
import java.io.IOException;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MetaMessage;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;

public class Startup
{
	public static void main(String[] args)
	{
		final String[] notes = {"A", "A#", "B", "C", "C#", "D", "D#", "E", "F", "F#", "G", "G#"};
		Sequence seq;
		try {
			seq = MidiSystem.getSequence(new File("elise.mid"));
			for(Track track : seq.getTracks())
			{
				int currentChannel = -1;
				for(int eventIndex = 0; eventIndex < track.size(); eventIndex++)
				{
					MidiEvent event = track.get(eventIndex);
					MidiMessage message = event.getMessage();
					if(message instanceof ShortMessage)
					{
						ShortMessage a = (ShortMessage)message;
						int channel = a.getChannel();
						int velocity = a.getData2();
						int pitch = (a.getData1() / 12);
						String note = notes[pitch % 12];
						String status = (a.getCommand() == ShortMessage.NOTE_ON) ? "On" : "Off";
						
						if(channel != currentChannel)
						{
							currentChannel = channel;
							System.out.println("\n\nChannel:" + channel + "\n\tNote:" + note + "\\Status:" + status + "\\Velocity:" + velocity);
						}
						else
							System.out.println("\tNote:" + note + " \\Status:" + status + " \\Velocity:" + velocity);
					}
					else if(message instanceof MetaMessage)
					{
						MetaMessage a = (MetaMessage)message;
						System.out.println("METADATA--------- " + new String(a.getMessage()));
					}
				}
			}
		} catch (InvalidMidiDataException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
