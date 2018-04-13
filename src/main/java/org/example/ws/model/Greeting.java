package org.example.ws.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;


@Entity
public class Greeting {

@Id
@GeneratedValue
private Long id;
private String text;

public Greeting() {

}

public Long getId(){
        return id;
}

public void setId(Long id){
        this.id = id;
}

public String getText(){
        return text;
}

public void setText(String text){
        this.text = text;
}


}
