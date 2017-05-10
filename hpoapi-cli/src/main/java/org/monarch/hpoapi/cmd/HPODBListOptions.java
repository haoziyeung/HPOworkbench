package org.monarch.hpoapi.cmd;

import java.util.function.BiFunction;

import com.google.common.collect.Lists;


import net.sourceforge.argparse4j.impl.Arguments;
import net.sourceforge.argparse4j.inf.ArgumentGroup;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.Namespace;
import net.sourceforge.argparse4j.inf.Subparser;
import net.sourceforge.argparse4j.inf.Subparsers;
import org.monarch.hpoapi.exception.UncheckedException;

/**
 * Configuration for the <tt>db-list</tt> command
 *
 * @author <a href="mailto:manuel.holtgrewe@bihealth.de">Manuel Holtgrewe</a>
 */
public class HPODBListOptions extends PhenotypeDBOptions {

    /**
     * Setup {@link ArgumentParser}
     *
     * @param subParsers {@link Subparsers} to setup
     */
    public static void setupParser(Subparsers subParsers) {
        BiFunction<String[], Namespace, DatabaseListCommand> handler = (argv, args) -> {
            try {
                return new DatabaseListCommand(argv, args);
            } catch (CommandLineParsingException e) {
                throw new UncheckedException("Could not parse command line",e );
            }
        };

        Subparser subParser = subParsers.addParser("db-list", true).help("list databases available for download")
                .setDefault("cmd", handler);
        subParser.description("List databases available for download");

        ArgumentGroup optionalGroup = subParser.addArgumentGroup("Optional Arguments");
        optionalGroup.addArgument("-s", "--io-source-list").help("INI file with io source list")
                .setDefault(Lists.newArrayList("bundle:///default_sources.ini")).action(Arguments.append());

        PhenotypeBaseOptions.setupParser(subParser);
    }

}

