package re.vianneyfaiv.folderation.logic;

import java.io.File;
import java.io.IOException;

import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;

import re.vianneyfaiv.folderation.model.AudioFolder;

public class AudioFolderBusiness {

	/**
	 * Compute total time in seconds
	 */
	public long computeTotalDuration(AudioFolder folder) {
		long totalInSec = 0;
		for(File child : folder.listFiles()) {
			totalInSec += getDuration(child);
		}

		return totalInSec;
	}

	/**
	 * Generate new folder name
	 */
	public String getNewFolderName(AudioFolder folder, long durationInSec) {
		final String oldFolderName = folder.getName();
		final long totalInMin = durationInSec / 60;
		String newFolderName = String.format("%s (%s min)", oldFolderName, totalInMin);
		return newFolderName;
	}

	/**
	 * Rename target folder using new name
	 */
	public void renameFolder(AudioFolder folder, String newFolderName) {
		File newTargetFolder = new File(folder.getParent() + File.separator + newFolderName);

		boolean renamed = folder.renameTo(newTargetFolder);
		if(!renamed) {
			throw new RuntimeException(String.format("Failed to rename folder %s to %s", folder.getName(), newFolderName));
		}
	}

	/**
	 * @return duration of the audio file provided or zero if an error occured
	 */
	private static int getDuration(File child) {
		int duration = 0;

		try {
			duration = AudioFileIO.read(child).getAudioHeader().getTrackLength();
		} catch (CannotReadException | IOException | TagException | ReadOnlyFileException
				| InvalidAudioFrameException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return duration;
	}
}
