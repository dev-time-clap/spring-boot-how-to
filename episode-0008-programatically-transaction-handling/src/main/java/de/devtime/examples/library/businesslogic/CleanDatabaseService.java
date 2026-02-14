package de.devtime.examples.library.businesslogic;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.devtime.examples.library.event.CleanDatabaseEvent;
import de.devtime.examples.library.persistence.repository.AuthorRepository;
import de.devtime.examples.library.persistence.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class CleanDatabaseService {

  private final BookRepository bookRepo;
  private final AuthorRepository authorRepo;

  @EventListener(CleanDatabaseEvent.class)
  @Transactional
  public void onCleanDatabaseEvent(final CleanDatabaseEvent event) {
    log.info("clear database");
    this.bookRepo.deleteAll();
    this.authorRepo.deleteAll();
  }
}
