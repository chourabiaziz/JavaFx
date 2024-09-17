package tn.esprit.interfaces;

import tn.esprit.models.Comment;
import tn.esprit.models.Post;

import java.util.List;

public interface CommentInterface<C> {


    public boolean add(Comment c);

    public void edit(Comment c);

    public void delete(int id);

    public Comment getById(int id);

    public List<C> getAll(int id );





}
