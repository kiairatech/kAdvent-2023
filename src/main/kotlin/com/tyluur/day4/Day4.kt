package com.tyluur.day4

import com.tyluur.Puzzle

/**
 * Solver for Day 4 of the Advent of Code puzzle "Scratchcards".
 *
 * @property filePath The path to the input file containing scratchcard data.
 */
object Day4 : Puzzle<List<Scratchcard>>(4) {
	/**
	 * Parses the input data for the puzzle.
	 *
	 * @param input The input data as a sequence of strings.
	 * @return A list of [Scratchcard] objects representing each line in the input data.
	 */
	override fun parse(input: Sequence<String>): List<Scratchcard> {
		return input.map(Scratchcard::parse).toList()
	}

	/**
	 * Solves Part 1 of the puzzle.
	 *
	 * @param input The list of [Scratchcard] objects parsed from the input data.
	 * @return The total points calculated from all scratchcards.
	 */
	override fun solvePart1(input: List<Scratchcard>): Int {
		return input.sumOf { it.calculatePoints() }
	}

	/**
	 * Solves Part 2 of the puzzle.
	 *
	 * @param input The list of [Scratchcard] objects parsed from the input data.
	 * @return The total number of scratchcards won, including originals and copies.
	 */
	override fun solvePart2(input: List<Scratchcard>): Int {
		val counts = MutableList(input.size) { 1 }
		for ((index, card) in input.withIndex()) {
			repeat(card.winningNumbersCount()) { offset ->
				if (index + offset + 1 < input.size) {
					counts[index + offset + 1] += counts[index]
				}
			}
		}
		return counts.sum()
	}
}

/**
 * Data class representing a scratchcard with winning numbers and owned numbers.
 *
 * @property winningNumbers Set of integers representing winning numbers.
 * @property ownedNumbers Set of integers representing the numbers you own.
 */
data class Scratchcard(val winningNumbers: Set<Int>, val ownedNumbers: Set<Int>) {
	/**
	 * Calculates the points for this scratchcard based on the number of matching numbers.
	 *
	 * @return The calculated points.
	 */
	fun calculatePoints(): Int {
		return 1.shl(winningNumbersCount()).coerceAtLeast(1) - 1
	}

	/**
	 * Calculates the count of winning numbers that match the owned numbers.
	 *
	 * @return The count of matching winning numbers.
	 */
	fun winningNumbersCount(): Int {
		return winningNumbers.intersect(ownedNumbers).size
	}

	/**
	 * Companion object containing functions for parsing scratchcards from strings.
	 */
	companion object {
		/**
		 * Parses a scratchcard from a string representation.
		 *
		 * @param s The string representing the scratchcard.
		 * @return The parsed [Scratchcard] object.
		 */
		fun parse(s: String): Scratchcard {
			val parts = s.split(": ", " | ")
			val winningNumbers = parts[1].split(" ").filter { it.isNotEmpty() }.map(String::toInt).toSet()
			val ownedNumbers = parts[2].split(" ").filter { it.isNotEmpty() }.map(String::toInt).toSet()
			return Scratchcard(winningNumbers, ownedNumbers)
		}
	}
}
