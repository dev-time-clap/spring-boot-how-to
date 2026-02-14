package de.devtime.examples.library.api.impl;

import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import de.devtime.examples.library.api.contract.EndpointConstants;
import de.devtime.examples.library.api.contract.request.BookRegistrationRequestDto;
import de.devtime.examples.library.event.AuthorRegistrationRequestedEvent;
import de.devtime.examples.library.event.BookRegistrationRequestedEvent;
import de.devtime.examples.library.event.CleanDatabaseEvent;
import de.devtime.examples.library.event.TransactionShowCase;
import de.devtime.examples.library.persistence.util.TransactionHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
public class BookRestController {

  private final TransactionHelper txHelper;

  @PostMapping(EndpointConstants.PATH_BOOKS_REGISTRATION + "-clear-database")
  @ResponseStatus(HttpStatus.CREATED)
  @Transactional
  public void clearDatabase() {
    CleanDatabaseEvent.builder().fire();
  }

  @PostMapping(EndpointConstants.PATH_BOOKS_REGISTRATION + "-showcase-01-transactions-on-save")
  @ResponseStatus(HttpStatus.CREATED)
  public void registerBookWithTransactionOnSave(@RequestBody final BookRegistrationRequestDto requestDto) {
    log.debug("requestDto: {}", requestDto);
    BookRegistrationRequestedEvent.builder()
        .withBook(requestDto.getBook())
        .fire();
    AuthorRegistrationRequestedEvent.builder()
        .withAuthor(requestDto.getAuthor())
        .fire();
  }

  @PostMapping(EndpointConstants.PATH_BOOKS_REGISTRATION + "-showcase-02-transactions-on-save-with-exception")
  @ResponseStatus(HttpStatus.CREATED)
  public void registerBookWithTransactionOnSaveAndException(@RequestBody final BookRegistrationRequestDto requestDto) {
    log.debug("requestDto: {}", requestDto);
    BookRegistrationRequestedEvent.builder()
        .withBook(requestDto.getBook())
        .fire();
    AuthorRegistrationRequestedEvent.builder()
        .withAuthor(requestDto.getAuthor())
        .withShowCase(TransactionShowCase.THROW_EXCEPTION)
        .fire();
  }

  @PostMapping(EndpointConstants.PATH_BOOKS_REGISTRATION + "-showcase-03-single-tx-for-both-services")
  @ResponseStatus(HttpStatus.CREATED)
  @Transactional
  public void registerBookWithSingleTxForBothServices(
      @RequestBody final BookRegistrationRequestDto requestDto) {
    log.debug("requestDto: {}", requestDto);
    BookRegistrationRequestedEvent.builder()
        .withBook(requestDto.getBook())
        .fire();
    AuthorRegistrationRequestedEvent.builder()
        .withAuthor(requestDto.getAuthor())
        .fire();
  }

  @PostMapping(EndpointConstants.PATH_BOOKS_REGISTRATION + "-showcase-04-single-tx-for-both-services-with-exception")
  @ResponseStatus(HttpStatus.CREATED)
  @Transactional
  public void registerBookWithSingleTxForBothServicesAndException(
      @RequestBody final BookRegistrationRequestDto requestDto) {
    log.debug("requestDto: {}", requestDto);
    AuthorRegistrationRequestedEvent.builder()
        .withAuthor(requestDto.getAuthor())
        .fire();
    BookRegistrationRequestedEvent.builder()
        .withBook(requestDto.getBook())
        .withShowCase(TransactionShowCase.THROW_EXCEPTION)
        .fire();
  }

  @PostMapping(EndpointConstants.PATH_BOOKS_REGISTRATION + "-showcase-05-different-tx-in-same-method")
  @ResponseStatus(HttpStatus.CREATED)
  public void registerBookWithDifferentTxInSameMethod(
      @RequestBody final BookRegistrationRequestDto requestDto) {
    log.debug("requestDto: {}", requestDto);
    fireAuthorRegistrationRequestedEvent(requestDto);
    fireBookRegistrationRequestedEvent(requestDto);
  }

  private void fireAuthorRegistrationRequestedEvent(final BookRegistrationRequestDto requestDto) {
    this.txHelper.executeInTxWithoutResult(() -> {
      AuthorRegistrationRequestedEvent.builder()
          .withAuthor(requestDto.getAuthor())
          .fire();
    });
  }

  private void fireBookRegistrationRequestedEvent(final BookRegistrationRequestDto requestDto) {
    this.txHelper.executeInTxWithoutResult(() -> {
      BookRegistrationRequestedEvent.builder()
          .withBook(requestDto.getBook())
          .fire();
    });
  }
}
