package seb_dot_hajek_at_gmail_dot_com.dots.shared;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Collections;
import java.util.stream.Stream;
import seb_dot_hajek_at_gmail_dot_com.dots.installer.config.ConfigManager;

public class FileUtils {

	public static final Path CONFIG_PATH =
	  Paths.get(System.getProperty("user.home"), ".config");

	/**
	 * Copies all files from a resource directory to a target directory. Creates
	 * the target directory if it doesn't exist.
	 *
	 * @param resourcePath the path to the resource directory
	 * @param targetPath the target directory path
	 * @throws IOException if file operations fail
	 * @throws URISyntaxException if resource URL is invalid
	 * @throws RuntimeException if resource directory is not found
	 */
	public static void copyResourceToDirectory(
	  String resourcePath,
	  Path   targetPath
	) throws IOException, URISyntaxException {
		copyResourceToDirectory(resourcePath, targetPath, true);
	}

	/**
	 * Copies all files from a resource directory to a target directory.
	 *
	 * @param resourcePath the path to the resource directory
	 * @param targetPath the target directory path
	 * @param createTargetDir whether to create the target directory if it
	 *   doesn't exist
	 * @throws IOException if file operations fail
	 * @throws URISyntaxException if resource URL is invalid
	 * @throws RuntimeException if resource directory is not found or target
	 *   directory doesn't exist
	 *     when createTargetDir is false
	 */
	public static void copyResourceToDirectory(
	  String  resourcePath,
	  Path    targetPath,
	  boolean createTargetDir
	) throws IOException, URISyntaxException {

		Logger.logger().info(
		  String.format("opening up resource @ %s ", resourcePath)
		);
		URL resourceUrl =
		  FileUtils.class.getClassLoader().getResource(resourcePath);
		if (resourceUrl == null) {
			throw new RuntimeException(
			  "Resource directory not found: " + resourcePath
			);
		}

		if (createTargetDir) {
			Logger.logger().info(String.format(
			  "making sure directories exist @ %s", targetPath.toString()
			));
			Files.createDirectories(targetPath);
		} else if (!Files.exists(targetPath)) {
			throw new RuntimeException(
			  "Target directory does not exist: " + targetPath
			);
		}

		URI resourceUri = resourceUrl.toURI();

		if (resourceUri.getScheme().equals("jar")) {
			copyFromJarResource(resourceUri, targetPath);
		} else {
			Path sourceDir = Paths.get(resourceUri);
			copyDirectoryRecursively(sourceDir, targetPath);
		}
	}

	/**
	 * Copies resources from inside a JAR file.
	 *
	 * @param jarUri the JAR URI of the resource
	 * @param targetPath the target directory path
	 * @throws IOException if file operations fail
	 */
	private static void copyFromJarResource(URI jarUri, Path targetPath)
	  throws IOException {
		try (
		  FileSystem jarFs =
		    FileSystems.newFileSystem(jarUri, Collections.emptyMap())
		) {
			// Extract the path within the JAR
			String jarPath      = jarUri.toString();
			String resourcePath = jarPath.substring(jarPath.indexOf("!") + 1);
			Path   sourceDir    = jarFs.getPath(resourcePath);

			copyJarDirectoryRecursively(sourceDir, targetPath);
		}
	}

	/**
	 * Recursively copies all files and subdirectories from a JAR source to a
	 * regular file system target. This method handles the path resolution
	 * between different file systems.
	 *
	 * @param source the source directory (from JAR file system)
	 * @param target the target directory (regular file system)
	 * @throws IOException if file operations fail
	 */
	private static void copyJarDirectoryRecursively(Path source, Path target)
	  throws IOException {
		try (Stream<Path> paths = Files.walk(source)) {
			paths.forEach(sourcePath -> {
				try {
					// Convert the relative path to a string to avoid file
					// system provider mismatch
					Path   relativePath    = source.relativize(sourcePath);
					String relativePathStr = relativePath.toString();

					// Resolve the target path using string concatenation to
					// avoid provider mismatch
					Path targetPath = target.resolve(relativePathStr);

					if (Files.isDirectory(sourcePath)) {
						Files.createDirectories(targetPath);
					} else {
						var log = String.format(
						  "copying: %s -> %s",
						  sourcePath.toString(),
						  targetPath.toString()
						);
						if (!ConfigManager.cfg().getConfig().dryRun()) {
							Logger.logger().info(log);
							Files.copy(
							  sourcePath,
							  targetPath,
							  StandardCopyOption.REPLACE_EXISTING
							);
						} else {
							Logger.logger().dryRun(log);
						}
					}
				} catch (IOException e) {
					throw new RuntimeException(
					  "Failed to copy: " + sourcePath, e
					);
				}
			});
		}
	}

	/**
	 * Copies files from a source directory to a target directory. Creates the
	 * target directory if it doesn't exist.
	 *
	 * @param sourcePath the source directory path
	 * @param targetPath the target directory path
	 * @throws IOException if file operations fail
	 */
	public static void copyDirectory(Path sourcePath, Path targetPath)
	  throws IOException {
		copyDirectory(sourcePath, targetPath, true);
	}

	/**
	 * Copies files from a source directory to a target directory.
	 *
	 * @param sourcePath the source directory path
	 * @param targetPath the target directory path
	 * @param createTargetDir whether to create the target directory if it
	 *   doesn't exist
	 * @throws IOException if file operations fail
	 * @throws RuntimeException if target directory doesn't exist when
	 *   createTargetDir is false
	 */
	public static void
	copyDirectory(Path sourcePath, Path targetPath, boolean createTargetDir)
	  throws IOException {
		if (!Files.exists(sourcePath)) {
			throw new RuntimeException(
			  "Source directory does not exist: " + sourcePath
			);
		}

		if (createTargetDir) {
			Logger.logger().info(String.format(
			  "making sure directories exist @ %s", targetPath.toString()
			));
			Files.createDirectories(targetPath);
		} else if (!Files.exists(targetPath)) {
			throw new RuntimeException(
			  "Target directory does not exist: " + targetPath
			);
		}

		copyDirectoryRecursively(sourcePath, targetPath);
	}

	/**
	 * Recursively copies all files and subdirectories from source to target.
	 *
	 * @param source the source directory
	 * @param target the target directory
	 * @throws IOException if file operations fail
	 */
	private static void copyDirectoryRecursively(Path source, Path target)
	  throws IOException {
		try (Stream<Path> paths = Files.walk(source)) {
			paths.forEach(sourcePath -> {
				try {
					Path targetPath =
					  target.resolve(source.relativize(sourcePath));
					if (Files.isDirectory(sourcePath)) {
						Files.createDirectories(targetPath);
					} else {
						var log = String.format(
						  "copying: %s -> %s",
						  sourcePath.toString(),
						  targetPath.toString()
						);
						if (!ConfigManager.cfg().getConfig().dryRun()) {

							Logger.logger().info(log);
							Files.copy(
							  sourcePath,
							  targetPath,
							  StandardCopyOption.REPLACE_EXISTING
							);
						} else {
							Logger.logger().dryRun(log);
						}
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
