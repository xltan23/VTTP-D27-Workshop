package sg.edu.nus.iss.D27.models;

import java.io.StringReader;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import org.bson.Document;

import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import jakarta.json.JsonReader;

public class Review {
    
    // Review members
    private String id;
    private String user;
    private Integer rating;
    private String comment;
    private Integer gameId;
    private String boardGame;
    private LocalDateTime posted;
    private List<EditedComment> editedList;
    private Boolean isEdited;
    private LocalDateTime timestamp;
    
    // Review Constructor
    public Review() {
    }

    public Review(String id, String user, Integer rating, String comment, Integer gameId, String boardGame) {
        this.id = id;
        this.user = user;
        this.rating = rating;
        this.comment = comment;
        this.gameId = gameId;
        this.boardGame = boardGame;
        this.posted = LocalDateTime.now();
    }

    // Generate getter and setter
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getUser() {
        return user;
    }
    public void setUser(String user) {
        this.user = user;
    }
    public Integer getRating() {
        return rating;
    }
    public void setRating(Integer rating) {
        this.rating = rating;
    }
    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }
    public Integer getGameId() {
        return gameId;
    }
    public void setGameId(Integer gameId) {
        this.gameId = gameId;
    }
    public String getBoardGame() {
        return boardGame;
    }
    public void setBoardGame(String boardGame) {
        this.boardGame = boardGame;
    }
    public LocalDateTime getPosted() {
        return posted;
    }
    public void setPosted(LocalDateTime posted) {
        this.posted = posted;
    }
    public List<EditedComment> getEditedList() {
        return editedList;
    }

    public void setEditedList(List<EditedComment> editedList) {
        this.editedList = editedList;
    }

    public Boolean getIsEdited() {
        return isEdited;
    }

    public void setIsEdited(Boolean isEdited) {
        this.isEdited = isEdited;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    // Create Review object from Document retrieved from query
    public static Review create(Document document) {
        Review review = new Review();
        review.setId(document.getObjectId("_id").toString());
        review.setUser(document.getString("user"));
        review.setRating(document.getInteger("rating"));
        review.setComment(document.getString("comment"));
        review.setGameId(document.getInteger("gameId"));
        review.setBoardGame(document.getString("boardGame"));
        LocalDateTime postDateTime = Instant.ofEpochMilli(document.getDate("posted").getTime())
                                            .atZone(ZoneId.systemDefault())
                                            .toLocalDateTime();
        review.setPosted(postDateTime);
        return review;
    }

    // Create Review object from JsonObject
    public static Review create(JsonObject jo) {
        Review review = new Review();
        review.setUser(jo.getString("user"));
        review.setRating(jo.getInt("rating"));
        review.setComment(jo.getString("comment"));
        review.setGameId(jo.getInt("gameId"));
        review.setBoardGame(jo.getString("boardGame"));
        review.setPosted(LocalDateTime.now());
        return review;
    }

    // Create Review object from JsonString
    public static Review create(String jsonString) {
        StringReader sr = new StringReader(jsonString);
        JsonReader jr = Json.createReader(sr);
        return create(jr.readObject());
    }
    
    // Creating Json Object from Review Object
    public JsonObject toJSON() {
        JsonObjectBuilder job = Json.createObjectBuilder();
        job.add("_id", id);
        job.add("user", user);
        job.add("rating", rating);
        job.add("comment", comment);
        job.add("gameId", gameId);
        job.add("boardGame", boardGame);
        job.add("posted", posted.toString());
        if (editedList != null) {
            // List<JsonObject> JsonEditedList = editedList.stream().map(e -> e.toJSON()).toList();
            JsonArrayBuilder jab = Json.createArrayBuilder();
            for (EditedComment ec : editedList) {
                jab.add(ec.toJSON());
            }
            job.add("Edited", jab.build().toString());
            job.add("Is Edited", isEdited);
        }
        job.add("timestamp", timestamp.toString());
        return job.build();
    }

    @Override
    public String toString() {
        return "Review [id=" + id + ", user=" + user + ", rating=" + rating + ", comment=" + comment + ", gameId="
                + gameId + ", boardGame=" + boardGame + ", posted=" + posted + "]";
    }

}
