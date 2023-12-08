package com.tyluur.day6

import com.tyluur.Puzzle

/**
 * Solver for the Day 6 Advent of Code puzzle, "Wait For It".
 * Calculates the number of ways to win toy boat races based on their duration and record distances.
 */
object Day6 : Puzzle<List<Race>>(6) {
	/**
	 * Parses the input data for the puzzle.
	 *
	 * @param input The input data as a sequence of strings.
	 * @return The parsed input data.
	 */
	override fun parse(input: Sequence<String>): List<Race> {
		val lines = input.toList()
		val (durations, records) = lines.map { it.substringAfter(":").trim() }
			.take(2)
			.map { parseLongs(it) }
			.toList()
		return durations.zip(records).map { (duration, record) -> Race(duration, record) }
	}

	/**
	 * Solves Part 1 of the puzzle by calculating the total number of ways to win across all races.
	 *
	 * @param input The parsed input data.
	 * @return The solution to Part 1 of the puzzle.
	 */
	override fun solvePart1(input: List<Race>): Int {
		return input.map(Race::countWins).reduce(Int::times)
	}

	/**
	 * Solves Part 2 of the puzzle by calculating the number of ways to win a single, combined race.
	 *
	 * @param input The parsed input data.
	 * @return The solution to Part 2 of the puzzle.
	 */
	override fun solvePart2(input: List<Race>): Int {
		return input[0].countWins()
	}

	/**
	 * Parses a string containing long numbers separated by spaces.
	 *
	 * @param s The string to be parsed.
	 * @return A list of long numbers extracted from the string.
	 */
	private fun parseLongs(s: String) =
		s.split(" +".toRegex()).map(String::toLong)
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
	fun countWins() = (0..duration).count { velocity ->
		(duration - velocity) * velocity > record
	}
}
