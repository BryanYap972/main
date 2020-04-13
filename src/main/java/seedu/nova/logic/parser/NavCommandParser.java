package seedu.nova.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.nova.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import seedu.nova.logic.commands.commoncommands.NavCommand;
import seedu.nova.logic.parser.exceptions.ParseException;

/**
 * Parser to parse navigation related commands
 */
public class NavCommandParser implements Parser<NavCommand> {
    @Override
    public NavCommand parse(String args) throws ParseException {
        requireNonNull(args);
        switch (args) {
        case "home":
            return new NavCommand(ModeEnum.HOME);

        case "ab":
            return new NavCommand(ModeEnum.ADDRESSBOOK);

        case "schedule":
            return new NavCommand(ModeEnum.SCHEDULE);

        case "progresstracker":
            return new NavCommand(ModeEnum.PROGRESSTRACKER);

        case "planner":
            return new NavCommand(ModeEnum.PLANNER);

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }
}
