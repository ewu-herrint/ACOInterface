package acg.project.startup;

import acg.architecture.support.Assert;
import acg.project.cli.CommandLineInterface;

//=============================================================================================================================================================
/**
 * Defines the entry point to the simulator.
 * 
 * #CS350 DO NOT DO ANYTHING WITH THIS FILE#
 * 
 * @author Dan Tappan [31.01.14]
 */
public class Startup
{
   // ---------------------------------------------------------------------------------------------------------------------------------------------------------
   /**
    * Starts the simulator.
    * 
    * @param arguments - there are no arguments
    * 
    * @throws Exception if anything fails
    */
   public static void main(final String[] arguments) throws Exception
   {
      Assert.notallowed(arguments);

      new Startup();
   }

   // ---------------------------------------------------------------------------------------------------------------------------------------------------------
   /**
    * Creates a simulator.
    * 
    * @throws Exception if anything fails
    */
   public Startup() throws Exception
   {
      System.err.println("### ACCESSING YOUR Startup ###");

      (new StartupLoader()).buildDesktop(false);

      try
      {
         CommandLineInterface cli = new CommandLineInterface();

         cli.processOutput("Welcome to our aircraft-carrier simulator!");

         cli.execute();
      }
      catch (Exception exception)
      {
         exception.printStackTrace();
      }
   }
}
