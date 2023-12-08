package com.tyluur.day5

import com.github.michaelbull.logging.InlineLogger
import java.io.File

/**
 * Solver for the Day 5 Advent of Code puzzle, "If You Give A Seed A Fertilizer".
 * Parses an almanac to determine the lowest location number corresponding to seed numbers.
 *
 * @property filePath Path to the input file containing the puzzle data.
 */
class Day5Solver(private val filePath: String) {

	/**
	 * Lazy-initialized Almanac object representing the parsed input data.
	 */
	private val almanac: Almanac by lazy { parseAlmanac() }

	/**
	 * Parses the input file to create an Almanac object.
	 * Processes seeds and mappings from the almanac input format.
	 *
	 * @return Almanac object representing the parsed data.
	 */
	private fun parseAlmanac(): Almanac {
		val lines = File(filePath).readText().trim().split("\n\n")
		val seeds = lines.first().substringAfter("seeds: ").split(" ").map { it.toLong() }
		val maps = lines.drop(1).associate { section ->
			val header = section.substringBefore("\n")
			val mapName = header.substringBefore("-to-")
			val mappings = section.substringAfter("\n").lines().map { line ->
				val (destStart, sourceStart, length) = line.split(" ").map(String::toLong)
				Mapping(sourceStart, destStart, length)
			}
			mapName to mappings
		}
		return Almanac(seeds, maps)
	}

	/**
	 * Solves Part 1 of the puzzle.
	 * Determines the lowest location number corresponding to the initial seed numbers.
	 *
	 * @return The lowest location number for the initial seeds.
	 */
	fun solvePart1(): Long {
		return almanac.seeds.map { seed ->
			convertThroughMaps(seed, almanac.maps)
		}.minOrNull() ?: Long.MAX_VALUE
	}

	/**
	 * Solves Part 2 of the puzzle.
	 * Handles seed ranges and determines the lowest location number for these seed ranges.
	 *
	 * @return The lowest location number for the seed ranges.
	 */
	fun solvePart2(): Long {
		val seedRanges = almanac.seeds.chunked(2).flatMap { (start, length) ->
			(start until (start + length)).toList()
		}
		return seedRanges.map { seed ->
			convertThroughMaps(seed, almanac.maps)
		}.minOrNull() ?: Long.MAX_VALUE
	}

	/**
	 * Converts a seed number through a series of mappings to find its corresponding location number.
	 *
	 * @param value The initial seed value to be converted.
	 * @param maps The mappings from the almanac to apply.
	 * @return The final location number after applying all mappings.
	 */
	private fun convertThroughMaps(value: Long, maps: Map<String, List<Mapping>>): Long {
		var result = value
		for ((_, entries) in maps) {
			result = entries.fold(result) { acc, mapping ->
				if (acc in mapping.sourceRange) {
					mapping.destStart + (acc - mapping.sourceStart)
				} else {
					acc
				}
			}
		}
		return result
	}

	/**
	 * Data class representing the almanac with seeds and mappings.
	 *
	 * @property seeds List of initial seed numbers.
	 * @property maps Map of category names to their corresponding mappings.
	 */
	data class Almanac(
		val seeds: List<Long>,
		val maps: Map<String, List<Mapping>>
	)

	/**
	 * Data class representing a mapping entry in the almanac.
	 *
	 * @property sourceStart Starting number of the source range.
	 * @property destStart Starting number of the destination range.
	 * @property length Length of the number range.
	 */
	data class Mapping(
		val sourceStart: Long,
		val destStart: Long,
		val length: Long
	) {
		val sourceRange = sourceStart until sourceStart + length
	}
}

fun main() {
	val solver = Day5Solver("path/to/day-5-input.txt")
	logger.info { "Lowest location number (Part 1): ${solver.solvePart1()}" }
	logger.info { "Lowest location number (Part 2): ${solver.solvePart2()}" }
}

/** The instance of the logger for day 5 */
private val logger = InlineLogger()
