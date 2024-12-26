package com.springboot.journalApp.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.*;
import java.time.LocalDateTime;
import java.util.Date;

@Document(collection = "journal_entries") //This annotation is used to indicate that the class is a MongoDB document.
@Data //This annotation is used to generate getters and setters for the fields.
@NoArgsConstructor //This annotation is used to generate a no-argument constructor.
@AllArgsConstructor //This annotation is used to generate a constructor that initializes all fields.
public class JournalEntry {

    @Id //This annotation is used to indicate that the field is a primary key.
    @JsonProperty("id") // We map the ObjectId to the "id" field in JSON output
    private ObjectId id;

    private String title;
    private String content;
    private LocalDateTime date;

}
