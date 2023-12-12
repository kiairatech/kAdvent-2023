package ca.kiaira.advent2023.day12

/**
 * Encapsulates the puzzle input consisting of spring conditions and damaged groups.
 *
 * @property springConditions A list of [SpringCondition] representing each row's spring condition.
 * @property damagedGroups A list of [DamagedGroup] representing groups of damaged springs.
 */
class SpringConditionRecords(
	val springConditions: List<SpringCondition>,
	val damagedGroups: List<DamagedGroup>
)
