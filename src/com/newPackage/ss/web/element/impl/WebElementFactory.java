package com.csc.ss.web.element.impl;

import com.csc.intellis.constants.ElementType;
import com.csc.intellis.exception.IntelliSException;

/**
 * Factory class for producing executor objects based on element type
 */
public class WebElementFactory {

	private static volatile WebElementFactory instance = null;

	private WebElementFactory() {
	}

	/**
	 * Method to get an instance of this class
	 * 
	 * @return
	 */
	public static WebElementFactory getInstance() {
		if (instance == null) {
			synchronized (WebElementFactory.class) {
				if (instance == null)
					instance = new WebElementFactory();
			}
		}
		return instance;
	}

	/**
	 * Method to generate driver objects
	 * 
	 * @return
	 * @param srvType
	 * @throws MockSrvException
	 */
	public IIntelliSWebElement getElement(String type) throws IntelliSException {
		if (type.equals(ElementType.TEXTBOX.name())) {
			return new IntelliSTextBox();
		} else if (type.equals(ElementType.CHECKUNCHECK.name())) {
			return new IntelliSCheckUncheckCheckBox();
		} else if (type.equals(ElementType.BUTTON.name())) {
			return new IntelliSButton();
		} else if (type.equals(ElementType.COPY.name())) {
			return new IntelliSCopy();
		} else if (type.equals(ElementType.PASTE.name())) {
			return new IntelliSPaste();
		} else if (type.equals(ElementType.RETURNKEY.name())) {
			return new IntelliSReturnKey();
		} else if (type.equals(ElementType.DELETEKEY.name())) {
			return new IntelliSDeleteKey();
		} else if (type.equals(ElementType.ESCAPEKEY.name())) {
			return new IntelliSEscapeKey();
		} else if (type.equals(ElementType.DISABLE.name())) {
			return new IntelliSVerifyDisabled();
		} else if (type.equals(ElementType.VERIFYENABLED.name())) {
			return new IntelliSVerifyEnabled();
		} else if (type.equals(ElementType.TABLE.name())) {
			return new IntelliSTable();
		} else if (type.equals(ElementType.TAB.name())) {
			return new IntelliSTab();
		} else if (type.equals(ElementType.DRAG.name())) {
			return new IntelliSDrag();
		} else if (type.equals(ElementType.DROP.name())) {
			return new IntelliSDrop();
		} else if (type.equals(ElementType.VERIFY.name())) {
			return new IntelliSVerifyTextPresent();
		} else if (type.equals(ElementType.TEXTBOXVERIFY.name())) {
			return new IntelliSVerifyTextboxDataPresent();
		} else if (type.equals(ElementType.NOTVERIFY.name())) {
			return new IntelliSVerifyTextNOTPresent();
		} else if (type.equals(ElementType.TODAYDATE.name())) {
			return new IntelliSTodayDate();
		} else if (type.equals(ElementType.YESTERDAYDATE.name())) {
			return new IntelliSYesterDayDate();
		} else if (type.equals(ElementType.TOMORROWDATE.name())) {
			return new IntelliSTommorowDate();
		} else if (type.equals(ElementType.CurrMnthDate.name())) {
			return new IntelliSCurrntMnthDate();
		} else if (type.equals(ElementType.NextMnthDate.name())) {
			return new IntelliSNextMnthDate();
		} else if (type.equals(ElementType.SWITCHTOCHILD.name())) {
			return new IntelliSSwitchToParentWindow();
		} else if (type.equals(ElementType.SWITCHTOPARENT.name())) {
			return new IntelliSSwitchToChildWindow();
		} else if (type.equals(ElementType.DATE.name())) {
			return new IntelliSDatePicker();
		} else if (type.equals(ElementType.SETDATE.name())) {
			return new IntelliSSetDate();
		} else if (type.equals(ElementType.TOAST.name())) {
			return new IntelliSToast();
		} else if (type.equals(ElementType.TOOLTIP.name())) {
			return new IntelliSVerifyToolTipText();
		} else if (type.equals(ElementType.LINK.name())) {
			return new IntelliSLink();
		} else if (type.equals(ElementType.JLINK.name())) {
			return new IntelliSJLink();
		} else if (type.equals(ElementType.HOVER.name())) {
			return new IntelliSOnHover();
		} else if (type.equals(ElementType.TEXTAREA.name())) {
			return new IntelliSTextArea();
		} else if (type.equals(ElementType.CHECKBOX.name())) {
			return new IntelliSCheckBox();
		} else if (type.equals(ElementType.RADIOBUTTON.name())) {
			return new IntelliSRadioButton();
		} else if (type.equals(ElementType.IMAGE.name())) {
			return new IntelliSImage();
		} else if (type.equals(ElementType.SCROLL.name())) {
			return new IntelliSScroll();
		} else if (type.equals(ElementType.ALERT.name())) {
			return new IntelliSJSAlert();
		} else if (type.equals(ElementType.ERRMESSAGE.name())) {
			return new IntelliSFieldLvlErrMessages();
		} else if (type.equals(ElementType.DYNAMICSELBOX.name())) {
			return new IntelliSDynamicSelectBox();
		} else if (type.equals(ElementType.ELEMENTNOTPRESENT.name())) {
			return new IntelliSElementNotPresent();
		} else if (type.equals(ElementType.JHOVER.name())) {
			return new IntelliSJHover();
		} else if (type.equals(ElementType.SELECTBOX.name())) {
			return new IntelliSSelectBox() {
			};
		} else {
			throw new IntelliSException("500", "Element type not defined :"
					+ type);
		}
	}
}