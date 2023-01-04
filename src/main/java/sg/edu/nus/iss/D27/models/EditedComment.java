package sg.edu.nus.iss.D27.models;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import org.bson.Document;

import jakarta.json.Json;
import jakarta.json.JsonObject;

public class EditedComment {
    
    // Edited Comment members
    private String comment; 
    private Integer rating;
    private LocalDateTime posted;

    // Generate getter and setter
    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }
    public Integer getRating() {
        return rating;
    }
    public void setRating(Integer rating) {
        this.rating = rating;
    }
    public LocalDateTime getPosted() {
        return posted;
    }
    public void setPosted(LocalDateTime posted) {
        this.posted = posted;
    }

    // Create Edited Comment object from document object
    public static EditedComment create(Document document) {
        EditedComment comment = new EditedComment();
        comment.setComment(document.getString("comment"));
        comment.setRating(document.getInteger("rating"));
        LocalDateTime postDateTime = Instant.ofEpochMilli(document.getDate("posted").getTime())
                                            .atZone(ZoneId.systemDefault())
                                            .toLocalDateTime();
        comment.setPosted(postDateTime);
        return comment;
    }    
    
    // Create Json Object from Edited Comment object
    public JsonObject toJSON() {
        return Json.createObjectBuilder()
                    .add("comment", comment)
                    .add("rating", rating)
                    .add("posted", posted.toString())
                    .build();
    }
}
