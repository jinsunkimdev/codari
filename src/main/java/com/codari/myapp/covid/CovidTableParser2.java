package com.codari.myapp.covid;
import java.text.SimpleDateFormat;

import java.util.Calendar;

import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.codari.myapp.vo.CovidDetailVO;

@lombok.extern.slf4j.Slf4j
public class CovidTableParser2 {
	private static String getTagValue(String tag, Element eElement) {
	    NodeList nlList = eElement.getElementsByTagName(tag).item(0).getChildNodes();
	    Node nValue = (Node) nlList.item(0);
	    if(nValue == null)
	        return null;
	    return nValue.getNodeValue();
	}
	 	 	public List<CovidDetailVO> getCovidDetail() {
	 	 	List<CovidDetailVO> cdList = new ArrayList<>();
	 		int page = 1;	// 페이지 초기
	 		Calendar cal = Calendar.getInstance();
	 		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	 		String today = sdf.format(cal.getTime());
	 		
	 		try{
	 			while(true){
	 				// parsing할 url 지정(API 키 포함해서)
	 				String url = "http://openapi.data.go.kr/openapi/service/rest/Covid19/getCovid19SidoInfStateJson?serviceKey=euX6vFhPLUxGCkMSRoTlyG4Dwdy%2BP%2BHjEiRR5hCNLSd6L87ZLNyZb50xq8PXcYK3cOCC2r2SH4i7DFbRZTElAg%3D%3D&numOfRows=10&startCreateDt="+today+"&endCreateDt="+today+"&pageNo="+page;
	 				
	 				DocumentBuilderFactory dbFactoty = DocumentBuilderFactory.newInstance();
	 				DocumentBuilder dBuilder = dbFactoty.newDocumentBuilder();
	 				Document doc = dBuilder.parse(url);
	 				
	 				// root tag
	 				doc.getDocumentElement().normalize();
	 				System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
	 				
	 				// 파싱할 tag
	 				NodeList nList = doc.getElementsByTagName("item");
	 				
	 				for(int temp = 0; temp < nList.getLength(); temp++){
	 					Node nNode = nList.item(temp);
	 					if(nNode.getNodeType() == Node.ELEMENT_NODE){
	 						
	 						Element eElement = (Element) nNode;
	 						CovidDetailVO cd = new CovidDetailVO();
	 						cd.setDeathCnt(getTagValue("deathCnt", eElement));
							cd.setDefCnt(getTagValue("defCnt", eElement));
							cd.setGubun(getTagValue("gubun", eElement));
							cd.setLocalOccCnt(getTagValue("localOccCnt", eElement));
							cd.setOverFlowCnt(getTagValue("overFlowCnt", eElement));
							cd.setStdDay(getTagValue("stdDay", eElement));
							cdList.add(cd);
	 					}	// for end
	 						cdList.sort(null);
	 						log.info(cdList.toString());
	 				}	// if end
	 				
	 				page += 1;
	 				System.out.println("page number : "+page);
	 				if(page > 1){	
	 					break;
	 				}
	 			}	// while end
	 			
	 		} catch (Exception e){	
	 			e.printStackTrace();
	 		}	// try~catch end
	 		return cdList;
	 	}	// main end
	 }	// class end
	