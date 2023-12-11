package com.tyluur.day1

import com.tyluur.Puzzle

/**
 * The Day 1 puzzle solution for Advent of Code 2023.
 * The puzzle involves calculating calibration values from a list of strings.
 * Part 1 calculates these directly, while Part 2 includes converting spelled-out numbers to digits.
 *
 * @author Tyluur
 * @since December 8th, 2023
 */
object Day1 : Puzzle<List<String>>(1) {

	/**
	 * Mapping of spelled-out numbers to their respective digits
	 */
	private val numberMapping = mapOf(
		"one" to "1",
		"two" to "2",
		"three" to "3",
		"four" to "4",
		"five" to "5",
		"six" to "6",
		"seven" to "7",
		"eight" to "8",
		"nine" to "9"
	)

	/**
	 * Parses the input sequence of strings into a list of strings.
	 *
	 * @param input The input data as a sequence of strings.
	 * @return A list of strings representing the puzzle input.
	 */
	override fun parse(input: Sequence<String>): List<String> = input.toList()

	/**
	 * Solves Part 1 of the Day 1 puzzle.
	 * Sums up calibration values obtained from each line of input without replacing spelled-out numbers.
	 *
	 * @param input The parsed list of strings.
	 * @return The sum of calibration values for Part 1.
	 */
	override fun solvePart1(input: List<String>): Any = input.sumOf { calculateCalibrationValue(it, false) }

	/**
	 * Solves Part 2 of the Day 1 puzzle.
	 * Sums up calibration values obtained from each line of input, with spelled-out numbers replaced by digits.
	 *
	 * @param input The parsed list of strings.
	 * @return The sum of calibration values for Part 2.
	 */
	override fun solvePart2(input: List<String>): Any = input.sumOf { calculateCalibrationValue(it, true) }

	/**
	 * Calculates the calibration value of a single line.
	 * Optionally replaces spelled-out numbers with digits before calculation.
	 *
	 * @param line The line of text to process.
	 * @param replaceWords Flag to indicate whether spelled-out numbers should be replaced.
	 * @return The calculated calibration value of the line.
	 */
	private fun calculateCalibrationValue(line: String, replaceWords: Boolean): Int {
		val processedLine = if (replaceWords) replaceSpelledOutNumbers(line) else line
		val digits = processedLine.filter { it.isDigit() }
		return when {
			digits.length >= 2 -> "${digits.first()}${digits.last()}".toInt()
			digits.length == 1 -> "${digits.first()}${digits.first()}".toInt()
			else -> 0
		}
	}

	/**
	 * Replaces all spelled-out numbers in a line with their corresponding digit representations.
	 *
	 * @param line The line of text to process.
	 * @return The line with all spelled-out numbers replaced by digits.
	 */
	private fun replaceSpelledOutNumbers(line: String): String =
		numberMapping.entries.fold(line) { acc, (word, digit) -> acc.replace(word, digit) }
}
