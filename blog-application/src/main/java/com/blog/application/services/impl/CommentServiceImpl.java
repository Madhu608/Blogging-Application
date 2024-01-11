package com.blog.application.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.application.entities.Comment;
import com.blog.application.entities.Post;
import com.blog.application.exceptions.ResourceNotFoundException;
import com.blog.application.payloads.CommentDto;
import com.blog.application.repositories.CommentRepo;
import com.blog.application.repositories.PostRepo;
import com.blog.application.services.CommentService;

@Service
public class CommentServiceImpl implements CommentService {
	
	
	@Autowired
    private PostRepo postRepo;
    
    @Autowired
    private CommentRepo commentRepo;
    
    
    @Autowired
    private ModelMapper modelMapper;

	@Override
	public CommentDto createComment(CommentDto commenDto, Integer postId) {
		Post post = this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "post Id", postId));
		Comment comment = this.modelMapper.map(commenDto, Comment.class);
		
		comment.setPost(post);
		Comment savedComment = this.commentRepo.save(comment);
		
		return this.modelMapper.map(savedComment, CommentDto.class);
	}

	@Override
	public void deleteComment(Integer commentId) {
		Comment com = this.commentRepo.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("Comment", "commentId", commentId));
		this.commentRepo.delete(com);
	}

}
