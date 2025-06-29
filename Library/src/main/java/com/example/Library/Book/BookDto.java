package com.example.Library.Book;

import com.example.Library.Utils.Genre;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Data Transfer Object for Book")
public class BookDto {

    @Schema(description = "Unique identifier of the book", example = "100")
    private Long id;

    @Schema(description = "Name of the book", example = "Pride and Prejudice")
    private String name;

    @Schema(description = "List of Writer IDs for the book",
            example = "[1, 2, 3]")
    private List<Long> writerList;

    @Schema(description = "List of genres for the book")
    private List<Genre> genre;
}
