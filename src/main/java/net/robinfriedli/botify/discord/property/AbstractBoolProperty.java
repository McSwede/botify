package net.robinfriedli.botify.discord.property;

import net.robinfriedli.botify.entities.GuildSpecification;
import net.robinfriedli.botify.entities.xml.GuildPropertyContribution;

/**
 * Property extension for properties that have a boolean value.
 */
public abstract class AbstractBoolProperty extends AbstractGuildProperty {

    public AbstractBoolProperty(GuildPropertyContribution contribution) {
        super(contribution);
    }

    @Override
    public void validate(Object state) {
    }

    @Override
    public Object process(String input) {
        return Boolean.parseBoolean(input);
    }

    @Override
    public void setValue(String value, GuildSpecification guildSpecification) {
        setBoolValue((boolean) process(value), guildSpecification);
    }

    protected abstract void setBoolValue(boolean bool, GuildSpecification guildSpecification);

}
