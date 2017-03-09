package re.vianneyfaiv.folderation;

import java.io.File;
import java.io.IOException;
import java.security.InvalidParameterException;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;

public class FolderationApp {

	public static void main(String[] args) {

		/*
		 * Check args
		 */
		if(args.length == 0) {
			throw new InvalidParameterException("Provide target path");
		}

		String targetPath = args[0];

		File targetFolder = new File(targetPath);

		if(!targetFolder.exists() || !targetFolder.isDirectory()) {
			throw new InvalidParameterException(String.format("Provided path %s is not a directory", targetPath));
		}

		if(targetPath.endsWith("min)")) {
			throw new InvalidParameterException(String.format("Provided folder %s has already been renamed", targetPath));
		}

		/*
		 * Compute total time
		 */
		long totalInSec = 0;
		for(File child : targetFolder.listFiles()) {
			totalInSec += getDuration(child);
		}

		/*
		 * Generate new folder name
		 */
		final String oldFolderName = targetFolder.getName();
		final long totalInMin = totalInSec / 60;
		String newFolderName = String.format("%s (%s min)", oldFolderName, totalInMin);

		/*
		 * Rename target folder using new name
		 */
		File newTargetFolder = new File(targetFolder.getParent() + File.separator + newFolderName);

		boolean renamed = targetFolder.renameTo(newTargetFolder);

		if(!renamed) {
			throw new UnknownError(String.format("Failed to rename folder %s to %s", oldFolderName, newFolderName));
		}
	}

	private static int getDuration(File child) {

		int duration = 0;

		try {
			AudioFile audioFile = AudioFileIO.read(child);
			duration = audioFile.getAudioHeader().getTrackLength();
		} catch (CannotReadException | IOException | TagException | ReadOnlyFileException
				| InvalidAudioFrameException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return duration;
	}
}
