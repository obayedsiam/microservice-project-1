package com.example.Library.Book;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
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

    @Schema(description = "Rating of the book (out of 5)", example = "4.5")
    private Double rating;

    @Schema(description = "Indicates whether the book has been read", example = "true")
    private Boolean isRead;

    @Schema(description = "Reading progress in percentage", example = "75")
    private Integer readingPercentage;

    @Schema(description = "Person who gifted the book (if any)", example = "Uncle John")
    private String giftedBy;

    @Schema(description = "Date when the book was bought", example = "2023-08-15")
    private LocalDate buyingDate;

    @Schema(description = "Place where the book was bought", example = "Dhaka Book Fair")
    private String buyingLocation;

    @Schema(description = "Current physical location of the book", example = "Home Library Shelf A3")
    private String currentBookLocation;

    @Schema(description = "Indicates whether the book is currently borrowed", example = "false")
    private Boolean borrowed;

    @Schema(description = "Name of the person who borrowed the book", example = "Sabbir Ahmed")
    private String borrowerName;

    @Schema(description = "Phone number of the borrower", example = "+8801712345678")
    private String borrowerPhone;

    @Schema(description = "Writer ID for the book", example = "3")
    private Long writerId;

    @Schema(description = "List of genre IDs for the book", example = "[1, 3]")
    private List<Long> genreIds;
}
