package re.vianneyfaiv.folderation.model;

import java.io.File;
import java.security.InvalidParameterException;

public class AudioFolder {

	private File folder;

	public AudioFolder(String path) {
		File targetFolder = new File(path);

		if(!targetFolder.exists() || !targetFolder.isDirectory()) {
			throw new InvalidParameterException(String.format("Provided path %s is not a directory", path));
		}

		if(path.endsWith("min)")) {
			throw new InvalidParameterException(String.format("Provided folder %s has already been renamed", path));
		}

		this.folder = targetFolder;
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

	public boolean renameTo(File newName) {
		return this.folder.renameTo(newName);
	}

}
