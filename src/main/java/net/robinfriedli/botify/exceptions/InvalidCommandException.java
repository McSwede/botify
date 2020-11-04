package net.robinfriedli.botify.exceptions;

import net.robinfriedli.botify.discord.property.properties.PrefixProperty;

/**
 * Type of command thrown when the cause is user error. This exception typically gets caught and its message sent to
 * Discord.
 */
public class InvalidCommandException extends AdditionalInformationException {

    public InvalidCommandException() {
        super();
    }

    public InvalidCommandException(String errorMessage) {
        super(errorMessage);
    }

    public InvalidCommandException(Throwable cause) {
        super(cause);
    }

    public InvalidCommandException(String errorMessage, Throwable cause) {
        super(errorMessage, cause);
    }

    @Override
    public String getAdditionalInformation() {
        String prefix = PrefixProperty.getEffectiveCommandStartForCurrentContext();
        return String.format("All available commands and their syntax is written in the pinned message");
    }

}
