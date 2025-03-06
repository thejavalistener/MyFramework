package thejavalistener.fwk.util;

public class MyRegex
{
	public static final String EMAIL = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
	public static final String DATE_DDMMYYYY="^(?:(?:31(\\/|-|\\.)(?:0?[13578]|1[02]))\\1|(?:(?:29|30)(\\/|-|\\.)(?:0?[13-9]|1[0-2])\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|^(?:29(\\/|-|\\.)0?2\\3(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\\d|2[0-8])(\\/|-|\\.)(?:(?:0?[1-9])|(?:1[0-2]))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$";
	public static final String DATE_YYYYMMDD="^(?:(?:(?:1[6-9]|[2-9]\\d)?\\d{2})(\\/|-|\\.)(?:0?[13578]|1[02])\\1(?:31))$|^(?:(?:(?:1[6-9]|[2-9]\\d)?\\d{2})(\\/|-|\\.)(?:0?[13-9]|1[0-2])\\2(?:29|30))$|^(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00)))(\\/|-|\\.)0?2\\3(?:29)$|^(?:(?:(?:1[6-9]|[2-9]\\d)?\\d{2})(\\/|-|\\.)(?:0?[1-9]|1[0-2])\\4(?:0?[1-9]|1\\d|2[0-8]))$";

	
	
}
