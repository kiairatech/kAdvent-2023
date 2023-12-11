package com.tyluur.day1

import com.tyluur.Puzzle

/**
 * Day1 object represents the puzzle for Advent of Code Day 1.
 * It extends the Puzzle class with a list of strings as input data.
 *
 * @constructor Creates a Day1 object with the puzzle number 1.
 *
 * @author Tyluur
 * @since December 10th, 2023
 */
object Day1 : Puzzle<List<String>>(1) {

	// Mapping of spelled-out digits to their corresponding numerical values
	private val digitsMap = mapOf(
		"one"   to 1,
		"two"   to 2,
		"three" to 3,
		"four"  to 4,
		"five"  to 5,
		"six"   to 6,
		"seven" to 7,
		"eight" to 8,
		"nine"  to 9
	)

	/**
	 * Parses the input data for the Day1 puzzle.
	 *
	 * @param input A sequence of strings representing the puzzle input.
	 * @return A list of strings containing the parsed input data.
	 */
	override fun parse(input: Sequence<String>): List<String> = input.toList()

	/**
	 * Solves Part 1 of the Day1 puzzle.
	 *
	 * @param input The parsed input data as a list of strings.
	 * @return The solution to Part 1 of the puzzle.
	 */
	override fun solvePart1(input: List<String>): Any {
		return input.sumOf { line ->
			val first = line.first(Char::isDigit)
			val last = line.last(Char::isDigit)
			"$first$last".toInt()
		}
	}

	/**
	 * Solves Part 2 of the Day1 puzzle.
	 *
	 * @param input The parsed input data as a list of strings.
	 * @return The solution to Part 2 of the puzzle.
	 */
	override fun solvePart2(input: List<String>): Any {
		return input.sumOf { line ->
			val digits = mutableListOf<Int>()
			var temp = line

			while (temp.isNotEmpty()) {
				if (temp.first().isDigit()) {
					digits += temp.first().digitToInt()
				} else {
					digitsMap.forEach { (k, v) ->
						if (temp.startsWith(k)) {
							digits += v
							return@forEach
						}
					}
				}
				temp = temp.drop(1)
			}

			digits.first() * 10 + digits.last()
		}
	}
}
