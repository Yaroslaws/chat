package code.chat.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.EqualsAndHashCode;


import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@Data
@EqualsAndHashCode(of = {"id"})
public class Message  {

    public Message(Long id, String title, LocalDate creationDate) {
        this.id = id;
        this.title = title;
        this.creationDate = creationDate;
    }


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonView(Views.IdName.class)
    private Long id;



    @ManyToOne()
    @JoinColumn(name="user_id")
    private User user;

    @JsonView(Views.IdName.class)
    private String title;

    @JsonView(Views.FullMessage.class)
    private LocalDate creationDate;

    public Message() {

    }



    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
