package one.terenin.dev.entities;

import lombok.Data;
import one.terenin.dev.graphics.BaseScreen;
import one.terenin.dev.levels.BaseLevel;

@Data
public abstract class Entity {

    public int x;
    public int y;

    protected BaseLevel level;

    public Entity(BaseLevel level) {
        init(level);
    }

    public void init(BaseLevel level){
        this.level = level;
    }

    public abstract void render(BaseScreen screen);

    public abstract void tick();

}
