package com.codari.myapp.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openkoreantext.processor.OpenKoreanTextProcessorJava;
import org.openkoreantext.processor.tokenizer.KoreanTokenizer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.extern.slf4j.Slf4j;
import scala.collection.Seq;

@Slf4j
@Controller
@RequestMapping("")
public class IndexController {
	@RequestMapping(value = {"", "/index", "codari"}, method = RequestMethod.GET)
	public String Index() {
		return "/index";
	}

	@RequestMapping(value = "/word-cloud/jsoupData", method = RequestMethod.GET)
	@ResponseBody
	public List<Object> jsoupData() {
		StringBuilder sb = new StringBuilder();
		int pageCnt = 1;
		for (int i = 0; i < 3; i++) {
			String url="https://search.naver.com/search.naver?&where=news&query=%EC%BD%94%EB%A1%9C%EB%82%98&sm=tab_pge&sort=0&photo=0&field=0&reporter_article=&pd=0&ds=&de=&docid=&nso=so:r,p:all,a:all&mynews=0&cluster_rank=73&start="+pageCnt+"&refresh_start=0";
			pageCnt += 10;
	        Document doc, artclDoc;
	        try {
	            doc = Jsoup.connect(url).get();
	            Elements contentP = doc.select("a");
	            Elements contentTit=contentP.select(".info");
	            for(Element e:contentTit) {
	                String artclAddr=e.select("a").attr("href");
	                artclDoc = Jsoup.connect(artclAddr).get(); 
	                String artclContent;
	                if (artclDoc.select("div#articleBodyContents").text() != null) {
	                	artclContent = artclDoc.select("div#articleBodyContents").text();
	                	sb.append(artclContent);
	                }
	            }
	        } catch (IllegalArgumentException e) {
				log.info("주소가 비어 있습니다.");
	        } catch (Exception e) {
	        	e.printStackTrace(); 
	        }
		}
        // 가공되지 않은 원본 데이터
        String result = sb.toString();
        
        // Normalize
        CharSequence normalized = OpenKoreanTextProcessorJava.normalize(result);
        normalized = OpenKoreanTextProcessorJava.normalize(result);
        
        // Tokenize
        Seq<KoreanTokenizer.KoreanToken> tokens = OpenKoreanTextProcessorJava.tokenize(normalized);
        
        // Tokenize > List(sortable)
        List<String> javaParsed = OpenKoreanTextProcessorJava.tokensToJavaStringList(tokens);
        
        Map<String, Integer> words = new LinkedHashMap<>();
        for(String s:javaParsed) {
        	if(s.contains("-") || s.contains(")") || s.contains("(") || s.contains("부터") || s.contains("네이버") || s.contains("바이러스") || s.contains("19") || s.contains("코로나") || s.contains("기자") || s.contains("연합뉴스") || s.contains("하고") || s.contains("된다") || s.contains("됐다") || s.contains("지난") || s.contains("까지") || s.contains("하는") || s.contains("대해") || s.contains("밝혔다") || s.contains("All") || s.contains("Copyright") || s.contains("rights") || s.contains("by") || s.contains("·") || s.contains(",") || s.contains("’") || s.contains("‘") || s.contains("…") || s.contains("“") || s.contains("”") || s.contains(".") || s.contains("]") || s.contains("'") || s.contains("[") || s.equals("으로") || s.equals("에서") || s.equals("이다") || s.equals("있다") || s.equals("했다") || s.contains("있다.")) {
            	continue;  
            }
        	if(s.equals("진자")) {
        		s = "확진자";
        	}
            if(s.length()>1){
            	if(words.containsKey(s)) {
            		words.put(s, words.get(s) + 1);
            		continue;
            	} else {
            		words.put(s, 1);
            	}
            }
        }
        
        List<Object> wordList = new ArrayList<Object>();

        for (String s : words.keySet()) {
        	if(words.get(s) > 20) {
        		Map<String, String> map1 = new LinkedHashMap<String, String>();
            	map1.put("text", s);
            	map1.put("size", words.get(s).toString());
            	wordList.add(map1);	
        	} else {
        		continue;
        	}
        }
        
		return wordList;
	}
}
