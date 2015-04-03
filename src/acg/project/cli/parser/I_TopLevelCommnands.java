package acg.project.cli.parser;

/**
 * Describes all of the available top level commands.
 * 
 * Use this instead of hardcoding the strings into your code.
 * @author ben
 *
 */
public interface I_TopLevelCommnands {
	
	public static final String DEFINE = "DEFINE";
	public static final String CREATE = "CREATE";
	public static final String LIST = "LIST";
	public static final String UNDEFINE = "UNDEFINE";
	public static final String DO = "DO";
	public static final String ATDO = "@DO";
	public static final String SHOW = "SHOW";
	public static final String COMMIT = "COMMIT";
	public static final String POPULATE = "POPULATE";
	public static final String GET = "GET";
	public static final String SET = "SET";
	public static final String CLOCK = "@CLOCK";
	public static final String RUN = "@RUN";
	public static final String EXIT = "@EXIT";
	public static final String WAIT = "@WAIT";
	public static final String UNCREATE = "UNCREATE";
	public static final String DESCRIBE = "DESCRIBE";

}
