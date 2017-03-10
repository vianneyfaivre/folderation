package re.vianneyfaiv.folderation.controller;

import java.time.Duration;

import re.vianneyfaiv.folderation.model.AudioFolder;
import re.vianneyfaiv.folderation.model.AudioLibrary;

public class FolderationController {

	public void process(String targetPath) {

		AudioLibrary library = new AudioLibrary(targetPath);

		for(AudioFolder folder : library.getAudioFolders()) {

			Duration duration = folder.getAudioDuration();

			String newFolderName = folder.getNewFolderName(duration);

			boolean renamed = folder.renameTo(newFolderName);
			if(!renamed) {
				System.err.println(String.format("Failed to rename folder %s to %s", folder.getName(), newFolderName));
			}
		}
	}
}
