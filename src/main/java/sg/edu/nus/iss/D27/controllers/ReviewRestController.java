package sg.edu.nus.iss.D27.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import sg.edu.nus.iss.D27.models.EditedComment;
import sg.edu.nus.iss.D27.models.Review;
import sg.edu.nus.iss.D27.services.ReviewService;

@RestController
@RequestMapping(path = "/api/review")
public class ReviewRestController {
    
    @Autowired
    private ReviewService reviewSvc;

    @PostMapping
    public ResponseEntity<String> insertReview(@RequestBody String jsonString) {
        Review review = Review.create(jsonString);
        Review insertedReview = reviewSvc.insertReview(review);
        JsonObject jo = Json.createObjectBuilder()
                            .add("Added By", insertedReview.getUser())
                            .add("Game Reviewed", insertedReview.getBoardGame())
                            .add("Time of post", insertedReview.getPosted().toString())
                            .build();
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(jo.toString());
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<String> getReview(@PathVariable String id) {
        Review review = reviewSvc.getReview(id);
        JsonObject jo = Json.createObjectBuilder()
                            .add("review", review.toJSON())
                            .build();
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(jo.toString());
    }

    @PutMapping(path = "{id}")
    public ResponseEntity<String> updateReview(@PathVariable String id, @RequestBody EditedComment comment) {
        Review review = reviewSvc.updateReview(id, comment);
        JsonObject jo = Json.createObjectBuilder()
                            .add("review", review.toJSON())
                            .build();
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(jo.toString());
    }
}
