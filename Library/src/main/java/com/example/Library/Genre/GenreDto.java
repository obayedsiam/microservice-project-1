package com.example.Library.Genre;

import com.example.Library.Utils.RecordStatus;
import lombok.Data;

@Data
public class GenreDto {
    private Long id;
    private String name;
    private RecordStatus recordStatus;
}
