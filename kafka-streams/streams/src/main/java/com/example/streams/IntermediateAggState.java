package com.example.streams;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IntermediateAggState {
    private Integer tempCount;
    private Double tempSum;
    private Integer humCount;
    private Double humSum;
}
