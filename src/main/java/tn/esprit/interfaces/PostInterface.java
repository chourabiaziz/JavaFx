package tn.esprit.interfaces;

import tn.esprit.models.Post;

import java.util.List;

public interface PostInterface<P> {


    public boolean add(Post c);

    public void edit(Post c);

    public void delete(int id);

    public Post getById(int id);

    public List<P> getAll();





}
