package visualiser;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JPanel;

import dungen.Cell;
import dungen.Result;

/**
 * The results frame.
 */
public class ResultsFrame extends JPanel {	
	private static final long serialVersionUID = 1L;
	/**
	 * The results.
	 */
	private Result result;

	/**
	 * Creates a new instance of the ResultsFrame class.
	 * @param result The generated result.
	 */
	private ResultsFrame(Result result) {
		this.result = result;
		int frameWidth  = result.getConfiguration().width * Constants.RESULTS_FRAME_CELL_SIZE;
		int frameHeight = result.getConfiguration().height * Constants.RESULTS_FRAME_CELL_SIZE;
		this.setPreferredSize(new Dimension(frameWidth, frameHeight));
		this.setVisible(true);
	}
	
	/**
	 * Paint the display.
	 */
	@Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        // Draw each pixel to the display.
        for (int y = 0; y < result.getConfiguration().height; y++) {
        	for (int x = 0; x < result.getConfiguration().width; x++) {
        		// TODO Do this right!
        		Cell cell = result.getCells().get(x, y);
        		g.setColor(cell != null && !cell.getType().equals("WALL") ? Color.WHITE : Color.BLACK);
        		g.fillRect(x * Constants.RESULTS_FRAME_CELL_SIZE, y * Constants.RESULTS_FRAME_CELL_SIZE, Constants.RESULTS_FRAME_CELL_SIZE, Constants.RESULTS_FRAME_CELL_SIZE);
            }        	
        }
    }
	
	/**
	 * Creates a ResultsFrame instance wrapped in a JFrame.
	 */
	public static ResultsFrame create(String title, Result result) {
		// Create the ResultsFrame.
		ResultsFrame display = new ResultsFrame(result);
		
		// Create the application jframe in which to show the display. 
		JFrame frame = new JFrame(title);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setContentPane(display);
		frame.pack();
		frame.setVisible(true);
		
		return display;
	}
}