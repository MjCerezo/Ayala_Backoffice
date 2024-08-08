package member.forms;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageResizer {
	/**
	 * Resizes an image to a absolute width and height (the image may not be
	 * proportional)
	 * 
	 * @param inputImagePath  Path of the original image
	 * @param outputImagePath Path to save the resized image
	 * @param scaledWidth     absolute width in pixels
	 * @param scaledHeight    absolute height in pixels
	 * @throws IOException
	 */
	public static void resize(String inputImagePath, String outputImagePath, int scaledWidth, int scaledHeight)
			throws IOException {
		// reads input image
		File inputFile = new File(inputImagePath);
		System.out.println(inputFile.exists());
		BufferedImage inputImage = ImageIO.read(inputFile);

		// creates output image
		BufferedImage outputImage = new BufferedImage(scaledWidth, scaledHeight, inputImage.getType());

		// scales the input image to the output image
		Graphics2D g2d = outputImage.createGraphics();
		g2d.drawImage(inputImage, 0, 0, scaledWidth, scaledHeight, null);
		g2d.dispose();
		System.out.println(outputImagePath);
		// extracts extension of output file
		String formatName = outputImagePath.substring(outputImagePath.lastIndexOf(".") + 1);
		System.out.println("name: "+formatName);
		File f = new File(outputImagePath);
		System.out.println(f.exists());
		if(f.exists()) {
			f.delete();
		}
		// writes to output file
		ImageIO.write(outputImage, "test", new File(outputImagePath));
	}

	/**
	 * Resizes an image by a percentage of original size (proportional).
	 * 
	 * @param inputImagePath  Path of the original image
	 * @param outputImagePath Path to save the resized image
	 * @param percent         a double number specifies percentage of the output
	 *                        image over the input image.
	 * @throws IOException
	 */
	public static void resize(String inputImagePath, String outputImagePath, double percent) throws IOException {
		File inputFile = new File(inputImagePath);
		BufferedImage inputImage = ImageIO.read(inputFile);
		int scaledWidth = (int) (inputImage.getWidth() * percent);
		int scaledHeight = (int) (inputImage.getHeight() * percent);
		resize(inputImagePath, outputImagePath, scaledWidth, scaledHeight);
	}
	
	public static BufferedImage resize(BufferedImage img, int height, int width) {
		Image tmp = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = resized.createGraphics();
		g2d.drawImage(tmp, 0, 0, null);
		g2d.dispose();
		return resized;
	}

}
