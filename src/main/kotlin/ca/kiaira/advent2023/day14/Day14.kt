package ca.kiaira.advent2023.day14

import ca.kiaira.advent2023.Puzzle
import ca.kiaira.advent2023.day14.Day14.GridState

class Day14 : Puzzle<GridState>(14) {

	override fun parse(input: Sequence<String>): GridState {
		val lines = input.toList()
		val grid = lines.map { line ->
			line.map { char -> char == 'O' }.toBooleanArray()
		}

		return GridState(grid, lines.first().length, lines.size)
	}

	override fun solvePart1(input: GridState): Any {
		return input.calculateTotalLoad()
	}

	override fun solvePart2(input: GridState): Any {
		return input.runSpinCycle(1_000_000_000)
	}

	data class GridState(private val grid: List<BooleanArray>, val width: Int, val height: Int) {
		fun calculateTotalLoad(): Int {
			var totalLoad = 0
			for (y in 0 until height) {
				for (x in 0 until width) {
					if (grid[y][x]) {
						// The load is equal to the number of rows from the rock to the south edge of the platform
						totalLoad += height - y
					}
				}
			}
			return totalLoad
		}

		fun runSpinCycle(cycles: Int): Int {
			repeat(cycles) {
				tiltNorth()
				tiltWest()
				tiltSouth()
				tiltEast()
			}
			return calculateTotalLoad()
		}

		private fun tiltNorth() {
			for (x in 0 until width) {
				var emptySpace = -1
				for (y in 0 until height) {
					if (!grid[y][x] && emptySpace == -1) {
						emptySpace = y
					} else if (grid[y][x] && emptySpace != -1) {
						grid[y][x] = false
						grid[emptySpace][x] = true
						emptySpace++
					}
				}
			}
		}

		private fun tiltWest() {
			for (y in 0 until height) {
				var emptySpace = -1
				for (x in 0 until width) {
					if (!grid[y][x] && emptySpace == -1) {
						emptySpace = x
					} else if (grid[y][x] && emptySpace != -1) {
						grid[y][x] = false
						grid[y][emptySpace] = true
						emptySpace++
					}
				}
			}
		}

		private fun tiltSouth() {
			for (x in 0 until width) {
				var emptySpace = -1
				for (y in height - 1 downTo 0) {
					if (!grid[y][x] && emptySpace == -1) {
						emptySpace = y
					} else if (grid[y][x] && emptySpace != -1) {
						grid[y][x] = false
						grid[emptySpace][x] = true
						emptySpace--
					}
				}
			}
		}

		private fun tiltEast() {
			for (y in 0 until height) {
				var emptySpace = -1
				for (x in width - 1 downTo 0) {
					if (!grid[y][x] && emptySpace == -1) {
						emptySpace = x
					} else if (grid[y][x] && emptySpace != -1) {
						grid[y][x] = false
						grid[y][emptySpace] = true
						emptySpace--
					}
				}
			}
		}
	}
}

fun main() {
	val puzzle = Day14()
	puzzle.solve()
}
