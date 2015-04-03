package team7.test;

import acg.project.action.ActionSet;
import acg.project.cli.CommandLineInterface;
import acg.project.cli.parser.CommandParser;
import acg.project.cli.parser.ParseException;

public class Test {

	private ActionSet actionSet = new ActionSet(new CommandLineInterface());
	
	public static void main(String[] args) throws ParseException {
		
		new Test().test();

	}
	

	private void test() throws ParseException{
		// Templates
		correctParseDefineTrap();
		correctParseDefineCatapult();
		correctParseDefineOLS_XMT();
		correctParseDefineOLS_RCV();
		correctParseDefineCarrier();
		correctParseDefineFighter();
		correctParseDefineTanker();
		correctParseDefineBoomMale();
		correctParseDefineBoomFemale();
		correctDefineTailhook();
		correctDefineBarrier();
		correctDefineAuxTank();
		//correctUndefine(); // undefine works. Don't want to use currently.
		correctShowTemplate();
		correctListTemplates();
		
		// Agents
		correctCreateTrap();
		correctCreateBarrier();
		correctCreateAuxTank();
		correctCreateCatapult();
		correctCreateOLS_XMT();
		correctCreateOLS_RCV();
		correctCreateBoom();
		correctCreateTailhook();
		//correctUncreate(); Uncreate works. Don't want to use currently.
		correctDescribe();
		correctListAgents();
		correctCreateCarrier();
		correctCreateFighter(); //NASTY ERRORS IN FIGHTER
		correctCreateTanker();
		
		// Structural
		//correctPopulateCarrier();
		correctPopulateWorld();
		correctCommit();
		
		// Behavioral
		correctDoAsk();
		correctDoPosition();
		correctDoBarrier();
		correctDoCatapult();
		correctDoSetSpeed();
		correctDoSetAltitude();
		correctDoSetHeading();
		correctDoTailhook();
		correctDoCaptureOLS();
		correctDoBoom();
		correctDoTransfer();
		
		correctForceCoordinates();
		correctForceAltitude();
		correctForceHeading();
		correctForceSpeed();
		correctForceAll();
		
		correctSetWindDirection();
		correcdSetWindSpeed();
		correctGetWind();
		
		// Misc
		correctClockRate();
		correctClockPause();
		correctClockResume();
		correctClockUpdate();
		correctClockOutputRate();
		correctRunFile();
		correctExit();
		correctWait();
	
		incorrectParseDefineTrap();
	}
	
	private void correctWait() throws ParseException{

		CommandParser command = new CommandParser((actionSet), 
				"@WAIT 100");

		command.interpret();
	}

	private void correctExit() throws ParseException{

		CommandParser command = new CommandParser((actionSet), 
				"@EXIT");
		command.interpret();
	}

	private void correctRunFile() throws ParseException{

		CommandParser command = new CommandParser((actionSet), 
				"@RUN commandfile.txt");
		
		command.interpret();
	}

	private void correctClockOutputRate() throws ParseException{

		CommandParser command = new CommandParser((actionSet), 
				"@CLOCK");
		command.interpret();
	}

	private void correctClockUpdate() throws ParseException{

		CommandParser command = new CommandParser((actionSet), 
				"@CLOCK UPDATE");
		command.interpret();
	}

	private void correctClockResume() throws ParseException{

		CommandParser command = new CommandParser((actionSet), 
				"@CLOCK RESUME");
		command.interpret();
	}

	private void correctClockPause() throws ParseException{

		CommandParser command = new CommandParser((actionSet), 
				"@CLOCK PAUSE");
		command.interpret();
	}

	private void correctClockRate() throws ParseException{

		CommandParser command = new CommandParser((actionSet), 
				"@CLOCK 10");
		command.interpret();
	}

	private void correctGetWind() throws ParseException{
		
		CommandParser command = new CommandParser((actionSet), 
				"GET WIND CONDITIONS");	
		command.interpret();
	}

	private void correcdSetWindSpeed() throws ParseException{

		CommandParser command = new CommandParser((actionSet), 
				"SET WIND SPEED 100");
		command.interpret();
	}

	private void correctSetWindDirection() throws ParseException{
		
		CommandParser command = new CommandParser((actionSet), 
				"SET WIND DIRECTION 45");
		command.interpret();
	}

	private void correctForceAll() throws ParseException{
		
		CommandParser command = new CommandParser((actionSet), 
				"@DO aFighter FORCE COORDINATES 45*30\'15\"/110*30\'10\" "
				+ "altitude 25 heading 2 speed 2");
		command.interpret();
	}

	private void correctForceSpeed() throws ParseException{
		CommandParser command = new CommandParser((actionSet), 
				"@do aCarrier force speed 0");
		
		command.interpret();
	}

	private void correctForceHeading() throws ParseException{
		CommandParser command = new CommandParser((actionSet), 
				"@DO aCarrier force heading 25");
		
		command.interpret();
	}

	private void correctForceAltitude() throws ParseException{
		CommandParser command = new CommandParser((actionSet), 
				"@DO aTanker force altitude 50");
		
		command.interpret();
	}

	private void correctForceCoordinates() throws ParseException{
		CommandParser command = new CommandParser((actionSet), 
				"@DO aCarrier force coordinates 90*30'15\"/10*30'10\"");
		
		command.interpret();
	}

	private void correctDoBoom() throws ParseException{
		CommandParser command = new CommandParser((actionSet), 
				"do aTanker boom extend");
		
		command.interpret();
	}

	private void correctDoTransfer() throws ParseException{
		CommandParser command = new CommandParser((actionSet), 
				"do aTanker transfer start");
		
		command.interpret();
	}

	private void correctDoCaptureOLS() throws ParseException{
		CommandParser command = new CommandParser((actionSet), 
				"do aFighter capture OLS");
		
		command.interpret();
	}

	private void correctDoTailhook() throws ParseException{
		CommandParser command = new CommandParser((actionSet), 
				"do aFighter tailhook down");
		
		command.interpret();
	}

	private void correctDoSetHeading() throws ParseException{
		CommandParser command = new CommandParser((actionSet), 
				"do aCarrier set heading 60 left"); // Checked and works with no direction as well.
		
		command.interpret();
	}

	private void correctDoSetAltitude() throws ParseException{
		CommandParser command = new CommandParser((actionSet), 
				"do aTanker set altitude 10");
		
		command.interpret();
	}

	private void correctDoSetSpeed() throws ParseException{
		CommandParser command = new CommandParser((actionSet), 
				"do aCarrier set speed 5");
		
		command.interpret();
	}

	private void correctDoCatapult() throws ParseException{
		CommandParser command = new CommandParser((actionSet), 
				"do aCarrier catapult launch with speed 100");
		
		command.interpret();
	}
	
	private void correctDoBarrier() throws ParseException{
		CommandParser command = new CommandParser((actionSet), 
				"do aCarrier barrier up");
		
		command.interpret();
	}

	private void correctDoPosition() throws ParseException{
		CommandParser command = new CommandParser((actionSet), 
				"do aCarrier position");
		
		command.interpret();
	}

	private void correctDoAsk() throws ParseException{
		CommandParser command = new CommandParser((actionSet), 
				"do aCarrier ask all");
		
		command.interpret();
	}

	private void correctCommit() throws ParseException{
		CommandParser command = new CommandParser((actionSet), 
				"COMMIT");
		
		command.interpret();
	}

	private void correctPopulateWorld() throws ParseException{
		CommandParser command = new CommandParser((actionSet), 
				"populate world with aCarrier aFighter aTanker");
		
		command.interpret();
	}

	private void correctPopulateCarrier() throws ParseException{
		CommandParser command = new CommandParser((actionSet), 
				"populate carrier aCarrier with fighter aFighter");
		
		command.interpret();
	}

	private void correctListAgents() throws ParseException{
		CommandParser command = new CommandParser((actionSet), 
				"list agents");
		
		command.interpret();
	}

	private void correctDescribe() throws ParseException{
		CommandParser command = new CommandParser((actionSet), 
				"describe aM_boom");
		
		command.interpret();
	}

	private void correctUncreate() throws ParseException{
		CommandParser command = new CommandParser((actionSet), 
				"uncreate aTailhook");
		
		command.interpret();
	}

	private void correctCreateTailhook() throws ParseException{
		CommandParser command = new CommandParser((actionSet), 
				"create tailhook aTailhook from tTailhook");
		
		command.interpret();
	}

	private void correctCreateBoom() throws ParseException{
		CommandParser command = new CommandParser((actionSet), 
				"create boom aM_Boom from tM_boom");
		
		command.interpret();
		
		command = new CommandParser((actionSet), 
				"create boom aF_Boom from tF_boom");
		
		command.interpret();
	}

	private void correctCreateOLS_RCV() throws ParseException{
		CommandParser command = new CommandParser((actionSet), 
				"create ols_rcv aOLS_RCV from tOLS_RCV");
		
		command.interpret();
	}

	private void correctCreateOLS_XMT() throws ParseException{
		CommandParser command = new CommandParser((actionSet), 
				"create ols_xmt aOLS_XMT from tOLS_XMT");
		
		command.interpret();
	}

	private void correctCreateCatapult() throws ParseException{
		CommandParser command = new CommandParser((actionSet), 
				"create catapult aCatapult from tCatapult");
		
		command.interpret();
	}

	private void correctCreateAuxTank() throws ParseException{
		CommandParser command = new CommandParser((actionSet), 
				"create aux_tank aTank from tTank");
		
		command.interpret();
	}

	private void correctCreateBarrier() throws ParseException{
		CommandParser command = new CommandParser((actionSet), 
				"create barrier aBarrier from tBarrier");
		
		command.interpret();
	}

	private void correctCreateTrap() throws ParseException{
		CommandParser command = new CommandParser((actionSet), 
				"create trap aTrap from tTrap");
		
		command.interpret();
	}

	private void correctCreateTanker() throws ParseException{
		CommandParser command = new CommandParser((actionSet), 
				"create tanker aTanker from tTanker with boom "
				+ "aF_Boom at coordinates 90*30'15\"/10*30'10\" "
				+ "altitude 25 heading 45 speed 10");
		
		command.interpret();
	}

	private void correctCreateFighter() throws ParseException{
		CommandParser command = new CommandParser((actionSet), 
				"create fighter aFighter from tFighter with "
				+ "OLS aOLS_RCV BOOM aM_Boom tailhook aTailhook "
				+ "tanks aTank " 
				+ "overriding aTailhook.time with 10 "
				+ "at coordinates 45*30'15\"/110*30'10\" "
				+ "altitude 300 heading 45 speed 20");
		
		command.interpret();
	}

	private void correctCreateCarrier() throws ParseException{
		CommandParser command = new CommandParser((actionSet), 
				"create carrier aCarrier from tCarrier with "
				+ "catapult aCatapult barrier aBarrier trap "
				+ "aTrap ols aOLS_XMT at coordinates "
				+ "45*30'15\"/110*30'10\" heading 45 speed 25");
		
		command.interpret();
	}

	private void correctListTemplates() throws ParseException{
		CommandParser command = new CommandParser((actionSet), 
				"list templates");
		
		command.interpret();
	}

	private void correctShowTemplate() throws ParseException{
		CommandParser command = new CommandParser((actionSet), 
				"show template tTank");
		
		command.interpret();
	}
	
	
	
	private void correctUndefine()  throws ParseException{
		CommandParser command = new CommandParser((actionSet), 
				"undefine tBarrier");
		
		command.interpret();
	}

	private void correctDefineAuxTank()  throws ParseException{
		CommandParser command = new CommandParser((actionSet), 
				"define aux_tank tTank amount 50");
		
		command.interpret();
	}

	private void correctDefineBarrier()  throws ParseException{
		CommandParser command = new CommandParser((actionSet), 
				"define barrier tBarrier origin 5:8 azimuth 45 "
				+ "width 10 time 30");
		
		command.interpret();
	}

	private void correctDefineTailhook()  throws ParseException{
		CommandParser command = new CommandParser((actionSet), 
				"define tailhook tTailhook time 5");
		
		command.interpret();
	}

	private void correctParseDefineBoomFemale() throws ParseException{
		CommandParser command = new CommandParser((actionSet), 
				"define boom female tF_boom length 15 diameter 2 "
				+ "elevation 15 flow 15");
		
		command.interpret();
	}

	private void correctParseDefineBoomMale() throws ParseException{
		CommandParser command = new CommandParser((actionSet), 
				"define boom male tM_boom length 15 diameter 2 "
				+ "flow 15");
		
		command.interpret();
	}

	private void correctParseDefineTanker() throws ParseException{
		CommandParser command = new CommandParser((actionSet), 
				"define tanker tTanker speed min 5 MAX 25 DELTA "
				+ "increase 5 decrease 5 turn 15 climb 5 descent 5 "
				+ "tank 100");
		
		command.interpret();
	}
	
	private void correctParseDefineFighter() throws ParseException{
		CommandParser command = new CommandParser((actionSet), 
				"define fighter tFighter speed min 0 max 150 delta "
				+ "increase 5 decrease 5 turn 20 climb 10 "
				+ "descent 10 empty weight 10 fuel initial 6 "
				+ "delta 5");
		
		command.interpret();
	}

	private void correctParseDefineCarrier() throws ParseException{
		CommandParser command = new CommandParser((actionSet), 
				"define carrier tCarrier speed max 200 delta increase 5 "
				+ "decrease 5 turn 15 layout layout.txt");
		
		command.interpret();
	}

	private void correctParseDefineOLS_RCV() throws ParseException{
		CommandParser command = new CommandParser((actionSet), 
				"define ols_rcv tOLS_RCV diameter 5");
		
		command.interpret();
	}

	private void correctParseDefineOLS_XMT() throws ParseException{
		CommandParser command = new CommandParser((actionSet), 
				"define ols_xmt tOLS_XMT origin 5:8 azimuth 74 elevation 10 "
				+ "range 20 diameter 5");
		
		command.interpret();
	}

	private void correctParseDefineCatapult() throws ParseException{
		CommandParser command = new CommandParser((actionSet), 
				"define catapult tCatapult origin 5:8 azimuth 45 "
				+ "length 12 acceleration 10 limit weight 50 "
				+ "speed 200 reset 45");
		
		command.interpret();
	}

	private void correctParseDefineTrap() throws ParseException{
		CommandParser command = new CommandParser((actionSet), 
				"define trap tTrap origin 5:8 azimuth 45 width 10 limit weight 200 speed "
				+ "25 miss 25");
		
		command.interpret();
	}

	
	
	private void incorrectParseDefineTrap(){
//		try{
//			new CommandParser(null, "bad command here");
//			throw new RuntimeException();
//		}
//		catch(ParseException e){
//			
//		}
		
	}

}
