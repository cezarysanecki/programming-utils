package pl.cezarysanecki.utils.decider.heroes;

import java.util.Map;
import java.util.stream.Collectors;

record Cost(
        Map<Resource, Integer> resources
) {
    Cost multiply(int multiplier) {
        return new Cost(resources.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue() * multiplier
                )));
    }
}
