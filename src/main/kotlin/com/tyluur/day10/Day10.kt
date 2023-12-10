package com.tyluur.day10

import com.tyluur.Puzzle

object Day10 : Puzzle<List<String>>(10) {

	override fun parse(input: Sequence<String>): List<String> = input.toList()

	override fun solvePart1(input: List<String>): Any {
		val (start, direction) = findStart(input)
		var point = start
		var currentDirection = direction
		var loopSize = 0

		while (true) {
			if (point == start && loopSize > 0) break
			val result = traverse(point, currentDirection, input)
			point = result.first
			currentDirection = result.second
			loopSize++
		}

		return loopSize / 2
	}

	private fun findStart(input: List<String>): Pair<Pair<Int, Int>, Int> {
		val y = input.indexOfFirst { line -> 'S' in line }
		val x = input[y].indexOf('S')
		val start = Pair(x, y)

		val direction = when {
			input.getOrNull(y - 1)?.get(x) in listOf('7', '|', 'F') -> 1
			input[y].getOrNull(x - 1) in listOf('F', '-', 'L') -> 2
			else -> 3 // Assuming the 'S' pipe connects to two directions.
		}
		return Pair(start, direction)
	}

	private fun traverse(point: Pair<Int, Int>, direction: Int, input: List<String>): Pair<Pair<Int, Int>, Int> {
		val (x, y) = point
		var newDirection = direction
		val newPoint = when (direction) {
			1 -> Pair(x, y - 1).also {
				newDirection = when (input[y - 1][x]) {
					'7' -> 2
					'F' -> 3
					else -> direction
				}
			}

			2 -> Pair(x - 1, y).also {
				newDirection = when (input[y][x - 1]) {
					'L' -> 1
					'F' -> 4
					else -> direction
				}
			}

			3 -> Pair(x + 1, y).also {
				newDirection = when (input[y][x + 1]) {
					'J' -> 1
					'7' -> 4
					else -> direction
				}
			}

			4 -> Pair(x, y + 1).also {
				newDirection = when (input[y + 1][x]) {
					'L' -> 3
					'J' -> 2
					else -> direction
				}
			}

			else -> point
		}
		return Pair(newPoint, newDirection)
	}

}