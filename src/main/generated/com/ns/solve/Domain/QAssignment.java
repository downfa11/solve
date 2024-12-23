package com.ns.solve.Domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAssignment is a Querydsl query type for Assignment
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAssignment extends EntityPathBase<Assignment> {

    private static final long serialVersionUID = 1428616620L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAssignment assignment = new QAssignment("assignment");

    public final NumberPath<Long> assignmentId = createNumber("assignmentId", Long.class);

    public final StringPath comment = createString("comment");

    public final StringPath detail = createString("detail");

    public final StringPath gitRepository = createString("gitRepository");

    public final QMembership membership;

    public final QProblem problem;

    public QAssignment(String variable) {
        this(Assignment.class, forVariable(variable), INITS);
    }

    public QAssignment(Path<? extends Assignment> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAssignment(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAssignment(PathMetadata metadata, PathInits inits) {
        this(Assignment.class, metadata, inits);
    }

    public QAssignment(Class<? extends Assignment> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.membership = inits.isInitialized("membership") ? new QMembership(forProperty("membership")) : null;
        this.problem = inits.isInitialized("problem") ? new QProblem(forProperty("problem"), inits.get("problem")) : null;
    }

}

