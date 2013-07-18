package midiParse;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MetaMessage;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;

public class MidiParser {
	public void parseFile(String fileName)
	{
		File saveFile = new File("C:\\Users\\cbaldwin\\Desktop\\MidiOutput.txt");
		Sequence seq;
		try {
			PrintWriter writer = new PrintWriter(saveFile);
			seq = MidiSystem.getSequence(new File(fileName));
			for(Track track : seq.getTracks()) {
				int currentChannel = -1;
				for(int eventIndex = 0; eventIndex < track.size(); eventIndex++) {
					MidiEvent event = track.get(eventIndex);
					MidiMessage message = event.getMessage();
					String output;
					
					if(message instanceof ShortMessage) {
						ShortMessage a = (ShortMessage)message;
						
						int channel = a.getChannel();
						int velocity = a.getData2();
						int key = a.getData1();
						int octave = (key/12);
						
						String note = Notes.getNoteName(key % 12);
						String status = (a.getCommand() == ShortMessage.NOTE_ON) ? "On" : "Off";
						
						if(channel != currentChannel) {
							currentChannel = channel;
							output = "\r\n\r\nChannel:" + channel + "\r\n\tNote:" + note + octave + "\\Status:" + status + "\\Velocity:" + velocity + "\r\n";
							System.out.print(output);
							writer.print(output);
						}
						else {
							output = "\tNote:" + note + octave + " \\Status:" + status + " \\Velocity:" + velocity + "\r\n";
							System.out.print(output);
							writer.print(output);
						}
						
						writer.flush();
					}
					else if(message instanceof MetaMessage) {
						MetaMessage a = (MetaMessage)message;
						output = "METADATA--------- " + new String(a.getMessage()) + "\r\n";
						System.out.print(output);
						writer.print(output);
					}
				}
			}
			writer.close();
		} catch (InvalidMidiDataException | IOException e) {
			e.printStackTrace();
		} 
	}
}
