package org.example.midterm.config;

            import org.example.midterm.entities.Movie;
            import org.example.midterm.entities.Type;
            import org.example.midterm.repositories.MovieRepository;
            import org.example.midterm.repositories.TypeRepository;
            import org.springframework.boot.CommandLineRunner;
            import org.springframework.context.annotation.Profile;
            import org.springframework.stereotype.Component;

            import java.util.List;

            @Component
            @Profile("dev")
            public class DataLoader implements CommandLineRunner {

                private final MovieRepository movieRepository;
                private final TypeRepository typeRepository;

                public DataLoader(MovieRepository movieRepository, TypeRepository typeRepository) {
                    this.movieRepository = movieRepository;
                    this.typeRepository = typeRepository;
                }

                @Override
                public void run(String... args) throws Exception {
                    if (movieRepository.count() == 0) {
                        System.out.println("Loading sample data...");

                        Type action = new Type("Action");
                        Type comedy = new Type("Comedy");
                        Type drama = new Type("Drama");
                        typeRepository.saveAll(List.of(action, comedy, drama));

                        Movie movie1 = new Movie("Inception", "A mind-bending thriller", 2010, action);
                        Movie movie2 = new Movie("The Hangover", "Comedy adventure", 2009, comedy);
                        Movie movie3 = new Movie("The Godfather", "Mafia crime drama", 1972, drama);
                        movieRepository.saveAll(List.of(movie1, movie2, movie3));

                        System.out.println("Sample data loaded successfully!");
                    } else {
                        System.out.println("Data already exists, skipping...");
                    }
                }
            }