package de.devtime.examples.library.businesslogic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import de.devtime.examples.library.api.contract.book.BookRegistrationDto;
import de.devtime.examples.library.persistence.entity.BookEntity;
import de.devtime.examples.library.persistence.repository.BookRepository;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BookService {

  @Setter(onMethod_ = @Autowired)
  private BookRepository bookRepo;

  public void registerBook(final BookRegistrationDto registrationDto) {
    log.debug("registrationDto: {}", registrationDto);
    internalSave(registrationDto);
  }

  @Transactional(propagation = Propagation.MANDATORY)
  public void registerBookInMandatoryTx(final BookRegistrationDto registrationDto) {
    log.debug("registrationDto: {}", registrationDto);
    internalSave(registrationDto);
  }

  @Transactional(propagation = Propagation.NESTED)
  public void registerBookInNestedTx(final BookRegistrationDto registrationDto) {
    log.debug("registrationDto: {}", registrationDto);
    internalSave(registrationDto);
  }

  @Transactional(propagation = Propagation.NEVER)
  public void registerBookInNeverTx(final BookRegistrationDto registrationDto) {
    log.debug("registrationDto: {}", registrationDto);
    internalSave(registrationDto);
  }

  @Transactional(propagation = Propagation.NOT_SUPPORTED)
  public void registerBookInNotSupportedTx(final BookRegistrationDto registrationDto) {
    log.debug("registrationDto: {}", registrationDto);
    internalSave(registrationDto);
  }

  @Transactional(propagation = Propagation.REQUIRED)
  public void registerBookInRequiredTx(final BookRegistrationDto registrationDto) {
    log.debug("registrationDto: {}", registrationDto);
    internalSave(registrationDto);
  }

  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void registerBookInNewTx(final BookRegistrationDto registrationDto) {
    log.debug("registrationDto: {}", registrationDto);
    internalSave(registrationDto);
  }

  @Transactional(propagation = Propagation.SUPPORTS)
  public void registerBookInSupportsTx(final BookRegistrationDto registrationDto) {
    log.debug("registrationDto: {}", registrationDto);
    internalSave(registrationDto);
  }

  private void internalSave(final BookRegistrationDto registrationDto) {
    BookEntity bookToRegisterEntity = BookEntity.builder()
        .withIsbn(registrationDto.getIsbn())
        .withTitle(registrationDto.getTitle())
        .withIsOnLoan(false)
        .buildAndInit();

    this.bookRepo.save(bookToRegisterEntity);
  }
}
