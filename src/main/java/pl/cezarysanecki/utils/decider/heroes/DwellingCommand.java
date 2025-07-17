package pl.cezarysanecki.utils.decider.heroes;

import java.util.UUID;

sealed interface DwellingCommand {

    UUID dwellingId();

    record RecruitCreature(
            UUID dwellingId,
            UUID creatureId,
            int amount
    ) implements DwellingCommand {
    }

    record IncreaseAvailableCreatures(
            UUID dwellingId,
            UUID creatureId,
            int increaseBy
    ) implements DwellingCommand {
    }

}
