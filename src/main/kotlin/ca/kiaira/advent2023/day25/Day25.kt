package ca.kiaira.advent2023.day25

import ca.kiaira.advent2023.Puzzle
import org.jgrapht.alg.StoerWagnerMinimumCut
import org.jgrapht.graph.DefaultEdge
import org.jgrapht.graph.DefaultUndirectedGraph

/**
 * Day 25 Puzzle Solution.
 *
 * @constructor Initializes the Day25 puzzle.
 *
 * @author Muthigani Kiaira <mukiaira@gmail.com>
 * @since December 25th, 2023
 */
object Day25 : Puzzle<Pair<List<String>, List<Pair<String, String>>>>(25) {

	/**
	 * Parses the input data into a Pair of component names and wire connections.
	 *
	 * @param input Sequence of strings representing puzzle input.
	 * @return Pair containing a list of component names and a list of wire connections.
	 */
	override fun parse(input: Sequence<String>): Pair<List<String>, List<Pair<String, String>>> {
		val components = mutableListOf<String>()
		val wires = mutableListOf<Pair<String, String>>()

		// Iterate through each line of the input
		for (line in input) {
			// Extract individual component connections using a regular expression
			val connections = Regex("\\w+").findAll(line).map { it.value }.toList()
			val name = connections.first()

			// Add the component name to the list
			components.add(name)

			// Create pairs of connections and add them to the list of wires
			connections.drop(1).forEach { s ->
				wires.add(Pair(name, s))
			}
		}

		// Return the Pair of components and wires
		return Pair(components, wires)
	}

	/**
	 * Solves Part 1 of the puzzle.
	 *
	 * @param input Pair containing a list of component names and a list of wire connections.
	 * @return The solution to Part 1.
	 */
	override fun solvePart1(input: Pair<List<String>, List<Pair<String, String>>>): Any {
		val (components, wires) = input
		val graph = DefaultUndirectedGraph<String, DefaultEdge>(DefaultEdge::class.java)

		// Build an undirected graph based on the wire connections
		for (line in wires) {
			val (name, connection) = line
			// Add vertices for the components
			graph.addVertex(name)
			graph.addVertex(connection)
			// Add edges between connected components
			graph.addEdge(name, connection)
		}

		// Use StoerWagnerMinimumCut algorithm to find minimum cut
		val stoerWagnerMinimumCut = StoerWagnerMinimumCut(graph)
		val minimumCutSize = stoerWagnerMinimumCut.minCut().size

		// Calculate and return the solution based on the sizes of connected components
		return minimumCutSize * (graph.vertexSet().size - minimumCutSize)
	}

}