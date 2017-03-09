package re.vianneyfaiv.folderation.controller;

import re.vianneyfaiv.folderation.logic.AudioFolderBusiness;
import re.vianneyfaiv.folderation.model.AudioFolder;

public class FolderationController {

	private AudioFolderBusiness audioFolderBusiness = new AudioFolderBusiness();

	public void process(String targetPath) {

		AudioFolder folder = new AudioFolder(targetPath);

		long durationInSec = this.audioFolderBusiness.computeTotalDuration(folder);

		String newFolderName = this.audioFolderBusiness.getNewFolderName(folder, durationInSec);

		this.audioFolderBusiness.renameFolder(folder, newFolderName);
	}
}
