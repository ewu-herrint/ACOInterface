package acg.project.action;

import java.util.ArrayList;
import java.util.List;

import acg.architecture.datatype.Acceleration;
import acg.architecture.datatype.Altitude;
import acg.architecture.datatype.AngleNavigational;
import acg.architecture.datatype.AttitudePitch;
import acg.architecture.datatype.CoordinateCartesianRelative;
import acg.architecture.datatype.CoordinateWorld;
import acg.architecture.datatype.Distance;
import acg.architecture.datatype.Flow;
import acg.architecture.datatype.Identifier;
import acg.architecture.datatype.Percent;
import acg.architecture.datatype.Speed;
import acg.architecture.datatype.Time;
import acg.architecture.datatype.Weight;
import acg.architecture.support.Assert;
import acg.project.action.command.ParameterAssignment;
import acg.project.action.command.creational.create.A_CommandCreationalCreate;
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
import acg.project.agent.A_Agent;
import acg.project.agent.primary.AgentPrimaryCarrier;
import acg.project.agent.primary.AgentPrimaryFighter;
import acg.project.agent.primary.AgentPrimaryTanker;
import acg.project.agent.secondary.AgentSecondaryAuxiliaryFuelTank;
import acg.project.agent.secondary.AgentSecondaryBarrier;
import acg.project.agent.secondary.AgentSecondaryBoomFemale;
import acg.project.agent.secondary.AgentSecondaryBoomMale;
import acg.project.agent.secondary.AgentSecondaryCatapult;
import acg.project.agent.secondary.AgentSecondaryOLSReceiver;
import acg.project.agent.secondary.AgentSecondaryOLSTransmitter;
import acg.project.agent.secondary.AgentSecondaryTailhook;
import acg.project.agent.secondary.AgentSecondaryTrap;
import acg.project.cli.CommandLineInterface;
import acg.project.map.MapAgent;
import acg.project.map.MapTemplate;

//=============================================================================================================================================================
/**
 * Defines the action processor for creational create commands. 
 * 
 * @author Dan Tappan [17.02.14]
 */
public class ActionCreationalCreate extends A_ActionCreational  
{
   /** the map of all defined templates */
   private final MapTemplate _mapTemplates;

   /** the map of agents that have been created but not added to other agents or the world yet */
   private final MapAgent _mapAgentsStaging;

   // ---------------------------------------------------------------------------------------------------------------------------------------------------------
   /**
    * Creates an action processor.
    * 
    * @param actionSet - the shared set of action processors for all command categories
    */
   protected ActionCreationalCreate(final ActionSet actionSet)
   {
      super(actionSet);

      _mapTemplates = actionSet.getMapTemplates();

      _mapAgentsStaging = actionSet.getMapAgentsStaging();
      
      System.err.println("### ACCESSING YOUR ActionCreationalCreate ###");
   }

   // ---------------------------------------------------------------------------------------------------------------------------------------------------------
   /**
    * Preprocesses a command for error checking and logging.
    * 
    * @param command - the command
    */
   private void preprocess(final A_CommandCreationalCreate command)
   {
      Assert.nonnull(command);

      if (isCommitted())
      {
         throw new RuntimeException("command not available after commit");
      }

      getActionSet().getCommandLineInterface().processOutput("CREATE> " + command); 
   }

   // #CS350 DON'T TOUCH ANYTHING ABOVE THIS POINT#
   
   // ---------------------------------------------------------------------------------------------------------------------------------------------------------
   /**
    * Submits a creational command for processing.
    * 
    * @param command - the command
    */
   public void submit(final CommandCreationalCreateAuxiliaryTank command)
   {
      preprocess(command);
      
      // #CS350 THIS IS A COMPLETE WORKING SOLUTION AS AN EXAMPLE#

      Identifier idAgent = command.getID_();
      Identifier idTemplate = command.getIDTemplate();

      CommandCreationalDefineAuxiliaryTank template = _mapTemplates.getCommand(idTemplate, CommandCreationalDefineAuxiliaryTank.class);

      Weight weight = template.getWeight();

      AgentSecondaryAuxiliaryFuelTank agent = new AgentSecondaryAuxiliaryFuelTank(idAgent, weight);

      _mapAgentsStaging.addAgent(agent);
   }

   // ---------------------------------------------------------------------------------------------------------------------------------------------------------
   /**
    * Submits a creational command for processing.
    * 
    * @param command - the command
    */
   public void submit(final CommandCreationalCreateBarrier command)
   {
      preprocess(command);

      // #CS350 IMPLEMENT THIS#  add an AgentSecondaryBarrier 
      
      Identifier idAgent = command.getID_();
      Identifier idTemplate = command.getIDTemplate();
      
      CommandCreationalDefineBarrier template = _mapTemplates.getCommand(idTemplate, CommandCreationalDefineBarrier.class);
      
      AngleNavigational azimuth = template.getAzimuth();
      
      CoordinateCartesianRelative origin = template.getOffset();
      
      Time time = template.getTime();
      
      Distance width = template.getWidth();
      
      AgentSecondaryBarrier agent = new AgentSecondaryBarrier(idAgent, origin, azimuth, width, time);
      
      _mapAgentsStaging.addAgent(agent);      
   }

   // ---------------------------------------------------------------------------------------------------------------------------------------------------------
   /**
    * Submits a creational command for processing.
    * 
    * @param command - the command
    */
   public void submit(final CommandCreationalCreateBoomFemale command)
   {
      preprocess(command);

      // #CS350 IMPLEMENT THIS#  add an AgentSecondaryBoomFemale 

      Identifier idAgent = command.getID_();
      Identifier idTemplate = command.getIDTemplate();
      
      CommandCreationalDefineBoomFemale template = _mapTemplates.getCommand(idTemplate, CommandCreationalDefineBoomFemale.class);
      
      Distance length = template.getLength();
      
      Distance diameter = template.getDiameter();
      
      AttitudePitch elevation = template.getElevation();
      
      Flow flow = template.getFlow();
      
      AgentSecondaryBoomFemale agent = new AgentSecondaryBoomFemale(idAgent, length, diameter, elevation, flow);
      
      _mapAgentsStaging.addAgent(agent);     
   }

   // ---------------------------------------------------------------------------------------------------------------------------------------------------------
   /**
    * Submits a creational command for processing.
    * 
    * @param command - the command
    */
   public void submit(final CommandCreationalCreateBoomMale command)
   {
      preprocess(command);

      // #CS350 IMPLEMENT THIS#  add an AgentSecondaryBoomMale 
      
      Identifier idAgent = command.getID_();
      Identifier idTemplate = command.getIDTemplate();
      
      CommandCreationalDefineBoomMale template = _mapTemplates.getCommand(idTemplate, CommandCreationalDefineBoomMale.class);
      
      Distance length = template.getLength();
      
      Distance diameter = template.getDiameter();
      
      Flow flow = template.getFlow();
      
      AgentSecondaryBoomMale agent = new AgentSecondaryBoomMale(idAgent, length, diameter, flow);
      
      _mapAgentsStaging.addAgent(agent);
   }

   // ---------------------------------------------------------------------------------------------------------------------------------------------------------
   /**
    * Submits a creational command for processing.
    * 
    * @param command - the command
    */
   public void submit(final CommandCreationalCreateCarrier command)
   {
      preprocess(command);
      
      Identifier idAgent = command.getID_();
      Identifier idTemplate = command.getIDTemplate();
      
      /*
       *  AgentPrimaryCarrier(Identifier id, AgentSecondaryCatapult catapult, AgentSecondaryBarrier barrier,
       *  AgentSecondaryTrap trap, AgentSecondaryOLSTransmitter olsTransmitter, CoordinateWorld coordinates, 
       *  AngleNavigational heading, Speed speed, Speed speedMax, Speed speedDeltaIncrease, Speed speedDeltaDecrease,
       *  AngleNavigational headingDelta, java.lang.String layoutFilename)
       */
      
      CommandCreationalDefineCarrier template = _mapTemplates.getCommand(idTemplate, CommandCreationalDefineCarrier.class);
      
      Identifier idCatapult = command.getIDAgentCatapult();
      AgentSecondaryCatapult catapult = _mapAgentsStaging.getAgent(idCatapult, AgentSecondaryCatapult.class);
      Identifier idBarrier = command.getIDAgentBarrier();
      AgentSecondaryBarrier barrier = _mapAgentsStaging.getAgent(idBarrier, AgentSecondaryBarrier.class);
      Identifier idTrap = command.getIDAgentTrap();
      AgentSecondaryTrap trap = _mapAgentsStaging.getAgent(idTrap, AgentSecondaryTrap.class);
      Identifier idOls = command.getIDAgentOLS();
      AgentSecondaryOLSTransmitter ols = _mapAgentsStaging.getAgent(idOls, AgentSecondaryOLSTransmitter.class);
      CoordinateWorld coordinates = command.getCoordinates();
      AngleNavigational heading = command.getHeading();
      Speed speed = command.getSpeed();
      
      AngleNavigational azimuthDelta = template.getHeadingDelta();
      Speed deltaIncrease = template.getSpeedDeltaIncrease();
      Speed deltaDecrease = template.getSpeedDeltaDecrease();
      Speed speedMax = template.getSpeedMax();
      String layoutFile = template.getLayoutFilename();
      
      
      AgentPrimaryCarrier carrier = new AgentPrimaryCarrier(idAgent, catapult, barrier, trap, ols, coordinates, heading,
    		  speed, speedMax, deltaIncrease,deltaDecrease, azimuthDelta, layoutFile);
      
      _mapAgentsStaging.addAgent(carrier);

      // #CS350 IMPLEMENT THIS#  add an AgentPrimaryCarrier 
   }

   // ---------------------------------------------------------------------------------------------------------------------------------------------------------
   /**
    * Submits a creational command for processing.
    * 
    * @param command - the command
    */
   public void submit(final CommandCreationalCreateCatapult command)
   {
      preprocess(command);

      // #CS350 IMPLEMENT THIS#  add an AgentSecondaryCatapult 
      
      Identifier idAgent = command.getID_();
      Identifier idTemplate = command.getIDTemplate();
      
      CommandCreationalDefineCatapult template = _mapTemplates.getCommand(idTemplate, CommandCreationalDefineCatapult.class);
      
      CoordinateCartesianRelative origin = template.getOffset();
      
      AngleNavigational azimuth = template.getAzimuth();
      
      Distance length = template.getLength();
      
      Acceleration acceleration = template.getAcceleration();
      
      Weight weight = template.getWeight();
      
      Speed speed = template.getSpeed();
      
      Time time = template.getTime();
      
      AgentSecondaryCatapult agent = new AgentSecondaryCatapult(idAgent, origin, azimuth, length, acceleration, weight, speed, time);
            
      _mapAgentsStaging.addAgent(agent);
   }

   // ---------------------------------------------------------------------------------------------------------------------------------------------------------
   /**
    * Submits a creational command for processing.
    * 
    * @param command - the command
    */
   public void submit(final CommandCreationalCreateFighter command)
   {
      preprocess(command);

      // #CS350 IMPLEMENT THIS#  add an AgentPrimaryFighter 
      
      Identifier idAgent = command.getID_();
      Identifier idTemplate = command.getIDTemplate();
      
      CommandCreationalDefineFighter template = _mapTemplates.getCommand(idTemplate, CommandCreationalDefineFighter.class);
      
      //OLS Reciever      
      AgentSecondaryOLSReceiver olsReceiver = _mapAgentsStaging.getAgent(command.getIDAgentOLS(), AgentSecondaryOLSReceiver.class);
      
      //BoomMale      
      AgentSecondaryBoomMale boomMale = _mapAgentsStaging.getAgent(command.getIDAgentBoom(), AgentSecondaryBoomMale.class);
      
      //Tailhook      
      AgentSecondaryTailhook tailhook = _mapAgentsStaging.getAgent(command.getIDAgentTailhook(), AgentSecondaryTailhook.class);
      
      //FuelTanks      
      
      List<Identifier> idTanks = command.getIDAgentTanks();
      List<AgentSecondaryAuxiliaryFuelTank> theTanks = new ArrayList<AgentSecondaryAuxiliaryFuelTank>();
      
      for(int count = 0; count < idTanks.size(); count++)
      {
    	  AgentSecondaryAuxiliaryFuelTank tempTank = _mapAgentsStaging.getAgent(idTanks.get(count), AgentSecondaryAuxiliaryFuelTank.class);
    	  theTanks.add(tempTank);
      }
      
      //Parameters (overrided)
      
      List<ParameterAssignment> params = command.getParameters();
      
      //Coordinates      
      CoordinateWorld coordinates = command.getCoordinates();
      
      //Altitude      
      Altitude altitude = command.getAltitude();
      
      //Heading      
      AngleNavigational heading = command.getHeading();
      
      //Speed      
      Speed speed = command.getSpeed();
      
      //SpeedMin      
      Speed speedMin = template.getSpeedMin();
      
      //SpeedMax      
      Speed speedMax = template.getSpeedMax();
      
      //SpeedDeltaIncrease      
      Speed speedDeltaIncrease = template.getSpeedDeltaIncrease();
      
      //SpeedDeltaDecrease      
      Speed speedDeltaDecrease = template.getSpeedDeltaDecrease();
      
      //HeadingDelta      
      AngleNavigational headingDelta = template.getHeadingDelta();
      
      //AltitudeDeltaClimb      
      Altitude altitudeDeltaClimb = template.getAltitudeDeltaClimb();
      
      //AltitudeDeltaDecent      
      Altitude altitudeDeltaDecent = template.getAltitudeDeltaDescent();
      
      //WeightEmpty      
      Weight empty = template.getWeightEmpty();
      
      //WeightFuel      
      Weight fuel = template.getWeightFuel();
      
      //WeightFuelDelta      
      Weight fuelDelta = template.getWeightFuelDelta();
      
      AgentPrimaryFighter agent = new AgentPrimaryFighter(idAgent, olsReceiver, boomMale, tailhook, theTanks, params, coordinates, altitude, heading, speed, speedMin, speedMax, speedDeltaIncrease, speedDeltaDecrease, headingDelta, altitudeDeltaClimb, altitudeDeltaDecent, empty, fuel, fuelDelta);
      
      _mapAgentsStaging.addAgent(agent);
   }

   // ---------------------------------------------------------------------------------------------------------------------------------------------------------
   /**
    * Submits a creational command for processing.
    * 
    * @param command - the command
    */
   public void submit(final CommandCreationalCreateOLSReceiver command)
   {
      preprocess(command);

      // #CS350 IMPLEMENT THIS#  add an AgentSecondaryOLSReceiver 
      
      Identifier idAgent = command.getID_();
      Identifier idTemplate = command.getIDTemplate();
      
      CommandCreationalDefineOLSReceiver template = _mapTemplates.getCommand(idTemplate, CommandCreationalDefineOLSReceiver.class);
      
      Distance diameter = template.getDiameter();
      
      AgentSecondaryOLSReceiver agent = new AgentSecondaryOLSReceiver(idAgent, diameter);
      
      _mapAgentsStaging.addAgent(agent);
   }

   // ---------------------------------------------------------------------------------------------------------------------------------------------------------
   /**
    * Submits a creational command for processing.
    * 
    * @param command - the command
    */
   public void submit(final CommandCreationalCreateOLSTransmitter command)
   {
      preprocess(command);

      // #CS350 IMPLEMENT THIS#  add an AgentSecondaryOLSTransmitter 
      
      Identifier idAgent = command.getID_();
      Identifier idTemplate = command.getIDTemplate();
      
      CommandCreationalDefineOLSTransmitter template = _mapTemplates.getCommand(idTemplate, CommandCreationalDefineOLSTransmitter.class);
      
      CoordinateCartesianRelative origin = template.getOffset();
      
      AngleNavigational azimuth = template.getAzimuth();
      
      AttitudePitch elevation = template.getElevation();
      
      Distance range = template.getRange();
      
      Distance diameter = template.getRange();
      
      AgentSecondaryOLSTransmitter agent = new AgentSecondaryOLSTransmitter(idAgent, origin, azimuth, elevation, range, diameter);
      
      _mapAgentsStaging.addAgent(agent);
   }

   // ---------------------------------------------------------------------------------------------------------------------------------------------------------
   /**
    * Submits a creational command for processing.
    * 
    * @param command - the command
    */
   public void submit(final CommandCreationalCreateTailhook command)
   {
      preprocess(command);
      
      // #CS350 IMPLEMENT THIS#  add an AgentSecondaryTailhook 
      
      Identifier idAgent = command.getID_();
      Identifier idTemplate = command.getIDTemplate();
      
      CommandCreationalDefineTailhook template = _mapTemplates.getCommand(idTemplate, CommandCreationalDefineTailhook.class);
      
      Time time = template.getTime();
      
      AgentSecondaryTailhook agent = new AgentSecondaryTailhook(idAgent, time);
      
      _mapAgentsStaging.addAgent(agent);
   }

   // ---------------------------------------------------------------------------------------------------------------------------------------------------------
   /**
    * Submits a creational command for processing.
    * 
    * @param command - the command
    */
   public void submit(final CommandCreationalCreateTanker command)
   {
      preprocess(command);

      // #CS350 IMPLEMENT THIS#  add an AgentPrimaryTanker 
      
      Identifier idAgent = command.getID_();
      Identifier idTemplate = command.getIDTemplate();
      
      CommandCreationalDefineTanker template = _mapTemplates.getCommand(idTemplate, CommandCreationalDefineTanker.class);
      
      Identifier idBoom = command.getIDAgentBoom();
      AgentSecondaryBoomFemale boom = _mapAgentsStaging.getAgent(idBoom, AgentSecondaryBoomFemale.class);

      
      CoordinateWorld coords = command.getCoordinates();
      Altitude alt = command.getAltitude();
      AngleNavigational heading = command.getHeading();
      Speed speed = command.getSpeed();
      
      Speed speedMin = template.getSpeedMin();
      Speed speedMax = template.getSpeedMax();
      Speed speedDeltaIncrease = template.getSpeedDeltaIncrease();
      Speed speedDeltaDecrease = template.getSpeedDeltaDecrease();
      AngleNavigational headingDelta = template.getHeadingDelta();
      Altitude altitudeDeltaClimb = template.getAltitudeDeltaClimb();
      Altitude altitudeDeltaDescent = template.getAltitudeDeltaDescent();
      Weight weight = template.getFuelWeight();
      
      AgentPrimaryTanker agent = new AgentPrimaryTanker(idAgent, boom, coords, alt, 
    		  heading, speed, speedMin, speedMax, speedDeltaIncrease, speedDeltaDecrease, 
    		  headingDelta, altitudeDeltaClimb, altitudeDeltaDescent, weight);
      
      _mapAgentsStaging.addAgent(agent);
   }

   // ---------------------------------------------------------------------------------------------------------------------------------------------------------
   /**
    * Submits a creational command for processing.
    * 
    * @param command - the command
    */
   public void submit(final CommandCreationalCreateTrap command)
   {
      preprocess(command);

      // #CS350 IMPLEMENT THIS#  add an AgentSecondaryTrap 
      
      Identifier idAgent = command.getID_();
      Identifier idTemplate = command.getIDTemplate();
      
      CommandCreationalDefineTrap template = _mapTemplates.getCommand(idTemplate, CommandCreationalDefineTrap.class);
      
      CoordinateCartesianRelative origin = template.getOffset();
      
      AngleNavigational azimuth = template.getAzimuth();
      
      Distance width = template.getWidth();
      
      Weight weight = template.getWeight();
      
      Speed speed = template.getSpeed();
      
      Percent missProbability = template.getMissProbability();
      
      AgentSecondaryTrap agent = new AgentSecondaryTrap(idAgent, origin, azimuth, width, weight, speed, missProbability);
      
      _mapAgentsStaging.addAgent(agent);
   }

   // #CS350 DON'T TOUCH ANYTHING BELOW THIS POINT#
   
   // ---------------------------------------------------------------------------------------------------------------------------------------------------------
   /**
    * Submits a creational command for processing.
    * 
    * @param command - the command
    */
   public void submit(final CommandCreationalDescribe command)
   {
      Assert.nonnull(command);

      CommandLineInterface cli = getActionSet().getCommandLineInterface();
      
      cli.processOutput("CREATE> " + command);

      Identifier idAgent = command.getID_();

      A_Agent agent = _mapAgentsStaging.getAgent(idAgent);

      cli.processOutput("AGENT> " + agent);
   }

   // ---------------------------------------------------------------------------------------------------------------------------------------------------------
   /**
    * Submits a creational command for processing.
    * 
    * @param command - the command
    */
   public void submit(final CommandCreationalListAgents command)
   {
      Assert.nonnull(command);

      CommandLineInterface cli = getActionSet().getCommandLineInterface();

      cli.processOutput("CREATE> " + command);
      cli.processOutput("AGENTS>");

      List<A_Agent> agents = _mapAgentsStaging.getAgents();

      for (A_Agent agent : agents)
      {
         cli.processOutput("      > " + agent.getID_());
      }
   }

   // ---------------------------------------------------------------------------------------------------------------------------------------------------------
   /**
    * Submits a creational command for processing.
    * 
    * @param command - the command
    */
   public void submit(final CommandCreationalUncreate command)
   {
      preprocess(command);

      Identifier idAgent = command.getID_();

      _mapAgentsStaging.removeAgent(idAgent);
   }
}
