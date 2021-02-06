package com.codari.myapp.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FactFileVO {
	private int file_id;
	private String file_name;
	private int fact_id;
}
