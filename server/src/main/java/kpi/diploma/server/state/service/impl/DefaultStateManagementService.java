package kpi.diploma.server.state.service.impl;

import kpi.diploma.server.state.adapter.ConfigurableStateManagementAdapter;
import kpi.diploma.server.state.adapter.StateManagementAdapter;
import kpi.diploma.server.state.dto.Transition;
import kpi.diploma.server.state.exception.IncorrectStateException;
import kpi.diploma.server.state.service.StateManagementService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import static java.lang.String.format;
import static java.util.Objects.isNull;
import static java.util.Objects.requireNonNull;
import static java.util.Optional.ofNullable;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class DefaultStateManagementService<T, S extends Enum<S>> implements StateManagementService<T, S> {

    private static final String NULL_STATE_ERROR_MESSAGE = "Object state cannot be null";
    private static final String TRANSITION_NOT_FOUND_ERROR_TEMPLATE = "Cannot find transition [%s -> %s] for object";

    private final Map<S, Map<S, BiConsumer<T, Object>>> transitionMap;
    private final StateManagementAdapter<T, S> stateManagementAdapter;
    private final S initialState;
    private final BiConsumer<T, Object> initialAction;

    public static <T, S extends Enum<S>> StateManagementServiceBuilder<T, S> builder() {
        return new StateManagementServiceBuilder<>();
    }

    @Override
    public void changeState(T target, S state) {
        changeState(target, state, null);
    }

    @Override
    public void changeState(T target, S state, Object context) {
        var currentState = stateManagementAdapter.getState(target);
        if (isNull(currentState)) {
            throw new IncorrectStateException(NULL_STATE_ERROR_MESSAGE);
        }

        BiConsumer<T, Object> action = resolveTransitionAction(currentState, state);

        stateManagementAdapter.setState(target, state);

        action.accept(target, context);
    }

    @Override
    public void initiate(T target) {
        this.initiate(target, null);
    }

    @Override
    public void initiate(T target, Object context) {
        stateManagementAdapter.setState(target, initialState);
        initialAction.accept(target, context);
    }

    private BiConsumer<T, Object> resolveTransitionAction(S sourceState, S targetState) {
        return ofNullable(transitionMap.get(sourceState))
                .map(transitions -> transitions.get(targetState))
                .orElseThrow(() -> new IncorrectStateException(
                        format(TRANSITION_NOT_FOUND_ERROR_TEMPLATE, sourceState, targetState)));
    }


    public static class StateManagementServiceBuilder<T, S extends Enum<S>> {

        private final List<Transition<T, S>> transitions = new ArrayList<>();
        private StateManagementAdapter<T, S> stateManagementAdapter;
        private S initialState;
        private BiConsumer<T, Object> initialAction = (v, o) -> {};

        public StateManagementServiceBuilder<T, S> transition(S source, S target) {
            return addTransition(source, target, Transition.<T, S>builder());
        }

        public StateManagementServiceBuilder<T, S> transition(Consumer<Transition.TransitionBuilder<T, S>> builderConsumer) {
            var builder = Transition.<T, S>builder();
            builderConsumer.accept(builder);
            transitions.add(builder.build());
            return this;
        }

        public StateManagementServiceBuilder<T, S> transition(S source, S target, Consumer<T> action) {
            return addTransition(source, target, Transition.<T, S>builder().action(action));
        }

        public StateManagementServiceBuilder<T, S> transition(S source, S target, BiConsumer<T, Object> action) {
            return addTransition(source, target, Transition.<T, S>builder().action(action));
        }

        public StateManagementServiceBuilder<T, S> transition(S source, S target, Runnable action) {
            return addTransition(source, target, Transition.<T, S>builder().action(action));
        }

        public StateManagementServiceBuilder<T, S> initialState(S initialState) {
            this.initialState = initialState;
            return this;
        }

        public StateManagementServiceBuilder<T, S> initialState(S initialState, BiConsumer<T, Object> initialAction) {
            this.initialState = initialState;
            this.initialAction = initialAction;
            return this;
        }

        public StateManagementServiceBuilder<T, S> managementAdapter
                (Consumer<ConfigurableStateManagementAdapter.ConfigurableStateManagementAdapterBuilder<T, S>> stateManagementAdapterBuilderConsumer) {
            var builder = ConfigurableStateManagementAdapter.<T, S>builder();
            stateManagementAdapterBuilderConsumer.accept(builder);
            stateManagementAdapter = builder.build();
            return this;
        }

        public DefaultStateManagementService<T, S> build() {
            requireNonNull(stateManagementAdapter);
            requireNonNull(initialState);
            return new DefaultStateManagementService<>(convertTransitionsToMap(), stateManagementAdapter, initialState, initialAction);
        }


        private StateManagementServiceBuilder<T, S> addTransition(S source, S target, Transition.TransitionBuilder<T, S> builder) {
            transitions.add(
                    builder.source(source)
                            .target(target)
                            .build()
            );
            return this;
        }

        private Map<S, Map<S, BiConsumer<T, Object>>> convertTransitionsToMap() {
            Map<S, Map<S, BiConsumer<T, Object>>> transitionMap = new HashMap<>();

            for (var transition : transitions) {
                transitionMap.computeIfAbsent(transition.getSource(), k -> new HashMap<>())
                        .putIfAbsent(transition.getTarget(), transition.getAction());
            }

            return transitionMap;
        }
    }
}
