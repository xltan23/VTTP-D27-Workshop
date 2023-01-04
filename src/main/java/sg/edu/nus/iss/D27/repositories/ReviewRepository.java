package sg.edu.nus.iss.D27.repositories;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import sg.edu.nus.iss.D27.models.EditedComment;
import sg.edu.nus.iss.D27.models.Review;

@Repository
public class ReviewRepository {

    private static final String REVIEWS_COL = "reviews";
    
    @Autowired
    private MongoTemplate mongoTemplate;

    public Review insertReview(Review review) {
        return mongoTemplate.insert(review, REVIEWS_COL);
    }

    public Review getReview(String id) {
        ObjectId docId = new ObjectId(id);
        return mongoTemplate.findById(docId, Review.class, "reviews");
    }

    public Review updateReview(String id, EditedComment comment) {
        // Retrieve Review object by ObjectID
        Review review = getReview(id);
        // If Review exist:
        if (review != null) {
            // Create edited comment with timestamp
            EditedComment newComment = new EditedComment();
            newComment.setComment(comment.getComment());
            newComment.setRating(comment.getRating());
            newComment.setPosted(LocalDateTime.now());
            // If review has history of edited comments:
            if (review.getEditedList() != null) {
                review.getEditedList().add(newComment);
            // If review has no history of edited comments:
            } else {
                // Create new edited comments list and add edited comment
                List<EditedComment> newEditedList = new LinkedList<>();
                newEditedList.add(newComment);
                review.setEditedList(newEditedList);
            }
            // Since it is updated and last viewed, isEdited is set to true and timestamp to now
            review.setIsEdited(true);
            review.setTimestamp(LocalDateTime.now());
            mongoTemplate.save(review, REVIEWS_COL);
        }
        // If Review does not exist, return null
        return review;
    }
}
