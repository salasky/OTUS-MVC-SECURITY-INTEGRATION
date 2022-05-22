package ru.otus.lesson14hw.repository;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.otus.lesson14hw.domain.Genre;

@RunWith(SpringRunner.class)
@DataMongoTest
public class GenreRepositoryTest {

    private static final String GENRE = "genre";

    @Autowired
    private GenreRepository genreRepository;
    private Genre genre;

    @Before
    public void init(){
        genre = new Genre();
        genre.setGenre(GENRE);
        genre = genreRepository.save(genre);
    }

    @After
    public void destroy(){
        genreRepository.deleteAll();
    }

    @Test
    public void testGenre(){
        Assert.assertTrue(GENRE.equals(genre.getGenre()));
    }

    @Test
    public void testCount(){
        Assert.assertEquals(genreRepository.count(), 1);
    }

    @Test
    public void testDeleteAll(){
        genreRepository.deleteAll();
        Assert.assertEquals(genreRepository.count(),0);
    }
}
