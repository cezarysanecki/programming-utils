package pl.cezarysanecki.utils.decider;

import java.util.List;

public interface Decider<C, S, E> {

    List<E> decide(C command, S state);

    S evolve(S state, E event);

    S initialState();

    default List<E> decide(C command, List<E> events) {
        return decide(command, evolve(events));
    }

    default S evolve(List<E> events) {
        S state = initialState();
        for (E event : events) {
            state = evolve(state, event);
        }
        return state;
    }

}
