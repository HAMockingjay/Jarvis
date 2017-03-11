package sr.will.jarvis.command;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.User;
import net.noxal.common.util.DateUtils;
import sr.will.jarvis.Jarvis;

import java.awt.*;

public class CommandMuteTime extends Command {
    private Jarvis jarvis;

    public CommandMuteTime(Jarvis jarvis) {
        this.jarvis = jarvis;
    }

    @Override
    public void execute(Message message, String... args) {
        if (message.getMentionedUsers().size() == 0) {
            message.getChannel().sendMessage(new EmbedBuilder().setTitle("Error", "https://jarvis.will.sr").setColor(Color.RED).setDescription("No user tagged").build()).queue();
            return;
        }

        User user = message.getMentionedUsers().get(0);
        long duration = jarvis.muteManager.getMuteDuration(message.getGuild().getId(), user.getId());

        if (!DateUtils.timestampApplies(duration)) {
            message.getChannel().sendMessage(new EmbedBuilder().setTitle("Error", "https://jarvis.will.sr").setColor(Color.RED).setDescription("User not muted").build()).queue();
            return;
        }

        message.getChannel().sendMessage(new EmbedBuilder().setTitle("Success", "https://jarvis.will.sr").setColor(Color.GREEN).setDescription("User is muted for " + DateUtils.formatDateDiff(duration)).build()).queue();
    }
}
