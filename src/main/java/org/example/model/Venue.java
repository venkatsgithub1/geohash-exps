package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Venue {
    private String name;
    private String latitude;
    private String longitude;
    private String rating;
}
