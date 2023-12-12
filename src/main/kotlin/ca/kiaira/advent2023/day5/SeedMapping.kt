package ca.kiaira.advent2023.day5

/**
 * Represents a series of mappings used for transforming seed numbers.
 *
 * This class encapsulates a list of mapping ranges, each defining a transformation
 * from a source number range to a destination number range.
 *
 * @property mappings The list of [SeedMappingRange]s defining the transformation logic.
 *
 * @author Muthigani Kiaira <mukiaira@gmail.com>
 * @since December 10th, 2023
 */
class SeedMapping(private val mappings: List<SeedMappingRange>) {

	/**
	 * Transforms a given seed number according to the defined mapping ranges.
	 *
	 * For each seed, it finds the first applicable mapping range and applies the
	 * transformation. If no mapping range is applicable, the original seed number is returned.
	 *
	 * @param seed The seed number to be transformed.
	 * @return The transformed seed number or the original number if no mapping applies.
	 */
	fun mapSeed(seed: Long): Long =
		mappings.firstOrNull { seed in it.sourceRange }?.mapSourceToDestination(seed) ?: seed

	/**
	 * Creates a new [SeedMapping] instance with the mappings reversed.
	 *
	 * Each mapping range is reversed (source becomes destination and vice versa),
	 * and the order of mappings is reversed as well.
	 *
	 * @return A new [SeedMapping] instance with reversed mappings.
	 */
	fun reversed(): SeedMapping = SeedMapping(mappings.map { it.reversed() }.asReversed())

	/**
	 * Reverses a [SeedMappingRange].
	 *
	 * This is a helper function to reverse the source and destination of a mapping range.
	 *
	 * @return A new [SeedMappingRange] with reversed source and destination.
	 */
	private fun SeedMappingRange.reversed() = SeedMappingRange(sourceStart, destinationStart, rangeLength)
}
