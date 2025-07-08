package com.example.Library.Writer;

import com.example.Library.Utils.RecordStatus;
import lombok.Data;

@Data
public class WriterDto {
    Long id;
    String name;
    private RecordStatus recordStatus;
}
