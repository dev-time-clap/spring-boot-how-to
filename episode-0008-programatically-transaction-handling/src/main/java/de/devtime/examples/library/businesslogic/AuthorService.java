package de.devtime.examples.library.businesslogic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import de.devtime.examples.library.api.contract.author.AuthorRegistrationDto;
import de.devtime.examples.library.persistence.entity.AuthorEntity;
import de.devtime.examples.library.persistence.repository.AuthorRepository;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AuthorService {

  @Setter(onMethod_ = @Autowired)
  private AuthorRepository authorRepo;

  public void registerAuthor(final AuthorRegistrationDto registrationDto) {
    internalSave(registrationDto);
  }

  @Transactional(propagation = Propagation.MANDATORY)
  public void registerAuthorInMandatoryTx(final AuthorRegistrationDto registrationDto) {
    log.debug("registrationDto: {}", registrationDto);
    internalSave(registrationDto);
  }

  @Transactional(propagation = Propagation.NESTED)
  public void registerAuthorInNestedTx(final AuthorRegistrationDto registrationDto) {
    log.debug("registrationDto: {}", registrationDto);
    internalSave(registrationDto);
  }

  @Transactional(propagation = Propagation.NEVER)
  public void registerAuthorInNeverTx(final AuthorRegistrationDto registrationDto) {
    log.debug("registrationDto: {}", registrationDto);
    internalSave(registrationDto);
  }

  @Transactional(propagation = Propagation.NOT_SUPPORTED)
  public void registerAuthorInNotSupportedTx(final AuthorRegistrationDto registrationDto) {
    log.debug("registrationDto: {}", registrationDto);
    internalSave(registrationDto);
  }

  @Transactional(propagation = Propagation.REQUIRED)
  public void registerAuthorInRequiredTx(final AuthorRegistrationDto registrationDto) {
    log.debug("registrationDto: {}", registrationDto);
    internalSave(registrationDto);
  }

  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void registerAuthorInNewTx(final AuthorRegistrationDto registrationDto) {
    log.debug("registrationDto: {}", registrationDto);
    internalSave(registrationDto);
  }

  @Transactional(propagation = Propagation.SUPPORTS)
  public void registerAuthorInSupportsTx(final AuthorRegistrationDto registrationDto) {
    log.debug("registrationDto: {}", registrationDto);
    internalSave(registrationDto);
  }

  private void internalSave(final AuthorRegistrationDto registrationDto) {
    AuthorEntity authorToRegisterEntity = AuthorEntity.builder()
        .withArtistName(registrationDto.getArtistName())
        .withFirstName(registrationDto.getFirstName())
        .withLastName(registrationDto.getLastName())
        .withBirthday(registrationDto.getBirthday())
        .buildAndInit();

    this.authorRepo.save(authorToRegisterEntity);
  }
}
