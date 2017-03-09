package re.vianneyfaiv.folderation;

import java.security.InvalidParameterException;

import re.vianneyfaiv.folderation.controller.FolderationController;

public class FolderationApp {

	public static void main(String[] args) {
		if(args.length == 0) {
			throw new InvalidParameterException("Provide target path");
		}

		String targetPath = args[0];

		new FolderationController().process(targetPath);
	}
}
