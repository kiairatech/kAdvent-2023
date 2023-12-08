package com.tyluur.day6

import com.github.michaelbull.logging.InlineLogger
import java.io.File

/**
 * Solver for the Day 6 Advent of Code puzzle, "Wait For It".
 * Calculates the number of ways to win toy boat races based on their duration and record distances.
 *
 * @property filePath The path to the input file containing the race data.
 */
class Day6Solver(private val filePath: String) {
	/**
	 * Parses the input file to create a list of Race objects.
	 *
	 * @return A list of Race objects representing each race in the input file.
	 */
	private fun parseInput(): List<Race> = File(filePath).useLines { lines ->
		val (durations, records) = lines.map { it.substringAfter(":").trim() }
			.take(2)
			.map { parseLongs(it) }
			.toList()
		durations.zip(records).map { (duration, record) -> Race(duration, record) }
	}

	/**
	 * Parses a string containing long numbers separated by spaces.
	 *
	 * @param s The string to be parsed.
	 * @return A list of long numbers extracted from the string.
	 */
	private fun parseLongs(s: String): List<Long> =
		s.split(" +".toRegex()).map(String::toLong)

	/**
	 * Solves Part 1 of the puzzle by calculating the total number of ways to win across all races.
	 *
	 * @return The product of the number of ways to win each race.
	 */
	fun solvePart1(): Int = parseInput().map(Race::countWins).reduce(Int::times)

	/**
	 * Solves Part 2 of the puzzle by calculating the number of ways to win a single, combined race.
	 *
	 * @return The number of ways to win the combined race.
	 */
	fun solvePart2(): Int = File(filePath).useLines { lines ->
		val (duration, record) = lines.map { it.substringAfter(":").replace(" +".toRegex(), "").toLong() }
			.toList()
		Race(duration, record).countWins()
	}
}

/**
 * Represents a race with its duration and record distance.
 *
 * @property duration The duration of the race in milliseconds.
 * @property record The record distance for the race in millimeters.
 */
data class Race(val duration: Long, val record: Long) {
	/**
	 * Calculates the number of ways to win the race by holding the button to charge the toy boat.
	 *
	 * @return The number of ways to win the race.
	 */
	fun countWins(): Int = (0..duration).count { velocity ->
		(duration - velocity) * velocity > record
	}
}

/**
 * Main function to run the solver for Day 6's puzzle.
 */
fun main() {
	val solver = Day6Solver("src/main/resources/day-6-input.txt")
	logger.info { "Number of ways to win (Part 1): ${solver.solvePart1()}" }
	logger.info { "Number of ways to win (Part 2): ${solver.solvePart2()}" }
}

/** The instance of the logger for day 6 */
private val logger = InlineLogger()
