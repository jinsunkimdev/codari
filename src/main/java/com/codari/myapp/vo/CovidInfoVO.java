package com.codari.myapp.vo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CovidInfoVO implements Comparable<CovidInfoVO> { //비교 능력이 있어요.
	
	private String stateDt;
	private String decideCnt;
	private String deathCnt;
	private String accExamCnt;
	private String accExamCompCnt;
	private String resutlNegCnt;
	private String clearCnt;
	
	private String dailyDecideCnt;
	private String dailyDeathCnt;
	private String dailyExCnt;
	private String dailyExcCnt;
	private String dailyRnCnt;
	private String dailyClCnt;
	
	@Override
	public int compareTo(CovidInfoVO o) { //비교
		// TODO Auto-generated method stub
		return stateDt.compareTo(o.stateDt); //문자 비교, 어센딩 소트
	}
}
