package com.mainlab.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.mainlab.common.DateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.joda.time.DateTime;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserCareer implements Serializable {
    private static final long serialVersionUID = -589415825044635602L;

    private int careerId;
    @JsonIgnore
    private String userId;
    @JsonSerialize(using = DateTimeSerializer.class)
    private DateTime careerStart;
    @JsonSerialize(using = DateTimeSerializer.class)
    private DateTime careerEnd;
    private String company;
    private String jobTitle;
    private String description;

    private List<UserProject> userProjectList = new LinkedList<>();
}
