package ca.kiaira.advent2023.day5

/**
 * Represents a single range mapping for seed transformation.
 *
 * This data class encapsulates the logic for mapping a seed number from a source range
 * to a destination range. The mapping is defined by the start of the destination range,
 * the start of the source range, and the length of the range.
 *
 * @property destinationStart The start of the destination range for the mapping.
 * @property sourceStart The start of the source range for the mapping.
 * @property rangeLength The length of the source and destination ranges.
 *
 * @author Muthigani Kiaira <mukiaira@gmail.com>
 * @since December 10th, 2023
 */
data class SeedMappingRange(
	val destinationStart: Long,
	val sourceStart: Long,
	val rangeLength: Long
) {
	/**
	 * The range of source numbers that this mapping applies to.
	 */
	val sourceRange: LongRange get() = sourceStart until sourceStart + rangeLength

	/**
	 * Maps a given source number to its corresponding destination number according to this mapping range.
	 *
	 * If the source number is within the source range, it is mapped to a number in the destination range.
	 * Otherwise, the source number is returned unchanged.
	 *
	 * @param source The source number to be mapped.
	 * @return The mapped destination number or the original source number if outside the source range.
	 */
	fun mapSourceToDestination(source: Long): Long =
		if (source in sourceRange) destinationStart + (source - sourceStart) else source
}