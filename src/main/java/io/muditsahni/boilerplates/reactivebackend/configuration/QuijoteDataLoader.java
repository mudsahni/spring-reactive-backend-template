package io.muditsahni.boilerplates.reactivebackend.configuration;

import io.muditsahni.boilerplates.reactivebackend.domain.Quote;
import io.muditsahni.boilerplates.reactivebackend.repository.QuoteMongoReactiveRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.function.Supplier;

@Component
public class QuijoteDataLoader implements ApplicationRunner {

    private static final Logger logger = LoggerFactory.getLogger(QuijoteDataLoader.class);

    private final QuoteMongoReactiveRepository quoteMongoReactiveRepository;

    QuijoteDataLoader(final QuoteMongoReactiveRepository quoteMongoReactiveRepository) {
        this.quoteMongoReactiveRepository = quoteMongoReactiveRepository;
    }

    @Override
    public void run(final ApplicationArguments args) {
        if (quoteMongoReactiveRepository.count().block() == 0L) {
            Supplier<String> idSupplier = getIdSequenceSupplier();
            var bufferedReader = new BufferedReader(
                    new InputStreamReader(getClass().getClassLoader().getResourceAsStream("book.txt"))
            );
            Flux.fromStream(bufferedReader.lines().filter(l -> !l.trim().isEmpty()).map(l -> quoteMongoReactiveRepository.save(new Quote(idSupplier.get(), "El Quijote", l)))
            ).subscribe(m -> logger.info("New quote loaded: {}", m.block()));
            logger.info("Repository contains {} entries.", quoteMongoReactiveRepository.count().block());
        }
    }

    private Supplier<String>  getIdSequenceSupplier() {
        return new Supplier<>() {
            Long l = 0L;

            @Override
            public String get() {
                // adds padding of zeroes
                return String.format("%05d", l++);
            }
        };
    }

}
