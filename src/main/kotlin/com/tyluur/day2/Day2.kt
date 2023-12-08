package com.tyluur.day2

import com.github.michaelbull.logging.InlineLogger
import java.io.File

/**
 * Main function for solving Advent of Code 2023, Day 2 puzzles.
 */
fun main() {
	val filePath = "src/main/resources/day-2-input.txt" // Path to the input file
	val games = parseGames(filePath)

	// Part 1: Sum of IDs of feasible games
	val feasibleGamesSum = games.filter { it.isFeasible(12, 13, 14) }.sumOf { it.id }
	logger.info { "Sum of IDs of feasible games: $feasibleGamesSum" }

	// Part 2: Sum of the power of minimum sets
	val totalPowerSum = games.sumOf { it.calculatePower() }
	logger.info { "Sum of the power of minimum sets: $totalPowerSum" }
}

/**
 * Data class representing a single game with its ID and subsets of cubes.
 *
 * @param id The unique identifier of the game.
 * @param subsets The list of subsets of cubes revealed in each game.
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
	 *
	 * @return The product of the minimum number of red, green, and blue cubes.
	 */
	fun calculatePower(): Int {
		val minRed = subsets.maxOfOrNull { it.getOrDefault("red", 0) } ?: 0
		val minGreen = subsets.maxOfOrNull { it.getOrDefault("green", 0) } ?: 0
		val minBlue = subsets.maxOfOrNull { it.getOrDefault("blue", 0) } ?: 0
		return minRed * minGreen * minBlue
	}
}

/**
 * Parses the game data from a file.
 *
 * @param filePath The path to the file containing game data.
 * @return A list of [Game] objects parsed from the file.
 */
fun parseGames(filePath: String): List<Game> {
	val games = mutableListOf<Game>()
	File(filePath).forEachLine { line ->
		val parts = line.split(": ")
		val id = parts[0].removePrefix("Game ").toInt()
		val subsets = parts[1].split("; ").map { parseSubset(it) }
		games.add(Game(id, subsets))
	}
	return games
}

/**
 * Parses a subset of cubes from a string.
 *
 * @param subset The string representation of a subset.
 * @return A map of cube colors to their counts.
 */
fun parseSubset(subset: String): Map<String, Int> {
	val counts = mutableMapOf<String, Int>()
	val colorCounts = subset.split(", ")
	for (colorCount in colorCounts) {
		val (count, color) = colorCount.split(" ")
		counts[color] = count.toInt()
	}
	return counts
}

/** Logger for logging information. */
private val logger = InlineLogger()
