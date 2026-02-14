package de.devtime.examples.library.event;

import de.devtime.examples.library.api.contract.author.AuthorRegistrationDto;
import de.devtime.examples.library.context.AbstractManualAutowiredBean;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AuthorRegistrationRequestedEvent extends AbstractEvent {

  @Getter
  private AuthorRegistrationDto author;

  @Getter
  private TransactionShowCase showCase;

  @Builder(setterPrefix = "with")
  public AuthorRegistrationRequestedEvent(
      final AuthorRegistrationDto author,
      final TransactionShowCase showCase) {
    super();
    this.author = author;
    this.showCase = showCase;
  }

  public static class AuthorRegistrationRequestedEventBuilder
      extends AbstractManualAutowiredBean<AuthorRegistrationRequestedEventBuilder> {

    public void fire() {
      AuthorRegistrationRequestedEvent eventToFire = build();
      log.debug("Fire event {}", eventToFire);
      this.appContext.publishEvent(eventToFire);
    }
  }
}
