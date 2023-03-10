package com.example.wirelesshome.component;

import com.example.wirelesshome.exception.TaskNotFound;
import com.example.wirelesshome.model.device.thermostat.Thermostat;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ScheduledFuture;

@Component
public class BoundComponent {

    @Value("${thermostat.bound.period}")
    private long period;

    private final TaskScheduler scheduler;

    private final ConcurrentMap<String, ScheduledFuture<?>> scheduledTasks = new ConcurrentHashMap<>();

    public BoundComponent(TaskScheduler scheduler) {
        this.scheduler = scheduler;
    }
    
    public void register(Thermostat thermostat, Runnable task) {
        if (thermostat.isBound()){
            ScheduledFuture<?> scheduledTask = scheduler.scheduleAtFixedRate(task, period);

            Optional<? extends ScheduledFuture<?>> scheduledFuture = Optional.ofNullable(scheduledTasks.get(thermostat.getId()));
            scheduledFuture.ifPresent(future -> future.cancel(true));

            scheduledTasks.put(thermostat.getId(), scheduledTask);
        } else {
            Optional<? extends ScheduledFuture<?>> scheduledFuture = Optional.ofNullable(scheduledTasks.get(thermostat.getId()));
            scheduledFuture.ifPresentOrElse(future -> future.cancel(true), TaskNotFound::new);
        }
    }
}
