package pl.cezarysanecki.utils.decider.heroes;

import pl.cezarysanecki.utils.decider.Decider;
import pl.cezarysanecki.utils.decider.heroes.DwellingCommand.RecruitCreature;

import java.util.List;
import java.util.UUID;

import static pl.cezarysanecki.utils.decider.heroes.DwellingCommand.IncreaseAvailableCreatures;
import static pl.cezarysanecki.utils.decider.heroes.DwellingEvent.AvailableCreaturesChanged;
import static pl.cezarysanecki.utils.decider.heroes.DwellingEvent.CreatureRecruited;

public record DwellingDecider(
        UUID creatureId,
        Cost costPerTroop
) implements Decider<DwellingCommand, Dwelling, DwellingEvent> {
    @Override
    public List<DwellingEvent> decide(DwellingCommand command, Dwelling dwelling) {
        return switch (command) {
            case RecruitCreature(UUID dwellingId, UUID creatureId, int amount) -> {
                if (!dwelling.creatureId().equals(creatureId) || dwelling.availableCreatures() < amount) {
                    yield List.of();
                }
                Cost totalCost = costPerTroop.multiply(amount);
                yield List.of(new CreatureRecruited(dwellingId, creatureId, amount, totalCost));
            }
            case IncreaseAvailableCreatures(UUID dwellingId, UUID creatureId, int increaseBy) -> {
                if (!dwelling.creatureId().equals(creatureId)) {
                    yield List.of();
                }
                yield List.of(new AvailableCreaturesChanged(dwellingId, creatureId, dwelling.availableCreatures() + increaseBy));
            }
        };
    }

    @Override
    public Dwelling evolve(Dwelling dwelling, DwellingEvent event) {
        return switch (event) {
            case CreatureRecruited(UUID dwellingId, UUID creatureId, int recruited, Cost totalCost) ->
                    new Dwelling(dwelling.creatureId(), dwelling.costPerTroop(), dwelling.availableCreatures() - recruited);
            case AvailableCreaturesChanged(UUID dwellingId, UUID creatureId, int changedTo) ->
                    new Dwelling(dwelling.creatureId(), dwelling.costPerTroop(), changedTo);
        };
    }

    @Override
    public Dwelling initialState() {
        return new Dwelling(creatureId, costPerTroop, 0);
    }
}

