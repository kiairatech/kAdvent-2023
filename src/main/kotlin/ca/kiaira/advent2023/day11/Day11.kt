package ca.kiaira.advent2023.day11

import ca.kiaira.advent2023.Puzzle
import kotlin.math.abs

/**
 * Solution object for Day 11 puzzle of Advent of Code 2023.
 *
 * @author Muthigani Kiaira <mukiaira@gmail.com>
 * @since December 11th, 2023
 */
object Day11 : Puzzle<List<List<Char>>>(11) {

	/**
	 * Parses the raw input data into a List of Lists containing individual characters.
	 *
	 * @param input The input data as a sequence of lines.
	 * @return A List of Lists representing the puzzle input.
	 */
	override fun parse(input: Sequence<String>): List<List<Char>> {
		return input.map { it.toCharArray().toList() }.toList()
	}

	/**
	 * Solves Part 1 of the Day 11 puzzle.
	 *
	 * @param input The parsed puzzle data.
	 * @return The solution to Part 1 as any type.
	*/
	override fun solvePart1(input: List<List<Char>>): Any {
		return solve(input, 2)
	}

	/**
	 * Solves Part 2 of the Day 11 puzzle.
	 *
	 * @param input The parsed puzzle data.
	 * @return The solution to Part 2 as any type.
	 */
	override fun solvePart2(input: List<List<Char>>): Any {
		return solve(input, 1000000)
	}

	/**
	 * The core logic for solving both parts of the puzzle.
	 *
	 * @param universe The puzzle input represented as a List of Lists containing characters.
	 * @param size The size parameter used in the solution logic.
	 * @return The solution as a Long.
	 */
	private fun solve(universe: List<List<Char>>, size: Long): Long {
		// Identify initial galaxy positions
		val galaxies = mutableListOf<Pair<Int, Int>>()
		for (row in universe.indices) {
			for (col in universe[0].indices) {
				if (universe[row][col] == '#') {
					galaxies.add(row to col)
				}
			}
		}

		// Expand the universe based on size parameter
		val expandedGalaxies = expandUniverse(galaxies, universe, size)

		// Calculate the shortest paths between all expanded galaxies
		return calculateShortestPaths(expandedGalaxies)
	}

	/**
	 * Expands the universe based on the given parameters and calculates new galaxy positions.
	 *
	 * @param galaxies The list of initial galaxy positions.
	 * @param universe The puzzle input represented as a List of Lists containing characters.
	 * @param size The size parameter used for expanding the universe.
	 * @return A List of expanded galaxy positions.
	 */
	private fun expandUniverse(
		galaxies: MutableList<Pair<Int, Int>>,
		universe: List<List<Char>>,
		size: Long
	): List<Pair<Long, Long>> {
		// Identify empty rows and columns
		val emptyRows = universe.indices.filter { row -> universe[row].all { it == '.' } }
		val emptyCols = universe[0].indices.filter { col -> universe.all { it[col] == '.' } }

		// Apply expansion logic for each galaxy
		return galaxies.map { (row, col) ->
			var newRow = row.toLong()
			var newCol = col.toLong()
			for (er in emptyRows) {
				if (row > er) newRow += size - 1
			}
			for (ec in emptyCols) {
				if (col > ec) newCol += size - 1
			}
			newRow to newCol
		}
	}

	/**
	 * Calculates the shortest paths between all pairs of galaxies in the expanded universe.
	 *
	 * @param galaxies The list of expanded galaxy positions.
	 * @return The sum of all shortest paths as a Long.
	 */
	private fun calculateShortestPaths(galaxies: List<Pair<Long, Long>>): Long {
		// Calculate all pairwise distances
		return galaxies.flatMap { galaxy1 ->
			galaxies.map { galaxy2 ->
				abs(galaxy1.first - galaxy2.first) + abs(galaxy1.second - galaxy2.second)
			}
		}.sum() / 2L  // Each pair is counted twice, so divide by 2
	}
}
