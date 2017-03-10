package re.vianneyfaiv.folderation.model;

import java.io.File;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

public class AudioLibrary {

	private File root;
	private List<AudioFolder> folders;

	public AudioLibrary(String path) {
		this.root = this.getRoot(path);

		this.folders = new ArrayList<>();

		if(this.compute(this.root)) {
			try {
				this.folders.add(new AudioFolder(this.root));
			} catch (InvalidFolderException ife) {
				System.err.println(ife.getMessage());
			}
		}
	}

	/**
	 * if parent is a directory and contains files only => it will be renamed
	 */
	private boolean compute(File parent) {

		boolean onlyFiles = true;

		for(File f : parent.listFiles()) {

			if(f.isDirectory() && this.compute(f)) {

				try {
					this.folders.add(new AudioFolder(f));
				} catch(InvalidFolderException ife) {
					System.err.println(ife.getMessage());
				}
			}

			onlyFiles &= f.isFile();
		}

		return onlyFiles;
	}

	private File getRoot(String path) {
		File root = new File(path);

		if(!root.exists() || !root.isDirectory()) {
			throw new InvalidParameterException(String.format("Provided path %s is not a directory", path));
		}

		return root;
	}

	public List<AudioFolder> getAudioFolders() {
		return this.folders;
	}
}
