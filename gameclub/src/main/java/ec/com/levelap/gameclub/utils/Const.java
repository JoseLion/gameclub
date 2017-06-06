package ec.com.levelap.gameclub.utils;

public interface Const {

	public static final String SCHEMA = "gameclub";

	public static final String PACKAGE_NAMING = "ec.com.levelap";

	public static final String JNDI_DATA_BASE = "java:jboss/jdbc/GameClubDS";

	public static final String JNDI_MAIL = "java:jboss/mail/GameClub";

	public final static String PASSWORD_SYMBOLS = "1234567890abcdefghijklmnopqrstuvwxyz0987654321";

	public final static int ENCODER_STRENGTH = 12;

	public final static Integer TABLE_SIZE = 20;

	public final static String ADMIN_USER = "adminUser";

	public final static String PUBLIC_USER = "publicUser";

	public final static String KUSHKI_PLAN_NAME = "GAMECLUB";

	public final static String KUSHKI_PERIODICITY = "custom";

	public final static String KUSHKI_LANGUAGE = "es";
	
	public final static long POSTGRESQL_MAX_DATE = 253402318800000L;
}
