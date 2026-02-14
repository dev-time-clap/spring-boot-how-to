package de.devtime.examples.library.event;

import de.devtime.examples.library.context.AbstractManualAutowiredBean;
import lombok.Builder;

@Builder(setterPrefix = "with")
public class CleanDatabaseEvent extends AbstractEvent {

  public static class CleanDatabaseEventBuilder
      extends AbstractManualAutowiredBean<CleanDatabaseEventBuilder> {

    public void fire() {
      CleanDatabaseEvent eventToFire = build();
      this.appContext.publishEvent(eventToFire);
    }
  }
}
