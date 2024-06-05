package kpi.diploma.server.state.adapter;

public interface StateManagementAdapter<T, S extends Enum<S>> {
    S getState(T target);
    void setState(T target, S state);
}
