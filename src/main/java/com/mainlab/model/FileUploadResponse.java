package com.mainlab.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FileUploadResponse implements Serializable {
    private static final long serialVersionUID = -5653835543540834966L;

    private String objectPath;
}
