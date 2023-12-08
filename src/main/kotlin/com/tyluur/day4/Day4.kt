package com.tyluur.day4

import java.io.File

/**
 * Solver for Day 4 of the Advent of Code puzzle "Scratchcards".
 *
 * @property filePath The path to the input file containing scratchcard data.
 */
class Day4(private val filePath: String) {

	/**
	 * Lazy-loaded list of scratchcards parsed from the input file.
	 */
	private val scratchcards: List<Scratchcard> by lazy { parseScratchcards() }

	/**
	 * Parses the input file to create a list of scratchcards.
	 *
	 * @return A list of [Scratchcard] objects representing each line in the input file.
	 */
	private fun parseScratchcards(): List<Scratchcard> {
		return File(filePath).readLines().map { line ->
			// Splitting the line into parts and then extracting winning and owned numbers
			val parts = line.split(": ", " | ")
			val winningNumbers = parts[1].split(" ").filter { it.isNotEmpty() }.map(String::toInt).toSet()
			val ownedNumbers = parts[2].split(" ").filter { it.isNotEmpty() }.map(String::toInt).toSet()
			Scratchcard(winningNumbers, ownedNumbers)
		}
	}

	/**
	 * Solves Part 1 of the puzzle.
	 *
	 * @return The total points calculated from all scratchcards.
	 */
	fun solvePart1(): Int {
		return scratchcards.sumOf { it.calculatePoints() }
	}

	/**
	 * Solves Part 2 of the puzzle.
	 *
	 * @return The total number of scratchcards won, including originals and copies.
	 */
	fun solvePart2(): Int {
		val counts = MutableList(scratchcards.size) { 1 }
		for ((index, card) in scratchcards.withIndex()) {
			repeat(card.winningNumbersCount) { offset ->
				if (index + offset + 1 < scratchcards.size) {
					counts[index + offset + 1] += counts[index]
				}
			}
		}
		return counts.sum()
	}

	/**
	 * Data class representing a scratchcard with winning numbers and owned numbers.
	 *
	 * @property winningNumbers Set of integers representing winning numbers.
	 * @property ownedNumbers Set of integers representing the numbers you own.
	 */
	data class Scratchcard(val winningNumbers: Set<Int>, val ownedNumbers: Set<Int>) {
		val winningNumbersCount: Int = winningNumbers.intersect(ownedNumbers).size

		/**
		 * Calculates the points for this scratchcard based on the number of matching numbers.
		 *
		 * @return The calculated points.
		 */
		fun calculatePoints(): Int {
			return 1.shl(winningNumbersCount).coerceAtLeast(1) - 1
		}
	}
}

/**
 * Main function for solving Advent of Code 2023, Day 4 puzzles.
 */
fun main() {
	val solver = Day4("src/main/resources/day-4-input.txt")
	println("Total points (Part 1): ${solver.solvePart1()}")
	println("Total scratchcards won (Part 2): ${solver.solvePart2()}")
}
