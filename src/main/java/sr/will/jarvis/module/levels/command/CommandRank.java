package sr.will.jarvis.module.levels.command;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.User;
import sr.will.jarvis.command.Command;
import sr.will.jarvis.module.levels.ModuleLevels;

import java.awt.*;

public class CommandRank extends Command {
    private ModuleLevels module;

    public CommandRank(ModuleLevels module) {
        super("rank", "rank [user mention|user id]", "Displays the experience info about the mentioned member", module);
        this.module = module;
    }

    @Override
    public void execute(Message message, String... args) {
        checkModuleEnabled(message, module);

        User user = getMentionedUser(message, args);
        if (user == null) {
            user = message.getAuthor();
        }

        EmbedBuilder embed = new EmbedBuilder().setColor(Color.GREEN);
        embed.setAuthor(message.getGuild().getMember(user).getEffectiveName(), null, user.getEffectiveAvatarUrl());

        ModuleLevels.XPUser xpUser = module.getXPUser(message.getGuild().getIdLong(), user.getIdLong());

        int userLevel = module.getLevelFromXp(xpUser.xp);
        long levelXp = module.getLevelXp(userLevel);
        long nextLevelXp = module.getLevelXp(userLevel + 1);
        long userLevelXp = xpUser.xp - levelXp;

        embed.addField("Rank", xpUser.pos + "/" + xpUser.pos_total, true);
        embed.addField("Lvl", xpUser.level + "", true);
        embed.addField("Exp", userLevelXp + "/" + nextLevelXp + " (tot " + xpUser.xp + ")", true);

        message.getChannel().sendMessage(embed.build()).queue();
    }
}
