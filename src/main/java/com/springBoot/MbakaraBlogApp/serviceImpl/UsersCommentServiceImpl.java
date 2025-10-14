package com.springBoot.MbakaraBlogApp.serviceImpl;

import com.springBoot.MbakaraBlogApp.dtos.UsersCommentDTO;
import com.springBoot.MbakaraBlogApp.entity.UsersCommentEntity;
import com.springBoot.MbakaraBlogApp.entity.UsersPost;
import com.springBoot.MbakaraBlogApp.exception.BlogCommentPostException;
import com.springBoot.MbakaraBlogApp.exception.ResourceNotFoundException;
import com.springBoot.MbakaraBlogApp.repository.UserPostRepository;
import com.springBoot.MbakaraBlogApp.repository.UsersCommentRepository;
import com.springBoot.MbakaraBlogApp.service.UsersCommentService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsersCommentServiceImpl implements UsersCommentService {

    private final UsersCommentRepository usersCommentRepository;
    private UsersCommentRepository commentRepository;
    private UserPostRepository userPostRepository;
    private ModelMapper mapper;

    public UsersCommentServiceImpl(UsersCommentRepository commentRepository, UserPostRepository userPostRepository,
                                   UsersCommentRepository usersCommentRepository, ModelMapper mapper) {
        this.commentRepository = commentRepository;
        this.userPostRepository = userPostRepository;
        this.usersCommentRepository = usersCommentRepository;
        this.mapper = mapper;
    }

    @Override
    public UsersCommentDTO createComment(long usersPostId, UsersCommentDTO commentsObject) {
        UsersCommentEntity comment = mapCommentToEntity(commentsObject);

        // Retrieving UserPost by Id
        UsersPost post = userPostRepository.findById(usersPostId).orElseThrow(
                () -> new ResourceNotFoundException("UsersPost", "id", usersPostId));

        // Set usersPost to userComment entity
        comment.setUsersPost(post);

        // Save commentEntity to database
         UsersCommentEntity newComment = usersCommentRepository.save(comment);


        return mapCommentToDTO(newComment);
    }

    @Override
    public List<UsersCommentDTO> getAllCommentByPostId(long postId) {

        // Retrieving Comments by Post Id
        List<UsersCommentEntity> comments = usersCommentRepository.findByUsersPostId(postId);

        // Converting list of Entities to list of comment dto's.
        return comments.stream().map(comment -> mapCommentToDTO(comment)).collect(Collectors.toList());
    }

    @Override
    public UsersCommentDTO getCommentById(Long postId, Long usersCommentId) {
        // Retrieving usersPost from the database

        UsersPost posts = userPostRepository.findById(postId).orElseThrow(
                ()-> new ResourceNotFoundException("UsersPost", "id", postId));

        // Retrieve Comment By Id
        UsersCommentEntity usersComment = usersCommentRepository.findById(usersCommentId).orElseThrow(
                ()-> new ResourceNotFoundException("Comment", "id", usersCommentId));

        if (!usersComment.getUsersPost().getId().equals(posts.getId())){
            throw new BlogCommentPostException(HttpStatus.BAD_REQUEST, "Comment doesn't belong to the post");
        }


        return mapCommentToDTO(usersComment);
    }

    @Override
    public UsersCommentDTO updateComment(Long postId, long commentId, UsersCommentDTO commentRequest) {

        // The below logic is just to retrieve/get a post from the db using postId
        UsersPost updatePost = userPostRepository.findById(postId).orElseThrow(
                ()-> new ResourceNotFoundException("UsersPost", "id", postId));

        // Retrieve comment by id
        UsersCommentEntity usersComment = usersCommentRepository.findById(commentId).orElseThrow(
                ()-> new ResourceNotFoundException("Comment", "id", commentId));

        // Checking if the comment belongs to a particular post or not
        if (!usersComment.getUsersPost().getId().equals(updatePost.getId())){
            throw new BlogCommentPostException(HttpStatus.BAD_REQUEST, "Comment doesn't belongs to post");

        }

        usersComment.setUsersName(commentRequest.getUsersName());
        usersComment.setUsersEmail(commentRequest.getUsersEmail());
        usersComment.setMessageBody(commentRequest.getMessageBody());

        UsersCommentEntity updatedComment = usersCommentRepository.save(usersComment);
        return mapCommentToDTO(updatedComment);

    }

    @Override
    public void deleteUsersComment(Long postId, Long usersCommentId) {
        // Retrieving a userPost by id from the db.
        UsersPost deletion = userPostRepository.findById(postId).orElseThrow(
                ()-> new ResourceNotFoundException("UsersPost", "id", postId));

        // Retrieving usersComment by id
        UsersCommentEntity usersComment = usersCommentRepository.findById(usersCommentId).orElseThrow(
                ()-> new ResourceNotFoundException("UsersComment", "id", usersCommentId));

        if (!usersComment.getUsersPost().getId().equals(deletion.getId())){
            throw new BlogCommentPostException(HttpStatus.BAD_REQUEST, "Comment doesn't belong to post");
        }

        usersCommentRepository.delete(usersComment);
    }

    // writing method for comment conversion from entity to dto, and from dto to entity

//    private UsersCommentDTO mapCommentToDTO(UsersComment commentEntity){
//        UsersCommentDTO commentDTO = new UsersCommentDTO();
//        commentDTO.setId(commentEntity.getId());
//        commentDTO.setUsersName(commentEntity.getUsersName());
//        commentDTO.setUsersEmail(commentEntity.getUsersEmail());
//        commentDTO.setMessageBody(commentEntity.getMessageBody());
//        return commentDTO;
//
//    }

    /**
      Using ModelMapper API method to convert Entity to DTO below
     */
    private UsersCommentDTO mapCommentToDTO(UsersCommentEntity commentEntity){
        UsersCommentDTO commentDTO = mapper.map(commentEntity, UsersCommentDTO.class);
        return commentDTO;

    }


//    private UsersCommentEntity mapCommentToEntity(UsersCommentDTO commentDTO){
//        UsersComment commentEntity = new UsersComment();
//        commentEntity.setId(commentDTO.getId());
//        commentEntity.setUsersName(commentDTO.getUsersName());
//        commentEntity.setUsersEmail(commentDTO.getUsersEmail());
//        commentEntity.setMessageBody(commentDTO.getMessageBody());
//        return commentEntity;
//    }

    /**
     * Same formula here
     */

    private UsersCommentEntity mapCommentToEntity(UsersCommentDTO commentDTO){
        UsersCommentEntity commentEntity = mapper.map(commentDTO, UsersCommentEntity.class);
        return commentEntity;
    }
}
