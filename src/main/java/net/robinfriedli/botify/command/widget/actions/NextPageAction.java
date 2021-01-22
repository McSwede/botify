package net.robinfriedli.botify.command.widget.actions;

import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.robinfriedli.botify.command.CommandContext;
import net.robinfriedli.botify.command.widget.AbstractPaginationAction;
import net.robinfriedli.botify.command.widget.AbstractPaginationWidget;
import net.robinfriedli.botify.command.widget.AbstractWidget;
import net.robinfriedli.botify.command.widget.WidgetManager;

public class NextPageAction extends AbstractPaginationAction {

    public NextPageAction(String identifier, String emojiUnicode, boolean resetRequired, CommandContext context, AbstractWidget widget, GuildMessageReactionAddEvent event, WidgetManager.WidgetActionDefinition widgetActionDefinition) {
        super(identifier, emojiUnicode, resetRequired, context, widget, event, widgetActionDefinition);
    }

    @Override
    public void doRun() throws Exception {
        AbstractPaginationWidget<?> paginationWidget = getPaginationWidget();

        int pageCount = paginationWidget.getPages().size();
        int currentPage = paginationWidget.getCurrentPage();

        if (pageCount > 1) {
            if (currentPage < pageCount - 1) {
                paginationWidget.incrementPage();
            } else {
                paginationWidget.setCurrentPage(0);
            }
        } else {
            setFailed(true);
        }
    }
}
