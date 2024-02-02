package com.ajdeyemi.conduit.services;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.ajdeyemi.conduit.models.Articles;
import com.ajdeyemi.conduit.models.Followers;
import com.ajdeyemi.conduit.models.Tags;
import com.ajdeyemi.conduit.models.Users;
import com.ajdeyemi.conduit.repositories.ArticlesRepository;
import com.ajdeyemi.conduit.repositories.FollowersRepository;
import com.ajdeyemi.conduit.repositories.TagsRepository;
import com.ajdeyemi.conduit.repositories.UsersRepository;

@Service
public class ArticlesService {
    @Autowired
    ArticlesRepository articlesRepository;

    @Autowired
    TagsRepository tagsRepository;

    @Autowired
    FollowersRepository followersRepository;

    @Autowired
    UsersRepository usersRepository;

    public Page<Articles> geArticles(int page, int size){
        // List<Passenger> passenger = repository.findByOrderBySeatNumberAsc(Limit.of(1));
        PageRequest articles = PageRequest.of(page, size);
        return articlesRepository.findAll(articles);
    }


    public List<Articles> getFeed(){
    String authenticated = SecurityContextHolder.getContext().getAuthentication().getName();
    Users currentUser= usersRepository.findUsersByEmail(authenticated);
    List<Articles> articles=new ArrayList<>();
    // Get the user's followers
   List<Followers> followers= followersRepository.findFollowers(currentUser.getId());
//    Get articles of each followers
for(Followers follower: followers){
    List<Articles> articlesByFollower= articlesRepository.findByUser(follower.getId());
    for(Articles article: articlesByFollower){
        articles.add(article);
    }
}
return articles;
    }


    public Articles getArticle(String slug) throws Exception{
        if(slug!=null && !(slug.isBlank())){
            Articles article= articlesRepository.findBySlug(slug);

            return article;
        }else{
            throw new Exception("This Article cannot be found");
        }
    }


    public Articles createArticle(String title, String description, String body,List<String> tags) throws Exception{
        String authenticated = SecurityContextHolder.getContext().getAuthentication().getName();
    Users currentUser= usersRepository.findUsersByEmail(authenticated);
        // long user, String title, String description, String body
        if(title==null){
            throw new Exception("Title is a required field");
        }
        if(description==null){
            throw new Exception("Description is a required field");
        }
        if(body==null){
            throw new Exception("Body is a required field");
        }
        if(title.isEmpty() || description.isEmpty() || body.isEmpty() || tags.size()==0){
            throw new Exception("You cannot have empty fields");
        }
        Articles articles=new Articles(currentUser.getId(),title,description,body,tags);
        articlesRepository.save(articles);
        for(String tag: tags){
            Tags articleTag= new Tags(tag,articles.getId());
            tagsRepository.save(articleTag);
        }
        return articles;
    }


    public Articles updateArticle(String slug,String title, String description, String body)throws Exception{
        String authenticated = SecurityContextHolder.getContext().getAuthentication().getName();
        Users currentUser= usersRepository.findUsersByEmail(authenticated);
        if(slug!=null){
         Articles article= articlesRepository.findBySlug(slug);
         if(article.getUser()==currentUser.getId()){
            String setTitle= title!=null?title:article.getTitle();
            String setDescription= description!=null?description:article.getDescription();
            String setBody= body!=null?body:article.getBody();
            article.setTitle(setTitle);
            article.setDescription(setDescription);
            article.setBody(setBody);
            articlesRepository.save(article);
            return article;
         }else{
            throw new Exception("You are not permitted to update this article");
         }
        }else{
            throw new Exception("Article slug required");
        }

    }

    public String deleteArticle(String slug) throws Exception{
        String authenticated = SecurityContextHolder.getContext().getAuthentication().getName();
        Users currentUser= usersRepository.findUsersByEmail(authenticated);
        if(slug!=null){
            Articles article= articlesRepository.findBySlug(slug);  
            if(article.getUser()==currentUser.getId()){
                articlesRepository.delete(article);
                return article.getTitle() + " has been deleted successfully";
            }else{
                throw new Exception("You are not permitted to delete this article");  
            }
        }else{
            throw new Exception("Article slug required");
        }
    }

}
