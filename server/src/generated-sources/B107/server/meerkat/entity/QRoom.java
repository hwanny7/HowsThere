package B107.server.meerkat.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QRoom is a Querydsl query type for Room
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRoom extends EntityPathBase<Room> {

    private static final long serialVersionUID = 1249267838L;

    public static final QRoom room = new QRoom("room");

    public final NumberPath<Long> idx = createNumber("idx", Long.class);

    public final BooleanPath isValid = createBoolean("isValid");

    public final NumberPath<Long> requestIdx = createNumber("requestIdx", Long.class);

    public final NumberPath<Long> responseIdx = createNumber("responseIdx", Long.class);

    public final StringPath roomName = createString("roomName");

    public QRoom(String variable) {
        super(Room.class, forVariable(variable));
    }

    public QRoom(Path<? extends Room> path) {
        super(path.getType(), path.getMetadata());
    }

    public QRoom(PathMetadata metadata) {
        super(Room.class, metadata);
    }

}

