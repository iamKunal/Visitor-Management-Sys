package visMan.utils;


import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.ByteArrayInputStream;

import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;

import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.embed.swing.SwingFXUtils;
import javafx.print.PageLayout;
import javafx.print.PageOrientation;
import javafx.print.Paper;
import javafx.print.Printer;
import javafx.print.Printer.MarginType;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.transform.Scale;

/**
 * Provide general purpose methods for handling OpenCV-JavaFX data conversion.
 * Moreover, expose some "low level" methods for matching few JavaFX behavior.
 *
 * @author <a href="mailto:luigi.derussis@polito.it">Luigi De Russis</a>
 * @author <a href="http://max-z.de">Maximilian Zuleger</a>
 * @version 1.0 (2016-09-17)
 * @since 1.0
 * 
 */
public final class Utils
{
	public static String toTitleCase(String input) {
	    StringBuilder titleCase = new StringBuilder();
	    boolean nextTitleCase = true;

	    for (char c : input.toCharArray()) {
	        if (Character.isSpaceChar(c)) {
	            nextTitleCase = true;
	        } else if (nextTitleCase) {
	            c = Character.toTitleCase(c);
	            nextTitleCase = false;
	        }

	        titleCase.append(c);
	    }

	    return titleCase.toString();
	}
	public static void print(Node node, Paper paper){
//		double conversionFactor = 60624/(8.27);
//		double pHeight= paper.getHeight();
//		System.out.println("a" + pHeight);
		Printer printer = Printer.getDefaultPrinter();
		PageLayout pageLayout = printer.createPageLayout(paper, PageOrientation.PORTRAIT, 54,54,54,54);
//		double dpi = pageLayout.getPrintableHeight()/pHeight;
//		System.out.println(pageLayout.getPrintableHeight());
//		double marginIn = 1.5;
//		double marginPx = dpi*marginIn;
//		System.out.println(pageLayout.getLeftMargin());
		double scaleX = (pageLayout.getPrintableWidth() ) / node.getBoundsInParent().getWidth();
        double scaleY = (pageLayout.getPrintableHeight() ) / node.getBoundsInParent().getHeight();
        node.getTransforms().add(new Scale(scaleX, scaleY));
		PrinterJob printerJob = PrinterJob.createPrinterJob();
		if(printerJob.printPage(node))
			   printerJob.endJob();
		scaleX = 1/scaleX;
		scaleY = 1/scaleY;
        node.getTransforms().add(new Scale(scaleX, scaleY));
	}
	public static String getToggleText(ToggleGroup t){
		try{
		return ((RadioButton)t.getSelectedToggle()).getText();
		}
		catch(Exception e){
			return "null";
		}
		
	}
	public static String getToggleText(Toggle t){
		return getToggleText(t.getToggleGroup());
	}
	
	/**
	 * Convert a Mat object (OpenCV) in the corresponding Image for JavaFX
	 *
	 * @param frame
	 *            the {@link Mat} representing the current frame
	 * @return the {@link Image} to show
	 */
	public static Image mat2Image(Mat frame)
	{
        // create a temporary buffer
        MatOfByte buffer = new MatOfByte();
        // encode the frame in the buffer
        Imgcodecs.imencode(".png", frame, buffer);
        // build and return an Image created from the image encoded in the
        // buffer
        return new Image(new ByteArrayInputStream(buffer.toArray()));
	}
	
	/**
	 * Generic method for putting element running on a non-JavaFX thread on the
	 * JavaFX thread, to properly update the UI
	 * 
	 * @param property
	 *            a {@link ObjectProperty}
	 * @param value
	 *            the value to set for the given {@link ObjectProperty}
	 */
	public static <T> void onFXThread(final ObjectProperty<T> property, final T value)
	{
		Platform.runLater(() -> {
			property.set(value);
		});
	}
	
	/**
	 * Support for the {@link mat2image()} method
	 * 
	 * @param original
	 *            the {@link Mat} object in BGR or grayscale
	 * @return the corresponding {@link BufferedImage}
	 */
	private static BufferedImage matToBufferedImage(Mat original)
	{
		// init
		BufferedImage image = null;
		int width = original.width(), height = original.height(), channels = original.channels();
		byte[] sourcePixels = new byte[width * height * channels];
		original.get(0, 0, sourcePixels);
		
		if (original.channels() > 1)
		{
			image = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
			
		}
		else
		{
			image = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
		}
		final byte[] targetPixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
		System.arraycopy(sourcePixels, 0, targetPixels, 0, sourcePixels.length);
		
		return image;
	}
}
