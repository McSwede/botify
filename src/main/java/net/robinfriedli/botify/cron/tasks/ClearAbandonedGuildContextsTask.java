package net.robinfriedli.botify.cron.tasks;

import org.slf4j.LoggerFactory;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.sharding.ShardManager;
import net.robinfriedli.botify.Botify;
import net.robinfriedli.botify.concurrent.CommandExecutionQueueManager;
import net.robinfriedli.botify.cron.AbstractCronTask;
import net.robinfriedli.botify.discord.GuildContext;
import net.robinfriedli.botify.discord.GuildManager;
import net.robinfriedli.botify.discord.listeners.GuildManagementListener;
import net.robinfriedli.exec.Mode;
import org.quartz.JobExecutionContext;

/**
 * Task that periodically removes {@link GuildContext} instances belonging to guilds that are no longer associated with
 * this bot and whose exit was not picked up by the {@link GuildManagementListener}
 */
public class ClearAbandonedGuildContextsTask extends AbstractCronTask {

    @Override
    protected void run(JobExecutionContext jobExecutionContext) {
        Botify botify = Botify.get();
        ShardManager shardManager = botify.getShardManager();
        GuildManager guildManager = botify.getGuildManager();
        CommandExecutionQueueManager executionQueueManager = botify.getExecutionQueueManager();

        int removedGuilds = 0;
        for (GuildContext guildContext : guildManager.getGuildContexts()) {
            Guild guild = guildContext.getGuild();
            if (shardManager.getGuildById(guild.getIdLong()) == null) {
                guildManager.removeGuild(guild);
                executionQueueManager.removeGuild(guild);
                ++removedGuilds;
            }
        }

        if (removedGuilds > 0) {
            LoggerFactory.getLogger(getClass()).info("Destroyed context for " + removedGuilds + " missing guilds");
        }
    }

    @Override
    protected Mode getMode() {
        return Mode.create();
    }
}
