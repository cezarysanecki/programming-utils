# Programming utils

Small, reusable building blocks I come back to when modelling business logic. Each one comes with an example and tests.

## Decider

[`Decider<C, S, E>`](src/main/java/pl/cezarysanecki/utils/decider/Decider.java) is a functional way to write event-sourced logic. It has three parts:

- `decide(command, state)` returns the events a command produces,
- `evolve(state, event)` returns the state after an event,
- `initialState()` is where every stream starts.

The default methods rebuild the state from a list of past events, so a command can be decided straight from history: `decide(command, events)`.

### Example: a creature dwelling

The [`heroes`](src/main/java/pl/cezarysanecki/utils/decider/heroes) package models a dwelling from a strategy game. A dwelling holds creatures of one type, each with a cost in resources.

| Command | Event | Rule |
| --- | --- | --- |
| `IncreaseAvailableCreatures` | `AvailableCreaturesChanged` | only for the dwelling's own creature |
| `RecruitCreature` | `CreatureRecruited` with the total cost | only if enough creatures are available |

A command that breaks a rule produces no events. Commands and events are sealed interfaces with records, so `decide` and `evolve` are exhaustive `switch` expressions.

[`DwellingDeciderSpec`](src/test/groovy/pl/cezarysanecki/utils/decider/heroes/DwellingDeciderSpec.groovy) shows the given / when / then style that fits a decider: past events, a command, expected events.

## Running

Java 21 or newer, Gradle wrapper included.

```bash
./gradlew test
```

## License

MIT, see [`LICENSE`](LICENSE).
