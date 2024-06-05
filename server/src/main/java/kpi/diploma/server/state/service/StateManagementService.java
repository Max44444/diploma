package kpi.diploma.server.state.service;

public interface StateManagementService<T, S extends Enum<S>> {

    void changeState(T target, S state);
    void changeState(T target, S state, Object context);

    void initiate(T target);

    void initiate(T target, Object context);

}
