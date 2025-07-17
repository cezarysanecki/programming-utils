package pl.cezarysanecki.utils.decider.heroes;

import java.util.UUID;

record Dwelling(
        UUID creatureId,
        Cost costPerTroop,
        int availableCreatures
) {
}
