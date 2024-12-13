package com.ns.solve.Domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QAssignment is a Querydsl query type for Assignment
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAssignment extends EntityPathBase<Assignment> {

    private static final long serialVersionUID = 1428616620L;

    public static final QAssignment assignment = new QAssignment("assignment");

    public final NumberPath<Long> assignmentId = createNumber("assignmentId", Long.class);

    public final StringPath detail = createString("detail");

    public final StringPath gitRepository = createString("gitRepository");

    public final NumberPath<Long> membershipId = createNumber("membershipId", Long.class);

    public final NumberPath<Long> problemId = createNumber("problemId", Long.class);

    public QAssignment(String variable) {
        super(Assignment.class, forVariable(variable));
    }

    public QAssignment(Path<? extends Assignment> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAssignment(PathMetadata metadata) {
        super(Assignment.class, metadata);
    }

}

