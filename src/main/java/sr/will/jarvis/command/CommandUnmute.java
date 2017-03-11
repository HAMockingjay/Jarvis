package sr.will.jarvis.command;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;
import sr.will.jarvis.Jarvis;

import java.awt.*;

public class CommandUnmute extends Command {
    private Jarvis jarvis;

    public CommandUnmute(Jarvis jarvis) {
        this.jarvis = jarvis;
    }

    @Override
    public void execute(Message message, String... args) {
        if (!message.getGuild().getMemberById(message.getAuthor().getId()).hasPermission(Permission.VOICE_MUTE_OTHERS)) {
            message.getChannel().sendMessage(new EmbedBuilder().setTitle("Error", "https://jarvis.will.sr").setColor(Color.RED).setDescription("You don't have permission for that").build()).queue();
            return;
        }

        for (User user : message.getMentionedUsers()) {
            unmute(user, message.getGuild(), message.getChannel());
        }
    }

    public void unmute(User user, Guild guild, MessageChannel channel) {
        if (!jarvis.muteManager.isMuted(guild.getId(), user.getId())) {
            channel.sendMessage(new EmbedBuilder().setTitle("Error", "https://jarvis.will.sr").setColor(Color.RED).setDescription("User is not muted").build()).queue();
            return;
        }

        jarvis.muteManager.unmute(guild.getId(), user.getId());
        channel.sendMessage(new EmbedBuilder().setTitle("Success", "https://jarvis.will.sr").setColor(Color.GREEN).setDescription(user.getAsMention() + " has been unmuted").build()).queue();
    }
}
