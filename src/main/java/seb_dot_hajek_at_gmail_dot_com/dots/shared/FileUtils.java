package seb_dot_hajek_at_gmail_dot_com.dots.shared;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.Collections;
import java.util.stream.Stream;
import seb_dot_hajek_at_gmail_dot_com.dots.installer.config.ConfigManager;

public class FileUtils {

	public static final Path HOME_PATH =
	  Paths.get(System.getProperty("user.home"));
	public static final Path CONFIG_PATH = HOME_PATH.resolve(".config");

	public static void writeToFile(Path filePath, String content)
	  throws IOException {
		if (filePath.getParent() != null) {
			Files.createDirectories(filePath.getParent());
		}

		String log = String.format("writing content to file: %s", filePath);
		if (ConfigManager.cfg().getConfig().dryRun()) {
			Logger.logger().dryRun(log);
		} else {
			Logger.logger().info(log);
			Files.writeString(
			  filePath,
			  content,
			  StandardOpenOption.CREATE,
			  StandardOpenOption.TRUNCATE_EXISTING
			);
		}
	}

	public static void copyFile(Path source, Path target) throws IOException {
		if (target.getParent() != null) {
			Files.createDirectories(target.getParent());
		}

		String log = String.format("copying file: %s -> %s", source, target);
		if (ConfigManager.cfg().getConfig().dryRun()) {
			Logger.logger().dryRun(log);
		} else {
			Logger.logger().info(log);
			Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
		}
	}

	public static void copyDirectory(Path source, Path target)
	  throws IOException {
		Files.createDirectories(target);

		try (Stream<Path> paths = Files.walk(source)) {
			paths.forEach(sourcePath -> {
				try {
					Path targetPath =
					  target.resolve(source.relativize(sourcePath));
					if (Files.isDirectory(sourcePath)) {
						Files.createDirectories(targetPath);
					} else {
						copyFile(sourcePath, targetPath);
					}
				} catch (IOException e) {
					throw new RuntimeException(
					  "Failed to copy: " + sourcePath, e
					);
				}
			});
		}
	}

	public static void copyResourceDirectory(Path resourcePath, Path targetPath)
	  throws IOException, URISyntaxException {
		Logger.logger().info(
		  String.format("opening resource @ %s", resourcePath)
		);

		var resourceUrl =
		  FileUtils.class.getClassLoader().getResource(resourcePath.toString());
		if (resourceUrl == null) {
			throw new RuntimeException("Resource not found: " + resourcePath);
		}

		Files.createDirectories(targetPath);
		URI resourceUri = resourceUrl.toURI();

		if (resourceUri.getScheme().equals("jar")) {
			copyFromJar(resourceUri, targetPath);
		} else {
			copyDirectory(Paths.get(resourceUri), targetPath);
		}
	}

	public static void copyResourceFile(Path resourcePath, Path targetPath)
	  throws IOException, URISyntaxException {
		var resourceUrl =
		  FileUtils.class.getClassLoader().getResource(resourcePath.toString());
		if (resourceUrl == null) {
			throw new RuntimeException("Resource not found: " + resourcePath);
		}

		if (targetPath.getParent() != null) {
			Files.createDirectories(targetPath.getParent());
		}

		URI resourceUri = resourceUrl.toURI();

		if (resourceUri.getScheme().equals("jar")) {
			try (
			  FileSystem jarFs =
			    FileSystems.newFileSystem(resourceUri, Collections.emptyMap())
			) {
				String jarPath = resourceUri.toString();
				String internalPath =
				  jarPath.substring(jarPath.indexOf("!") + 1);
				Path sourceFile = jarFs.getPath(internalPath);
				copyFile(sourceFile, targetPath);
			}
		} else {
			copyFile(Paths.get(resourceUri), targetPath);
		}
	}

	private static void copyFromJar(URI jarUri, Path targetPath)
	  throws IOException {
		try (
		  FileSystem jarFs =
		    FileSystems.newFileSystem(jarUri, Collections.emptyMap())
		) {
			String jarPath      = jarUri.toString();
			String resourcePath = jarPath.substring(jarPath.indexOf("!") + 1);
			Path   sourceDir    = jarFs.getPath(resourcePath);

			try (Stream<Path> paths = Files.walk(sourceDir)) {
				paths.forEach(sourcePath -> {
					try {
						Path relativePath = sourceDir.relativize(sourcePath);
						Path targetFilePath =
						  targetPath.resolve(relativePath.toString());

						if (Files.isDirectory(sourcePath)) {
							Files.createDirectories(targetFilePath);
						} else {
							copyFile(sourcePath, targetFilePath);
						}
					} catch (IOException e) {
						throw new RuntimeException(
						  "Failed to copy: " + sourcePath, e
						);
					}
				});
			}
		}
	}
}
