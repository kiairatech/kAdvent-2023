package com.tyluur

import com.github.michaelbull.logging.InlineLogger
import com.tyluur.day1.Day1
import com.tyluur.day2.Day2
import kotlin.time.Duration
import kotlin.time.measureTimedValue

/**
 * The main function for running Advent of Code puzzles.
 * It selects and runs puzzles, measures execution time, and prints the solutions.
 *
 * @param args Command line arguments, which can include the specific day number to run.
 */
fun main(args: Array<String>) {
	// List of all puzzle objects for each day.
	val puzzles = mutableListOf<Puzzle<*>>(
		Day1, Day2
	)

	// If a specific day is provided in the command line arguments, filter to run only that day's puzzle.
	val day = args.firstOrNull()?.toIntOrNull()
	if (day != null) {
		puzzles.removeIf { puzzle -> puzzle.number != day }
	}

	// Solve each puzzle in the list.
	for (puzzle in puzzles) {
		solve(puzzle)
	}
}

/**
 * Solves a given puzzle, measures the time taken for execution, and prints the results.
 *
 * @param T The type of the parsed input data for the puzzle.
 * @param puzzle The puzzle object to be solved.
 */
private fun <T> solve(puzzle: Puzzle<T>) {
	// Measure time taken to parse the puzzle input.
	val input = measureTimedValue {
		puzzle.parse()
	}

	// Solve Part 1 of the puzzle and measure the time taken.
	val solutionPart1 = measureTimedValue {
		puzzle.solvePart1(input.value)
	}
	printSolution(puzzle.number, 1, solutionPart1.value, solutionPart1.duration, input.duration)

	// Solve Part 2 of the puzzle, if implemented, and measure the time taken.
	val solutionPart2 = measureTimedValue {
		puzzle.solvePart2(input.value)
	}
	if (solutionPart2.value != null) {
		printSolution(puzzle.number, 2, solutionPart2.value!!, solutionPart2.duration)
	}
}

/**
 * Prints the solution of a puzzle along with the time taken for execution.
 *
 * @param day The day number of the puzzle.
 * @param part The part number of the puzzle solution (1 or 2).
 * @param solution The solution to the puzzle.
 * @param duration The duration of the puzzle solution execution.
 * @param parseDuration The duration of the puzzle input parsing, if applicable.
 */
private fun printSolution(day: Int, part: Int, solution: Any, duration: Duration, parseDuration: Duration? = null) {
	val suffix = if (parseDuration != null) " + $parseDuration parsing" else ""
	val value = solution.toString()

	if (value.contains('\n')) {
		logger.info { "Day $day Part $part: ($duration$suffix)" }
		logger.info { value.trim() }
	} else {
		logger.info { "Day $day Part $part: $value ($duration$suffix)" }
	}
}

/** The instance of the logger for this class */
private val logger = InlineLogger()
