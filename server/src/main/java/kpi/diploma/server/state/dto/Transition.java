package kpi.diploma.server.state.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import static java.lang.String.format;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class Transition<T, S extends Enum<S>> {

    private final S source;
    private final S target;
    private final BiConsumer<T, Object> action;

    public static <T, S extends Enum<S>> TransitionBuilder<T, S> builder() {
        return new TransitionBuilder<>();
    }

    public static class TransitionBuilder<T, S extends Enum<S>> {

        private static final String EMPTY_TRANSITION_ERROR_TEMPLATE = "Transition %s must be configured";

        private S source;
        private S target;
        private BiConsumer<T, Object> action = (t, s) -> {
        };

        public TransitionBuilder<T, S> source(S source) {
            this.source = source;
            return this;
        }

        public TransitionBuilder<T, S> target(S target) {
            this.target = target;
            return this;
        }

        public TransitionBuilder<T, S> action(BiConsumer<T, Object> action) {
            this.action = action;
            return this;
        }

        public TransitionBuilder<T, S> action(Consumer<T> action) {
            this.action = (t, s) -> action.accept(t);
            return this;
        }

        public TransitionBuilder<T, S> action(Runnable action) {
            this.action = (t, s) -> action.run();
            return this;
        }

        public Transition<T, S> build() {
            Objects.requireNonNull(source, format(EMPTY_TRANSITION_ERROR_TEMPLATE, "source"));
            Objects.requireNonNull(target, format(EMPTY_TRANSITION_ERROR_TEMPLATE, "target"));
            return new Transition<>(source, target, action);
        }

    }

}
