package client;

import client.view.components.Component;

public interface Mediator {
    void registerComponent(Component component);
    String getName();
}