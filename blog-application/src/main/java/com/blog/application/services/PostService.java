package com.blog.application.services;

import java.util.List;

import com.blog.application.entities.Post;
import com.blog.application.payloads.PostDto;
import com.blog.application.payloads.PostResponse;

public interface PostService {
	
	// create
	PostDto createPost(PostDto postDto,Integer userId,Integer categoryId);
	
	//update
	PostDto updatePost(PostDto postDto, Integer postId);
	
	//delete
	void deletePost(Integer postId);
	
	// get all Posts
	PostResponse getAllPost(Integer pageNumber, Integer pageSize,String sortBy, String sortDir);
	
	// get single post
	PostDto getPostsById(Integer postId);
	
	//get all posts by category
	List<PostDto> getPostsByCategory(Integer categoryId);
	
	
	//get all posts by user
	List<PostDto> getPostsByUser(Integer userId);
	
	// search posts
	List<PostDto> searchPosts(String keyword);

}
