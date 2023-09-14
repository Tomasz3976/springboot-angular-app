package io.getarrays.server.model;

import io.getarrays.server.enumeration.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;

import static javax.persistence.GenerationType.AUTO;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Server {

    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;

    @Column(unique = true)
    @NotEmpty(message = "IP Address cannot be empty or null")
    private String ipAddress;

    private String name;
    private String memory;
    private String type;
    private String imageUrl;
    private Status status;
}
