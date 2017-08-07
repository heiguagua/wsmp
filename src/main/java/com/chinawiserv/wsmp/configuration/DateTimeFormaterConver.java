package com.chinawiserv.wsmp.configuration;

import java.beans.PropertyEditorSupport;
import java.time.format.DateTimeFormatter;

public class DateTimeFormaterConver extends PropertyEditorSupport {

	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		this.setValue(DateTimeFormatter.ofPattern(text));
	}
}
