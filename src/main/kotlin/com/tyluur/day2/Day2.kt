package com.tyluur.day2

import com.github.michaelbull.logging.InlineLogger
import java.io.File

fun main() {
	val filePath = "src/main/resources/day-2-input.txt"
	val games = parseGames(filePath)
	val feasibleGamesSum = games.filter { it.isFeasible(12, 13, 14) }.sumOf { it.id }
	logger.info { "Sum of IDs of feasible games: $feasibleGamesSum" }
}

data class Game(val id: Int, val subsets: List<Map<String, Int>>) {
	fun isFeasible(maxRed: Int, maxGreen: Int, maxBlue: Int): Boolean {
		return subsets.all { subset ->
			subset.getOrDefault("red", 0) <= maxRed && subset.getOrDefault(
				"green", 0
			) <= maxGreen && subset.getOrDefault("blue", 0) <= maxBlue
		}
	}
}

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

fun parseSubset(subset: String): Map<String, Int> {
	val counts = mutableMapOf<String, Int>()
	val colorCounts = subset.split(", ")
	for (colorCount in colorCounts) {
		val (count, color) = colorCount.split(" ")
		counts[color] = count.toInt()
	}
	return counts
}

private val logger = InlineLogger()
