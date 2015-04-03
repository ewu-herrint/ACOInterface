package acg.project.cli.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import acg.architecture.datatype.Acceleration;
import acg.architecture.datatype.Altitude;
import acg.architecture.datatype.AngleNavigational;
import acg.architecture.datatype.AttitudePitch;
import acg.architecture.datatype.CoordinateCartesianRelative;
import acg.architecture.datatype.CoordinateWorld;
import acg.architecture.datatype.Distance;
import acg.architecture.datatype.Flow;
import acg.architecture.datatype.Identifier;
import acg.architecture.datatype.Latitude;
import acg.architecture.datatype.Longitude;
import acg.architecture.datatype.Percent;
import acg.architecture.datatype.Rate;
import acg.architecture.datatype.Speed;
import acg.architecture.datatype.Time;
import acg.architecture.datatype.Weight;
import acg.project.action.ActionCreationalCreate;
import acg.project.action.ActionSet;
import acg.project.action.command.ParameterAssignment;
import acg.project.action.command.behavioral.A_CommandBehavioral;
import acg.project.action.command.behavioral.A_CommandBehavioralDo;
import acg.project.action.command.behavioral.CommandBehavioralDoAsk;
import acg.project.action.command.behavioral.CommandBehavioralDoAsk.E_Parameter;
import acg.project.action.command.behavioral.CommandBehavioralDoBarrier;
import acg.project.action.command.behavioral.CommandBehavioralDoBoom;
import acg.project.action.command.behavioral.CommandBehavioralDoCaptureOLS;
import acg.project.action.command.behavioral.CommandBehavioralDoCatapult;
import acg.project.action.command.behavioral.CommandBehavioralDoForceAll;
import acg.project.action.command.behavioral.CommandBehavioralDoForceAltitude;
import acg.project.action.command.behavioral.CommandBehavioralDoForceCoordinates;
import acg.project.action.command.behavioral.CommandBehavioralDoForceHeading;
import acg.project.action.command.behavioral.CommandBehavioralDoForceSpeed;
import acg.project.action.command.behavioral.CommandBehavioralDoPosition;
import acg.project.action.command.behavioral.CommandBehavioralDoSetAltitude;
import acg.project.action.command.behavioral.CommandBehavioralDoSetHeading;
import acg.project.action.command.behavioral.CommandBehavioralDoSetHeading.E_Direction;
import acg.project.action.command.behavioral.CommandBehavioralDoSetSpeed;
import acg.project.action.command.behavioral.CommandBehavioralDoTailhook;
import acg.project.action.command.behavioral.CommandBehavioralDoTransfer;
import acg.project.action.command.behavioral.CommandBehavioralGetWindConditions;
import acg.project.action.command.behavioral.CommandBehavioralSetWindDirection;
import acg.project.action.command.behavioral.CommandBehavioralSetWindSpeed;
import acg.project.action.command.creational.A_CommandCreational;
import acg.project.action.command.creational.create.CommandCreationalCreateAuxiliaryTank;
import acg.project.action.command.creational.create.CommandCreationalCreateBarrier;
import acg.project.action.command.creational.create.CommandCreationalCreateBoomFemale;
import acg.project.action.command.creational.create.CommandCreationalCreateBoomMale;
import acg.project.action.command.creational.create.CommandCreationalCreateCarrier;
import acg.project.action.command.creational.create.CommandCreationalCreateCatapult;
import acg.project.action.command.creational.create.CommandCreationalCreateFighter;
import acg.project.action.command.creational.create.CommandCreationalCreateOLSReceiver;
import acg.project.action.command.creational.create.CommandCreationalCreateOLSTransmitter;
import acg.project.action.command.creational.create.CommandCreationalCreateTailhook;
import acg.project.action.command.creational.create.CommandCreationalCreateTanker;
import acg.project.action.command.creational.create.CommandCreationalCreateTrap;
import acg.project.action.command.creational.create.CommandCreationalDescribe;
import acg.project.action.command.creational.create.CommandCreationalListAgents;
import acg.project.action.command.creational.create.CommandCreationalUncreate;
import acg.project.action.command.creational.define.A_CommandCreationalDefine;
import acg.project.action.command.creational.define.A_CommandCreationalDefineBoom;
import acg.project.action.command.creational.define.CommandCreationalDefineAuxiliaryTank;
import acg.project.action.command.creational.define.CommandCreationalDefineBarrier;
import acg.project.action.command.creational.define.CommandCreationalDefineBoomFemale;
import acg.project.action.command.creational.define.CommandCreationalDefineBoomMale;
import acg.project.action.command.creational.define.CommandCreationalDefineCarrier;
import acg.project.action.command.creational.define.CommandCreationalDefineCatapult;
import acg.project.action.command.creational.define.CommandCreationalDefineFighter;
import acg.project.action.command.creational.define.CommandCreationalDefineOLSReceiver;
import acg.project.action.command.creational.define.CommandCreationalDefineOLSTransmitter;
import acg.project.action.command.creational.define.CommandCreationalDefineTailhook;
import acg.project.action.command.creational.define.CommandCreationalDefineTanker;
import acg.project.action.command.creational.define.CommandCreationalDefineTrap;
import acg.project.action.command.creational.define.CommandCreationalListTemplates;
import acg.project.action.command.creational.define.CommandCreationalShowTemplate;
import acg.project.action.command.creational.define.CommandCreationalUndefine;
import acg.project.action.command.miscellaneous.A_CommandMisc;
import acg.project.action.command.miscellaneous.CommandMiscDoClockUpdate;
import acg.project.action.command.miscellaneous.CommandMiscDoExit;
import acg.project.action.command.miscellaneous.CommandMiscDoRun;
import acg.project.action.command.miscellaneous.CommandMiscDoSetClockRate;
import acg.project.action.command.miscellaneous.CommandMiscDoSetClockRunning;
import acg.project.action.command.miscellaneous.CommandMiscDoShowClock;
import acg.project.action.command.miscellaneous.CommandMiscDoWait;
import acg.project.action.command.structural.A_CommandStructural;
import acg.project.action.command.structural.CommandStructuralCommit;
import acg.project.action.command.structural.CommandStructuralPopulateCarrier;
import acg.project.action.command.structural.CommandStructuralPopulateWorld;
import acg.project.map.MapTemplate;

/**
 * 
 * @author Tyler Herrin, Ben Daschel, Paul Grinder
 *
 */

public class CommandParser implements I_TopLevelCommnands, I_Definables, I_DefinableArgs{
	
	/*
	 * This is the mScanner that will be hooked to any input source.
	 * Using a mScanner for any type of input source will greatly simplify 
	 * the task of parsing. 
	 */
	private Scanner mScanner;
	private ActionSet mActionSet;
	
	
	public CommandParser(ActionSet actionset, String text){
		if(actionset == null || text == null){
			throw new RuntimeException();
		}
		mActionSet = actionset;
		mScanner = new Scanner(text);

		
	}
	
	public ActionSet getActionSet(){
		return mActionSet;
	}

	
	/**
	 * Parse the command into a usable object.
	 * 
	 * @throws if command is not valid
	 */
	public void interpret() throws ParseException{
		if(mScanner == null){
			return;
		}
		
		String commandClass = mScanner.next().toUpperCase();
		
		switch(commandClass){
			
			/*
			 * Interpret the DEFINE command
			 */
			case DEFINE:
				mActionSet.getActionCreationalDefine().submit(parse_define());
				break;
			/*
			 * Interpret the CREATE command
			 */
			case CREATE:
				//command submitted inside method call
				parse_create();
				break;
			/*
			 * Interpret the UNDEFINE command
			 */
			case UNDEFINE:
				mActionSet.getActionCreationalDefine().submit(parse_undefine());
				break;
			/*
			 * Interpret the SHOW command
			 */
			case SHOW:
				mActionSet.getActionCreationalDefine().submit(parse_show());
				break;
			/*
			 * Interpret the LIST command
			 */
			case LIST:
				//command submitted inside method call
				parse_list();
				break;
			
			/*
			 * Interpret the DO command
			 */
			case DO:
				mActionSet.getActionBehavioral().submit(parse_do());
				break;
					
			/*
			 * Interpret the @DO command
			 */
			case ATDO:
				mActionSet.getActionBehavioral().submit(	parse_force_do());
				break;
			
			/*
			 * Interpret the COMMIT command	
			 */
			case COMMIT:
				mActionSet.getActionStructural().submit(parse_commit());
				break;
			
			/*
			 * Interpret the POPULATE command
			 */
			case POPULATE:
				//command submitted inside method call
				parse_populate();
				break;
			
			/*
			 * Interpret the GET command
			 */
			case GET:
				mActionSet.getActionBehavioral().submit(parse_get());
				break;
			/*
			 * Interpret the SET command	
			 */
			case SET:
				mActionSet.getActionBehavioral().submit(parse_set());
				break;
			
			/*
			 * Interpret the CLOCK command
			 */
			case CLOCK:
				mActionSet.getActionMisc().submit(parse_clock());
				break;
			/*
			 * Interpret the RUN command	
			 */
			case RUN:
				mActionSet.getActionMisc().submit(parse_run());
				break;
			/*
			 * Interpret the EXIT command	
			 */
			case EXIT:
				mActionSet.getActionMisc().submit(parse_exit());
				break;
			/*
			 * Interpret the WAIT command	
			 */
			case WAIT:
				mActionSet.getActionMisc().submit(parse_wait());
				break;
				
			case UNCREATE:
				mActionSet.getActionCreationalCreate().submit(parse_uncreate());
				break;
			
			case DESCRIBE:
				mActionSet.getActionCreationalCreate().submit(parse_describe());
				break;
				
		default:
			throw new ParseException("Unrecognized command: " + commandClass);
		}
		
	}

	private CommandCreationalDescribe parse_describe() throws ParseException {
		Identifier aid = parse_id();
		
		return new CommandCreationalDescribe(aid);
	}

	private A_CommandBehavioralDo parse_force_do() throws ParseException {
		Identifier aid = parse_id();
		String token = mScanner.next().toUpperCase();
		
		switch(token)
		{
			case "FORCE":
				token = token + " " + mScanner.next().toUpperCase();
				switch(token) // Doesn't currently handle forcing all.
				{
					case "FORCE COORDINATES":
						CoordinateWorld coords = parse_coordinates();
						if(!mScanner.hasNext())
							return force_coordinates(aid, coords);
						else
						{
							String next = mScanner.next().toUpperCase();
							if(next.compareTo("ALTITUDE") == 0)
							{
								return force_all(aid, coords, true);
							}
							else if(next.compareTo("HEADING") == 0)
							{
								return force_all(aid, coords, false);
							}
							else
								throw new ParseException("Syntax error.");
						}
				case "FORCE ALTITUDE":
						return force_altitude(aid);
					case "FORCE HEADING":
						return force_heading(aid);
					case "FORCE SPEED":
						return force_speed(aid);
				}
		}
		
		throw new ParseException("Unable to do: " + token);
		
	}


	private A_CommandMisc parse_clock() throws ParseException {		

		if(! mScanner.hasNext()){
			return  new CommandMiscDoShowClock();
		}
		
		String nextToken = mScanner.next();
		
		if(nextToken.equalsIgnoreCase("PAUSE")){
			
			return new CommandMiscDoSetClockRunning(false);
		}
		else if(nextToken.equalsIgnoreCase("RESUME")){
			
			return new CommandMiscDoSetClockRunning(true);
		}
		else if(nextToken.equalsIgnoreCase("UPDATE")){
			
			return new CommandMiscDoClockUpdate();
		}
		else{
			Rate rate = parse_rate(nextToken);
			return new CommandMiscDoSetClockRate(rate);
		}
		
	}


	private CommandStructuralCommit parse_commit() {
		return new CommandStructuralCommit();
		
	}


	private void parse_create() throws ParseException {
		
		ActionCreationalCreate creator = mActionSet.getActionCreationalCreate();
		
		/**
		 * Looks at the next token in the command
		 * to determine which sub-parser to send the command to.
		 * If the token is invalid, then will throw a parse exception.
		 */
		String token = mScanner.next().toUpperCase();
		switch (token){
		

		case AUX_TANK:
			creator.submit(create_aux_tank());
			break;
			
		case BARRIER:
			creator.submit(create_barrier());
			break;

		case BOOM:
			/*
			 * In this case, we let the sub-method handle the submission
			 * of the command into the queue.  There is no generic boom 
			 * class that can be returned.
			 */
			create_boom();
			break;
			
		case CARRIER:
			creator.submit(create_carrier());
			break;
			
		case CATAPULT:
			creator.submit(create_catapult());
			break;

		case FIGHTER:
			creator.submit(create_fighter());
			break;

		case TAILHOOK:
			creator.submit(create_tailhook());
			break;

		case TANKER:
			creator.submit(create_tanker());
			break;

		case TRAP:
			creator.submit(create_trap());
			break;

		case OLS_RCV:
			creator.submit(create_ols_rcv());
			break;

		case OLS_XMT:
			creator.submit(create_ols_xmt());
			break;

		default:
			throw new ParseException("Unable to create agent: " + token);
		}
		
	}


	private A_CommandCreationalDefine parse_define() throws ParseException {
		/*
		 * Switch on the next token.  
		 * A proper token represents the type of object
		 * that the user intends to define.
		 * If the token is now a type of definable object,
		 * throw a parse exception.
		 */
		String token = mScanner.next().toUpperCase();
		switch (token){
		
		case AUX_TANK:
			return define_aux_tank();
						
		case BARRIER:
			return define_barrier();

			
			/*
			 * boom male or female??
			 */
		case BOOM:
			return define_boom();

			
		case CARRIER:
			return define_carrier();

			
		case CATAPULT:
			return define_catapult();

			
		case FIGHTER:
			return define_fighter();

		
		case OLS_RCV:
			return define_ols_rcv();

			
		case OLS_XMT:
			return define_ols_xmt();

			
		case TAILHOOK:
			return define_tailhook();

			
		case TANKER:
			return define_tanker();

			
		case TRAP:
			return define_trap();
		
				
		}
		/*
		 * If we get here, then someone tried to define some unrecognized 
		 * type of template, so we give them an exception. 
		 */
		throw new ParseException("Unable to define type: " + token );
	}


	private A_CommandBehavioralDo parse_do() throws ParseException {
		
		Identifier aid = parse_id();
		String token = mScanner.next().toUpperCase();
		
		switch(token)
		{
			case "ASK":
					return do_ask( aid);
			case "POSITION":
				return do_position( aid);
			case "BARRIER":
				return do_barrier( aid);
			case "CATAPULT":
				// Pull off "launch with speed" and make sure command is CATAPULT LAUNCH WITH SPEED
				token = token + " " + mScanner.next().toUpperCase() +
					" " + mScanner.next().toUpperCase() + " " +
					mScanner.next().toUpperCase();
				if(token.compareTo("CATAPULT LAUNCH WITH SPEED") == 0)
					return do_catapult_launch( aid);
			case "SET":
				token = token + " " + mScanner.next().toUpperCase();
				switch(token)
				{
					case "SET SPEED":
						return do_set_speed( aid);
					case "SET ALTITUDE":
						return do_set_altitude( aid);
					case "SET HEADING":
						return do_set_heading( aid);
				}
			case "TAILHOOK":
				return do_tailhook( aid);
			case "CAPTURE":
				return do_capture_ols( aid);
			case "BOOM":
				return do_boom( aid);
			case "TRANSFER":
				return do_transfer( aid);
		}
		
		throw new ParseException("Unable to do: " + token);
	}


	private CommandMiscDoExit parse_exit() {
		return new CommandMiscDoExit();
	}


	private CommandBehavioralGetWindConditions parse_get() throws ParseException {
		
		if(mScanner.next().equalsIgnoreCase("WIND"))
		{
			return new CommandBehavioralGetWindConditions();
		}
		throw new ParseException();
		
	}


	private void parse_list() throws ParseException { 
		String token = mScanner.next();
		
		if(token.toUpperCase().compareTo("AGENTS") == 0)
			mActionSet.getActionCreationalCreate().submit( new CommandCreationalListAgents());
		
		else if(token.toUpperCase().compareTo("TEMPLATES") == 0)
			mActionSet.getActionCreationalDefine().submit(new CommandCreationalListTemplates());
		
		else{
			throw new ParseException("Invalid Syntax");
		}
		
	}


	private void parse_populate() throws ParseException {
		String token = mScanner.next();
		
		if(token.equalsIgnoreCase(CARRIER))
			mActionSet.getActionStructural().submit( parse_populate_carrier());
		else if(token.equalsIgnoreCase("WORLD"))
			mActionSet.getActionStructural().submit( parse_populate_world());
		else
			throw new ParseException();

		
	}


	private CommandMiscDoRun parse_run() {
		String filename = mScanner.next();
		return new CommandMiscDoRun(filename);
		
	}


	private A_CommandBehavioral parse_set() throws ParseException {
		if(mScanner.next().equalsIgnoreCase("WIND")){
			
			String nextToken = mScanner.next();
			
			if(nextToken.equalsIgnoreCase("DIRECTION")){
				
				AngleNavigational course = parse_azimuth();
				return new CommandBehavioralSetWindDirection(course);
			}
			
			if(nextToken.equalsIgnoreCase("SPEED")){
				Speed speed = parse_speed();
				return new CommandBehavioralSetWindSpeed(speed);
			}
			
		}
		throw new ParseException();
		
	}

	private CommandCreationalShowTemplate parse_show() throws ParseException {
		Identifier id = null;
		if(mScanner.next().equalsIgnoreCase("TEMPLATE")){
			id = new Identifier(mScanner.next());
		}
		else
			throw new ParseException();
		
		return new CommandCreationalShowTemplate(id);
		
		
	}

	private CommandCreationalUndefine parse_undefine() {
		Identifier id = new Identifier(mScanner.next());
		
		return new CommandCreationalUndefine(id);
		
	}

	private CommandMiscDoWait parse_wait() throws ParseException {
		Rate rate = parse_rate();
		return new CommandMiscDoWait(rate);
		
	}
	
	private CommandCreationalUncreate parse_uncreate(){
		String token = mScanner.next();
		
		return new CommandCreationalUncreate(new Identifier(token));
		
	}



	private  CommandCreationalCreateAuxiliaryTank create_aux_tank() throws ParseException {

		Identifier aid = parse_id();
		
		if(mScanner.next().compareToIgnoreCase("FROM") != 0)
			throw new ParseException("Invalid Syntax");
		
		Identifier tid = parse_id();
		

		return new CommandCreationalCreateAuxiliaryTank(aid, tid);
	}

	private  CommandCreationalCreateBarrier create_barrier() throws ParseException {

		
		Identifier aid = parse_id();
		
		if(mScanner.next().compareToIgnoreCase("FROM") != 0)
			throw new ParseException("Invalid Syntax");
		
		Identifier tid = parse_id();
		
		return new CommandCreationalCreateBarrier(aid, tid);
	}

	private  void create_boom() throws ParseException {
		
		Identifier aid = parse_id();
		
		if(mScanner.next().compareToIgnoreCase("FROM") != 0)
			throw new ParseException("Invalid Syntax");
		
		Identifier tid = parse_id();
		
		MapTemplate tMap = getActionSet().getMapTemplates();
		
		A_CommandCreationalDefine commandDefine  = tMap.getCommand(tid);
		
		if(commandDefine instanceof A_CommandCreationalDefineBoom){
			
			A_CommandCreationalDefineBoom boomDefine = (A_CommandCreationalDefineBoom) commandDefine;
			
			if(boomDefine.isMale())
				mActionSet.getActionCreationalCreate().submit( new CommandCreationalCreateBoomMale(aid, tid));
			
			else
				mActionSet.getActionCreationalCreate().submit( new CommandCreationalCreateBoomFemale(aid, tid));
			
			
		}
		
		//identify the kind of boom requested
		//return male or female depending on result of above
		
	}

	private  CommandCreationalCreateCarrier create_carrier() throws ParseException{

		Identifier carrier_aid = parse_id();
		
		if(mScanner.next().compareToIgnoreCase("FROM") != 0)
			throw new ParseException("Invalid Syntax");
		
		Identifier tid = parse_id();
		
		if(mScanner.next().compareToIgnoreCase("WITH") != 0)
			throw new ParseException("Invalid Syntax");
		
		if(mScanner.next().compareToIgnoreCase("CATAPULT") != 0)
			throw new ParseException("Invalid Syntax");
		
		Identifier catapult_aid = parse_id();
		
		if(mScanner.next().compareToIgnoreCase("BARRIER") != 0)
			throw new ParseException("Invalid Syntax");
		
		Identifier barrier_aid = parse_id();
		
		if(mScanner.next().compareToIgnoreCase("TRAP") != 0)
			throw new ParseException("Invalid Syntax");
		
		Identifier trap_aid = parse_id();
		
		if(mScanner.next().compareToIgnoreCase("OLS") != 0)
			throw new ParseException("Invalid Syntax");
		
		Identifier ols_aid = parse_id();
		
		if( mScanner.next().compareToIgnoreCase("AT") != 0)
			throw new ParseException("Invalid Syntax");
		
		if(mScanner.next().compareToIgnoreCase("COORDINATES") != 0)
			throw new ParseException("Invalid Syntax");
		
		CoordinateWorld coordinate = parse_coordinates();
		
		if(mScanner.next().compareToIgnoreCase("HEADING") != 0)
			throw new ParseException("Invalid Syntax");
		
		AngleNavigational heading = parse_course();
		
		if(mScanner.next().compareToIgnoreCase("SPEED") != 0)
			throw new ParseException("Invalid Syntax");
		
		Speed speed = parse_speed();
		
		return new CommandCreationalCreateCarrier(carrier_aid, tid, catapult_aid, barrier_aid, trap_aid, ols_aid, coordinate, heading, speed);
		
		
	}

	private  CommandCreationalCreateCatapult create_catapult() throws ParseException {
		
		Identifier aid = parse_id();
		
		if(mScanner.next().compareToIgnoreCase("FROM") != 0)
			throw new ParseException("Invalid Syntax");
		
		Identifier tid = parse_id();
		
		return new CommandCreationalCreateCatapult(aid, tid);
	}

	private  CommandCreationalCreateFighter create_fighter() throws ParseException {
		
		Identifier aid = null;
		Identifier tid = null;
		Identifier olsId = null;
		Identifier boomId = null;
		Identifier tailhookId = null;
		List<Identifier> tankIds = new ArrayList<Identifier>();;
		List<ParameterAssignment> params = new ArrayList<ParameterAssignment>();
		
		CoordinateWorld coords = null;
		Altitude altitude = null;
		AngleNavigational heading = null;
		Speed speed = null;
		
		/*
		 *CREATE FIGHTER <aid1> FROM <tid> WITH OLS <aid2> BOOM <aid3> TAILHOOK <aid4> [TANKS <aidn>+] 
		 *[OVERRIDING (<aidm>.<argname> WITH <string>)+] [AT COORDINATES <coordinates> ALTITUDE <altitude> 
		 *HEADING <course> SPEED <speed>]
		 * 
		 * 
		 */
		
		aid = new Identifier(mScanner.next());
		
		if(mScanner.next().equalsIgnoreCase("from")){
			tid = new Identifier(mScanner.next());
		}
		if(mScanner.next().equalsIgnoreCase("with") && mScanner.next().equalsIgnoreCase("ols")){
			olsId = new Identifier(mScanner.next());
		}
		if(mScanner.next().equalsIgnoreCase(BOOM)){
			boomId = new Identifier(mScanner.next());
		}
		if(mScanner.next().equalsIgnoreCase(TAILHOOK)){
			tailhookId = new Identifier(mScanner.next());
		}
		
		String restOfCommand = mScanner.nextLine();
		
		String[] tokens = restOfCommand.split(" ");
		ArrayList<String> tokenList = new ArrayList<String>();
		for(String s: tokens){
			tokenList.add(s);
		}
		int index = 0;
		//parse optional tanks
		if(StringUtils.containsIgnoreCase(tokenList, "tanks")){
			index = StringUtils.indexOfIgnoreCase(tokenList, "tanks") +1;
			
			while(index < tokenList.size()
					&& ! tokenList.get(index).equalsIgnoreCase("overriding")
					&& ! tokenList.get(index).equalsIgnoreCase("at")){
				
				Identifier tankId = new Identifier(tokenList.get(index++));
				
				tankIds.add(tankId);
			}
			
			
		}
		
		//parse optional override commands
		if(StringUtils.containsIgnoreCase(tokenList, "overriding")){
			index = StringUtils.indexOfIgnoreCase(tokenList, "overriding") + 1;
			
			
			
			while(index < tokenList.size()
					&& ! tokenList.get(index).equalsIgnoreCase("at")){
				
				
				String agentProp = tokenList.get(index++);
				String with = tokenList.get(index++);
				String overrideWith = tokenList.get(index++);
				
				String agentId = agentProp.split("[.]")[0];
				String property = agentProp.split("[.]")[1];
				
				ParameterAssignment param = new ParameterAssignment(
						new Identifier(agentId).append(property), overrideWith);
				
				params.add(param);
						
				
			}
			
			
		}
		
		
		//parse optional position commands
		if(StringUtils.containsIgnoreCase(tokenList, "at")){
			index = StringUtils.indexOfIgnoreCase(tokenList, "at") + 1;
			

			
			if(tokenList.get(index++).equalsIgnoreCase("coordinates")){
				coords = parse_coordinates(tokenList.get(index++));
			}
			
			if(tokenList.get(index++).equalsIgnoreCase("altitude")){
				altitude = parse_altitude(tokenList.get(index++));
			}
			
			if(tokenList.get(index++).equalsIgnoreCase("heading")){
				heading = parse_azimuth(tokenList.get(index++));
				
			}
			
			if(tokenList.get(index++).equalsIgnoreCase("speed")){
				speed = parse_speed(tokenList.get(index++));
				
			}
			
			return new CommandCreationalCreateFighter(aid, tid, olsId, boomId, 
					tailhookId, tankIds, params, coords, altitude, heading, speed);
		}
		
		return new CommandCreationalCreateFighter(aid, tid, olsId, boomId, tailhookId, tankIds, params);
	}

	

	private  CommandCreationalCreateTailhook create_tailhook() throws ParseException {

		
		Identifier aid = parse_id();
		
		if(mScanner.next().compareToIgnoreCase("FROM") != 0)
			throw new ParseException("Invalid Syntax");
		
		Identifier tid = parse_id();
		
		return new CommandCreationalCreateTailhook(aid, tid);
	}

	private  CommandCreationalCreateTanker create_tanker() throws ParseException 
	{
		Identifier aid1 = parse_id();
		
		if(mScanner.next().compareToIgnoreCase("FROM") != 0)
			throw new ParseException("Invalid Syntax");
		
		Identifier tid = parse_id();
		
		if((mScanner.next() + " " + mScanner.next()).compareToIgnoreCase("WITH BOOM") != 0)
			throw new ParseException("Invalid Syntax");
		
		Identifier aid2 = parse_id();
		
		
		if((mScanner.next() + " " + mScanner.next()).compareToIgnoreCase("AT COORDINATES") != 0)
			throw new ParseException("Invalid Syntax");
		
		CoordinateWorld coords = parse_coordinates();
		
		if(mScanner.next().compareToIgnoreCase("ALTITUDE") != 0)
			throw new ParseException("Invalid Syntax");
		
		Altitude alt = parse_altitude();
		
		if(mScanner.next().compareToIgnoreCase("HEADING") != 0)
			throw new ParseException("Invalid Syntax");
		
		AngleNavigational heading = parse_course();
		
		if(mScanner.next().compareToIgnoreCase("SPEED") != 0)
			throw new ParseException("Invalid Syntax");
		
		Speed speed = parse_speed();
		
		return new CommandCreationalCreateTanker(aid1, tid, aid2, coords, alt, heading, speed);
	}

	private  CommandCreationalCreateTrap create_trap() throws ParseException {

		
		Identifier aid = parse_id();
		
		if(mScanner.next().compareToIgnoreCase("FROM") != 0)
			throw new ParseException("Invalid Syntax");
		
		Identifier tid = parse_id();
		
		return new CommandCreationalCreateTrap(aid, tid);
	}

	private  CommandCreationalCreateOLSReceiver create_ols_rcv() throws ParseException {

		
		Identifier aid = parse_id();
		
		if(mScanner.next().compareToIgnoreCase("FROM") != 0)
			throw new ParseException("Invalid Syntax");
		
		Identifier tid = parse_id();
		
		return new CommandCreationalCreateOLSReceiver(aid, tid);
	}

	private  CommandCreationalCreateOLSTransmitter create_ols_xmt() throws ParseException {

		
		Identifier aid = parse_id();
		
		if(mScanner.next().compareToIgnoreCase("FROM") != 0)
			throw new ParseException("Invalid Syntax");
		
		Identifier tid = parse_id();

		return new CommandCreationalCreateOLSTransmitter(aid, tid);
	}


	private  CommandCreationalDefineTrap define_trap() throws ParseException {
		  Identifier id = null;
		  CoordinateCartesianRelative origin = null;
      AngleNavigational azimuth = null;
      Distance width = null;
      Weight weight = null;
      Speed speed = null;
      Percent missProbability = null;
      
      id = new Identifier(mScanner.next());
      
     /*
      * DEFINE TRAP <tid> ORIGIN <origin> AZIMUTH <azimuth> WIDTH <distance> 
      * LIMIT WEIGHT <weight> SPEED <speed> MISS <percent>
      * 
      */
      if(mScanner.next().equalsIgnoreCase(ORIGIN))
      	origin = parse_origin();
      
      if(mScanner.next().equalsIgnoreCase(AZIMUTH))
      	azimuth = parse_azimuth();
      
      if(mScanner.next().equalsIgnoreCase(WIDTH))
      	width = parse_distance();
      
      if(mScanner.next().equalsIgnoreCase(LIMIT) && mScanner.next().equalsIgnoreCase(WEIGHT))
      	weight = parse_weight();
      	
      if(mScanner.next().equalsIgnoreCase(SPEED))
      	speed = parse_speed();
      
      if(mScanner.next().equalsIgnoreCase(MISS))
      	missProbability = parse_percent();
      else{
      	throw new ParseException();
      }
      	

      return new CommandCreationalDefineTrap(id, origin, azimuth, width, weight, speed, missProbability);
      
	}


	


	private  CommandCreationalDefineTanker define_tanker() throws ParseException {
	    Identifier id = null;
      Weight weight = null;
      Speed min = null;
      Speed max = null;
      Speed increase = null;
      Speed decrease = null;
      AngleNavigational turn = null;
      Altitude climb = null;
      Altitude descend = null;
      
      /*
       * DEFINE TANKER <tid> SPEED MIN <speed1> MAX <speed2> DELTA INCREASE <speed3> 
       * DECREASE <speed4> TURN <azimuth> CLIMB <altitude1> DESCENT <altitude2> TANK <weight>
       * 
       */
	
		id = new Identifier(mScanner.next());
		
	
    	if(mScanner.next().equalsIgnoreCase(SPEED)&& mScanner.next().equalsIgnoreCase(MIN))
    		  min = parse_speed();

    	if(mScanner.next().equalsIgnoreCase(MAX))
    		  max = parse_speed();
    	
    	if(mScanner.next().equalsIgnoreCase(DELTA) && mScanner.next().equalsIgnoreCase(INCREASE))
    		  increase = parse_speed();
    	
    	if(mScanner.next().equalsIgnoreCase(DECREASE))
    		  decrease = parse_speed();
    	
    	if(mScanner.next().equalsIgnoreCase(TURN))
    		  turn = parse_azimuth();
    	
    	if(mScanner.next().equalsIgnoreCase(CLIMB))
    		  climb = parse_altitude();
    	
    	if(mScanner.next().equalsIgnoreCase(DESCENT))
    		  descend = parse_altitude();
    	
    	if(mScanner.next().equalsIgnoreCase(TANK))
    		  weight = parse_weight();
    	else
    		throw new ParseException();
	      		   
		return new CommandCreationalDefineTanker(id, min, max, increase, decrease, turn, climb, descend, weight);
	}


	private   CommandCreationalDefineTailhook define_tailhook() throws ParseException {
		Identifier id = null;
		Time time = null;
		
		/*
		 * DEFINE TAILHOOK <tid> TIME <time>
		 */
		
		id = new Identifier(mScanner.next());
		
		if(mScanner.next().equalsIgnoreCase(TIME))
			time = parse_time();
		else
			throw new ParseException();
		
		return new CommandCreationalDefineTailhook(id, time);
	}


	private  CommandCreationalDefineOLSTransmitter define_ols_xmt() throws ParseException {
		Identifier id= null;
		CoordinateCartesianRelative origin = null;
		AngleNavigational azimuth = null;
		AttitudePitch elevation = null;
		Distance range = null;
		Distance diameter = null;
		
		/*
		 * DEFINE OLS_XMT <tid> ORIGIN <origin> AZIMUTH <azimuth> ELEVATION <elevation> 
		 * RANGE <distance1> DIAMETER <distance2>
		 * 
		 */
		
		id = new Identifier(mScanner.next());
		
	
		if(mScanner.next().equalsIgnoreCase(ORIGIN))
			origin = parse_origin();
		if(mScanner.next().equalsIgnoreCase(AZIMUTH))
			azimuth = parse_azimuth();
		if(mScanner.next().equalsIgnoreCase(ELEVATION))
			elevation = parse_elevation();
		if(mScanner.next().equalsIgnoreCase(RANGE))
			range= parse_distance();
		if(mScanner.next().equalsIgnoreCase(DIAMETER))
			diameter = parse_distance();
		else
			throw new ParseException();

		
		return new CommandCreationalDefineOLSTransmitter(id, origin, azimuth, elevation, range, diameter);
	}


	private  CommandCreationalDefineOLSReceiver define_ols_rcv() throws ParseException {

		Identifier id = null;
		Distance diameter = null;
		
		/*
		 * DEFINE OLS_RCV <tid> DIAMETER <distance>
		 * 
		 */
		
		id = new Identifier(mScanner.next());
		
		if(mScanner.next().equalsIgnoreCase(DIAMETER)){
			diameter = parse_distance();
		}
		else{
			throw new ParseException();
		}
		
		return new CommandCreationalDefineOLSReceiver(id, diameter);
	}


	private  CommandCreationalDefineFighter define_fighter() throws ParseException {
		Identifier id = null;
		Speed speedMin = null;
		Speed speedMax = null;
		Speed deltaIncrease = null;
		Speed deltaDecrease = null;
		AngleNavigational turn = null;
		Altitude climb = null;
		Altitude descend = null;
		Weight empty = null;
		Weight fuelWeight  = null;
		Weight fuelDelta = null;
		
		
		/*
		 * 
		 * DEFINE FIGHTER <tid> SPEED MIN speedmin<speed1> MAX speedmax<speed2> 
		 * DELTA INCREASE dspeedinc<speed3> DECREASE dspeeddec<speed4> TURN dturn<azimuth> 
		 * CLIMB dclimb<altitude1> DESCENT ddescent<altitude2> EMPTY WEIGHT weight<weight1> 
		 * FUEL INITIAL fuelinit<weight2> DELTA dfuel<weight3>
		 * 
		 * 
		 */
		
		id = new Identifier(mScanner.next());
		
		if(mScanner.next().equalsIgnoreCase(SPEED) && mScanner.next().equalsIgnoreCase(MIN))
			speedMin = parse_speed();
		if(mScanner.next().equalsIgnoreCase(MAX))
			speedMax = parse_speed();
		
		if(mScanner.next().equalsIgnoreCase(DELTA) && mScanner.next().equalsIgnoreCase(INCREASE))
			deltaIncrease = parse_speed();
		
		if(mScanner.next().equalsIgnoreCase(DECREASE))
			deltaDecrease = parse_speed();
		
		if(mScanner.next().equalsIgnoreCase(TURN))
			turn = parse_azimuth();
		
		if(mScanner.next().equalsIgnoreCase(CLIMB))
			climb = parse_altitude();
		
		if(mScanner.next().equalsIgnoreCase(DESCENT))
			descend = parse_altitude();
		
		if(mScanner.next().equalsIgnoreCase(EMPTY) && mScanner.next().equalsIgnoreCase(WEIGHT))
			empty = parse_weight();
		
		if(mScanner.next().equalsIgnoreCase(FUEL) && mScanner.next().equalsIgnoreCase(INITIAL))
			fuelWeight = parse_weight();
		
		if(mScanner.next().equalsIgnoreCase(DELTA))
			fuelDelta = parse_weight();
			
		else
			throw new ParseException();
			
		
		return new CommandCreationalDefineFighter(id, speedMin, speedMax, deltaIncrease, deltaDecrease, turn, 
				climb, descend, empty, fuelWeight, fuelDelta);
	}


	private  CommandCreationalDefineCatapult define_catapult() throws ParseException {
		/*
		 * DEFINE CATAPULT <tid> ORIGIN <origin> AZIMUTH <azimuth> LENGTH <distance> 
		 * ACCELERATION <acceleration> LIMIT WEIGHT <weight> SPEED <speed> RESET <time>
		 * 
		 */
		Identifier id = new Identifier(mScanner.next());
		CoordinateCartesianRelative origin = null;
		AngleNavigational azimuth = null;
		Distance length = null;
		Acceleration accel = null;
		Weight weight = null;
		Speed speed = null;
		Time reset = null;
		
		if(mScanner.next().equalsIgnoreCase(ORIGIN))
			origin = parse_origin();
		if(mScanner.next().equalsIgnoreCase(AZIMUTH))
			azimuth = parse_azimuth();
		if(mScanner.next().equalsIgnoreCase(LENGTH))
			length  =  parse_distance();
		if(mScanner.next().equalsIgnoreCase(ACCELERATION))
			accel = parse_acceleration();
		if(mScanner.next().equalsIgnoreCase(LIMIT)  && mScanner.next().equalsIgnoreCase(WEIGHT))
			weight = parse_weight();
		if(mScanner.next().equalsIgnoreCase(SPEED))
			speed = parse_speed();
		if(mScanner.next().equalsIgnoreCase(RESET))
			reset = parse_time();
		else
			throw new ParseException();
		
		return new CommandCreationalDefineCatapult(id, origin, azimuth, length, accel, weight, speed, reset);
	}


	private  CommandCreationalDefineCarrier define_carrier() throws ParseException {
		String layoutFilename= null;
		Identifier id = null;
		Speed speedMax = null;
		Speed speedDeltaIncrease = null;
		Speed speedDeltaDecrease = null;
		AngleNavigational turn = null;
		
		/*
		 * DEFINE CARRIER <tid> SPEED MAX <speed1> 
		 * DELTA INCREASE <speed2> DECREASE <speed3> TURN <azimuth> LAYOUT <string>
		 */
		
		id = new Identifier(mScanner.next());
		
		if(mScanner.next().equalsIgnoreCase(SPEED) && mScanner.next().equalsIgnoreCase(MAX))
			speedMax = parse_speed();
		
		if(mScanner.next().equalsIgnoreCase(DELTA) && mScanner.next().equalsIgnoreCase(INCREASE))
			speedDeltaIncrease = parse_speed();
		
		if(mScanner.next().equalsIgnoreCase(DECREASE))
			speedDeltaDecrease = parse_speed();
		
		if(mScanner.next().equalsIgnoreCase(TURN))
			turn = parse_azimuth();
		
		if(mScanner.next().equalsIgnoreCase(LAYOUT))
			layoutFilename = mScanner.next();
		else
			throw new ParseException();
		
		return new CommandCreationalDefineCarrier(id, speedMax, speedDeltaIncrease, speedDeltaDecrease, turn, layoutFilename);
	}


	private  A_CommandCreationalDefine define_boom() throws ParseException{
		A_CommandCreationalDefine boom = null;
		
		
		String type = mScanner.next().toUpperCase();
		
		switch(type){
		
		case MALE:
			boom = define_boom_male();
			break;
		case FEMALE:
			boom = define_boom_female();
			break;
			default:
				throw new ParseException("Unable to parse token: " + type);
		
		}
		
		return boom;
	}
	
	private  CommandCreationalDefineBoomMale define_boom_male() throws ParseException{
		Identifier id = null;
		Distance length  = null;
		Distance diameter = null;
		Flow flow = null;
		
		/*
		 * DEFINE BOOM MALE <tid> LENGTH <distance1> DIAMETER <distance2> FLOW <weight>
		 */
		id = new Identifier(mScanner.next());
		
		if(mScanner.next().equalsIgnoreCase(LENGTH))
			length = parse_distance();
		
		if(mScanner.next().equalsIgnoreCase(DIAMETER))
			diameter = parse_distance();
		

		if(mScanner.next().equalsIgnoreCase(FLOW))
			flow = parse_flow();
		else
				throw new ParseException();
			
		return new CommandCreationalDefineBoomMale(id, length, diameter, flow);
	}
	
	private  CommandCreationalDefineBoomFemale define_boom_female() throws ParseException{
		Identifier id = null;
		Distance length  = null;
		Distance diameter = null;
		Flow flow = null;
		AttitudePitch elevation = null;
		
		/*
		 * DEFINE BOOM FEMALE <tid> LENGTH <distance1> DIAMETER <distance2> ELEVATION <elevation> FLOW <weight>
		 */
		
		id = new Identifier(mScanner.next());
		
		if(mScanner.next().equalsIgnoreCase(LENGTH))
			length = parse_distance();
		if(mScanner.next().equalsIgnoreCase(DIAMETER))
			diameter = parse_distance();
		if(mScanner.next().equalsIgnoreCase(ELEVATION))
			flow = parse_flow();
		if(mScanner.next().equalsIgnoreCase(FLOW))
			elevation = parse_elevation();
		else
			throw new ParseException();

		return new CommandCreationalDefineBoomFemale(id, length, diameter, elevation, flow);
	}


	private  CommandCreationalDefineBarrier define_barrier() throws ParseException {
		Identifier id = null;
		CoordinateCartesianRelative origin = null;
		AngleNavigational azimuth = null;
		Distance width = null;
		Time time = null;
		
		/*
		 * DEFINE BARRIER <tid> ORIGIN <origin> AZIMUTH <azimuth> WIDTH <distance> TIME <time>
		 * 
		 */
		
		id = new Identifier(mScanner.next());
		
		if(mScanner.next().equalsIgnoreCase(ORIGIN))
			origin = parse_origin();
		
		if(mScanner.next().equalsIgnoreCase(AZIMUTH))
			azimuth = parse_azimuth();
		
		if(mScanner.next().equalsIgnoreCase(WIDTH))
			width = parse_distance();
		
		if(mScanner.next().equalsIgnoreCase(TIME))
			time = parse_time();
		
		return new CommandCreationalDefineBarrier(id, origin, azimuth, width, time);
	}


	private  CommandCreationalDefineAuxiliaryTank define_aux_tank() throws ParseException {
		Identifier id = null;
		Weight amount = null;
		/*
		 * DEFINE AUX_TANK <tid> AMOUNT <weight>
		 */
		id = new Identifier(mScanner.next());
		
		
		if(mScanner.next().equalsIgnoreCase(AMOUNT))
			amount = parse_weight();
		else
			throw new ParseException();
		
		
		return new CommandCreationalDefineAuxiliaryTank(id, amount);
	}
	
	private  CommandStructuralPopulateCarrier parse_populate_carrier(){
		Identifier idCarrier = null;
		List<Identifier> idFighters = null;
		
		/*
		 * POPULATE CARRIER <aid1> WITH FIGHTER[S] <aidn>+
		 */
		idCarrier = new Identifier(mScanner.next());
		
		if(mScanner.next().equalsIgnoreCase("WITH")){
			String fighters = mScanner.next();
			if(fighters.equalsIgnoreCase("FIGHTER") || fighters.equalsIgnoreCase("FIGHTERs")){
				idFighters = new ArrayList<Identifier>();
				while(mScanner.hasNext()){
					Identifier fighterId = new Identifier(mScanner.next());
					idFighters.add(fighterId);
					
					
				}
				
				
			}
			
			
		}
		
		
		return new CommandStructuralPopulateCarrier(idCarrier, idFighters);
	}
	
	
	private  CommandStructuralPopulateWorld parse_populate_world(){
		List<Identifier> idList = new ArrayList<Identifier>();
		
		/*
		 * 
		 * POPULATE WORLD WITH <aidn>+
		 */
		
		if(mScanner.next().equalsIgnoreCase("WITH")){
			
			while(mScanner.hasNext()){
				
				Identifier id = new Identifier(mScanner.next());
				idList.add(id);
			}
			
		}
		return new CommandStructuralPopulateWorld(idList);
		
	}
	
	private CommandBehavioralDoForceSpeed force_speed(Identifier aid) throws ParseException 
	{
		Speed speed = parse_speed();
		
		return new CommandBehavioralDoForceSpeed(aid, speed);
	}

	private CommandBehavioralDoForceHeading force_heading(Identifier aid ) throws ParseException 
	{
		AngleNavigational course = parse_course();
		
		return new CommandBehavioralDoForceHeading(aid, course);
	}

	private CommandBehavioralDoForceAltitude force_altitude(Identifier aid) throws ParseException 
	{
		Altitude alt = parse_altitude();
		
		return new CommandBehavioralDoForceAltitude(aid, alt);
	}

	private CommandBehavioralDoForceCoordinates force_coordinates(Identifier aid, CoordinateWorld coords ) throws ParseException 
	{
		return new CommandBehavioralDoForceCoordinates(aid, coords);
	}
	
	private CommandBehavioralDoForceAll force_all(Identifier aid, CoordinateWorld coords, boolean hasAlt) throws ParseException 
	{
		if(hasAlt)
		{
			Altitude alt = parse_altitude();
			
			if(mScanner.next().toUpperCase().compareTo("HEADING") != 0)
				throw new ParseException("Invalid syntax.");
			
			AngleNavigational course = parse_course();
			
			if(mScanner.next().toUpperCase().compareTo("SPEED") != 0)
				throw new ParseException("Invalid syntax.");
			Speed speed = parse_speed();
			
			return new CommandBehavioralDoForceAll(aid, coords, alt, course, speed);
		}
		else
		{
			AngleNavigational course = parse_course();
			
			if(mScanner.next().toUpperCase().compareTo("SPEED") != 0)
				throw new ParseException("Invalid syntax.");
			Speed speed = parse_speed();
			
			return new CommandBehavioralDoForceAll(aid, coords, course, speed);
		}
	}
	
	private CommandBehavioralDoTransfer do_transfer( Identifier aid) throws ParseException
	{
		String s = mScanner.next();
		
		if(s.toUpperCase().compareTo("START") == 0)
			return new CommandBehavioralDoTransfer(aid, true);
		if(s.toUpperCase().compareTo("STOP") == 0)
			return new CommandBehavioralDoTransfer(aid, false);
		
		throw new ParseException("Invalid Syntax");
		
	}

	private CommandBehavioralDoBoom do_boom( Identifier aid) throws ParseException
	{
		String s = mScanner.next();
		if(s.toUpperCase().compareTo("EXTEND") == 0)
			return new CommandBehavioralDoBoom(aid, true);
		if(s.toUpperCase().compareTo("RETRACT") == 0)
			return new CommandBehavioralDoBoom(aid, false);
		 throw new ParseException("Invalid Syntax");

	}

	private CommandBehavioralDoCaptureOLS do_capture_ols(Identifier aid) 
	{
		return new CommandBehavioralDoCaptureOLS(aid);
	}

	private CommandBehavioralDoTailhook do_tailhook( Identifier aid) throws ParseException 
	{
		String s = mScanner.next();
		if(s.toUpperCase().compareTo("UP") == 0)
			return new CommandBehavioralDoTailhook(aid, true);
		if(s.toUpperCase().compareTo("DOWN") == 0)
			return new CommandBehavioralDoTailhook(aid, false);
		
		throw new ParseException("Invalid Syntax");
	}

	private CommandBehavioralDoSetHeading do_set_heading( Identifier aid) throws ParseException 
	{
		AngleNavigational course = parse_course();
		
		if(mScanner.hasNext())
		{
			E_Direction direction;
			
			try { direction = E_Direction.valueOf(mScanner.next().toUpperCase()); }
			
			catch(Exception e) { throw new ParseException("Invalid Syntax"); }
			
			return new CommandBehavioralDoSetHeading(aid, course, direction);
		}
		else
			return new CommandBehavioralDoSetHeading(aid, course, E_Direction.SHORTEST);
		
	}

	private CommandBehavioralDoSetAltitude do_set_altitude( Identifier aid) throws ParseException 
	{
		Altitude alt = parse_altitude();
		
		return new CommandBehavioralDoSetAltitude(aid, alt);
	}

	private CommandBehavioralDoSetSpeed do_set_speed(Identifier aid) throws ParseException 
	{
		Speed speed = parse_speed();
		
		return new CommandBehavioralDoSetSpeed(aid, speed);
	}

	private CommandBehavioralDoCatapult do_catapult_launch( Identifier aid) throws ParseException 
	{
		Speed speed = parse_speed();
		
		return new CommandBehavioralDoCatapult(aid, speed);
	}

	private CommandBehavioralDoBarrier do_barrier( Identifier aid) throws ParseException 
	{
		boolean isUp;
		
		try{ isUp= Boolean.parseBoolean(mScanner.next()); }
		
		catch(Exception e) { throw new ParseException("Invalid Syntax"); }
		
		return new CommandBehavioralDoBarrier(aid, isUp);
	}

	private CommandBehavioralDoPosition do_position(Identifier aid) 
	{
		return new CommandBehavioralDoPosition(aid);
	}

	private CommandBehavioralDoAsk do_ask(Identifier aid) throws ParseException 
	{
		E_Parameter param; 
		
		try { param = E_Parameter.valueOf(mScanner.next().toUpperCase()); }
		
		catch(Exception e) { throw new ParseException("Invalid Syntax"); }
		
		return new CommandBehavioralDoAsk(aid, param);
	}
	
	private Acceleration parse_acceleration() throws ParseException
	{
		double accelerationFPS;
		
		try { accelerationFPS = mScanner.nextDouble(); }
		
		catch(NumberFormatException e) { throw new ParseException("Invalid acceleration value."); }
		
		return new Acceleration(accelerationFPS);
	}
	
	private Altitude parse_altitude() throws ParseException
	{
		double altitiudeFeet;
		
		try { altitiudeFeet = mScanner.nextDouble(); }
		
		catch(NumberFormatException e) { throw new ParseException("Invalid altitude value."); }
		
		return new Altitude(altitiudeFeet);
	}
	
	private Altitude parse_altitude(String string)  throws ParseException{
		double altitiudeFeet;
		
		try { altitiudeFeet = Double.parseDouble(string); }
		
		catch(NumberFormatException e) { throw new ParseException("Invalid altitude value."); }
		
		return new Altitude(altitiudeFeet);
	}
	
	private CoordinateWorld parse_coordinates() throws ParseException
	{	
		String[] coords = mScanner.next().split("/");
		
		Latitude lat = parse_latitude(coords[0]); // Assuming latitude comes first
		Longitude lon = parse_longitude(coords[1]);	
		
		return new CoordinateWorld(lat, lon);
	}
	
	private CoordinateWorld parse_coordinates(String string) throws ParseException{
		String[] coords = string.split("/");
		
		Latitude lat = parse_latitude(coords[0]); // Assuming latitude comes first
		Longitude lon = parse_longitude(coords[1]);	
		
		return new CoordinateWorld(lat, lon);
		
		
	}
	
	private AngleNavigational parse_course() throws ParseException
	{
		double angleDegrees;
		
		try{ angleDegrees = mScanner.nextDouble(); }
		
		catch(NumberFormatException e) { throw new ParseException("Invalid heading value."); }
		
		return new AngleNavigational(angleDegrees);
	}
	
	private Distance parse_distance() throws ParseException
	{
		double distanceFeet;
		
		try { distanceFeet = mScanner.nextDouble(); }
		
		catch(NumberFormatException e) { throw new ParseException("Invalid distance value."); }
		
		return new Distance(distanceFeet);
	}
	
	private AttitudePitch parse_elevation() throws ParseException
	{
		double attitudeMathDegrees;
		
		try { attitudeMathDegrees = mScanner.nextDouble(); }
		
		catch(NumberFormatException e) { throw new ParseException("Invalid attitude value."); }
		
		return new AttitudePitch(attitudeMathDegrees);
	}
	
	private Flow parse_flow() throws ParseException
	{
		double flowPPS;
		
		try { flowPPS = mScanner.nextDouble(); }
		
		catch(NumberFormatException e) { throw new ParseException("Invalid flow value."); }
		
		return new Flow(flowPPS);
	}
	
	private Identifier parse_id() throws ParseException 
	{
		String regex = "^[a-zA-Z0-9_]*$";
		String ID = mScanner.next();
		
		if(ID.matches(regex))
			return new Identifier(ID);
		else
			throw new ParseException("Invalid ID value.");
	}
	
	@SuppressWarnings("unused")
	private Latitude parse_latitude() throws ParseException
	{
		String[] values = mScanner.nextLine().split("[*'\"]");
		int degrees;
		int minutes;
		double seconds;
		
		try
		{
			degrees = Integer.parseInt(values[0]);
			minutes = Integer.parseInt(values[1]);
			seconds = Double.parseDouble(values[2]);
		}
		catch(NumberFormatException e) { throw new ParseException("Invalide latitude value."); }
		
		return new Latitude(degrees, minutes, seconds);
	}
	
	private Latitude parse_latitude(String lat) throws ParseException
	{
		String[] values = lat.split("[*'\"]");
		int degrees;
		int minutes;
		double seconds;
		
		try
		{
			degrees = Integer.parseInt(values[0]);
			minutes = Integer.parseInt(values[1]);
			seconds = Double.parseDouble(values[2]);
		}
		catch(NumberFormatException e) { throw new ParseException("Invalide latitude value."); }
		
		return new Latitude(degrees, minutes, seconds);
	}

	@SuppressWarnings("unused")
	private Longitude parse_longitude() throws ParseException
	{
		String[] values = mScanner.nextLine().split("[*'\"]");
		int degrees;
		int minutes;
		double seconds;
		
		try
		{
			degrees = Integer.parseInt(values[0]);
			minutes = Integer.parseInt(values[1]);
			seconds = Double.parseDouble(values[2]);
		}
		
		catch(NumberFormatException e) { throw new ParseException("Invalid longitude value."); }
		
		return new Longitude(degrees, minutes, seconds);
	}
	
	private Longitude parse_longitude(String lon) throws ParseException
	{
		String[] values = lon.split("[*'\"]");
		int degrees;
		int minutes;
		double seconds;
		
		try
		{
			degrees = Integer.parseInt(values[0]);
			minutes = Integer.parseInt(values[1]);
			seconds = Double.parseDouble(values[2]);
		}
		
		catch(NumberFormatException e) { throw new ParseException("Invalid longitude value."); }
		
		return new Longitude(degrees, minutes, seconds);
	}
	
	private Rate parse_rate() throws ParseException
	{
		int rateMS;
		
		try { rateMS = mScanner.nextInt(); }
		
		catch(NumberFormatException e) {throw new ParseException("Invalid rate value."); }
		
		return new Rate(rateMS);
	}
	
	
	private Time parse_time() throws ParseException
	{
		double seconds;
		
		try { seconds = mScanner.nextDouble(); }
		
		catch(NumberFormatException e) { throw new ParseException("Invalid time value."); }
		
		return new Time(seconds);
	}
	
	
	private AngleNavigational parse_azimuth() throws ParseException 
	{
		double azimuth;
		
		try{ azimuth = Double.parseDouble(mScanner.next()); }
		
		catch(NumberFormatException e) { throw new ParseException("Invalid azimuth value."); }
		
		return new AngleNavigational(azimuth);
	}
	
	private AngleNavigational parse_azimuth(String string) throws ParseException 
	{
		double azimuth;
		
		try{ azimuth = Double.parseDouble(string); }
		
		catch(NumberFormatException e) { throw new ParseException("Invalid azimuth value."); }
		
		return new AngleNavigational(azimuth);
	}


	private Percent parse_percent() throws ParseException 
	{
		Double percent;
		
		try{ percent = Double.parseDouble(mScanner.next()); }
		
		catch(NumberFormatException e) { throw new ParseException("Invalid percent value."); }
		
		return new Percent(percent);
	}


	private CoordinateCartesianRelative parse_origin() throws ParseException 
	{
		String originString = mScanner.next();
		String[] xy = originString.split(":");
		
		double x;
		double y;
		
		try
		{
			x = Double.parseDouble(xy[0]);
			y = Double.parseDouble(xy[1]);
		}
		catch(NumberFormatException e) { throw new ParseException("Invalid origin value."); }
		
		return new CoordinateCartesianRelative(x, y);
		
	}


	private Speed parse_speed() throws ParseException 
	{
		double speed;
		
		try{ speed = Double.parseDouble(mScanner.next()); }
		
		catch(NumberFormatException e) { throw new ParseException("Invalid speed value."); }
		
		return new Speed(speed);
	}
	
	private Speed parse_speed(String string) throws ParseException 
	{
		double speed;
		
		try{ speed = Double.parseDouble(string); }
		
		catch(NumberFormatException e) { throw new ParseException("Invalid speed value."); }
		
		return new Speed(speed);
	}


	private Weight parse_weight() throws ParseException 
	{
		double weight;
		
		try{ weight = Double.parseDouble(mScanner.next()); }
		
		catch(NumberFormatException e) { throw new ParseException("Invalid weight value"); }
		
		return new Weight(weight);
	}



	private Rate parse_rate(String nextToken) throws ParseException 
	{
		int rateMS;
		
		try { rateMS = Integer.parseInt(nextToken); }
		
		catch(NumberFormatException e) {throw new ParseException("Invalid rate value."); }
		
		return new Rate(rateMS);
	}
	
}
