package com.codari.myapp.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FactVO {
	private int fact_id;
	private String fact_title;
	private String fact_content;
	private int user_id;
	private String fact_thumbnail;
}
