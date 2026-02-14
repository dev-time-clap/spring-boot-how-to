package de.devtime.examples.library.event;

import de.devtime.examples.library.api.contract.book.BookRegistrationDto;
import de.devtime.examples.library.context.AbstractManualAutowiredBean;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BookRegistrationRequestedEvent extends AbstractEvent {

  @Getter
  private BookRegistrationDto book;

  @Getter
  private TransactionShowCase showCase;

  @Builder(setterPrefix = "with")
  public BookRegistrationRequestedEvent(
      final BookRegistrationDto book,
      final TransactionShowCase showCase) {
    super();
    this.book = book;
    this.showCase = showCase;
  }

  public static class BookRegistrationRequestedEventBuilder
      extends AbstractManualAutowiredBean<BookRegistrationRequestedEventBuilder> {

    public void fire() {
      BookRegistrationRequestedEvent eventToFire = build();
      log.debug("Fire event {}", eventToFire);
      this.appContext.publishEvent(eventToFire);
    }
  }
}
