package ru.otus.lesson14hw.repository;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.otus.lesson14hw.domain.Author;
import ru.otus.lesson14hw.repository.AuthorRepository;

@RunWith(SpringRunner.class)
@DataMongoTest
public class AuthorRepositoryTest {

    private static final String FIRST_NAME = "firstName";
    private static final String SECOND_NAME = "secondName";

    @Autowired
    private AuthorRepository authorRepository;
    private Author author;

    @Before
    public void init(){
        author = new Author(FIRST_NAME, SECOND_NAME);
        author = authorRepository.save(author);
    }

    @After
    public void destroy(){
        authorRepository.deleteAll();
    }

    @Test
    public void testNames(){
        Assert.assertTrue(FIRST_NAME.equals(author.getFirstName()));
        Assert.assertTrue(SECOND_NAME.equals(author.getSecondName()));
    }

    @Test
    public void testCount(){
        Assert.assertEquals(authorRepository.count(), 1);
    }

    @Test
    public void testDeleteAll(){
        authorRepository.deleteAll();
        Assert.assertEquals(authorRepository.count(), 0);
    }
}
