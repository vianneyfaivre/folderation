package re.vianneyfaiv.folderation.model;

import java.io.File;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.time.Duration;
import java.time.temporal.ChronoUnit;

import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;

public class AudioFolder {

	private File folder;

	public AudioFolder(String path) throws InvalidFolderException {
		this(new File(path));
	}

	public AudioFolder(File targetFolder) throws InvalidFolderException {
		if(!targetFolder.exists() || !targetFolder.isDirectory()) {
			throw new InvalidParameterException(String.format("Provided folder %s is not a directory", targetFolder.getAbsolutePath()));
		}

		if(targetFolder.getName().endsWith("min)")) {
			throw new InvalidFolderException(String.format("Provided folder %s has already been renamed", targetFolder.getAbsolutePath()));
		}

		this.folder = targetFolder;
	}

	/**
	 * Compute total time in seconds
	 */
	public Duration getAudioDuration() {
		long duration = 0;

		for(File child : this.folder.listFiles()) {
			duration += getFileDuration(child);
		}

		return Duration.of(duration, ChronoUnit.SECONDS);
	}

	/**
	 * Generate new folder name
	 */
	public String getNewFolderName(Duration duration) {
		final String oldFolderName = this.folder.getName();

		long totalInSec = duration.get(ChronoUnit.SECONDS);
		long totalInMin = totalInSec / 60;

		return String.format("%s (%s min)", oldFolderName, totalInMin);
	}

	/**
	 * Rename target folder using new name
	 */
	public boolean renameTo(String newName) {
		File newTargetFolder = new File(this.folder.getParent() + File.separator + newName);
		return this.folder.renameTo(newTargetFolder);
	}

	public File[] listFiles() {
		return this.folder.listFiles();
	}

	public String getName() {
		return this.folder.getName();
	}

	public String getParent() {
		return this.folder.getParent();
	}

	/**
	 * @return duration of the audio file in seconds or zero if an error occured
	 */
	private static int getFileDuration(File child) {
		int seconds = 0;

		try {
			seconds = AudioFileIO.read(child).getAudioHeader().getTrackLength();
		} catch (CannotReadException | IOException | TagException | ReadOnlyFileException
				| InvalidAudioFrameException e) {
			System.err.println(e.getMessage());
		}

		return seconds;
	}
}
