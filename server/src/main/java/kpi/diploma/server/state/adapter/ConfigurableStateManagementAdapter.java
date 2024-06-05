package kpi.diploma.server.state.adapter;

import lombok.Builder;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.function.BiConsumer;
import java.util.function.Function;

@Builder
@RequiredArgsConstructor
public class ConfigurableStateManagementAdapter<T, S extends Enum<S>> implements StateManagementAdapter<T, S> {

    @NonNull
    private final Function<T, S> stateProvider;
    @NonNull
    private final BiConsumer<T, S> stateApplier;

    @Override
    public S getState(T target) {
        return stateProvider.apply(target);
    }

    @Override
    public void setState(T target, S state) {
        stateApplier.accept(target, state);
    }
}
