package sr.will.jarvis.event;

import sr.will.jarvis.module.Module;

public abstract class EventHandler {
    private Module module;
    private EventPriority priority;

    protected EventHandler(Module module, EventPriority priority) {
        this.module = module;
        this.priority = priority;
    }

    public abstract void onEvent(net.dv8tion.jda.core.events.Event event);

    public Module getModule() {
        return module;
    }

    public EventPriority getPriority() {
        return priority;
    }
}
