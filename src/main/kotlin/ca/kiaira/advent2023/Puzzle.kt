package ca.kiaira.advent2023

import com.github.michaelbull.logging.InlineLogger
import java.io.File
import java.io.FileNotFoundException

/**
 * Abstract base class for solving Advent of Code puzzles.
 * Each day's puzzle extends this class, providing specific implementations for parsing and solving.
 *
 * @param T The type of the parsed input data for the puzzle.
 * @property number The day number of the puzzle.
 */
abstract class Puzzle<T>(val number: Int) {

	/**
	 * Parses the input data for the puzzle from a file.
	 *
	 * The method reads from a file named "day-[number]-input.txt" located in the "src/main/resources" directory.
	 * It uses the file's lines to parse the input data specific to the puzzle.
	 *
	 * @return The parsed input data of type [T].
	 * @throws FileNotFoundException if the input file is not found in the specified path.
	 */
	fun parse(): Result<T> {
		val fileName = "src/main/resources/day-$number-input.txt"
		val file = File(fileName)

		return try {
			if (!file.exists()) {
				throw FileNotFoundException("Input file $fileName not found.")
			}

			Result.success(file.useLines { lines -> parse(lines) })
		} catch (e: Exception) {
			Result.failure(e)
		}
	}

	/**
	 * Parses the input data for the puzzle from a string.
	 * Useful for testing or when input data is available as a string rather than a file.
	 *
	 * @param input The input data as a string.
	 * @return The parsed input data of type [T].
	 */
	fun parse(input: String): Result<T> {
		return try {
			Result.success(parse(input.splitToSequence('\n')))
		} catch (e: Exception) {
			Result.failure(e)
		}
	}

	/**
	 * Abstract method to parse the input data for the puzzle.
	 * Must be implemented by each subclass to handle the specific parsing logic for the puzzle's input.
	 *
	 * @param input The input data as a sequence of strings.
	 * @return The parsed input data of type [T].
	 */
	abstract fun parse(input: Sequence<String>): T

	/**
	 * Abstract method to solve Part 1 of the puzzle.
	 * Must be implemented by each subclass to provide the logic for solving Part 1 of the puzzle.
	 *
	 * @param input The parsed input data.
	 * @return The solution to Part 1 of the puzzle.
	 */
	abstract fun solvePart1(input: T): Any

	/**
	 * Method to solve Part 2 of the puzzle.
	 * Can be optionally overridden by subclasses to provide the logic for solving Part 2 of the puzzle.
	 *
	 * @param input The parsed input data.
	 * @return The solution to Part 2 of the puzzle, if implemented.
	 */
	open fun solvePart2(input: T): Any? = null

	/**
	 * Executes the solutions to both parts of the puzzle and prints both parts of the solution sets
	 */
	fun solve() = with(this) {
		val parsed = parse().getOrThrow()

		logger.info { "Day $number Solutions" }
		logger.info { "Part 1: ${solvePart1(parsed)}" }
		logger.info { "Part 2: ${solvePart2(parsed)}" }
	}

	/** The instance of the logger */
	private val logger = InlineLogger()
}
