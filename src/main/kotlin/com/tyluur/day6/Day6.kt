package com.tyluur.day6

import com.tyluur.Puzzle
import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.sqrt

/**
 * Class responsible for solving the Day 6 puzzle of Advent of Code.
 *
 * This class extends the Puzzle class, providing implementations for parsing the input
 * and solving the two parts of the puzzle.
 *
 * @author Tyluur <contact@tyluur.com>
 * @since December 11th, 2023
 */
object Day6 : Puzzle<List<Race>>(6) {

	/**
	 * Parses a sequence of strings into a list of Race objects.
	 * Assumes the first line contains race times and the second line contains records, separated by spaces.
	 *
	 * @param input The input sequence of strings.
	 * @return A list of Race objects.
	 */
	override fun parse(input: Sequence<String>): List<Race> {
		val lines = input.toList()
		val raceTimes = lines[0].split("\\s+".toRegex()).drop(1).map { it.toLong() }
		val records = lines[1].split("\\s+".toRegex()).drop(1).map { it.toLong() }
		return raceTimes.zip(records).map { Race(it.first, it.second) }
	}

	/**
	 * Solves the first part of the puzzle.
	 * Calculates the product of the number of ways to beat each race record.
	 *
	 * @param input List of Race objects.
	 * @return The product of ways to beat each race's record.
	 */
	override fun solvePart1(input: List<Race>): Long {
		return input.fold(1L) { acc, race ->
			acc * calculateWaysToBeatRecord(race)
		}
	}

	/**
	 * Solves the second part of the puzzle.
	 * Combines all races into one and calculates the ways to beat the combined race record.
	 *
	 * @param input List of Race objects.
	 * @return The number of ways to beat the combined race record.
	 */
	override fun solvePart2(input: List<Race>): Long {
		val combinedRace = combineRaces(input)
		return calculateWaysToBeatRecord(combinedRace)
	}

	/**
	 * Calculates the number of ways to beat a race record.
	 * Utilizes the quadratic equation to determine the range of possible solutions.
	 *
	 * @param race The Race object.
	 * @return The number of ways to beat the race record.
	 */
	private fun calculateWaysToBeatRecord(race: Race): Long {
		val (time, record) = race
		val discriminantSqrt = sqrt(time.toDouble() * time - 4.0 * record)
		var possibleRoot1 = floor((time + discriminantSqrt) / 2).toLong()
		var possibleRoot2 = ceil((time - discriminantSqrt) / 2).toLong()
		if (possibleRoot1 * (time - possibleRoot1) <= record) possibleRoot1--
		if (possibleRoot2 * (time - possibleRoot2) <= record) possibleRoot2++
		return possibleRoot1 - possibleRoot2 + 1
	}

	/**
	 * Combines multiple races into a single race.
	 * Concatenates the times and records of individual races.
	 *
	 * @param races The list of Race objects.
	 * @return A combined Race object.
	 */
	private fun combineRaces(races: List<Race>): Race {
		val combinedRaceTime = races.map { it.time }.joinToString("").toLong()
		val combinedRecord = races.map { it.record }.joinToString("").toLong()
		return Race(combinedRaceTime, combinedRecord)
	}
}