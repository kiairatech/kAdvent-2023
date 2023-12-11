package com.tyluur.day11

import com.tyluur.Puzzle
import kotlin.math.abs

object Day11 : Puzzle<List<List<Char>>>(11) {

	override fun parse(input: Sequence<String>): List<List<Char>> {
		return input.map { it.toCharArray().toList() }.toList()
	}

	override fun solvePart1(input: List<List<Char>>): Any {
		return solve(input, 2)
	}

	override fun solvePart2(input: List<List<Char>>): Any {
		return solve(input, 1000000)
	}

	private fun solve(universe: List<List<Char>>, size: Long): Long {
		val galaxies = mutableListOf<Pair<Int, Int>>()
		for (r in universe.indices) {
			for (c in universe[0].indices) {
				if (universe[r][c] == '#') {
					galaxies.add(r to c)
				}
			}
		}

		val expandedGalaxies = expandUniverse(galaxies, universe, size)
		return calculateShortestPaths(expandedGalaxies)
	}

	private fun expandUniverse(
		galaxies: MutableList<Pair<Int, Int>>,
		universe: List<List<Char>>,
		size: Long
	): List<Pair<Long, Long>> {
		val emptyRows = universe.indices.filter { r -> universe[r].all { it == '.' } }
		val emptyCols = universe[0].indices.filter { c -> universe.all { it[c] == '.' } }

		return galaxies.map { (r, c) ->
			var newR = r.toLong()
			var newC = c.toLong()
			for (er in emptyRows) {
				if (r > er) newR += size - 1
			}
			for (ec in emptyCols) {
				if (c > ec) newC += size - 1
			}
			newR to newC
		}
	}

	private fun calculateShortestPaths(galaxies: List<Pair<Long, Long>>): Long {
		return galaxies.flatMap { galaxy1 ->
			galaxies.map { galaxy2 ->
				abs(galaxy1.first - galaxy2.first) + abs(galaxy1.second - galaxy2.second)
			}
		}.sum() / 2L  // Each pair is counted twice, so divide by 2
	}
}

fun main() {
	val puzzle = Day11
	val input = puzzle.parse()
	println("Part 1: ${puzzle.solvePart1(input)}")
	println("Part 2: ${puzzle.solvePart2(input)}")
}
