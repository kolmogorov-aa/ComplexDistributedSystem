package ru.ulmc.school.repository.impl;

import org.springframework.stereotype.Service;
import ru.ulmc.school.model.Comment;
import ru.ulmc.school.model.User;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class CommentsService {
    private final UserCommentsDao dao;

    public CommentsService(UserCommentsDao dao) {
        this.dao = dao;
    }

    @Transactional
    public void createUserWithComments() {
        User user = new User();
        user.setLogin("MyLogin");
        user.setPassword("pass");

        Comment comment = new Comment();
        comment.setText("Spam");

        Comment comment2 = new Comment();
        comment2.setText("Spam2");
        user = dao.persist(user);
        List<Comment> list = new ArrayList<>();
        list.add(comment);
        list.add(comment2);
        user.setComments(list);

        comment.setAuthor(user);
        comment2.setAuthor(user);

        dao.persist(comment);
        dao.persist(comment2);
    }
}
