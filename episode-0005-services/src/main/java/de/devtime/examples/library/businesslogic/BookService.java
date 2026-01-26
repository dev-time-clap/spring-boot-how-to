package de.devtime.examples.library.businesslogic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.devtime.examples.library.api.contract.book.BookRegistrationDto;
import de.devtime.examples.library.persistence.entity.BookEntity;
import de.devtime.examples.library.persistence.repository.BookRepository;
import lombok.Setter;

@Service
public class BookService {

  @Setter(onMethod_ = @Autowired)
  private BookRepository bookRepo;

  @Transactional
  public void registerBook(final BookRegistrationDto registrationDto) {
    BookEntity bookToRegister = BookEntity.builder()
        .withAuthor(registrationDto.getAuthor())
        .withIsbn(registrationDto.getIsbn())
        .withTitle(registrationDto.getTitle())
        .withIsOnLoan(false)
        .buildAndInit();

    this.bookRepo.save(bookToRegister);
  }
}
