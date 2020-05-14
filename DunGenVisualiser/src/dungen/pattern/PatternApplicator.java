package dungen.pattern;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;
import dungen.Cell;
import dungen.Cells;
import dungen.Configuration;
import dungen.lotto.Lotto;

/**
 * Applicator of cell patterns to cell collections.
 */
public class PatternApplicator {
	/**
	 * Apply the given pattern to the cell collection.
	 * @param pattern The pattern to apply.
	 * @param cells The cell collection.
	 * @param config The generator configuration.
	 * @param rng The RNG to use.
	 */
	public static void apply(Pattern pattern, Cells cells, Configuration config, Random rng) {		
		// If there is a chance that the pattern should not be applied we should check now and skip the pattern if the chance fails.
		if (rng.nextDouble() <= pattern.getApplicationChance()) {
			return;
		}
		
		// Pick how many patterns we are going to apply based on the pattern min/max values.
		int totalApplications = (int) (Math.floor(rng.nextInt() * (pattern.getMaximumApplications() - pattern.getMinimumApplications() + 1)) + pattern.getMinimumApplications());
		
		// Apply the pattern randomly as many times as we have decided to.
		for (int count = 0; count < totalApplications; count++) {
			// Get a matching cell position.
			ApplicationPosition applicationPosition = findApplicationPosition(pattern, cells, config, rng);
			
			
		}
	}
	
	/**
	 * Finds a space at which the specified pattern can be applied, or null if no space exists.
	 * @return A space at which the specified pattern can be applied, or null if no space exists.
	 */
	private static ApplicationPosition findApplicationPosition(Pattern pattern, Cells cells, Configuration config, Random rng) {
		// Create a lotto to use in selecting a sequence based on ticket counts.
		Lotto<Sequence> sequenceLotto = new Lotto<Sequence>();
		
		// Add each pattern sequence to our lotto.
		for (Sequence sequence : pattern.getSequences()) {
			sequenceLotto.add(sequence, sequence.getTickets());
		}
		
		// Select the winning sequence to use based on ticket counts.
		Sequence sequence = sequenceLotto.draw();
		
		// Create a list for every position in the dungeon where the sequence can be applied.
		ArrayList<ApplicationPosition> applicablePositions = new ArrayList<ApplicationPosition>();
		
		// Check this pattern against every space in the dungeon.
		for (var x = 0; x < config.width; x++) {
			for (var y = 0; y < config.height; y++) {
				// Check whether the pattern matches the current space, and check whether we should apply it based on chance.
				if (doesSequenceApplyToSpace(sequence, cells, x, y)) {
					applicablePositions.add(new ApplicationPosition(sequence, new Point(x, y)));
				}
			}
		}
		
		// Return a random position at which we were able to apply the sequence.
		return applicablePositions.get(rng.nextInt(applicablePositions.size()));
	}
	
	/**
	 * Gets whether a sequence can be applied to a particular cell position.
	 * @param sequence
	 * @param cells
	 * @param x
	 * @param y
	 * @return
	 */
	private static boolean doesSequenceApplyToSpace(Sequence sequence, Cells cells, int x, int y) {
		// A sequence will apply to a cell if every match cell in the sequence 
		for (PatternCell cell : sequence.getMatchCells()) {
			// Get the absolute positioned existing cell.
			Cell target = cells.get(x + cell.getxOffset(), y + cell.getyOffset());
			
			// No out-of-bounds cell can be treated as a matching cell.
			if (target == Cells.OUT_OF_BOUNDS) {
				return false;
			}
			
			// No frozen cell can be treated as a matching cell.
			if (target.isFrozen()) {
				return false;
			}
			
			// TODO Make sure cell.name (possible csv list of names) contains the name of target.name.
		}

		// Our pattern matched!
		return true;
	}
}
