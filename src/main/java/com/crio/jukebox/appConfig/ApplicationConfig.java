package com.crio.jukebox.appConfig;

import com.crio.jukebox.repositories.IUserRepository;
import com.crio.jukebox.repositories.UserRepository;
import com.crio.jukebox.commands.CommandInvoker;
import com.crio.jukebox.commands.CreatePlayListCommand;
import com.crio.jukebox.commands.CreateUserCommand;
import com.crio.jukebox.commands.DeletePlayListCommand;
import com.crio.jukebox.commands.LoadDataCommand;
import com.crio.jukebox.commands.ModifyPlayListCommand;
import com.crio.jukebox.commands.PlayPlayListCommand;
import com.crio.jukebox.commands.PlaySongCommand;
import com.crio.jukebox.repositories.IPlayListRepository;
import com.crio.jukebox.repositories.ISongRepository;
import com.crio.jukebox.repositories.PlayListRepository;
import com.crio.jukebox.repositories.SongRepository;
import com.crio.jukebox.services.IPlayListService;
import com.crio.jukebox.services.IUserService;
import com.crio.jukebox.services.PlayListService;
import com.crio.jukebox.services.UserService;

public class ApplicationConfig {
    private final IUserRepository userRepository = new UserRepository();
    private final ISongRepository songRepository = new SongRepository();
    private final IPlayListRepository playListRepository = new PlayListRepository();

    private final IUserService userService = new UserService(userRepository);
    private final IPlayListService playListService =
            new PlayListService(userRepository, songRepository, playListRepository);

    private final CreateUserCommand createUserCommand = new CreateUserCommand(userService);
    private final CreatePlayListCommand createPlayListCommand = new CreatePlayListCommand(playListService);
    private final DeletePlayListCommand deletePlayListCommand =
            new DeletePlayListCommand(playListService);
    private final LoadDataCommand loadDataCommand = new LoadDataCommand(songRepository);
    private final ModifyPlayListCommand modifyPlayListCommand =
            new ModifyPlayListCommand(playListService);
    private final PlayPlayListCommand playPlayListCommand =
            new PlayPlayListCommand(playListService);
    private final PlaySongCommand playSongCommand = new PlaySongCommand(playListService);

    private final CommandInvoker commandInvoker = new CommandInvoker();


    public CommandInvoker getCommandInvoker() {
        commandInvoker.register("LOAD-DATA", loadDataCommand);
        commandInvoker.register("CREATE-USER", createUserCommand);
        commandInvoker.register("CREATE-PLAYLIST", createPlayListCommand);
        commandInvoker.register("DELETE-PLAYLIST", deletePlayListCommand);
        commandInvoker.register("MODIFY-PLAYLIST", modifyPlayListCommand);
        commandInvoker.register("PLAY-PLAYLIST", playPlayListCommand);
        commandInvoker.register("PLAY-SONG", playSongCommand);

        return commandInvoker;
    }


}
