package ca.kiaira.advent2023.day13

import ca.kiaira.advent2023.Puzzle

/**
 * The main object representing the puzzle for Day 13.
 * It extends the Puzzle class and provides implementations for parsing and solving.
 */
object Day13 : Puzzle<List<GridPattern>>(13) {

	/**
	 * Parses the input data and converts it into a list of GridPattern objects.
	 * @param input The input data as a sequence of strings.
	 * @return A list of GridPattern objects representing the parsed data.
	 */
	override fun parse(input: Sequence<String>): List<GridPattern> {
		return input.joinToString("\n").split("\n\n").map { chunk ->
			GridPattern(chunk.split("\n").map { it.toCharArray() }.toTypedArray())
		}
	}

	/**
	 * Solves Part 1 of the puzzle by finding valid reflections for each GridPattern and summing the results.
	 * @param input The list of GridPattern objects to be solved.
	 * @return The sum of the results of solving each GridPattern.
	 */
	override fun solvePart1(input: List<GridPattern>): Any {
		return input.sumOf { GridPattern.solvePattern(it, 0) }
	}

	/**
	 * Solves Part 2 of the puzzle by finding valid reflections with two non-matching places for each GridPattern and summing the results.
	 * @param input The list of GridPattern objects to be solved.
	 * @return The sum of the results of solving each GridPattern.
	 */
	override fun solvePart2(input: List<GridPattern>): Any {
		return input.sumOf { GridPattern.solvePattern(it, 2) }
	}
}