package de.devtime.examples.library.api.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import de.devtime.examples.library.api.contract.EndpointConstants;
import de.devtime.examples.library.api.contract.book.BookRegistrationDto;
import de.devtime.examples.library.persistence.entity.BookEntity;
import de.devtime.examples.library.persistence.repository.BookRepository;
import jakarta.transaction.Transactional;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class BookRestController {
  //
  @Setter(onMethod_ = @Autowired)
  private BookRepository bookRepo;

  @PostMapping(EndpointConstants.PATH_BOOKS_REGISTRATION)
  @ResponseStatus(HttpStatus.CREATED)
  @Transactional
  public void registerBook(@RequestBody final BookRegistrationDto registrationDto) {
    log.info("A book registration was requested with the following data: {}", registrationDto);

    BookEntity bookToRegister = BookEntity.builder()
        .withAuthor(registrationDto.getAuthor())
        .withIsbn(registrationDto.getIsbn())
        .withTitle(registrationDto.getTitle())
        .withIsOnLoan(false)
        .buildAndInit();

    this.bookRepo.save(bookToRegister);

  }

}
