package net.robinfriedli.botify.entities.xml;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import javax.annotation.Nullable;

import net.robinfriedli.botify.command.AbstractCommand;
import net.robinfriedli.botify.command.CommandContext;
import net.robinfriedli.botify.command.CommandManager;
import net.robinfriedli.botify.exceptions.InvalidCommandException;
import net.robinfriedli.jxp.api.AbstractXmlElement;
import net.robinfriedli.jxp.collections.NodeList;
import net.robinfriedli.jxp.persist.Context;
import org.w3c.dom.Element;

public class CommandContribution extends AbstractXmlElement {

    // invoked by JXP
    @SuppressWarnings("unused")
    public CommandContribution(Element element, NodeList childNodes, Context context) {
        super(element, childNodes, context);
    }

    @Nullable
    @Override
    public String getId() {
        return getIdentifier();
    }

    public String getIdentifier() {
        return getAttribute("identifier").getValue();
    }

    @SuppressWarnings("unchecked")
    public AbstractCommand instantiate(CommandManager commandManager, CommandContext commandContext, String commandBody) {
        String identifier = getIdentifier();
        String implementation = getAttribute("implementation").getValue();
        String description = getAttribute("description").getValue();
        Class<? extends AbstractCommand> commandClass;
        try {
            commandClass = (Class<? extends AbstractCommand>) Class.forName(implementation);
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("Class " + implementation + " does not exist.");
        } catch (ClassCastException e) {
            throw new IllegalStateException("Class " + implementation + " is not an AbstractCommand");
        }

        try {
            Constructor<? extends AbstractCommand> constructor =
                commandClass.getConstructor(CommandContribution.class, CommandContext.class, CommandManager.class, String.class, String.class, String.class);
            return constructor.newInstance(this, commandContext, commandManager, commandBody, identifier, description);
        } catch (InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (cause instanceof InvalidCommandException) {
                throw (InvalidCommandException) cause;
            }
            throw new RuntimeException("Exception while invoking constructor of " + commandClass.getSimpleName(), e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("Class " + commandClass.getSimpleName() +
                " does not have a constructor matching CommandContext, CommandManager, String, String", e);
        } catch (InstantiationException e) {
            throw new RuntimeException("Command class " + commandClass.getSimpleName() + " could not be instantiated.", e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Cannot access constructor of " + commandClass.getSimpleName(), e);
        }
    }

}
