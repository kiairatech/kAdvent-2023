package ca.kiaira.advent2023.day5

import ca.kiaira.advent2023.Puzzle

/**
 * Solves the Day 5 puzzle of Advent of Code.
 *
 * The puzzle involves processing a list of seed numbers through a series of mappings
 * to transform each seed number and determine specific results based on these transformations.
 *
 * @constructor Creates an instance of Day5 puzzle solver which extends the Puzzle class.
 *
 * @author Muthigani Kiaira <mukiaira@gmail.com>
 * @since December 10th, 2023
 */
object Day5 : Puzzle<SeedMappingData>(5) {

	/**
	 * Parses the input data for the puzzle.
	 *
	 * The input is expected to start with a line of seed numbers followed by several blocks
	 * of mapping data. Each block of mapping data consists of multiple lines that define
	 * a transformation mapping from a source range to a destination range.
	 *
	 * @param input A sequence of strings representing the lines of puzzle input.
	 * @return The parsed seed numbers and their corresponding mapping data as [SeedMappingData].
	 */
	override fun parse(input: Sequence<String>): SeedMappingData {
		val lines = input.toList()
		val seeds = lines.first().removePrefix("seeds: ").split(" ").map(String::toLong)

		val mappings = lines.drop(2).joinToString("\n").split("\n\n").map { block ->
			SeedMapping(block.split("\n").drop(1).map { line ->
				line.split(" ").map(String::toLong).let { SeedMappingRange(it[0], it[1], it[2]) }
			})
		}

		return SeedMappingData(seeds, mappings)
	}

	/**
	 * Solves Part 1 of the Day 5 puzzle.
	 *
	 * This method sequentially applies each mapping to the list of seed numbers
	 * and finds the minimum value among the final transformed seeds.
	 *
	 * @param input The parsed puzzle input data as [SeedMappingData].
	 * @return The smallest transformed seed number as a result of applying all mappings.
	 */
	override fun solvePart1(input: SeedMappingData): Any = input.mappings.fold(input.seeds) { acc, mapping ->
		acc.map(mapping::mapSeed)
	}.minOrNull() ?: -1L

	/**
	 * Solves Part 2 of the Day 5 puzzle.
	 *
	 * This method involves more complex logic dealing with ranges of seed numbers. It reverses
	 * the order of the conversion maps and finds the first number that, after applying all
	 * reversed mappings, falls within any specified seed range.
	 *
	 * @param input The parsed puzzle input data as [SeedMappingData].
	 * @return The first number that meets the criteria defined for Part 2 of the puzzle.
	 */
	override fun solvePart2(input: SeedMappingData): Any {
		val seedRanges = input.seeds.windowed(2, 2, false).map { it[0] until it[0] + it[1] }
		val reversedMapping = input.mappings.asReversed().map(SeedMapping::reversed)

		return generateSequence(0L) { it + 1 }.first { location ->
			val finalSeed = reversedMapping.fold(location) { acc, map ->
				map.mapSeed(acc)
			}
			seedRanges.any { finalSeed in it }
		}
	}
}
