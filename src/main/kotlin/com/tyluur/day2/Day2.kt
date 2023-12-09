package com.tyluur.day2

import com.github.michaelbull.logging.InlineLogger
import com.tyluur.Puzzle

/**
 * Class representing the Day 2 puzzle for Advent of Code 2023.
 * It includes methods to parse game data and solve the puzzle for each part.
 *
 * @author Tyluur
 * @since December 8th, 2023
 */
object Day2 : Puzzle<List<Game>>(2) {

	/**
	 * Parses the input data into a list of Game objects.
	 *
	 * @param input The input data as a sequence of strings.
	 * @return A list of Game objects.
	 */
	override fun parse(input: Sequence<String>): List<Game> {
		return input.map { line ->
			val parts = line.split(": ")
			val id = parts[0].removePrefix("Game ").toInt()
			val subsets = parts[1].split("; ").map { parseSubset(it) }
			Game(id, subsets)
		}.toList()
	}

	/**
	 * Solves Part 1 of the Day 2 puzzle.
	 * Determines the sum of IDs of games feasible with specific cube limits.
	 *
	 * @param input The parsed list of Game objects.
	 * @return The sum of IDs of feasible games.
	 */
	override fun solvePart1(input: List<Game>): Any {
		return input.filter { it.isFeasible(12, 13, 14) }.sumOf { it.id }
	}

	/**
	 * Solves Part 2 of the Day 2 puzzle.
	 * Calculates the sum of the power of minimum sets of cubes required for each game.
	 *
	 * @param input The parsed list of Game objects.
	 * @return The sum of the power of minimum sets.
	 */
	override fun solvePart2(input: List<Game>): Any {
		return input.sumOf { it.calculatePower() }
	}

	/**
	 * Parses a subset of cubes from a string into a map.
	 *
	 * @param subset The string representation of a subset of cubes.
	 * @return A map representing the count of each color of cubes.
	 */
	private fun parseSubset(subset: String): Map<String, Int> {
		return subset.split(", ").associate { colorCount ->
			val (count, color) = colorCount.split(" ")
			color to count.toInt()
		}
	}
}

/**
 * Data class representing a single game with its ID and subsets of cubes.
 *
 * @param id The unique identifier of the game.
 * @param subsets The list of subsets of cubes revealed in each game.
 *
 * @author Tyluur
 * @since December 8th, 2023
 */
data class Game(val id: Int, val subsets: List<Map<String, Int>>) {

	/**
	 * Determines if a game is feasible with a given maximum number of red, green, and blue cubes.
	 *
	 * @param maxRed The maximum number of red cubes available.
	 * @param maxGreen The maximum number of green cubes available.
	 * @param maxBlue The maximum number of blue cubes available.
	 * @return True if the game is feasible, False otherwise.
	 */
	fun isFeasible(maxRed: Int, maxGreen: Int, maxBlue: Int): Boolean {
		return subsets.all { subset ->
			subset.getOrDefault("red", 0) <= maxRed &&
					subset.getOrDefault("green", 0) <= maxGreen &&
					subset.getOrDefault("blue", 0) <= maxBlue
		}
	}

	/**
	 * Calculates the power of the minimum set of cubes required for the game.
	 * The power is defined as the product of the minimum number of each color of cubes required.
	 *
	 * @return The power of the minimum set of cubes.
	 */
	fun calculatePower(): Int {
		val minRed = subsets.maxOfOrNull { it.getOrDefault("red", 0) } ?: 0
		val minGreen = subsets.maxOfOrNull { it.getOrDefault("green", 0) } ?: 0
		val minBlue = subsets.maxOfOrNull { it.getOrDefault("blue", 0) } ?: 0
		return minRed * minGreen * minBlue
	}
}

/** Logger for logging information. */
private val logger = InlineLogger()
