package de.devtime.examples.library.businesslogic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import de.devtime.examples.library.event.BookRegistrationRequestedEvent;
import lombok.Setter;

@Component
public class BookEventListener {

  @Setter(onMethod_ = @Autowired)
  private BookService bookService;

  @EventListener
  void onBookRegistrationRequestedEvent(final BookRegistrationRequestedEvent event) {
    switch (event.getShowCase()) {
      case null -> this.bookService.registerBook(event.getBook());
      case PROPAGATION_MANDATORY -> this.bookService.registerBookInMandatoryTx(event.getBook());
      case PROPAGATION_NEVER -> this.bookService.registerBookInNeverTx(event.getBook());
      case PROPAGATION_NESTED -> this.bookService.registerBookInNestedTx(event.getBook());
      case PROPAGATION_NOT_SUPPORTED -> this.bookService.registerBookInNotSupportedTx(event.getBook());
      case PROPAGATION_REQUIRED -> this.bookService.registerBookInRequiredTx(event.getBook());
      case PROPAGATION_REQUIRES_NEW -> this.bookService.registerBookInNewTx(event.getBook());
      case PROPAGATION_SUPPORTS -> this.bookService.registerBookInSupportsTx(event.getBook());
      case THROW_EXCEPTION -> throw new IllegalStateException();
      default -> this.bookService.registerBook(event.getBook());
    }
  }
}
