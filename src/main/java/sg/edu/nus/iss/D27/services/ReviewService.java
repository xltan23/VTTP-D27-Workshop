package sg.edu.nus.iss.D27.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.edu.nus.iss.D27.models.EditedComment;
import sg.edu.nus.iss.D27.models.Review;
import sg.edu.nus.iss.D27.repositories.ReviewRepository;

@Service
public class ReviewService {
    
    @Autowired
    private ReviewRepository reviewRepo;

    // Review insertion does not fill up List<EditedComment>, isEdited and timestamp
    public Review insertReview(Review review) {
        return reviewRepo.insertReview(review);
    }

    // Review retrieval fills up isEdited and timestamp
    public Review getReview(String id) {
        Review review = reviewRepo.getReview(id);
        // If review has history of edited comments:
        if (review.getEditedList() != null) {
            List<EditedComment> editedList = review.getEditedList();
            if (editedList.size() > 0) {
                // Set isEdited to true if edited comment list is not empty
                review.setIsEdited(true);
            } else {
                // Set isEdited to false if edited comment list is empty
                review.setIsEdited(false);
            }
        }
        // Set current timestamp so the last date of retrieval is recorded 
        review.setTimestamp(LocalDateTime.now());
        return review;
    }

    public Review updateReview(String id, EditedComment comment) {
        return reviewRepo.updateReview(id, comment);
    }
}
