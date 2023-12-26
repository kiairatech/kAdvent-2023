package ca.kiaira.advent2023

import ca.kiaira.advent2023.day1.Day1
import ca.kiaira.advent2023.day10.Day10
import ca.kiaira.advent2023.day11.Day11
import ca.kiaira.advent2023.day12.Day12
import ca.kiaira.advent2023.day13.Day13
import ca.kiaira.advent2023.day14.Day14
import ca.kiaira.advent2023.day15.Day15
import ca.kiaira.advent2023.day2.Day2
import ca.kiaira.advent2023.day25.Day25
import ca.kiaira.advent2023.day3.Day3
import ca.kiaira.advent2023.day4.Day4
import ca.kiaira.advent2023.day5.Day5
import ca.kiaira.advent2023.day6.Day6
import ca.kiaira.advent2023.day7.Day7
import ca.kiaira.advent2023.day8.Day8
import ca.kiaira.advent2023.day9.Day9
import com.github.michaelbull.logging.InlineLogger
import kotlin.io.path.Path
import kotlin.io.path.writeText
import kotlin.time.Duration
import kotlin.time.measureTimedValue

/**
 * The main class for the Advent of Code puzzle solutions.
 * This class runs the puzzle solutions, measures their execution times,
 * and generates a benchmark report.
 */
object Main {

	/** StringBuilder to accumulate benchmark results. */
	private val benchmarkResults = StringBuilder()

	/**
	 * The main entry point of the application.
	 *
	 * @param args Command line arguments. If a specific day number is provided, only that day's puzzle is run.
	 */
	@JvmStatic
	fun main(args: Array<String>) {
		benchmarkResults.append("| Day | Part | Solution | Execution Time | Parsing Time |\n")
		benchmarkResults.append("| --- | ---- | -------- | ------------- | ------------ |\n")

		val puzzles = mutableListOf<Puzzle<*>>(
			Day1, Day2, Day3, Day4, Day5, Day6, Day7, Day8, Day9, Day10, Day11, Day12, Day13, Day14, Day15, Day25
		)

		args.firstOrNull()?.toIntOrNull()?.let { day ->
			puzzles.removeIf { puzzle -> puzzle.number != day }
		}

		for (puzzle in puzzles) {
			solve(puzzle)
		}

		Path("benchmark.md").writeText(benchmarkResults.toString())
	}

	/**
	 * Solves a given puzzle and appends the results to the benchmark report.
	 *
	 * @param T The type of the parsed input data for the puzzle.
	 * @param puzzle The puzzle to be solved.
	 */
	private fun <T> solve(puzzle: Puzzle<T>) {
		// Measure time taken to parse the puzzle input.
		val input = measureTimedValue {
			puzzle.parse().getOrThrow()
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

		// Append benchmark results
		appendBenchmarkResult(puzzle.number, 1, solutionPart1.value, solutionPart1.duration, input.duration)
		if (solutionPart2.value != null) {
			appendBenchmarkResult(puzzle.number, 2, solutionPart2.value!!, solutionPart2.duration)
		}
	}

	/**
	 * Appends the results of a puzzle solution to the benchmark report.
	 *
	 * @param day The day number of the puzzle.
	 * @param part The part number of the puzzle solution.
	 * @param solution The solution to the puzzle.
	 * @param duration The duration of the puzzle solution execution.
	 * @param parseDuration The duration of the puzzle input parsing, if applicable.
	 */
	private fun appendBenchmarkResult(
		day: Int, part: Int, solution: Any, duration: Duration, parseDuration: Duration? = null
	) {
		val parseTime = parseDuration?.toString() ?: "-"
		benchmarkResults.append("| $day | $part | $solution | ${duration.inWholeMilliseconds} ms | $parseTime |\n")
	}

	/**
	 * Prints the solution of a puzzle along with the time taken for execution.
	 *
	 * @param day The day number of the puzzle.
	 * @param part The part number of the puzzle solution.
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

	/** Logger instance for logging execution details. */
	private val logger = InlineLogger()
}
