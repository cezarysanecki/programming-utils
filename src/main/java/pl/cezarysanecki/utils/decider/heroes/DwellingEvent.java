package pl.cezarysanecki.utils.decider.heroes;

import java.util.UUID;

sealed interface DwellingEvent {

    UUID dwellingId();

    record CreatureRecruited(
            UUID dwellingId,
            UUID creatureId,
            int recruited,
            Cost totalCost
    ) implements DwellingEvent {
    }

    record AvailableCreaturesChanged(
            UUID dwellingId,
            UUID creatureId,
            int changedTo
    ) implements DwellingEvent {
    }

}
