package ec.com.levelap.gameclub.utils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public interface Const {

	public static final String PACKAGE_NAMING = "ec.com.levelap";

	public static final String JNDI_DATA_BASE = "java:jboss/jdbc/GameClubDS";

	public static final String JNDI_MAIL = "java:jboss/mail/GameClub";

	public final static Date DEFAULT_START_DATE = java.sql.Date.valueOf(LocalDateTime.ofInstant(new Date(0).toInstant(), ZoneId.systemDefault()).toLocalDate());

	public final static String PASSWORD_SYMBOLS = "1234567890abcdefghijklmnopqrstuvwxyz0987654321";

	public final static int ENCODER_STRENGTH = 12;

	public final static Integer TABLE_SIZE = 20;

}
