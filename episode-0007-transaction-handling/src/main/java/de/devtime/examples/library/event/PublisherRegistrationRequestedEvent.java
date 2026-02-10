package de.devtime.examples.library.event;

import de.devtime.examples.library.api.contract.publisher.PublisherRegistrationDto;
import de.devtime.examples.library.context.AbstractManualAutowiredBean;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PublisherRegistrationRequestedEvent extends AbstractEvent {

  @Getter
  private PublisherRegistrationDto publisher;

  @Getter
  private TransactionShowCase showCase;

  @Builder(setterPrefix = "with")
  public PublisherRegistrationRequestedEvent(
      final PublisherRegistrationDto publisher,
      final TransactionShowCase showCase) {
    super();
    this.publisher = publisher;
    this.showCase = showCase;
  }

  public static class PublisherRegistrationRequestedEventBuilder
      extends AbstractManualAutowiredBean<PublisherRegistrationRequestedEventBuilder> {

    public void fire() {
      PublisherRegistrationRequestedEvent eventToFire = build();
      log.debug("Fire event {}", eventToFire);
      this.appContext.publishEvent(eventToFire);
    }
  }
}
