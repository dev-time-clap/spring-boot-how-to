package de.devtime.examples.library.businesslogic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import de.devtime.examples.library.event.AuthorRegistrationRequestedEvent;
import lombok.Setter;

@Component
public class AuthorEventListener {

  @Setter(onMethod_ = @Autowired)
  private AuthorService authorService;

  @EventListener
  void onBookRegistrationRequestedEvent(final AuthorRegistrationRequestedEvent event) {
    switch (event.getShowCase()) {
      case null -> this.authorService.registerAuthor(event.getAuthor());
      case PROPAGATION_MANDATORY -> this.authorService.registerAuthorInMandatoryTx(event.getAuthor());
      case PROPAGATION_NEVER -> this.authorService.registerAuthorInNeverTx(event.getAuthor());
      case PROPAGATION_NESTED -> this.authorService.registerAuthorInNestedTx(event.getAuthor());
      case PROPAGATION_NOT_SUPPORTED -> this.authorService.registerAuthorInNotSupportedTx(event.getAuthor());
      case PROPAGATION_REQUIRED -> this.authorService.registerAuthorInRequiredTx(event.getAuthor());
      case PROPAGATION_REQUIRES_NEW -> this.authorService.registerAuthorInNewTx(event.getAuthor());
      case PROPAGATION_SUPPORTS -> this.authorService.registerAuthorInSupportsTx(event.getAuthor());
      case THROW_EXCEPTION -> throw new IllegalStateException();
      default -> this.authorService.registerAuthor(event.getAuthor());
    }
  }
}
