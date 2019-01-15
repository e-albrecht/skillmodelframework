package de.unigoe.informatik.swe.data;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;

import de.unigoe.informatik.swe.eval.CaseStudyConfig;
import de.unigoe.informatik.swe.eval.CaseStudyRunner;

public class Main {
	
	private static CaseStudyConfig config;
	
	/**
	 * Runs a complete analysis
	 * @param args First argument specifies for which course an analysis shall be performed, second argument specifies KC level (default: 0=all)
	 * @throws Exception
	 */
	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws Exception {
		config = new CaseStudyConfig();
		CmdLineParser parser = new CmdLineParser(config);
		try {
			parser.parseArgument(args); // load configuration from command line options
			if (config.getLevel()<0 || config.getLevel() >4) throw new CmdLineException(parser, "Only level 0-3 allowed");
			CaseStudyRunner runner = new CaseStudyRunner(config);
			runner.run();          			
		} catch (CmdLineException e) {
            // handling of wrong arguments
            System.err.println(e.getMessage());
            parser.printUsage(System.err);
        }
	}
	
}
