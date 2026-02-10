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
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class BookRestController {

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
}
