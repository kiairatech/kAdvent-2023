package com.tyluur.day10

class PipeMazeSolver(private val grid: List<String>) {

	private val dirs = listOf(listOf(0, -1), listOf(1, 0), listOf(0, 1), listOf(-1, 0))
	private val options = mapOf(
		'|' to listOf(true, false, true, false),
		'-' to listOf(false, true, false, true),
		'L' to listOf(true, true, false, false),
		'J' to listOf(true, false, false, true),
		'7' to listOf(false, false, true, true),
		'F' to listOf(false, true, true, false),
		'.' to listOf(false, false, false, false),
		'S' to listOf(true, true, true, true)
	)

	companion object {
		fun fromInput(input: List<String>): PipeMazeSolver = PipeMazeSolver(input)
	}

	fun countSteps(): Int {
		val startY = grid.indexOfFirst { it.contains('S') }
		val startX = grid[startY].indexOf('S')
		var x = startX
		var y = startY
		var steps = 0
		var lastD = -1
		while (true) {
			for (d in dirs.indices) {
				if (d == (lastD + 2) % 4) continue // No turning back
				val nx = x + dirs[d][0]
				val ny = y + dirs[d][1]
				if (!(nx in grid[0].indices && ny in grid.indices)) continue // Out of bounds
				if (options[grid[y][x]]!![d] && options[grid[ny][nx]]!![(d + 2) % 4]) {
					x = nx
					y = ny
					lastD = d
					steps++
					break
				}
			}
			if (x == startX && y == startY) break
		}
		return steps / 2
	}

}