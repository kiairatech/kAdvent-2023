import java.io.File

/**
 * A solver for Day 3's "Gear Ratios" puzzle.
 *
 * @property filePath Path to the input file containing the engine schematic.
 */
class Day3 {

	/**
	 * Lazy-loaded list of strings representing the engine schematic.
	 */
	private val schematic: List<String> by lazy { File("src/main/resources/day-3-input.txt").readLines() }

	/**
	 * Set of characters considered as symbols in the engine schematic.
	 */
	private val symbols = setOf('*', '#', '&', '@', '$', '+', '-', '%', '/', '=')

	/**
	 * Solves Part 1 of the puzzle by identifying and listing all part numbers.
	 *
	 * @return List of integers representing part numbers.
	 */
	fun solvePart1(): List<Int> {
		return schematic.flatMapIndexed { y, line ->
			line.mapIndexedNotNull { x, char ->
				if (char.isDigit() && isAdjacentToSymbol(x, y)) char.toString().toInt() else null
			}
		}
	}

	/**
	 * Solves Part 2 of the puzzle by identifying and listing the gear ratios.
	 *
	 * @return List of integers representing gear ratios.
	 */
	fun solvePart2(): List<Int> {
		return schematic.flatMapIndexed { y, line ->
			line.mapIndexedNotNull { x, char ->
				if (char == '*' && isGear(x, y)) calculateGearRatio(x, y) else null
			}
		}
	}

	/**
	 * Checks if a given position in the schematic is adjacent to a symbol.
	 *
	 * @param x X-coordinate in the schematic.
	 * @param y Y-coordinate in the schematic.
	 * @return True if the position is adjacent to a symbol, false otherwise.
	 */
	private fun isAdjacentToSymbol(x: Int, y: Int): Boolean {
		return getAdjacentPositions(x, y).any { (ax, ay) ->
			schematic.getOrNull(ay)?.getOrNull(ax) in symbols
		}
	}

	/**
	 * Checks if a '*' at a given position represents a gear.
	 *
	 * @param x X-coordinate in the schematic.
	 * @param y Y-coordinate in the schematic.
	 * @return True if the '*' symbol represents a gear, false otherwise.
	 */
	private fun isGear(x: Int, y: Int): Boolean {
		return getAdjacentPositions(x, y).count { (ax, ay) ->
			schematic.getOrNull(ay)?.getOrNull(ax)?.isDigit() == true
		} == 2
	}

	/**
	 * Calculates the gear ratio for a gear at a given position.
	 *
	 * @param x X-coordinate of the gear in the schematic.
	 * @param y Y-coordinate of the gear in the schematic.
	 * @return Gear ratio as an integer.
	 */
	private fun calculateGearRatio(x: Int, y: Int): Int {
		val partNumbers = getAdjacentPositions(x, y).mapNotNull { (ax, ay) ->
			schematic.getOrNull(ay)?.getOrNull(ax)?.toString()?.toIntOrNull()
		}
		return partNumbers.reduce(Int::times)
	}

	/**
	 * Gets positions adjacent to a given position in the schematic.
	 *
	 * @param x X-coordinate in the schematic.
	 * @param y Y-coordinate in the schematic.
	 * @return List of pairs representing adjacent positions.
	 */
	private fun getAdjacentPositions(x: Int, y: Int): List<Pair<Int, Int>> {
		return listOf(
			x - 1 to y, x + 1 to y, // left and right
			x to y - 1, x to y + 1, // up and down
			x - 1 to y - 1, x + 1 to y + 1, // diagonal
			x - 1 to y + 1, x + 1 to y - 1
		)
	}
}

fun main() {
	val solver = Day3() // Replace with the actual file path
	println("List of part numbers (Part 1): ${solver.solvePart1().sum()}")
	println("List of gear ratios (Part 2): ${solver.solvePart2().sum()}")
}
