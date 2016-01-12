package com.eixox;

import com.eixox.globalization.Culture;
import com.eixox.globalization.Cultures;
import com.eixox.ui.ControlState;
import com.eixox.ui.ControlType;
import com.eixox.ui.UIPresentation;
import com.eixox.ui.UIPresentationMember;

public abstract class Usecase {

	public final UsecaseAspect aspect;
	public final UIPresentation presentation;
	public Culture culture;

	public Usecase(Culture culture) {
		this.culture = culture;
		this.aspect = UsecaseAspect.getInstance(getClass());
		this.presentation = new UIPresentation(this.aspect.getCount());
		for (UsecaseAspectMember member : this.aspect) {
			if (member.ui.controlType != ControlType.NONE)
				this.presentation.add(new UIPresentationMember(member.ui));
		}
	}

	public Usecase() {
		this(Cultures.EN_US);
	}

	public void parsePresentation() {
		for (UIPresentationMember uiMember : this.presentation) {
			UsecaseAspectMember member = this.aspect.get(uiMember.name);
			if (member != null)
				member.parse(this, culture, uiMember.value);
		}
	}
	
	public final void parsePresentation(Culture culture){
		this.culture = culture;
		parsePresentation();
	}

	public void formatPresentation() {
		for (UIPresentationMember uiMember : this.presentation) {
			UsecaseAspectMember member = this.aspect.get(uiMember.name);
			if (member != null)
				uiMember.value = member.getValueToString(this, culture);
		}
	}

	public String getTitle() {
		return toString();
	}

	// private static final DateFormat DATE_FORMAT = new
	// SimpleDateFormat("yyyy-MM-dd");
	// private static final DateFormat TIME_FORMAT = new
	// SimpleDateFormat("HH:mm:ss");

	protected void postExecute(UsecaseResult result) {
		/*
		 * try { BasicDBObject attribs = new BasicDBObject(); int s =
		 * result.presentation.size(); for (int i = 0; i < s; i++) {
		 * UIPresentationMember uim = result.presentation.get(i);
		 * attribs.append(uim.name, uim.value); } Date now = new Date();
		 * BasicDBObject dbu = new BasicDBObject("class", getClass().getName());
		 * dbu.append("created_date", DATE_FORMAT.format(now));
		 * dbu.append("created_time", TIME_FORMAT.format(now));
		 * dbu.append("title", getTitle()); dbu.append("presentation", attribs);
		 * dbu.append("version", 1.0); dbu.append("message", result.message);
		 * dbu.append("resultType", result.resultType.toString()); if
		 * (result.exception != null) dbu.append("exception",
		 * result.exception.getMessage());
		 * 
		 * MongoClient client = new MongoClient("mongodb.eixox.com.br");
		 * client.getDB("profiler").getCollection("usecase_log").insert(dbu);
		 * 
		 * } catch (Exception e) { System.out.println(e); }
		 */
	}

	public synchronized boolean validate() {
		int l = this.presentation.size();
		boolean valid = true;
		for (int i = 0; i < l; i++) {
			UsecaseAspectMember uam = this.aspect.get(i);
			Object value = uam.getValue(this);
			UIPresentationMember uipm = this.presentation.get(i);
			uipm.message = uam.restrictions.getRestrictionMessageFor(value);
			if (uipm.message != null && uipm.message.length() > 0) {
				uipm.controlState = ControlState.ERROR;
				valid = false;
			} else
				uipm.controlState = ControlState.SUCCESS;
		}
		return valid;
	}

	protected abstract void executeFlow(UsecaseResult result) throws Exception;

	public synchronized final UsecaseResult execute() {
		UsecaseResult result = new UsecaseResult();
		result.presentation = this.presentation;
		
		try {
			if (validate()) {
				executeFlow(result);
			} else {
				result.message = "Os campos estão inválidos";
				result.resultType = UsecaseResultType.VALIDATION_FAILED;
			}
		} catch (Exception ex) {
			result.exception = ex;
			result.resultType = UsecaseResultType.EXCEPTION;
			result.message = ex.toString();
		}
		try {
			postExecute(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	public static final boolean isValidName(String name) {
		if (name == null || name.isEmpty())
			return false;

		int l = name.length();

		if (l > 255)
			return false;

		for (int i = 0; i < l; i++) {
			char c = name.charAt(i);
			if (!Character.isLetterOrDigit(c))
				if (c != '-')
					return false;

		}

		return true;
	}

}
