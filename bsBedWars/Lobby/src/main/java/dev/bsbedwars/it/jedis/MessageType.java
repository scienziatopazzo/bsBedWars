package dev.bsbedwars.it.jedis;

import dev.bsbedwars.it.bedwars.PluginStatus;
import dev.bsbedwars.it.bedwars.Status;
import dev.bsbedwars.it.bedwars.Type;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum MessageType {

    STATUS(Status.class),
    TYPE(Type.class),
    PLUGINSTATUS(PluginStatus.class);



    private final Class<? extends Enum> enumClass;

}
