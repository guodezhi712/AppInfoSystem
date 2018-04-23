package cn.appsys.tools;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.core.convert.converter.Converter;

public class StringToDateConverter implements Converter<String,Date> {
	private String datePatter;
	
	public StringToDateConverter(String datePatter) {
		this.datePatter = datePatter;
	}
	@Override
	public Date convert(String s) {
		Date date=null;
		try {
			date=new SimpleDateFormat(datePatter).parse(s);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return date;
	}

}
