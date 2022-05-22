package ru.otus.lesson14hw.repository;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.otus.lesson14hw.domain.Comment;
import ru.otus.lesson14hw.repository.CommentRepository;

@RunWith(SpringRunner.class)
@DataMongoTest
public class CommentRepositoryTest {

    private static final String COMMENT = "comment";

    @Autowired
    private CommentRepository commentRepository;
    private Comment comment;

    @Before
    public void init(){
        comment = new Comment();
        comment.setComment(COMMENT);
        comment = commentRepository.save(comment);
    }

    @After
    public void destroy(){
        commentRepository.deleteAll();
    }

    @Test
    public void testComment(){
        Assert.assertTrue(COMMENT.equals(comment.getComment()));
    }

    @Test
    public void testCount(){
        System.out.println(commentRepository.findAll());
        Assert.assertEquals(commentRepository.count() ,1);
    }

    @Test
    public void testDeleteAll(){
        commentRepository.deleteAll();
        Assert.assertEquals(commentRepository.count(),0);
    }
}
